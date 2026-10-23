
package com.study.case_study_trans.service;

import com.study.case_study_trans.dto.TransactionRequest;
import com.study.case_study_trans.dto.TransactionResponse;

public interface TransactionService {

    TransactionResponse submitTransaction(
            TransactionRequest request);
}
