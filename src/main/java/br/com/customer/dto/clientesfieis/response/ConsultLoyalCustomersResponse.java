package br.com.customer.dto.clientesfieis.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultLoyalCustomersResponse {

    @JsonProperty("nome")
    private String nomeCliente;

    @JsonProperty("cpf")
    private String cpfCliente;

    @JsonProperty("valorTotalCompra")
    private double valorTotalCompra;

    @JsonIgnore()
    private Integer anoCompra;

}
