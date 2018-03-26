package org.neu.ruotwang.courseservice1.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.neu.ruotwang.courseservice1.dao.Announcement;
import org.neu.ruotwang.courseservice1.dao.AnnouncementDao;
import org.neu.ruotwang.courseservice1.descriptor.AnnouncementDescriptor;

@Path("/announcements")
public class AnnouncementResourse {
	
	private AnnouncementDao announcementDao = new AnnouncementDao();
	
	/**
	 * Retrive a specific announcement record according to announcementId
	 * */
	@GET
	@Path("{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnnouncement(@PathParam("{announcementId}") String announcementId) {
		return Response.ok(announcementDao.get(announcementId).isPresent() ?
				announcementDao.get(announcementId).get() : "No record was found.").build();
	}
	
	/**
	 * Create a new announcement record
	 * */
	@POST
	@Path("{announcementId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createProfessor(@PathParam("{announcementId}") String announcementId,
			AnnouncementDescriptor announcementDescriptor) {
		if(announcementDao.get(announcementId).isPresent()) {
			return Response.ok(String.format("Announcement ID: s% already exists.", announcementId)).build();
		}
		announcementDao.save(mapFromAnnouncementDescriptor(announcementId, announcementDescriptor));
		return Response.ok("Create new announcement succeed!").build();
	}
	
	private Announcement mapFromAnnouncementDescriptor(String announcementId,
			AnnouncementDescriptor announcementDescriptor) {
		Announcement newAnnouncement = new Announcement();
		newAnnouncement.setAnnouncementId(announcementId);
		newAnnouncement.setContent(announcementDescriptor.getContent());
		newAnnouncement.setProfessorId(announcementDescriptor.getProfessorId());
		return newAnnouncement;
	}
}
