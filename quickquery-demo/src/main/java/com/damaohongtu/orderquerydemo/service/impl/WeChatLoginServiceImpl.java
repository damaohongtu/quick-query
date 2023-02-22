package com.damaohongtu.orderquerydemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.damaohongtu.orderquerydemo.config.properties.WxConfig;
import com.damaohongtu.orderquerydemo.service.WeChatLoginService;
import com.damaohongtu.orderquerydemo.util.HttpClientUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WeChatLoginServiceImpl implements WeChatLoginService {
    @Resource
    private WxConfig wxConfig;
    @Resource
    private WxMpService wxMpService;

    @Override
    public String getAccessToken() {
        String getTokenUrl = wxConfig.getTokenUrl()
                .replace("APPID", wxConfig.getAppId()).replace("SECRET", wxConfig.getAppSecret());
        String result = HttpClientUtil.doGet(getTokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("access_token");
    }

    @Override
    public WxMpUser getUserInfoByOpenid(String openId) throws WxErrorException {
        return wxMpService.getUserService().userInfo(openId);
    }
}