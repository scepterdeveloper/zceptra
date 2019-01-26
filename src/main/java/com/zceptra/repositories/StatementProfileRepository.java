package com.zceptra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.zceptra.entities.StatementProfile;

public interface StatementProfileRepository extends JpaRepository<StatementProfile, Long> {
}
