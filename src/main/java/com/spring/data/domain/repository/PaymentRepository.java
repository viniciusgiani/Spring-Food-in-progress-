package com.spring.data.domain.repository;

import com.spring.data.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select max(updateDate) from Payment")
    OffsetDateTime getLastUpdateDate();

    @Query("select updateDate from Payment where id = :paymentId")
    OffsetDateTime getUpdateDateById(Long paymentId);

}
