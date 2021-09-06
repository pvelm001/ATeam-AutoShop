package com.cs166.CS166Backend.pojo;

public class CarMeta {
	
	Integer carId;
	String make;
	String model;
	String color;
	String yearOfManufacture;
	Character transmission;
	Double averageMileage;
	
	public Integer getCarId()
	{
		return carId;
	}
	public void setCarId(Integer carId){
		this.carId = carId;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getYearOfManufacture() {
		return yearOfManufacture;
	}
	public void setYearOfManufacture(String yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}
	public Character getTransmission() {
		return transmission;
	}
	public void setTransmission(Character transmission) {
		this.transmission = transmission;
	}
	public Double getAverageMileage() {
		return averageMileage;
	}
	public void setAverageMileage(Double averageMileage) {
		this.averageMileage = averageMileage;
	}

}
