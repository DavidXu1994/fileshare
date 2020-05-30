package com.bysj.fileshare.service;

import com.bysj.fileshare.entity.vo.UserInfoVo;

import javax.servlet.http.HttpSession;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.service
 * @ClassName: LoginService
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/23 11:53 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 11:53 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
public interface LoginService {

    /**
     * 用户注册
     * @param userInfoVo
     */
    void registerUser(UserInfoVo userInfoVo);

    /**
     * 用户登录
     * @param session
     * @param userInfoVo
     */
    void userLogin(HttpSession session,UserInfoVo userInfoVo);

}
