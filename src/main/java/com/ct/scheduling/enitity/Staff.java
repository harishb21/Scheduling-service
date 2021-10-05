package com.ct.scheduling.enitity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Staff {

	private long userId;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private Date birthDate;
	private long contactNo;
	private String password;
	private int attempt;
	private boolean deleted;
	private String status;
	private Date createdOn;
	private Date updatedOn;
	private int roleId;
	private int empId;
}
