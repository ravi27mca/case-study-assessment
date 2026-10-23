
package com.study.case_study_trans.dto;

import com.study.case_study_trans.entity.Transaction;
import com.study.case_study_trans.entity.TransactionStatus;
import com.study.case_study_trans.entity.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        String transactionId,
        String accountId,
        TransactionType transactionType,
        BigDecimal amount,
        String currency,
        Long sequenceNumber,
        String requestId,
        TransactionStatus status,
        int retryCount,
        Instant eventTime,
        Instant createdAt
) {

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getSequenceNumber(),
                transaction.getRequestId(),
                transaction.getStatus(),
                transaction.getRetryCount(),
                transaction.getEventTime(),
                transaction.getCreatedAt()
        );
    }
}
