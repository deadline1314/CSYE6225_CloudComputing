package org.neu.ruotwang.courseservice1.configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

public class AWSDynamoDBClient {

	/**
	 * API to retrieve a specific DynamoDBMapper: Devo or Prod
	 * TODO: env setting
	 * */
	public static DynamoDBMapper getDynamoDBMapper() {
		return getDynamoDBMapperDevo();
		// return getDynamoDBMapperProd();
	}
	
	/**
	 * initialize dynamoDBMapper in Prod environment
	 * */
	private static AmazonDynamoDB getDynamoDBConfigProd() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withRegion(Regions.US_WEST_2)
				.build();
		return client;
	}
	private static DynamoDBMapper getDynamoDBMapperProd() {
		return new DynamoDBMapper(getDynamoDBConfigProd());
	}
	
	/**
	 * initialize dynamoDBMapper in Devo environment
	 * */
	private static AmazonDynamoDB getDynamoDBConfigDevo() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
				.build();
		return client;
	}
	private static DynamoDBMapper getDynamoDBMapperDevo() {
		return new DynamoDBMapper(getDynamoDBConfigDevo());
	}
}
