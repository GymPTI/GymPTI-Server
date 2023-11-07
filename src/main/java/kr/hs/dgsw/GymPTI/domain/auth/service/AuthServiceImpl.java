package kr.hs.dgsw.GymPTI.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.hs.dgsw.GymPTI.global.jwt.JwtUtil;
import kr.hs.dgsw.GymPTI.domain.auth.exception.EmailNotValidatedException;
import kr.hs.dgsw.GymPTI.domain.auth.exception.ExistEmailException;
import kr.hs.dgsw.GymPTI.domain.auth.exception.ExistUserIdException;
import kr.hs.dgsw.GymPTI.domain.auth.exception.UserBadRequestException;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.request.LoginRequestDTO;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.request.RegisterRequestDTO;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.response.LoginResponseDTO;
import kr.hs.dgsw.GymPTI.domain.email.entity.ValidatedStatus;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void register(RegisterRequestDTO registerRequestDTO, HttpServletRequest request) {

        HttpSession session = Optional.ofNullable(request.getSession(false))
                .orElseThrow(() -> new RuntimeException("이메일 인증 요청이 만료되었습니다"));

        if (userRepository.existsByUserId(registerRequestDTO.getUserId())) {
            throw ExistUserIdException.EXCEPTION;
        }

        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw ExistEmailException.EXCEPTION;
        }

        if (session.getAttribute("VALIDATE_STATUS").equals(ValidatedStatus.NOT_VALIDATED)) {
            throw EmailNotValidatedException.EXCEPTION;
        }

        userRepository.save(User.builder()
                .userId(registerRequestDTO.getUserId())
                .email(registerRequestDTO.getEmail())
                .nickname(registerRequestDTO.getNickname())
                .password(registerRequestDTO.getPassword())
                .build());
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByUserId(loginRequestDTO.getUserId())
                .orElseThrow(() -> UserBadRequestException.EXCEPTION);

        if (!(user.getPassword().equals(loginRequestDTO.getPassword()))) {
            throw UserBadRequestException.EXCEPTION;
        }

        return LoginResponseDTO.builder()
                .accessToken(jwtUtil.generateAccessToken(user.getEmail()))
                .refreshToken(jwtUtil.generateRefreshToken(user.getEmail()))
                .build();
    }

}
