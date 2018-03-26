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

import org.neu.ruotwang.courseservice1.dao.Professor;
import org.neu.ruotwang.courseservice1.dao.ProfessorDao;
import org.neu.ruotwang.courseservice1.descriptor.ProfessorDescriptor;

@Path("/professor")
public class ProfessorResourse {

	private ProfessorDao professorDao = new ProfessorDao();
	
	/**
	 * Retrive a specific professor record according to professorId
	 * */
	@GET
	@Path("{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfessor(@PathParam("{professorId}") String professorId) {
		return Response.ok(professorDao.get(professorId).isPresent() ?
				professorDao.get(professorId).get() : "No record was found.").build();
	}
	
	/**
	 * Create a new professor record
	 * */
	@POST
	@Path("{professorId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createProfessor(@PathParam("{professorId}") String professorId,
			ProfessorDescriptor professorDescriptor) {
		if(professorDao.get(professorId).isPresent()) {
			return Response.ok(String.format("Progfessor ID: s% already exists.", professorId)).build();
		}
		professorDao.save(mapFromProfessorDescriptor(professorId, professorDescriptor));
		return Response.ok("Create new professor succeed!").build();
	}
	
	/**
	 * Update an existing professor record according to professorId
	 * */
	@PUT
	@Path("{professorId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateProfessor(@PathParam("{professorId}") String professorId,
			ProfessorDescriptor professorDescriptor) {
		try {
			Optional<Professor> existingProfessor = professorDao.get(professorId);
			if(!existingProfessor.isPresent()) {
				throw new RuntimeException(String.format("Professor ID doesn't exist with ID: %s", professorId));
			}
			Professor newProfessor = mapFromProfessorDescriptor(professorId, professorDescriptor);
			newProfessor.setVersionNumber(existingProfessor.get().getVersionNumber());
			professorDao.save(newProfessor);
			return Response.ok("Update professor succeed!").build();
		} catch(RuntimeException ex) {
			throw new RuntimeException("Internal Server Error", ex);
		}
	}
	
	private Professor mapFromProfessorDescriptor(String professorId, ProfessorDescriptor professorDescriptor) {
		Professor newProfessor = new Professor();
		newProfessor.setProfessorId(professorId);
		newProfessor.setProfessorName(professorDescriptor.getProfessorName());
		newProfessor.setCourseIdList(professorDescriptor.getCourseIdList());
		return newProfessor;
	}
}
