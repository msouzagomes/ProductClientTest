package br.com.customer.client;

import br.com.customer.config.FeignGenericErrorDecoder;
import br.com.customer.model.ClientesCompra;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "customersPurchases", url = "${list.client.shopping}", configuration = {FeignGenericErrorDecoder.class})
public interface CustomersShoppingClient {

    @GetMapping()
    @Headers("Content-Type: application/json")
    List<ClientesCompra> getClientesCompras();

}
