package br.com.customer.dto.maiorcompra.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ConsultBiggestPurchaseResponse {

    @JsonProperty("nome")
    private String nomeCliente;

    @JsonProperty("cpf")
    private String cpfCliente;

    @JsonProperty("codigoProduto")
    private String codigoProduto;

    @JsonProperty("tipoVinho")
    private String tipoVinho;

    @JsonProperty("quantidadeCompra")
    private int quantidadeCompra;

    @JsonProperty("precoUnitario")
    private double precoUnitario;

    @JsonProperty("valorTotalCompra")
    private double valorTotalCompra;

    @JsonProperty("anoCompra")
    private Integer anoCompra;
}
