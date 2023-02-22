package com.damaohongtu.orderquerydemo.config;

import com.damaohongtu.orderquerydemo.config.handler.ScanHandler;
import com.damaohongtu.orderquerydemo.config.handler.TextHandler;
import com.damaohongtu.orderquerydemo.config.properties.WxConfig;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

@Configuration
public class WeChatConfig {

    @Resource
    private WxConfig wxConfig;

    @Resource
    private WxMpService wxMpService;

    @Resource
    private TextHandler textHandler;

    @Resource
    private ImageHandler imageHandler;
    @Resource
    private SubscribeHandler subscribeHandler;
    @Resource
    private UnSubscribeHandler unSubscribeHandler;
    @Resource
    private ScanHandler scanHandler;

    // 单例
    @Bean
    @Scope("singleton")
    public WxMpService wxMpService() {
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(wxConfig.getAppId());
        wxMpDefaultConfig.setSecret(wxConfig.getAppSecret());
        wxMpDefaultConfig.setToken(wxConfig.getToken());
        WxMpService wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(wxMpDefaultConfig);
        return wxService;
    }

    @Bean
    public WxMpMessageRouter messageRouter() {
        // 创建消息路由
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 添加文本消息路由
        router.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(textHandler).end();
        // 添加关注事件路由
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler).end();
        // 添加扫码事件路由
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(scanHandler).end();
        return router;
    }

}