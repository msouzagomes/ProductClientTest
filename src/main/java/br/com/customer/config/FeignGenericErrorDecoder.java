package br.com.customer.config;

import br.com.customer.exceptions.GenericException;
import br.com.customer.model.handler.ExceptionHandleResponse;
import com.google.gson.Gson;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

public class FeignGenericErrorDecoder implements ErrorDecoder {

    @SneakyThrows(Exception.class)
    @Override
    public Exception decode(String s, Response response) {

        byte[] bodyData = Util.toByteArray(response.body().asInputStream());
        String json = Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data");
        ExceptionHandleResponse exceptionHandleResponse = new Gson().fromJson(json, ExceptionHandleResponse.class);
        return new GenericException(exceptionHandleResponse);
    }
}
