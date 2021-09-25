package com.ct.scheduling.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ScheduleNotFoundException extends RuntimeException {

	public ScheduleNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ScheduleNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ScheduleNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public ScheduleNotFoundException(String message) {
		super(message);
	}

}
