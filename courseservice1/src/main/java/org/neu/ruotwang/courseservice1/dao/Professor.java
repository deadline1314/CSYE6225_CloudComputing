package org.neu.ruotwang.courseservice1.dao;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "PROFESSOR_DEV")
public class Professor {
	@DynamoDBHashKey(attributeName = "professorId")
	private String professorId;
	
	@DynamoDBAttribute(attributeName = "professorName")
	private String professorName;
	
	@DynamoDBAttribute(attributeName = "courseIdList")
	private List<String> courseIdList;
	
	@DynamoDBVersionAttribute(attributeName = "versionNumber")
	private Long versionNumber;
}
