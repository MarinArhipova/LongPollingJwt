//package ru.itis.chat.utils;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.util.List;
//
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(resolver());
//    }
//
//    private HandlerMethodArgumentResolver resolver() {
//        return new AuthenticationPrincipalArgumentResolver();
//    }
//}
