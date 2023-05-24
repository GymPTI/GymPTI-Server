package kr.hs.dgsw.GymPTI.domain.user.presentation.dto.response;

import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String userId;

    private final String nickname;

    private final String email;

    private final String profileImage;

    private final String statusMessage;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.statusMessage = user.getStatusMessage();
    }

}
