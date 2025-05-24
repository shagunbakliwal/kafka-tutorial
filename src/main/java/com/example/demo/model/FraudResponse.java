package com.example.demo.model;

import lombok.Getter;

import java.time.LocalDateTime;

public record FraudResponse(@Getter Long paymentId, @Getter String status, @Getter LocalDateTime updateTimestamp) {
}
