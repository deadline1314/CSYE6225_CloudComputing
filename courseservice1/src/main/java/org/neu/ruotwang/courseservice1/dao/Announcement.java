package org.neu.ruotwang.courseservice1.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "ANNOUNCEMENT_DEV")
public class Announcement {
	@DynamoDBHashKey(attributeName = "announcementId")
	private String announcementId;
	
	@DynamoDBAttribute(attributeName = "professorId")
	private String professorId;
	
	@DynamoDBAttribute(attributeName = "content")
	private String content;
	
	@DynamoDBVersionAttribute(attributeName = "versionNumber")
	private Long versionNumber;
}
