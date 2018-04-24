package org.neu.ruotwang.courseservice1.statemachine;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CustomerServiceFunction implements RequestHandler<String, CustomerRequest> {
	
	CustomerRequest request = new CustomerRequest(1);

	@Override
	public CustomerRequest handleRequest(String input, Context context) {

		return request;
	}

}

class CustomerRequest {
	
	int customertag;
	public CustomerRequest(int customertag) {
		this.customertag = customertag;
	}	
	public int getCustomertag() {
		return customertag;
	}
	public void setCustomertag(int customertag) {
		this.customertag = customertag;
	}
}
