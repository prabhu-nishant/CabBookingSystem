package com.cbs.process;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.cbs.model.BookingRequest;
import com.cbs.model.Cab;
import com.cbs.model.Schedule;

public class ProcessRequestImplTest {

	ProcessRequestImpl systemUnderTest;
	
	@Mock
	Calendar c;
	
	@Before
	public void setUp(){
		
		c = Mockito.mock(Calendar.class);
		systemUnderTest = new ProcessRequestImpl();
	}
	
	@Test
	public void testOldRequest() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR001");
		request.setPickUpArea(100025);
		request.setDropArea(100036);
		
		Calendar cal = getCalendar();
		cal.add(Calendar.DATE, -1);
		request.setPickUpTime(cal.getTime());
		
		mockCalendar();
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
		Calendar cal = getCalendar();
		cal.add(Calendar.MINUTE,-55);
		request.setPickUpTime(cal.getTime());
		
		mockCalendar();
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
		
		Calendar cal = getCalendar();
		cal.add(Calendar.MINUTE,27);
		
		request.setPickUpTime(cal.getTime());
		
		mockCalendar();		
		systemUnderTest.processRequest(request, getCabList());
		Assert.assertEquals("Request Status invalid","Booked",request.getStatus() );
		Assert.assertEquals("Request Status description invalid ","Confirmed",request.getStatusDescription() );
		Assert.assertEquals("Cab No invalid ","DL01HB001",request.getCabNo() );
		
	}
	
	@Test
	public void testRequestWithSinglePriorSchedule() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR001");
		request.setPickUpArea(100022);
		request.setDropArea(100027);
		
		Calendar cal = getCalendar();
		cal.add(Calendar.MINUTE,50);
		request.setPickUpTime(cal.getTime());
		
		mockCalendar();
		systemUnderTest.processRequest(request, getCabListWithPriorSchedule());
		Assert.assertEquals("Request Status invalid","Booked",request.getStatus());
		Assert.assertEquals("Request Status description invalid ","Confirmed" ,request.getStatusDescription());
		Assert.assertEquals("Cab No invalid ","DL01HB001",request.getCabNo() );
		
	}
	
	@Test
	public void testRequestWithMultiplePriorSchedule() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR006");
		request.setPickUpArea(100050);
		request.setDropArea(100057);
		Calendar cal = getCalendar();
		cal.add(Calendar.MINUTE,100);
		request.setPickUpTime(cal.getTime());
		
		mockCalendar();
		systemUnderTest.processRequest(request, getCabListWithPriorSchedule());
		Assert.assertEquals("Request Status invalid","Booked",request.getStatus());
		Assert.assertEquals("Request Status description invalid ","Confirmed" ,request.getStatusDescription());
		Assert.assertEquals("Cab No invalid ","DL01HB002",request.getCabNo() );
		
	}
	
	@Test
	public void testRequestWithSingleFutureSchedule() {
		
		BookingRequest request = new BookingRequest();
		request.setBookingId("BR006");
		request.setPickUpArea(100065);
		request.setDropArea(100069);
		Calendar cal = getCalendar();
		cal.add(Calendar.MINUTE,100);
		request.setPickUpTime(cal.getTime());
		
		mockCalendar();
		systemUnderTest.processRequest(request, getCabListWithPriorSchedule());
		Assert.assertEquals("Request Status invalid","Booked",request.getStatus());
		Assert.assertEquals("Request Status description invalid ","Confirmed" ,request.getStatusDescription());
		Assert.assertEquals("Cab No invalid ","DL01HB003",request.getCabNo() );
		
	}
	
	
	private static Calendar getCalendar(){
		
		Calendar c = Calendar.getInstance();
		if(c.get(Calendar.HOUR_OF_DAY) > 20){
			c.add(Calendar.HOUR_OF_DAY, -10);
		}
		else if(c.get(Calendar.HOUR_OF_DAY) < 4){
			c.add(Calendar.HOUR_OF_DAY, 10);
		}
		
		return c;
		
	}
	
	private void mockCalendar() {
		Mockito.when(c.getTime()).thenAnswer(new Answer() {
			 public Object answer(InvocationOnMock invocation) {   
			     return getCalendar().getTime();
			 }
		});
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
		schedule.setBookingId("BR005");
		schedule.setPickUpArea(100020);
		schedule.setDropArea(100021);
		Calendar cal = getCalendar();
		cal.add(Calendar.MINUTE,15);
		schedule.setPickUpTime(cal.getTime());
		cal.add(Calendar.MINUTE, 17);
		schedule.setDropTime(cal.getTime());
		
		Cab cab1 = new Cab();
		cab1.setCabNo("DL01HB001");
		cab1.setInitialLocation(100020);
		
		List<Schedule> schedulelist = new ArrayList<Schedule>();
		schedulelist.add(schedule);
		cab1.setScheduleList(schedulelist);
		
		schedulelist = new ArrayList<Schedule>();
		
		schedule = new Schedule();
		schedule.setBookingId("BR005");
		schedule.setPickUpArea(100040);
		schedule.setDropArea(100047);
		cal = getCalendar();
		cal.add(Calendar.MINUTE,30);
		schedule.setPickUpTime(cal.getTime());
		cal.add(Calendar.MINUTE, 44);
		schedule.setDropTime(cal.getTime());
		schedulelist.add(schedule);
		
		schedule = new Schedule();
		schedule.setBookingId("BR0010");
		schedule.setPickUpArea(100060);
		schedule.setDropArea(100065);
		cal = getCalendar();
		cal.add(Calendar.MINUTE,200);
		schedule.setPickUpTime(cal.getTime());
		cal.add(Calendar.MINUTE, 258);
		schedule.setDropTime(cal.getTime());
		schedulelist.add(schedule);
		
		Cab cab2 = new Cab();
		cab2.setCabNo("DL01HB002");
		cab2.setInitialLocation(100040);
		cab2.setScheduleList(new ArrayList<Schedule>());
		cab2.setScheduleList(schedulelist);
		
		
		schedulelist = new ArrayList<Schedule>();
		
		schedule = new Schedule();
		schedule.setBookingId("BR011");
		schedule.setPickUpArea(100070);
		schedule.setDropArea(100075);
		cal = getCalendar();
		cal.add(Calendar.MINUTE,200);
		schedule.setPickUpTime(cal.getTime());
		cal.add(Calendar.MINUTE, 210);
		schedule.setDropTime(cal.getTime());
		schedulelist.add(schedule);
		
		Cab cab3 = new Cab();
		cab3.setCabNo("DL01HB003");
		cab3.setInitialLocation(100065);
		cab3.setScheduleList(schedulelist);
		
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

	@After
	public void tearDown(){
		
	}
	
}
