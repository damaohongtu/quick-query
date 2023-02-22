package com.damaohongtu.orderquerydemo.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

public interface WeChatLoginService {
    public String getAccessToken();
    public WxMpUser getUserInfoByOpenid(String openId) throws WxErrorException;
}
