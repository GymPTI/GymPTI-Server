package kr.hs.dgsw.GymPTI.domain.routine.exception;

import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import kr.hs.dgsw.GymPTI.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserNotMatchedException extends CustomException {

    public static final CustomException EXCEPTION = new UserNotMatchedException();

    private UserNotMatchedException() {
        super(ErrorCode.INVALID_USER);
    }

}
