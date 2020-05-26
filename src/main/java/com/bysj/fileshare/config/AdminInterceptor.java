package com.bysj.fileshare.config;

import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.config
 * @ClassName: AdminInterceptor
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/26 1:29 上午
 * @UpdateUser:
 * @UpdateDate: 2020/5/26 1:29 上午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
//@Component
public class AdminInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        System.out.println("执行了TestInterceptor的preHandle方法");
            //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
            Object user=request.getSession().getAttribute("useName");
            if (user == null) {
                // 未登录，给出错误信息，
                request.setAttribute("msg","无权限请先登录");
                // 获取request返回页面到登录页
                try {
                    request.getRequestDispatcher("/index.html").forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            } else {
                // 已登录，放行
                return true;
            }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//         System.out.println("执行了TestInterceptor的postHandle方法");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        System.out.println("执行了TestInterceptor的afterCompletion方法");
    }
}
