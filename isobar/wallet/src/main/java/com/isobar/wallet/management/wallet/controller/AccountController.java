package com.isobar.wallet.management.wallet.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isobar.wallet.management.wallet.dto.AccountMasterDto;
import com.isobar.wallet.management.wallet.dto.ResponseDto;
import com.isobar.wallet.management.wallet.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    
	
	@Autowired
	private AccountService accountService;

	@PostMapping("/account")
	public ResponseDto createAccount(@RequestBody AccountMasterDto accountDto) {
		ResponseDto response = null;

		AccountMasterDto createdAccount = accountService.createAccount(accountDto);
		if (createdAccount != null) {
			response = ResponseDto.builder().message("Account created").responseBody(createdAccount).statusCode(200)
					.build();
		} else {
			response = ResponseDto.builder().message("Could not create account").errorMessage("internal server")
					.statusCode(500).build();
		}

		return response;
	}

	@PutMapping("/account/{accountCode}")
	public ResponseDto updateAccount(@PathVariable Integer accountCode, @RequestBody AccountMasterDto accountDto) {
		ResponseDto response = null;

		AccountMasterDto createdAccount = accountService.updateAccount(accountCode, accountDto);
		if (createdAccount != null) {
			response = ResponseDto.builder().message("Account updated").responseBody(createdAccount).statusCode(200)
					.build();
		} else {
			response = ResponseDto.builder().message("Could not updated account").errorMessage("internal server")
					.statusCode(500).build();
		}

		return response;
	}

	@DeleteMapping("/account/{accountCode}")
	public ResponseDto deleteAccount(@PathVariable Integer accountCode) {
		ResponseDto response;
		Boolean result = accountService.deleteAccount(accountCode);
		if (result) {
			response = ResponseDto.builder().message("account deleted").build();
		} else {
			response = ResponseDto.builder().message("could not delete account").build();
		}
		return response;

	}

	@GetMapping("/account/{accountCode}")
	public ResponseDto getAccount(@PathVariable Integer accountCode) {
		ResponseDto response;
		AccountMasterDto accountDto = accountService.getAccount(accountCode);
		if (accountDto != null) {
			response = ResponseDto.builder().message("account details").responseBody(accountDto).statusCode(200).build();
		} else {
			response = ResponseDto.builder().message("No records fount ").statusCode(500).build();
		}
		return response;

	}
	@PostMapping("/account/{accountCode}/funds/{amount}")
	public ResponseDto rechargeAccount(@PathVariable Integer accountCode,BigDecimal amount)
	{
		ResponseDto response = accountService.rechargeAccount(accountCode,amount);
		return response;
	}
	@DeleteMapping("/account/{accountCode}/funds/{amount}")
	public ResponseDto consumeAccount(@PathVariable Integer accountCode,BigDecimal amount)
	{
		ResponseDto response = accountService.consumeAccount(accountCode,amount);
		return response;
	}
	@GetMapping("/account/{accountCode}/statement")
	public ResponseDto findAccountStatement(@PathVariable Integer accountCode)
	{
		ResponseDto response = accountService.getAccountStatement(accountCode);
		return response;
	}
	@GetMapping("/account/{accountCode}/statement/{fromDate}/{toDate}")
	public ResponseDto findAccountStatement(@PathVariable Integer accountCode,@PathVariable String fromDate,  @PathVariable String  toDate)
	{    
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(toDate,formatter);
		ResponseDto response = accountService.getAccountStatement(accountCode,formatDateTime,toDateTime);
		return response;
	}
}
