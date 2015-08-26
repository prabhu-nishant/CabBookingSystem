package com.cbs.model;

import java.util.Calendar;
import java.util.Date;

public class Schedule implements Comparable<Schedule>{

	private String bookingId;
	
	private int pickUpArea;
	
	private int dropArea;
	
	private Date pickUpTime;
	
	private Date dropTime;
	
	private String status;
	
	private double profit;

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public int getPickUpArea() {
		return pickUpArea;
	}

	public void setPickUpArea(int pickUpArea) {
		this.pickUpArea = pickUpArea;
	}

	public int getDropArea() {
		return dropArea;
	}

	public void setDropArea(int dropArea) {
		this.dropArea = dropArea;
	}

	public Date getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public Date getDropTime() {
		return dropTime;
	}

	public void setDropTime(Date dropTime) {
		this.dropTime = dropTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	@Override
	public int compareTo(Schedule s) {

		Calendar cal1 = Calendar.getInstance();
    	Calendar cal2 = Calendar.getInstance();
    	cal1.setTime(this.getPickUpTime());
    	cal2.setTime(s.getPickUpTime());
    	
    	if(cal1.after(cal2)){
    		return 1;
    	}
    	
    	if(cal1.before(cal2)){
    		return -1;
    	}
    	
    	return 0;
    	
	}
	
}
