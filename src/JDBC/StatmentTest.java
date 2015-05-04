package JDBC;

import java.sql.SQLException;

import org.junit.Test;

import DBMS.DBMSTest;
import DBMS.MyDBMS;

import junit.framework.TestCase;

public class StatmentTest extends TestCase {

	Statement test;
	MyDBMS r = new MyDBMS();
	Connection con ;

	
	public void intialize ()
	{
		con = new Connection();

		test = new Statement(con);
		try {
			test.execute("create database gggggggggggggg;");
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
			
			
	
	
	
	
	
	
	
	@Test
	public void testExecute ()
	{
	 intialize();
	 try {
	  assertFalse(test.execute("Create Table y {name String , age int};"));
	  assertFalse(test.execute("Insert Into y Values ('Reham', '19');"));
	  assertTrue(test.execute("select * from y;"));
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	// add Batch & execute Batch
	@Test
	public void testExecuteBatch ()
	{
		intialize();
        try {
        	test.addBatch("Create Table r {name String , age int}");
			test.addBatch("Create Table r {name String , age int};");
	        test.addBatch("Insert Into r Values ('Reham', '19');");
            test.addBatch("select * from r;");
            int [] result = test.executeBatch();
            assertEquals(0, result[0]);
            assertEquals(1, result[1]);
            assertEquals(1, result[2]);
            assertEquals(1, result[3]);
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@Test
	public void testExecuteUpdate ()
	{
		intialize();
		
		try {
			assertEquals(1,test.executeUpdate("Create Table h {name String , age int};"));
			assertEquals(0,test.executeUpdate("Select * from h;"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetConnection ()
	{

		intialize();
		try {
			assertEquals(con,test.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
    
	
	
	
	
	
	
	
	
	
}
