package kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class FindUserIdRequestDto {

    @Schema(example = "example@dgsw.hs.kr")
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$", message = "이메일 정규표현식이 맞지 않습니다")
    @NotBlank(message = "'email'은(는) null이거나 공백일 수 없습니다")
    private String email;

}
