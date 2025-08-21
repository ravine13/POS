package com.pos.soap.endpoint;

import com.pos.soap.model.Sale;
import com.pos.soap.service.SaleService;
import com.pos.soap.ws.SaleRequest;
import com.pos.soap.ws.SaleResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class SaleEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/ws";

    private final SaleService saleService;

    public SaleEndpoint(SaleService saleService) {
        this.saleService = saleService;
    }

    // Get all sales
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllSalesRequest")
    @ResponsePayload
    public SaleResponse getAllSales(@RequestPayload SaleRequest request) {
        List<Sale> sales = saleService.getAllSales();
        SaleResponse response = new SaleResponse();
        response.getSales().addAll(sales);
        return response;
    }

    // Get sale by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetSaleByIdRequest")
    @ResponsePayload
    public SaleResponse getSaleById(@RequestPayload SaleRequest request) {
        SaleResponse response = new SaleResponse();
        saleService.getSaleById(request.getId())
                .ifPresent(response::addSale);
        return response;
    }

    // Create or update sale
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveSaleRequest")
    @ResponsePayload
    public SaleResponse saveSale(@RequestPayload SaleRequest request) {
        Sale sale = new Sale();
        sale.setId(request.getId());
        sale.setCashierId(request.getCashierId()); // using ID instead of User object
        sale.setProductId(request.getProductId()); // using ID instead of Product object
        sale.setQuantity(request.getQuantity());
        sale.setTotalPrice(request.getTotalPrice());
        sale.setSaleDate(request.getSaleDate());

        Sale savedSale = saleService.saveSale(sale);

        SaleResponse response = new SaleResponse();
        response.addSale(savedSale);
        return response;
    }

    // Delete sale
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteSaleRequest")
    @ResponsePayload
    public SaleResponse deleteSale(@RequestPayload SaleRequest request) {
        saleService.deleteSale(request.getId());
        return new SaleResponse(); // empty response
    }
}
