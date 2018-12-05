package com.xujing.miaosha.mail;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertiesUtil {

    private final ResourceBundle resource;

    private final String fileName;

    // yzovvcconiymggac
    // jonlnzlygqrogjea
    /**
     * 获取资源文件对象
     * */
    public PropertiesUtil(String fileName){
        this.fileName = fileName;
        Locale locale = new Locale("zh","CN");
        this.resource = ResourceBundle.getBundle(fileName,locale);
    }

    /**
     * 根据key获取值
     * */
    public String getValue(String key){
        return this.resource.getString(key);
    }



}

