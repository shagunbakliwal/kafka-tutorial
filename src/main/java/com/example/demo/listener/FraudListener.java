/*
package com.example.demo.listener;

import com.example.demo.entity.PaymentStatusEntity;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.EventBridgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
*/
/*
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
*//*

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class FraudListener {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    EventBridgeService eventBridgeService;

    // This method will be invoked when a message is received from the Kafka topic
    // The topic name and group ID can be configured in application properties
    // or directly in the annotation.
    // For example, @KafkaListener(topics = "topicName", groupId = "foo")
    @KafkaListener(topics = "topicName", groupId = "foo")
    public void listen(@Payload Long paymentId, @Payload String status, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition, Acknowledgment acknowledgment) {
        log.info("Received Message: " + paymentId + status + " from partition: " + partition);
        Optional<PaymentStatusEntity> byId = paymentRepository.findById(paymentId);
        if (byId.isPresent()) {
            PaymentStatusEntity paymentStatusEntity = byId.get();
            paymentStatusEntity.setPaymentStatus(status);
            paymentRepository.save(paymentStatusEntity);
            eventBridgeService.cancelSchedule(paymentStatusEntity.getPaymentId());
            log.info("Updated Payment Status: " + paymentStatusEntity);
        } else {
            log.error("Payment ID not found: " + paymentId);
        }

        acknowledgment.acknowledge();
    }
}
*/
