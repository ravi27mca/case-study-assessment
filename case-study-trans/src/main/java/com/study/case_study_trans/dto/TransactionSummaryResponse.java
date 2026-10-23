
package com.study.case_study_trans.dto;

public record TransactionSummaryResponse(
        long totalTransactions,
        long received,
        long processing,
        long processed,
        long duplicate,
        long failed,
        long retryPending
) {
}
