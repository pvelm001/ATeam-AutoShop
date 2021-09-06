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
public class MechanicAction {
	
	@PostMapping("/mechanic")
	public String createMechanic(String firstName, String lastName, String yearsOfExp, String salary, String rating, String speciality, String role) throws Exception
	{
		
		JSONObject obj = new JSONObject();
		obj.put("firstName", firstName);
		obj.put("lastName", lastName);
		obj.put("yearsOfExp", Integer.parseInt(yearsOfExp));
		obj.put("salary", Float.parseFloat(salary));
		obj.put("rating", Integer.parseInt(rating));
		obj.put("speciality", speciality);
		obj.put("role", role);
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleMechanicOperation(obj, OperationType.POST).toString();
	}
	
	
	@PutMapping("/mechanic")
	public String updateMechanic(String employeeId, String firstName, String lastName, String yearsOfExp, String salary, String rating, String speciality, String role) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("employeeId", Integer.parseInt(employeeId));
		obj.put("firstName", firstName);
		obj.put("lastName", lastName);
		obj.put("yearsOfExp", Integer.parseInt(yearsOfExp));
		obj.put("salary", Long.parseLong(salary));
		obj.put("rating", Integer.parseInt(rating));
		obj.put("speciality", speciality);
		obj.put("role", role);
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleMechanicOperation(obj, OperationType.PUT).toString();
	}
	
	@GetMapping("/mechanic")
	public String getMechanic(String employeeId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("employeeId", Integer.parseInt(employeeId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleMechanicOperation(obj, OperationType.GET).toString();
	}
	
	@DeleteMapping("/mechanic")
	public String deleteMechanic(String employeeId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("employeeId", Integer.parseInt(employeeId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleMechanicOperation(obj, OperationType.DELETE).toString();
	}
	
	@GetMapping("/mechanic/all")
	public String getAllMechanics() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleMechanicOperation(null, OperationType.ALL).toString();
	}

}
