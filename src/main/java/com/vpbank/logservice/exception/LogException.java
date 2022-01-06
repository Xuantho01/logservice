package com.vpbank.logservice.exception;


import com.vpbank.logservice.model.enums.ResponseCode;

/**
 * @author thohx_cmc
 * @created 08 - 21 - 2021
 * @description Manual message exception
 */
public class LogException extends Exception {

    private static final long serialVersionUID = 1L;
    private String message;
    private ResponseCode errorCode;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogException(String message) {
        this.message = message;
        errorCode = ResponseCode.ERROR;

    }

    public LogException(String message, ResponseCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;

    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public LogException(String message, ResponseCode errorCode, Throwable cause) {
        super(cause);
        this.message = message;
        this.errorCode = errorCode;
    }

    public ResponseCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ResponseCode errorCode) {
        this.errorCode = errorCode;
    }
}