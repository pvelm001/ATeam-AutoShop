package com.cs166.CS166Backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'MySQL'
 *
 */


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.cs166.CS166Backend.pojo.Bill;
import com.cs166.CS166Backend.pojo.BillDetail;
import com.cs166.CS166Backend.pojo.Car;
import com.cs166.CS166Backend.pojo.CarMeta;
import com.cs166.CS166Backend.pojo.Customer;
import com.cs166.CS166Backend.pojo.Mechanic;
import com.cs166.CS166Backend.pojo.ServiceRequest;
import com.google.common.base.Throwables;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with MySQL JDBC drivers.
 *
 */

public class MechanicShop{
	//reference to physical database connection
	
	private static Logger LOGGER = Logger.getLogger(MechanicShop.class.getName());
	
	private static final String CUSTOMER_TABLE = "Customer";
	private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO " + CUSTOMER_TABLE + "(first_name, last_name, phone_number, address) VALUES ('%s','%s','%s','%s'); ";
	private static final String UPDATE_CUSTOMER_QUERY = "UPDATE " + CUSTOMER_TABLE + " SET first_name = '%s', last_name = '%s', phone_number = '%s', address = '%s' WHERE customer_id = '%d'; ";
	private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM " + CUSTOMER_TABLE + " WHERE customer_id = '%d' ;";
	private static final String GET_CUSTOMER_QUERY = "SELECT * FROM " + CUSTOMER_TABLE + " WHERE customer_id = '%d'; ";
	private static final String GET_ALL_CUSTOMER_QUERY = "SELECT * FROM " + CUSTOMER_TABLE + ";";
	
	private static final String MECHANIC_TABLE = "Mechanic";
	private static final String INSERT_MECHANIC_QUERY = "INSERT INTO " + MECHANIC_TABLE + "(first_name, last_name, yearsOfExp, salary, rating, speciality, roleName) VALUES ('%s','%s', %d, %d, %d, '%s', '%s'); ";
	private static final String UPDATE_MECHANIC_QUERY = "UPDATE " + MECHANIC_TABLE + " SET first_name = '%s', last_name = '%s', yearsOfExp = '%d', salary = '%d', rating = '%d', speciality = '%s', roleName = '%s' WHERE employee_id = '%d'; ";
	private static final String DELETE_MECHANIC_QUERY = "DELETE FROM " + MECHANIC_TABLE + " WHERE employee_id = '%d' ;";
	private static final String GET_MECHANIC_QUERY = "SELECT * FROM " + MECHANIC_TABLE + " WHERE employee_id = '%d'; ";
	private static final String GET_ALL_MECHANIC_QUERY = "SELECT * FROM " + MECHANIC_TABLE + ";";
	
	private static final String CARMETA_TABLE = "CarMeta";
	private static final String GET_CAR_ID_FROM_META_QUERY = "SELECT car_id FROM "+ CARMETA_TABLE + " WHERE make = '%s' AND model = '%s' AND color = '%s' AND yearOfManufacture = '%s' AND transmission = '%c' AND average_mileage= '%f' ;";
	private static final String GET_CAR_META_FROM_CAR_ID_QUERY = "SELECT * FROM "+ CARMETA_TABLE + " WHERE car_id= '%d' ;";
	private static final String GET_ALL_CAR_META_QUERY = "SELECT * FROM "+ CARMETA_TABLE + " ;";
	private static final String INSERT_CAR_META_QUERY = "INSERT INTO " + CARMETA_TABLE + "(make, model, color, yearOfManufacture, transmission, average_mileage) VALUES ('%s','%s', '%s', '%s', '%c', '%f'); ";
	
