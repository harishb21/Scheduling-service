package com.ct.scheduling.service;

import java.util.List;
import java.util.Optional;

import com.ct.scheduling.enitity.Patient;
import com.ct.scheduling.enitity.ResponseTemplate;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.enitity.ScheduleResponseDTO;
import com.ct.scheduling.enitity.Staff;


public interface ScheduleService {

	public List<Schedule> getAllAppointmets();
	
	public Schedule saveSchedule(Schedule schedule);
	
	public Optional<Schedule> getSchedule(long id);
	
	public void deleteSchedule(long id);

	public ResponseTemplate getAllStaffDetails(long id);

	public ResponseTemplate getPatientDetails(long id);

	public List<Staff> getAllEmployess();

	public List<Patient> getAllpatients();
	
	public List<Schedule> getAllAppointmentsByEmp(long roleId,long empId);

	public boolean getAppointmentSlotByEmp(long empId);
	
	public List<Schedule> getAppointmentByEmpId(long empId);
	

	
}
