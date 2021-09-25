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

	private long staffId;
	private int roleId;
	private int empId;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String birthDate;
	private String username;
	private String password;
	private boolean deleted;
	private boolean active;
	private String createdOn;
	private String updatedOn;
}
