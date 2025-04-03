package com.example.search.dto;

import java.util.Map;

public class GeneralResponse {

    private int code;
    private long timestamp;
    private Map<String, Object> data;

    public GeneralResponse(int code, long timestamp, Map<String, Object> data) {
        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
