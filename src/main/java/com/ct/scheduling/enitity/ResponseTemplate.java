package com.ct.scheduling.enitity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate {

	private Schedule schedule;
	private Staff staff;
	private Patient patient;
}
