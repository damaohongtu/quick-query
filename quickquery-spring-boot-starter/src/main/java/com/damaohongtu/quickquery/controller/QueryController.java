package com.damaohongtu.quickquery.controller;

import com.alibaba.fastjson.JSON;
import com.damaohongtu.quickquery.client.QuickQueryClient;
import com.damaohongtu.quickquery.dto.client.QuickQueryRequest;
import com.damaohongtu.quickquery.dto.client.QuickQueryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/quickquery/")
public class QueryController {

    @Resource
    private QuickQueryClient quickQueryClient;

    @GetMapping("/")
    public String query(@RequestParam(name = "bizCode") String bizCode, @RequestParam(name = "serialNo") String serialNo) {
        QuickQueryRequest request = new QuickQueryRequest();
        request.setBizCode(bizCode);
        request.setSerialNo(serialNo);
        QuickQueryResponse response = quickQueryClient.query(request);
        return JSON.toJSONString(response);
    }
}
