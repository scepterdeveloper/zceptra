package com.zceptra.entities;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionType {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	private String name;
	private String description;
	private String dateLabel;
	private String amountLabel;
	private String debitAccountLabel;
	private String creditAccountLabel;
	private String descriptionLabel;
	private Boolean creditAccountHidden;
	private Boolean debitAccountHidden;
	private Boolean creditAccountLeading;
	
	@ElementCollection
	private List<OrganizingEntity> debitableAccounts;
	
	@ElementCollection
	private List<OrganizingEntity> creditableAccounts;

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

	public String getDateLabel() {
		return dateLabel;
	}

	public void setDateLabel(String dateLabel) {
		this.dateLabel = dateLabel;
	}

	public String getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(String amountLabel) {
		this.amountLabel = amountLabel;
	}

	public String getDebitAccountLabel() {
		return debitAccountLabel;
	}

	public void setDebitAccountLabel(String debitAccountLabel) {
		this.debitAccountLabel = debitAccountLabel;
	}

	public String getCreditAccountLabel() {
		return creditAccountLabel;
	}

	public void setCreditAccountLabel(String creditAccountLabel) {
		this.creditAccountLabel = creditAccountLabel;
	}

	public String getDescriptionLabel() {
		return descriptionLabel;
	}

	public void setDescriptionLabel(String descriptionLabel) {
		this.descriptionLabel = descriptionLabel;
	}

	public Boolean getCreditAccountHidden() {
		return creditAccountHidden;
	}

	public void setCreditAccountHidden(Boolean creditAccountHidden) {
		this.creditAccountHidden = creditAccountHidden;
	}

	public Boolean getDebitAccountHidden() {
		return debitAccountHidden;
	}

	public void setDebitAccountHidden(Boolean debitAccountHidden) {
		this.debitAccountHidden = debitAccountHidden;
	}

	public Boolean getCreditAccountLeading() {
		return creditAccountLeading;
	}

	public void setCreditAccountLeading(Boolean creditAccountLeading) {
		this.creditAccountLeading = creditAccountLeading;
	}

	public List<OrganizingEntity> getDebitableAccounts() {
		return debitableAccounts;
	}

	public void setDebitableAccounts(List<OrganizingEntity> debitableAccounts) {
		this.debitableAccounts = debitableAccounts;
	}

	public List<OrganizingEntity> getCreditableAccounts() {
		return creditableAccounts;
	}

	public void setCreditableAccounts(List<OrganizingEntity> creditableAccounts) {
		this.creditableAccounts = creditableAccounts;
	}	
}
