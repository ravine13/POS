package com.pos.soap.endpoint;

import com.pos.soap.model.Customer;
import com.pos.soap.service.CustomerService;
import com.pos.soap.ws.CustomerRequest;
import com.pos.soap.ws.CustomerResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CustomerEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/ws";

    private final CustomerService customerService;

    public CustomerEndpoint(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get all customers
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCustomersRequest")
    @ResponsePayload
    public CustomerResponse getAllCustomers(@RequestPayload CustomerRequest request) {
        List<Customer> customers = customerService.getAllCustomers();
        CustomerResponse response = new CustomerResponse();
        response.getCustomers().addAll(customers);
        return response;
    }

    // Get customer by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCustomerByIdRequest")
    @ResponsePayload
    public CustomerResponse getCustomerById(@RequestPayload CustomerRequest request) {
        CustomerResponse response = new CustomerResponse();
        customerService.getCustomerById(request.getId())
                .ifPresent(response::addCustomer);
        return response;
    }

    // Create or update customer
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveCustomerRequest")
    @ResponsePayload
    public CustomerResponse saveCustomer(@RequestPayload CustomerRequest request) {
        Customer customer = new Customer();
        customer.setId(request.getId());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        Customer savedCustomer = customerService.saveCustomer(customer);

        CustomerResponse response = new CustomerResponse();
        response.addCustomer(savedCustomer);
        return response;
    }

    // Delete customer
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteCustomerRequest")
    @ResponsePayload
    public CustomerResponse deleteCustomer(@RequestPayload CustomerRequest request) {
        customerService.deleteCustomer(request.getId());
        return new CustomerResponse(); // empty response
    }
}
