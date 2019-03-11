package com.shanzhaozhen.classroom.param;

import java.util.Date;

public class ResultParam {

    private Date timestamp;

    private int status;

    private int code;

    private String error;

    private String message;

    public Date getTimestamp() {
        return timestamp;
    }

    public ResultParam(Date timestamp, int status, int code, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.error = error;
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
