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
        if (null == codeMsg) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public static <T> Result<T> success(T data)
    {
        return new Result(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg)
    {
        return new Result(codeMsg);
    }

    public int getCode()
    {
        return this.code;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public T getData()
    {
        return this.data;
    }
}
