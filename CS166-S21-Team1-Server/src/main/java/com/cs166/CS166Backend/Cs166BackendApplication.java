package com.cs166.CS166Backend;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cs166.CS166Backend.database.DBUtil;
import com.google.common.base.Throwables;

@SpringBootApplication
public class Cs166BackendApplication {
	
	private static Logger LOGGER = Logger.getLogger(Cs166BackendApplication.class.getName());
	
	public static DBUtil dbUtil;
	
	public static void main(String[] args) {
		SpringApplication.run(Cs166BackendApplication.class, args);
		Cs166BackendApplication app = new Cs166BackendApplication();
		app.setupMySQL();
	}
	
	private void setupMySQL()
	{
		try
		{
			dbUtil = new DBUtil();
		}catch(Exception e)
		{
			LOGGER.log(Level.INFO, "Error in main = "+Throwables.getStackTraceAsString(e));
		}
	}
}
