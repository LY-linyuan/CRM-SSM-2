package com.onlyone.crm.commons.domain;

import java.util.Objects;

/**
 * @Author 临渊
 * @Date 2023-11-24 20:15
 */
public class ReturnObject {

    private String code;

    private String message;

    private Object retData;

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", retData=" + retData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnObject that = (ReturnObject) o;
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(retData, that.retData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, retData);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }

    public ReturnObject() {
    }

    public ReturnObject(String code, String message, Object retData) {
        this.code = code;
        this.message = message;
        this.retData = retData;
    }
}
