package com.git.easyloan.utils.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MyException  extends RuntimeException {
    private static final long serialVersionUID = -7866074123502673924L;
    private String errCode = "";

    public MyException() {
    }

    public MyException(String message) {
        super(message.replaceAll("服务层异常，", ""));
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message, Throwable cause) {
        super(message.replaceAll("服务层异常，", ""), cause);
    }

    public MyException(String errCode, String message) {
        super(message.replaceAll("服务层异常，", ""));
        this.errCode = errCode;
    }

    public MyException(String errCode, String message, Throwable cause) {
        super(message.replaceAll("服务层异常，", ""), cause);
        this.errCode = errCode;
    }

    public String toString() {
        String s = this.getClass().getName();
        String message = this.getLocalizedMessage();
        if (null != this.getCause() && this.getCause() instanceof RuntimeBussinessException) {
            String causeMessage = this.getCause().getLocalizedMessage();
            if (causeMessage == null) {
                causeMessage = "";
            }

            return message != null ? message.replaceAll("服务层异常，", "") : "" + causeMessage;
        } else {
            return message != null ? s + ": " + message.replaceAll("服务层异常，", "") : s;
        }
    }

    public void printStackTrace(PrintStream s) {
        synchronized(s) {
            if (null != this.getCause() && this.getCause() instanceof RuntimeBussinessException) {
                s.println(this);
                return;
            }
        }

        super.printStackTrace(s);
    }

    public void printStackTrace(PrintWriter s) {
        synchronized(s) {
            if (null != this.getCause() && this.getCause() instanceof RuntimeBussinessException) {
                s.println(this);
                return;
            }
        }

        super.printStackTrace(s);
    }

    public String getMessage() {
        String causeMessage = "";
        if (null != this.getCause()) {
            causeMessage = this.getCause().getLocalizedMessage();
        }

        String messge = super.getMessage();
        if (null == messge) {
            messge = causeMessage;
        } else if (null != causeMessage && !"".equals(causeMessage.trim())) {
            messge = messge + "-" + causeMessage;
        }

        return messge.replaceAll("服务层异常，", "");
    }

    public String errCode() {
        return this.errCode;
    }
}
