package com.zceptra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.entities.Category;
import com.zceptra.repositories.CategoryRepository;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository repository;
	
	@RequestMapping(value="get-all-categories")
	public Iterable<Category> getAllCategories()	{
		
		return repository.findAll();
	}
}
