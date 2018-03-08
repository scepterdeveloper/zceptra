package com.zceptra.entities;

import javax.persistence.Embeddable;

@Embeddable
public class OrganizingEntity {
	
	private Integer type;
	private Long id;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
