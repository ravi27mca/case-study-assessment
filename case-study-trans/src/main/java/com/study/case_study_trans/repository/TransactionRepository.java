
package com.study.case_study_trans.repository;

import com.study.case_study_trans.entity.Transaction;
import com.study.case_study_trans.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Optional<Transaction> findByTransactionId(String transactionId);

	boolean existsByTransactionId(String transactionId);

	List<Transaction> findTop20ByStatusOrderByCreatedAtAsc(TransactionStatus status);

	long countByStatus(TransactionStatus status);

	List<Transaction> findByAccountIdAndStatusOrderBySequenceNumberAsc(String accountId, TransactionStatus status);

}
