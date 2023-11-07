package kr.hs.dgsw.GymPTI.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.*;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.response.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponseDto retrieveUserInfo(User user);

    void updateNickname(UpdateNicknameRequestDto updateNicknameRequestDto, User user);

    void updateStatusMessage(UpdateStatusMessageRequestDto updateStatusMessageRequestDto, User user);

    String findByUserId(FindUserIdRequestDto findUserIdRequestDto, HttpServletRequest request, User user);

    void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto, User user);

    void updateUserBodyInfo(UpdateUserBodyInfoRequest request, User user);

    void updateProfileImage(MultipartFile profileImage, User user);

}
