package kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.res;

import kr.hs.dgsw.GymPTI.domain.routine.entity.Routine;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RoutineResponse {

    private Long id;

    private String exerciseName;

    private List<String> targetMuscle;

    private int reps;

    private int sets;

    private int restTime;

    private boolean isCompleted;

    private LocalDateTime createdDate;

    @Builder
    public RoutineResponse(Routine routine) {
        this.id = routine.getId();
        this.exerciseName = routine.getExercise().getExerciseName();
        this.targetMuscle = routine.getExercise().getTargetMuscle();
        this.reps = routine.getReps();
        this.sets = routine.getSets();
        this.restTime = routine.getRestTime();
        this.isCompleted = routine.isCompleted();
        this.createdDate = routine.getCreatedDate();
    }

}
