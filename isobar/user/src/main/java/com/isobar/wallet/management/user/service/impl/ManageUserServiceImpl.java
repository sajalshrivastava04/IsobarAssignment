package com.isobar.wallet.management.user.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.isobar.wallet.management.user.dto.AccountMasterDto;
import com.isobar.wallet.management.user.dto.ResponseDto;
import com.isobar.wallet.management.user.dto.UserDto;
import com.isobar.wallet.management.user.entity.User;
import com.isobar.wallet.management.user.mapper.UserMapper;
import com.isobar.wallet.management.user.repository.ManageUserRepository;
import com.isobar.wallet.management.user.service.ManageUserService;
import com.isobar.wallet.management.user.utils.RestUtil;

@Service
public class ManageUserServiceImpl implements ManageUserService {

	@Autowired
	private ManageUserRepository manageUserRepository;

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	RestUtil restUtil;

	@Override
	@Transactional
	public UserDto createUser(UserDto userDto) {

		User user = userMapper.convertDTOToEntity(userDto);
		User savedUser = manageUserRepository.save(user);
		AccountMasterDto accountMasterDto = AccountMasterDto.builder().accountType("wallet type").balanceAmount(BigDecimal.ZERO).createdDate(LocalDateTime.now()).userCode(savedUser.getUserCode()).build();
		ResponseDto response =  restUtil.invokeServiceAPI("http://wallet/wallet-management/accounts/account", HttpMethod.POST, accountMasterDto,ResponseDto.class);
        if(response == null)
        {
        	throw new RuntimeException("could not create account");
        }
        AccountMasterDto savedAccountMasterDto = (AccountMasterDto) restUtil.objectToObjectConverter(response.getResponseBody(),AccountMasterDto.class);
        savedUser.setAccountCode(savedAccountMasterDto.getAccountCode());
        User updatedUser = manageUserRepository.save(savedUser);
		return userMapper.convertEntityToDTO(updatedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto) {

		Optional<User> user = manageUserRepository.findById(userDto.getUserCode());
		if (user.isPresent()) {
			User userToUpdate = userMapper.updateDTOToEntity(userDto, user.get());
			User updatedUser = manageUserRepository.save(userToUpdate);
			return userMapper.convertEntityToDTO(updatedUser);

		} else {
			return null;
		}
	}

	@Override
	public Boolean deleteUser(Integer userCode) {
		Optional<User> user = manageUserRepository.findById(userCode);
		if (user.isPresent()) {
			manageUserRepository.delete(user.get());
			return true;

		} else {
			return false;
		}

	}
	@Override
	public UserDto getUser(Integer userCode) {
		Optional<User> user = manageUserRepository.findById(userCode);
		if (user.isPresent()) {
			UserDto userDto = userMapper.convertEntityToDTO(user.get());
			ResponseDto response =  restUtil.invokeServiceAPI("http://wallet/wallet-management/accounts/account/"+userDto.getAccountCode(), HttpMethod.GET, null,ResponseDto.class);
			AccountMasterDto savedAccountMasterDto = (AccountMasterDto) restUtil.objectToObjectConverter(response.getResponseBody(),AccountMasterDto.class);
			userDto.setBalanceAmount(savedAccountMasterDto.getBalanceAmount());
			return userDto;
		} else {
			return null;
		}
	}

}
