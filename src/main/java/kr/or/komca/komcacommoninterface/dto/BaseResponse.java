package kr.or.komca.komcacommoninterface.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.komca.komcacommoninterface.response_code.BaseResponseCode;
import kr.or.komca.komcacommoninterface.response_code.ErrorCode;
import kr.or.komca.komcacommoninterface.response_code.SuccessCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)  // 추가
public abstract class BaseResponse<T> {
    @JsonProperty("errorCode")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected ErrorCode errorCode;

    @JsonProperty("successCode")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected SuccessCode successCode;

    protected final T data;
    protected final Object errorDetail;
    protected final LocalDateTime timestamp = LocalDateTime.now();


    protected BaseResponse(BaseResponseCode baseResponseCode, T data, Object errorDetail) {
        if (baseResponseCode instanceof ErrorCode) {
            this.errorCode = (ErrorCode) baseResponseCode;
        } else if (baseResponseCode instanceof SuccessCode) {
            this.successCode = (SuccessCode) baseResponseCode;
        }
        this.data = data;
        this.errorDetail = errorDetail;
    }

    public int getStatusCode() {
        if (errorCode != null) {
            return errorCode.getStatusCode();
        } else if (successCode != null) {
            return successCode.getStatusCode();
        }
        return HttpStatus.OK.value();
    }

    public void toResponseJSON(HttpServletResponse response) throws IOException {
        response.setStatus(this.getStatusCode());
        response.setContentType("application/json;charset=UTF-8");
        String json = new ObjectMapper().writeValueAsString(this);
        response.getWriter().write(json);
    }
}