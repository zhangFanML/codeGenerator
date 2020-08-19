package com.git.easyloan.utils.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class RuntimeBussinessException extends MyException {
    private static final long serialVersionUID = -7683611469378806986L;

    public RuntimeBussinessException(String message) {
        super(message);
    }

    public String toString() {
        String message = this.getLocalizedMessage();
        return message != null ? message : "";
    }

    public void printStackTrace(PrintStream s) {
        synchronized(s) {
            s.println(this);
        }
    }

    public void printStackTrace(PrintWriter s) {
        synchronized(s) {
            s.println(this);
        }
    }
}
