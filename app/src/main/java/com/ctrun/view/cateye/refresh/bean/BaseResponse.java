package com.ctrun.view.cateye.refresh.bean;

public class BaseResponse<T> {
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_NONE = -1;

    public int code = CODE_NONE;
    public String msg;
    public T data;
    public boolean success;

    public ErrorResponse error;

    public boolean isSuccess() {
        return code == CODE_SUCCESS || success;
    }

    public static class ErrorResponse {
        public int code;
        public String message;
    }

}
