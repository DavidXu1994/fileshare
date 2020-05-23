package com.bysj.fileshare.service.impl;

import com.bysj.fileshare.entity.vo.UserInfoVo;
import com.bysj.fileshare.mybatis.mapper.LoginMapper;
import com.bysj.fileshare.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        //判断密码是否相同
        if (userInfoVo.getPassword().equals(userInfoVo.getPasswordAgain())) {
            /**
             * todo 判断用户名是否存在
             */
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
            throw new RuntimeException("2次输入密码不一致");
        }
        loginMapper.registerUser(userInfoVo);
    }

    @Override
    public void userLogin(UserInfoVo userInfoVo) {
        String password= loginMapper.queryByUserName(userInfoVo.getUserName());
        String pwd = encodePassword(userInfoVo.getPassword());
        if(!pwd.equals(password)){
            throw new RuntimeException("密码错误");
        }
    }


    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
