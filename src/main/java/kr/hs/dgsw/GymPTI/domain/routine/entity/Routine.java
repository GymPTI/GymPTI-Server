package kr.hs.dgsw.GymPTI.domain.routine.entity;

import jakarta.persistence.*;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "routine")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Routine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Exercise exercise;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private int reps;

    @Column(nullable = false)
    private int sets;

    @Column(nullable = false)
    private int restTime;

    @Column(nullable = false)
    private boolean isCompleted;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public Routine(User user, Exercise exercise, DayOfWeek dayOfWeek, int reps, int sets, int restTime) {
        this.user = user;
        this.exercise = exercise;
        this.dayOfWeek = dayOfWeek;
        this.reps = reps;
        this.sets = sets;
        this.restTime = restTime;
        this.isCompleted = false;
    }

    public void updateIsCompleted() {
        this.isCompleted = !isCompleted;
    }

}
