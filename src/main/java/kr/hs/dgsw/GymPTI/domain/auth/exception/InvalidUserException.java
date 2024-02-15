package kr.hs.dgsw.GymPTI.domain.auth.exception;

import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import kr.hs.dgsw.GymPTI.global.exception.CustomException;

public class InvalidUserException extends CustomException {

    public static final CustomException EXCEPTION = new InvalidUserException(ErrorCode.INVALID_USER);

    private InvalidUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
