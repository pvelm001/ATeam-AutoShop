package com.cs166.CS166Backend.api;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class ServiceRequestAction {
	
	@GetMapping("/servicerequest")
	public String getServiceRequest(String requestId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("requestId", Integer.parseInt(requestId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleServiceRequestOperation(obj, OperationType.GET).toString();
	}
	
	
	@PostMapping("/servicerequest")
	public String createServiceRequest(String customerId, String vin, String employeeId, String statusId, String dateIn, String comments, String odometer, String fuelLevel) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("customerId", Integer.parseInt(customerId));
		obj.put("vin", vin);
		obj.put("employeeId", Integer.parseInt(employeeId));
		obj.put("statusId", Integer.parseInt(statusId));
		obj.put("dateIn", dateIn);
		obj.put("comments", comments);
		obj.put("odometer", Double.valueOf(odometer));
		obj.put("fuelLevel", Integer.parseInt(fuelLevel));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleServiceRequestOperation(obj, OperationType.POST).toString();
	}
	
	@PutMapping("/servicerequest")
	public String updateServiceRequest(String requestId, String employeeId, String statusId, String dateIn, String comments, String odometer, String fuelLevel) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("requestId", Integer.parseInt(requestId));
		obj.put("employeeId", Integer.parseInt(employeeId));
		obj.put("statusId", Integer.parseInt(statusId));
		obj.put("dateIn", dateIn);
		obj.put("comments", comments);
		obj.put("odometer", Double.valueOf(odometer));
		obj.put("fuelLevel", Integer.parseInt(fuelLevel));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleServiceRequestOperation(obj, OperationType.PUT).toString();
	}
	
	@PutMapping("/servicerequest/close")
	public String updateServiceRequestClose(String requestId, String billId, String employeeId, String statusId, String dateIn, String dateOut, String comments, String odometer, String fuelLevel) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("requestId", Integer.parseInt(requestId));
		obj.put("billId", (billId != null && !billId.isEmpty()) ? Integer.parseInt(billId) : null);
		obj.put("employeeId", Integer.parseInt(employeeId));
		obj.put("statusId", Integer.parseInt(statusId));
		obj.put("dateIn", dateIn);
		obj.put("dateOut", dateOut);
		obj.put("comments", comments);
		obj.put("odometer", Double.valueOf(odometer));
		obj.put("fuelLevel", Integer.parseInt(fuelLevel));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleServiceRequestOperation(obj, OperationType.PUT_WITH_BILL).toString();
	}
	
	@GetMapping("/servicerequest/all")
	public String getAllServiceRequests() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleServiceRequestOperation(null, OperationType.ALL).toString();
	}

}
