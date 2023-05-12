package kr.hs.dgsw.GymPTI.domain.email.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.hs.dgsw.GymPTI.common.response.Response;
import kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request.ValidateEmailRequestDto;
import kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request.SendEmailRequestDto;
import kr.hs.dgsw.GymPTI.domain.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "Email", description = "메일 API Document")
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "이메일 인증 요청 전송")
    @PostMapping("/sendMailVerification")
    public ResponseEntity<Response> sendMailVerification(
            @Valid @RequestBody SendEmailRequestDto emailVerificationRequestDto,
            HttpServletRequest request
    ) {
        emailService.sendMail(emailVerificationRequestDto, request);
        return Response.ok("메일 전송 성공");
    }

    @Operation(summary = "이메일 인증 번호 검증")
    @PostMapping("/validateMailVerification")
    public ResponseEntity<Response> validateEmailVerificationCode(
            @Valid @RequestBody ValidateEmailRequestDto validateEmailRequestDto,
            HttpServletRequest request
    ) {
        emailService.validateEmailVerificationCode(validateEmailRequestDto, request);
        return Response.ok("메일 인증 성공");
    }

}
