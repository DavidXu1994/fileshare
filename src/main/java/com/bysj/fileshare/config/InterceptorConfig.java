package com.bysj.fileshare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.config
 * @ClassName: InterceptorConfig
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/23 7:51 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 7:51 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
//@Configuration
    public class InterceptorConfig  implements WebMvcConfigurer {

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截的请求，并排除几个不拦截的请求
        /*registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/favicon.ico", "/swagger**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/index.html","/signup.html", "/user/register", "/user/login");*/
    }





    /**
     * 配置静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    /**
     * 拦截某个请求跳转固定位置
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //默认地址（可以是页面或后台请求接口）
        registry.addViewController("/").setViewName("index");
        //设置过滤优先级最高
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
       // WebMvcConfigurer.super.addViewControllers(registry);

    }


}