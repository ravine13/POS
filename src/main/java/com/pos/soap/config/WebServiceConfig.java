package com.pos.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    // 1️⃣ Register MessageDispatcherServlet for SOAP
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*"); // SOAP endpoints at /ws/*
    }

    // 2️⃣ WSDL for Categories
    @Bean(name = "categories")
    public DefaultWsdl11Definition categoriesWsdl(XsdSchema categoriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CategoriesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://pos.com/soap/ws");
        wsdl11Definition.setSchema(categoriesSchema);
        return wsdl11Definition;
    }

    // 3️⃣ XSD Schema for Categories
    @Bean
    public XsdSchema categoriesSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("categories.xsd"));
    }


    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdl(XsdSchema productsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://pos.com/soap/ws");
        wsdl11Definition.setSchema(productsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema productsSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("products.xsd"));
    }

    // Do the same for Customer, Order, OrderItem, Sale, User
}
