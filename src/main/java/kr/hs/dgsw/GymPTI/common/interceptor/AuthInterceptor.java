package kr.hs.dgsw.GymPTI.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hs.dgsw.GymPTI.common.annotation.CheckAuthorization;
import kr.hs.dgsw.GymPTI.common.error.ErrorCode;
import kr.hs.dgsw.GymPTI.common.exception.CustomException;
import kr.hs.dgsw.GymPTI.common.extractor.AuthExtractor;
import kr.hs.dgsw.GymPTI.common.jwt.JwtUtil;
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

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        CheckAuthorization checkAuthorization = handlerMethod.getMethodAnnotation(CheckAuthorization.class);

        if (checkAuthorization == null) {
            return true;
        }

        String token = authExtractor.extract(request, "Bearer");
        if (token == null || token.length() == 0) {
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
