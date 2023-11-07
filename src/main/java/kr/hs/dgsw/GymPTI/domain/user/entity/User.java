package kr.hs.dgsw.GymPTI.domain.user.entity;

import jakarta.persistence.*;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdateUserBodyInfoRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profileImage;

    private String statusMessage;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double height;

    private Double weight;

    private Integer age;

    @Builder
    public User(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateUserBodyInfo(UpdateUserBodyInfoRequest request) {
        this.gender = request.getGender() == null ? this.gender : request.getGender();
        this.height = request.getHeight() == null ? this.height : request.getHeight();
        this.weight = request.getWeight() == null ? this.weight : request.getWeight();
        this.age = request.getAge() == null ? this.age : request.getAge();
    }
}
