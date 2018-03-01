package com.zceptra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.entities.Category;
import com.zceptra.repositories.CategoryRepository;

@RestController
public class CategoryController {
	
	//Test gitignore
	
	@Autowired
	private CategoryRepository repository;
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-all-categories")
	public Iterable<Category> getAllCategories()	{
		
		return repository.findAll();
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-category")
	public Category getCategory(Long id)	{
		
		return repository.findOne(id);
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="edit-category", method=RequestMethod.POST)
	@ResponseBody
	public Category saveCategory(@RequestBody Category editedCategory)	{
		
		Category category = null;
		
		if(editedCategory.getId() != null)	{
			
			category = repository.findOne(editedCategory.getId());
		}
		else {
			category = new Category();
		}
		
		category.setName(editedCategory.getName());
		category.setDescription(editedCategory.getDescription());
		return repository.save(category);
	}
}
