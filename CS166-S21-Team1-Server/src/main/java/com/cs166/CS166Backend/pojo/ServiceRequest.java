package com.cs166.CS166Backend.pojo;

public class ServiceRequest {
	
	Integer requestId;
	Integer customerId;
	Integer billId;
	String vin;
	Integer allocatedTo;
	Integer statusId;
	String dateIn;
	String dateOut;
	String comments;
	Double odometer;
	Integer fuelLevel;
	
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Integer getAllocatedTo() {
		return allocatedTo;
	}
	public void setAllocatedTo(Integer allocatedTo) {
		this.allocatedTo = allocatedTo;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getDateIn() {
		return dateIn;
	}
	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}
	public String getDateOut() {
		return dateOut;
	}
	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Double getOdometer() {
		return odometer;
	}
	public void setOdometer(Double odometer) {
		this.odometer = odometer;
	}
	public Integer getFuelLevel() {
		return fuelLevel;
	}
	public void setFuelLevel(Integer fuelLevel) {
		this.fuelLevel = fuelLevel;
	}
}
