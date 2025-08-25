package com.pos.soap.service;

import com.pos.soap.ws.InitiateStkRequest;
import com.pos.soap.ws.InitiateStkResponse;
import org.springframework.stereotype.Service;

@Service
public class MpesaService {

    public InitiateStkResponse initiateStkPush(InitiateStkRequest req) {
        // Extract values from the SOAP request
        String phoneNumber = req.getPhoneNumber();
        String amount = req.getAmount();
        String accountReference = req.getAccountReference();
        String transactionDesc = req.getTransactionDesc();


        InitiateStkResponse response = new InitiateStkResponse();
        response.setResponseCode("0"); // success code
        response.setResponseMessage(
                "STK Push initiated successfully for phone: " + phoneNumber +
                        ", amount: " + amount +
                        ", account: " + accountReference +
                        ", desc: " + transactionDesc
        );

        return response;
    }
}
