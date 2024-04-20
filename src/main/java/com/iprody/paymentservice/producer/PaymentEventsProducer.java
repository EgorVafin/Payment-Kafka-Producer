package com.iprody.paymentservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.paymentservice.dto.PaymentRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class PaymentEventsProducer {

    @Value("${spring.kafka.template.default-topic}")
    public String topic;

    private final KafkaTemplate<Long, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public PaymentEventsProducer(KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<SendResult<Long, String>> sendPaymentEvent(PaymentRequestDto paymentEvent) throws JsonProcessingException {

        var key = paymentEvent.getId();
        var value = objectMapper.writeValueAsString(paymentEvent);
        var completableFuture = kafkaTemplate.send(topic, key, value);

        return completableFuture
                .whenComplete((sendResult, throwable) -> {
                    if (throwable != null) {
                        handleFailure(key, value, throwable);
                    } else {
                        handleSuccess(key, value, sendResult);
                    }
                });
    }

    private void handleSuccess(Long key, String value, SendResult<Long, String> sendResult) {
        log.info("Message send Successfully for the key : {} and the value : {} , partition is {} ",
                key, value, sendResult.getRecordMetadata().partition());
    }

    private void handleFailure(Long key, String value, Throwable ex) {
        log.error("Error sending the message and the exception is {} ", ex.getMessage(), ex);
    }
}
