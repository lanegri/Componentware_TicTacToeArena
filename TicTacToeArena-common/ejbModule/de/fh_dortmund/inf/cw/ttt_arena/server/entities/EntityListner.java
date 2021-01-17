package de.fh_dortmund.inf.cw.ttt_arena.server.entities;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntityListner {
	
	@PrePersist
	public void createAt(EntitiesInfo entity){
		entity.setCreatedAt(new Date());
	}
	
	@PreUpdate
	public void updatedAt(EntitiesInfo entity) {
		entity.setUpdatedAt(new Date());
	}
}
