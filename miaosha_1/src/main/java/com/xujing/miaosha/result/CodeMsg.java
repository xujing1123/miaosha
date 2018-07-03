package com.xujing.miaosha.result;

public class CodeMsg {
    public int code;

    public String msg;

    // 通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROE = new CodeMsg(500100,"服务端异常");

    // 登陆模块 5002xx

    // 商品模块 5003xx

    //订单模块 5004xx

    // 秒杀模块 5005xx

    public CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
