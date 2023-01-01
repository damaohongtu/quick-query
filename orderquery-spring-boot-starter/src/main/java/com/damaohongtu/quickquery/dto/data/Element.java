package com.damaohongtu.quickquery.dto.data;

import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: NodeField
 * Description: 节点字段属性
 */

@Data
public class Element {

    /**
     * 字段
     */
    private String key;

    /**
     * 字段名
     */
    private String name;

    /**
     * 基础数据类型
     */
    private String type;

    /**
     * 结果
     */
    private Object value;
}
