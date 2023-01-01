package com.damaohongtu.orderquery.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageInfo<T> {

    private Long total;

    private List<T> data;

}
