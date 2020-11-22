package com.sustech.gamercenter.service.token;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component("Md5TokenGenerator")
public class MD5TokenGenerator implements TokenGenerator {

    @Override
    public String generate(String... strings) {
        long timestamp = System.currentTimeMillis();
        StringBuilder tokenMeta = new StringBuilder();
        for (String s : strings) {
            tokenMeta.append(s);
        }
        tokenMeta.append(timestamp);
        return DigestUtils.md5DigestAsHex(tokenMeta.toString().getBytes());
    }
}
