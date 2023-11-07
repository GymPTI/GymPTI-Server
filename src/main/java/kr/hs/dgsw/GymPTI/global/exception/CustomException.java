package kr.hs.dgsw.GymPTI.global.exception;

import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

}
