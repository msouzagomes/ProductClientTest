package br.com.customer.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.annotation.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "codigo",
        "tipo_vinho",
        "preco",
        "safra",
        "ano_compra"
})
public class Produto {

    @JsonProperty("codigo")
    private Integer codigo;
    @JsonProperty("tipo_vinho")
    private String tipoVinho;
    @JsonProperty("preco")
    private Double preco;
    @JsonProperty("safra")
    private String safra;
    @JsonProperty("ano_compra")
    private Integer anoCompra;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
