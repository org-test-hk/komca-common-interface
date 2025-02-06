package kr.or.komca.komcacommoninterface.exception;

import kr.or.komca.komcacommoninterface.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public abstract class KomcaNonFieldException extends RuntimeException implements BaseException {
	private final ErrorCode errorCode;

	// 기본 생성자
	public KomcaNonFieldException(ErrorCode errorCode) {
		super(errorCode.getCode());
		this.errorCode = errorCode;
	}
}
