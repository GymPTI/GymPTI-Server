package kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNicknameRequestDto {

    @Schema(example = "예시")
    @NotBlank(message = "'nickname'은(는) null이거나 공백일 수 없습니다")
    @Size(min = 1, max = 32, message = "닉네임은 한글 1글자 ~ 16글자, 영어 1글자 ~ 32글자 사이여야 합니다")
    private String newNickname;

}
