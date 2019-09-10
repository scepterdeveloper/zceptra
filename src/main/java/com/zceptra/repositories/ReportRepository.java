package com.zceptra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zceptra.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

}
