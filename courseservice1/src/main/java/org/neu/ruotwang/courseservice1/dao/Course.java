package org.neu.ruotwang.courseservice1.dao;

import java.util.List;

import org.neu.ruotwang.courseservice1.dao.converter.BoardConverter;
import org.neu.ruotwang.courseservice1.dao.converter.LectureListConverter;
import org.neu.ruotwang.courseservice1.dao.converter.RosterConverter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "COURSE_DEV")
public class Course {
	@DynamoDBHashKey(attributeName = "courseId")
	private String courseId;
	
	@DynamoDBAttribute(attributeName = "courseName")
	private String courseName;
	
	@DynamoDBAttribute(attributeName = "lectureList")
	@DynamoDBTypeConverted(converter = LectureListConverter.class)
	private List<Lecture> lectureList;
	
	@DynamoDBAttribute(attributeName = "board")
	@DynamoDBTypeConverted(converter = BoardConverter.class)
	private Board board;
	
	@DynamoDBAttribute(attributeName = "roster")
	@DynamoDBTypeConverted(converter = RosterConverter.class)
	private Roster roster;
	
	@DynamoDBAttribute(attributeName = "snsTopic")
	private String snsTopic;
	
	@DynamoDBVersionAttribute(attributeName = "versionNumber")
	private Long versionNumber;
}
