package br.com.customer.builder;

import br.com.customer.dto.compras.response.CombinedPurchase;
import br.com.customer.dto.maiorcompra.response.ConsultBiggestPurchaseResponse;
import br.com.customer.dto.recomendacaocliente.response.ConsultRecommendationTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class ObjetBuilder {

     public static ConsultBiggestPurchaseResponse getConsultBiggestPurchaseResponse(CombinedPurchase purchase){
         return ConsultBiggestPurchaseResponse.builder()
                 .nomeCliente(purchase.getNomeCliente())
                 .cpfCliente(purchase.getCpfCliente())
                 .codigoProduto(purchase.getCodigoProduto())
                 .tipoVinho(purchase.getTipoVinho())
                 .quantidadeCompra(purchase.getQuantidadeCompra())
                 .precoUnitario(purchase.getPrecoUnitario())
                 .valorTotalCompra(purchase.getValorTotalCompra())
                 .anoCompra(purchase.getAnoCompra())
                 .build();
     }

    public static ConsultRecommendationTypeResponse getConsultRecommendationTypeResponse(String frequentWine){
        return ConsultRecommendationTypeResponse.builder()
                .tiposVinho(frequentWine)
                .build();
    }
}
