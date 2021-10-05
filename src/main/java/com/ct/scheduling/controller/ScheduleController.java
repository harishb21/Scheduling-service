package com.ct.scheduling.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.scheduling.enitity.Patient;
import com.ct.scheduling.enitity.ResponseTemplate;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.enitity.Staff;
import com.ct.scheduling.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Slf4j
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@GetMapping("/appointments")
	public ResponseEntity<List<Schedule>> getAllAppointments() {
		log.info("getAllAppointments-----------line 38");
		return new ResponseEntity<>(scheduleService.getAllAppointmets(), HttpStatus.OK);
	}

	@PostMapping("/appointments")
	public ResponseEntity<Schedule> newAppointment(@RequestBody Schedule theschedule) {
		log.info("newAppointment-----------line----47----------");
		// scheduleService.saveSchedule(theschedule);
		return new ResponseEntity<>(scheduleService.saveSchedule(theschedule), HttpStatus.OK);
	}

	@PutMapping("/appointments")
	public ResponseEntity<Schedule> updateAppointment(@RequestBody Schedule theschedule) {

		return new ResponseEntity<>(scheduleService.saveSchedule(theschedule), HttpStatus.OK);
	}

	@DeleteMapping("/appointments/{id}")
	public String deleteAppointment(@PathVariable long id) {
		log.info("deleteAppointment-----------line 58");
		scheduleService.deleteSchedule(id);
		return "deleted id :" + id;
	}

	@GetMapping("/appointments/patient/{id}")
	public ResponseEntity<ResponseTemplate> getPatientDetails(@PathVariable long id) {
		log.info("getPatientDetails-----------line 66");
		return new ResponseEntity<>(scheduleService.getPatientDetails(id), HttpStatus.OK);
	}

	@GetMapping("/appointments/employees")
	public ResponseEntity<List<Staff>> getAllEmployess() {
		log.info("getAllEmployess-----------line 71");
		return new ResponseEntity<>(scheduleService.getAllEmployess(), HttpStatus.OK);
	}

	@GetMapping("/appointments/patients")
	public ResponseEntity<List<Patient>> getAllpatients() {
		log.info("getAllpatients-----------line 76");
		return new ResponseEntity<>(scheduleService.getAllpatients(), HttpStatus.OK);
	}


	@GetMapping("/appointments/employees/{id}")
	public ResponseTemplate getAllStaffDetails(@PathVariable long id) {

		return scheduleService.getAllStaffDetails(id);
	}
	
//	@GetMapping("/appointments/{id}")
//	public Optional<Schedule> getAppointmentById(@PathVariable long id) {
//
//		// Optional<Schedule> ob = scheduleService.getSchedule(id);
//
//		return scheduleService.getSchedule(id);
//	}

}
