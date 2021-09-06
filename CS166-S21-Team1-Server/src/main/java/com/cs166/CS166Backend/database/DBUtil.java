package com.cs166.CS166Backend.database;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cs166.CS166Backend.MechanicShop;
import com.cs166.CS166Backend.pojo.Bill;
import com.cs166.CS166Backend.pojo.BillDetail;
import com.cs166.CS166Backend.pojo.Car;
import com.cs166.CS166Backend.pojo.CarMeta;
import com.cs166.CS166Backend.pojo.Customer;
import com.cs166.CS166Backend.pojo.Mechanic;
import com.cs166.CS166Backend.pojo.ServiceRequest;
import com.cs166.CS166Backend.utils.OperationType;
import com.google.common.base.Throwables;


public class DBUtil {
	
	private static MechanicShop ms;
	
	private static final String DB_NAME = "cs166db";
	private static final String DB_PORT = "3306";
	private static final String DB_USER = "root";
	private static final String DB_PWD = "Test#135";
	
	private static Logger LOGGER = Logger.getLogger(DBUtil.class.getName());
	
	
	private static final List<String> CUSTOMER_COLUMN_LIST = Arrays.asList("customer_id","first_name","last_name","phone_number","address");
	private static final List<String> MECHANIC_COLUMN_LIST = Arrays.asList("employee_id","first_name","last_name","yearsOfExp","salary","rating","speciality","roleName");
	private static final List<String> CAR_COLUMN_LIST = Arrays.asList("vin", "customer_id","car_id");
	private static final List<String> CAR_META_COLUMN_LIST = Arrays.asList("car_id", "make","model", "color", "yearOfManufacture","transmission","average_mileage");
	private static final List<String> SERVICE_REQUEST_META_COLUMN_LIST = Arrays.asList("request_id", "customer_id","bill_id", "vin", "allocated_to","status_id","date_in","date_out","comments","odometer","fuel_level");
	private static final List<String> BILL_META_COLUMN_LIST = Arrays.asList("bill_id", "request_id", "billDate","tax", "discount", "labor_cost","total_cost");
	private static final List<String> BILL_DETAIL_META_COLUMN_LIST = Arrays.asList("bill_detail_id", "bill_id", "bill_description","quantity", "cost");
	private static final List<String> GET_ALL_CAR_LIST = Arrays.asList("vin", "customer_id", "car_id", "make", "model", "color", "yearOfManufacture", "transmission", "average_mileage");
	
	private static final List<String> FILTER_SIX_LIST = Arrays.asList("date_in", "date_out", "comments", "total_cost");
	private static final List<String> FILTER_SEVEN_LIST = Arrays.asList("first_name", "last_name");
	private static final List<String> FILTER_EIGHT_LIST = Arrays.asList("make", "model", "manufacture");
	private static final List<String> FILTER_NINE_LIST = Arrays.asList("make", "model", "total");
	private static final List<String> FILTER_TEN_LIST = Arrays.asList("first_name", "last_name", "total_cost");
	
