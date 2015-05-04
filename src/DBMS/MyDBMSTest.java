package DBMS;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

public class MyDBMSTest {
	DBMS tester = new MyDBMS();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		MyDBMS.initializeDBMSFolder();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
//	@Test
//	public void testInitializeDBMSFolder() {
//		fail("Not yet implemented");
//	}

	@Test(expected = Exception.class)
	public void testCreateDatabase() throws Exception {
		
		  tester.createDatabase("JunitTest");
//	assertEquals( , );
		    
//		fail("Not yet implemented");
	}

	@Test(expected = Exception.class)
	public void testSetDatabase() throws Exception {
		tester.setDatabase("JunitTest");
		tester.setDatabase("JunitTestasa");
//		fail("Not yet implemented");
	}

//	@Test(expected = Exception.class)
//	public void testCreateTable() throws Exception {
//		ArrayList<Column> col = new ArrayList<>();
//		col.add(new Column("Name", "String"));
//		col.add(new Column("ID", "int"));
//		tester.setDatabase("JunitTest");
//		 tester.createTable("JtestTable2", col);
//		
////		fail("Not yet implemented");
//	}

	@Test(expected = Exception.class)
	public void testInsertStringArrayListOfString() throws Exception {
		ArrayList<String> test = new ArrayList<String>();
		 test.add("amr");
		test.add("8");
		
		ArrayList<String> colnames = new ArrayList<String>();
		 colnames.add("Name");
		colnames.add("ID");
		
		tester.insert("bassem",/* colnames,*/ test);
		
		
//		fail("Not yet implemented");
	}

	@Test(expected = Exception.class)
	public void testInsertStringArrayListOfStringArrayListOfString() throws Exception {
		ArrayList<String> test = new ArrayList<String>();
		 test.add("amr");
		test.add("8");
		
		ArrayList<String> colnames = new ArrayList<String>();
		 colnames.add("Name");
		colnames.add("ID");
		tester.insert("bassem", colnames, test);
		tester.insert("bassem", colnames, test);
		
//		fail("Not yet implemented");
	}

	@Test
	public void testSelectTable() {
//		fail("Not yet implemented");
	}

	@Test
	public void testDeleteString() {
//		fail("Not yet implemented");
	}

	@Test
	public void testDeleteStringArrayListOfArrayListOfComparisonStatement() {
//		fail("Not yet implemented");
	}

	@Test
	public void testUpdateStringArrayListOfStringArrayListOfString() {
//		fail("Not yet implemented");
	}

	@Test
	public void testUpdateStringArrayListOfStringArrayListOfStringArrayListOfArrayListOfComparisonStatement() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetDatabase() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetPath() {
//		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
//		fail("Not yet implemented");
	}

	@Test
	public void testIsInteger() {
//		fail("Not yet implemented");
	}
//	
//	 @Test(expected = Exception.class)
//	   //assert test. General case for any test when first param is less than 1000 and second param is any value
//	   public void testMultiply() throws Exception {
//	     DBMS tester = new MyDBMS();
//	  tester.createDatabase("n001010");
//	     //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
//	     //fail();
//	   }

}