	private static final String CAR_TABLE = "Car";
	private static final String INSERT_CAR_QUERY = "INSERT INTO " + CAR_TABLE + "(vin, customer_id, car_id) VALUES ('%s','%d', %d); ";
	private static final String UPDATE_CAR_QUERY = "UPDATE " + CAR_TABLE + " SET vin = '%s', customer_id = '%d', car_id = '%d' WHERE vin = '%s'; ";
	private static final String DELETE_CAR_QUERY = "DELETE FROM " + CAR_TABLE + " WHERE vin = '%s' ;";
	private static final String GET_CAR_QUERY = "SELECT * FROM " + CAR_TABLE + " WHERE vin = '%s'; ";
	private static final String GET_CARS_FOR_CUSTOMER = "SELECT * FROM " + CAR_TABLE + " WHERE customer_id = '%d'; ";
	private static final String GET_ALL_CARS = "SELECT vin, " + CUSTOMER_TABLE + ".customer_id, " + CAR_TABLE + ".car_id, make, model, color, yearOfManufacture, transmission, average_mileage FROM " + CAR_TABLE + ", " + CUSTOMER_TABLE + ", " + CARMETA_TABLE + " WHERE "+ CAR_TABLE + ".customer_id = " + CUSTOMER_TABLE + ".customer_id AND " + CAR_TABLE + ".car_id = "+ CARMETA_TABLE + ".car_id; ";
	
	private static final String SERVICE_REQUEST_TABLE = "ServiceRequest";
	private static final String GET_SERVICE_REQUEST_QUERY = "SELECT * FROM "+ SERVICE_REQUEST_TABLE + " WHERE request_id = '%d' ;";
	private static final String INSERT_SERVICE_REQUEST_QUERY = "INSERT INTO "+ SERVICE_REQUEST_TABLE + " (customer_id, vin, allocated_to, status_id, date_in, comments, odometer, fuel_level) VALUES ('%d', '%s', '%d', '%d', '%s', '%s', '%.2f', '%d') ;";
	private static final String UPDATE_SERVICE_REQUEST_QUERY = "UPDATE "+ SERVICE_REQUEST_TABLE + " SET  allocated_to='%d', status_id='%d', date_in = '%s', comments = '%s', odometer = '%.2f', fuel_level = '%d'  WHERE request_id= '%d' ;";
	private static final String UPDATE_SERVICE_REQUEST_CLOSE_QUERY = "UPDATE "+ SERVICE_REQUEST_TABLE + " SET bill_id = '%d', allocated_to='%d', status_id='%d', date_in = '%s', date_out = '%s', comments = '%s', odometer = '%.2f', fuel_level = '%d'  WHERE request_id= '%d' ;";
	private static final String GET_ALL_SERVICE_REQUEST_QUERY = "SELECT * FROM "+ SERVICE_REQUEST_TABLE + " ;";
	
	private static final String BILL_TABLE = "Bill";
	private static final String GET_BILL_QUERY = "SELECT * FROM "+ BILL_TABLE + " WHERE bill_id = '%d' ;";
	private static final String INSERT_BILL_QUERY = "INSERT INTO "+ BILL_TABLE + " (request_id, billDate, tax, discount, labor_cost, total_cost) VALUES ('%d', '%s', '%.2f', '%.2f', '%.2f', '%.2f') ;";
	private static final String UPDATE_BILL_QUERY = "UPDATE "+ BILL_TABLE + " SET request_id = '%d', billDate = '%s', tax = '%.2f', discount = '%.2f', labor_cost='%.2f', total_cost='%.2f'  WHERE bill_id= '%d' ;";
	private static final String DELETE_BILL_QUERY = "DELETE FROM "+ BILL_TABLE + " WHERE bill_id = '%d' ;";
	private static final String GET_BILL_ID_FROM_REQUEST_ID_QUERY = "SELECT bill_id FROM "+ BILL_TABLE + " WHERE request_id = '%d'; ";
	
	private static final String BILL_DETAIL_TABLE = "BillDetail";
	private static final String GET_BILL_DETAIL_FOR_BILL_ID_QUERY = "SELECT * FROM "+ BILL_DETAIL_TABLE + " WHERE bill_id = '%d' ;";
	private static final String INSERT_BILL_DETAIL_QUERY = "INSERT INTO "+ BILL_DETAIL_TABLE + " (bill_id, bill_description, quantity, cost) VALUES ('%d', '%s', '%d', '%.2f') ;";
	private static final String UPDATE_BILL_DETAIL_QUERY = "UPDATE "+ BILL_DETAIL_TABLE + " SET bill_id = '%d', bill_description = '%s', quantity = '%d', cost='%.2f'  WHERE bill_detail_id= '%d' ;";
	private static final String DELETE_BILL_DETAIL_QUERY = "DELETE FROM "+ BILL_DETAIL_TABLE + " WHERE bill_detail_id = '%d' ;";
	
