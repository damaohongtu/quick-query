package com.damaohongtu.quickquery.processor;

import com.damaohongtu.quickquery.dto.data.Condition;
import com.damaohongtu.quickquery.dto.data.Element;
import com.damaohongtu.quickquery.dto.graph.NodeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 大袤宏图
 * FileName: QueryProcessor
 * Description: 查询处理器
 */

@Component
public abstract class QueryProcessor {

    public List<List<Element>> query(NodeDto nodeDTO, Condition condition){
        List<List<Element>> result = new ArrayList<>();

        List<Map> rows = this.fetch(nodeDTO, condition);

        Map<String, Element> resultFieldMap = nodeDTO.getOutputField().stream()
                .collect(Collectors.toMap(Element::getKey, Element -> Element));

        for(Map row : rows){
            List<Element> elementList = new ArrayList<>();
            for(Object key : row.keySet()){
                Element element = new Element();
                element.setKey(String.valueOf(key));
                element.setName(resultFieldMap.get(String.valueOf(key)).getName());
                element.setValue(row.get(key));
                elementList.add(element);
            }
            result.add(elementList);
        }
        return result;
    }


    /**
     * 取数据，所有数据返回为List<Map>
     * @param nodeDTO
     * @param condition
     * @return
     * @throws Exception
     */
    public abstract List<Map> fetch(NodeDto nodeDTO, Condition condition);
}
