package com.zceptra.api;

public class ReportData2DLine {
	
	private String xCoordinate;
	private String yCoordinate;
		
	public ReportData2DLine(String xCoordinate, String yCoordinate) {
		super();
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	public String getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(String xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public String getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(String yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
}
