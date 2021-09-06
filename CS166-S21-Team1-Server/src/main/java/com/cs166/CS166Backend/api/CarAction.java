package com.cs166.CS166Backend.api;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs166.CS166Backend.Cs166BackendApplication;
import com.cs166.CS166Backend.database.DBUtil;
import com.cs166.CS166Backend.utils.OperationType;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CarAction {
	
	@PostMapping("/car")
	public String createCar(String vin, String customerId, String carId) throws Exception
	{
		
		JSONObject obj = new JSONObject();
		
		obj.put("vin", vin);
		obj.put("customerId", Integer.parseInt(customerId));
		obj.put("carId", Integer.parseInt(carId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarOperation(obj, OperationType.POST).toString();
	}
	
	
	@PutMapping("/car")
	public String updateCar(String vin, String customerId, String carId) throws Exception
	{
		JSONObject obj = new JSONObject();
		
		obj.put("vin", vin);
		obj.put("customerId", Integer.parseInt(customerId));
		obj.put("carId", Integer.parseInt(carId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarOperation(obj, OperationType.PUT).toString();
	}
	
	@GetMapping("/car")
	public String getCar(String vin) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("vin", vin);
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarOperation(obj, OperationType.GET).toString();
	}
	
	@DeleteMapping("/car")
	public String deleteCar(String vin) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("vin", vin);
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarOperation(obj, OperationType.DELETE).toString();
	}
	
	@GetMapping("/car/customer")
	public String getAllCarsForCustomer(String customerId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("customerId", Integer.parseInt(customerId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarOperation(obj, OperationType.CUSTOMER_SPECIFIC_CARS).toString();
	}

	@GetMapping("/car/all")
	public String getAllCars() throws Exception
	{
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarOperation(null, OperationType.ALL).toString();
	}
	
}
