package com.pos.soap.endpoint;

import com.pos.soap.model.Sale;
import com.pos.soap.model.Customer;
import com.pos.soap.service.SaleService;
import com.pos.soap.service.CustomerService;
import com.pos.soap.ws.DeleteSaleRequest;
import com.pos.soap.ws.DeleteSaleResponse;
import com.pos.soap.ws.GetAllSalesRequest;
import com.pos.soap.ws.GetAllSalesResponse;
import com.pos.soap.ws.GetSaleByIdRequest;
import com.pos.soap.ws.GetSaleByIdResponse;
import com.pos.soap.ws.SaveSaleRequest;
import com.pos.soap.ws.SaveSaleResponse;
import com.pos.soap.ws.SaleType;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;

@Endpoint
public class SaleEndpoint {

    private static final String NAMESPACE_URI = "http://www.pos.com/soap/sale";

    private final SaleService saleService;
    private final CustomerService customerService;

    public SaleEndpoint(SaleService saleService, CustomerService customerService) {
        this.saleService = saleService;
        this.customerService = customerService;
    }

    // ===== Helpers for LocalDateTime <-> XMLGregorianCalendar =====
    private static XMLGregorianCalendar toXmlDateTime(LocalDateTime ldt) {
        if (ldt == null) return null;
        try {
            GregorianCalendar gc = GregorianCalendar.from(ldt.atZone(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static LocalDateTime toLocalDateTime(XMLGregorianCalendar xdt) {
        if (xdt == null) return null;
        return xdt.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }

    // ===== Mappers: JPA <-> JAXB =====
    private SaleType toWs(Sale e) {
        if (e == null) return null;
        SaleType s = new SaleType();
        s.setSaleId(e.getId());
        s.setCashierId(e.getCashierId());
        s.setCustomerId(e.getCustomer() != null ? e.getCustomer().getId() : null);
        s.setProductId(e.getProductId());
        s.setQuantity(e.getQuantity());
        s.setTotalPrice(e.getTotalPrice() != null ? new java.math.BigDecimal(e.getTotalPrice().toString()) : null);
        s.setSaleDate(toXmlDateTime(e.getSaleDate()));
        return s;
    }

    private Sale toEntity(SaleType s) {
        if (s == null) return null;
        Sale e = new Sale();
        e.setId(s.getSaleId());
        e.setCashierId(s.getCashierId());

        if (s.getCustomerId() != null) {
            Customer customer = customerService.getCustomerById(s.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + s.getCustomerId()));
            e.setCustomer(customer);
        }

        e.setProductId(s.getProductId());
        e.setQuantity(s.getQuantity());
        e.setTotalPrice(s.getTotalPrice() != null ? s.getTotalPrice().doubleValue() : null);
        e.setSaleDate(toLocalDateTime(s.getSaleDate()));
        return e;
    }

    // ===== Operations matching sale.xsd =====

    // Get all
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllSalesRequest")
    @ResponsePayload
    public GetAllSalesResponse getAllSales(@RequestPayload GetAllSalesRequest request) {
        List<Sale> sales = saleService.getAllSales();
        GetAllSalesResponse resp = new GetAllSalesResponse();
        sales.stream().map(this::toWs).forEach(resp.getSales()::add);
        return resp;
    }

    // Get by id
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetSaleByIdRequest")
    @ResponsePayload
    public GetSaleByIdResponse getSaleById(@RequestPayload GetSaleByIdRequest request) {
        GetSaleByIdResponse resp = new GetSaleByIdResponse();
        saleService.getSaleById(request.getSaleId())
                .map(this::toWs)
                .ifPresent(resp::setSale);
        return resp;
    }

    // Create/update
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveSaleRequest")
    @ResponsePayload
    public SaveSaleResponse saveSale(@RequestPayload SaveSaleRequest request) {
        // In this XSD SaveSaleRequest is type SaleType, so JAXB often generates SaveSaleRequest as SaleType
        // If generation wraps the type, adjust to request.getSale() pattern accordingly.
        Sale saved = saleService.saveSale(toEntity((SaleType) request));

        SaveSaleResponse resp = new SaveSaleResponse();
        resp.setSaleId(saved.getId());
        resp.setStatus("SUCCESS");
        resp.setMessage("Sale saved");
        return resp;
    }

    // Delete
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteSaleRequest")
    @ResponsePayload
    public DeleteSaleResponse deleteSale(@RequestPayload DeleteSaleRequest request) {
        saleService.deleteSale(request.getSaleId());
        DeleteSaleResponse resp = new DeleteSaleResponse();
        resp.setStatus("SUCCESS");
        return resp;
    }
}
