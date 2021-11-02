package com.ct.scheduling.facade;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.scheduling.enitity.Schedule;
@Service
public class ScheduleDTOimpl implements ScheduleDTOService{

	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public Schedule convertToEntity(ScheduleDTO scheduleDTO) {
		return  modelMapper.map(scheduleDTO, Schedule.class);
        
    }
	@Override
	public ScheduleDTO convertToDTO(Schedule schedule) {
		return modelMapper.map(schedule, ScheduleDTO.class);
     
	}

}
