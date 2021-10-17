package com.ct.scheduling.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ct.scheduling.enitity.Schedule;

@Repository
public interface ScheduleRespository extends JpaRepository<Schedule, Long>{

	List<Schedule> findByphysicianId(long empId);

	

}
