package com.iprody.paymentservice.repository;

import com.iprody.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>,
        JpaRepository<Payment, Long> {


}
