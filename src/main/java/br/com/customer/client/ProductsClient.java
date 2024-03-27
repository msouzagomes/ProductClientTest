package br.com.customer.client;

import br.com.customer.config.FeignGenericErrorDecoder;
import br.com.customer.model.Produto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "products", url = "${list.products}", configuration = {FeignGenericErrorDecoder.class})
public interface ProductsClient {

    @GetMapping()
    @Headers("Content-Type: application/json")
    List<Produto> getProdutos();

}
