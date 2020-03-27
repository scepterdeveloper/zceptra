package com.zceptra.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zceptra.api.ReportData;
import com.zceptra.api.ReportData2D;
import com.zceptra.entities.Account;
import com.zceptra.entities.AccountSummary;
import com.zceptra.entities.Report;
import com.zceptra.repositories.ReportRepository;

@Service
public class ReportEngine {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ReportRepository reportRepository;

	public ReportData getReportData(Report report) throws Exception {

		String queryString = "SELECT " + qualifyFieldName(report.getxCoord()) + ", "
				+ qualifyFieldName(report.getyCoord()) + " FROM " + report.getEntityName() + " a";

		String whereClause = report.getQuery();
		if (whereClause != null && whereClause.length() > 0)
			queryString += " WHERE " + whereClause;
		System.out.println("Query: " + queryString);
		Query query = entityManager.createQuery(queryString);

		if (report.getReportType().equals("2D"))
			return get2DReport(report.getName(), report.getxCoordLabel(), report.getyCoordLabel(), "Total",
					query.getResultList());
		throw new Exception("Unsupported report type");
	}

	public ReportData executeReport(Long reportId) throws Exception {

		return getReportData(reportRepository.getOne(reportId));
	}

	public List<ReportData> executeReports() throws Exception {

		List<Report> reports = reportRepository.findAll();
		List<ReportData> reportDataList = new ArrayList<>();
		
		for(Report report: reports)	{
			reportDataList.add(getReportData(report));
		}
		
		return reportDataList;
	}

	private ReportData get2DReport(String caption, String xCoordinateLabel, String yCoordinateLabel,
			String totalValueLabel, List resultList) {

		ReportData2D reportData = new ReportData2D();

		reportData.setCaption(caption);
		reportData.setxCoordinateLabel(xCoordinateLabel);
		reportData.setyCoordinateLabel(yCoordinateLabel);

		double totalValue = 0;

		for (Object[] row : (List<Object[]>) resultList) {
			System.out.println("X: " + row[0].toString() + ", Y: " + row[1].toString());
			totalValue += (double) row[1];
			reportData.addValue(row[0].toString(), row[1].toString());
		}

		reportData.setTotalValue(totalValue + "");
		reportData.setTotalValueLabel(totalValueLabel);

		return reportData;
	}

	public void getTabularReport(String entityName, List<String> fields, String whereClause) {

		String queryString = "SELECT ";

		for (String fieldName : fields) {
			queryString += qualifyFieldName(fieldName) + ", ";
		}

		queryString += " a.id FROM " + entityName + " a";
		if (whereClause != null && whereClause.length() > 0)
			queryString += " WHERE " + whereClause;
		System.out.println("Query: " + queryString);

		Query query = entityManager.createQuery(queryString);
		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			for (Object value : row) {
				System.out.print(value.toString() + " ");
			}
			System.out.println();
		}
	}

	private String qualifyFieldName(String fieldName) {

		String qualifiedFieldName = "";

		if (fieldName.contains("+")) {

			String[] operands = fieldName.split("\\+");

			for (String operand : operands) {
				qualifiedFieldName += "a." + operand;
				if (!operand.equals(operands[operands.length - 1]))
					qualifiedFieldName += "+";

			}

			return qualifiedFieldName;

		} else
			return "a." + fieldName;
	}
}
