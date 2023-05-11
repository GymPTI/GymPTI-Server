package kr.hs.dgsw.GymPTI.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.request.LoginRequestDTO;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.request.RegisterRequestDTO;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.response.LoginResponseDTO;

public interface AuthService {

    void register(RegisterRequestDTO registerRequestDTO, HttpServletRequest request);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
