package com.dida.nowcoder.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    //获取随机字符串
    public static String generateUUID() {
        //获取随机字符串，并替换掉所有的横线
        return UUID.randomUUID().toString().replace("-", "");
    }

    //MD5加密
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
