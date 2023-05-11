package kr.hs.dgsw.GymPTI.domain.auth.exception;

import kr.hs.dgsw.GymPTI.common.error.ErrorCode;
import kr.hs.dgsw.GymPTI.common.exception.CustomException;

public class ExistUserIdException extends CustomException {

    public static final CustomException EXCEPTION = new ExistUserIdException(ErrorCode.EXIST_USER_ID);

    public ExistUserIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
