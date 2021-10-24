package com.ct.scheduling.enitity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {

	private String responseCode;
	private String responseMsg;
	private String timeStamp;

}
