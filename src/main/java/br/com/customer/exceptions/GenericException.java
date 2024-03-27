package br.com.customer.exceptions;

import br.com.customer.model.handler.ErrorHandle;
import br.com.customer.model.handler.ExceptionHandleResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {
	

	private static final long serialVersionUID = -7082212867266961646L;
	private String customMessage;
	private ErrorHandle errorHandle;
	private ExceptionHandleResponse exceptionHandleResponse;
	private Exception exception;
	private String detailedMessage;
	private HttpStatus httpStatus;


	public GenericException(String msg) {
		super(msg);
		this.customMessage = msg;

	}

	public GenericException(ExceptionHandleResponse err) {
		this.exceptionHandleResponse = err;

	}

	public GenericException(ErrorHandle err) {
		this.errorHandle = err;

	}

	public GenericException(String msg,  Exception exception) {
		this.customMessage = msg;
		this.exception = exception;

	}
	public GenericException(String msg, HttpStatus status) {
		this.customMessage = msg;
		this.httpStatus = status;
	}
	public GenericException(String msg, HttpStatus status, Exception exception) {
		this.customMessage = msg;
		this.exception = exception;
		this.httpStatus = status;
	}

	public GenericException(String msg, String detailedMessage, HttpStatus status, Exception exception) {
		this.customMessage = msg;
		this.detailedMessage = detailedMessage;
		this.exception = exception;
		this.httpStatus = status;

	}

	public GenericException() {}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}


	public void setException(Exception exception) {
		this.exception = exception;
	}

	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}


}
