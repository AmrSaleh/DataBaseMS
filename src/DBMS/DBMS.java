package DBMS;

import java.util.ArrayList;
import java.util.Stack;

public interface  DBMS {
	
	
	
	  //////////////////////////////////////////////////////////////////////////// CREATE TABLE
    // CREATE DATABASE my_db;
	public void createDatabase (String Name ) throws Exception;
	//////////////////////////////////////////////////////////////////////////// set dataBase
	public void setDatabase (String database) throws Exception;
	
	
	//////////////////////////////////////////////////////////////////////////// CREARTE TABLE
	
// 	CREATE TABLE table_name
// (
// column_name1 data_type(size),
// column_name2 data_type(size),
// column_name3 data_type(size),
// ....
// );
	public void createTable (String tableName, ArrayList<Column> columns) throws Exception;
	
	// the new object Column consites of 2 or more parameters but for this phase 2 only
	// 1) col name
	// 2) data type
	
	//////////////////////////////////////////////////////////////////////////// INSERT
	// INSERT INTO table_name
    // VALUES (value1,value2,value3,...);
	public void insert (String tableName,ArrayList<String> data) throws Exception;
    
    //  INSERT INTO table_name (column1,column2,column3,...)
    //  VALUES (value1,value2,value3,...);
	public void insert (String tableName,ArrayList<String> cols,ArrayList<String> data)throws Exception;
	
	//////////////////////////////////////////////////////////////////////////// SELECT
	
	
//	// select *
//	public Table selectTable(String tablename);
//	// select columns 
//	public Table selectTable1(String tablename,ArrayList<String> returnCols);
//    /// select * WHERE 
//    public Table selectTable(String tablename,ArrayList<ArrayList<ComparisonStatement>> arr);
//	/// select cloums WHERE 
//    public Table selectTable(String tablename,ArrayList<String> returnCols,ArrayList<ArrayList<ComparisonStatement>> arr) throws Exception;
		
		/*
		ComparisonStatment is a new object consisting of three attributes 
		1) String col name 
		2) String value 
		3) String operation > < = 
		
		the 2D arraylist will allow us do some requirements in the next part but for this part the inner arraylist
		will be of size 1 for only 1 operation 
		
		in this part there is one operation only create 2D arraylist then add 1 object in the first inner array
		
		*/
		
	//////////////////////////////////////////////////////////////////////////// DELETE	
	// "delete table_name"  or "delete * FROM table_table "	
	public void delete(String tablename) throws Exception;
	
	// DELETE FROM Customers
	// WHERE CustomerName='Alfreds Futterkiste' ; 
//    public void delete(String tablename,ArrayList<ArrayList<ComparisonStatement>> arr) throws Exception;	
    
    ////////////////////////////////////////////////////////////////////////////  UPDATE
    
    // UPDATE * FROM Customers
    // SET ContactName='Alfred Schmidt', City='Hamburg'; 
    // or
    // UPDATE Customers
    // SET ContactName='Alfred Schmidt', City='Hamburg'; 
    
    // A warning should be sent at this method because it will update all columns with 1 value
    public void update(String tableName,ArrayList<String> cols,ArrayList<String> data ) throws Exception;
    
    
    // UPDATE Customers
    // SET ContactName='Alfred Schmidt', City='Hamburg'
    // WHERE CustomerName='Alfreds Futterkiste'; 
//    public void update(String tableName,ArrayList<String> cols,ArrayList<String> data,ArrayList<ArrayList<ComparisonStatement> > arr ) throws Exception;
	Table selectTable(String tableName, ArrayList<String> returnCols,
			Stack<Object> postFixStk) throws Exception;
	void update(String tableName, ArrayList<String> cols,
			ArrayList<String> data, Stack<Object> postFixStk) throws Exception;
	void delete(String tableName, Stack<Object> postFix) throws Exception;
	 
	 
}
