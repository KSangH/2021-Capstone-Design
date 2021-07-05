package com.basecamp.campong.util;

import java.util.HashMap;

/**
 * 에러코드와 메세지를 저장하는 HashMap
 */
public class JsonMap extends HashMap<String, Object> {

    public JsonMap(){
        put("error", false);
    }

    public JsonMap setMessage(String data){
        put("data", data);
        return this;
    }

    public JsonMap setMessage(JsonMap data){
        put("data", data);
        return this;
    }

    public JsonMap setError(long errorcode, String message){
        put("error", true);
        put("errorcode", errorcode);
        put("message", message);
        return this;
    }

    public JsonMap setAuthFailed(){
        put("error", true);
        put("errorcode", 1006);
        put("message", "인증 실패");
        return this;
    }

    public boolean isError(){
        return (boolean) get("error");
    }

}
