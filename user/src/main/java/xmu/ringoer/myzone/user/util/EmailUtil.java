package xmu.ringoer.myzone.user.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailUtil {
    private static String from = "";
    private static String user = "";
    private static String password = "";
    private static String host = "";
    /*
     * 读取属性文件的内容，并为上面上个属性赋初始值
     */
    static {
        Properties prop = new Properties();
        InputStream is = EmailUtil.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            prop.load(is);
            from = prop.getProperty("from");
            user = prop.getProperty("username");
            password = prop.getProperty("password");
            host = prop.getProperty("host");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean sendMail(String to,String text,String title) {
        Properties props = new Properties();
        //设置邮件服务器主机名
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.host", host);
        //邮箱发送服务器端口,这里设置为465端口
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        //发送服务器需要身份验证
        props.put("mail.smtp.auth", "true");
        //设置邮件等待延时
        props.put("mail.smtp.timeout", 10000);
        props.put("mail.smtp.connectiontimeout", 10000);
        props.put("mail.smtp.writetimeout", 10000);

        //设置环境信息
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        Multipart multipart;
        BodyPart contentPart;
        Transport transport = null;
        try {
            //设置发件人
            message.setFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(title);
            //设置附件
            multipart = new MimeMultipart();
            contentPart = new MimeBodyPart();
            contentPart.setContent(text, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);
            message.saveChanges();
            //发送消息
            transport = session.getTransport("smtp");
            transport.connect(host, user, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transport.close();
                return false;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }
}
