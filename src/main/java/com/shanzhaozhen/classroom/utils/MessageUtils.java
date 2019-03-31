package com.shanzhaozhen.classroom.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class MessageUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String ObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> resultSimpleMessage(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", message);
        return map;
    }

    public static Map<String, Object> resultSuccessMessage(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("msg", message);
        return map;
    }

    public static Map<String, Object> resultSuccessMessage(String title, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("success", true);
        map.put("msg", message);
        return map;
    }

    public static Map<String, Object> resultSuccessMessage(String title, String message, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("success", true);
        map.put("msg", message);
        map.put("data", object);
        return map;
    }

    public static Map<String, Object> resultFailureMessage(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg", message);
        return map;
    }

    public static Map<String, Object> resultFailureMessage(String title, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("success", false);
        map.put("msg", message);
        return map;
    }

    public static Map<String, Object> resultFailureMessage(String title, String message, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("success", false);
        map.put("msg", message);
        map.put("data", object);
        return map;
    }

}
