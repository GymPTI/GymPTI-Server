package kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request;

import jakarta.annotation.Nullable;
import kr.hs.dgsw.GymPTI.domain.user.entity.Gender;
import lombok.Getter;

@Getter
public class UpdateUserBodyInfoRequest {

    @Nullable
    private Gender gender;

    @Nullable
    private Double height;

    @Nullable
    private Double weight;

    @Nullable
    private Integer age;

}
