package com.shanzhaozhen.classroom.param;

public class JsonMessage {

    private String title;

    private String message;

    private boolean success;

    private Object data;


    public JsonMessage(String message) {
        this.message = message;
    }

    public JsonMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public JsonMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public JsonMessage(String title, String message, boolean success) {
        this.title = title;
        this.message = message;
        this.success = success;
    }

    public JsonMessage(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public JsonMessage(String title, String message, boolean success, Object data) {
        this.title = title;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    //    public Map<String, Object> ResultOnlyMessage(String message) {
//        Map<String, Object> result = new HashMap<>();
//        result.put("message", message);
//        return result;
//    }
//
//    public Map<String, Object> ResultTitleAndMessage(String title, String message) {
//        Map<String, Object> result = new HashMap<>();
//        result.put("title", title);
//        result.put("message", message);
//        return result;
//    }



}
