package com.ll.sb20231114.global.webmvc;

import com.ll.sb20231114.global.interceptor.NeedToAdminInterceptor;
import com.ll.sb20231114.global.interceptor.NeedToLoginInterceptor;
import com.ll.sb20231114.global.interceptor.NeedToLogoutInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {


    private final NeedToAdminInterceptor needToAdminInterceptor;
    private final NeedToLoginInterceptor needToLoginInterceptor;
    private final NeedToLogoutInterceptor needToLogoutInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(needToLogoutInterceptor)
                .addPathPatterns("/member/join")
                .addPathPatterns("/member/login")
                .addPathPatterns("/member/findUsername")
                .addPathPatterns("/member/findPassword");
        
        registry.addInterceptor(needToLoginInterceptor)
                .addPathPatterns("/adm/**")
                .addPathPatterns("/article/write")
                .addPathPatterns("/article/modify")
                .addPathPatterns("/article/delete");

        registry.addInterceptor(needToAdminInterceptor)
                .addPathPatterns("/adm/**");

    }
}
