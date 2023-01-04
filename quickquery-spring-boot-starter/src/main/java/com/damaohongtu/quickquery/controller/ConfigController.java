package com.damaohongtu.quickquery.controller;

import com.damaohongtu.quickquery.controller.response.BaseResponse;
import com.damaohongtu.quickquery.controller.response.PageInfo;
import com.damaohongtu.quickquery.dto.config.QuickQueryConfigDto;
import com.damaohongtu.quickquery.dto.graph.GraphDto;
import com.damaohongtu.quickquery.dto.sharding.ShardingDto;
import com.damaohongtu.quickquery.enums.ResponseCodeEnum;
import com.damaohongtu.quickquery.service.config.ConfigService;
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
@RequestMapping("/api/quickquery")
public class ConfigController {

    @Resource
    private ConfigService configService;


    @GetMapping("/graph/list")
    public BaseResponse listGraph(@RequestParam Integer page, @RequestParam Integer size){

        PageInfo<GraphDto> data = configService.listGraph(page, size);

        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(data)
                .build();
        return response;
    }


    @PostMapping("/graph/config")
    public BaseResponse configGraph(@RequestBody QuickQueryConfigDto QuickQueryConfigDto){
        String resMsg = configService.configGraph(QuickQueryConfigDto);
        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .msg(resMsg)
                .build();
        return response;
    }

    @GetMapping("/graph/query")
    public BaseResponse queryGraph(@RequestParam Long graphId){

        QuickQueryConfigDto quickQueryConfigDto = configService.queryGraph(graphId);
        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(quickQueryConfigDto)
                .build();
        return response;
    }


    @GetMapping("/sharding/list")
    public BaseResponse listSharding(@RequestParam Integer page, @RequestParam Integer size){
        PageInfo<ShardingDto> shardingList = configService.listSharding(page, size);
        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(shardingList)
                .build();
        return response;
    }

    @GetMapping("/sharding/all")
    public BaseResponse listSharding(){
        PageInfo pageInfo = configService.listSharding();
        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(pageInfo)
                .build();
        return response;
    }

    @PostMapping("/sharding/config")
    public BaseResponse configSharding(@RequestBody ShardingDto shardingDto){
        String resMsg = configService.configSharding(shardingDto);
        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(resMsg)
                .build();
        return response;
    }

    @GetMapping("/sharding/query")
    public BaseResponse querySharding(@RequestParam String code){

        ShardingDto shardingDto = configService.querySharding(code);
        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(shardingDto)
                .build();
        return response;
    }

    @GetMapping("/database/all")
    public BaseResponse queryDataBase(){

        Map<String, List<String>> databaseList = configService.listDatabase();

        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(databaseList)
                .build();
        return response;
    }


    @GetMapping("/table/info")
    public BaseResponse queryTableInfo(@RequestParam String dataSourceType, @RequestParam String dataSourceName, @RequestParam String table){

        List tableInfo = configService.queryTableInfo(dataSourceType, dataSourceName, table);
        BaseResponse response = BaseResponse.builder()
                .status(ResponseCodeEnum.SUCCESS.getStatus())
                .code(ResponseCodeEnum.SUCCESS.getCode())
                .data(tableInfo)
                .build();
        return response;
    }


}
