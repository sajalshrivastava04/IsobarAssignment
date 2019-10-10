package com.isobar.wallet.management.wallet.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isobar.wallet.management.wallet.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
   BigDecimal findAmountSumBeforeDate(@Param("accountCode") Integer accountCode, @Param("seletedDate") LocalDateTime date,@Param("crdr") Character crDr);
   List<Transaction> findBetweenDate(@Param("accountCode") Integer accountCode,@Param("fromDate") LocalDateTime fromDate,@Param("toDate") LocalDateTime toDate);
}
