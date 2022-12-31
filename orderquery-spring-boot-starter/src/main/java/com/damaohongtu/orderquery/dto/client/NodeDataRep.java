package com.damaohongtu.orderquery.dto.client;

import com.damaohongtu.orderquery.dto.data.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author: 大袤宏图
 * FileName: NodeDataRep
 * Description: 节点数据
 */

@Data
@Builder
@AllArgsConstructor
public class NodeDataRep {
    private String nodeName;
    private String nodeCode;
    private Set<List<Element>> rows;
}
