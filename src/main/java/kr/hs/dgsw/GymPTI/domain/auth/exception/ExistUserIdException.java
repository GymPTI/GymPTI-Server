package kr.hs.dgsw.GymPTI.domain.auth.exception;

import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import kr.hs.dgsw.GymPTI.global.exception.CustomException;

public class ExistUserIdException extends CustomException {

    public static final CustomException EXCEPTION = new ExistUserIdException(ErrorCode.EXIST_USER_ID);

    public ExistUserIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
