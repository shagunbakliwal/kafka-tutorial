package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PaymentStatusEntity {
    @Id
    private Long paymentId;
    private String paymentStatus;
    private LocalDateTime sourceTimestamp;
    private boolean scheduled;
    private boolean cancelled;
}
