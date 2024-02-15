package kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class AiRoutineCreateResponse {

    private List<Result> result;

    @Getter
    public static class Result {

        private Integer sets;
        private Integer restTime;
        private Integer reps;
        private String exerciseName;

    }

}
