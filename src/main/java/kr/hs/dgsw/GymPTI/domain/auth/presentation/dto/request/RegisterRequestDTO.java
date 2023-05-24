package kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterRequestDTO {

    @Schema(example = "example")
    @Pattern(regexp = "^[a-zA-Z0-9]*", message = "아이디 정규표현식이 맞지 않습니다")
    @NotBlank(message = "'userId'은(는) null이거나 공백일 수 없습니다")
    @Size(min = 4, max = 20, message = "아이디는 4글자 ~ 20글자 사이여야 합니다")
    private String userId;

    @Schema(example = "예시")
    @NotBlank(message = "'nickname'은(는) null이거나 공백일 수 없습니다")
    @Size(min = 1, max = 32, message = "닉네임은 한글 1글자 ~ 16글자, 영어 1글자 ~ 32글자 사이여야 합니다")
    private String nickname;

    @Schema(example = "example@dgsw.hs.kr")
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$", message = "이메일 정규표현식이 맞지 않습니다")
    @NotBlank(message = "'email'은(는) null이거나 공백일 수 없습니다")
    private String email;

    @Schema(example = "string, SHA-512")
    @NotBlank(message = "'password'은(는) null이거나 공백일 수 없습니다")
    @Size(min = 128, max = 128, message = "패스워드가 SHA-512으로 암호화 되어있지 않습니다")
    private String password;

}
