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
public class CustomerAction {
	
	@PostMapping("/customer")
	public String createCustomer(String firstName, String lastName, String address, String phone) throws Exception
	{
		
		JSONObject obj = new JSONObject();
		obj.put("firstName", firstName);
		obj.put("lastName", lastName);
		obj.put("address", address);
		obj.put("phone", phone);
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCustomerOperation(obj, OperationType.POST).toString();
	}
	
	
	@PutMapping("/customer")
	public String updateCustomer(String customerId, String firstName, String lastName, String address, String phone) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("firstName", firstName);
		obj.put("lastName", lastName);
		obj.put("address", address);
		obj.put("phone", phone);
		obj.put("customerId", Integer.parseInt(customerId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCustomerOperation(obj, OperationType.PUT).toString();
	}
	
	@GetMapping("/customer")
	public String getCustomer(String customerId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("customerId", Integer.parseInt(customerId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCustomerOperation(obj, OperationType.GET).toString();
	}
	
	@DeleteMapping("/customer")
	public String deleteCustomer(String customerId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("customerId", Integer.parseInt(customerId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCustomerOperation(obj, OperationType.DELETE).toString();
	}
	
	@GetMapping("/customer/all")
	public String getAllCustomers() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleCustomerOperation(null, OperationType.ALL).toString();
	}
	
}