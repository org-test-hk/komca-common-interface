package kr.or.komca.komcacommoninterface.exception;

import kr.or.komca.komcacommoninterface.response_code.ErrorCode;

public interface BaseException {
    ErrorCode getErrorCode();
}
