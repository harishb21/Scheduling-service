package com.ct.scheduling.enitity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

	@ApiModelProperty(notes = "Appointment Id",name="id",required=true,value="1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_id")
	private long id;
	
	@ApiModelProperty(notes = "Appointment Title",name="title",required=true,value="Polio Virus")
	@Column(name = "meeting_title")
	private String title;
	
	@ApiModelProperty(notes = "Appointment description",name="description",required=true,value="Virus enters inside the body through food or water.")
	@Column(name = "description")
	private String description;
	
	@ApiModelProperty(notes = "Appointment physicianId",name="physicianId",required=true,value="3423")
	@Column(name = "physician_id")
	private long physicianId;
	
	@ApiModelProperty(notes = "Appointment patientId",name="patientId",required=true,value="2")
	@Column(name = "patient_id")
	private long patientId;
	
	@ApiModelProperty(notes = "Appointment startTime",name="startTime",required=true,value="2021-10-17T07:00:00.000Z")
	@Column(name = "appointment_starttime")
	private String startTime;
	
	@ApiModelProperty(notes = "Appointment endTime",name="endTime",required=true,value="2021-10-17T09:00:00.000Z")
	@Column(name = "appointment_endtime")
	private String endTime;
	
	@ApiModelProperty(notes = "Appointment reason",name="reason",required=true,value="reason for to update Appointment")
	@Column(name = "reason")
	private String 	reason;
	
	
}
