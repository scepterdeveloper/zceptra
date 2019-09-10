package com.zceptra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.entities.Report;
import com.zceptra.repositories.ReportRepository;

@RestController
public class ReportController {
	
	@Autowired
	private ReportRepository reportRepository;
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-all-reports")
	public Iterable<Report> getAllReports()	{
		
		return reportRepository.findAll();
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-report")
	public Report getReport(@RequestParam Long id)	{
		
		return reportRepository.getOne(id);
	}		
	
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="edit-report", method=RequestMethod.POST)
	@ResponseBody
	public Report saveReport(@RequestBody Report editedReport)	{
				
		/*if(editedReport.getId() != null)	{
			
			editedReport = reportRepository.getOne(editedReport.getId());
		}*/
		
		return reportRepository.save(editedReport);
	}
	
}
