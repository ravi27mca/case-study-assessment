
package com.study.case_study_trans.service.impl;

import com.study.case_study_trans.dto.TransactionSummaryResponse;
import com.study.case_study_trans.entity.TransactionStatus;
import com.study.case_study_trans.repository.TransactionRepository;
import com.study.case_study_trans.service.TransactionSummaryService;

import org.springframework.stereotype.Service;

@Service
public class TransactionSummaryServiceImpl implements TransactionSummaryService {

	private final TransactionRepository transactionRepository;

	public TransactionSummaryServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public TransactionSummaryResponse getSummary() {

		long received = transactionRepository.countByStatus(TransactionStatus.RECEIVED);

		long processing = transactionRepository.countByStatus(TransactionStatus.PROCESSING);

		long processed = transactionRepository.countByStatus(TransactionStatus.PROCESSED);

		long duplicate = transactionRepository.countByStatus(TransactionStatus.DUPLICATE);

		long failed = transactionRepository.countByStatus(TransactionStatus.FAILED);

		long retryPending = transactionRepository.countByStatus(TransactionStatus.RETRY_PENDING);

		long total = transactionRepository.count();

		return new TransactionSummaryResponse(total, received, processing, processed, duplicate, failed, retryPending);
	}
}
