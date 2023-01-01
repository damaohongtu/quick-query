package com.damaohongtu.orderquery.controller;

import com.damaohongtu.orderquery.dao.entity.Graph;
import com.damaohongtu.orderquery.dao.entity.Sharding;
import com.damaohongtu.orderquery.dao.repo.GraphRepo;
import com.damaohongtu.orderquery.dao.repo.ShardingRepo;
import com.damaohongtu.orderquery.dto.config.OrderQueryConfigDto;
import com.damaohongtu.orderquery.executor.DataBaseExecutor;
import com.damaohongtu.orderquery.service.config.ConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: 大袤宏图
 * FileName: ConfigController
 * Description: 关联图配置HTTP入口
 */

@RestController
@RequestMapping("/api/orderquery/datasource")
public class DatasourceController {

    @Resource
    private ShardingRepo shardingRepo;

    @Resource
    private DataBaseExecutor dataBaseExecutor;

    @GetMapping("/hash")
    public List<Sharding> queryAllHash(){
        return shardingRepo.queryAllSharding();
    }


    @PostMapping("/hash/config")
    public String configHash(@RequestBody Sharding sharding){

        shardingRepo.insert(sharding);

        return "SUCCESS";
    }

    @GetMapping("/hash/query")
    public Sharding queryHash(@RequestParam String code){
        return shardingRepo.selectByCode(code);
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
