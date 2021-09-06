package com.cs166.CS166Backend.api;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs166.CS166Backend.Cs166BackendApplication;
import com.cs166.CS166Backend.database.DBUtil;
import com.cs166.CS166Backend.utils.OperationType;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CarMetaAction {
	
	@GetMapping("/car/meta/id")
	public String getCarMeta(String carId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("carId", Integer.parseInt(carId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarMetaOperation(obj, OperationType.GET).toString();
	}
	
	@GetMapping("/car/meta/all")
	public String getAllCarMeta() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarMetaOperation(null, OperationType.ALL).toString();
	}
	
	@GetMapping("/car/meta")
	public String getCarMeta(String make, String model, String color, String yearOfManufacture, String transmission, String averageMileage) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("make", make);
		obj.put("model", model);
		obj.put("color", color);
		obj.put("yearOfManufacture", yearOfManufacture);
		obj.put("transmission", transmission);
		obj.put("averageMileage", averageMileage);
		
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarMetaOperation(obj, OperationType.GET_CAR_META).toString();
	}
	
	@PostMapping("/car/meta")
	public String createCarMeta(String make, String model, String color, String yearOfManufacture, String transmission, String averageMileage) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("make", make);
		obj.put("model", model);
		obj.put("color", color);
		obj.put("yearOfManufacture", yearOfManufacture);
		obj.put("transmission", transmission);
		obj.put("averageMileage", averageMileage);
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCarMetaOperation(obj, OperationType.POST).toString();
	}
	
}
