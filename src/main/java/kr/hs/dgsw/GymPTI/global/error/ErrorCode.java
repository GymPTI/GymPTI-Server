package kr.hs.dgsw.GymPTI.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    TOKEN_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "토큰이 입력되지 않았습니다"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰"),

    EXIST_USER_ID(HttpStatus.BAD_REQUEST, "존재하는 아이디입니다"),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다"),
    USER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호가 틀립니다"),
    EMAIL_NOT_VALIDATED(HttpStatus.FORBIDDEN, "이메일 인증이 완료되지 않았습니다"),
    EMAIL_VALIDATED_EXPIRED(HttpStatus.UNAUTHORIZED, "이메일 인증이 완료되지 않았습니다"),

    INVALID_USER(HttpStatus.FORBIDDEN, "권한이 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
