package br.com.customer.exceptions;

import br.com.customer.builder.ExceptionHandleResponseBuilder;
import br.com.customer.model.handler.ExceptionHandleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;

import java.util.Objects;

@ControllerAdvice
public class AppExceptionHandler {

    @Autowired
    private ExceptionHandleResponseBuilder exceptionHandleResponseBuilder;

    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class})
    public ResponseEntity<ExceptionHandleResponse> handleConflictBadRequest(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.Unauthorized.class})
    public ResponseEntity<ExceptionHandleResponse> handleConflictUnauthorized(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.Forbidden.class})
    public ResponseEntity<ExceptionHandleResponse> handleConflictForbidden(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.NotFound.class})
    public ResponseEntity<ExceptionHandleResponse> handleConflictNotFound(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.MethodNotAllowed.class})
    public ResponseEntity<ExceptionHandleResponse> handleConflictMethodNotAllowed(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.NotAcceptable.class})
    public ResponseEntity<ExceptionHandleResponse> handleConflictNotAcceptable(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.TooManyRequests.class})
    protected ResponseEntity<ExceptionHandleResponse> handleConflictTooManyRequests(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.UnsupportedMediaType.class})
    public ResponseEntity<ExceptionHandleResponse> handleUnsupportedMediaTypeRequests(HttpClientErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {ServerErrorException.class })
    public ResponseEntity<ExceptionHandleResponse> handleExceptionHandleResponse(ServerErrorException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<ExceptionHandleResponse> handleConflict(HttpClientErrorException exception, WebRequest request) {
        if(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value() == exception.getStatusCode().value()){
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception.getStatusCode().value(), exception.getMessage()));
        }
        return null;
    }
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ExceptionHandleResponse> handleGeneric(GenericException exception, WebRequest request) {
        if(Objects.nonNull(exception.getExceptionHandleResponse())){
            Integer httpStatus = Integer.valueOf(exception.getExceptionHandleResponse().getError().getHttpCode());
            return ResponseEntity.status(httpStatus).body(exception.getExceptionHandleResponse());
        }else{

            var httpstatus = (Objects.nonNull(exception.getHttpStatus())) ? exception.getHttpStatus() : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(httpstatus).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(exception));
        }

    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionHandleResponse> handleThrowable(Throwable exception, WebRequest request) {

            if (exception instanceof GenericException) {
                GenericException ex = (GenericException) exception;
                return handleGeneric(ex, request);
            }
            if (exception instanceof NullPointerException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
            }

            var mens = exception.getMessage();
            if(Objects.isNull(mens)){
                mens = exception.getCause().getCause().getMessage();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionHandleResponseBuilder.getExceptionHandleResponse(HttpStatus.BAD_REQUEST.value(), mens));
    }
}