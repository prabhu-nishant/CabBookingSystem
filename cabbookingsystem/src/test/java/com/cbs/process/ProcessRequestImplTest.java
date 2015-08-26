package com.cbs.process;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.cbs.model.BookingRequest;
import com.cbs.model.Cab;
import com.cbs.model.Schedule;
import com.cbs.utils.TimeGenerator;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TimeGenerator.class)
public class ProcessRequestImplTest {

	ProcessRequestImpl systemUnderTest;

	@Before
	public void setUp(){
		
		PowerMockito.mockStatic(TimeGenerator.class);
		systemUnderTest = new ProcessRequestImpl();
	}
	
	@Test
	public void testProcessRequest() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR001");
		request.setPickUpArea(100025);
		request.setDropArea(100036);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		request.setPickUpTime(cal.getTime());
		
		systemUnderTest.processRequest(request, getCabList());
		Assert.assertEquals("Request Status invalid","-",request.getStatus() );
		Assert.assertEquals("Request Status description invalid ","Only same day requests are served",request.getStatusDescription() );
		
	}
	
	@Test
	public void testExpiredRequest() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR001");
		request.setPickUpArea(100025);
		request.setDropArea(100036);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1);
		
		request.setPickUpTime(cal.getTime());
		
		systemUnderTest.processRequest(request, getCabList());
		Assert.assertEquals("Request Status invalid","-",request.getStatus() );
		Assert.assertEquals("Request Status description invalid ","Request cannot be completed as it is expired" ,request.getStatusDescription());
		
	}
	
	@Test
	public void testRequestWithNoPriorSchedule() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR001");
		request.setPickUpArea(100025);
		request.setDropArea(100036);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE,27);
		
		request.setPickUpTime(cal.getTime());
		
		systemUnderTest.processRequest(request, getCabList());
		Assert.assertEquals("Request Status invalid","Booked",request.getStatus() );
		Assert.assertEquals("Request Status description invalid ","Confirmed",request.getStatusDescription() );
		Assert.assertEquals("Cab No invalid ","DL01HB001",request.getCabNo() );
		
	}
	
	
	@Test
	public void testRequestWithPriorSchedule() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR001");
		request.setPickUpArea(100022);
		request.setDropArea(100027);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE,50);
		
		request.setPickUpTime(cal.getTime());
		
		systemUnderTest.processRequest(request, getCabListWithPriorSchedule());
		Assert.assertEquals("Request Status invalid","Booked",request.getStatus());
		Assert.assertEquals("Request Status description invalid ","Confirmed" ,request.getStatusDescription());
		Assert.assertEquals("Cab No invalid ","DL01HB001",request.getCabNo() );
		
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
	
private static List<Cab> getCabListWithPriorSchedule() {
		
		List<Cab> list = new ArrayList<Cab>();
		
		Schedule schedule = new Schedule();
		schedule.setBookingId("BR004");
		schedule.setPickUpArea(100020);
		schedule.setDropArea(100021);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE,15);
		
		schedule.setPickUpTime(cal.getTime());
		
		cal.add(Calendar.MINUTE, 2);
		schedule.setDropTime(cal.getTime());
		
		Cab cab1 = new Cab();
		cab1.setCabNo("DL01HB001");
		cab1.setInitialLocation(100020);
		
		List<Schedule> schedulelist = new ArrayList<Schedule>();
		schedulelist.add(schedule);
		cab1.setScheduleList(schedulelist);
		
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
