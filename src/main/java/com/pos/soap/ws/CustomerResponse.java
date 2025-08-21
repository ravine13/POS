package com.pos.soap.ws;

import com.pos.soap.model.Customer;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "CustomerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerResponse", propOrder = {
        "customers"
})
public class CustomerResponse {

    @XmlElement(name = "customer")
    private List<Customer> customers = new ArrayList<>();

    // Constructors
    public CustomerResponse() {}

    public CustomerResponse(List<Customer> customers) {
        this.customers = customers;
    }

    // Getter and Setter
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    // Add a single customer
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
}
