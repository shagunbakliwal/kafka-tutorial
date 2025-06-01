package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public record FraudResponse(@Getter @Setter Long paymentId, @Getter @Setter String paymentStatus, @Getter @Setter LocalDateTime paymentTs) {
}
