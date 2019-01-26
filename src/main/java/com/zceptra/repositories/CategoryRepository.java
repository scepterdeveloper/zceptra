package com.zceptra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zceptra.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
