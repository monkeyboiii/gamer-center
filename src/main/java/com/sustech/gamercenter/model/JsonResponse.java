package com.sustech.gamercenter.model;

import java.util.Collections;
import java.util.List;

public class JsonResponse {
    // -1: error; 0: OK; 1: warn
    Integer code;
    String msg;
    List<?> data;


//  ----------------------------------------


    public JsonResponse(Integer code, String msg, List<?> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = Collections.emptyList();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
