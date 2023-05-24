package kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateStatusMessageRequestDto {

    @Size(min = 1, max = 60, message = "상태메시지는 한글 1글자 ~ 30글자, 영어 1글자 ~ 60글자 사이여야 합니다")
    private String statusMessage;

}
