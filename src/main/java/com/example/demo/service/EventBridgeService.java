package com.example.demo.service;

import com.example.demo.constants.AppConstants;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.*;

import java.security.Security;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

@Component
@Slf4j
public class EventBridgeService {
    private String schedulerGroup = "payment-timeout-group";
    private String lambdaArn = "arn:aws:lambda:us-east-1:874133362086:function:payments-lambda";
    //Amazon_EventBridge_Scheduler_LAMBDA_9ff7c2a908


    private SchedulerClient schedulerClient;

    public EventBridgeService() {
        this.schedulerClient = SchedulerClient.builder().region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
        log.info("EventBridgeService initialized with SchedulerClient ={}", schedulerClient);
    }

    // Example method to publish an event
    public CreateScheduleResponse createSchedule(Long paymentId, String payload, Instant triggerTime) {
        log.info("Creating schedule for paymentId: {}, payload: {}, triggerTime: {}", paymentId, payload, triggerTime);
        String scheduleName = AppConstants.SCHEDULE_NAME_PREFIX + paymentId;

        String withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(triggerTime);

        String formattedTriggerTime = triggerTime
                .truncatedTo(ChronoUnit.SECONDS)
                .toString(); // This gives the ISO 8601 format with Z and no milliseconds
        // formattedTriggerTime will be like "2025-06-01T16:36:39Z"

        CreateScheduleRequest request = CreateScheduleRequest.builder()
                .name(scheduleName)
                .scheduleExpression("at(" + withoutZone + ")") // Example rate expression, adjust as needed
                .flexibleTimeWindow(builder -> builder.mode("OFF"))
                .groupName(schedulerGroup)
                .target(Target.builder().arn(lambdaArn)
                        .roleArn("arn:aws:iam::874133362086:role/service-role/Amazon_EventBridge_Scheduler_LAMBDA_9ff7c2a908")
                        .input(payload)
                        .build())
                .build();
        log.info("Request to create schedule: {}", request);
        CreateScheduleResponse createScheduleResponse = schedulerClient.createSchedule(request);
        log.info("Schedule created successfully: {}", createScheduleResponse);
        return createScheduleResponse;
    }

    public DeleteScheduleResponse cancelSchedule(Long paymentId) {
        log.info("Cancelling schedule for paymentId: {}", paymentId);
        String scheduleName = AppConstants.SCHEDULE_NAME_PREFIX + paymentId;

        DeleteScheduleRequest deleteScheduleRequest = DeleteScheduleRequest.builder()
                .name(scheduleName)
                .groupName(schedulerGroup)
                .build();

        log.info("Request to delete schedule: {}", deleteScheduleRequest);
        DeleteScheduleResponse deleteScheduleResponse = schedulerClient.deleteSchedule(deleteScheduleRequest);
        log.info("Schedule cancelled successfully: {}", deleteScheduleResponse);
        return deleteScheduleResponse;
    }
}
