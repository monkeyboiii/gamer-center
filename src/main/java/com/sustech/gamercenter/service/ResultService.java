package com.sustech.gamercenter.service;

import java.util.HashMap;
import java.util.Map;

public class ResultService {
    public static Object success(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "success");
        map.put("data", data);
        return map;
    }
}
