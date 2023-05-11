package kr.hs.dgsw.GymPTI.domain.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.hs.dgsw.GymPTI.domain.auth.exception.ExistEmailException;
import kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request.SendEmailRequestDto;
import kr.hs.dgsw.GymPTI.domain.email.presentation.dto.request.ValidateEmailRequestDto;
import kr.hs.dgsw.GymPTI.domain.email.entity.ValidatedStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String SEND_FROM_ADDRESS;

    @Override
    public void sendMail(SendEmailRequestDto mailRequestDto, HttpServletRequest request) {

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

            messageHelper.setFrom(SEND_FROM_ADDRESS);
            messageHelper.setTo(mailRequestDto.getEmail());
            messageHelper.setSubject("GymPTI 이메일 인증을 진행해 주세요.");

            String verificationCode = issuedEmailVerificationCode();
            String emailContent = String.format("""
                    <body style="background-color: #f4f4f4">
                        <div style="margin: 0 auto; margin-top: 200px; width: 700px; height: 550px; background-color: #fff;">
                            <h2 style="padding: 30px">GymPTI</h2>
                            <h1 style="text-align: center">이메일 인증번호 확인</h1>
                            <h4 style="font-weight: lighter; text-align: center; margin-top: 50px">GymPTI 로그인을 위한 인증번호입니다.</h4>
                            <h4 style="font-weight: lighter; text-align: center">아래 인증번호를 진행 중인 화면에 입력해서 이메일 인증을 완료해 주세요.</h4>
                            <h4 style="font-weight: lighter; text-align: center; font-weight: bold; margin-top: 60px">인증번호</h4>
                            <div style="width: 300px; height: 80px; background-color: #ebdbff; margin: 0 auto; margin-top: 30px; border-radius: 10px;">
                                <span style="line-height: 80px; color: #7000ff; font-size: 40px; margin-left: 75px; font-weight: bold; letter-spacing: 15px;">%s</span>
                            </div>
                        </div>
                    </body>
                    """, verificationCode);

            messageHelper.setText(emailContent, true);

            javaMailSender.send(message);

            createSession(request, mailRequestDto, verificationCode);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void validateEmailVerificationCode(ValidateEmailRequestDto validateEmailRequestDto, HttpServletRequest request) {

        HttpSession session = Optional.ofNullable(request.getSession(false))
                .orElseThrow(() -> new RuntimeException("이메일 인증 요청이 만료되었습니다"));

        if(!session.getAttribute("EMAIL").equals(validateEmailRequestDto.getEmail())) {
            throw ExistEmailException.EXCEPTION;
        }

        if(!session.getAttribute("VERIFICATION_CODE").equals(validateEmailRequestDto.getEmailVerificationCode())) {
            throw new RuntimeException("이메일 인증 코드 틀림");
        }

        session.setAttribute("VALIDATE_STATUS", ValidatedStatus.VALIDATED);

        session.setMaxInactiveInterval(600);

    }

    public String issuedEmailVerificationCode() {

        Random random = new Random();

        String emailVerificationCode = String.valueOf(random.nextInt(1000, 9999));

        return emailVerificationCode;
    }

    public void createSession(HttpServletRequest request, SendEmailRequestDto mailRequestDto, String verificationCode) {

        HttpSession session = request.getSession();

        session.setAttribute("EMAIL", mailRequestDto.getEmail());
        session.setAttribute("VERIFICATION_CODE", verificationCode);
        session.setAttribute("VALIDATE_STATUS", ValidatedStatus.NOT_VALIDATED);
        session.setMaxInactiveInterval(300);

    }

}