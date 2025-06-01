package com.example.demo.repository;

import com.example.demo.entity.PaymentStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentStatusEntity, Long> {
    List<PaymentStatusEntity> findAllByScheduledFalseAndSourceTimestampBetween(LocalDateTime start, LocalDateTime end);

}
