package com.example.demo.controller;

import com.example.demo.model.FraudResponse;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.EventBridgeService;
import com.example.demo.service.PaymentFraudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.scheduler.model.DeleteScheduleResponse;

@RestController
@RequestMapping("/payments/fraud")
@Slf4j
public class PaymentsFraudController {
    @Autowired
    PaymentFraudService paymentFraudService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    EventBridgeService eventBridgeService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<FraudResponse> getPaymentStatus(@PathVariable(name = "paymentId") Long paymentId) {
        // Example method to demonstrate usage of MyService
        FraudResponse response = paymentFraudService.checkFraud(paymentId);
        log.info("Fraud check response for paymentId {}: {}", paymentId, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/listen/{paymentId}")
    public void dd(@PathVariable(name = "paymentId") Long paymentId) {
        log.info("Cancelling schedule for paymentId: {}", paymentId);
        DeleteScheduleResponse deleteScheduleResponse = eventBridgeService.cancelSchedule(paymentId);
        log.info("Schedule cancelled successfully: {}", deleteScheduleResponse);

    }
}
