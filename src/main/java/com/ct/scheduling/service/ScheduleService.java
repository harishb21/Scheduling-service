package com.ct.scheduling.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;

import com.ct.scheduling.enitity.Patient;
import com.ct.scheduling.enitity.ResponseTemplate;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.enitity.Staff;
import com.ct.scheduling.enitity.TimeSlot;
import com.ct.scheduling.enitity.TimeSlotDTO;


public interface ScheduleService {

	public List<Schedule> getAllAppointmets();
	
	public Schedule saveSchedule(Schedule schedule);
	
	public Optional<Schedule> getSchedule(long id);
	
	public void deleteSchedule(long id);

	public List<Staff> getAllEmployees();

	public List<Patient> getAllpatients();
	
	public List<Schedule> getAllAppointmentsByEmp(long roleId,long empId);

	public List<Schedule> getAppointmentByEmpId(long empId);

	public TimeSlotDTO getTimeSlotCheck(TimeSlot timeslot);

	public List<Schedule> getSortedAppointments(long roleId,long employeeId);
	

	
}
