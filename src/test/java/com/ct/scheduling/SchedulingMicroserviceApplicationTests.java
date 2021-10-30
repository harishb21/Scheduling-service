package com.ct.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.ct.scheduling.enitity.Schedule;
import com.ct.scheduling.facade.ScheduleDTO;

@SpringBootTest
class SchedulingMicroserviceApplicationTests {

	private ModelMapper modelMapper = new ModelMapper();


	 @Test
	     void testModelMapperToEntity() {
	        ScheduleDTO dto = new ScheduleDTO(
	        				5, 
	        				"Therapy - Robert", 
	        				"Therapy - Robert description",
	        				2498,
	        				2,
	        				"2021-09-30T04:30:00.000Z",
	        				"2021-09-30T05:00:00.000Z",
	        				"no reason",
	        				new Date("12-10-2021"));


	        Schedule entity = modelMapper.map(dto, Schedule.class);
	        assertEquals(entity.getStartTime(), dto.getStartTime());
	        assertEquals(entity.getEndTime(), dto.getEndTime());
	        assertEquals(entity.getPatientId(), dto.getPatientId());
	        assertEquals(entity.getPhysicianId(), dto.getPhysicianId());
	    }
	 
	 @Test
	    void testModelMapperToDto() {
		 Schedule entity = new Schedule(
				 5, 
 				"Therapy - Robert", 
 				"Therapy - Robert description",
 				2498,
 				2,
 				"2021-09-30T04:30:00.000Z",
 				"2021-09-30T05:00:00.000Z",
 				"no reason",
 				new Date("12-10-2021"));
				

		 ScheduleDTO dto = modelMapper.map(entity, ScheduleDTO.class);

		 assertEquals(entity.getStartTime(), dto.getStartTime());
	        assertEquals(entity.getEndTime(), dto.getEndTime());
	        assertEquals(entity.getPatientId(), dto.getPatientId());
	        assertEquals(entity.getPhysicianId(), dto.getPhysicianId());
	    }
}
