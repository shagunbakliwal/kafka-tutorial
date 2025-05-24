package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PaymentStatus {
    @Id
    private Long id;
    private Long paymentId;
    private String status;
    private LocalDateTime updateTimestamp;
    private boolean published;
}
