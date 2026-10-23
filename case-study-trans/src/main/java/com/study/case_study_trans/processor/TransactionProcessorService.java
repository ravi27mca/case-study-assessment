
package com.study.case_study_trans.processor;

import com.study.case_study_trans.entity.Transaction;
import com.study.case_study_trans.entity.TransactionStatus;
import com.study.case_study_trans.repository.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionProcessorService {

	private static final Logger log = LoggerFactory.getLogger(TransactionProcessorService.class);

	private final TransactionRepository transactionRepository;
	private final TransactionProcessingWorker processingWorker;

	public TransactionProcessorService(TransactionRepository transactionRepository,
			TransactionProcessingWorker processingWorker) {
		this.transactionRepository = transactionRepository;
		this.processingWorker = processingWorker;
	}

	@Scheduled(fixedDelay = 5000)
	public void processPendingTransactions() {

		for (TransactionStatus status : List.of(TransactionStatus.RECEIVED, TransactionStatus.RETRY_PENDING)) {

			List<Transaction> transactions = transactionRepository.findTop20ByStatusOrderByCreatedAtAsc(status);

			for (Transaction transaction : transactions) {
				try {
					processingWorker.processTransaction(transaction.getId());
				} catch (Exception ex) {
					log.error("Failed to process transaction {}", transaction.getTransactionId(), ex);
				}
			}
		}
	}

}
