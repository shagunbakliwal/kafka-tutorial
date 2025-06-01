package com.example.demo.client;

import com.example.demo.constants.FraudStatus;
import com.example.demo.model.FraudResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
@Slf4j
public class FraudClient {
    @Autowired
    @Qualifier("my-downstream-service-rest-template")
    RestTemplate restTemplate;

    public FraudResponse getPaymentStatus(Long paymentId) {
        // Example of calling an external service
        //String url = "http://example.com/api/payments/" + paymentId;
        //FraudResponse response = restTemplate.getForObject(url, FraudResponse.class);

        // Process the response as needed
        //log.info("Response from external service: " + response);

        return new FraudResponse(paymentId, FraudStatus.OFFLINE_REVIEW.name(), LocalDateTime.now());
    }
}
