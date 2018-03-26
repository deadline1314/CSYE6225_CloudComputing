package org.neu.ruotwang.courseservice1.resource;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.neu.ruotwang.courseservice1.configuration.AWSSNSClient;
import org.neu.ruotwang.courseservice1.dao.Student;
import org.neu.ruotwang.courseservice1.dao.StudentDao;
import org.neu.ruotwang.courseservice1.descriptor.StudentDescriptor;

import com.amazonaws.services.sns.model.SubscribeRequest;
// import org.neu.ruotwang.courseservice1.tempstorage.TempStorage;

@Path("/students")
public class StudentResource {

	// private TempStorage tempStorage = new TempStorage();
	private StudentDao studentDao = new StudentDao();
	
	/**
	 * Retrieve all student records
	 * */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents() {
		return Response.ok(studentDao.getAll()).build();
	}
	
	/**
	 * Retrive a specific student record according to studentId
	 * */
	@GET
	@Path("{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@PathParam("studentId") String studentId) {
		return Response.ok(studentDao.get(studentId).isPresent() ?
        		"No record was found." : studentDao.get(studentId).get()).build();
	}
	
	/**
	 * Create a new student record
	 * */
	@POST
	@Path("{studentId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createStudent(@PathParam("studentId") String studentId,
			StudentDescriptor studentDescriptor) {
		if(studentDao.get(studentId).isPresent()) {
			return Response.ok(String.format("Student ID: s% already exists.", studentId)).build();
		}
		Student newStudent = mapFromStudentDescriptor(studentId, studentDescriptor);
		studentDao.save(newStudent);
		
		String email = newStudent.getStudentEmail();
		newStudent.getCourseIds().forEach(courseId -> {
			AWSSNSClient.getSNSClient().subscribe(new SubscribeRequest(AWSSNSClient.SNS_TOPIC_PREFIX + courseId, "email", email));
		});
		return Response.ok("Create new student succeed!").build();
	}
	
	/**
	 * Update an existing student record according to studentId
	 * */
	@PUT
	@Path("{studentId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateStudent(@PathParam("studentId") String studentId,
			StudentDescriptor studentDescriptor) {
		try {
			Optional<Student> existingStudent = studentDao.get(studentId);
			if(!existingStudent.isPresent()) {
				throw new RuntimeException(String.format("Student ID doesn't exist with ID: %s", studentId));
			}
			Student newStudent = mapFromStudentDescriptor(studentId, studentDescriptor);
			newStudent.setVersionNumber(existingStudent.get().getVersionNumber());
			studentDao.save(newStudent);
			
			// if email was modified, re-subscribe all the course ARN
			// else only subscribe new course ARN
			String email = newStudent.getStudentEmail();
			if(!existingStudent.get().getStudentEmail().equals(email)) {
				newStudent.getCourseIds().forEach(courseId -> {
					AWSSNSClient.getSNSClient().subscribe(new SubscribeRequest(AWSSNSClient.SNS_TOPIC_PREFIX + courseId, "email", email));
				});
			} else {
				newStudent.getCourseIds().forEach(courseId -> {
					if(!existingStudent.get().getCourseIds().contains(courseId)) {
						AWSSNSClient.getSNSClient().subscribe(new SubscribeRequest(AWSSNSClient.SNS_TOPIC_PREFIX + courseId, "email", email));
					}
				});
			}
			return Response.ok("Update student succeed!").build();
		} catch(RuntimeException ex) {
			throw new RuntimeException("Internal Server Error", ex);
		}
	}
	
	private Student mapFromStudentDescriptor(String studentId, StudentDescriptor studentDescriptor) {
		Student newStudent = new Student();
		newStudent.setStudentId(studentId);
		newStudent.setStudentName(studentDescriptor.getStudentName());
		newStudent.setStudentEmail(studentDescriptor.getStudentEmail());
		newStudent.setImagePath(studentDescriptor.getImagePath());
		newStudent.setCourseIds(studentDescriptor.getCourseIds());
		newStudent.setProgramId(studentDescriptor.getProgramId());
		return newStudent;
	}
	
}
