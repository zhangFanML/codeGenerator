package com.git.easyloan.utils.base;

import com.git.easyloan.entity.Error;
import com.git.easyloan.entity.Responder;
import com.git.easyloan.utils.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseMessage {

    private static Logger log = LoggerFactory.getLogger(BaseMessage.class);

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "处理成功:";
    public static final String WARN_CODE = "300";
    public static final String WARN_MESSAGE = "警告:";
    public static final String ERROR_CODE = "500";
    public static final String ERROR_MESSAGE = "交易失败:";

    public BaseMessage() {
    }

    public <T> Responder success() {
        return this.success("200", "处理成功:", (Object)null);
    }

    public <T> Responder success(String statusCode, String message) {
        return this.success(statusCode, message, (Object)null);
    }

    public <T> Responder success(String statusCode, String message, Object object) {
        return this.result(statusCode, message, object);
    }

    public <T> Responder warn(String wanrMsg) {
        return this.warn("300", "警告:" + wanrMsg, (Object)null);
    }

    public <T> Responder warn(String statusCode, String message, Object object) {
        return this.result(statusCode, message, object);
    }

    public <T> Responder result(String statusCode, String message, Object... object) {
        Responder<T> responder = new Responder();
        responder.setCode(statusCode);
        responder.setMessage(message);
        if (null != object) {
            if (object.length == 1) {
                responder.setRetObj(object[0]);
            } else {
                responder.setRetObjs(object);
            }
        } else {
            error(Error.ERROR_UNKNOWN);
        }

        return responder;
    }

    public static MyException error(Error error) {
        log.error("错误码：" + error.getCode() + ",错误信息：" + error.getMsg(), new Object[0]);
        MyException ee = new MyException(error.getCode(), error.getMsg());
        return ee;
    }

    public static MyException error(String statusCode, String message) {
        log.error("错误码：" + statusCode + ",错误信息：" + message, new Object[0]);
        MyException ee = new MyException(statusCode, message);
        return ee;
    }

    public static MyException error() {
        return error("500", "交易失败:");
    }

    public static boolean isSucc(Responder responder) {
        return "200".equals(responder.getCode());
    }

    public static boolean isError(Responder responder) {
        return !isSucc(responder);
    }
}

