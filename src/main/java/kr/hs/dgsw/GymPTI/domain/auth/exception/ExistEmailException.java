package kr.hs.dgsw.GymPTI.domain.auth.exception;

import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import kr.hs.dgsw.GymPTI.global.exception.CustomException;

public class ExistEmailException extends CustomException {

    public static final CustomException EXCEPTION = new ExistEmailException(ErrorCode.EXIST_EMAIL);

    public ExistEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
