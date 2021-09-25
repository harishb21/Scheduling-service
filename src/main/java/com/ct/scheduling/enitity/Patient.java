package com.ct.scheduling.enitity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

	private long patientId;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String birthDate;
	private long contactNo;
	private String race;
	private String ethnicity;
	private String languages;
	private String address;
	private String username;
	private String password;
	private boolean deleted;
	private boolean active;
	private String createdOn;
	private String updatedOn;
}
