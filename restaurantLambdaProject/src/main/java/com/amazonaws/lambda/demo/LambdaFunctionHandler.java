package com.amazonaws.lambda.demo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class LambdaFunctionHandler implements RequestHandler<DynamodbEvent, String> {

	private AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard()
			.withRegion(Regions.US_WEST_2).build();
	private static String RESTAURANT_SNS_TOPIC = "arn:aws:sns:us-west-2:954846963373:LetTheWarBegan";
	
    @Override
    public String handleRequest(DynamodbEvent input, Context context) {    
    	String output = "";
    	// Read DDB Records
    	for (DynamodbStreamRecord record : input.getRecords()) {
    		if (record == null || record.getEventName().equals("REMOVE")) {
    			continue;
    		}
        	// Business logic to decide to send a notification
        	String inputString = String.valueOf(record.getDynamodb().getNewImage());
        	context.getLogger().log("Input: " + inputString);
        	// Send Notification(send email)
        	output = "Hello, " + inputString + "!";
        	String outputBody = output + " No Dota, no life.";
        	sendEmailNotification(output, outputBody);
    	}
    	return output;
    }
    
    private void sendEmailNotification(final String subject, final String message) {
    	// Message object
    	PublishRequest publishRequest = new PublishRequest(RESTAURANT_SNS_TOPIC, message, subject);
    	// Call Client.publishMessage
    	SNS_CLIENT.publish(publishRequest);
    }
    
    private String getRestaurantName(String record) {
    	String restaurantName = "";
    	// JSON parser
    	return restaurantName;
    }
    
    private String getLocation(String record) {
    	String location = "";
    	// JSON parser
    	return location;
    }

}
