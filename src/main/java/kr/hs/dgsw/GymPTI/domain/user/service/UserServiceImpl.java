package kr.hs.dgsw.GymPTI.domain.user.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.hs.dgsw.GymPTI.domain.auth.exception.EmailNotValidatedException;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.FindUserIdRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdateNicknameRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdatePasswordRequestDto;
import kr.hs.dgsw.GymPTI.domain.email.entity.ValidatedStatus;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.domain.user.entity.repository.UserRepository;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdateStatusMessageRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage;

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

            //프로필 이미지가 없을 때, 삭제 작업 스킵
            if (user.getProfileImage() != null) {
                BlobId blobId = BlobId.of(bucketName, user.getProfileImage().substring(51));
                storage.delete(blobId);
            }

            //프로필 이미지 업로드 작업
            String uuid = UUID.randomUUID().toString();
            String extension = profileImage.getContentType();

            BlobId blobId = BlobId.of(bucketName, uuid);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(extension)
                    .build();
            storage.createFrom(blobInfo, profileImage.getInputStream());

            user.updateProfileImage("https://storage.googleapis.com/%s/%s".formatted(bucketName, uuid));
            userRepository.save(user);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
