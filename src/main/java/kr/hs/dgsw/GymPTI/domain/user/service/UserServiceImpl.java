package kr.hs.dgsw.GymPTI.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.hs.dgsw.GymPTI.domain.auth.exception.EmailNotValidatedException;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.*;
import kr.hs.dgsw.GymPTI.domain.email.entity.ValidatedStatus;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.domain.user.entity.repository.UserRepository;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.response.UserResponseDto;
import kr.hs.dgsw.GymPTI.global.libs.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;

    @Override
    public UserResponseDto retrieveUserInfo(User user) {
        return new UserResponseDto(user);
    }

    @Override
    public void updateNickname(UpdateNicknameRequestDto updateNicknameRequestDto, User user) {

        user.updateNickname(updateNicknameRequestDto.getNewNickname());

        userRepository.save(user);

    }

    @Override
    public void updateStatusMessage(UpdateStatusMessageRequestDto updateStatusMessageRequestDto, User user) {

        user.updateStatusMessage(updateStatusMessageRequestDto.getStatusMessage());

        userRepository.save(user);

    }

    @Override
    public String findByUserId(FindUserIdRequestDto findUserIdRequestDto, HttpServletRequest request, User user) {

        HttpSession session = Optional.ofNullable(request.getSession(false))
                .orElseThrow(() -> new RuntimeException("이메일 인증 요청이 만료되었습니다"));

        if (session.getAttribute("VALIDATE_STATUS").equals(ValidatedStatus.NOT_VALIDATED)) {
            throw EmailNotValidatedException.EXCEPTION;
        }

        if (!Objects.equals(findUserIdRequestDto.getEmail(), user.getEmail())) {
            throw new RuntimeException("이메일이 일치하지 않습니다");
        }

        return user.getUserId();
    }

    @Override
    public void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto, User user) {

        if (!user.getPassword().equals(updatePasswordRequestDto.getOldPassword())) {
            throw new RuntimeException("기존 비밀번호가 일치하지 않습니다");
        }

        user.updatePassword(updatePasswordRequestDto.getNewPassword());

        userRepository.save(user);

    }

    @Override
    public void updateUserBodyInfo(UpdateUserBodyInfoRequest request, User user) {

        user.updateUserBodyInfo(request);

        userRepository.save(user);

    }

    @Override
    public void updateProfileImage(MultipartFile profileImage, User user) {

        String url = s3Uploader.uploadImage(profileImage);

        user.updateProfileImage(url);

        userRepository.save(user);

    }

}
