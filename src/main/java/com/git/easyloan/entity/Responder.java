package com.git.easyloan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class Responder<T> implements Serializable {

    private static final long serialVersionUID = 1500471167560546838L;
    private String code;
    private String message;
    private Object retObj;
    private Object[] retObjs;

    public Responder() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetObj() {
        return this.retObj;
    }

    public void setRetObj(Object retObj) {
        this.retObj = retObj;
    }

    public Object[] getRetObjs() {
        return this.retObjs;
    }

    public void setRetObjs(Object[] retObjs) {
        this.retObjs = retObjs;
    }

    public String getRetString() {
        return String.valueOf(this.retObj);
    }

    public int getRetInt() {
        return Integer.parseInt(this.getRetString());
    }

    public BigDecimal getRetBigDecimal() {
        return new BigDecimal(this.getRetString());
    }

    public String toString() {
        return "Responder [code=" + this.code + ", message=" + this.message + ", retObj=" + this.retObj + ", retObjs=" + Arrays.toString(this.retObjs) + "]";
    }
}

