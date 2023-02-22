package com.damaohongtu.orderquerydemo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WxConfig {
    private String appId;
    private String appSecret;
    private String qrCodeUrl;
    private String tokenUrl;
    /**
     * 验签使用的token
     */
    private String token;
}