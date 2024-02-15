package kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    @Schema(example = "string, SHA-512")
    @NotBlank(message = "'password'은(는) null이거나 공백일 수 없습니다")
    @Size(min = 128, max = 128, message = "패스워드가 SHA-512으로 암호화 되어있지 않습니다")
    private String oldPassword;

    @Schema(example = "string, SHA-512")
    @NotBlank(message = "'password'은(는) null이거나 공백일 수 없습니다")
    @Size(min = 128, max = 128, message = "패스워드가 SHA-512으로 암호화 되어있지 않습니다")
    private String newPassword;

}
