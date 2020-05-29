package com.bysj.fileshare.service.impl;

import com.bysj.fileshare.entity.vo.UserInfoVo;
import com.bysj.fileshare.mybatis.mapper.LoginMapper;
import com.bysj.fileshare.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpSession;

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
    public void userLogin(HttpSession session,UserInfoVo userInfoVo) {
        if (StringUtils.isEmpty(userInfoVo.getUserName()) || StringUtils.isEmpty(userInfoVo.getPassword())) {
            throw new RuntimeException("用户名密码不得为空！");
        }
        String password = loginMapper.queryByUserName(userInfoVo.getUserName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(userInfoVo.getPassword(), password)) {
            throw new RuntimeException("用户名密码不匹配，请检查！");
        }
        //如果用户登录成功，设置session

        session.setAttribute("userName",userInfoVo.getUserName());
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




}
