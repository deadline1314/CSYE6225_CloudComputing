package org.neu.ruotwang.courseservice1.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "PROGRAM_DEV")
public class Program {
	@DynamoDBHashKey(attributeName = "programId")
	private String programId;
	
	@DynamoDBAttribute(attributeName = "programName")
	private String programName;
	
	@DynamoDBVersionAttribute(attributeName = "versionNumber")
	private Long versionNumber;
}
