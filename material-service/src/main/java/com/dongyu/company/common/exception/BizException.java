package com.dongyu.company.common.exception;

/**
 * 系统异常
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3033655773692464022L;
    protected Object errorCode;
    protected Object[] args;

    public BizException() {
        super();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.args = args;
    }

    public BizException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public BizException(String message, Throwable cause, Object errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BizException(String message, Object errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(String message, Throwable cause, Object errorCode, Object[] args) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    public BizException(String message, Object errorCode, Object[] args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
