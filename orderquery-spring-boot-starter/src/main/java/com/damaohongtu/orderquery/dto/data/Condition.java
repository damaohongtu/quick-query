package com.damaohongtu.orderquery.dto.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: 大袤宏图
 * FileName: Condition
 * Description: 条件
 */
@Data
@Builder
public class Condition {
    /**
     * 条件字段名
     */
    private String key;

    /**
     * 条件取值
     */
    private List<Object> values;
}
