package br.com.customer.dto.compras.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CombinedPurchase {

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

    @JsonIgnore()
    private Integer anoCompra;
}
