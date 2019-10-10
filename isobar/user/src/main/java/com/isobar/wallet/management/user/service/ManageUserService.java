package com.isobar.wallet.management.user.service;

import com.isobar.wallet.management.user.dto.UserDto;

public interface ManageUserService {

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto);

	Boolean deleteUser(Integer userCode);

	UserDto getUser(Integer userCode);

}
