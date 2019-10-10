package com.isobar.wallet.management.wallet.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountStatementDto {

	private Integer accountCode;
	private Integer userCode;
	private BigDecimal balanceAmount;
	private BigDecimal openingamount;
	private List<TransactionDto> transactions;

}
