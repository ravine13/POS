package com.pos.soap.ws;

import com.pos.soap.model.Sale;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "SaleResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaleResponse", propOrder = {
        "sales"
})
public class SaleResponse {

    @XmlElement(name = "sale")
    private List<Sale> sales = new ArrayList<>();

    // Constructors
    public SaleResponse() {}

    public SaleResponse(List<Sale> sales) {
        this.sales = sales;
    }

    // Getter and setter
    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    // Add a single sale
    public void addSale(Sale sale) {
        this.sales.add(sale);
    }
}
