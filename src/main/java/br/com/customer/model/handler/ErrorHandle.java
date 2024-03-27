package br.com.customer.model.handler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "httpCode",
        "errorCode",
        "message",
        "detailedMessage",
        "link"
})
@Generated("jsonschema2pojo")
public class ErrorHandle {

    @JsonProperty("httpCode")
    private String httpCode;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("detailedMessage")
    private String detailedMessage;
    @JsonProperty("link")
    private Link link;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
