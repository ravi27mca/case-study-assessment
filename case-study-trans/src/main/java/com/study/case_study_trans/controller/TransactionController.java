
package com.study.case_study_trans.controller;

import com.study.case_study_trans.dto.TransactionRequest;
import com.study.case_study_trans.dto.TransactionResponse;
import com.study.case_study_trans.dto.TransactionSummaryResponse;

import com.study.case_study_trans.service.TransactionService;
import com.study.case_study_trans.service.TransactionSummaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction Management", description = "APIs for submitting and monitoring transactions")
public class TransactionController {

	private final TransactionService transactionService;
	private final TransactionSummaryService transactionSummaryService;

	public TransactionController(TransactionService transactionService,
			TransactionSummaryService transactionSummaryService) {

		this.transactionService = transactionService;
		this.transactionSummaryService = transactionSummaryService;
	}

	@Operation(summary = "Submit a transaction", description = "Accepts a transaction request and saves it for processing")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid transaction request"),
			@ApiResponse(responseCode = "409", description = "Duplicate transaction ID") })
	@PostMapping
	public ResponseEntity<TransactionResponse> submitTransaction(@Valid @RequestBody TransactionRequest request) {

		TransactionResponse response = transactionService.submitTransaction(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Get transaction summary", description = "Returns transaction counts grouped by status")
	@ApiResponse(responseCode = "200", description = "Transaction summary retrieved successfully")
	@GetMapping("/summary")
	public ResponseEntity<TransactionSummaryResponse> getSummary() {

		TransactionSummaryResponse summary = transactionSummaryService.getSummary();

		return ResponseEntity.ok(summary);
	}
}
