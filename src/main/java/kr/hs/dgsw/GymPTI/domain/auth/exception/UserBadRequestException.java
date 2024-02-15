package kr.hs.dgsw.GymPTI.domain.auth.exception;

import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import kr.hs.dgsw.GymPTI.global.exception.CustomException;

public class UserBadRequestException extends CustomException {

    public static final CustomException EXCEPTION = new UserBadRequestException(ErrorCode.USER_BAD_REQUEST);

    private UserBadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
