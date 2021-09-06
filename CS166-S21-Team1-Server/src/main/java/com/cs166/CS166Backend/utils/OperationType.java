package com.cs166.CS166Backend.utils;

public enum OperationType
{
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	ALL("ALL"),
	CUSTOMER_SPECIFIC_CARS("CUSTOMER_SPECIFIC_CARS"),
	GET_CAR_META("GET_CAR_META"),
	GET_ALL_BILL_DETAILS_FOR_BILL_ID("GET_ALL_BILL_DETAILS_FOR_BILL_ID"),
	FILTER_SIX("FILTER_SIX"),
	FILTER_SEVEN("FILTER_SEVEN"),
	FILTER_EIGHT("FILTER_EIGHT"),
	FILTER_NINE("FILTER_NINE"),
	FILTER_TEN("FILTER_TEN"),
	PUT_WITH_BILL("PUT_WITH_BILL");
	
	String purpose;
	
	public String getPurpose()
	{
		return purpose;
	}
	
	OperationType(String purpose)
	{
		this.purpose = purpose;
	}
}
