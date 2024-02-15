package kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.req;

import kr.hs.dgsw.GymPTI.domain.routine.entity.Exercise;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.DayOfWeek;

@Getter
@NoArgsConstructor
public class RoutineCreateRequest {

    private Exercise exercise;
    private DayOfWeek dayOfWeek;
    private int reps;
    private int sets;
    private int restTime;

}
