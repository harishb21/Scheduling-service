package com.ct.scheduling.enitity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ScheduleResponseDTO {

	@Schema( example = "200")
	public String responseCode;
	@Schema( example = "Success")
	public String responseMsg;
	@Schema( example = "2021-09-02T17:28:28+05:30")
	public String timeStamp;

}
