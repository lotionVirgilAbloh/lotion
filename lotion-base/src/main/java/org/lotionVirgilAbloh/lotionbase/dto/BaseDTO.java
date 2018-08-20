package org.lotionvirgilabloh.lotionbase.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -2387196972406927899L;

    private String msg;

    public BaseDTO() {
    }

    public BaseDTO(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "msg='" + msg + '\'' +
                '}';
    }
}