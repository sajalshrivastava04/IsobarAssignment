package com.isobar.wallet.management.wallet.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="transaction")
@Data
@NamedQueries({
	@NamedQuery(name = "Transaction.findAmountSumBeforeDate", query = "select SUM(amount) as Amount from Transaction t where t.accountCode = :accountCode and t.transactionDate < :seletedDate and  t.crDrIndicator = :crdr"),
	@NamedQuery(name = "Transaction.findBetweenDate", query = "select t from Transaction t where t.accountCode = :accountCode and t.transactionDate >=:fromDate and  t.transactionDate <= :toDate")
	
	})
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionCode;
	private Character crDrIndicator;
	private BigDecimal amount;
	private BigDecimal balanceAmount;
	private LocalDateTime transactionDate;
	private Integer accountCode;
	
}
