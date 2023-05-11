package kr.hs.dgsw.GymPTI.domain.auth.exception;

import kr.hs.dgsw.GymPTI.common.error.ErrorCode;
import kr.hs.dgsw.GymPTI.common.exception.CustomException;

public class EmailValidatedExpiredException extends CustomException {

    public static final CustomException EXCEPTION = new EmailNotValidatedException(ErrorCode.EXIST_EMAIL);

    public EmailValidatedExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
