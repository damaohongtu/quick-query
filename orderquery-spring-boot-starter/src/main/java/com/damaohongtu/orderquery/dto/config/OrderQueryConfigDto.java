package com.damaohongtu.orderquery.dto.config;

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
public class OrderQueryConfigDto {

    private String bizCode;

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
         * 数据源：{"dataSource":"zft","table":"pay_global_index","hash":{"split":true,"dbSplit":true,"dbCount":8,"tableCount":128,"time":"month"}}
         */
        private String dataSource;
        /**
         * 输入字段：{"key":"pay_id"}
         */
        private String inputField;
        /**
         * 输出字段：[{"key":"pay_id","name":"直付单号"}, {"key":"out_pay_id","name":"外部单号"}, {"key":"seller_id","name":"商户ID"}, {"key":"partner_trade_no","name":"外部交易单号"}]
         */
        private String outputField;
        /**
         * 路由规则：^A.*$
         */
        private String routeRule;
        /**
         * 关联关系：(eg. [{"fromNode":"test_node_02","fromField":"index_id","toNode":"test_node_03","toField":"refund_id"},{"fromNode":"test_node_02","fromField":"index_id","toNode":"test_node_04","toField":"settle_id"}])
         */
        private String relations;
    }

}
