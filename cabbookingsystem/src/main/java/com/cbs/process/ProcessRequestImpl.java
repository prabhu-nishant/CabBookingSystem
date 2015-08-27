/**
 * 
 */
package com.cbs.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cbs.constants.Constants;
import com.cbs.model.BookingRequest;
import com.cbs.model.Cab;
import com.cbs.model.Schedule;
import com.cbs.utils.ScheduleProfitComparator;
import com.cbs.utils.TimeGenerator;

/**
 * @author Nishant
 *
 */
public class ProcessRequestImpl implements ProcessRequest{

	public void processRequest(BookingRequest request, List<Cab> availableCabsList) {
			
		if(!TimeGenerator.checkSameDayRequest(request.getPickUpTime())){
			
			request.setStatus(Constants.CAB_STATUS_CANNOT_BE_BOOKED);
			request.setStatusDescription("Only same day requests are served");
			return;
		}
		else if(TimeGenerator.getCurrentTime().after(request.getPickUpTime())){
			
			request.setStatus(Constants.CAB_STATUS_CANNOT_BE_BOOKED);
			request.setStatusDescription("Request cannot be completed as it is expired");
			return;
			
		}
		
		Map<String,Schedule> finalScheduleMap = new HashMap<String,Schedule>();
		getCab(request, availableCabsList, finalScheduleMap);
		
		List <Entry<String,Schedule>> list = new ArrayList<Entry<String,Schedule>>(finalScheduleMap.entrySet());
		Collections.sort(list,new ScheduleProfitComparator());
		
		if(list.size()!=0){
			
			Entry<String,Schedule> entry = list.get(0);
			
			Cab temp = null;
			for(Cab cab:availableCabsList){
				
				if(cab.getCabNo().equals(entry.getKey())){
					
					temp = cab;
					break;
				}
			}
			
			entry.getValue().setStatus(Constants.SCHEDULE_STATUS_CONFIRMED);
			temp.getScheduleList().add(entry.getValue());
			Collections.sort(temp.getScheduleList());

			request.setCabNo(entry.getKey());
			request.setStatus(Constants.CAB_STATUS_BOOKED);
			request.setStatusDescription("Confirmed");
		}
		else{
			request.setStatus(Constants.CAB_STATUS_IS_PENDING);
			request.setStatusDescription("Pending");
		}
		
	}

	private void getCab(BookingRequest request, List<Cab> availableCabsList,Map<String, Schedule> finalScheduleMap) {
		
		for(Cab cab:availableCabsList){
			
			Schedule schedule = null;
			Schedule prevschedule = null;
			Schedule nextSchedule = null;
			
			List<Schedule> scheduleList = cab.getScheduleList();
			
			if(scheduleList.size()!=0){
				
				Iterator<Schedule> scheduleIterator = scheduleList.listIterator();
				while(scheduleIterator.hasNext()){
				
					prevschedule = scheduleIterator.next();
					
					if(scheduleIterator.hasNext()){
						
						nextSchedule = scheduleIterator.next();
					}
					
					if(prevschedule!=null  && nextSchedule == null){
						
						if(prevschedule.getDropTime().before(request.getPickUpTime())){
							
							schedule = getCabSchedule(prevschedule.getDropArea(),prevschedule,request);
							
						}
						else if(prevschedule.getDropTime().after(request.getPickUpTime())){
							
							schedule = getCabSchedule(cab.getInitialLocation(),request);
							verifyScheduleCanBeIncluded(schedule,prevschedule);
						}
					}
					else if (prevschedule!=null && prevschedule.getDropTime().before(request.getPickUpTime()) && nextSchedule != null ){
					
						schedule = getCabSchedule(prevschedule.getDropArea(),prevschedule,request);
						verifyScheduleCanBeIncluded(schedule,nextSchedule);
						
					}
				}
			
			}
			else{
				
				schedule = getCabSchedule(cab.getInitialLocation(),request);
				
			}
			
			if(schedule!=null){
				
				finalScheduleMap.put(cab.getCabNo(),schedule);
			}
		}
	}

	private void verifyScheduleCanBeIncluded(Schedule schedule,Schedule nextschedule) {
		
		if(schedule!=null){
			
			int distance = Math.abs(nextschedule.getPickUpArea()-schedule.getDropArea());
			
			Date futureReportingTime = TimeGenerator.getDifferentTime(schedule.getDropTime(),distance * Constants.TIME_TAKEN_TO_TRAVEL_1_KM);
			
			int timeDiff = TimeGenerator.getTimeDifference(nextschedule.getPickUpTime(),futureReportingTime); 
			
			if(timeDiff < 15){
				
				schedule = null;
			}
			
		}
		
	}

	private Schedule getCabSchedule(int location,BookingRequest request) {

		return getCabSchedule(location,null,request);
	}
	
	private Schedule getCabSchedule(int location,Schedule prevSchedule,BookingRequest request) {
		
		Schedule schedule = null;
		Date futureReportingTime = null;
		
		int distance = Math.abs(location - request.getPickUpArea());
	 	
		if(prevSchedule!=null){
			
			futureReportingTime = TimeGenerator.getDifferentTime(prevSchedule.getDropTime(),distance * Constants.TIME_TAKEN_TO_TRAVEL_1_KM) ;
		}
		else {
			futureReportingTime = TimeGenerator.getDifferentTime(distance * Constants.TIME_TAKEN_TO_TRAVEL_1_KM) ;
		}
		
		int timeDiff = TimeGenerator.getTimeDifference(request.getPickUpTime(),futureReportingTime) ;
		
		if(TimeGenerator.checkSameDayRequest(futureReportingTime) && timeDiff >= Constants.CAB_REPORTING_THRESHOLD){
			
			int costIncurred = (distance + Math.abs(request.getDropArea()-request.getPickUpArea())) * 5 ;
			int charge = Math.abs(request.getDropArea()-request.getPickUpArea()) * 10;
			double profit = charge - costIncurred;
			
			if(profit > 0){
				
				profit/=costIncurred;
				profit*=100;
			}
			
			if(profit >= Constants.MIN_PROFIT_MARGIN){
				
				schedule = new Schedule();
				schedule.setBookingId(request.getBookingId());
				schedule.setPickUpArea(request.getPickUpArea());
				schedule.setDropArea(request.getDropArea());
				schedule.setPickUpTime(request.getPickUpTime());
				
				Date dropTime = TimeGenerator.getDifferentTime(request.getPickUpTime(),Math.abs(request.getPickUpArea()-request.getDropArea())*2);
				schedule.setDropTime(dropTime);
				schedule.setStatus(Constants.SCHEDULE_STATUS_NOT_CONFIRMED);
				schedule.setProfit(profit);
			}
		}
				
		return schedule;
	}
}
