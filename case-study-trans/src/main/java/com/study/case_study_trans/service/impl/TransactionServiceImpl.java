
package com.study.case_study_trans.service.impl;

import com.study.case_study_trans.dto.TransactionRequest;
import com.study.case_study_trans.dto.TransactionResponse;
import com.study.case_study_trans.entity.Transaction;
import com.study.case_study_trans.entity.TransactionStatus;
import com.study.case_study_trans.repository.TransactionRepository;
import com.study.case_study_trans.service.TransactionService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public TransactionResponse submitTransaction(
            TransactionRequest request) {

        // Step 1: Check whether transaction already exists
        if (transactionRepository.existsByTransactionId(
                request.transactionId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Duplicate transaction ID: "
                            + request.transactionId()
            );
        }

        // Step 2: Create transaction entity
        Transaction transaction = new Transaction();

        transaction.setTransactionId(request.transactionId());
        transaction.setAccountId(request.accountId());
        transaction.setTransactionType(request.transactionType());
        transaction.setAmount(request.amount());
        transaction.setCurrency(request.currency().toUpperCase());
        transaction.setSequenceNumber(request.sequenceNumber());
        transaction.setRequestId(request.requestId());
        transaction.setEventTime(request.eventTime());
        transaction.setStatus(TransactionStatus.RECEIVED);

        // Step 3: Save transaction in database
        Transaction savedTransaction =
                transactionRepository.save(transaction);

        // Step 4: Convert entity to response DTO
        return TransactionResponse.from(savedTransaction);
    }
}
