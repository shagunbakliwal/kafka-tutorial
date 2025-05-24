package com.example.demo.service;

import com.example.demo.repository.PaymentRepository;
import com.example.demo.client.FraudClient;
import com.example.demo.constants.FraudStatus;
import com.example.demo.entity.PaymentStatus;
import com.example.demo.model.FraudResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentFraudService {
    @Autowired
    private FraudClient fraudClient;

    @Autowired
    PaymentRepository paymentRepository;

    public String getPaymentStatus(String paymentId) {
        FraudResponse fraudResponse = fraudClient.getPaymentStatus(paymentId);
        if (fraudResponse == null) {
            // Handle the case where the response is null
            log.error("No response received for payment ID: " + paymentId);
            return "No response";
        }
        PaymentStatus paymentStatus = savePaymentStatus(fraudResponse);
        try {
            FraudStatus fraudStatus = FraudStatus.valueOf(paymentStatus.getStatus());
            if (fraudStatus == FraudStatus.PENDING_REVIEW) {
                // Handle fraud case
                log.info("Fraud detected for payment ID: " + paymentId);
            } else {
                // Handle non-fraud case
                log.info("No fraud detected for payment ID: " + paymentId);
            }
        } catch (Exception e) {
            // Handle the case where the status is not recognized
            log.error("Error processing fraud response for payment ID: " + paymentId);
        }
        return paymentId;
    }

    private PaymentStatus savePaymentStatus(FraudResponse fraudResponse) {
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentId(fraudResponse.paymentId());
        paymentStatus.setStatus(fraudResponse.status());
        paymentStatus.setUpdateTimestamp(fraudResponse.getUpdateTimestamp());
        // Save the payment status to the repository
        paymentRepository.save(paymentStatus);
        return paymentStatus;
    }
}
