package kr.hs.dgsw.GymPTI.domain.user.presentation.dto.response;

import kr.hs.dgsw.GymPTI.domain.user.entity.Gender;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String userId;

    private final String nickname;

    private final String email;

    private final String profileImage;

    private final String statusMessage;

    private final Gender gender;

    private final double height;

    private final double weight;

    private final int age;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.statusMessage = user.getStatusMessage();
        this.gender = user.getGender();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.age = user.getAge();
    }

}
