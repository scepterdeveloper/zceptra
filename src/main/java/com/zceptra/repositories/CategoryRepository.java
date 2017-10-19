package com.zceptra.repositories;

import org.springframework.data.repository.CrudRepository;

import com.zceptra.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
