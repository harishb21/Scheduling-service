package com.ct.scheduling.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ct.scheduling.enitity.Patient;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.enitity.Staff;
import com.ct.scheduling.enitity.TimeSlot;
import com.ct.scheduling.enitity.TimeSlotDTO;
import com.ct.scheduling.service.ScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Slf4j
@Api(value = "ScheduleController", tags = { "Schedule Controller" })
@SwaggerDefinition(tags = { @Tag(name = "Schedule Controller", description = "REST API for Scheduling") })
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@ApiOperation(value = "Get all appointments", response = ArrayList.class, tags = "getAllAppointments")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping("/appointments")
	public ResponseEntity<List<Schedule>> getAllAppointments() {
		log.info("ScheduleController getAllAppointments()");
		return new ResponseEntity<>(scheduleService.getAllAppointmets(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all Employees", response = ArrayList.class, tags = "getAllEmployees")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping("/appointments/employees")
	public ResponseEntity<List<Staff>> getAllEmployees() {
		log.info("ScheduleController getAllEmployess()");
		return new ResponseEntity<>(scheduleService.getAllEmployees(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all patients", response = ArrayList.class, tags = "getAllpatients")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping("/appointments/patients")
	public ResponseEntity<List<Patient>> getAllpatients() {
		log.info("ScheduleController getAllpatients()");
		return new ResponseEntity<>(scheduleService.getAllpatients(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all AppointmentsByEmp", response = ArrayList.class, tags = "getAllAppointmentsByEmp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping("/appointments/{roleId}/{employeeId}")
	public ResponseEntity<List<Schedule>> getAllAppointmentsByEmp(@PathVariable long roleId,
			@PathVariable long employeeId) {
		log.info("ScheduleController getAllAppointmentsByEmp()");
		return new ResponseEntity<>(scheduleService.getAllAppointmentsByEmp(roleId, employeeId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get AppointmentsByEmp", response = ArrayList.class, tags = "getAppointmentByEmpId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"),
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping("/appointments/physicians/{physicianId}")
	public ResponseEntity<List<Schedule>> getAppointmentByEmpId(@PathVariable long physicianId) {
		log.info("ScheduleController getAppointmentByEmpId()");
		return new ResponseEntity<>(scheduleService.getAppointmentByEmpId(physicianId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get AppointmentById", response = ArrayList.class, tags = "getAppointmentById")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping("/appointments/{appointmentById}")
	public Optional<Schedule> getAppointmentById(@PathVariable long appointmentById) {
		log.info("ScheduleController getAllAppointmentsByEmp()");
		return scheduleService.getSchedule(appointmentById);
	}

	@ApiOperation(value = "Add new Appointment", response = Schedule.class, tags = "newAppointment")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@PostMapping("/appointments")
	public ResponseEntity<Schedule> newAppointment(@RequestBody Schedule newAppointment) {
		log.info("ScheduleController newAppointment()");

		return new ResponseEntity<>(scheduleService.saveSchedule(newAppointment), HttpStatus.OK);
	}

	@ApiOperation(value = "update Appointment", response = Schedule.class, tags = "updateAppointment")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@PutMapping("/appointments")
	public ResponseEntity<Schedule> updateAppointment(@RequestBody Schedule updateAppointment) {
		log.info("ScheduleController updateAppointment()");
		return new ResponseEntity<>(scheduleService.saveSchedule(updateAppointment), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Appointment", response = String.class, tags = "deleteAppointment")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@DeleteMapping("/appointments/{appointmentId}")
	public String deleteAppointment(@PathVariable long appointmentId) {
		log.info("ScheduleController deleteAppointment()");
		scheduleService.deleteSchedule(appointmentId);
		return "deleted id :" + appointmentId;
	}

	@ApiOperation(value = "Get Appointment TimeSlotCheck", response = TimeSlotDTO.class, tags = "getTimeSlotCheck")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@PostMapping("/appointments/timeSlotCheck")
	public ResponseEntity<TimeSlotDTO> getTimeSlotCheck(@RequestBody TimeSlot timeslot) {
		log.info("ScheduleController getTimeSlotCheck()");
		return new ResponseEntity<>(scheduleService.getTimeSlotCheck(timeslot), HttpStatus.OK);
	}
	@ApiOperation(value = "Get all getSortedAppointments", response = ArrayList.class, tags = "getSortedAppointments")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
							@ApiResponse(code = 401, message = "Not Authorized!"), 
							@ApiResponse(code = 403, message = "Forbidden!"),
							@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping("/appointments/sorted/{roleId}/{employeeId}")
	public ResponseEntity<List<Schedule>> getSortedAppointments(@PathVariable long roleId,
			@PathVariable long employeeId) {
		log.info("ScheduleController getSortedAppointments()");
		return new ResponseEntity<>(scheduleService.getSortedAppointments(roleId,employeeId), HttpStatus.OK);
	}

	@GetMapping("/appointments/dates/{patientId}")
	public ResponseEntity<List<String>> getAppointments(@PathVariable Long patientId) {
	return new ResponseEntity<>(scheduleService.getAppointments(patientId), HttpStatus.OK);
	}

	@GetMapping("/appointment/id")
	public ResponseEntity<?> getAppointments(@RequestParam("visitedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date visitedDate) {
	return new ResponseEntity<>(scheduleService.getAppointmentIdByAppointmentDate(visitedDate), HttpStatus.OK);
	}
	
}
