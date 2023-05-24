package kr.hs.dgsw.GymPTI.common.config;

import kr.hs.dgsw.GymPTI.common.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor);
    }

}