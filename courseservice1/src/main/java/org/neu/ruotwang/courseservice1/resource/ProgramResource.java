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

import org.neu.ruotwang.courseservice1.descriptor.ProgramDescriptor;
import org.neu.ruotwang.courseservice1.tempstorage.TempStorage;

@Path("/programs")
public class ProgramResource {
	
	private TempStorage tempStorage = new TempStorage();
	
	/**
	 * Retrieve all programs records
	 * */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPrograms() {
		return Response.ok(tempStorage.getPROGRAM_TABLE()).build();
	}
	
	/**
	 * Retrive a specific program record according to programId
	 * */
	@GET
	@Path("{programId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgram(@PathParam("{programId}") String programId) {
		return Response.ok(tempStorage.getPROGRAM_TABLE().get(programId) == null ?
        		"No record was found." : tempStorage.getPROGRAM_TABLE().get(programId)).build();
	}
	
	/**
	 * Create a new program record
	 * */
	@POST
	@Path("{programId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createProgram(@PathParam("{programId}") String programId,
			ProgramDescriptor programDescriptor) {
		if(tempStorage.getPROGRAM_TABLE().containsKey(programId)) {
			return Response.ok(String.format("Program ID: s% already exists.", programId)).build();
		}
		tempStorage.getPROGRAM_TABLE().put(programId, programDescriptor);
		return Response.ok("Create new program succeed!").build();
	}
	
	/**
	 * Update an existing program record according to programId
	 * */
	@PUT
	@Path("{programId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateProgram(@PathParam("{programId}") String programId,
			ProgramDescriptor programDescriptor) {
		try {
			if(!tempStorage.getPROGRAM_TABLE().containsKey(programId)) {
				throw new RuntimeException(String.format("Program ID doesn't exist with ID: %s", programId));
			}
			tempStorage.getPROGRAM_TABLE().put(programId, programDescriptor);
			return Response.ok("Update program succeed!").build();
		} catch(RuntimeException ex) {
			throw new RuntimeException("Internal Server Error", ex);
		}
	}
	
}
