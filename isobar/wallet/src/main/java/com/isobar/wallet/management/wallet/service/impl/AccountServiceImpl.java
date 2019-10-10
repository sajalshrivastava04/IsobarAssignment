package com.isobar.wallet.management.wallet.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isobar.wallet.management.wallet.dto.AccountMasterDto;
import com.isobar.wallet.management.wallet.dto.AccountStatementDto;
import com.isobar.wallet.management.wallet.dto.ResponseDto;
import com.isobar.wallet.management.wallet.dto.TransactionDto;
import com.isobar.wallet.management.wallet.entity.AccountMaster;
import com.isobar.wallet.management.wallet.entity.Transaction;
import com.isobar.wallet.management.wallet.mapper.AccountMasterMapper;
import com.isobar.wallet.management.wallet.mapper.TransactionMapper;
import com.isobar.wallet.management.wallet.repository.AccountMasterRepository;
import com.isobar.wallet.management.wallet.repository.TransactionRepository;
import com.isobar.wallet.management.wallet.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMasterRepository  accountMasterRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@Autowired
	private AccountMasterMapper accountMasterMapper;
	
	
	@Override
	public AccountMasterDto createAccount(AccountMasterDto accountMasterDto) {
		if(accountMasterDto != null)
		{
		AccountMaster accountMaster =  accountMasterMapper.convertDTOToEntity(accountMasterDto);
		AccountMaster savedAccountMaster =  accountMasterRepository.save(accountMaster);
		return accountMasterMapper.convertEntityToDTO(savedAccountMaster);
		}
		else
		{
			return null;
		}
	}

	
	@Override
	public AccountMasterDto getAccount(Integer accountCode) {
		
		
		Optional<AccountMaster> accountMaster =  accountMasterRepository.findById(accountCode);
		if(accountMaster.isPresent())
		{
			return accountMasterMapper.convertEntityToDTO(accountMaster.get());
		}
		else
		{	
		return null;
		}
	}

	@Override
	public Boolean deleteAccount(Integer accountCode) {
		Optional<AccountMaster> accountMaster =  accountMasterRepository.findById(accountCode);
		if(accountMaster.isPresent())
		{   accountMasterRepository.delete(accountMaster.get());
		    return true; 
		}
		else
		{	
		    return false;
		}
	}


	@Override
	public AccountMasterDto updateAccount(Integer accountCode, AccountMasterDto accountMasterDto) {
		Optional<AccountMaster> accountMaster =  accountMasterRepository.findById(accountCode);
		if(accountMaster.isPresent())
		{   
		    AccountMaster accountMasterToUpdate =  accountMasterMapper.updateDTOToEntity(accountMasterDto, accountMaster.get());
		    AccountMaster accountMasterUpdated =  accountMasterRepository.save(accountMasterToUpdate);
		    return accountMasterMapper.convertEntityToDTO(accountMasterUpdated); 
		}
		else
		{	
		    return null;
		}
	}


	@Override
	@Transactional
	public ResponseDto rechargeAccount(Integer accountCode, BigDecimal amount) {
		
		Optional<AccountMaster> accountMaster =  accountMasterRepository.findById(accountCode);
		if(accountMaster.isPresent())
		{   
			accountMaster.get().setBalanceAmount(accountMaster.get().getBalanceAmount().add(amount));
			Transaction transaction =  getTransaction(accountMaster.get(),amount,'C');
			transactionRepository.save(transaction);
		    AccountMaster accountMasterUpdated =  accountMasterRepository.save(accountMaster.get());
		    return ResponseDto.builder().message("recharge successful").statusCode(200).responseBody(accountMasterMapper.convertEntityToDTO(accountMasterUpdated)).build(); 
		}
		else
		{	
		    return ResponseDto.builder().errorMessage("No records found for account"+accountCode).statusCode(400).build();
		}
		
	}


	private Transaction getTransaction(AccountMaster accountMaster, BigDecimal amount,Character crDrIndicator) {
		
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setCrDrIndicator(crDrIndicator);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setAccountCode(accountMaster.getAccountCode());
		transaction.setBalanceAmount(accountMaster.getBalanceAmount());
		return transaction;
	}


	@Override
	public ResponseDto consumeAccount(Integer accountCode, BigDecimal amount) {
		Optional<AccountMaster> accountMaster =  accountMasterRepository.findById(accountCode);
		if(accountMaster.isPresent())
		{   
			if(accountMaster.get().getBalanceAmount().compareTo(amount)>0)
			{
			accountMaster.get().setBalanceAmount(accountMaster.get().getBalanceAmount().subtract(amount));
			Transaction transaction =  getTransaction(accountMaster.get(),amount,'D');
			transactionRepository.save(transaction);
		    AccountMaster accountMasterUpdated =  accountMasterRepository.save(accountMaster.get());
		    return ResponseDto.builder().message("consumed successful").statusCode(200).responseBody(accountMasterMapper.convertEntityToDTO(accountMasterUpdated)).build(); 
			}
			else
			{
				return ResponseDto.builder().errorMessage("No sufficient balance in account"+accountCode).statusCode(401).build();
			}
		}
		else
		{	
		    return ResponseDto.builder().errorMessage("No records found for account"+accountCode).statusCode(400).build();
		}
	}


	@Override
	public ResponseDto getAccountStatement(Integer accountCode) {
	    
		Optional<AccountMaster> accountMaster =  accountMasterRepository.findById(accountCode);
		if(accountMaster.isPresent())
		{   
			LocalDateTime toDate = LocalDateTime.now();
			LocalDateTime fromDate = LocalDateTime.now().minusDays(30);
			AccountStatementDto accountStatementDto = getAccountStatementBetweenDates(accountCode, accountMaster,
					toDate, fromDate);
		    return ResponseDto.builder().message("Account Statement").statusCode(200).responseBody(accountStatementDto).build();
			
		}
		else
		{	
		    return ResponseDto.builder().errorMessage("account code does not exists"+accountCode).statusCode(400).build();
		}
		
	}


	private AccountStatementDto getAccountStatementBetweenDates(Integer accountCode,
			Optional<AccountMaster> accountMaster, LocalDateTime toDate, LocalDateTime fromDate) {
		BigDecimal creditOpening =  transactionRepository.findAmountSumBeforeDate(accountCode,fromDate,'C');
		if(creditOpening == null) creditOpening =  BigDecimal.ZERO;
		BigDecimal debitOpening  =  transactionRepository.findAmountSumBeforeDate(accountCode,fromDate, 'D');
		if(debitOpening == null) debitOpening =  BigDecimal.ZERO;
		BigDecimal openingBalance = creditOpening.subtract(debitOpening);
		List<Transaction> transactions =transactionRepository.findBetweenDate(accountCode, fromDate, toDate);
		List<TransactionDto> transactionDtos = transactionMapper.convertEntitylistToDTOList(transactions);
		AccountStatementDto accountStatementDto = AccountStatementDto.builder().accountCode(accountCode).balanceAmount(accountMaster.get().getBalanceAmount()).openingamount(openingBalance).transactions(transactionDtos).userCode(accountMaster.get().getUserCode()).build();
		return accountStatementDto;
	}

	@Override
	public ResponseDto getAccountStatement(Integer accountCode, LocalDateTime fromDate, LocalDateTime toDate) {
		Optional<AccountMaster> accountMaster =  accountMasterRepository.findById(accountCode);
		if(accountMaster.isPresent())
		{   
			AccountStatementDto accountStatementDto = getAccountStatementBetweenDates(accountCode, accountMaster,
					toDate, fromDate);
		    return ResponseDto.builder().message("Account Statement").statusCode(200).responseBody(accountStatementDto).build();
			
		}
		else
		{	
		    return ResponseDto.builder().errorMessage("account code does not exists"+accountCode).statusCode(400).build();
		}
	}

}
