package br.com.customer.builder;


import br.com.customer.exceptions.GenericException;
import br.com.customer.model.handler.ErrorHandle;
import br.com.customer.model.handler.ExceptionHandleResponse;
import br.com.customer.model.handler.Link;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExceptionHandleResponseBuilder {


    public ExceptionHandleResponse getExceptionHandleResponse(Integer httpCode, String message){
        return ExceptionHandleResponse.builder()
                .apiVersion("1.0.0")
                .transactionId("")
                .error(ErrorHandle.builder()
                        .httpCode(httpCode.toString())
                        .errorCode("API-addresses-"+httpCode)
                        .message(message)
                        .detailedMessage(message)
                        .link(Link.builder()
                                .rel("")
                                .href("")
                                .build())
                        .build())
                .build();
    }

    public ExceptionHandleResponse getExceptionHandleResponse(GenericException exception){

        var httpCode =  ( Objects.nonNull(exception.getHttpStatus())) ? exception.getHttpStatus().toString() : HttpStatus.BAD_REQUEST.toString();
        var erroCode =  ( Objects.nonNull(exception.getHttpStatus())) ? exception.getHttpStatus().value() : HttpStatus.BAD_REQUEST.value();
        var message  =  ( Objects.nonNull(exception.getCustomMessage())) ?  exception.getCustomMessage() : exception.getException().getMessage();

        return ExceptionHandleResponse.builder()
                .apiVersion("1.0.0")
                .transactionId("")
                .error(ErrorHandle.builder()
                        .httpCode( httpCode )
                        .errorCode("API-BFFIDM-"+ erroCode)
                        .message(message)
                        .detailedMessage(exception.getDetailedMessage())
                        .link(Link.builder()
                                .rel("")
                                .href("")
                                .build())
                        .build())
                .build();
    }



}
