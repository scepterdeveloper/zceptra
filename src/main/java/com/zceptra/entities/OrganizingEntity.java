package com.zceptra.entities;

import javax.persistence.Embeddable;

@Embeddable
public class OrganizingEntity {
	
	private OrganizingEntityType type;
	private Long id;
	
	public OrganizingEntityType getType() {
		return type;
	}
	public void setType(OrganizingEntityType type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
