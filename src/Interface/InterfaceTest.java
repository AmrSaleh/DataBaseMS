package Interface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import DBMS.MyDBMS;
import Parser.Parser;
import Parser.ParserInterface;

public class InterfaceTest {

  @BeforeClass
  public static void testSetup() { //prepare the testing
//	  MyDBMS.initializeDBMSFolder();
  }

  @AfterClass
  public static void testCleanup() { //clear any data after testing
    // Teardown for data used by the unit tests
  }
  //the above part is a preparation for the test
/*
  @Test(expected = IllegalArgumentException.class)
  //first test. You must use @Test before every test. This tests the case
  //when x is larger than 999
  public void testExceptionIsThrown() {
    ParserInterface tester = new Parser(); //instantiation of the class we need to test
    tester.parse("");
  }
  @Ignore //ignore the test, hide it to run the test.
  @Test(expected = NullPointerException.class)
  //Tests null pointer exception. This exception is never thrown so
  //this test always generates an error
  public void testNoException() {
	    MyClass tester = new MyClass();
	    tester.multiply(2000, 50);
	  }
*/
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply1() throws SQLException {
	  //ParserInterface tester = new Parser();
    assertEquals("Testing ", "DataBase Created Successfully", Interface.process("Create DataBase db_name;"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply2() throws SQLException {
	  //ParserInterface tester = new Parser();
    assertEquals("Testing ", "Done. Enter the first SQL command", Interface.process("Use db_name;"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply3() throws SQLException {
	//  ParserInterface tester = new Parser();
    assertEquals("Testing ", "Table Created Successfully", Interface.process("create table table_name {column_name1 int primary notsearchable autoincrement readonly nullable, column_name2 string primary notsearchable autoincrement readonly nullable, column_name3 String primary notsearchable autoincrement readonly nullable};"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply4() throws SQLException {
	 // ParserInterface tester = new Parser();
    assertEquals("Testing ", "Row inserted Successfully", Interface.process("Insert Into table_name Values ('5', 'value2', 'koko');"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply5() throws SQLException {
	  //ParserInterface tester = new Parser();
    assertEquals("Testing ", "Row inserted Successfully", Interface.process("Insert Into table_name (column_name1,column_name2,Column_name3) Values ('6', 'value2', 'value3');"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply6() throws SQLException {
	  //ParserInterface tester = new Parser();
    assertEquals("Testing ",
"\n+--------------------------------------+\n"+
"|column_name1|column_name2|column_name3|\n"+
"+--------------------------------------+\n"+
"|5           |value2      |koko        |\n"+
"|6           |value2      |            |\n"+
"+--------------------------------------+\n", Interface.process("select column_name1, column_name2, column_name3 from table_name;"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply7() throws SQLException {
	 // ParserInterface tester = new Parser();
    assertEquals("Testing ","\n+--------------------------------------+\n"+
    		"|column_name1|column_name2|column_name3|\n"+
    		"+--------------------------------------+\n"+
    		"|5           |value2      |koko        |\n"+
    		"+--------------------------------------+\n", Interface.process("select column_name1, column_name2, column_name3 from table_name where column_name1 = '5';"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply8() throws SQLException {
	  //ParserInterface tester = new Parser();
    assertEquals("Testing ", "Data Deleted Successfully", Interface.process("delete from table_name;"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply9() throws SQLException {
	 // ParserInterface tester = new Parser();
    assertEquals("Testing ", "Data Deleted Successfully", Interface.process("delete from table_name where column_name1 = \'value2\';"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
  @Test
  //assert test. General case for any test when first param is less than 1000 and second param is any value
  public void testMultiply10() throws SQLException {
	  //ParserInterface tester = new Parser();
    assertEquals("Testing ", "Table Updated Successfully", Interface.process("update table_name set column_name1 =\'value1\', column_name2 = \'value2\', column_name3 = \'value3\';"));
    //assertEquals("Any string that explains the operation (optional)", expected result, yourFunction(parameters));
    //fail();
  }
} 