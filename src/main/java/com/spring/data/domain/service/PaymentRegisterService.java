package com.spring.data.domain.service;

import com.spring.data.domain.exception.EntityInUseException;
import com.spring.data.domain.exception.PaymentMethodNotFoundException;
import com.spring.data.domain.model.Payment;
import com.spring.data.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentRegisterService {

    private static final String MSG_PAYMENT_METHOD_IN_USE =
            "Payment method with code %d is in use";

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional
    public void delete(Long paymentId) {
        try {
            paymentRepository.deleteById(paymentId);
            paymentRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentMethodNotFoundException(paymentId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_PAYMENT_METHOD_IN_USE, paymentId)
            );
        }
    }

    public Payment searchOrFail(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentMethodNotFoundException(paymentId));
    }

}
