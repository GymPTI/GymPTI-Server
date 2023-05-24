package kr.hs.dgsw.GymPTI.domain.email.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request.SendEmailRequestDto;
import kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request.ValidateEmailRequestDto;

public interface EmailService {

    void sendMail(SendEmailRequestDto mailRequestDto, HttpServletRequest request);

    void validateEmailVerificationCode(ValidateEmailRequestDto validateEmailRequestDto, HttpServletRequest request);

}
