package com.ct.scheduling.enitity;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

	private long userId;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private Date birthDate;
	private long contactNo;
	private String password;
	private Integer attempt;
	private boolean deleted;
	private String status;
	private Date createdOn;
	private Date updatedOn;
	private Integer roleId;
	private Integer empId;
	private String race;
	private String ethnicity;
	private String languages;
	private String address;
	
	
	
	
	
	
	
}
