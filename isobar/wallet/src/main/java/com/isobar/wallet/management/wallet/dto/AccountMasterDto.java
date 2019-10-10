package com.isobar.wallet.management.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class AccountMasterDto {
	
	private Integer accountCode;
	@NotNull
	private Integer userCode;
	private  BigDecimal balanceAmount = BigDecimal.ZERO;
	private  LocalDateTime createdDate;
	@Pattern(regexp = "wallet type", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Expected value is wallet type")
	private  String accountType;

}
