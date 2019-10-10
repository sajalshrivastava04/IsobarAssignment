package com.isobar.wallet.management.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_details")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userCode;
	private String userName;
	private Long mobile;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String landMark;
	private String city;
	private String state;
	private String country;
	private Integer pinCode;
	private Integer accountCode;
	private StringBuilder passward; 

}
