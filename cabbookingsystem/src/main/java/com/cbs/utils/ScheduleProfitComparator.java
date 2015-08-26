package com.cbs.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import com.cbs.model.Cab;
import com.cbs.model.Schedule;

public class ScheduleProfitComparator implements Comparator<Entry<String,Schedule>>{

	@Override
	public int compare(Entry<String, Schedule> o1, Entry<String, Schedule> o2) {

		if(o2.getValue().getProfit() > o1.getValue().getProfit()){
			return  1;
		}
		else if (o2.getValue().getProfit() < o1.getValue().getProfit()){
			
			return -1;
		}
		
		return 0;
	}

}
