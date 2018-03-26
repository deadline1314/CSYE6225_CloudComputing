package org.neu.ruotwang.courseservice1.dao;

import java.util.Optional;

import org.neu.ruotwang.courseservice1.configuration.AWSDynamoDBClient;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class AnnouncementDao extends AbstractServiceDao<Announcement> {
	
	protected final DynamoDBMapper mapper = AWSDynamoDBClient.getDynamoDBMapper();

	@Override
	public Optional<Announcement> get(String announcementId) throws RuntimeException {
		try {
			Announcement announcement = new Announcement();
			announcement.setAnnouncementId(announcementId);
			return Optional.ofNullable(mapper.load(announcement));
		} catch(Exception ex) {
			throw new RuntimeException("Exception while querying an announcement " + announcementId, ex);
		}
	}

}
