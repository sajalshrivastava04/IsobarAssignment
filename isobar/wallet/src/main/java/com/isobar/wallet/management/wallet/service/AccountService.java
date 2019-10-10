package com.isobar.wallet.management.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.isobar.wallet.management.wallet.dto.AccountMasterDto;
import com.isobar.wallet.management.wallet.dto.ResponseDto;

public interface AccountService {

	AccountMasterDto createAccount(AccountMasterDto accountDto);

	AccountMasterDto updateAccount(Integer accountCode, AccountMasterDto accountDto);

	AccountMasterDto getAccount(Integer accountCode);

	Boolean deleteAccount(Integer accountCode);

	ResponseDto rechargeAccount(Integer accountCode, BigDecimal amount);

	ResponseDto consumeAccount(Integer accountCode, BigDecimal amount);

	ResponseDto getAccountStatement(Integer accountCode);

	ResponseDto getAccountStatement(Integer accountCode, LocalDateTime fromDate, LocalDateTime toDate);

}
