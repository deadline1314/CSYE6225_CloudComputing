package org.neu.ruotwang.courseservice1.resource;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.neu.ruotwang.courseservice1.configuration.AWSSNSClient;
import org.neu.ruotwang.courseservice1.dao.Course;
import org.neu.ruotwang.courseservice1.dao.CourseDao;
import org.neu.ruotwang.courseservice1.descriptor.CourseDescriptor;

import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
// import org.neu.ruotwang.courseservice1.tempstorage.TempStorage;


@Path("/courses")
public class CourseResource {
	
	// private TempStorage tempStorage = new TempStorage();
	private CourseDao courseDao = new CourseDao();
	
	/**
	 * Retrieve all courses records
	 * */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses() {
		return Response.ok(courseDao.getAll()).build();
	}
	
	/**
	 * Retrive a specific course record according to courseId
	 * */
	@GET
	@Path("{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourse(@PathParam("courseId") String courseId) {
		return Response.ok(courseDao.get(courseId).isPresent() ?
        		 courseDao.get(courseId).get() : "No record was found.").build();
	}
	
	/**
	 * Create a new course record
	 * */
	@POST
	@Path("{courseId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createCourse(@PathParam("courseId") String courseId,
			CourseDescriptor courseDescriptor) {
		if(courseDao.get(courseId).isPresent()) {
			return Response.ok(String.format("Course ID: s% already exists.", courseId)).build();
		}
		
		AWSSNSClient.getSNSClient().createTopic(new CreateTopicRequest(courseId));
		
		Course newCourse = mapFromCourseDescriptor(courseId, courseDescriptor);
		newCourse.setSnsTopic(AWSSNSClient.SNS_TOPIC_PREFIX + courseId); 
		courseDao.save(newCourse);
		return Response.ok("Create new course succeed!").build();
	}
	
	/**
	 * Update an existing course record according to courseId
	 * */
	@PUT
	@Path("{courseId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateCourse(@PathParam("courseId") String courseId,
			CourseDescriptor courseDescriptor) {
		try {
			Optional<Course> exisitingCourse = courseDao.get(courseId);
			if(!exisitingCourse.isPresent()) {
				throw new RuntimeException(String.format("Course ID doesn't exist with ID: %s", courseId));
			}
			Course newCourse = mapFromCourseDescriptor(courseId, courseDescriptor);
			newCourse.setSnsTopic(AWSSNSClient.SNS_TOPIC_PREFIX + courseId); 
			newCourse.setVersionNumber(exisitingCourse.get().getVersionNumber());
			courseDao.save(newCourse);
			return Response.ok("Update course succeed!").build();
		} catch(RuntimeException ex) {
			throw new RuntimeException("Internal Server Error", ex);
		}
	}
	
	private Course mapFromCourseDescriptor(String courseId, CourseDescriptor courseDescriptor) {
		Course newCourse = new Course();
		newCourse.setCourseId(courseId);
		newCourse.setCourseName(courseDescriptor.getCourseName());
		newCourse.setLectureList(courseDescriptor.getLectureList());
		newCourse.setBoard(courseDescriptor.getBoard());
		newCourse.setRoster(courseDescriptor.getRoster());
		return newCourse;
	}
	
	@DELETE
	@Path("{courseId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteCourse(@PathParam("courseId") String courseId) {
		Course courseToDelect = new Course();
		courseToDelect.setCourseId(courseId);
		courseDao.delete(courseToDelect);
		
		AWSSNSClient.getSNSClient().deleteTopic(new DeleteTopicRequest(
				AWSSNSClient.SNS_TOPIC_PREFIX + courseId));
		return Response.ok("Delete course succeed!").build();
	}
	
}
