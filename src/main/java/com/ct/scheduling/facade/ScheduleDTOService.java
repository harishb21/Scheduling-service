package com.ct.scheduling.facade;


import com.ct.scheduling.enitity.Schedule;


public interface ScheduleDTOService {

	public Schedule convertToEntity (ScheduleDTO scheduleDTO);
    
    public ScheduleDTO convertToDTO (Schedule schedule);
    
}
