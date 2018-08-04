package org.lotionvirgilabloh.lotionwebcontrol.entity;

import java.util.ArrayList;
import java.util.List;

public class JSchReturn {

    private boolean isSuccess;

    private List<String> returns;

    private int returnType;

    public JSchReturn() {
        this.isSuccess = false;
        this.returns = new ArrayList<>();
        this.returnType = 0;
    }

    public JSchReturn(boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.returns = new ArrayList<>();
        this.returnType = 0;
    }

    public JSchReturn(int returnType) {
        this.isSuccess = false;
        this.returns = new ArrayList<>();
        this.returnType = returnType;
    }

    public JSchReturn(boolean isSuccess, int returnType) {
        this.isSuccess = isSuccess;
        this.returns = new ArrayList<>();
        this.returnType = returnType;
    }

    public JSchReturn(boolean isSuccess, List<String> returns, int returnType) {
        this.isSuccess = isSuccess;
        this.returns = returns;
        this.returnType = returnType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<String> getReturns() {
        return returns;
    }

    public void setReturns(List<String> returns) {
        this.returns = returns;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "JSchReturn{" +
                "isSuccess=" + isSuccess +
                ", returns=" + returns +
                ", returnType=" + returnType +
                '}';
    }
}
