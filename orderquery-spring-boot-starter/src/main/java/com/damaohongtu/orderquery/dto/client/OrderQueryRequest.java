package com.damaohongtu.orderquery.dto.client;

import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryRequest
 * Description: OrderQuery请求
 */
@Data
public class OrderQueryRequest {

    /**
     * 业务线编号：对应关联图的graphCode，业务方不感知OrderQuery关联图
     */
    private String bizCode;

    /**
     * 待查询流水号
     */
    private String serialNo;

}
