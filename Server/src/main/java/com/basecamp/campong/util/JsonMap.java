package com.basecamp.campong.util;

import java.util.HashMap;

/**
 * 에러코드와 메세지를 저장하는 HashMap
 */
public class JsonMap extends HashMap<String, Object> {

    public JsonMap(){
        put("error", false);
    }

    public void setMessage(String data){
        put("data", data);
    }

    public void setMessage(JsonMap data){
        put("data", data);
    }

    public void setError(long errorcode, String message){
        put("error", true);
        put("errorcode", errorcode);
        put("message", message);
    }

    public JsonMap setAuthFailed(){
        put("error", true);
        put("errorcode", 1006);
        put("message", "인증 실패");
        return this;
    }

}
