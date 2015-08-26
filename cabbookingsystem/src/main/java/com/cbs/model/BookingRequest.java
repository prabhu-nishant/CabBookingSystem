package com.cbs.model;

import java.util.Date;



/**
 * @author Nishant
 * 
 * This class represents booking request received from customers.
 */
public class BookingRequest {

	private String bookingId;
	
	private int pickUpArea;
	
	private int dropArea;
	
	private Date pickUpTime;
	
	private String cabNo;
	
	private String status;
	
	private String statusDescription;

	/**
	 * @return the bookingId
	 */
	public String getBookingId() {
		return bookingId;
	}

	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * @return the pickUpArea
	 */
	public int getPickUpArea() {
		return pickUpArea;
	}

	/**
	 * @param pickUpArea the pickUpArea to set
	 */
	public void setPickUpArea(int pickUpArea) {
		this.pickUpArea = pickUpArea;
	}

	/**
	 * @return the dropArea
	 */
	public int getDropArea() {
		return dropArea;
	}

	/**
	 * @param dropArea the dropArea to set
	 */
	public void setDropArea(int dropArea) {
		this.dropArea = dropArea;
	}

	/**
	 * @return the pickUpTime
	 */
	public Date getPickUpTime() {
		return pickUpTime;
	}

	/**
	 * @param pickUpTime the pickUpTime to set
	 */
	public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	/**
	 * @return the cabNo
	 */
	public String getCabNo() {
		return cabNo;
	}

	/**
	 * @param cabNo the cabNo to set
	 */
	public void setCabNo(String cabNo) {
		this.cabNo = cabNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String toString(){
		
		StringBuilder str = new StringBuilder();
		str.append("Booking Request Id :"+bookingId);
		str.append("\nPick Up Area :"+pickUpArea);
		str.append("\nDrop Area :"+dropArea);
		str.append("\nPick up Time :"+pickUpTime.toString());
		str.append("\nCab No :"+cabNo);
		str.append("\nStatus :"+status);
		str.append("\nStatus Description :"+statusDescription);
		str.append("\n----------------------------");
				
		return str.toString();
		
		
	}
}
