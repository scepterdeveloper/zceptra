package com.zceptra.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private String name;
	private String description;
	private String entityName;
	private String reportType;
	private String query;
	private String xCoord;
	private String yCoord;
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getxCoord() {
		return xCoord;
	}
	public void setxCoord(String xCoord) {
		this.xCoord = xCoord;
	}
	public String getyCoord() {
		return yCoord;
	}
	public void setyCoord(String yCoord) {
		this.yCoord = yCoord;
	}		
}
