package kr.hs.dgsw.GymPTI.domain.routine.presentation;

import jakarta.validation.Valid;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.req.AiRoutineCreateRequest;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.res.AiRoutineCreateResponse;
import kr.hs.dgsw.GymPTI.global.annotation.CheckAuthorization;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.req.RoutineCreateRequest;
import kr.hs.dgsw.GymPTI.domain.routine.presentation.dto.res.RoutineResponse;
import kr.hs.dgsw.GymPTI.domain.routine.service.RoutineService;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.global.response.DataResponse;
import kr.hs.dgsw.GymPTI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routine")
public class RoutineController {

    private final RoutineService routineService;

    @CheckAuthorization
    @PostMapping("/create")
    public ResponseEntity<Response> createRoutine(
            @Valid @RequestBody RoutineCreateRequest request,
            @RequestAttribute User user
    ) {
        routineService.save(request, user);
        return Response.created("루틴 생성 성공");
    }

    @CheckAuthorization
    @PostMapping("/ai-create")
    public ResponseEntity<DataResponse<AiRoutineCreateResponse>> createAiRoutine(
            @Valid @RequestBody AiRoutineCreateRequest request,
            @RequestAttribute User user
    ) {
        AiRoutineCreateResponse response = routineService.createAiRoutine(request, user);
        return DataResponse.ok("Ai 루틴 생성 성공", response);
    }

    @CheckAuthorization
    @GetMapping("/list")
    public ResponseEntity<DataResponse<List<RoutineResponse>>> getRoutineListByDayOfWeekAndUser(
            @RequestParam DayOfWeek dayOfWeek,
            @RequestAttribute User user
    ) {
        List<RoutineResponse> routineResponseList = routineService.getRoutineListByDayOfWeekAndUser(dayOfWeek, user);
        return DataResponse.ok("루틴 리스트 조회 성공", routineResponseList);
    }

    @CheckAuthorization
    @PutMapping("/isComplete/{id}")
    public ResponseEntity<DataResponse<Boolean>> updateIsCompleted(@PathVariable Long id) {

        boolean isCompleted = routineService.updateIsCompleted(id);

        return DataResponse.ok("루틴 완료 변경 성공", isCompleted);
    }

    @CheckAuthorization
    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteRoutine(
            @RequestParam DayOfWeek dayOfWeek,
            @RequestAttribute User user
    ) {

        routineService.deleteByDayOfWeek(dayOfWeek, user);

        return Response.ok("요일별 루틴 삭제 성공");
    }

    @CheckAuthorization
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRoutine(
            @PathVariable Long id,
            @RequestAttribute User user
    ) {

            routineService.deleteById(id, user);

            return Response.ok("루틴 삭제 성공");
    }

}
