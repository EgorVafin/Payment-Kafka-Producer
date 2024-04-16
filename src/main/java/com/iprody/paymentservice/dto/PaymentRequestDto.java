package com.iprody.paymentservice.dto;

import com.iprody.paymentservice.entity.Status;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestDto {

    private long id;

    @NotNull
    @Positive
    private long inquiryRefId;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=5, fraction=2)
    @NotNull
    private BigDecimal amount;

    @Size(min=5, max = 5)
    @NotBlank
    private String currency;

    @Positive
    @NotNull
    //Unique
    private long transactionRefId;

    @Size(max = 128)
    private Status status;



}
