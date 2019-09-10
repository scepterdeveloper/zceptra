package com.zceptra.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zceptra.api.ReportData;
import com.zceptra.services.ReportEngine;

@RestController
public class ReportRuntimeController {
	
	@Autowired
	private ReportEngine reportEngine;
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="execute-report")
	public String executeReport(@RequestParam Long reportId) throws Exception	{
		
		ReportData reportData = reportEngine.executeReport(reportId);
		ObjectMapper mapper = new ObjectMapper(); 
		return mapper.writeValueAsString(reportData);
	}		
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-tabular-report")
	public String getTabularReport(@RequestParam String jpqlQuery)	{
		
		List<String> fields = new ArrayList<>();
		fields.add("account.name");
		fields.add("credits");
		fields.add("debits");
		fields.add("credits+debits");
		reportEngine.getTabularReport("AccountSummary", fields, "CURRENT_DATE BETWEEN a.validFrom AND a.validTo");
		return "Check Console.";
	}		
}
