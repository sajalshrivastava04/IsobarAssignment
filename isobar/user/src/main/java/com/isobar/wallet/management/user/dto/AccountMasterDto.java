package com.isobar.wallet.management.user.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountMasterDto {
	
	Integer accountCode;
	Integer userCode;
	BigDecimal balanceAmount = BigDecimal.ZERO;
	LocalDateTime createdDate;
	String accountType;

}
