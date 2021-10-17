package com.ct.scheduling.enitity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class TimeSlot {

	private long roleId;
	private long staffempId;
	private long patientId;
	private String startDateTime;
	private String endTDateime;
}
