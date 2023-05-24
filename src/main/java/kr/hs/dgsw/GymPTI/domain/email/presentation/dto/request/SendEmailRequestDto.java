package kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SendEmailRequestDto {

    @Schema(example = "example@dgsw.hs.kr")
    private String email;

}
