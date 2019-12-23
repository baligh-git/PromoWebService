package it.baligh.webapp.exception;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private int codice;
	private String messagio;

}
