package com.damaohongtu.quickquery.dto.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryConfigRequest
 * Description: OrderQuery图配置参数
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuickQueryConfigDto {

    private Long bizCode;

    private String bizName;

    private String bizDesc;

    private List<Node> nodes;

    /**
     * 边存储
     */
    private String edges;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node{

        /**
         * 节点ID
         */
        private String id;

        /**
         * 节点数据源类型
         */
        private String nodeType;

        /**
         * 节点名
         */
        private String nodeName;

        /**
         * 节点坐标：top,left(逗号分割)
         */
        private String coordinate;

        /**
         * 配置详情
         */
        private ConfigInfo configInfo;

    }

    /**
     * 节点配置
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConfigInfo{
        /**
         * 数据源
         * */
        private String dataSource;
        /**
         * 输入字段：{"key":"pay_id"}
         */
        private String inputField;
        /**
         * 输出字段
         * */
        private String outputField;
        /**
         * 路由规则：^A.*$
         */
        private String routeRule;
        /**
         * 关联关系
         * */
        private String relations;
    }

}
