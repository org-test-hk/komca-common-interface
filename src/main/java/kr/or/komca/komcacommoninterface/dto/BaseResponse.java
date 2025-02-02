package kr.or.komca.komcacommoninterface.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)  // 추가
public abstract class BaseResponse<T> {

    protected final Status status;
    protected final T data;
    protected final List<ErrorDetail> errorData;
    protected final LocalDateTime timestamp = LocalDateTime.now();


    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Status {
        private final int code;          // HTTP 상태 코드
        private final String errorCode;  // 에러인 경우에만 포함
    }

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetail {
        private final String field;        // 에러 발생 필드
        private final String code;         // 에러 상세 코드
        private final Object value;        // 실제 입력값 (옵션)
        private final Map<String, Object> params;  // 부가 정보를 위한 유연한 구조
    }

    // 성공 응답용 생성자
    protected BaseResponse(int statusCode, T data) {
        this.status = Status.builder()
                .code(statusCode)
                .build();
        this.data = data;
        this.errorData = null;
    }

    // 에러 응답용 생성자
    protected BaseResponse(int statusCode, String errorCode, List<ErrorDetail> errorData) {
        this.status = Status.builder()
                .code(statusCode)
                .errorCode(errorCode)
                .build();
        this.data = null;
        this.errorData = errorData;
    }

    public void toResponseJSON(HttpServletResponse response) throws IOException {
        response.setStatus(this.status.getCode());
        response.setContentType("application/json;charset=UTF-8");
        String json = new ObjectMapper()
                .registerModule(new JavaTimeModule())  // LocalDateTime 직렬화를 위해 추가
                .writeValueAsString(this);
        response.getWriter().write(json);
    }
}