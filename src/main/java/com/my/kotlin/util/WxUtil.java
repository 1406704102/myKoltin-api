package com.my.kotlin.util;

import org.springframework.web.client.RestTemplate;

public class WxUtil {

    private static final String APPID = "wxf31996840f479cd8";
    private static final String SECRET = "983b9a62fd1fbd2ade0d2fd060ceff9c";

    public static String getJ2SR(String code) {
        return new RestTemplate().getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code", String.class);
    }
}
