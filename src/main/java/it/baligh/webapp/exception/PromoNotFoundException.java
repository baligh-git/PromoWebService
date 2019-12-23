package it.baligh.webapp.exception;

public class PromoNotFoundException extends Exception {

	private static final long serialVersionUID = 7279936821464872366L;
	private String message;
	
	public PromoNotFoundException() {
		super();
	}
	public PromoNotFoundException(String message) {
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
