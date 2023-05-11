package kr.hs.dgsw.GymPTI.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.hs.dgsw.GymPTI.domain.auth.exception.EmailNotValidatedException;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.FindUserIdRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdateNicknameRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdatePasswordRequestDto;
import kr.hs.dgsw.GymPTI.domain.email.entity.ValidatedStatus;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.domain.user.entity.repository.UserRepository;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

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
    public void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto, HttpServletRequest request, User user) {

        HttpSession session = Optional.ofNullable(request.getSession(false))
                .orElseThrow(() -> new RuntimeException("이메일 인증 요청이 만료되었습니다"));

        if (session.getAttribute("VALIDATE_STATUS").equals(ValidatedStatus.NOT_VALIDATED)) {
            throw EmailNotValidatedException.EXCEPTION;
        }

        if (!user.getPassword().equals(updatePasswordRequestDto.getOldPassword())) {
            throw new RuntimeException("기존 비밀번호가 일치하지 않습니다");
        }

        user.updatePassword(updatePasswordRequestDto.getNewPassword());

        userRepository.save(user);

    }

    @Override
    public void updateProfileImage(MultipartFile profileImage, User user) {
        try {
            if (profileImage != null) {
                String originalFileName = profileImage.getOriginalFilename();
                File dest = new File(originalFileName);
                profileImage.transferTo(dest);
                log.info(String.valueOf(dest));
                user.updateProfileImage("/static/" + originalFileName);
                userRepository.save(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
