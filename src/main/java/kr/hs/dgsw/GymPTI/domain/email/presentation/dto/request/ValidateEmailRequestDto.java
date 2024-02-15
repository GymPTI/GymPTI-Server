package kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ValidateEmailRequestDto {

    @Schema(example = "example@dgsw.hs.kr")
    private String email;

    @Schema(example = "9999")
    private String emailVerificationCode;

}
