package org.ject.support.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{
    private static final String LOG_FORMAT = "\nException Class = {}\nResponse Code = {}\nMessage = {}";

    /**
     * 비즈니스 로직에서 정의한 예외가 발생할 때 처리
     */
    @ExceptionHandler(BusinessException.class)
    protected ErrorCode handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        logException(e, errorCode);
        return errorCode;
    }

    /**
     * 애플리케이션 전역에 문제되는 예외가 발생할 때 처리
     */
    @ExceptionHandler(GlobalException.class)
    protected ErrorCode handleGlobalException(GlobalException e) {
        GlobalErrorCode errorCode = e.getErrorCode();
        logException(e, errorCode);
        return errorCode;
    }

    /**
     * 요청 매개변수의 타입이 잘못된 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ErrorCode handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        GlobalErrorCode errorCode = GlobalErrorCode.UNSUPPORTED_PARAMETER_TYPE;
        logException(e, errorCode);
        return errorCode;
    }

    /**
     * 존재하지 않는 API를 호출할 때 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ErrorCode handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        GlobalErrorCode errorCode = GlobalErrorCode.METHOD_NOT_ALLOWED;
        logException(e, errorCode);
        return errorCode;
    }


    /**
     * 예외 정보를 로깅 (ErrorCode 메시지 사용)
     */
    private void logException(final Exception e, final ErrorCode errorCode) {
        log.error(LOG_FORMAT,
                e.getClass(),
                errorCode.getCode(),
                errorCode.getMessage());
    }
}
