package com.isobar.wallet.management.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import com.isobar.wallet.management.user.dto.UserDto;
import com.isobar.wallet.management.user.entity.User;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

	User convertDTOToEntity(UserDto userDto);

	User updateDTOToEntity(UserDto userDto, @MappingTarget User user);

	UserDto convertEntityToDTO(User user);

}
