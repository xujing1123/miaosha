package com.xujing.miaosha.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;

public class MailSender {

    private static MailEntity mail = new MailEntity();

    /**
     * 设置邮件标题
     */
    public MailSender title(String title) {
        mail.setTitle(title);
        return this;
    }

    /**
     * 设置邮件内容
     */
    public MailSender content(String content) {
        mail.setContent(content);
        return this;
    }

    /**
     * 设置邮件格式
     */
    public MailSender contentType(MailContentTypeEnum contentType) {
        mail.setContentType(contentType.getValue());
        return this;
    }

    /**
     * 设置目标邮件地址
     */
    public MailSender targets(List<String> targets) {
        mail.setList(targets);
        return this;
    }

    /**
     * 执行发送邮件
     */
    public void send() throws Exception {
        // 默认只用http内容发送
        if (mail.getContentType() == null) {
            mail.setContentType(MailContentTypeEnum.HTML.getValue());
        }
        if (mail.getTitle() == null || mail.getTitle().trim().length() == 0) {
            throw new Exception("请设置标题");
        }
        if (mail.getContent() == null || mail.getContent().trim().length() == 0) {
            throw new Exception("请设置content");
        }
        if (mail.getList().size() == 0) {
            throw new Exception("请设置targets");
        }
        // 1.读取配置文件
        final PropertiesUtil propertiesUtil = new PropertiesUtil("mail");
        // 创建properties  记录邮件一些属性
        final Properties props = new Properties();

        props.setProperty("mail.smtp.auth", "true"); // 表示SMTP发送邮件，必须验证
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", propertiesUtil.getValue("mail.smtp.service"));
        // 设置端口
        props.setProperty("mail.smtp.port", propertiesUtil.getValue("mail.smtp.port"));
        // 设置发送邮箱
        props.setProperty("mail.user", propertiesUtil.getValue("mail.from.address"));
        // 设置口令
        props.setProperty("mail.pwdword", propertiesUtil.getValue("mail.from.smtp.pwd"));

        // 2. 发送邮件
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = props.getProperty("mail.user");
                String pwdword = props.getProperty("mail.pwdword");
                return new PasswordAuthentication(userName, pwdword);
            }
        };

        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);

        // 设置发送人
        String ninkName = MimeUtility.decodeText(propertiesUtil.getValue("mail.from.ninkname"));
        InternetAddress from = new InternetAddress(ninkName + "<" + props.getProperty("mail.user") + ">");
        message.setFrom(from);

        // 设置邮件标题
        message.setSubject(mail.getTitle());
        // html发送邮件
        if (mail.getContentType().equals(MailContentTypeEnum.HTML.getValue())) {
            message.setContent(mail.getContent(), mail.getContentType());
        }

        // 文本
        if (mail.getContentType().equals(MailContentTypeEnum.TEXT.getValue())) {
            message.setText(mail.getContent());
        }

        List<String> targets = mail.getList();
        System.out.println(propertiesUtil.getValue("mail.smtp.service"));
        for (int i = 0; i < targets.size(); i++) {
            try {
                InternetAddress to = new InternetAddress(targets.get(i));
                message.setRecipient(Message.RecipientType.TO,to);
                Transport.send(message);
            } catch (Exception e) {
                continue;
            }
        }
    }
}