	private static final String FILTER_SIX_QUERY = "SELECT date_in, date_out, comments, "+BILL_TABLE+".total_cost FROM "+SERVICE_REQUEST_TABLE+", "+BILL_TABLE+" WHERE "+SERVICE_REQUEST_TABLE+".request_id = "+BILL_TABLE+".request_id AND total_cost < 100 AND status_id = 0";
	private static final String FILTER_SEVEN_QUERY = "SELECT first_name, last_name FROM "+CUSTOMER_TABLE+" C1 WHERE 20 < (SELECT count(*) FROM "+CAR_TABLE+" CA2 WHERE CA2.customer_id = C1.customer_id);";
	private static final String FILTER_EIGHT_QUERY = "select CM.make, CM.model, CM.yearOfManufacture FROM "+CARMETA_TABLE+" CM, "+CAR_TABLE+" C, "+SERVICE_REQUEST_TABLE+" SR WHERE CM.car_id = C.car_id AND C.vin = SR.vin AND SR.odometer < 50000 AND CM.yearOfManufacture < 1995;";
	private static final String FILTER_NINE_QUERY = "SELECT CM.make, CM.model, count(*) as total FROM "+CARMETA_TABLE+" CM, "+CAR_TABLE+" C1, "+SERVICE_REQUEST_TABLE+" SR where CM.car_id=C1.car_id AND C1.vin=SR.vin GROUP BY SR.VIN ORDER BY total desc limit %d;";
	private static final String FILTER_TEN_QUERY = "select C1.first_name, C1.last_name, SUM(B1.total_cost) AS total_cost FROM "+CUSTOMER_TABLE+" C1, "+BILL_TABLE+" B1, "+SERVICE_REQUEST_TABLE+" SR WHERE C1.customer_id = SR.customer_id AND SR.request_id = B1.request_id GROUP BY C1.customer_id ORDER BY total_cost DESC;";
	
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public MechanicShop(String dbname, String dbport, String user, String passwd) throws SQLException {
		System.out.print("Connecting to database...");
		try{
			// constructs the connection URL
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:" + dbport + "/" + dbname;
			System.out.println ("Connection URL: " + url + "\n");
			
			// obtain a physical connection
	        this._connection = DriverManager.getConnection(url, user, passwd);
	        
	        System.out.println("Done");
		}catch(Exception e){
			System.err.println("Error - Unable to Connect to Database: " + Throwables.getStackTraceAsString(e));
	        System.out.println("Make sure you started MySQL on this machine");
	        System.exit(-1);
		}
	}
	