	public DBUtil() throws Exception
	{
		ms = new MechanicShop(DB_NAME, DB_PORT, DB_USER, DB_PWD);
	}
	
	
	public JSONArray handleCustomerOperation(JSONObject customerJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			Customer customer = new Customer();
			if(type == OperationType.GET)
			{
				int counter = 0;
				customer.setCustomerId(customerJson.getInt("customerId"));
				List<List<String>> resp = ms.getCustomer(customer);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(CUSTOMER_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.POST)
			{
				customer.setFirstName(customerJson.optString("firstName"));
				customer.setLastName(customerJson.optString("lastName"));
				customer.setAddress(customerJson.optString("address"));
				customer.setPhone(customerJson.optString("phone"));
				ms.createCustomer(customer);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
				
			}
			else if(type == OperationType.PUT)
			{
				customer.setCustomerId(customerJson.getInt("customerId"));
				customer.setFirstName(customerJson.optString("firstName"));
				customer.setLastName(customerJson.optString("lastName"));
				customer.setAddress(customerJson.optString("address"));
				customer.setPhone(customerJson.optString("phone"));
				ms.updateCustomer(customer);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.DELETE)
			{
				customer.setCustomerId(customerJson.getInt("customerId"));
				ms.deleteCustomer(customer);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.ALL)
			{
				int counter = 0;
				List<List<String>> resp = ms.getAllCustomers();
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(CUSTOMER_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
				LOGGER.log(Level.INFO, "Resp = "+resp.toString());
			}
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
	public JSONArray handleMechanicOperation(JSONObject mechanicJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			Mechanic mechanic = new Mechanic();
			if(type == OperationType.GET)
			{
				int counter = 0;
				mechanic.setEmployeeId(mechanicJson.getInt("employeeId"));
				List<List<String>> resp = ms.getMechanic(mechanic);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(MECHANIC_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.POST)
			{
				mechanic.setFirstName(mechanicJson.optString("firstName"));
				mechanic.setLastName(mechanicJson.optString("lastName"));
				mechanic.setYearsOfExp(mechanicJson.getInt("yearsOfExp"));
				mechanic.setSalary(mechanicJson.getLong("salary"));
				mechanic.setRating(mechanicJson.getInt("rating"));
				mechanic.setSpeciality(mechanicJson.optString("speciality"));
				mechanic.setRole(mechanicJson.optString("role"));
				ms.createMechanic(mechanic);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
				
			}
			else if(type == OperationType.PUT)
			{
				mechanic.setFirstName(mechanicJson.optString("firstName"));
				mechanic.setLastName(mechanicJson.optString("lastName"));
				mechanic.setYearsOfExp(mechanicJson.getInt("yearsOfExp"));
				mechanic.setSalary(mechanicJson.getLong("salary"));
				mechanic.setRating(mechanicJson.getInt("rating"));
				mechanic.setSpeciality(mechanicJson.optString("speciality"));
				mechanic.setRole(mechanicJson.optString("role"));
				mechanic.setEmployeeId(mechanicJson.getInt("employeeId"));
				ms.updateMechanic(mechanic);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.DELETE)
			{
				mechanic.setEmployeeId(mechanicJson.getInt("employeeId"));
				ms.deleteMechanic(mechanic);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else
			{
				int counter = 0;
				List<List<String>> resp = ms.getAllMechanics();
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(MECHANIC_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
				LOGGER.log(Level.INFO, "Resp = "+resp);
			}
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
	public JSONArray handleCarOperation(JSONObject carJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			Car car = new Car();
			if(type == OperationType.GET)
			{
				int counter = 0;
				car.setVin(carJson.getString("vin"));
				List<List<String>> resp = ms.getCar(car);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(CAR_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.POST)
			{
				car.setCustomerId(carJson.getInt("customerId"));
				car.setVin(carJson.getString("vin"));
				car.setCarId(carJson.getInt("carId"));
				
				ms.createCar(car);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.PUT)
			{
				car.setCustomerId(carJson.getInt("customerId"));
				car.setVin(carJson.getString("vin"));
				car.setCarId(carJson.getInt("carId"));
				
				ms.updateCar(car);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.DELETE)
			{
				car.setVin(carJson.getString("vin"));
				ms.deleteCar(car);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.CUSTOMER_SPECIFIC_CARS)
			{
				int counter = 0;
				car.setCustomerId(carJson.getInt("customerId"));
				List<List<String>> resp = ms.getAllCarsForCustomer(car);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(CAR_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.ALL)
			{
				List<List<String>> resp = ms.getAllCars();
				int counter = 0;
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(GET_ALL_CAR_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
	public JSONArray handleCarMetaOperation(JSONObject carMetaJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			CarMeta carMeta = new CarMeta();
			if(type == OperationType.GET)
			{
				int counter = 0;
				carMeta.setCarId(carMetaJson.getInt("carId"));
				List<List<String>> resp = ms.getCarMetaFromCarId(carMeta);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(CAR_META_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.ALL)
			{
				int counter = 0;
				List<List<String>> resp = ms.getAllCarMeta();
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(CAR_META_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.GET_CAR_META)
			{
				carMeta.setMake(carMetaJson.getString("make"));
				carMeta.setModel(carMetaJson.getString("model"));
				carMeta.setColor(carMetaJson.getString("color"));
				carMeta.setYearOfManufacture(carMetaJson.getString("yearOfManufacture"));
				carMeta.setTransmission(carMetaJson.getString("transmission").charAt(0));
				carMeta.setAverageMileage(Double.valueOf(carMetaJson.getString("averageMileage")));
				
				List<List<String>> resp = ms.getCarIdFromMeta(carMeta);
				if(resp == null || (resp != null && resp.size() == 0))
				{
					obj.put("code", 500);
					obj.put("Message", "Internal Server Error");
				}
				else
				{
					obj.put("carId", resp.get(0).get(0));
				}
				array.put(obj);
			}
			else if(type == OperationType.POST)
			{
				carMeta.setMake(carMetaJson.getString("make"));
				carMeta.setModel(carMetaJson.getString("model"));
				carMeta.setColor(carMetaJson.getString("color"));
				carMeta.setYearOfManufacture(carMetaJson.getString("yearOfManufacture"));
				carMeta.setTransmission(carMetaJson.getString("transmission").charAt(0));
				carMeta.setAverageMileage(Double.valueOf(carMetaJson.getString("averageMileage")));
				
				List<List<String>> resp = ms.createCarMeta(carMeta);
				if(resp == null || (resp != null && resp.size() == 0))
				{
					obj.put("code", 500);
					obj.put("Message", "Internal Server Error");
				}
				else
				{
					obj.put("carId", resp.get(0).get(0));
				}
				array.put(obj);
			}
			
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
	public JSONArray handleServiceRequestOperation(JSONObject serviceRequestJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			ServiceRequest srRequest = new ServiceRequest();
			if(type == OperationType.GET)
			{
				int counter = 0;
				srRequest.setRequestId(serviceRequestJson.getInt("requestId"));
				List<List<String>> resp = ms.getServiceRequest(srRequest);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(SERVICE_REQUEST_META_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.POST)
			{
				srRequest.setCustomerId(serviceRequestJson.getInt("customerId"));
				srRequest.setBillId(serviceRequestJson.has("billId")? serviceRequestJson.getInt("billId") : null);
				srRequest.setVin(serviceRequestJson.getString("vin"));
				srRequest.setAllocatedTo(serviceRequestJson.getInt("employeeId"));
				srRequest.setStatusId(serviceRequestJson.getInt("statusId"));
				srRequest.setDateIn(serviceRequestJson.getString("dateIn"));
				srRequest.setComments(serviceRequestJson.getString("comments"));
				srRequest.setOdometer(Double.valueOf(serviceRequestJson.getString("odometer")));
				srRequest.setFuelLevel(Integer.parseInt(serviceRequestJson.getString("fuelLevel")));
				
				ms.createServiceRequest(srRequest);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.PUT)
			{
				srRequest.setRequestId(serviceRequestJson.getInt("requestId"));
				srRequest.setAllocatedTo(serviceRequestJson.getInt("employeeId"));
				srRequest.setStatusId(serviceRequestJson.getInt("statusId"));
				srRequest.setDateIn(serviceRequestJson.getString("dateIn"));
				srRequest.setComments(serviceRequestJson.getString("comments"));
				srRequest.setOdometer(Double.valueOf(serviceRequestJson.getString("odometer")));
				srRequest.setFuelLevel(Integer.parseInt(serviceRequestJson.getString("fuelLevel")));
				
				ms.updateServiceRequest(srRequest);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.PUT_WITH_BILL)
			{
				srRequest.setRequestId(serviceRequestJson.getInt("requestId"));
				srRequest.setBillId(serviceRequestJson.getInt("billId"));
				srRequest.setAllocatedTo(serviceRequestJson.getInt("employeeId"));
				srRequest.setStatusId(serviceRequestJson.getInt("statusId"));
				srRequest.setDateIn(serviceRequestJson.getString("dateIn"));
				srRequest.setDateOut(serviceRequestJson.getString("dateOut"));
				srRequest.setComments(serviceRequestJson.getString("comments"));
				srRequest.setOdometer(Double.valueOf(serviceRequestJson.getString("odometer")));
				srRequest.setFuelLevel(Integer.parseInt(serviceRequestJson.getString("fuelLevel")));
				
				ms.updateServiceRequestWBill(srRequest);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.ALL)
			{
				int counter = 0;
				List<List<String>> resp = ms.getAllServiceRequests();
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						if(param == null)
						{
							obj.put(SERVICE_REQUEST_META_COLUMN_LIST.get(counter++), "NA");
						}
						else
						{
							obj.put(SERVICE_REQUEST_META_COLUMN_LIST.get(counter++), param);
						}
					}
					array.put(obj);
				}
			}
			
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
	public JSONArray handleBillOperation(JSONObject billJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			Bill bill = new Bill();
			if(type == OperationType.GET)
			{
				int counter = 0;
				bill.setBillId(billJson.getInt("billId"));
				List<List<String>> resp = ms.getBill(bill);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(BILL_META_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.POST)
			{
				bill.setRequestId(billJson.getInt("requestId"));
				bill.setBillDate(billJson.getString("billDate"));
				bill.setTax(billJson.getDouble("tax"));
				bill.setDiscount(billJson.getDouble("discount"));
				bill.setLaborCost(billJson.getDouble("laborCost"));
				bill.setTotalCost(billJson.getDouble("totalCost"));
				
				List<List<String>> resp = ms.createBill(bill);
				obj.put("billId", resp.get(0).get(0));
				array.put(obj);
			}
			else if(type == OperationType.PUT)
			{
				bill.setBillId(Integer.parseInt(billJson.getString("billId")));
				bill.setRequestId(billJson.getInt("requestId"));
				bill.setBillDate(billJson.getString("billDate"));
				bill.setTax(billJson.getDouble("tax"));
				bill.setDiscount(billJson.getDouble("discount"));
				bill.setLaborCost(billJson.getDouble("laborCost"));
				bill.setTotalCost(billJson.getDouble("totalCost"));
				
				ms.updateBill(bill);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.DELETE)
			{
				bill.setBillId(billJson.getInt("billId"));
				ms.deleteBill(bill);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
	public JSONArray handleBillDetailOperation(JSONObject billDetailJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			BillDetail billDetail = new BillDetail();
			if(type == OperationType.GET_ALL_BILL_DETAILS_FOR_BILL_ID)
			{
				int counter = 0;
				billDetail.setBillId(billDetailJson.getInt("billId"));
				List<List<String>> resp = ms.getAllBillDetailsForBillId(billDetail);
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(BILL_DETAIL_META_COLUMN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.POST)
			{
				billDetail.setBillId(billDetailJson.getInt("billId"));
				billDetail.setBillDescription(billDetailJson.getString("billDescription"));
				billDetail.setQuantity(billDetailJson.getInt("quantity"));
				billDetail.setCost(billDetailJson.getDouble("cost"));
				
				ms.createBillDetail(billDetail);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.PUT)
			{
				billDetail.setBillDetailId(billDetailJson.getInt("billDetailId"));
				billDetail.setBillId(billDetailJson.getInt("billId"));
				billDetail.setBillDescription(billDetailJson.getString("billDescription"));
				billDetail.setQuantity(billDetailJson.getInt("quantity"));
				billDetail.setCost(billDetailJson.getDouble("cost"));
				
				ms.updateBillDetail(billDetail);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			else if(type == OperationType.DELETE)
			{
				billDetail.setBillDetailId(billDetailJson.getInt("billDetailId"));
				ms.deleteBillDetail(billDetail);
				obj.put("code", 200);
				obj.put("Message", "Success");
				array.put(obj);
			}
			
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
	public JSONArray handleFilterOperation(JSONObject filterJson, OperationType type) throws Exception
	{
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		try
		{
			if(type == OperationType.FILTER_SIX)
			{
				List<List<String>> resp = ms.filterSix();
				int counter = 0;
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(FILTER_SIX_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.FILTER_SEVEN)
			{
				List<List<String>> resp = ms.filterSeven();
				int counter = 0;
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(FILTER_SEVEN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.FILTER_EIGHT)
			{
				List<List<String>> resp = ms.filterEight();
				int counter = 0;
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(FILTER_EIGHT_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.FILTER_NINE)
			{
				List<List<String>> resp = ms.filterNine(filterJson);
				int counter = 0;
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(FILTER_NINE_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			else if(type == OperationType.FILTER_TEN)
			{
				List<List<String>> resp = ms.filterTen();
				int counter = 0;
				for(List<String> tempResp : resp)
				{
					obj = new JSONObject();
					counter = 0;
					for(String param : tempResp)
					{
						obj.put(FILTER_TEN_LIST.get(counter++), param);
					}
					array.put(obj);
				}
			}
			
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Exception  = "+Throwables.getStackTraceAsString(e));
			obj.put("code", 500);
			obj.put("Message", "Internal Server Error");
			array.put(obj);
		}
		return array;
	}
	
}
