package com.zceptra.api;

import java.util.ArrayList;
import java.util.List;

public class ReportData2D implements ReportData{
	
	private static final long serialVersionUID = 1L;
	
	private String xCoordinateLabel;
	private String yCoordinateLabel;
	private List<ReportData2DLine> values;
	
	public ReportData2D()	{
		
		values = new ArrayList<ReportData2DLine>();
	}
	
	public void addValue(String xCoordinateValue, String yCoordinateValue)	{
		values.add(new ReportData2DLine(xCoordinateValue, yCoordinateValue));
	}
	
	public String getxCoordinateLabel() {
		return xCoordinateLabel;
	}
	public void setxCoordinateLabel(String xCoordinateLabel) {
		this.xCoordinateLabel = xCoordinateLabel;
	}
	public String getyCoordinateLabel() {
		return yCoordinateLabel;
	}
	public void setyCoordinateLabel(String yCoordinateLabel) {
		this.yCoordinateLabel = yCoordinateLabel;
	}

	public List<ReportData2DLine> getValues() {
		return values;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
