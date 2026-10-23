
package com.study.case_study_trans.processor;

import com.study.case_study_trans.entity.Transaction;
import com.study.case_study_trans.entity.TransactionStatus;
import com.study.case_study_trans.repository.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionProcessingWorker {

	private static final Logger log = LoggerFactory.getLogger(TransactionProcessingWorker.class);

	private static final int MAX_RETRIES = 3;

	private final TransactionRepository transactionRepository;

	public TransactionProcessingWorker(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Transactional
	public void processTransaction(Long id) {

		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + id));

		if (transaction.getStatus() != TransactionStatus.RECEIVED
				&& transaction.getStatus() != TransactionStatus.RETRY_PENDING) {
			return;
		}

		// Check for earlier transactions that have not completed.
		List<Transaction> earlierTransactions = transactionRepository.findByAccountIdAndStatusOrderBySequenceNumberAsc(
				transaction.getAccountId(), TransactionStatus.RECEIVED);

		boolean earlierSequenceExists = earlierTransactions.stream()
				.anyMatch(t -> t.getSequenceNumber() < transaction.getSequenceNumber());

		if (earlierSequenceExists) {
			log.info("Transaction {} is waiting for an earlier sequence", transaction.getTransactionId());
			return;
		}

		transaction.setStatus(TransactionStatus.PROCESSING);
		transactionRepository.save(transaction);

		try {
			simulateProcessing(transaction);

			transaction.setStatus(TransactionStatus.PROCESSED);
			transactionRepository.save(transaction);

			log.info("Transaction {} processed successfully", transaction.getTransactionId());

		} catch (RuntimeException ex) {

			int retryCount = transaction.getRetryCount() + 1;
			transaction.setRetryCount(retryCount);

			if (retryCount >= MAX_RETRIES) {
				transaction.setStatus(TransactionStatus.FAILED);
			} else {
				transaction.setStatus(TransactionStatus.RETRY_PENDING);
			}

			transactionRepository.save(transaction);

			log.error("Transaction {} failed. Retry count: {}, status: {}", transaction.getTransactionId(), retryCount,
					transaction.getStatus(), ex);
		}
	}

	private void simulateProcessing(Transaction transaction) {

		// Simulate a temporary failure for testing.
		if (transaction.getTransactionId().startsWith("FAIL-")) {
			throw new RuntimeException("Simulated temporary processing failure");
		}

		// Normal transactions succeed.
	}
}
