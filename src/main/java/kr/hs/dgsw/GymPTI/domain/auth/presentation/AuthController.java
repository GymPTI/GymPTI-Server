package kr.hs.dgsw.GymPTI.domain.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.hs.dgsw.GymPTI.common.response.DataResponse;
import kr.hs.dgsw.GymPTI.common.response.Response;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.request.LoginRequestDTO;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.request.RegisterRequestDTO;
import kr.hs.dgsw.GymPTI.domain.auth.presentation.dto.response.LoginResponseDTO;
import kr.hs.dgsw.GymPTI.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "인증 API Document")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입 요청")
    @PostMapping("/register")
    public ResponseEntity<Response> register(
            @Valid @RequestBody RegisterRequestDTO registerRequestDTO,
            HttpServletRequest request
    ) {

        authService.register(registerRequestDTO, request);

        return Response.created("회원가입 성공");
    }

    @Operation(summary = "로그인 요청 및 토큰 반환")
    @PostMapping("/login")
    public ResponseEntity<DataResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {

        LoginResponseDTO token = authService.login(loginRequestDTO);

        return DataResponse.ok("로그인 성공", token);
    }

}
