package kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.third;

import kr.hs.dgsw.GymPTI.domain.routine.entity.Exercise;
import kr.hs.dgsw.GymPTI.domain.user.entity.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
public class AiRoutineHttpRequest {

    private Double height;

    private Double weight;

    private Gender gender;

    private List<String> target;

    private Integer age;

    private Exercise want;

}
