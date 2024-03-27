package br.com.customer.resource.v1;

import br.com.customer.service.CustomersProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomersProductsResource {

    @Autowired
    private final CustomersProductsService customersProductsService;

    @GetMapping("/compras")
    public ResponseEntity<HttpStatus> consultShopping(){

        return (ResponseEntity<HttpStatus>) customersProductsService.consultShopping();
    }
    @GetMapping("/maior-compra/{ano}")
    public ResponseEntity<HttpStatus>  consultBiggestPurchase(@PathVariable String ano){

        return (ResponseEntity<HttpStatus>) customersProductsService.consultBiggestPurchase(Integer.parseInt(ano));
    }
    @GetMapping("/clientes-fieis")
    public ResponseEntity<HttpStatus> consultLoyalCustomers(){

        return (ResponseEntity<HttpStatus>) customersProductsService.consultLoyalCustomers();
    }
    @GetMapping("/recomendacao/cliente/tipo")
    public ResponseEntity<HttpStatus> consultRecommendationType(){

        return (ResponseEntity<HttpStatus>) customersProductsService.consultRecommendationType();
    }

}
