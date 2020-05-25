package com.bysj.fileshare.service.impl;

import com.bysj.fileshare.entity.vo.UserInfoVo;
import com.bysj.fileshare.mybatis.mapper.LoginMapper;
import com.bysj.fileshare.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.service.impl
 * @ClassName: LoginServiceImpl
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/23 11:53 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 11:53 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public void registerUser(UserInfoVo userInfoVo) {
        if (StringUtils.isEmpty(userInfoVo.getUserName()) || StringUtils.isEmpty(userInfoVo.getPassword())
                || StringUtils.isEmpty(userInfoVo.getEmail()) || StringUtils.isEmpty(userInfoVo.getPasswordAgain())) {
            throw new RuntimeException("表单信息不得为空！");
        }
        //判断用户名是否存在，查库
        String userName = loginMapper.queryUserNameExit(userInfoVo.getUserName());
        if (!StringUtils.isEmpty(userName)) {
            throw new RuntimeException("此用户名已存在！");
        }

        //判断密码是否相同
        if (userInfoVo.getPassword().equals(userInfoVo.getPasswordAgain())) {

            //转码
            String pwd = encodePassword(userInfoVo.getPassword());
            userInfoVo.setPassword(pwd);
            userInfoVo.setPasswordAgain(pwd);
            /**
             * 注册进来的用户都是0-0
             */
            userInfoVo.setState(0);
            userInfoVo.setType(0);
            userInfoVo.setCreateTime(System.currentTimeMillis());
            userInfoVo.setIsDeleted(false);
        } else {
            throw new RuntimeException("2次输入密码不一致！");
        }
        loginMapper.registerUser(userInfoVo);
    }

    @Override
    public void userLogin(UserInfoVo userInfoVo) {
        if (StringUtils.isEmpty(userInfoVo.getUserName()) || StringUtils.isEmpty(userInfoVo.getPassword())) {
            throw new RuntimeException("用户名密码不得为空！");
        }
        String password = loginMapper.queryByUserName(userInfoVo.getUserName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(userInfoVo.getPassword(), password)) {
            throw new RuntimeException("用户名密码不匹配，请检查！");
        }
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


    /**
     * 代码发送邮件,进行找回密码验证
     */

    public void sendMessage() {
        // 配置参数
        Properties prop = new Properties();
        // 发件人的邮箱的SMTP 服务器地址（不同的邮箱，服务器地址不同，如139和qq的邮箱服务器地址不同）
        prop.setProperty("mail.host", "smtp.qq.com");
        // 使用的协议（JavaMail规范要求）
        prop.setProperty("mail.transport.protocol", "smtp");
        // 需要请求认证
        prop.setProperty("mail.smtp.auth", "true");

        // 使用JavaMail发送邮件的5个步骤
        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        Transport ts = null;
        try {
            // 2、通过session得到transport对象
            ts = session.getTransport();
            // 3、使用邮箱的用户名和密码连接邮件服务器（不同类型的邮箱不一样，网易邮箱输入的是用户名和密码，这里我用的qq邮箱，输入的是邮箱用户名和smtp授权码，smtp授权码可登陆邮箱，进入设置启动smtp服务后获取）
            // 发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect("smtp.qq.com", "你的邮箱号 如：***@qq.com", "smtp授权码（登录邮箱启动smtp服务后可获取）");
            // 4、创建邮件
            Message message = createSimpleMail(session);
            // 5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭transport对象
                ts.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一封只包含文本的邮件
     *
     * @param session
     * @return
     * @throws MessagingException
     */
    public MimeMessage createSimpleMail(Session session)
            throws MessagingException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明发件人
        message.setFrom(new InternetAddress("**********@qq.com"));
        // 指明收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("**********@qq.com"));
        // 邮件的标题
        message.setSubject("邮件标题");
        // 邮件的文本内容
        message.setContent("邮件内容", "text/html;charset=UTF-8");
        return message;
    }

    /**
     * 创建一封包含文本、图片、文件的邮件
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static MimeMessage createComplexMail(Session session)
            throws MessagingException, UnsupportedEncodingException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明发件人
        message.setFrom(new InternetAddress("**********@qq.com"));
        // 指明收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("**********@qq.com"));
        // 邮件的标题
        message.setSubject("通知");

        // 邮件内容：文件、图片的添加步骤
        // 1. 创建图片“节点”
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh = new DataHandler(new FileDataSource("C:\\Users\\myComputer\\Desktop\\1.jpg"));
        // 将图片数据添加到“节点”
        image.setDataHandler(dh);
        // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
        image.setContentID("image_fairy_tail");

        // 2. 创建文本“节点”
        MimeBodyPart text = new MimeBodyPart();
        //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent("这是一条测试邮件</br>测试使用代码发送图片和文件<br/><img src='cid:image_fairy_tail'/>", "text/html;charset=UTF-8");

        // 3. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        // 关联关系
        mm_text_image.setSubType("related");

        // 4. 将 文本+图片 的混合“节点”封装成一个普通“节点”
        //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        //    上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 5. 创建附件“节点”
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh2 = new DataHandler(new FileDataSource("C:\\Users\\myComputer\\Desktop\\妖精的尾巴目录.doc"));
        // 将附件数据添加到“节点”
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 6. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
        MimeMultipart mMultipart = new MimeMultipart();
        mMultipart.addBodyPart(text_image);
        // 如果有多个附件，可以创建多个多次添加
        mMultipart.addBodyPart(attachment);
        // 混合关系
        mMultipart.setSubType("mixed");
        // 7. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mMultipart);
        // 8. 设置发件时间
        message.setSentDate(new Date());
        // 9. 保存上面的所有设置
        message.saveChanges();
        return message;
    }

}
