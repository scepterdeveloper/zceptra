package com.zceptra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.api.PostStatementRequest;
import com.zceptra.entities.Statement;
import com.zceptra.repositories.StatementProfileRepository;
import com.zceptra.services.StatementProcessor;

@RestController
public class StatementController {
	
	@Autowired
	private StatementProfileRepository profileRepository;	
	
	@Autowired
	private StatementProcessor statementProcessor;
	
	@RequestMapping(value = "/post-statement", method = RequestMethod.POST)
	public String postStatement(@RequestBody PostStatementRequest request) {
		
		Statement statement = new Statement();
		statement.setContent(request.getStatementContent());
		statement.setAccountId("1");
		statement.setProfile(profileRepository.findOne(request.getProfileId()));
		statementProcessor.processStatement(statement);
		
		return "Ok";
	}	
}
