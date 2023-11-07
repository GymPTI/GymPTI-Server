package kr.hs.dgsw.GymPTI.domain.routine.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Exercise {

    PUSH_UP("gr0001", "푸시업", "tm0002", List.of("대흉근", "삼두근", "삼각근")),
    DIPS("gr0002", "딥스", "tm0002", List.of("삼두근", "대흉근")),
    PULL_UP("gr0003", "풀업", "tm0003", List.of("광배근", "이두근", "승모근")),
    DUMBBELL_CURL("gr0004", "덤벨컬", "tm0005", List.of("이두근")),
    BENCH_PRESS("gr0005", "벤치 프레스", "tm0002", List.of("대흉근", "삼두근")),
    CHIN_UP("gr0006", "친업", "tm0003", List.of("이두근", "광배근")),
    PLANK("gr0007", "플랭크", "tm0006", List.of("코어")),
    RUSSIAN_TWIST("gr0008", "러시안 트위스트", "tm0006", List.of("외복사근", "복직근")),
    SIT_UP("gr0009", "싯업", "tm0006", List.of("복직근")),
    LEG_RAISE("gr0010", "레그 레이즈", "tm0006", List.of("하복근")),
    SQUAT("gr0011", "스쿼트", "tm0001", List.of("대퇴사둔근", "대둔근", "햄스트링")),
    LUNGE("gr0012", "런지", "tm0001", List.of("대퇴사둔근", "대둔근", "햄스트링", "종아리")),
    DEADLIFT("gr0013", "데드리프트", "tm0001", List.of("대퇴사둔근", "대둔근", "햄스트링", "하부 등근육")),
    CHEST_PRESS_MACHINE("gr0014", "체스트 프레스 머신", "tm0002", List.of("대흉근", "삼두근")),
    LAT_PULLDOWN("gr0015", "랫 풀 다운", "tm0003", List.of("광배근", "상부 등근육")),
    CABLE_SEATED_ROW("gr0016", "케이블 시티드 로우", "tm0003", List.of("중간 등근육", "이두근")),
    DUMBBELL_ROW("gr0017", "덤벨 로우", "tm0003", List.of("중간 등근육", "이두근")),
    SHOULDER_PRESS("gr0018", "숄더 프레스", "tm0005", List.of("삼각근")),
    SIDE_LATERAL_RAISE("gr0019", "사이드 레터럴 레이즈", "tm0004", List.of("측면 삼각근")),
    FRONT_RAISE("gr0020", "프론트 레이즈", "tm0004", List.of("전면 삼각근")),
    BENT_OVER_LATERAL_RAISE("gr0021", "벤트 오버 레터럴 레이즈", "tm0004", List.of("후면 삼각근")),
    DUMBBELL_FLY("gr0022", "덤벨 플라이", "tm0002", List.of("대흉근")),
    DUMBBELL_CONCENTRATION_CURL("gr0023", "덤벨 컨센트레이션 컽", "tm0005", List.of("이두근")),
    CABLE_FLY("gr0024", "케이블 플라이", "tm0002", List.of("대흉근")),
    HAMMER_CURL("gr0025", "해머컬", "tm0005", List.of("이두근")),
    CABLE_TRICEPS_PUSHDOWN("gr0026", "케이블 푸시다운", "tm0005", List.of("삼두근")),
    OVERHEAD_EXTENSION("gr0027", "오버헤드 익스텐션", "tm0005", List.of("삼두근")),
    LYING_TRICEPS_EXTENSION("gr0028", "라잉 트라이셉스 익스텐션", "tm0005", List.of("삼두근")),
    LEG_PRESS("gr0029", "레그 프레스", "tm0001", List.of("대퇴사두근", "대둔근", "햄스트링")),
    OVERHEAD_PRESS("gr0030", "오버헤드 프레스", "tm0004", List.of("삼각근")),
    CRUNCH("gr0031", "크런치", "tm0006", List.of("복직근"));

    private final String gymRoutineCode;

    private final String exerciseName;

    private final String targetMuscleCode;

    private final List<String> targetMuscle;

}
