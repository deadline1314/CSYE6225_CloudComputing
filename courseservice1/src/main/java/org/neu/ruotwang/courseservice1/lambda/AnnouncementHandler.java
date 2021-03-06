package org.neu.ruotwang.courseservice1.lambda;

import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.neu.ruotwang.courseservice1.configuration.AWSSNSClient;
import org.neu.ruotwang.courseservice1.dao.Course;
import org.neu.ruotwang.courseservice1.dao.CourseDao;
import org.neu.ruotwang.courseservice1.dao.Professor;
import org.neu.ruotwang.courseservice1.dao.ProfessorDao;
import org.neu.ruotwang.courseservice1.descriptor.AnnouncementDescriptor;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.model.PublishRequest;

public class AnnouncementHandler implements RequestHandler<DynamodbEvent, String> {

	private ProfessorDao professorDao = new ProfessorDao();
	private CourseDao courseDao = new CourseDao();

	/**
	 * 
	 * 1. user type in professorId
	 * ----Once an Anouncement object was created----
	 * 2. use professorId value to scan Professor Table, find the associated Professor object
	 * 3. use CourseId list to iterate all courseId, scan Course Table
	 * 4. for each Course object, find sns topic ARN, use that value to publish a topic
	 * 
	 * */
	@Override
	public String handleRequest(DynamodbEvent input, Context context) {
		for (DynamodbStreamRecord record : input.getRecords()) {
    		if (record == null || record.getEventName().equals("REMOVE")) {
    			continue;
    		}
    		final AnnouncementDescriptor announcementDescriptor = mapFromJSONObject(record.getDynamodb().getNewImage());
    		String professorId = announcementDescriptor.getProfessorId();
    		final Professor professor = professorDao.get(professorId).orElseThrow(
    				() -> new NotFoundException("Professor " + professorId + " not found."));
    		professor.getCourseIdList().forEach(courseId -> {
	    			Optional<Course> course = courseDao.get(courseId);
	    			String subjectName = professor.getProfessorName() + "post a new announcement.";
	    			if(course.isPresent()) {
	    				AWSSNSClient.getSNSClient().publish(new PublishRequest(course.get().getSnsTopic(),
	    						announcementDescriptor.getContent(), subjectName));
	    			} else {
	    				throw new NotFoundException("Course " + courseId + " not found.");
	    			}
    			});;
    		
		}
		return input.toString();
	}
	
	private AnnouncementDescriptor mapFromJSONObject(Map<String, AttributeValue> json) {
		return new AnnouncementDescriptor(json.get("professorId").getS(), json.get("content").getS());
	}

}
