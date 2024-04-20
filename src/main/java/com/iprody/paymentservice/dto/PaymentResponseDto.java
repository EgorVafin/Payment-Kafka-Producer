package com.iprody.paymentservice.dto;

import com.iprody.paymentservice.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponseDto {

    private long id;

    private long inquiryRefId;

    private BigDecimal amount;

    private String currency;

    private long transactionRefId;

    private Status status;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
