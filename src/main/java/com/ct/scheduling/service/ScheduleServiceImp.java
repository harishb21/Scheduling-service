package com.ct.scheduling.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.ct.scheduling.dao.ScheduleRespository;
import com.ct.scheduling.enitity.Patient;
import com.ct.scheduling.enitity.ResponseTemplate;
import com.ct.scheduling.enitity.Role;
import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.enitity.Staff;
import com.ct.scheduling.enitity.TimeSlot;
import com.ct.scheduling.enitity.TimeSlotDTO;
import com.ct.scheduling.enitity.Mail;
import com.ct.scheduling.exception.ScheduleNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImp implements ScheduleService {

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
		
	/*Patient patient = restTemplate.getForObject("http://localhost:8082/patients/" + schedule.getPatientId(),
				Patient.class);
	
	Staff staff = restTemplate.getForObject("http://localhost:8082/employees/employeeId/" + schedule.getPhysicianId(),
			Staff.class);*/
	
	//Mail mail = new Mail("harish.bandhamravuri@gmail.com","spring subject","hello");
	
	// mail1 = restTemplate.postForObject("http://localhost:8082/mail/send/" , mail,Mail.class);
		return schedule;
	}

	@Override
	public Optional<Schedule> getSchedule(long appointmentId) {

		Optional<Schedule> theschedule = scheduleDao.findById(appointmentId);
		if (!theschedule.isPresent()) {
			throw new ScheduleNotFoundException("Given appointmentId is not found. please provide valid AppointmentId");
		}
		return theschedule;

	}

	@Override
	public void deleteSchedule(long appointmentId) {
		log.info("ScheduleServiceImp  deleteSchedule()");
		Optional<Schedule> theschedule = scheduleDao.findById(appointmentId);
		if (!theschedule.isPresent()) {
			throw new ScheduleNotFoundException("Given appointmentId is not found. please provide valid AppointmentId");
		}
		Schedule schedule = scheduleDao.getById(appointmentId);
		scheduleDao.delete(schedule);
	}

	
	@Override
	public List<Staff> getAllEmployees() {
		log.info("ScheduleServiceImp  getAllEmployess()");
		List<Staff> allEmp = restTemplate.getForObject("http://localhost:8082/employees", ArrayList.class);
		return allEmp;
	}

	@Override
	public List<Patient> getAllpatients() {
		log.info("ScheduleServiceImp  getAllpatients()");
		List<Patient> allPatients = restTemplate.getForObject("http://localhost:8082/users/patients", ArrayList.class);
		return allPatients;
	}

	@Override
	public List<Schedule> getAllAppointmentsByEmp(long roleId, long empId) {
		List<Schedule> appointments = new ArrayList<>();
//		List<Role> allRoles= restTemplate.getForObject("http://localhost:8082/roles",ArrayList.class);
//		Optional<Role> theRole = allRoles.stream().filter(data->data.getRoleId() == roleId).findFirst();
//		if(!theRole.isPresent() && theRole.get().getRoleId() != roleId) {
//			throw new ScheduleNotFoundException("Given Role Id not found. please provide valid RoleId");
//		}

		if (roleId == 2) {
			appointments = scheduleDao.findByphysicianId(empId);
		} else if (roleId == 1 || roleId == 3) {
			appointments = scheduleDao.findAll();
		} else if (roleId == 4) {
			appointments = scheduleDao.findBypatientId(empId);
		}
		return appointments;
	}

	@Override
	public List<Schedule> getAppointmentByEmpId(long empId) {
		return scheduleDao.findByphysicianId(empId);
	}

	@Override
	public TimeSlotDTO getTimeSlotCheck(TimeSlot timeslot) {

		String uiStartTime = timeslot.getStartDateTime();
		String uiEndTime = timeslot.getEndDateTime();
		
		boolean slotFlag = false;

		if (getslotDate(uiStartTime).isAfter(getslotDate(uiEndTime))
				|| getslotDate(timeslot.getStartDateTime()).compareTo(getslotDate(timeslot.getEndDateTime())) == 0) {
			throw new ScheduleNotFoundException("please provide valid Start date Or end date time range");
		}
		Schedule physicianData = scheduleDao.findByphysicianId(timeslot.getPhysicianEmpId()).stream().findFirst().get();
		Schedule patientData = scheduleDao.findBypatientId(timeslot.getPatientId()).stream().findFirst().get();
		if (patientData.getPatientId() != timeslot.getPatientId()) {
			throw new ScheduleNotFoundException("Given PatientId is not found. please provide valid PatientId");
		}
		if (physicianData.getPhysicianId() != timeslot.getPhysicianEmpId()) {
			throw new ScheduleNotFoundException("Given PhysicianId is not found. please provide valid PhysicianId");
		}
		TimeSlotDTO timeslotdto = new TimeSlotDTO();
		List<Schedule> timeslots = scheduleDao.findByphysicianId(timeslot.getPhysicianEmpId()).stream()
				.filter(data -> getslotDay(data.getStartTime()) == getslotDay(uiStartTime))
				.collect(Collectors.toList());
		for (Schedule slot : timeslots) {

			slotFlag = slotValidation(uiStartTime, uiEndTime, slot);
			if (slotFlag) {
				break;
			}
		}
		timeslotdto.setTimeSlotFlag(slotFlag);
		timeslotdto.setMessage("");
		return timeslotdto;
	}

	private boolean slotValidation(String uiStartTime, String uiEndTime, Schedule slot) {
		boolean slotFlag = false;
	
		if (getslotDate(slot.getStartTime()).compareTo(getslotDate(uiStartTime)) == 0
				&& getslotDate(slot.getEndTime()).compareTo(getslotDate(uiEndTime)) == 0) {
			slotFlag = true;

		} else if ((getslotDate(slot.getStartTime()).isBefore(getslotDate(uiStartTime))
				|| (getslotDate(slot.getStartTime()).isEqual(getslotDate(uiStartTime))))
				&& getslotDate(slot.getEndTime()).isAfter(getslotDate(uiStartTime))) {
			slotFlag = true;
		} else if (getslotDate(slot.getStartTime()).isBefore(getslotDate(uiEndTime))
				&& (getslotDate(slot.getEndTime()).isAfter(getslotDate(uiEndTime)))
				|| getslotDate(slot.getEndTime()).compareTo(getslotDate(uiEndTime)) == 0) {
			slotFlag = true;
		} else if ((getslotDate(uiStartTime).isBefore(getslotDate(slot.getStartTime()))
				|| getslotDate(uiStartTime).compareTo(getslotDate(slot.getStartTime())) == 0)
				&& getslotDate(uiEndTime).isAfter(getslotDate(slot.getEndTime()))) {
			slotFlag = true;
		}
		return slotFlag;
	}

	public static LocalDateTime getslotDate(String date) {
		TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(date);
		Instant i11 = Instant.from(t1);
		return LocalDateTime.ofInstant(i11, ZoneOffset.UTC);
	}

	public static int getslotDay(String date) {
		TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(date);
		Instant i11 = Instant.from(t1);
		return LocalDateTime.ofInstant(i11, ZoneOffset.UTC).getDayOfYear();
	}
	
	public static String  getDate(String date) {
		TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(date);
		Instant i11 = Instant.from(t1);
		String formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.ofInstant(i11, ZoneOffset.UTC));
		return formatted;
	}

	public static String  getTime(String sDate,String eDate) {
		TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(sDate);
		TemporalAccessor t2 = DateTimeFormatter.ISO_INSTANT.parse(eDate);
		Instant i1 = Instant.from(t1);
		Instant i2 = Instant.from(t2);
		LocalDateTime ldt1 = LocalDateTime.ofInstant(i1, ZoneOffset.UTC);
		LocalDateTime ldt2 = LocalDateTime.ofInstant(i2, ZoneOffset.UTC);
		String time1  = ldt1.format(DateTimeFormatter.ofPattern("HH:mm"));
		String time2  = ldt2.format(DateTimeFormatter.ofPattern("HH:mm"));
		return (time1 +"-"+time2);
	}

	@Override
	public List<Schedule> getSortedAppointments(long roleId,
			long empId) {
		List<Schedule> sortedlist = new ArrayList<>();
		List<Schedule> appointments = new ArrayList<>();

		Comparator<Schedule> customComparator = new Comparator<Schedule>() {
		    @Override
		    public int compare(Schedule o1, Schedule o2) {
		    if(getslotDate(o1.getStartTime())== (getslotDate(o2.getStartTime())))
	            return 0;
	        else if(getslotDate(o1.getStartTime()).isAfter(getslotDate(o2.getStartTime())))
	            return 1;
	        else return -1;

		    }
		};
		
		if (roleId == 2) {
			appointments = scheduleDao.findByphysicianId(empId);
		} else if (roleId == 1 || roleId == 3) {
			appointments = scheduleDao.findAll();
		} else if (roleId == 4) {
			appointments = scheduleDao.findBypatientId(empId);
		}
		if(!appointments.isEmpty()) {
		sortedlist = appointments
				.stream()
				.filter(data->geFilterValue(data.getStartTime()))
				.sorted(customComparator)
				.collect(Collectors.toList());
		}
		return sortedlist;
	}
	
	boolean geFilterValue(String startTime) {
		
		boolean sortflag = false;
		LocalDate localDate =LocalDate.now();
		if(getslotDate(startTime).toLocalDate().isEqual(localDate) ||
		getslotDate(startTime).toLocalDate().isAfter(localDate)	
		) {
			sortflag = true;
		}
		
		return sortflag;
	}
	
}
