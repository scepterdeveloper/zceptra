package com.zceptra.entities;

import javax.persistence.Embeddable;

@Embeddable
public class OrganizingEntity {
	
	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
