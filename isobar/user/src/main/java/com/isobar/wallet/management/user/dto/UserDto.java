package com.isobar.wallet.management.user.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
	
	private Integer userCode;
	@NotNull
	private String userName;
	@NotNull
	@Min(value = 10,message = "Phone number should 10 digits")
	@Max(value = 10,message = "Phone number should 10 digits")
	private Long mobile;
	@Email(message = "enter valid email")
	private String email;
	@NotNull
	@Size(min = 10,max = 200,message="Must be between 10 to 200 characters")
	private String addressLine1;
	private String addressLine2;
	@Size(min = 10,max = 100,message="Must be between 10 to 100 characters")
	private String landMark;
	@Size(min = 10,max = 100,message="Must be between 10 to 100 characters")
	private String city;
	@Size(min = 10,max = 100,message="Must be between 10 to 100 characters")
	private String state;
	@Size(min = 10,max = 100,message="Must be between 10 to 100 characters")
	private String country;
	@NotNull
	@Min(value = 6,message = "pincode should be 6 digits")
	@Max(value = 6,message = "pincode should be 6 digits")
	private Integer pinCode;
	private Integer accountCode;
	@NotNull
	private StringBuilder passward; 
	private BigDecimal balanceAmount;

}
