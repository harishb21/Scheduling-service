package com.ct.scheduling.service;

import java.util.List;
import java.util.Optional;

import com.ct.scheduling.enitity.ResponseTemplate;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.enitity.ScheduleResponseDTO;

public interface ScheduleService {

	public List<Schedule> getAllAppointmets();
	
	public void saveSchedule(Schedule schedule);
	
	public Optional<Schedule> getSchedule(long id);
	
	public void deleteSchedule(long id);

	public ResponseTemplate getAllStaffDetails(long id);

	public ResponseTemplate getPatientDetails(long id);
}
