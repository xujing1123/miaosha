package com.xujing.miaosha.test;

import com.xujing.miaosha.mail.MailContentTypeEnum;
import com.xujing.miaosha.mail.MailSender;

import java.util.ArrayList;

public class TestMail {
    public static void main(String[] args) throws  Exception{
        new MailSender().title("测试SpringBoot发送邮件").content("简单文本内容发送").
                contentType(MailContentTypeEnum.TEXT).
                targets(new ArrayList<String>(){{
                    //add("xujing115417@163.com");
                    add("xujing@eserviceone.com");
                }})
                .send();
/*        MailSender mailSender =  new MailSender().title("测试SpringBoot发送邮件").content("简单文本内容发送").
                contentType(MailContentTypeEnum.TEXT).
                targets(new ArrayList<String>(){{
                    add("xujing@eserviceone.com");
                }});
        mailSender.send();
        */
    }
}