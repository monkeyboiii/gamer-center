package com.sustech.gamercenter.util.model;

import java.io.Serializable;

public class JsonResponse implements Serializable {
    // -1: error; 0: OK; 1: warn
    Integer code;
    String msg;
    Object data;


//  ----------------------------------------


    public JsonResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse() {
    }

    // Factory builder method
    public static class builder{
        Integer code;
        String msg;
        Object data;

        public builder code(Integer code) {
            this.code = code;
            return this;
        }

        public builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public builder data(Object data) {
            this.data = data;
            return this;
        }

        public JsonResponse build(){
            return new JsonResponse(code, msg, data);
        }

    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
