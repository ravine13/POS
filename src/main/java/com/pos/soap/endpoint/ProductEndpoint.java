package com.pos.soap.endpoint;

import com.pos.soap.model.Product;
import com.pos.soap.service.ProductService;
import com.pos.soap.ws.ProductRequest;
import com.pos.soap.ws.ProductResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/ws";

    private final ProductService productService;

    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllProductsRequest")
    @ResponsePayload
    public ProductResponse getAllProducts(@RequestPayload ProductRequest request) {
        List<Product> products = productService.getAllProducts();
        ProductResponse response = new ProductResponse();
        response.getProducts().addAll(products);
        return response;
    }

    // Get product by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductByIdRequest")
    @ResponsePayload
    public ProductResponse getProductById(@RequestPayload ProductRequest request) {
        ProductResponse response = new ProductResponse();
        productService.getProductById(request.getId())
                .ifPresent(response::addProduct);
        return response;
    }

    // Create or update product
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveProductRequest")
    @ResponsePayload
    public ProductResponse saveProduct(@RequestPayload ProductRequest request) {
        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategoryId(request.getCategoryId()); // using categoryId instead of Category object

        Product savedProduct = productService.saveProduct(product);

        ProductResponse response = new ProductResponse();
        response.addProduct(savedProduct);
        return response;
    }

    // Delete product
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteProductRequest")
    @ResponsePayload
    public ProductResponse deleteProduct(@RequestPayload ProductRequest request) {
        productService.deleteProduct(request.getId());
        return new ProductResponse(); // empty response
    }
}
