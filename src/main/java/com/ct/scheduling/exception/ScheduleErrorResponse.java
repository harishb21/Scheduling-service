package com.ct.scheduling.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleErrorResponse {

	private int status;
	private String response;
	private long timestamp;
}
