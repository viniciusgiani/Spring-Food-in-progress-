package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.PaymentInputDisassembler;
import com.spring.data.api.v1.assembler.PaymentModelAssembler;
import com.spring.data.api.v1.model.PaymentModel;
import com.spring.data.api.v1.model.input.PaymentInput;
import com.spring.data.api.v1.openapi.controller.PaymentControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.Payment;
import com.spring.data.domain.repository.PaymentRepository;
import com.spring.data.domain.service.PaymentRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/v1/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController implements PaymentControllerOpenApi {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentRegisterService registerPayment;

    @Autowired
    private PaymentModelAssembler paymentModelAssembler;

    @Autowired
    private PaymentInputDisassembler paymentInputDisassembler;

    @Override
    @CheckSecurity.Payment.CanQuery
    @GetMapping
    public ResponseEntity<CollectionModel<PaymentModel>> list(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = paymentRepository.getLastUpdateDate();

        if (lastUpdateDate != null) {
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<Payment> allPaymentMethods = paymentRepository.findAll();

        CollectionModel<PaymentModel> paymentModels = paymentModelAssembler.toCollectionModel(allPaymentMethods);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(paymentModels);
    }

    @Override
    @CheckSecurity.Payment.CanQuery
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentModel> search(@PathVariable Long paymentId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime updateDate = paymentRepository.getUpdateDateById(paymentId);

        if (updateDate != null) {
            eTag = String.valueOf(updateDate.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        Payment payment = registerPayment.searchOrFail(paymentId);

        PaymentModel paymentModel = paymentModelAssembler.toModel(payment);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(paymentModel);
    }

    @Override
    @CheckSecurity.Payment.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentModel add(@RequestBody @Valid PaymentInput paymentInput) {
        Payment payment = paymentInputDisassembler.toDomainObject(paymentInput);

        payment = registerPayment.save(payment);

        return paymentModelAssembler.toModel(payment);
    }

    @Override
    @CheckSecurity.Payment.CanEdit
    @PutMapping("/{paymentId}")
    public PaymentModel update(@PathVariable Long paymentId,
                                  @RequestBody @Valid PaymentInput paymentInput) {
        Payment currentPayment = registerPayment.searchOrFail(paymentId);

        paymentInputDisassembler.copyToDomainObject(paymentInput, currentPayment);

        currentPayment = registerPayment.save(currentPayment);

        return paymentModelAssembler.toModel(currentPayment);
    }

    @Override
    @CheckSecurity.Payment.CanEdit
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long paymentId) {
        registerPayment.delete(paymentId);
        return ResponseEntity.noContent().build();
    }
}
