package DBMS;



import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class DBMSTest  {
	
	MyDBMS test = new MyDBMS();

//	@Test
//	public void testAll () throws InterruptedException
//	{
////		testCreateDB();
////		Thread.sleep(20);
////		testCreateDB2();
////		testCreateTable();
////		testCreateTable2();
////		testInsert1();
////		testInsert2();
////		testInsert3();
////		testInsert4();
////		testInsert5();
//		
//	}
	
	
    @Test
	public void testCreateDB() {
		
		System.out.println("Test 1");
		try {
			test.createDatabase("last");
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("database creation test");
		}
		
		
	}

    @Test
	public void testCreateDB2() {
		System.out.println("Test 2");
		testCreateDB();
		boolean thrown = false;
		try {
			
			test.createDatabase("last");
		} catch (Exception e) {
			// TODOAuto-generated catch block
			thrown = true;
		}
		assertTrue(thrown);
	}

    @Test
	public void testCreateTable() {
		System.out.println("Test 3");
		testCreateDB();
		
		ArrayList<Column> cols = new

		ArrayList<Column>();
		cols.add(new Column("names", "String"));
		cols.add(new Column("age", "int"));

		try { 
			test.setDatabase("last");
			test.createTable("Data", cols);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("table creation test");
		}
	}
	
    @Test
	public void testCreateTable2 ()
	{
		System.out.println("Test 4");
		
		testCreateDB();
		testCreateTable();
		ArrayList<Column> cols = new

				ArrayList<Column>();
				cols.add(new Column("names", "String"));
				cols.add(new Column("age", "int"));
                boolean thrown = false;
				try {
					test.setDatabase("last");
					test.createTable("Data", cols);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					thrown = true;
				}
				assertTrue(thrown);
	}

    @Test
	public void testInsert1() {
		System.out.println("Test 5");
		testCreateDB();
		testCreateTable();
		ArrayList<String> values = new ArrayList<>();
		values.add("Basma");
		values.add("20");
		try {
			test.setDatabase("last");
			test.insert("Data", values);
			values.clear();
			values.add("Reham");
			values.add("19");
			test.insert("Data", values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

    @Test
	public void testInsert2() {
		System.out.println("Test 6");
		testCreateDB();
		testCreateTable();
		ArrayList<String> col = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		col.add("names");
		values.add("CSED");
		try {
			test.setDatabase("last");
			test.insert("Data", col, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Test
	public void testInsert3() {
		System.out.println("Test 7");
		testCreateDB();
		testCreateTable();
		ArrayList<String> values = new ArrayList<>();
		values.add("Nada");
		values.add("20");
		values.add("100");
		boolean thrown = false;
		try {
			test.setDatabase("last");
			test.insert("Data", values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			thrown = true;
		}
		assertTrue(thrown);
	}

    @Test
	public void testInsert4() {
		System.out.println("Test 8");
		testCreateDB();
		testCreateTable();
		ArrayList<String> col = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		col.add("names");
		values.add("CSED");
		values.add("14");
		boolean thrown = false;
		try {
			test.setDatabase("last");
			test.insert("Data", col, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			thrown = true;
		}
		assertTrue(thrown);
	}

    @Test
	public void testInsert5() {
		System.out.println("Test 9");
		testCreateDB();
		testCreateTable();
		ArrayList<String> col = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		col.add("names");
		col.add("age");
		col.add("year");
		values.add("CSED");
		values.add("14");
		values.add("2012");
		boolean thrown = false;
		try {
			test.setDatabase("last");
			test.insert("Data", col, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			thrown = true;
		}
		assertTrue(thrown);
	}

	
	
}
