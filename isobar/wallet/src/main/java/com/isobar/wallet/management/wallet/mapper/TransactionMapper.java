package com.isobar.wallet.management.wallet.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import com.isobar.wallet.management.wallet.dto.TransactionDto;
import com.isobar.wallet.management.wallet.entity.Transaction;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TransactionMapper {

	Transaction convertDTOToEntity(TransactionDto transactionDto);

	TransactionDto convertEntityToDTO(Transaction accountMaster);
	
	List<TransactionDto> convertEntitylistToDTOList(List<Transaction> accountMaster);
	

}
