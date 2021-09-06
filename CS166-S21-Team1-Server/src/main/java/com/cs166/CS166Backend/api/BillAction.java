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
public class BillAction {
	
	@GetMapping("/bill")
	public String getBill(String billId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("billId", Integer.parseInt(billId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillOperation(obj, OperationType.GET).toString();
	}
	
	
	@PostMapping("/bill")
	public String createBill(String requestId, String billDate, String tax, String discount, String laborCost, String totalCost) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("requestId", Integer.parseInt(requestId));
		obj.put("billDate", billDate);
		obj.put("tax", Double.valueOf(tax));
		obj.put("discount", Double.valueOf(discount));
		obj.put("laborCost", Double.valueOf(laborCost));
		obj.put("totalCost", Double.valueOf(totalCost));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillOperation(obj, OperationType.POST).toString();
	}
	
	@PutMapping("/bill")
	public String updateBill(String billId, String requestId, String billDate, String tax, String discount, String laborCost, String totalCost) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("billId", Integer.parseInt(billId));
		obj.put("requestId", Integer.parseInt(requestId));
		obj.put("billDate", billDate);
		obj.put("tax", Double.valueOf(tax));
		obj.put("discount", Double.valueOf(discount));
		obj.put("laborCost", Double.valueOf(laborCost));
		obj.put("totalCost", Double.valueOf(totalCost));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillOperation(obj, OperationType.PUT).toString();
	}
	
	@DeleteMapping("/bill")
	public String deleteBill(String billId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("billId", Integer.parseInt(billId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillOperation(obj, OperationType.DELETE).toString();
	}
	

}
