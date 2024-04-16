package com.iprody.paymentservice.service;

import com.iprody.paymentservice.dto.PaymentRequestDto;
import com.iprody.paymentservice.entity.Payment;
import com.iprody.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final PaymentRepository paymentRepository;

    private static final String NOT_FOUND_MASSAGE = "Could not find payment with id ";

    public Mono<Payment> save(PaymentRequestDto dto) {
        Payment payment = modelMapper.map(dto, Payment.class);

        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(payment.getCreatedAt());
        paymentRepository.save(payment);
        return Mono.just(payment);
    }

    public Mono<Payment> edit(Long id, PaymentRequestDto dto) {

        var paymentOpt = paymentRepository.findById(id);
        if (paymentOpt.isPresent()) {
            paymentOpt.get().setAmount(dto.getAmount());
            paymentOpt.get().setCurrency(dto.getCurrency());
            paymentOpt.get().setStatus(dto.getStatus());
            paymentOpt.get().setUpdatedAt(LocalDateTime.now());
            paymentOpt.get().setInquiryRefId(dto.getInquiryRefId());
            paymentOpt.get().setTransactionRefId(dto.getTransactionRefId());

            return Mono.just(paymentRepository.save(paymentOpt.get()));
        }
        return Mono.error(new NoSuchElementException(NOT_FOUND_MASSAGE + id));
    }

    public Mono<Payment> findById(Long id) {
        var payment = paymentRepository.findById(id);

        return payment.map(Mono::just).orElseGet(() ->
                Mono.error(new NoSuchElementException(NOT_FOUND_MASSAGE + id)));
    }

    public Mono<Void> delete(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MASSAGE + id));

        paymentRepository.delete(payment);
        return Mono.empty();
    }
}
