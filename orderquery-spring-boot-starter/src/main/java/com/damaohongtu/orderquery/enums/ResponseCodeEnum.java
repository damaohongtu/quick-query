package com.damaohongtu.orderquery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {

    SUCCESS(10000, "SUCCESS"),
    FAIL(10001, "FAIL"),

    ;

    private Integer status;
    private String code;
}
