package com.cbs.model;

import java.util.List;

/**
 * @author Nishant
 *
 * This class represents information related to a CAB.
 */
public class Cab {

	private String cabNo;
	private int initialLocation;
	private List<Schedule> scheduleList; 
	
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
	
	
	
	public int getInitialLocation() {
		return initialLocation;
	}

	public void setInitialLocation(int initialLocation) {
		this.initialLocation = initialLocation;
	}

	public List<Schedule> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}
	
	public String toString(){
		
		StringBuilder str = new StringBuilder();
				
		str.append("\nCab No :"+cabNo);
		str.append("\ninitialLocation :"+initialLocation);
		str.append("\n");
		str.append("\nSchedule :");
		
		for(Schedule schedule:scheduleList){
			
			str.append("\n\nbookingId :"+schedule.getBookingId());
			str.append("\npickUpArea :"+schedule.getPickUpArea());
			str.append("\ndropArea :"+schedule.getDropArea());
			str.append("\npickUpTime :"+schedule.getPickUpTime());
			str.append("\ndropTime :"+schedule.getDropTime());
			str.append("\nstatus :"+schedule.getStatus());
			str.append("\nprofit :"+schedule.getProfit());
		}
		
		str.append("\n-----------------------------------------------------------------------------");
				
		return str.toString();
		
		
	}
	
	
}
