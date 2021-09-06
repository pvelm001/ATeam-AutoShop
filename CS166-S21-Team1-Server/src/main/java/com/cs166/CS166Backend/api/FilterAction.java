package com.cs166.CS166Backend.api;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs166.CS166Backend.Cs166BackendApplication;
import com.cs166.CS166Backend.database.DBUtil;
import com.cs166.CS166Backend.utils.OperationType;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FilterAction {
	
	@GetMapping("/filter/six")
	public String getFilterSix() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleFilterOperation(null, OperationType.FILTER_SIX).toString();
	}
	
	@GetMapping("/filter/seven")
	public String getFilterSeven() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleFilterOperation(null, OperationType.FILTER_SEVEN).toString();
	}
	
	@GetMapping("/filter/eight")
	public String getFilterEight() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleFilterOperation(null, OperationType.FILTER_EIGHT).toString();
	}
	
	@GetMapping("/filter/nine") //Yet to complete
	public String getFilterNine(String k) throws Exception
	{
		
		JSONObject obj = new JSONObject();
		obj.put("k", Integer.parseInt(k));
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleFilterOperation(obj, OperationType.FILTER_NINE).toString();
	}
	
	@GetMapping("/filter/ten")
	public String getFilterTen() throws Exception
	{
		
		DBUtil dbUtil = Cs166BackendApplication.dbUtil;
		return dbUtil.handleFilterOperation(null, OperationType.FILTER_TEN).toString();
	}
	

}
