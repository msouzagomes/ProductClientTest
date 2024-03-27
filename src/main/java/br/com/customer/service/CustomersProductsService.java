package br.com.customer.service;

import br.com.customer.builder.ObjetBuilder;
import br.com.customer.client.CustomersShoppingClient;
import br.com.customer.client.ProductsClient;
import br.com.customer.dto.compras.response.CombinedPurchase;
import br.com.customer.dto.maiorcompra.response.ConsultBiggestPurchaseResponse;
import br.com.customer.dto.recomendacaocliente.response.ConsultRecommendationTypeResponse;
import br.com.customer.exceptions.GenericException;
import br.com.customer.model.ClientesCompra;
import br.com.customer.model.Compra;
import br.com.customer.model.Produto;
import br.com.customer.model.handler.ErrorHandle;
import br.com.customer.model.handler.ExceptionHandleResponse;
import br.com.customer.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomersProductsService {

    private final CustomersShoppingClient customersShoppingClient;
    private final ProductsClient productsClient;

    public CustomersProductsService(CustomersShoppingClient customersShoppingClient, ProductsClient productsClient) {
        this.customersShoppingClient = customersShoppingClient;
        this.productsClient = productsClient;
    }

    public Object consultShopping() {
        log.info("Consulting shopping information...");
        try {
            Map<String, Object> returnClient = returnClient();
            List<ClientesCompra> clientesCompras = (List<ClientesCompra>) returnClient.get("clientesCompras");
            List<Produto> produtos = (List<Produto>) returnClient.get("produtos");
            List<CombinedPurchase> combinedPurchase = combinedPurchase(clientesCompras, produtos);
            Collections.sort(combinedPurchase, (c1, c2) -> Double.compare(c1.getValorTotalCompra(), c2.getValorTotalCompra()));
            return ResponseEntity.ok(combinedPurchase);
        } catch (Exception e) {
            log.error("Error consulting shopping information: {}", e.getMessage());
            handleGenericException(e);
        }
        return null;
    }

    public Object consultBiggestPurchase(int year) {
        log.info("Consulting the biggest purchase...");

        if (!Util.isValidYear(year)) {
            log.warn("Ano inválido fornecido: {}", year);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ano inválido fornecido");
        }
        try {
            Map<String, Object> returnClient = returnClient();
            List<ClientesCompra> clientesCompras = (List<ClientesCompra>) returnClient.get("clientesCompras");
            List<Produto> produtos = (List<Produto>) returnClient.get("produtos");
            List<CombinedPurchase> combinedPurchase = combinedPurchase(clientesCompras, produtos);
            return ResponseEntity.ok(getCombinedPurchase(year, combinedPurchase));
        } catch (Exception e) {
            log.error("Error consulting the biggest purchase: {}", e.getMessage());
            handleGenericException(e);
        }
        return null;
    }

    public Object consultLoyalCustomers() {
        log.info("Consulting loyal customers...");
        try {
            Map<String, Object> returnClient = returnClient();
            List<ClientesCompra> clientesCompras = (List<ClientesCompra>) returnClient.get("clientesCompras");
            List<Produto> produtos = (List<Produto>) returnClient.get("produtos");
            List<CombinedPurchase> combinedPurchase = combinedPurchase(clientesCompras, produtos);
            return ResponseEntity.ok(methodTop3LoyalClients(combinedPurchase));
        } catch (Exception e) {
            log.error("Error consulting loyal customers: {}", e.getMessage());
            handleGenericException(e);
        }
        return null;
    }

    public Object consultRecommendationType() {
        log.info("Consulting customer recommendation type...");
        try {
            Map<String, Object> returnClient = returnClient();
            List<ClientesCompra> clientesCompras = (List<ClientesCompra>) returnClient.get("clientesCompras");
            List<Produto> produtos = (List<Produto>) returnClient.get("produtos");
            List<CombinedPurchase> combinedPurchase = combinedPurchase(clientesCompras, produtos);
            return ResponseEntity.ok(recommendWineType(combinedPurchase));
        } catch (Exception e) {
            log.error("Error consulting customer recommendation type: {}", e.getMessage());
            handleGenericException(e);
        }
        return null;
    }

    public ConsultRecommendationTypeResponse recommendWineType(List<CombinedPurchase> combinedPurchase) {

        Map<String, Integer> wineTypeFrequency = new HashMap<>();
        for (CombinedPurchase purchase : combinedPurchase) {
            String wineType = purchase.getTipoVinho();
            wineTypeFrequency.put(wineType, wineTypeFrequency.getOrDefault(wineType, 0) + 1);
        }
        String mostFrequentWineType = wineTypeFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        return ObjetBuilder.getConsultRecommendationTypeResponse(mostFrequentWineType);
    }

    public static @NotNull List<CombinedPurchase> combinedPurchase(List<ClientesCompra> clientesCompras, List<Produto> produtos) {

        List<CombinedPurchase> combinedPurchase = new ArrayList<>();

        for (ClientesCompra clienteCompra : clientesCompras) {
            String nomeCliente = clienteCompra.getNome();
            String cpfCliente = clienteCompra.getCpf();
            List<Compra> compras = clienteCompra.getCompras();
            for (Compra compra : compras) {
                String codigoProduto = compra.getCodigo();
                Produto produto = encontrarProduto(produtos, Integer.valueOf(codigoProduto));
                if (produto != null) {
                    String tipoVinho = produto.getTipoVinho();
                    int quantidadeCompra = compra.getQuantidade();
                    double precoUnitario = produto.getPreco();
                    double valorTotalCompra = quantidadeCompra * precoUnitario;
                    Integer anoCompra = produto.getAnoCompra();

                    combinedPurchase.add(new CombinedPurchase(nomeCliente, cpfCliente, codigoProduto, tipoVinho, quantidadeCompra, precoUnitario, valorTotalCompra, anoCompra));
                }
            }
        }

        return combinedPurchase;
    }

    private static ConsultBiggestPurchaseResponse getCombinedPurchase(int yearInquired, List<CombinedPurchase> shopping) {
        ConsultBiggestPurchaseResponse combinedPurchase = null;
        double value = 0;
        for (CombinedPurchase purchase : shopping) {
            if (purchase.getAnoCompra() == yearInquired) {
                if (purchase.getValorTotalCompra() > value) {
                    value = purchase.getValorTotalCompra();
                    combinedPurchase = ObjetBuilder.getConsultBiggestPurchaseResponse(purchase);
                }
            }
        }
        return combinedPurchase;
    }

    private List<Map<String, Object>> methodTop3LoyalClients(List<CombinedPurchase> shopping) {
        Map<String, Double> totalPurchasesPerClient = new HashMap<>();
        Map<String, Integer> totalRecurringPurchasesPerClient = new HashMap<>();
        Map<String, Integer> lastPurchaseYearPerClient = new HashMap<>();

        for (CombinedPurchase client : shopping) {
            String clientKey = client.getNomeCliente() + "-" + client.getCpfCliente();
            totalPurchasesPerClient.put(clientKey, totalPurchasesPerClient.getOrDefault(clientKey, 0.0) + client.getValorTotalCompra());
            totalRecurringPurchasesPerClient.put(clientKey, totalRecurringPurchasesPerClient.getOrDefault(clientKey, 0) + 1);
            Integer lastPurchaseYear = lastPurchaseYearPerClient.get(clientKey);
            if (lastPurchaseYear == null || client.getAnoCompra() > lastPurchaseYear) {
                lastPurchaseYearPerClient.put(clientKey, client.getAnoCompra());
            }
        }

        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(totalPurchasesPerClient.entrySet());
        sortedList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        List<Map<String, Object>> top3Clients = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Double> entry : sortedList) {
            if (count >= 3) break;
            String clientKey = entry.getKey();
            String[] parts = clientKey.split("-");
            String nomeCliente = parts[0];
            String cpfCliente = parts[1];
            double valorTotalCompra = totalPurchasesPerClient.get(clientKey);
            int anoCompra = lastPurchaseYearPerClient.get(clientKey);

            Map<String, Object> clientInfo = new HashMap<>();
            clientInfo.put("nomeCliente", nomeCliente);
            clientInfo.put("cpfCliente", cpfCliente);
            clientInfo.put("valorTotalCompra", valorTotalCompra);
            clientInfo.put("anoCompra", anoCompra);

            top3Clients.add(clientInfo);
            count++;
        }
        return top3Clients;
    }

    private static @Nullable Produto encontrarProduto(List<Produto> produtos, Integer codigoProduto) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(codigoProduto)) {
                return produto;
            }
        }
        return null;
    }

    private @NotNull Map<String, Object> returnClient() {
        log.info("Consulting clients and products...");
        List<ClientesCompra> clientesCompras = null;
        List<Produto> produtos = null;
        try {
            clientesCompras = customersShoppingClient.getClientesCompras();
            produtos = productsClient.getProdutos();
        } catch (Exception e) {
            log.error("Error retrieving clients and products: {}", e.getMessage());
            handleGenericException(e);
        }
        if (clientesCompras == null || produtos == null) {
            throw new GenericException(ExceptionHandleResponse.builder()
                    .apiVersion("1.0.0")
                    .transactionId(UUID.randomUUID().toString())
                    .error(
                            ErrorHandle.builder()
                                    .errorCode("404")
                                    .detailedMessage("Clients and/or products not found")
                                    .httpCode("404")
                                    .message("Not Found")
                                    .build())
                    .build());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("clientesCompras", clientesCompras);
        result.put("produtos", produtos);
        return result;
    }

    private void handleGenericException(Exception e) {
        log.error("InternalError:: CustomersProductsService :: ", e);
        throw new GenericException(ExceptionHandleResponse.builder()
                .apiVersion("1.0.0")
                .transactionId(UUID.randomUUID().toString())
                .error(
                        ErrorHandle.builder()
                                .errorCode("500")
                                .detailedMessage(e.getMessage())
                                .httpCode("500")
                                .message("Internal Server Error")
                                .build())
                .build());
    }
}