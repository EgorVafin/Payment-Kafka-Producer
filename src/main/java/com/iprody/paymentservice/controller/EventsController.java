package com.iprody.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iprody.paymentservice.dto.PaymentRequestDto;
import com.iprody.paymentservice.entity.Payment;
import com.iprody.paymentservice.producer.PaymentEventsProducer;
import com.iprody.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class EventsController {

    private final PaymentEventsProducer paymentEventsProducer;

    private final PaymentService paymentService;

    public EventsController(PaymentEventsProducer paymentEventsProducer, PaymentService paymentService) {
        this.paymentEventsProducer = paymentEventsProducer;
        this.paymentService = paymentService;
    }

    @PostMapping("/api/payment/event")
    public ResponseEntity<PaymentRequestDto> postPaymentEvent(
            @RequestBody PaymentRequestDto paymentEvent
    ) throws JsonProcessingException {
        Mono<Payment> payment = paymentService.save(paymentEvent);

        paymentEvent.setId(payment.block().getId());

        log.info("paymentEvent : {} ", paymentEvent);

        paymentEventsProducer.sendPaymentEvent(paymentEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentEvent);
    }
}
