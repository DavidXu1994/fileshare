package com.bysj.fileshare.controller;

import com.bysj.fileshare.config.ResponseResult;
import com.bysj.fileshare.entity.vo.UserInfoVo;
import com.bysj.fileshare.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.controller
 * @ClassName: LoginController
 * @Description: java类作用描述
 * @Author: 孙燕
 * @CreateDate: 2020/5/23 11:31 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 11:31 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: SunYan
 */
@Api(tags = "【用户登录注册】")
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @ApiOperation("用户注册")
    @PostMapping(value = "/user/register")
    public ResponseResult registerUser(@RequestBody UserInfoVo userInfoVo) {
        loginService.registerUser(userInfoVo);
        return ResponseResult.success();
    }

    @ApiOperation("用户登录")
    @PostMapping(value = "/user/login")
    public ResponseResult userLogin(HttpSession session, @RequestBody UserInfoVo userInfoVo) {
        loginService.userLogin(session, userInfoVo);
        return ResponseResult.success();
    }

    @ApiOperation("获取用户session")
    @PostMapping(value = "/user/session/query")
    public ResponseResult queryUserLogin(HttpSession session) {
        Object user=session.getAttribute("userName");
        return ResponseResult.success(user);
    }

    @ApiOperation("用户退出登录")
    @PostMapping(value = "/user/session/remove")
    public ResponseResult removeUserLogin(HttpSession session) {
       // session.removeAttribute("userName");
        session.invalidate();
        return ResponseResult.success();
    }
}
