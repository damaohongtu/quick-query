package com.damaohongtu.orderquerydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.damaohongtu.orderquerydemo.config.properties.WxConfig;
import com.damaohongtu.orderquerydemo.service.WeChatLoginService;
import com.damaohongtu.orderquerydemo.util.HttpClientUtil;
import com.damaohongtu.orderquerydemo.util.MyStringUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/login/wechat")
public class WechatLoginController {
    @Resource
    private WxConfig wxConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private WxMpService wxMpService;
    @Resource
    private WxMpMessageRouter wxMpMessageRouter;
    @Resource
    private WeChatLoginService weChatLoginService;

    /**
     * 获取登录二维码
     *
     * @return 登录二维码
     */
    @GetMapping("/getQrCode")
    public R getQrCode() {
        try {
            // 获取AccessToken
            String accessToken = weChatLoginService.getAccessToken();
            String getQrCodeUrl = wxConfig.getQrCodeUrl().replace("TOKEN", accessToken);
            // 这里生成一个带参数的二维码，参数是scene_str
            String sceneStr = MyStringUtil.getRandomString(8);
            String json = "{\"expire_seconds\": 120000, \"action_name\": \"QR_STR_SCENE\"" + ", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
            String result = HttpClientUtil.doPostJson(getQrCodeUrl, json);
            JSONObject jsonObject = JSONObject.parseObject(result);
            jsonObject.put("sceneStr", sceneStr);
            return R.ok().put("data", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     * 根据二维码场景值获取用户的openId => 获取用户信息
     *
     * @param eventKey
     * @return
     */
    @RequestMapping("/getOpenId")
    public R getOpenId(@RequestParam String eventKey) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(eventKey))) {
            return R.error("等待用户扫码");
        }
        User user = (User) redisTemplate.opsForValue().get(eventKey);
        redisTemplate.delete(eventKey);
        return R.ok("登录成功").put("data", user);
    }


    /**
     * 微信官方的回调处理
     * 官方文档:<a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html">...</a>
     *
     * @param request 微信发送的请求参数
     */
    @RequestMapping(value = "/message", produces = "application/xml; charset=UTF-8")
    public String handleMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取微信请求参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echoStr = request.getParameter("echostr");
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        } else {
            // 验签通过,直接返回echostr
//            response.getWriter().write(echoStr);
//            return null;
            // 解析消息体，封装为对象
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage;
            try {
                // 将消息路由给对应的处理器，获取响应
                outMessage = wxMpMessageRouter.route(inMessage);
            } catch (Exception e) {
                log.error("微信消息路由异常", e);
                outMessage = null;
            }
            // 将响应消息转换为xml格式返回
            response.getWriter().write(outMessage == null ? "" : outMessage.toXml());
            return null;
        }
    }
}