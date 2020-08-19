package com.git.easyloan.entity;


public enum Error {

    ERROR_0001("0001", "", "") {
        public Responder getReason() {
            return null;
        }
    },
    ERROR_NULL("9997", "空指针异常", "") {
        public Responder getReason() {
            return null;
        }
    },
    ERROR_UNKNOWN("9998", "不支持的类型", "") {
        public Responder getReason() {
            return null;
        }
    },
    ERROR_9999("9999", "未知错误", "") {
        public Responder getReason() {
            return null;
        }
    };

    private String code;
    private String msg;
    private String type;

    public abstract Responder getReason();

    private Error(String code, String msg, String type) {
        this.code = code;
        this.msg = msg;
        this.type = type;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
