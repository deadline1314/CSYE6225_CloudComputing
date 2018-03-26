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

import org.neu.ruotwang.courseservice1.dao.Program;
import org.neu.ruotwang.courseservice1.dao.ProgramDao;
import org.neu.ruotwang.courseservice1.descriptor.ProgramDescriptor;
// import org.neu.ruotwang.courseservice1.tempstorage.TempStorage;

@Path("/programs")
public class ProgramResource {
	
	// private TempStorage tempStorage = new TempStorage();
	private ProgramDao programDao = new ProgramDao();
	
	/**
	 * Retrive a specific program record according to programId
	 * */
	@GET
	@Path("{programId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgram(@PathParam("{programId}") String programId) {
		return Response.ok(programDao.get(programId).isPresent() ?
        		programDao.get(programId).get() : "No record was found.").build();
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
		if(programDao.get(programId).isPresent()) {
			return Response.ok(String.format("Program ID: s% already exists.", programId)).build();
		}
		Program newProgram = mapFromProgramDescriptor(programId, programDescriptor);
		programDao.save(newProgram);
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
			Optional<Program> exisitingProgram = programDao.get(programId);
			if(!exisitingProgram.isPresent()) {
				throw new RuntimeException(String.format("Program ID doesn't exist with ID: %s", programId));
			}
			Program newProgram = mapFromProgramDescriptor(programId, programDescriptor);
			newProgram.setVersionNumber(exisitingProgram.get().getVersionNumber());
			programDao.save(newProgram);
			return Response.ok("Update program succeed!").build();
		} catch(RuntimeException ex) {
			throw new RuntimeException("Internal Server Error", ex);
		}
	}
	
	private Program mapFromProgramDescriptor(String programId, ProgramDescriptor programDescriptor) {
		Program newProgram = new Program();
		newProgram.setProgramId(programId);
		newProgram.setProgramName(programDescriptor.getProgramName());
		return newProgram;
	}
	
}
