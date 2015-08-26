/**
 * 
 */
package com.cbs.process;

import java.util.List;
import com.cbs.model.BookingRequest;
import com.cbs.model.Cab;

/**
 * @author Nishant
 *
 */
public interface ProcessRequest {

	public void processRequest(BookingRequest request,List<Cab> availableCabsList);
}
