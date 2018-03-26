package org.neu.ruotwang.courseservice1.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.neu.ruotwang.courseservice1.descriptor.StudentDescriptor;
import org.neu.ruotwang.courseservice1.tempstorage.TempStorage;

@Path("/students")
public class StudentResource {

	private TempStorage tempStorage = new TempStorage();
	
	/**
	 * Retrieve all student records
	 * */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents() {
		return Response.ok(tempStorage.getSTUDENT_TABLE()).build();
	}
	
	/**
	 * Retrive a specific student record according to studentId
	 * */
	@GET
	@Path("{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@PathParam("studentId") String studentId) {
		return Response.ok(tempStorage.getSTUDENT_TABLE().get(studentId) == null ?
        		"No record was found." : tempStorage.getSTUDENT_TABLE().get(studentId)).build();
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
		if(tempStorage.getSTUDENT_TABLE().containsKey(studentId)) {
			return Response.ok(String.format("Student ID: s% already exists.", studentId)).build();
		}
		tempStorage.getSTUDENT_TABLE().put(studentId, studentDescriptor);
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
			if(!tempStorage.getSTUDENT_TABLE().containsKey(studentId)) {
				throw new RuntimeException(String.format("Student ID doesn't exist with ID: %s", studentId));
			}
			tempStorage.getSTUDENT_TABLE().put(studentId, studentDescriptor);
			return Response.ok("Update student succeed!").build();
		} catch(RuntimeException ex) {
			throw new RuntimeException("Internal Server Error", ex);
		}
	}
	
}
