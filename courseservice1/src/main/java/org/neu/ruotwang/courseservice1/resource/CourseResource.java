package org.neu.ruotwang.courseservice1.resource;

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

import org.neu.ruotwang.courseservice1.descriptor.CourseDescriptor;
import org.neu.ruotwang.courseservice1.tempstorage.TempStorage;

@Path("/courses")
public class CourseResource {
	
	private TempStorage tempStorage = new TempStorage();
	
	/**
	 * Retrieve all courses records
	 * */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses() {
		return Response.ok(tempStorage.getCOURSE_TABLE()).build();
	}
	
	/**
	 * Retrive a specific course record according to courseId
	 * */
	@GET
	@Path("{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourse(@PathParam("courseId") String courseId) {
		return Response.ok(tempStorage.getCOURSE_TABLE().get(courseId) == null ?
        		"No record was found." : tempStorage.getCOURSE_TABLE().get(courseId)).build();
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
		if(tempStorage.getCOURSE_TABLE().containsKey(courseId)) {
			return Response.ok(String.format("Course ID: s% already exists.", courseId)).build();
		}
		tempStorage.getCOURSE_TABLE().put(courseId, courseDescriptor);
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
			if(!tempStorage.getCOURSE_TABLE().containsKey(courseId)) {
				throw new RuntimeException(String.format("Course ID doesn't exist with ID: %s", courseId));
			}
			tempStorage.getCOURSE_TABLE().put(courseId, courseDescriptor);
			return Response.ok("Update course succeed!").build();
		} catch(RuntimeException ex) {
			throw new RuntimeException("Internal Server Error", ex);
		}
	}
	
	@DELETE
	@Path("{courseId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteCourse(@PathParam("courseId") String courseId) {
		tempStorage.getCOURSE_TABLE().remove(courseId);
		return Response.ok("Delete course succeed!").build();
	}
}
