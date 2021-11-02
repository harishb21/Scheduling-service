package com.ct.scheduling.facade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

	private long id;
	private String title;
	private String description;
	private long physicianId;
	private long patientId;
	private String startTime;
	private String endTime;
	private String reason;
	private Date appointmentDate;
	
	
}
