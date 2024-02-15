package kr.hs.dgsw.GymPTI.global.exception;

import kr.hs.dgsw.GymPTI.global.error.ErrorCode;
import kr.hs.dgsw.GymPTI.global.error.ErrorResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity handleCustomException(CustomException e) {
        log.warn(e.getErrorCode() + " : " + e.getErrorCode().getMessage());
        return ErrorResponseEntity.responseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception e) {
        log.error(e.toString());
        e.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(ErrorResponseEntity.builder()
                        .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                        .code(ErrorCode.INTERNAL_SERVER_ERROR.name())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity handleHttpRequestMethodException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(405)
                .body(ErrorResponseEntity.builder()
                        .status(e.getStatusCode().value())
                        .code("METHOD_NOT_ALLOWED")
                        .message(e.getMethod() + " 메소드는 여기서 허용되지 않습니다 " + e.getSupportedHttpMethods())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(400)
                .body(ErrorResponseEntity.builder()
                        .status(e.getStatusCode().value())
                        .code("NOT_VALID_EXCEPTION")
                        .message(e.getBindingResult().getFieldError().getDefaultMessage())
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(400)
                .body(ErrorResponseEntity.builder()
                        .status(400)
                        .code("DATA_INTEGRITY_VIOLATION_EXCEPTION")
                        .message("데이터 무결성 위반 에러입니다 요청에 문제가 있을 수 있습니다")
                        .build());
    }

    @ExceptionHandler(WebClientResponseException.InternalServerError.class)
    protected ResponseEntity handleWebClientInternalServerError(WebClientResponseException.InternalServerError e) {
        return ResponseEntity
                .status(e.getStatusCode().value())
                .body(ErrorResponseEntity.builder()
                        .status(e.getStatusCode().value())
                        .code(e.getStatusCode().toString())
                        .message(e.getMessage())
                        .build());
    }

}
