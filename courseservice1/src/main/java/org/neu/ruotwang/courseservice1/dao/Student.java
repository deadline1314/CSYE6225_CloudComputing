package org.neu.ruotwang.courseservice1.dao;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"studentId"})
@DynamoDBTable(tableName = "STUDNET_DEV")
public class Student {

	@DynamoDBHashKey(attributeName = "studentId")
	private String studentId;
	
	@DynamoDBAttribute(attributeName = "studentName")
	private String studentName;
	
	@DynamoDBAttribute(attributeName = "studentEmail")
	private String studentEmail;
	
	@DynamoDBAttribute(attributeName = "imagePath")
	private String imagePath;
	
	@DynamoDBAttribute(attributeName = "courseIds")
	private List<String> courseIds;
	
	@DynamoDBAttribute(attributeName = "programId")
	private String programId;
	
	@DynamoDBVersionAttribute(attributeName = "versionNumber")
	private Long versionNumber;

}
