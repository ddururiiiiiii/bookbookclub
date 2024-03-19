package toyproject.bookbookclub.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toyproject.bookbookclub.web.interceptor.LogInterceptor;
import toyproject.bookbookclub.web.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**", "/js/**");

       registry.addInterceptor(new LoginCheckInterceptor())
               .order(2)
               .addPathPatterns("/**")
               .excludePathPatterns(
                       "/", "/basic/members/join", "/login", "/logout",
                       "/css/**", "/*.ico", "/error", "/error-page/**", "/js/**");
    }
}
