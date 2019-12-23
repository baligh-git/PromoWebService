package it.baligh.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(PromoNotFoundException.class)
	public ResponseEntity<ErrorResponse> promoNotFoundHandler(Exception ex){
		ErrorResponse error=new ErrorResponse();
	    error.setCodice(HttpStatus.NOT_FOUND.value());
	    error.setMessagio(ex.getMessage());
	    return new ResponseEntity<ErrorResponse>(error,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorResponse> idNotFoundHandler(Exception ex){
		ErrorResponse error=new ErrorResponse();
	    error.setCodice(HttpStatus.BAD_REQUEST.value());
	    error.setMessagio(ex.getMessage());
	    return new ResponseEntity<ErrorResponse>(error,HttpStatus.BAD_REQUEST);
	}

}
