package com.cs166.CS166Backend.pojo;

public class Mechanic {
	
Integer employeeId;
String firstName;
String lastName;
Integer yearsOfExp;
Long salary;
Integer rating;
String speciality;
String role;

public Integer getEmployeeId() {
	return employeeId;
}
public void setEmployeeId(Integer employeeId) {
	this.employeeId = employeeId;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public Integer getYearsOfExp() {
	return yearsOfExp;
}
public void setYearsOfExp(Integer yearsOfExp) {
	this.yearsOfExp = yearsOfExp;
}
public Long getSalary() {
	return salary;
}
public void setSalary(Long salary) {
	this.salary = salary;
}
public Integer getRating() {
	return rating;
}
public void setRating(Integer rating) {
	this.rating = rating;
}
public String getSpeciality() {
	return speciality;
}
public void setSpeciality(String speciality) {
	this.speciality = speciality;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
}
