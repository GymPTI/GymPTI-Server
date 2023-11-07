package kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.req;

import kr.hs.dgsw.GymPTI.domain.routine.entity.Exercise;
import lombok.Getter;

import java.util.List;

@Getter
public class AiRoutineCreateRequest {

    private List<String> targetExercise;

    private Exercise wantExercise;

}
