package com.cbs.constants;

/**
 * @author Nishant
 *
 */
public class Constants {

	/*
	 * This constant value represents time taken by the cab to travel 1 KM in minutes.
	 */
	public static final int TIME_TAKEN_TO_TRAVEL_1_KM = 2;
	
	
	/*
	 * This constant value represents distance between two adjacent sectors in KM.
	 */
	public static final int DISTANCE_BETWEEN_TWO_SECTORS_IN_KM = 2;
	
	/*
	 * This constant value represents cost incurred by the company for a cab in rupees on per km basis.
	 */
	public static final int COST_INCURRED_BY_COMPANY_PER_KM = 5;
	
	/*
	 * This constant value represents fare charged by the company to it's customer in rupees on per km basis.
	 */
	public static final int CAB_FARE_CHARGE_PER_KM = 10;
	
	/*
	 * This value represents threshold value in minutes for a cab to reach before the pick up time.
	 */
	public static final int CAB_REPORTING_THRESHOLD = 15;
	
	/*
	 * This value represents compaany's minimum profit margin in percentage for each request.
	 */
	public static final int MIN_PROFIT_MARGIN = 20;
	
	public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm aa";
	
	public static final String CAB_STATUS_BOOKED = "Booked";
	
	public static final String CAB_STATUS_IS_PENDING = "Pending";
	
	public static final String CAB_STATUS_CANNOT_BE_BOOKED = "-";
	
	public static final String SCHEDULE_STATUS_CONFIRMED = "Confirmed";
	
	public static final String SCHEDULE_STATUS_NOT_CONFIRMED = "Not Confirmed";
		
	
}
