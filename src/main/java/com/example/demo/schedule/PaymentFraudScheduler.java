package com.example.demo.schedule;

import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.EventBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.example.demo.constants.FraudStatus.PENDING_REVIEW;

@EnableScheduling
@Component
public class PaymentFraudScheduler {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    EventBridgeService eventBridgeService;

    @Value("${fraud.check.interval}")
    private Duration fraudCheckInterval;

    @Scheduled(fixedRate = 10 * 1000 * 60) // Every 10 minutes
    public void checkForFraudulentPayments() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(fraudCheckInterval.toHours());
        // Find all payments with status PENDING_REVIEW and update timestamp older than the threshold
        paymentRepository.findAllByStatusAndUpdateTimestampLessThan(PENDING_REVIEW.name(), threshold).forEach(payment -> {
            eventBridgeService.publishEvent(payment.getPaymentId().toString());
            payment.setPublished(true);
        });
    }
}
