package com.ct.scheduling.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ct.scheduling.enitity.Schedule;

@Repository
public interface ScheduleRespository extends JpaRepository<Schedule, Long>{

	List<Schedule> findByphysicianId(long empId);
	
	List<Schedule> findBypatientId(long patientId);

	@Query("FROM Schedule WHERE patientId=:patientId order by startTime desc ")
	List<Schedule> findAllAppointmentIds(Long patientId);
	
	@Query("select id FROM Schedule WHERE startTime=:startTime")
	Long findIdByAppointmentDate(String startTime);

	

}
