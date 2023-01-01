package com.damaohongtu.orderquery.dto.context;

import com.alibaba.druid.util.StringUtils;
import com.damaohongtu.orderquery.dto.client.NodeDataRep;
import com.damaohongtu.orderquery.dto.data.Element;
import com.damaohongtu.orderquery.dto.graph.NodeDto;
import com.damaohongtu.orderquery.dto.graph.RelationDto;
import com.damaohongtu.orderquery.dto.client.OrderQueryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryContext
 * Description: OrderQuery上下文 ：
 *      由静态数据和动态数据两部分构成：（1）静态数据：缓存图信息；（2）动态数据：缓存中间结果、控制当前遍历进度。
 */
@Data
@Builder
@AllArgsConstructor
public class OrderQueryContext {

    /**
     * 图查询编码
     */
    private String graphCode;

    /**
     * 图查询名字
     */
    private String graphName;

    /**
     * 图信息, 在上下文中属于静态配置信息
     */
    private Map<String, NodeDto> graph;

    /**
     * 待查询流水号
     */
    private String serialNo;

    /**
     * 路由后的入口
     */
    private List<String> entryNodeList;

    /**
     * 图遍历的进度（防止重复查询）
     */
    private Map<String, Boolean> process;

    /**
     * 待查询的节点
     */
    private LinkedList<RelationDto> nextNode;

    /**
     * 当前查询结果
     */
    private Map<String, List<List<Element>>> currentResults;

    /**
     * 最终查询结果: key为节点编码，value为中间查询结果, 最终结果使用set进行去重
     */
    private Map<String, Set<List<Element>>> globalResults;


    /**
     * 将查询上下文信息转换为response
     * @return
     */
    public OrderQueryResponse toOrderQueryResponse(){

        Map<String, Set<List<Element>>> globalResMap = this.getGlobalResults();
        List<NodeDataRep> rows = new ArrayList<>();

        for(Object key:globalResMap.keySet()){
            NodeDto nodeDTO = graph.get(String.valueOf(key));
            NodeDataRep nodeDataRep = NodeDataRep.builder()
                    .nodeCode(nodeDTO.getNodeCode())
                    .nodeName(nodeDTO.getNodeName())
                    .rows(globalResMap.get(key))
                    .build();
            rows.add(nodeDataRep);
        }

        OrderQueryResponse response = OrderQueryResponse.builder()
                .bizName(this.graphName)
                .bizCode(this.graphCode)
                .data(rows)
                .build();
        return response;
    }


    /**
     * 更新进度：查询进度由待查询的"节点+字段+取值"三部分共同组成
     * @param fromCode
     * @param fromField
     * @param fromValue
     */
    public void updateProcess(String fromCode, String fromField, String fromValue){
        String key = String.format("%s_%s_%s", fromCode, fromField, fromValue);
        this.process.put(key, true);
    }

    /**
     * 更新待处理节点：当前节点在"progress"中没有记录或者还有条件没有被遍历，则加入待遍历的节点。
     * 按照节点进行遍历而非按照条件取值进行遍历，原因是按照节点遍历可以多个取值同时执行查询。
     * @param relationDtos
     * @param entryNodeRes
     */
    public void updateNextNode(List<RelationDto> relationDtos, List<List<Element>> entryNodeRes){
        for(RelationDto relationDto : relationDtos){
            TAG: for (List<Element> row : entryNodeRes){
                for(Element element : row){
                    if(StringUtils.equals(relationDto.getFromField(), element.getKey())){
                        String key = String.format("%s_%s_%s",
                                relationDto.getToNode(),
                                relationDto.getToField(),
                                String.valueOf(element.getValue()));
                        if(!this.process.containsKey(key)
                                || Objects.equals(this.process.get(key), Boolean.FALSE)){
                            this.nextNode.add(relationDto);
                            break TAG;
                        }
                    }
                }
            }
        }
    }


    /**
     * 更新当前结果
     * @param nodeCode
     * @param values
     */
    public void recordResult(String nodeCode, List<List<Element>> values){
        // 记录中间结果
        this.currentResults.computeIfAbsent(nodeCode, k -> new ArrayList<>()).addAll(values);

        // 记录全局结果
        this.globalResults.computeIfAbsent(nodeCode, k -> new HashSet<>()).addAll(values);
    }

    /**
     * 重置当前查询
     */
    public void resetProcess(){
        this.nextNode.clear();
        this.currentResults.clear();
        this.process.clear();
    }

}
