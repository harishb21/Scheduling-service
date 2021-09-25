package com.ct.scheduling.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ct.scheduling.dao.ScheduleRespository;
import com.ct.scheduling.enitity.Patient;
import com.ct.scheduling.enitity.ResponseTemplate;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.enitity.Staff;
import com.ct.scheduling.exception.ScheduleNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImp implements ScheduleService{

	@Autowired
	private ScheduleRespository scheduleDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<Schedule> getAllAppointmets() {
		return scheduleDao.findAll();
	}

	@Override
	public void saveSchedule(Schedule schedule) {
		scheduleDao.save(schedule);
	}

	@Override
	public Optional<Schedule> getSchedule(long appointmentId) {
		
		System.out.println("--get--service-----innn----");
		Optional<Schedule> theschedule = scheduleDao.findById(appointmentId);
		if(!theschedule.isPresent()) {
			System.out.println("--get--service--37---if----");
			throw new ScheduleNotFoundException("Appointment id Not Found "+appointmentId);
		}
		return theschedule;
		
	}

	@Override
	public void deleteSchedule(long appointmentId) {
		System.out.println("--deleteSchedule--------service 48-----------");
		//Optional<Schedule> theschedule = scheduleDao.findById(appointmentId);
		//System.out.println("------delete exception----present----"+theschedule.isPresent());
		Schedule schedule = scheduleDao.getById(appointmentId);
		System.out.println("schedule---"+schedule);
		
		 scheduleDao.delete(schedule);
	}
	
	public ResponseTemplate getAllStaffDetails(long id) {
		System.out.println("-----service metohd called -------------------------");
		ResponseTemplate vo = new ResponseTemplate();
		Schedule schedule= scheduleDao.getById(id);
		log.info("service infoo------------------------------");
//		List<Staff> staff = (List<Staff>) restTemplate.
//				getForObject("http://localhost:8082/employees", Staff.class);
//		System.out.println("---------------------------------");
//		System.out.println("staff-list--"+staff);
		Staff staff1 = restTemplate.
		getForObject("http://localhost:8082/employees/"+
		6, Staff.class);
		System.out.println("----staff1-----77-----");
		System.out.println(staff1);
		log.info("staff details --------------------------------------------");
		log.info(staff1.toString());
		vo.setSchedule(schedule);
		vo.setStaff(staff1);
		
		return vo;
	}

	@Override
	public ResponseTemplate getPatientDetails(long id) {
		ResponseTemplate vo = new ResponseTemplate();
		Schedule schedule= scheduleDao.getByAppointmentId(id);
		Patient patient = restTemplate.
		getForObject("http://localhost:8082/patients/"+schedule.getPatientId(),
				Patient.class);
		
		vo.setSchedule(schedule);
		vo.setPatient(patient);
		return vo;
	}

	
}
