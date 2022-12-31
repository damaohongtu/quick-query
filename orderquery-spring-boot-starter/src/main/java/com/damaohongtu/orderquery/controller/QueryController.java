package com.damaohongtu.orderquery.controller;

import com.alibaba.fastjson.JSON;
import com.damaohongtu.orderquery.client.OrderQueryClient;
import com.damaohongtu.orderquery.dto.client.OrderQueryRequest;
import com.damaohongtu.orderquery.dto.client.OrderQueryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/OrderQuery")
public class QueryController {

    @Resource
    private OrderQueryClient orderQueryClient;

    @GetMapping("/order/query")
    public String test1(@RequestParam(name = "bizCode") String bizCode, @RequestParam(name = "serialNo") String serialNo) {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setBizCode(bizCode);
        request.setSerialNo(serialNo);
        OrderQueryResponse response = orderQueryClient.query(request);
        return JSON.toJSONString(response);
    }
}
