package com.ct.scheduling.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.scheduling.enitity.ResponseTemplate;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.service.ScheduleService;

@RestController
@RequestMapping("/api")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@GetMapping("/appointments")
	public List<Schedule> getAllAppointments() {
		return scheduleService.getAllAppointmets();
	}

//	@GetMapping("/appointments/{id}")
//	public Optional<Schedule> getAppointmentById(@PathVariable long id) {
//
//		// Optional<Schedule> ob = scheduleService.getSchedule(id);
//
//		return scheduleService.getSchedule(id);
//	}

	@PostMapping("/appointments")
	public Schedule newAppointment(@RequestBody Schedule theschedule) {

		theschedule.setAppointmentId(0);
		scheduleService.saveSchedule(theschedule);
		return theschedule;
	}

	@PutMapping("/appointments")
	public Schedule updateAppointment(@RequestBody Schedule theschedule) {

		scheduleService.saveSchedule(theschedule);
		return theschedule;
	}

	@DeleteMapping("/appointments/{id}")
	public String deleteAppointment(@PathVariable long id) {
		System.out.println("-----controller  56s--------");
		scheduleService.deleteSchedule(id);
		return "deleted id :" + id;
	}
	
	@GetMapping("/appointments/{id}")
	public ResponseTemplate getAllStaffDetails(@PathVariable long id) {
		
		return scheduleService.getAllStaffDetails(id);
	}
	@GetMapping("/appointments/patient/{id}")
	public ResponseTemplate getPatientDetails(@PathVariable long id) {
		
		return scheduleService.getPatientDetails(id);
	}
	
}
