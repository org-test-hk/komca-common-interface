package kr.or.komca.komcacommoninterface.exception;

import kr.or.komca.komcacommoninterface.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public abstract class KomcaFieldException extends RuntimeException implements BaseException {
	private final ErrorCode errorCode;
	private final Object value;
	private final String field;

	// 기본 생성자
//	public KomcaFieldException(ErrorCode errorCode) {
//		super(errorCode.getCode());
//		this.errorCode = errorCode;
//		this.value = null;
//		this.field = null;
//	}

	// 추가 파라미터를 받는 생성자
	public KomcaFieldException(ErrorCode errorCode, String field, Object value) {
		super(errorCode.getCode());
		this.errorCode = errorCode;
		this.value = value;
		this.field = field;
	}
}
