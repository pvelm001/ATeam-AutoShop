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
public class BillDetailAction {
	
	@GetMapping("/billdetail/all/billid")
	public String getAllBillDetailForBillId(String billId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("billId", Integer.parseInt(billId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillDetailOperation(obj, OperationType.GET_ALL_BILL_DETAILS_FOR_BILL_ID).toString();
	}
	
	
	@PostMapping("/billdetail")
	public String createBillDetail(String billId, String billDescription, String quantity, String cost) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("billId", Integer.parseInt(billId));
		obj.put("billDescription", billDescription);
		obj.put("quantity", Integer.parseInt(quantity));
		obj.put("cost", Double.valueOf(cost));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillDetailOperation(obj, OperationType.POST).toString();
	}
	
	@PutMapping("/billdetail")
	public String updateBillDetail(String billDetailId, String billId, String billDescription, String quantity, String cost) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("billDetailId", Integer.parseInt(billDetailId));
		obj.put("billId", Integer.parseInt(billId));
		obj.put("billDescription", billDescription);
		obj.put("quantity", Integer.parseInt(quantity));
		obj.put("cost", Double.valueOf(cost));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillDetailOperation(obj, OperationType.PUT).toString();
	}
	
	@DeleteMapping("/billdetail")
	public String deleteBillDetail(String billDetailId) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("billDetailId", Integer.parseInt(billDetailId));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleBillDetailOperation(obj, OperationType.DELETE).toString();
	}

}
