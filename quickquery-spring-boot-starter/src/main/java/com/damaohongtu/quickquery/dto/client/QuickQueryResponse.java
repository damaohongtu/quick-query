package com.damaohongtu.quickquery.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryResponse
 * Description: OrderQuery响应
 */
@Data
@Builder
@AllArgsConstructor
public class QuickQueryResponse {

    private Long bizCode;

    private String bizName;

    private List<NodeDataRep> data;

}