	/**
	 * Method to execute an update SQL statement.  Update SQL instructions
	 * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
	 * 
	 * @param sql the input SQL string
	 * @throws java.sql.SQLException when update failed
	 * */
	public void executeUpdate (String sql) throws SQLException { 
		// creates a statement object
		Statement stmt = this._connection.createStatement ();

		// issues the update instruction
		stmt.executeUpdate (sql);

		// close the instruction
	    stmt.close ();
	}//end executeUpdate

	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and outputs the results to
	 * standard out.
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQueryAndPrintResult (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		/*
		 *  obtains the metadata object for the returned result set.  The metadata
		 *  contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCol = rsmd.getColumnCount ();
		int rowCount = 0;
		
		//iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t");
			    }
			    System.out.println();
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i)
				System.out.print (rs.getString (i) + "\t");
			System.out.println ();
			++rowCount;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the results as
	 * a list of records. Each record in turn is a list of attribute values
	 * 
	 * @param query the input query string
	 * @return the query result as a list of records
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
		//creates a statement object 
		Statement stmt = this._connection.createStatement (); 
		
		//issues the query instruction 
		ResultSet rs = stmt.executeQuery (query); 
	 
		/*
		 * obtains the metadata object for the returned result set.  The metadata 
		 * contains row and column info. 
		*/ 
		ResultSetMetaData rsmd = rs.getMetaData (); 
		int numCol = rsmd.getColumnCount (); 
		int rowCount = 0; 
	 
		//iterates through the result set and saves the data returned by the query. 
		boolean outputHeader = false;
		List<List<String>> result  = new ArrayList<List<String>>(); 
		while (rs.next()){
			List<String> record = new ArrayList<String>(); 
			for (int i=1; i<=numCol; ++i) 
				record.add(rs.getString (i)); 
			result.add(record); 
		}//end while 
		stmt.close (); 
		return result; 
	}//end executeQueryAndReturnResult
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the number of results
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQuery (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the result set and count nuber of results.
		if(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to fetch the last value from sequence. This
	 * method issues the query to the DBMS and returns the current 
	 * value of sequence used for autogenerated keys
	 * 
	 * @param sequence name of the DB sequence
	 * @return current value of a sequence
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	
	public int getCurrSeqVal(String sequence) throws SQLException {
		Statement stmt = this._connection.createStatement ();
		
		ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
		if (rs.next()) return rs.getInt(1);
		return -1;
	}

	/**
	 * Method to close the physical connection if it is open.
	 */
	public void cleanup(){
		try{
			if (this._connection != null){
				this._connection.close ();
			}//end if
		}catch (SQLException e){
	         // ignored.
		}//end try
	}//end cleanup

	/*
	 * Customer
	 */
	
	public List<List<String>> getAllCustomers() throws Exception{//1
		
		String query = String.format(GET_ALL_CUSTOMER_QUERY);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public Boolean createCustomer(Customer customer) throws Exception{//1
		
		String query = String.format(INSERT_CUSTOMER_QUERY, customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getAddress());
		executeUpdate(query);
		return true;
	}
	public Boolean updateCustomer(Customer customer) throws Exception{//1
		
		String query = String.format(UPDATE_CUSTOMER_QUERY, customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getAddress(), customer.getCustomerId());
		executeUpdate(query);
		return true;
	}
	public Boolean deleteCustomer(Customer customer) throws Exception{//1
		
		String query = String.format(DELETE_CUSTOMER_QUERY, customer.getCustomerId());
		executeUpdate(query);
		return true;
	}
	public List<List<String>> getCustomer(Customer customer) throws Exception{//1
		
		String query = String.format(GET_CUSTOMER_QUERY, customer.getCustomerId());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	
	/*
	 * Mechanic
	 */
	
	public List<List<String>> getAllMechanics() throws Exception{//1
		
		String query = String.format(GET_ALL_MECHANIC_QUERY);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public Boolean createMechanic(Mechanic mechanic) throws Exception{//1
		
		String query = String.format(INSERT_MECHANIC_QUERY, mechanic.getFirstName(), mechanic.getLastName(), mechanic.getYearsOfExp(), mechanic.getSalary(), mechanic.getRating(), mechanic.getSpeciality(), mechanic.getRole());
		executeUpdate(query);
		return true;
	}
	public Boolean updateMechanic(Mechanic mechanic) throws Exception{//1
		
		String query = String.format(UPDATE_MECHANIC_QUERY, mechanic.getFirstName(), mechanic.getLastName(), mechanic.getYearsOfExp(), mechanic.getSalary(), mechanic.getRating(), mechanic.getSpeciality(), mechanic.getRole(), mechanic.getEmployeeId());
		executeUpdate(query);
		return true;
	}
	public Boolean deleteMechanic(Mechanic mechanic) throws Exception{//1
		
		String query = String.format(DELETE_MECHANIC_QUERY, mechanic.getEmployeeId());
		executeUpdate(query);
		return true;
	}
	public List<List<String>> getMechanic(Mechanic mechanic) throws Exception{//1
		
		String query = String.format(GET_MECHANIC_QUERY, mechanic.getEmployeeId());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	/*
	 * Car Meta
	 */
	
	public List<List<String>> getCarIdFromMeta(CarMeta carMeta) throws Exception{
		String query = String.format(GET_CAR_ID_FROM_META_QUERY, carMeta.getMake(), carMeta.getModel(), carMeta.getColor(), carMeta.getYearOfManufacture(), carMeta.getTransmission(), carMeta.getAverageMileage());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	public List<List<String>> getCarMetaFromCarId(CarMeta carMeta) throws Exception{
		String query = String.format(GET_CAR_META_FROM_CAR_ID_QUERY, carMeta.getCarId());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public List<List<String>> getAllCarMeta() throws Exception{
		String query = String.format(GET_ALL_CAR_META_QUERY);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public List<List<String>> createCarMeta(CarMeta carMeta) throws Exception{
		String query = String.format(INSERT_CAR_META_QUERY, carMeta.getMake(), carMeta.getModel(), carMeta.getColor(), carMeta.getYearOfManufacture(), carMeta.getTransmission(), carMeta.getAverageMileage());
		executeUpdate(query);
		return getCarIdFromMeta(carMeta);
	}
	
	/*
	 * Car
	 */
	public List<List<String>> getAllCars() throws Exception{
		String query = String.format(GET_ALL_CARS);
		LOGGER.log(Level.INFO, query);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public List<List<String>> getAllCarsForCustomer(Car car) throws Exception{//1
		
		String query = String.format(GET_CARS_FOR_CUSTOMER, car.getCustomerId());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public Boolean createCar(Car car) throws Exception{//1
		
		String query = String.format(INSERT_CAR_QUERY, car.getVin(), car.getCustomerId(), car.getCarId());
		executeUpdate(query);
		return true;
	}
	public Boolean updateCar(Car car) throws Exception{//1
		
		String query = String.format(UPDATE_CAR_QUERY, car.getVin(), car.getCustomerId(), car.getCarId(), car.getVin());
		executeUpdate(query);
		return true;
	}
	public Boolean deleteCar(Car car) throws Exception{//1
		
		String query = String.format(DELETE_CAR_QUERY, car.getVin());
		executeUpdate(query);
		return true;
	}
	public List<List<String>> getCar(Car car) throws Exception{//1
		
		String query = String.format(GET_CAR_QUERY, car.getVin());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	/*
	 * Service Request
	 */
	
	public List<List<String>> getAllServiceRequests() throws Exception{//1
		
		String query = String.format(GET_ALL_SERVICE_REQUEST_QUERY);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public Boolean createServiceRequest(ServiceRequest sr) throws Exception{//1
		
		String query = String.format(INSERT_SERVICE_REQUEST_QUERY, sr.getCustomerId(), sr.getVin(), sr.getAllocatedTo(), sr.getStatusId(), sr.getDateIn(), sr.getComments(), sr.getOdometer(), sr.getFuelLevel()); 
		executeUpdate(query);
		return true;
	}
	public Boolean updateServiceRequest(ServiceRequest sr) throws Exception{//1
		
		String query = String.format(UPDATE_SERVICE_REQUEST_QUERY, sr.getAllocatedTo(), sr.getStatusId(), sr.getDateIn(), sr.getComments(), sr.getOdometer(), sr.getFuelLevel(), sr.getRequestId());
		executeUpdate(query);
		return true;
	}
	public Boolean updateServiceRequestWBill(ServiceRequest sr) throws Exception{
		String query = String.format(UPDATE_SERVICE_REQUEST_CLOSE_QUERY, sr.getBillId(), sr.getAllocatedTo(), sr.getStatusId(), sr.getDateIn(), sr.getDateOut(), sr.getComments(), sr.getOdometer(), sr.getFuelLevel(), sr.getRequestId());
		LOGGER.log(Level.INFO,"Query = "+query);
		executeUpdate(query);
		return true;
	}
	public List<List<String>> getServiceRequest(ServiceRequest sr) throws Exception{//1
		
		String query = String.format(GET_SERVICE_REQUEST_QUERY, sr.getRequestId());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	/*
	 * Bill
	 */
	public List<List<String>> getBill(Bill bill) throws Exception{//1
		
		String query = String.format(GET_BILL_QUERY, bill.getBillId());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public List<List<String>> createBill(Bill bill) throws Exception{//1
		
		String query = String.format(INSERT_BILL_QUERY, bill.getRequestId(), bill.getBillDate(), bill.getTax(), bill.getDiscount(), bill.getLaborCost(), bill.getTotalCost());
		LOGGER.log(Level.INFO, "Query = "+query);
		executeUpdate(query);
		return getBillIdFromRequestId(bill);
	}
	public Boolean updateBill(Bill bill) throws Exception{//1
		
		String query = String.format(UPDATE_BILL_QUERY, bill.getRequestId(), bill.getBillDate(), bill.getTax(), bill.getDiscount(), bill.getLaborCost(), bill.getTotalCost(), bill.getBillId());
		executeUpdate(query);
		return true;
	}
	public Boolean deleteBill(Bill bill) throws Exception{//1
		
		String query = String.format(DELETE_BILL_QUERY, bill.getBillId());
		executeUpdate(query);
		return true;
	}
	public List<List<String>> getBillIdFromRequestId(Bill bill) throws Exception
	{
		String query = String.format(GET_BILL_ID_FROM_REQUEST_ID_QUERY, bill.getRequestId());
		LOGGER.log(Level.INFO, "Query = "+query);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	/*
	 * Bill Detail
	 */
	public List<List<String>> getAllBillDetailsForBillId(BillDetail billDetail) throws Exception{//1
		
		String query = String.format(GET_BILL_DETAIL_FOR_BILL_ID_QUERY, billDetail.getBillId());
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	public Boolean createBillDetail(BillDetail billDetail) throws Exception{//1
		
		String query = String.format(INSERT_BILL_DETAIL_QUERY, billDetail.getBillId(), billDetail.getBillDescription(), billDetail.getQuantity(), billDetail.getCost()); 
		executeUpdate(query);
		return true;
	}
	public Boolean updateBillDetail(BillDetail billDetail) throws Exception{//1
		
		String query = String.format(UPDATE_BILL_DETAIL_QUERY, billDetail.getBillId(), billDetail.getBillDescription(), billDetail.getQuantity(), billDetail.getCost(), billDetail.getBillDetailId());
		executeUpdate(query);
		return true;
	}
	public Boolean deleteBillDetail(BillDetail billDetail) throws Exception{//1
		
		String query = String.format(DELETE_BILL_DETAIL_QUERY, billDetail.getBillDetailId());
		executeUpdate(query);
		return true;
	}
	
	/*
	 * Filter Queries
	 */
	
	public List<List<String>> filterSix() throws Exception {
		
		String query = String.format(FILTER_SIX_QUERY);
		LOGGER.log(Level.INFO, "Query = "+query);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	public List<List<String>> filterSeven() throws Exception {
		
		String query = String.format(FILTER_SEVEN_QUERY);
		LOGGER.log(Level.INFO, "Query = "+query);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	public List<List<String>> filterEight() throws Exception {
		
		String query = String.format(FILTER_EIGHT_QUERY);
		LOGGER.log(Level.INFO, "Query = "+query);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	public List<List<String>> filterNine(JSONObject filterJson) throws Exception {
		
		String query = String.format(FILTER_NINE_QUERY, filterJson.getInt("k"));
		LOGGER.log(Level.INFO, "Query = "+query);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	public List<List<String>> filterTen() throws Exception {
		
		String query = String.format(FILTER_TEN_QUERY);
		LOGGER.log(Level.INFO, "Query = "+query);
		List<List<String>> resp = executeQueryAndReturnResult(query);
		return resp;
	}
	
	public static void InsertServiceRequest(MechanicShop esql)throws Exception{//4
		
		
	}
	
	public static void CloseServiceRequest(MechanicShop esql) throws Exception{//5
		
	}
	
	public static void ListCustomersWithBillLessThan100(MechanicShop esql){//6
		
	}
	
	public static void ListCustomersWithMoreThan20Cars(MechanicShop esql){//7
		
	}
	
	public static void ListCarsBefore1995With50000Milles(MechanicShop esql){//8
		
	}
	
	public static void ListKCarsWithTheMostServices(MechanicShop esql){//9
		//
		
	}
	
	public static void ListCustomersInDescendingOrderOfTheirTotalBill(MechanicShop esql){//9
		//
		
	}
	
}