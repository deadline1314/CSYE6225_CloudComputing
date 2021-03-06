package org.neu.ruotwang.courseservice1.configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

public class AWSSNSClient {

	public static String SNS_TOPIC_PREFIX = "arn:aws:sns:us-west-2:954846963373:";
	
	public static AmazonSNS getSNSClient() {
		return AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
	}
}
