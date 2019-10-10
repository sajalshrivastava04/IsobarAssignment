package com.isobar.wallet.management.wallet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import com.isobar.wallet.management.wallet.dto.AccountMasterDto;
import com.isobar.wallet.management.wallet.entity.AccountMaster;



@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AccountMasterMapper {
	
	AccountMaster convertDTOToEntity(AccountMasterDto accountMasterDto);

	AccountMaster updateDTOToEntity(AccountMasterDto accountMasterDto, @MappingTarget AccountMaster accountMaster);

	AccountMasterDto convertEntityToDTO(AccountMaster accountMaster);


}
