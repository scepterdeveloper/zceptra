package com.zceptra.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StatementProfile {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private String dateHeader;
	private String amountHeader;
	private String textHeader;
	private String columnSeparator;
	private String rowSeparator;
	
	public String getDateHeader() {
		return dateHeader;
	}
	public void setDateHeader(String dateHeader) {
		this.dateHeader = dateHeader;
	}
	public String getAmountHeader() {
		return amountHeader;
	}
	public void setAmountHeader(String amountHeader) {
		this.amountHeader = amountHeader;
	}
	public String getTextHeader() {
		return textHeader;
	}
	public void setTextHeader(String textHeader) {
		this.textHeader = textHeader;
	}
	public String getColumnSeparator() {
		return columnSeparator;
	}
	public void setColumnSeparator(String columnSeparator) {
		this.columnSeparator = columnSeparator;
	}
	public String getRowSeparator() {
		return rowSeparator;
	}
	public void setRowSeparator(String rowSeparator) {
		this.rowSeparator = rowSeparator;
	}	
}
