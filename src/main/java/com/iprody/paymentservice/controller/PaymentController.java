package com.iprody.paymentservice.controller;

import com.iprody.paymentservice.dto.PaymentRequestDto;
import com.iprody.paymentservice.dto.PaymentResponseDto;
import com.iprody.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payment")
@Validated
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private static final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/add")
    public Mono<ResponseEntity<String>> create(@Valid @RequestBody PaymentRequestDto dto) {

        var payment = paymentService.save(dto);
        return (Mono.just(ResponseEntity.ok(String.valueOf(payment.block().getId()))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PaymentResponseDto>> findById(@Valid @Positive @PathVariable(value = "id") long id) {
        var payment = paymentService.findById(id);
        return payment.
                flatMap(element -> Mono.just(ResponseEntity.ok(modelMapper.map(element, PaymentResponseDto.class)))
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Long>> edit(@Valid @Positive @PathVariable(value = "id") long id,
                                           @Valid @RequestBody PaymentRequestDto dto) {
        var payment = paymentService.edit(id, dto);

        return payment.flatMap(element -> Mono.just(ResponseEntity.ok(element.getId()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> delete(@Valid @Positive @PathVariable(value = "id") long id) {
        paymentService.delete(id);

        return
                (Mono.just(ResponseEntity.ok("User with id=" + id + " was deleted")));
    }


}
