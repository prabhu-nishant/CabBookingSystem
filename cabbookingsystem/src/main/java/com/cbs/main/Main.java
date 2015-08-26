package com.cbs.main;

import java.util.ArrayList;
import java.util.List;

import com.cbs.model.BookingRequest;
import com.cbs.model.Cab;
import com.cbs.model.Schedule;
import com.cbs.process.ProcessRequest;
import com.cbs.process.ProcessRequestImpl;
import com.cbs.utils.TimeGenerator;

/**
 * @author Nishant
 *
 */
public class Main {

	public static void main(String[] args) {
		try {
			ProcessRequest requestHandler = new ProcessRequestImpl();
			List<Cab> cabList = getCabList();
			List<BookingRequest> requestList = getBookingRequestList();
			
			for(BookingRequest request:requestList){
				
				requestHandler.processRequest(request, cabList);
				System.out.println(request.toString());
			}
			
			for(Cab request:cabList){
				
				System.out.println(request.toString());
			}
									
		} catch (Exception e) {
			
			System.out.println("Exception while processing "+e);
			e.printStackTrace();
		}
	}

	private static List<BookingRequest> getBookingRequestList() {
		
		List<BookingRequest> list = new ArrayList<BookingRequest>();
		
		BookingRequest req1 = new BookingRequest();
		req1.setBookingId("BR001");
		req1.setPickUpArea(100025);
		req1.setDropArea(100036);
		req1.setPickUpTime(TimeGenerator.getDate("26/08/2015 10:45 pm"));
				
		BookingRequest req2 = new BookingRequest();
		req2.setBookingId("BR002");
		req2.setPickUpArea(100056);
		req2.setDropArea(100042);
		req2.setPickUpTime(TimeGenerator.getDate("26/08/2015 10:00 pm"));
		
		BookingRequest req3 = new BookingRequest();
		req3.setBookingId("BR003");
		req3.setPickUpArea(100044);
		req3.setDropArea(100056);
		req3.setPickUpTime(TimeGenerator.getDate("26/08/2015 11:00 pm"));
		
		BookingRequest req4 = new BookingRequest();
		req4.setBookingId("BR004");
		req4.setPickUpArea(100028);
		req4.setDropArea(100058);
		req4.setPickUpTime(TimeGenerator.getDate("26/08/2015 11:30 pm"));
		
		
		list.add(req1);
		list.add(req2);
		list.add(req3);
		list.add(req4);
		
		return list;
	}

	private static List<Cab> getCabList() {
		
		List<Cab> list = new ArrayList<Cab>();
		
		Cab cab1 = new Cab();
		cab1.setCabNo("DL01HB001");
		cab1.setInitialLocation(100020);
		cab1.setScheduleList(new ArrayList<Schedule>());
		
		Cab cab2 = new Cab();
		cab2.setCabNo("DL01HB002");
		cab2.setInitialLocation(100040);
		cab2.setScheduleList(new ArrayList<Schedule>());
		
		Cab cab3 = new Cab();
		cab3.setCabNo("DL01HB003");
		cab3.setInitialLocation(100060);
		cab3.setScheduleList(new ArrayList<Schedule>());
		
		Cab cab4 = new Cab();
		cab4.setCabNo("DL01HB004");
		cab4.setInitialLocation(100080);
		cab4.setScheduleList(new ArrayList<Schedule>());
		
		list.add(cab1);
		list.add(cab2);
		list.add(cab3);
		list.add(cab4);
		
		return list;
	}

}
