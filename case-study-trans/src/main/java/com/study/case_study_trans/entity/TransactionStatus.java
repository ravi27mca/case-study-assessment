package com.study.case_study_trans.entity;

public enum TransactionStatus {

    RECEIVED,
    PROCESSING,
    PROCESSED,
    DUPLICATE,
    FAILED,
    RETRY_PENDING,
    PENDING
}