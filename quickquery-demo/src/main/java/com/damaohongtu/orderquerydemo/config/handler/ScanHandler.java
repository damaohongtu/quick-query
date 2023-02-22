package com.damaohongtu.orderquerydemo.config.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
public class ScanHandler implements WxMpMessageHandler {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        log.info("ScanHandler调用");
        // 获取YYYY-MM-DD HH:MM:SS格式的时间
        String content = "您在" + (new DateTime().toString("yyyy-MM-dd HH:mm:ss")) + "通过微信扫码登录PaperFF网站,感谢您的使用。";
        // 获取场景值
        String openId = wxMpXmlMessage.getFromUser();
        // 判断用户是否存在
        User user = userService.getUserByOpenId(openId);
        if (user == null) {
            // 如果用户不存在，则创建用户
            user = userService.createUserByWeChat(openId);
        }
        // 将场景值和用户信息存入redis
        redisTemplate.opsForValue().set(wxMpXmlMessage.getEventKey(), user, 2, TimeUnit.MINUTES);
        return WxMpXmlOutMessage.TEXT().fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                .content(content).build();
    }
}