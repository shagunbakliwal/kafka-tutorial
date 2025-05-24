package com.example.demo.repository;

import com.example.demo.entity.PaymentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentStatus, Long> {
    List<PaymentStatus> findAllByStatusAndUpdateTimestampLessThan(String status, LocalDateTime updateTimestamp);

}
