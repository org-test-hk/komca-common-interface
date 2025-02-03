package kr.or.komca.komcacommoninterface.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)  // 추가
public abstract class BaseResponse {
    protected final int statusCode;
    protected final LocalDateTime timestamp;

    public BaseResponse(int statusCode) {
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }

    // ResponseEntity로 변환하는 추상 메서드
    public abstract ResponseEntity<? extends BaseResponse> toResponseEntity();

}