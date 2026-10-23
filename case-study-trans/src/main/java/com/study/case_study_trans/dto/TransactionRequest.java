
package com.study.case_study_trans.dto;

import com.study.case_study_trans.entity.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionRequest(

        @NotBlank(message = "Transaction ID is required")
        String transactionId,

        @NotBlank(message = "Account ID is required")
        String accountId,

        @NotNull(message = "Transaction type is required")
        TransactionType transactionType,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "Currency is required")
        String currency,

        @NotNull(message = "Sequence number is required")
        @Positive(message = "Sequence number must be positive")
        Long sequenceNumber,

        @NotBlank(message = "Request ID is required")
        String requestId,

        @NotNull(message = "Event time is required")
        Instant eventTime
) {
}
