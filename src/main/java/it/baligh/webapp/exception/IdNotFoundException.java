package it.baligh.webapp.exception;

public class IdNotFoundException extends Exception {

	private static final long serialVersionUID = 385833555582257116L;
	private String message;
	
	public IdNotFoundException() {
		super();
	}
	public IdNotFoundException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
