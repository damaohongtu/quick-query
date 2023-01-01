package com.damaohongtu.quickquery.dto.graph;

import com.damaohongtu.quickquery.dao.entity.Graph;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryGraph
 * Description: 关联图
 */

@Data
@Builder
public class GraphDto {

    private String graphCode;

    private String graphName;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    private String edges;

    public static GraphDto fromPo(Graph graph){
        GraphDto graphDto = GraphDto.builder()
                .graphCode(graph.getGraphCode())
                .graphName(graph.getGraphName())
                .createTime(graph.getCreateTime())
                .updateTime(graph.getUpdateTime())
                .createBy(graph.getCreateBy())
                .updateBy(graph.getUpdateBy())
                .build();
        return graphDto;
    }

}
