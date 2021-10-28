package com.ct.scheduling.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ct.scheduling.constant.SchedulerCommonConstant;
import com.ct.scheduling.dao.ScheduleRespository;
import com.ct.scheduling.enitity.Patient;
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
		Patient patient = restTemplate.getForObject(SchedulerCommonConstant.PATIENTSERVICE + schedule.getPatientId(),
				Patient.class);

		Staff staff = restTemplate.getForObject(SchedulerCommonConstant.EMPLOYEEBYID + schedule.getPhysicianId(),
				Staff.class);
		if (null == staff) {
			throw new ScheduleNotFoundException(SchedulerCommonConstant.PHYSICIANERROR);
		}
		if (null == patient ) {
			throw new ScheduleNotFoundException(SchedulerCommonConstant.PATIENTERROR);
		}
		String patientsubject = "Welcome to CT General Hospital!";
		String patientBody = String.format("Hi %s,"
				+ "\r\nThanks for choosing CT General hospital. You’ve just taken an exciting step in your wellness journey, and we’re so glad to be a part of it.\r\n"
				+ "\r\nYour appointment has booked for  %s on  %s\r\n"
				+ "All the information you need for your appointment is available here.\r\n"
				+ "\r\nTo Sign in to your account, please visit https://localhost:8080/ or Click here.\r\n"
				+ "Best Regards,\r\n" + "CT General Hospital", patient.getFirstName(),
				getFormateTime(schedule.getStartTime(), schedule.getEndTime()),
				getFormateDate(schedule.getStartTime()));

		String physicianSubject = "CT General Hospital Appointment Details";
		String physicianBody = String.format(
				"Hi %s," + "\r\nAppointment has booked with %s at  %s on  %s\r\n"
						+ "\r\nplease visit https://localhost:8080/ or Click here. \r\n" + "Best Regards,\r\n"
						+ "CT General Hospital",
				staff.getFirstName(), patient.getFirstName(),
				getFormateTime(schedule.getStartTime(), schedule.getEndTime()),
				getFormateDate(schedule.getStartTime()));

		Mail patientMail = new Mail(patient.getEmail(), patientsubject, patientBody);
		restTemplate.postForObject(SchedulerCommonConstant.MAILSERVICEURL, patientMail, Boolean.class);

		Mail physicianMail = new Mail(patient.getEmail(), physicianSubject, physicianBody);
		restTemplate.postForObject(SchedulerCommonConstant.MAILSERVICEURL, physicianMail, Boolean.class);
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
		Patient patient = restTemplate.getForObject(SchedulerCommonConstant.PATIENTSERVICE + +schedule.getPatientId(),
				Patient.class);

		Staff staff = restTemplate.getForObject(SchedulerCommonConstant.EMPLOYEEBYID + schedule.getPhysicianId(),
				Staff.class);
		if (null == staff) {
			throw new ScheduleNotFoundException(SchedulerCommonConstant.PHYSICIANERROR);
		}
		if ( null == patient) {
			throw new ScheduleNotFoundException(SchedulerCommonConstant.PATIENTERROR);
		}
		String patientsubject = "Welcome to CT General Hospital!";
		String patientBody = String.format(
				"Hi %s,\r\n" + "\r\nI apologize for the short notice and any inconvenience this may cause.\r\n"
						+ "\r\nI'm forced to cancel our appointment which was scheduled on %s at %s\r\n"
						+ "\r\nAll the information you need for your appointment is available here.\r\n"
						+ "\r\nplease visit https://localhost:8080/ or Click here. \r\n\r\n" + "Best Regards,\r\n"
						+ "CT General Hospital",
				patient.getFirstName(), getFormateDate(schedule.getStartTime()),
				getFormateTime(schedule.getStartTime(), schedule.getEndTime()));
		String physicianSubject = "CT General Hospital Appointment Details";
		String physicianBody = String.format(
				"Hi %s,\r\n" + "\r\nI apologize for the short notice and any inconvenience this may cause.\r\n"
						+ "\r\nI'm forced to cancel our appointment which was scheduled on %s at %s\r\n"
						+ "\r\nAll the information you need for your appointment is available here.\r\n"
						+ "\r\nplease visit https://localhost:8080/ or Click here. \r\n\r\n" + "Best Regards,\r\n"
						+ "CT General Hospital",
				staff.getFirstName(), getFormateTime(schedule.getStartTime(), schedule.getEndTime()),
				getFormateDate(schedule.getStartTime()));

		Mail patientMail = new Mail(patient.getEmail(), patientsubject, patientBody);
		restTemplate.postForObject(SchedulerCommonConstant.MAILSERVICEURL, patientMail, Boolean.class);

		Mail physicianMail = new Mail(patient.getEmail(), physicianSubject, physicianBody);
		restTemplate.postForObject(SchedulerCommonConstant.MAILSERVICEURL, physicianMail, Boolean.class);

	}

	@Override
	public List<Staff> getAllEmployees() {
		log.info("ScheduleServiceImp  getAllEmployess()");
		return restTemplate.getForObject(SchedulerCommonConstant.EMPLOYEES, ArrayList.class);
	}

	@Override
	public List<Patient> getAllpatients() {
		log.info("ScheduleServiceImp  getAllpatients()");
		return restTemplate.getForObject(SchedulerCommonConstant.PATIENTSERVICE, ArrayList.class);

	}

	@Override
	public List<Schedule> getAllAppointmentsByEmp(long roleId, long empId) {
		List<Schedule> appointments = new ArrayList<>();
		Role role = restTemplate.getForObject(SchedulerCommonConstant.ROLESERVICE + roleId, Role.class);
		if (role == null) {
			throw new ScheduleNotFoundException(SchedulerCommonConstant.ROLEERROR);
		}
		if (roleId == 2) {
			try {
				appointments = scheduleDao.findByphysicianId(empId);
			} catch (Exception e) {
				throw new ScheduleNotFoundException(SchedulerCommonConstant.PHYSICIANERROR);
			}
		} else if (roleId == 1 || roleId == 3) {
			appointments = scheduleDao.findAll();
		} else if (roleId == 4) {
			try {
				appointments = scheduleDao.findBypatientId(empId);
			} catch (Exception e) {
				throw new ScheduleNotFoundException(SchedulerCommonConstant.PATIENTERROR);
			}
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
		Staff staff = null;
		Patient patient = null;
		if (getslotDate(uiStartTime).isAfter(getslotDate(uiEndTime))
				|| getslotDate(timeslot.getStartDateTime()).compareTo(getslotDate(timeslot.getEndDateTime())) == 0) {
			throw new ScheduleNotFoundException("please provide valid Start date Or end date time range");
		}
		Role role = restTemplate.getForObject(SchedulerCommonConstant.ROLESERVICE + timeslot.getRoleId(), Role.class);
		if (null  == role) {
			throw new ScheduleNotFoundException(SchedulerCommonConstant.ROLEERROR);
		}
		
		TimeSlotDTO timeslotdto = new TimeSlotDTO();
		List<Schedule> timeslots = new ArrayList<>();

		if (timeslot.getRoleId() == 2) {
			staff = restTemplate.getForObject(SchedulerCommonConstant.EMPLOYEEBYID + timeslot.getPhysicianEmpId(),
					Staff.class);
			if(null != staff) {
			timeslots = scheduleDao.findByphysicianId(staff.getEmpId()).stream()
					.filter(data -> getslotDay(data.getStartTime()) == getslotDay(uiStartTime))
					.collect(Collectors.toList());
			timeslotdto.setMessage("Physician");
			}else {
				throw new ScheduleNotFoundException(SchedulerCommonConstant.PHYSICIANERROR);
			}
		} else if (timeslot.getRoleId() == 4) {
			patient = restTemplate.getForObject(SchedulerCommonConstant.PATIENTSERVICE + timeslot.getPatientId(),
					Patient.class);
			if(null != patient) {
			timeslots = scheduleDao.findBypatientId(patient.getUserId()).stream()
					.filter(data -> getslotDay(data.getStartTime()) == getslotDay(uiStartTime))
					.collect(Collectors.toList());
			timeslotdto.setMessage("Patient");
			}else {
				throw new ScheduleNotFoundException(SchedulerCommonConstant.PATIENTERROR);
			}
		}
		for (Schedule slot : timeslots) {

			slotFlag = slotValidation(uiStartTime, uiEndTime, slot);
			if (slotFlag) {
				break;
			}
		}
		timeslotdto.setTimeSlotFlag(slotFlag);
		return timeslotdto;
	}

	private boolean slotValidation(String uiStartTime, String uiEndTime, Schedule slot) {
		boolean slotFlag = false;

		if (getslotDate(slot.getStartTime()).compareTo(getslotDate(uiStartTime)) == 0
				&& getslotDate(slot.getEndTime()).compareTo(getslotDate(uiEndTime)) == 0) {
			slotFlag = true;

		} else if ((getslotDate(slot.getStartTime()).isBefore(getslotDate(uiStartTime))
				|| getslotDate(slot.getStartTime()).compareTo(getslotDate(uiStartTime)) == 0)
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

	public String getFormateDate(String sdate) {
		TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(sdate);
		Instant i11 = Instant.from(t1);
		LocalDateTime lt1 = LocalDateTime.ofInstant(i11, ZoneOffset.UTC);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return lt1.format(format);

	}

	public static String getFormateTime(String sDate, String eDate) {
		TemporalAccessor t1 = DateTimeFormatter.ISO_INSTANT.parse(sDate);
		TemporalAccessor t2 = DateTimeFormatter.ISO_INSTANT.parse(eDate);
		Instant i1 = Instant.from(t1);
		Instant i2 = Instant.from(t2);
		LocalDateTime ldt1 = LocalDateTime.ofInstant(i1, ZoneOffset.UTC);
		LocalDateTime ldt2 = LocalDateTime.ofInstant(i2, ZoneOffset.UTC);
		String time1 = ldt1.format(DateTimeFormatter.ofPattern("hh:mm a"));
		String time2 = ldt2.format(DateTimeFormatter.ofPattern("hh:mm a"));
		return (time1 + "-" + time2);
	}

	@Override
	public List<Schedule> getSortedAppointments(long roleId, long empId) {
		List<Schedule> sortedlist = new ArrayList<>();
		List<Schedule> appointments = new ArrayList<>();

		Comparator<Schedule> customComparator = (Schedule o1, Schedule o2) -> {
			if (getslotDate(o1.getStartTime()) == (getslotDate(o2.getStartTime())))
				return 0;
			else if (getslotDate(o1.getStartTime()).isAfter(getslotDate(o2.getStartTime())))
				return 1;
			else
				return -1;

		};

		if (roleId == 2) {
			appointments = scheduleDao.findByphysicianId(empId);
		} else if (roleId == 1 || roleId == 3) {
			appointments = scheduleDao.findAll();
		} else if (roleId == 4) {
			appointments = scheduleDao.findBypatientId(empId);
		}
		if (!appointments.isEmpty()) {
			sortedlist = appointments.stream().filter(data -> geFilterValue(data.getStartTime()))
					.sorted(customComparator).collect(Collectors.toList());
		}
		return sortedlist;
	}

	boolean geFilterValue(String startTime) {

		boolean sortflag = false;
		LocalDate localDate = LocalDate.now();
		if (getslotDate(startTime).toLocalDate().isEqual(localDate)
				|| getslotDate(startTime).toLocalDate().isAfter(localDate)) {
			sortflag = true;
		}

		return sortflag;
	}
	@Override
	public List<String> getAppointments(Long patientId) {



	List<Schedule> schList = scheduleDao.findAllAppointmentIds(patientId);
	List<String> list = new ArrayList<>();
	for (Schedule s : schList) {
	list.add(s.getAppointmentDate().toString());
	}



	return list;
	}



	@Override
	public Long getAppointmentIdByAppointmentDate(Date appointmentDate) {



	return scheduleDao.findIdByAppointmentDate(appointmentDate);
	}

}
