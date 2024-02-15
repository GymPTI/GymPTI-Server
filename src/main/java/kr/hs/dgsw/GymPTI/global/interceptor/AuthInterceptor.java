package kr.hs.dgsw.GymPTI.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hs.dgsw.GymPTI.global.annotation.CheckAuthorization;
import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import kr.hs.dgsw.GymPTI.global.exception.CustomException;
import kr.hs.dgsw.GymPTI.global.extractor.AuthExtractor;
import kr.hs.dgsw.GymPTI.global.jwt.JwtUtil;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final AuthExtractor authExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        CheckAuthorization checkAuthorization = handlerMethod.getMethodAnnotation(CheckAuthorization.class);

        if (checkAuthorization == null) {
            return true;
        }

        String token = authExtractor.extract(request, "Bearer");
        if (token == null || token.isEmpty()) {
            return true;
        }

        User user = jwtUtil.verifyToken(token);
        if (user == null) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        request.setAttribute("user", user);
        return true;
    }

}
