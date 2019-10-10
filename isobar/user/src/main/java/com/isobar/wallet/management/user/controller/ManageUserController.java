package com.isobar.wallet.management.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isobar.wallet.management.user.dto.ResponseDto;
import com.isobar.wallet.management.user.dto.UserDto;
import com.isobar.wallet.management.user.service.ManageUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
public class ManageUserController {
	
	@Autowired
	private ManageUserService manageUserService;
	
	
	@ApiOperation(value = "create user", produces = "application/json", response = ResponseDto.class, consumes = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseDto.class),
			@ApiResponse(code = 500, message = "Internal Server error", response = ResponseDto.class) })
	
	@PostMapping("/user")
	public ResponseDto createUser(@RequestBody UserDto userDto)
	{
		ResponseDto response;
		UserDto userDtoCreated = manageUserService.createUser(userDto);
		
		if(userDtoCreated != null)
		{
			response  =  ResponseDto.builder().statusCode(200).message("user created").responseBody(userDtoCreated).build();
		}
		else
		{
			response  =  ResponseDto.builder().statusCode(500).message("user not created").build();	
		}
		return response;
	}
	@PutMapping("/user")
	public ResponseDto updateUser(@RequestBody UserDto userDto)
	{
		ResponseDto response;
		UserDto userDtoCreated = manageUserService.updateUser(userDto);
		
		if(userDtoCreated != null)
		{
			response  =  ResponseDto.builder().statusCode(200).message("user updated").responseBody(userDtoCreated).build();
		}
		else
		{
			response  =  ResponseDto.builder().statusCode(500).message("user not updated").build();	
		}
		return response;
	}
	@DeleteMapping("/user/{userCode}")
	public ResponseDto deleteUser(@PathVariable Integer userCode)
	{
		ResponseDto response;
		
		Boolean result =  manageUserService.deleteUser(userCode);
		if(result)
		{
			response  =  ResponseDto.builder().statusCode(200).message("user deleted user code: "+userCode).build();
		}
		else
		{
			response  =  ResponseDto.builder().statusCode(500).message("could not delete the user").build();	
		}
		
		return null;
	}
	@GetMapping("/user/{userCode}")
	public ResponseDto getUser(@PathVariable Integer userCode)
	{
		ResponseDto response;
		UserDto userDto= manageUserService.getUser(userCode);
		
		if(userDto != null)
		{
			response  =  ResponseDto.builder().statusCode(200).message("user details ").responseBody(userDto).build();
		}
		else
		{
			response  =  ResponseDto.builder().statusCode(400).message("No records found").build();	
		}
		return response;	
		
	}
	
	
	
	
	
	

}
