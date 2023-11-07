package kr.hs.dgsw.GymPTI.domain.routine.service;

import kr.hs.dgsw.GymPTI.domain.routine.entity.Exercise;
import kr.hs.dgsw.GymPTI.domain.routine.entity.Routine;
import kr.hs.dgsw.GymPTI.domain.routine.entity.repository.RoutineRepository;
import kr.hs.dgsw.GymPTI.domain.routine.exception.UserNotMatchedException;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.req.AiRoutineCreateRequest;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.req.RoutineCreateRequest;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.res.AiRoutineCreateResponse;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.res.RoutineResponse;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.third.AiRoutineHttpRequest;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.DayOfWeek;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutineService {

    private final WebClient webClient;
    private final RoutineRepository routineRepository;

    @Transactional
    public void save(RoutineCreateRequest request, User user) {

        routineRepository.save(Routine.builder()
                .user(user)
                .exercise(request.getExercise())
                .dayOfWeek(request.getDayOfWeek())
                .reps(request.getReps())
                .sets(request.getSets())
                .restTime(request.getRestTime())
                .build());
    }

    public AiRoutineCreateResponse createAiRoutine(AiRoutineCreateRequest request, User user) {

        AiRoutineCreateResponse response = webClient.post()
                .uri("/test")
                .bodyValue(AiRoutineHttpRequest.builder()
                        .height(user.getHeight())
                        .weight(user.getWeight())
                        .gender(user.getGender())
                        .age(user.getAge())
                        .target(request.getTargetExercise())
                        .want(request.getWantExercise())
                        .build())
                .retrieve()
                .bodyToMono(AiRoutineCreateResponse.class)
                .block();

        return response;
    }

    public List<RoutineResponse> getRoutineListByDayOfWeekAndUser(DayOfWeek dayOfWeek, User user) {

        List<Routine> routine = routineRepository.findAllByDayOfWeekAndUserOrderByCreatedDateDesc(dayOfWeek, user);

        return routine.stream()
                .map(RoutineResponse::new)
                .toList();
    }

    @Transactional
    public boolean updateIsCompleted(Long id) {

        Routine routine = routineRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        routine.updateIsCompleted();

        return routine.isCompleted();
    }

    public void deleteById(Long id, User user) {

        Routine routine = routineRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        if (routine.getUser().getId() != user.getId()) {
            throw UserNotMatchedException.EXCEPTION;
        }

        routineRepository.deleteById(id);
    }

    @Transactional
    public void deleteByDayOfWeek(DayOfWeek dayOfWeek, User user) {
        routineRepository.deleteByDayOfWeekAndUser(dayOfWeek, user);
    }

}
