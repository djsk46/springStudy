package com.board.icia.userClass;

public class DbException extends RuntimeException {

	public DbException() {
		super("스프링은 RuntimeException(선택)예외만 인정합니다. 필수 예외가 발생한 경우 RuntimeException 예외로 변환한다. ");
	}
	
}
