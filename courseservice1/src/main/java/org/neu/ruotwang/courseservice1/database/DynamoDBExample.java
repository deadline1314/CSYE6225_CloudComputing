package org.neu.ruotwang.courseservice1.database;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

public class DynamoDBExample { 
	static AmazonDynamoDB dynamoDb;
	
	/**
	 * Method creates a DynamoDb object, and makes it available via the client
	 * @exception	Exception
	 * @return
	 * 		
	 * @throws Exception
	 */
	private static void init() throws Exception { 
		ProfileCredentialsProvider credentialsProvider = 
					new ProfileCredentialsProvider();
		
		credentialsProvider.getCredentials();
		
		dynamoDb = AmazonDynamoDBClientBuilder
						.standard()
						.withCredentials(credentialsProvider)
						.withRegion("us-west-2")
						.build();
		}
	
	public static void main(String[] args) throws Exception {
		init();
		String tableName = "resturant-test-table";
		
		CreateTableRequest createTableRequest = new CreateTableRequest()
				.withTableName(tableName)
				.withKeySchema(
						new KeySchemaElement()
						.withAttributeName("name")
						.withKeyType(KeyType.HASH))
				.withAttributeDefinitions(
						new AttributeDefinition()
						.withAttributeName("name")
						.withAttributeType(ScalarAttributeType.S))
				.withProvisionedThroughput(
						new ProvisionedThroughput()
							.withReadCapacityUnits(3L)
							.withWriteCapacityUnits(3L));
		
		TableUtils.createTableIfNotExists(dynamoDb, createTableRequest);
		TableUtils.waitUntilActive(dynamoDb, tableName);
		
		Map<String, AttributeValue> item = 
				new HashMap<String, AttributeValue>();
		item.put("name", 
				new AttributeValue().withS("Starbucks"));
		item.put("address", new AttributeValue().withS("500-kjohn-st"));
		
		
		PutItemRequest putItemRequest = new PutItemRequest(
				tableName, item);
		System.out.println("PutItemRequest : " + putItemRequest);
		PutItemResult putItemResult = dynamoDb.putItem(putItemRequest);
		System.out.println("PutItemResult: " + putItemResult);
		
				
		
	}
}