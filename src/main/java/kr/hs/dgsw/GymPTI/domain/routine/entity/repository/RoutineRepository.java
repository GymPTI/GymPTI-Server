package kr.hs.dgsw.GymPTI.domain.routine.entity.repository;

import kr.hs.dgsw.GymPTI.domain.routine.entity.Routine;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    List<Routine> findAllByDayOfWeekAndUserOrderByCreatedDateDesc(DayOfWeek dayOfWeek, User user);

    void deleteByDayOfWeekAndUser(DayOfWeek dayOfWeek, User user);
}
