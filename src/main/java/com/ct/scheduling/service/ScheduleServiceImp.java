package com.ct.scheduling.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public Schedule saveSchedule(Schedule schedule) {
		scheduleDao.save(schedule);
		return schedule;
	}

	@Override
	public Optional<Schedule> getSchedule(long appointmentId) {
		
		Optional<Schedule> theschedule = scheduleDao.findById(appointmentId);
		if(!theschedule.isPresent()) {
			throw new ScheduleNotFoundException("Appointment id Not Found "+appointmentId);
		}
		return theschedule;
		
	}

	@Override
	public void deleteSchedule(long appointmentId) {
	log.info("deleteSchedule call--------------");
		//Optional<Schedule> theschedule = scheduleDao.findById(appointmentId);
		//System.out.println("------delete exception----present----"+theschedule.isPresent());
		Schedule schedule = scheduleDao.getById(appointmentId);
		
		 scheduleDao.delete(schedule);
	}
	
	

	@Override
	public ResponseTemplate getPatientDetails(long id) {
		ResponseTemplate vo = new ResponseTemplate();
		Schedule schedule= scheduleDao.getById(id);
		Patient patient = restTemplate.
		getForObject("http://localhost:8082/patients/"+schedule.getPatientId(),
				Patient.class);
		
		vo.setSchedule(schedule);
		vo.setPatient(patient);
		return vo;
	}

	@Override
	public List<Staff> getAllEmployess() {
		
		List<Staff> allEmp= restTemplate.getForObject("http://localhost:8082/employees",ArrayList.class);
		return allEmp;
	}

	@Override
	public List<Patient> getAllpatients() {
	log.info("getAllpatients ---------");
		List<Patient> allPatients= restTemplate.getForObject("http://localhost:8082/patients",ArrayList.class);
		return allPatients;
	}


	public ResponseTemplate getAllStaffDetails(long id) {
		log.info("ScheduleServiceImp ResponseTemplate -------line 64-------");
		ResponseTemplate vo = new ResponseTemplate();
		Schedule schedule= scheduleDao.getById(id);
		log.info("service infoo------------------------------");
//		List<Staff> staff = (List<Staff>) restTemplate.
//				getForObject("http://localhost:8082/employees", Staff.class);
//		System.out.println("---------------------------------");
//		System.out.println("staff-list--"+staff);
		Staff staffRecord = restTemplate.getForObject("http://localhost:8082/employees/"+10, Staff.class);
		log.info("staffRecord infoo--------"+staffRecord);
		
		vo.setPatient(null);
		vo.setSchedule(schedule);
		vo.setStaff(staffRecord);
		
		return vo;
	}

	@Override
	public List<Schedule> getAllAppointmentsByEmp(long roleId, long empId) {
		List<Schedule> appointments= new ArrayList<>();
		if (roleId == 2) {
			appointments = scheduleDao.findByphysicianId(empId);
		} else if (roleId == 1 || roleId == 3) {
			appointments = scheduleDao.findAll();
		} else if (roleId == 4) {
			appointments = scheduleDao.findAll().stream().filter(data -> data.getPatientId() == empId)
				.collect(Collectors.toList());
		}
		return appointments;
	}

	@Override
	public boolean getAppointmentSlotByEmp(long empId) {
		 String startDate="2021-10-23T09:00:00.000Z";
		 String endDate="2021-10-23T04:30:00.000Z";
		 boolean slotFlag=false;
		List<Schedule> timeslots = scheduleDao.findByphysicianId(empId)
				.stream()
				.filter(data->getslotDay(data.getStartTime())==getslotDay(startDate))
				.collect(Collectors.toList());
		System.out.println("=================================================================");	
		timeslots.stream().forEach(System.out::println);
				
		for(Schedule slot:timeslots) {
			if(getslot(slot.getStartTime()).compareTo(getslot(startDate))==0
				&& getslot(slot.getEndTime()).compareTo(getslot(endDate))==0) {
				slotFlag = true;
			}else if(((getslot(slot.getStartTime()).isBefore(getslot(startDate)) 
					|| (getslot(slot.getStartTime()).isEqual(getslot(startDate))))
					&& (getslot(slot.getEndTime()).isAfter(getslot(startDate))))
				|| ((getslot(slot.getStartTime()).isBefore(getslot(endDate)) 
						|| (getslot(slot.getStartTime()).isEqual(getslot(endDate))))
						&& (getslot(slot.getEndTime()).isAfter(getslot(endDate))))
					) {
				slotFlag = true;
			}else if((getslot(startDate).isBefore(getslot(slot.getStartTime()))
					|| getslot(slot.getStartTime()).isEqual(getslot(slot.getStartTime())))
					&& getslot(startDate).isAfter(getslot(slot.getEndTime()))) {
				slotFlag = true;
			}
		}
	
		System.out.println("===========================sslot available flag======================================");	
		System.out.println("slotFlag ===="+slotFlag);
		
		
		return slotFlag;
		
	}
	
	static int getslotDay(String date){
		//String startDate="2021-09-30T04:30:00.000Z";
	    TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(date);
	    Instant i11 = Instant.from(t1);
	    return LocalDateTime.ofInstant(i11, ZoneOffset.UTC).getDayOfYear();
	}
	
	static LocalDateTime getslot(String date){
	    TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(date);
	    Instant i11 = Instant.from(t1);
	    return LocalDateTime.ofInstant(i11, ZoneOffset.UTC);
	}
//	appointments = scheduleDao.findAll().stream()
//	.filter(data -> data.getPhysicianId() == empId)
//	.collect(Collectors.toList());

	@Override
	public List<Schedule> getAppointmentByEmpId(long empId) {
		// TODO Auto-generated method stub
		return scheduleDao.findByphysicianId(empId);
	}
}
