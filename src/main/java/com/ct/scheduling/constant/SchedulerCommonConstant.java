package com.ct.scheduling.constant;

public final class SchedulerCommonConstant {

	public static final String MAILSERVICEURL ="http://localhost:8082/mail/send/";
	public static final String PATIENTSERVICE= "http://localhost:8082/users/patients/";
	public static final String EMPLOYEEBYID = "http://localhost:8082/users/employees/employeeId/";
	public static final String EMPLOYEES="http://localhost:8082/users/employees/";
	public static final String PHYSICIANERROR="Given PhysicianId is not found. please provide valid PhysicianId";
	public static final String PATIENTERROR="Given PatientId is not found. please provide valid PatientId";
	public static final String ROLESERVICE ="http://localhost:8082/roles/";
	public static final String ROLEERROR = "Given Role Id not found. please provide valid RoleId";
	private SchedulerCommonConstant() {
		
	}
}
