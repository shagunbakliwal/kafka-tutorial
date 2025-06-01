package com.example.demo.schedule;

import com.example.demo.entity.PaymentStatusEntity;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.EventBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.time.*;
import java.util.List;

@EnableScheduling
@Component
public class PaymentFetcherService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    EventBridgeService eventBridgeService;

    @Value("${fraud.check.interval}")
    private Duration fraudCheckInterval;

    @Scheduled(fixedRate = 1 * 1000 * 60) // Every 10 minutes
    public void fetchSchedulePayments() {

        // Find all payments with paymentStatus PENDING_REVIEW and update timestamp older than the threshold
        List<PaymentStatusEntity> paymentStatusList = paymentRepository.findAllByScheduledFalseAndSourceTimestampBetween(LocalDateTime.now().minusHours(fraudCheckInterval.toHours()), LocalDateTime.now());

        for (PaymentStatusEntity payment : paymentStatusList) {
            Instant delayInSec = calcDelay(payment.getSourceTimestamp());
            eventBridgeService.createSchedule(payment.getPaymentId(), null, delayInSec);
            payment.setScheduled(true);
        }
        paymentRepository.saveAll(paymentStatusList);
    }

    public Instant calcDelay(LocalDateTime sourceTimestamp) {
        return Instant.from(sourceTimestamp.atOffset(ZoneOffset.UTC).plusHours(fraudCheckInterval.toHours() * 3));
    }
}
