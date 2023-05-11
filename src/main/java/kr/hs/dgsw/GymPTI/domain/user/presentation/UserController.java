package kr.hs.dgsw.GymPTI.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.hs.dgsw.GymPTI.common.annotation.CheckAuthorization;
import kr.hs.dgsw.GymPTI.common.response.DataResponse;
import kr.hs.dgsw.GymPTI.common.response.Response;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.FindUserIdRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdateNicknameRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.request.UpdatePasswordRequestDto;
import kr.hs.dgsw.GymPTI.domain.user.presentation.dto.response.UserResponseDto;
import kr.hs.dgsw.GymPTI.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API Document")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회")
    @CheckAuthorization
    @GetMapping("/my")
    public ResponseEntity<DataResponse<UserResponseDto>> getUserData(@RequestAttribute User user) {
        return DataResponse.ok("내 정보 조회 성공", userService.retrieveUserInfo(user));
    }

    @Operation(summary = "닉네임 변경")
    @CheckAuthorization
    @PutMapping("nickname")
    public ResponseEntity<Response> updateNickname(
            @Valid @RequestBody UpdateNicknameRequestDto updateNicknameRequestDto,
            @RequestAttribute User user
    ) {
        userService.updateNickname(updateNicknameRequestDto, user);
        return Response.ok("닉네임 변경 성공");
    }

    @Operation(summary = "아이디 찾기")
    @CheckAuthorization
    @GetMapping("forgotId")
    public ResponseEntity<DataResponse<String>> findByUserId(
            @Valid @RequestBody FindUserIdRequestDto findUserIdRequestDto,
            HttpServletRequest request,
            @RequestAttribute User user
    ) {
        return DataResponse.ok("아이디 찾기 조회 성공", userService.findByUserId(findUserIdRequestDto, request, user));
    }

    @Operation(summary = "비밀번호 변경")
    @CheckAuthorization
    @PutMapping("/password")
    public ResponseEntity<Response> updatePassword(
            @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto,
            HttpServletRequest request,
            @RequestAttribute User user
    ) {
        userService.updatePassword(updatePasswordRequestDto, request, user);
        return Response.ok("비밀번호 변경 성공");
    }

    @Operation(summary = "프로필 사진 변경")
    @CheckAuthorization
    @PutMapping("profileImage")
    public ResponseEntity<Response> updateProfileImage(
            @RequestPart MultipartFile profileImage,
            @RequestAttribute User user
    ) {
        userService.updateProfileImage(profileImage, user);
        return Response.ok("프로필 사진 변경 성공");
    }

}
