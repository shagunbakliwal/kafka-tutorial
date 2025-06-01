package com.example.demo.service;

import com.example.demo.repository.PaymentRepository;
import com.example.demo.client.FraudClient;
import com.example.demo.constants.FraudStatus;
import com.example.demo.entity.PaymentStatusEntity;
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

    public FraudResponse checkFraud(Long paymentId) {
        FraudResponse fraudResponse = fraudClient.getPaymentStatus(paymentId);
        if (fraudResponse.getPaymentStatus().equals(FraudStatus.OFFLINE_REVIEW.name())) {
            PaymentStatusEntity paymentStatusEntity = new PaymentStatusEntity();
            paymentStatusEntity.setPaymentId(fraudResponse.paymentId());
            paymentStatusEntity.setPaymentStatus(fraudResponse.paymentStatus());
            paymentStatusEntity.setSourceTimestamp(fraudResponse.getPaymentTs());
            // Save the payment paymentStatus to the repository
            paymentRepository.save(paymentStatusEntity);
        }
        return fraudResponse;
    }


}
