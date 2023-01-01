package com.damaohongtu.quickquery.controller.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: BaseResponse
 * Description: 节点数据
 */
@Data
@Builder
public class BaseResponse<T> {

    private Integer status;

    private String code;

    private String msg;

    private T data;

}
