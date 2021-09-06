package com.cs166.CS166Backend.pojo;

public class BillDetail {
	
	Integer billDetailId;
	Integer billId;
	String billDescription;
	Integer quantity;
	Double cost;
	
	public Integer getBillDetailId() {
		return billDetailId;
	}
	public void setBillDetailId(Integer billDetailId) {
		this.billDetailId = billDetailId;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	public String getBillDescription() {
		return billDescription;
	}
	public void setBillDescription(String billDescription) {
		this.billDescription = billDescription;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
}
