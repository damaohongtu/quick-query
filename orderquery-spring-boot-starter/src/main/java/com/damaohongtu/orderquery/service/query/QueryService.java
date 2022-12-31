package com.damaohongtu.orderquery.service.query;

import com.alibaba.druid.util.StringUtils;
import com.damaohongtu.orderquery.dto.context.OrderQueryContext;
import com.damaohongtu.orderquery.dto.data.Condition;
import com.damaohongtu.orderquery.dto.data.Element;
import com.damaohongtu.orderquery.dto.graph.Node;
import com.damaohongtu.orderquery.dto.graph.Relation;
import com.damaohongtu.orderquery.enums.OrderQueryProcessorEnum;
import com.damaohongtu.orderquery.util.SpringUtil;
import com.damaohongtu.orderquery.processor.QueryProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: 大袤宏图
 * FileName: QueryService
 * Description: 查询服务
 */
@Service
public class QueryService {

    /**
     * 遍历route入口，选择相关数据源，组合查询条件，遍历查询。
     * @param context
     */
    public void query(OrderQueryContext context){

        for (String entryNodeCode : context.getEntryNodeList()) {
            Node entryNode = context.getGraph().get(entryNodeCode);
            Condition entryCondition = Condition.builder()
                    .key(entryNode.getInputField().getKey())
                    .values(Arrays.asList(context.getSerialNo())).build();

            this.traversal(context, entryNodeCode, entryCondition);

            while (CollectionUtils.isNotEmpty(context.getNextNode())) {

                Relation relation = context.getNextNode().pop();
                String toNodeCode = relation.getToNode();

                List<Object> conditions = this.genConditions(context, relation);
                Condition condition = Condition.builder()
                        .key(relation.getToField())
                        .values(conditions).build();
                this.traversal(context, toNodeCode, condition);
            }

            // 重置查询进度
            context.resetProcess();
        }
    }

    private void traversal(OrderQueryContext context, String nodeCode, Condition condition) {
        // 1. 获取入口节点
        Node node = context.getGraph().get(nodeCode);

        // 2. 更新取数进度
        condition.getValues().forEach(fromValue -> {
            context.updateProcess(nodeCode, condition.getKey(), String.valueOf(fromValue));
        });

        // 3. 取数
        List<List<Element>> entryNodeRes = this.fetchData(node, condition);

        // 4. 更新查询结果
        context.recordResult(nodeCode, entryNodeRes);

        // 5. 更新nextNode: 找到邻接节点
        List<Relation> relations = node.getNeighborNode();
        context.updateNextNode(relations, entryNodeRes);
    }


    /**
     * 通过历史数据 + 映射关系构造新的查询条件
     * @param context
     * @param relation
     * @return
     */
    private List<Object> genConditions(OrderQueryContext context, Relation relation) {
        List<Object> conditions = new ArrayList<>();

        String fromNodeCode = relation.getFromNode();
        List<List<Element>> rows = context.getCurrentResults().get(fromNodeCode);

        for (List<Element> row : rows){
            for(Element element : row){
                if(StringUtils.equals(relation.getFromField(), element.getKey())){
                    // 过滤已经查询过的条件取值
                    String key = String.format("%s_%s_%s",
                            relation.getToNode(),
                            relation.getToField(),
                            String.valueOf(element.getValue()));
                    if(!context.getProcess().containsKey(key)
                            || Objects.equals(context.getProcess().get(key), Boolean.FALSE)){
                        conditions.add(element.getValue());
                    }
                    break;
                }
            }
        }

        return conditions;
    }

    /**
     * 获取数据：基于反射，选择相应的数据源，获取数据
     * @param node
     * @param condition
     * @return
     */
    private List<List<Element>> fetchData(Node node, Condition condition)  {

        String nodeType = node.getNodeType();

        Class clazz = null;
        try {
            clazz = Class.forName(OrderQueryProcessorEnum.codeOf(nodeType).getQueryProcessor());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        QueryProcessor processor = (QueryProcessor) SpringUtil.getBean(clazz);

        return processor.query(node, condition);
    }


}
