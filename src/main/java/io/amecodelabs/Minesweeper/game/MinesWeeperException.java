package io.amecodelabs.Minesweeper.game;

public class MinesWeeperException extends Exception {
	private static final long serialVersionUID = 8914315088490781640L;
	
	private String errorMotive;
	private int code;
	
	public MinesWeeperException(String message) {
		super(message);
	}
	
	public MinesWeeperException(int code, String message, String errorMotive) {
		super(message);
		this.code = code;
		this.errorMotive = errorMotive;
	}
	
	public MinesWeeperException(int code, String message) {
		super(message);
		this.code = code;
		this.errorMotive = "";
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getErrorMotive() {
		return errorMotive;
	}
	
	public void setErrorMotive(String errorMotive) {
		this.errorMotive = errorMotive;
	}
}
