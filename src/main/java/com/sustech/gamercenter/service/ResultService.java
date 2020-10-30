package com.sustech.gamercenter.service;

import java.util.HashMap;
import java.util.Map;

public class ResultService {
    public static Object jsonResult(int code, String msg, Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }
}
