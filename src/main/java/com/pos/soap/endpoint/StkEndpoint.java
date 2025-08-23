package com.pos.soap.endpoint;


import com.pos.soap.service.MpesaService;
import com.pos.soap.ws.InitiateStkRequest;
import com.pos.soap.ws.InitiateStkResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StkEndpoint {

    private static final String NAMESPACE = "http://example.com/pos/stk";

    private final MpesaService mpesaService;

    public StkEndpoint(MpesaService mpesaService) {
        this.mpesaService = mpesaService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "InitiateStkRequest")
    @ResponsePayload
    public InitiateStkResponse initiateStk(@RequestPayload InitiateStkRequest request) {
        return mpesaService.initiateStkPush(request);
    }
}
