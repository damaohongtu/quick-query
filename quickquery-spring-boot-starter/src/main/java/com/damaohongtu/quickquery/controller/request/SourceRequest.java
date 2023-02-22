package com.damaohongtu.quickquery.controller.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SourceRequest {

    private String code;

    private String name;

    private String type;

    private String url;

    private String driver;

    private String username;

    private String password;

    private Integer order;


}
