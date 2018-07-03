package com.xujing.miaosha.result;

public class Result<T> {

    public int code;

    public String msg;

    public T data;

    private Result(T data)
    {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg)
    {
        if (null == codeMsg){
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }
    /**
     * 成功时使用
     * */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败时使用
     * */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }
}
