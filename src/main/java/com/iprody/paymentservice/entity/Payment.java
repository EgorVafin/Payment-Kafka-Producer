package com.iprody.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "inquiry_ref_id", nullable = false)
    private long inquiryRefId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 5, nullable = false)
    private String currency;

    @Column(name = "transaction_ref_id", nullable = false, unique = true)
    private long transactionRefId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 128)
    private Status status;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedAt;


//    @Column(name = "created_at", nullable = false)
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @NotNull
//    private Date createdAt;
//
//    @Column(name = "updated_at", nullable = false)
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @NotNull
//    private Date updatedAt;
}
