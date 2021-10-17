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
		log.info("ScheduleController getAllAppointments()");
		return new ResponseEntity<>(scheduleService.getAllAppointmets(), HttpStatus.OK);
	}

	@PostMapping("/appointments")
	public ResponseEntity<Schedule> newAppointment(@RequestBody Schedule theschedule) {
		log.info("ScheduleController newAppointment()");
		
		return new ResponseEntity<>(scheduleService.saveSchedule(theschedule), HttpStatus.OK);
	}

	@PutMapping("/appointments")
	public ResponseEntity<Schedule> updateAppointment(@RequestBody Schedule theschedule) {
		log.info("ScheduleController updateAppointment()");
		return new ResponseEntity<>(scheduleService.saveSchedule(theschedule), HttpStatus.OK);
	}

	@DeleteMapping("/appointments/{id}")
	public String deleteAppointment(@PathVariable long id) {
		log.info("ScheduleController deleteAppointment()");
		scheduleService.deleteSchedule(id);
		return "deleted id :" + id;
	}

	@GetMapping("/appointments/patient/{id}")
	public ResponseEntity<ResponseTemplate> getPatientDetails(@PathVariable long id) {
		log.info("ScheduleController getPatientDetails()");
		return new ResponseEntity<>(scheduleService.getPatientDetails(id), HttpStatus.OK);
	}

	@GetMapping("/appointments/employees")
	public ResponseEntity<List<Staff>> getAllEmployess() {
		log.info("ScheduleController getAllEmployess()");
		return new ResponseEntity<>(scheduleService.getAllEmployess(), HttpStatus.OK);
	}

	@GetMapping("/appointments/patients")
	public ResponseEntity<List<Patient>> getAllpatients() {
		log.info("ScheduleController getAllpatients()");
		return new ResponseEntity<>(scheduleService.getAllpatients(), HttpStatus.OK);
	}

//unused
	@GetMapping("/appointments/employees/{id}")
	public ResponseTemplate getAllStaffDetails(@PathVariable long id) {
		log.info("ScheduleController getAllStaffDetails()");
		return scheduleService.getAllStaffDetails(id);
	}
	
	@GetMapping("/appointments/{roleId}/{empId}")
	public ResponseEntity<List<Schedule>> getAllAppointmentsByEmp(@PathVariable long roleId,
			@PathVariable long empId) {
		log.info("ScheduleController getAllAppointmentsByEmp()");
		return new ResponseEntity<>(scheduleService.getAllAppointmentsByEmp(roleId,empId), HttpStatus.OK);
	}
	
	@GetMapping("/appointments/timeslot/{empId}")
	public boolean getAppointmentSlotByEmp(@PathVariable long empId) {
		log.info("ScheduleController getAllAppointmentsByEmp()");
		return scheduleService.getAppointmentSlotByEmp(empId);
	}
	
	@GetMapping("/appointments/sample/{empId}")
	public ResponseEntity<List<Schedule>> getAppointmentByEmpId(@PathVariable long empId) {
		log.info("ScheduleController getAllAppointmentsByEmp()");
		return new ResponseEntity<>(scheduleService.getAppointmentByEmpId(empId), HttpStatus.OK);
	}
	
	
//	@GetMapping("/appointments/{id}")
//	public Optional<Schedule> getAppointmentById(@PathVariable long id) {
//
//		// Optional<Schedule> ob = scheduleService.getSchedule(id);
//
//		return scheduleService.getSchedule(id);
//	}

}
