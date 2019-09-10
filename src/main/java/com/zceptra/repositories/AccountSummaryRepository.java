package com.zceptra.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zceptra.entities.AccountSummary;

public interface AccountSummaryRepository extends JpaRepository<AccountSummary, Long>  {
	
	public List<AccountSummary> findByAccountIdAndYearAndMonth(Long accountId, int year, int month);
	public List<AccountSummary> findByAccountIdAndYear(Long accountId, int year);
	public List<AccountSummary> findByAccountId(Long accountId);
	public List<AccountSummary> findAllByAccountIdAndValidFromLessThanEqualAndValidToGreaterThanEqual(Long accountId, LocalDate transactionStartDate, LocalDate transactionEndDate);	
}
