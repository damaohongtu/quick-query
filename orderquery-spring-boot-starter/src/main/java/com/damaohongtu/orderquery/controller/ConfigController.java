package com.damaohongtu.orderquery.controller;

import com.damaohongtu.orderquery.dto.config.OrderQueryConfigDto;
import com.damaohongtu.orderquery.executor.DataBaseExecutor;
import com.damaohongtu.orderquery.service.config.ConfigService;
import com.damaohongtu.orderquery.dal.entity.OrderQueryGraph;
import com.damaohongtu.orderquery.dal.entity.OrderQueryHash;
import com.damaohongtu.orderquery.dal.repo.OrderQueryGraphRepo;
import com.damaohongtu.orderquery.dal.repo.OrderQueryHashRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: 大袤宏图
 * FileName: ConfigController
 * Description: 关联图配置HTTP入口
 */

@RestController
@RequestMapping("/api/OrderQuery")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @Resource
    private OrderQueryGraphRepo OrderQueryGraphRepo;

    @Resource
    private OrderQueryHashRepo OrderQueryHashRepo;

    @Resource
    private DataBaseExecutor dataBaseExecutor;


    @GetMapping("/graph")
    public List<OrderQueryGraph> queryGraph(){
        return OrderQueryGraphRepo.queryAllGraph();
    }


    @PostMapping("/graph/config")
    public String configGraph(@RequestBody OrderQueryConfigDto OrderQueryConfigDto){
        return configService.config(OrderQueryConfigDto);
    }

    @GetMapping("/graph/query")
    public OrderQueryConfigDto queryGraph(@RequestParam String graphCode){
        return configService.query(graphCode);
    }


    @GetMapping("/hash")
    public List<OrderQueryHash> queryAllHash(){
        return OrderQueryHashRepo.queryAll();
    }


    @PostMapping("/hash/config")
    public String configHash(@RequestBody OrderQueryHash OrderQueryHash){

        OrderQueryHashRepo.insert(OrderQueryHash);

        return "SUCCESS";
    }

    @GetMapping("/hash/query")
    public OrderQueryHash queryHash(@RequestParam String code){
        return OrderQueryHashRepo.select(code);
    }

    @GetMapping("/database")
    public Map<String, List<String>> queryDataBase(){
        return dataBaseExecutor.listAllDbName();
    }

    @GetMapping("/tableInfo")
    public List queryTableInfo(@RequestParam String dataSourceType, @RequestParam String dataSourceName, @RequestParam String table){
        return dataBaseExecutor.getTableInfo(dataSourceType, dataSourceName, table);
    }

}
