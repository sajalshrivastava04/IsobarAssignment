package com.isobar.wallet.management.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionDto {
	private Integer transactionCode;
	private Character crDrIndicator;
	private BigDecimal amount;
	private BigDecimal balanceAmount;
	private LocalDateTime transactionDate;
	private Integer accountCode;
}
