package com.basecamp.campong.util;

import java.util.HashMap;

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

}
