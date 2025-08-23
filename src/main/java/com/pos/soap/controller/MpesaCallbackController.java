package com.pos.soap.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mpesa")
public class MpesaCallbackController {

    @PostMapping("/callback")
    public void stkCallback(@RequestBody Map<String, Object> callback) {
        System.out.println("Callback received: " + callback);

        // Safaricom returns { "Body": { "stkCallback": { ... } } }
        Map<String, Object> body = (Map<String, Object>) callback.get("Body");
        if (body == null) {
            System.out.println("Invalid callback: missing Body");
            return;
        }

        Map<String, Object> stkCallback = (Map<String, Object>) body.get("stkCallback");
        if (stkCallback == null) {
            System.out.println("Invalid callback: missing stkCallback");
            return;
        }

        Integer resultCode = (Integer) stkCallback.get("ResultCode");
        String checkoutRequestId = (String) stkCallback.get("CheckoutRequestID");

        if (resultCode != null && resultCode == 0) {
            // Mark transaction as successful in DB
            System.out.println("Payment successful for CheckoutRequestID: " + checkoutRequestId);
        } else {
            System.out.println("Payment failed: " + stkCallback.get("ResultDesc"));
        }
    }
}
