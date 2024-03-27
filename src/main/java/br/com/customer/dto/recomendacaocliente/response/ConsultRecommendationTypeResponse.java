package br.com.customer.dto.recomendacaocliente.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ConsultRecommendationTypeResponse {

    @JsonProperty("tipos de vinho ")
    private String tiposVinho ;
}
