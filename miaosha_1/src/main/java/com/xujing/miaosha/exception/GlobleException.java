package com.xujing.miaosha.exception;

import com.xujing.miaosha.result.CodeMsg;

public class GlobleException extends RuntimeException{

    private CodeMsg cm;

    public GlobleException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.cm = codeMsg;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
