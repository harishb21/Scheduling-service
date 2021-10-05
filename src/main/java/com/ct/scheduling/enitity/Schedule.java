package com.ct.scheduling.enitity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="appointment")
@Data
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_id")
	private long id;
	
	@Column(name = "meeting_title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "physician_id")
	private long physicianId;
	
	@Column(name = "patient_id")
	private long patientId;
	
	@Column(name = "appointment_starttime")
	private String startTime;
	
	@Column(name = "appointment_endtime")
	private String endTime;
	
	@Column(name = "reason")
	private String 	reason;
	
}
