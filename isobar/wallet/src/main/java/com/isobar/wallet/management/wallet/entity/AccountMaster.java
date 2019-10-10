package com.isobar.wallet.management.wallet.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name ="account_master")
@Data
public class AccountMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountCode;
	private Integer userCode;
	private BigDecimal balanceAmount;
	private LocalDateTime createdDate;
	private String accountType;

}
