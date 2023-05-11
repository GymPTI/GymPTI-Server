package kr.hs.dgsw.GymPTI.common.exception;

import kr.hs.dgsw.GymPTI.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

}
