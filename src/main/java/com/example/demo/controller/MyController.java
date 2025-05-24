package com.example.demo.controller;

import com.example.demo.service.PaymentFraudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    PaymentFraudService paymentFraudService;

    @GetMapping("/api/payments/{paymentId}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable(name = "paymentId") String paymentId) {
        // Example method to demonstrate usage of MyService
        String response = paymentFraudService.getPaymentStatus(paymentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
