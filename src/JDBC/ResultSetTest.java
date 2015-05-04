package JDBC;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.junit.*;

import DBMS.Column;
import DBMS.Row;
import DBMS.Table;
import junit.framework.TestCase;

public class ResultSetTest extends TestCase {
	
	ArrayList<Row> rows = new ArrayList<Row>();
	ArrayList<Column> cols = new ArrayList<Column>();
	Table mytable;
	ResultSet test;
    

	public void intializeTable ()
	{
		cols.add(new Column("name" , "String" ));
		cols.add(new Column("age" , "int"));
		cols.add(new Column("date" , "Date"));
		cols.add(new Column("salary" , "Double"));
		cols.add(new Column("present" , "boolean"));
		ArrayList<String> values = new ArrayList<String>();
		values.add("Basma");
		values.add("20");
		values.add("1990-06-20");
		values.add("100.5");
		values.add("1");
		rows.add(new Row(values));
		values = new ArrayList<String>();
		values.add("Reham");
		values.add("19");
		values.add("1990-12-07");
		values.add("90.6");
		values.add("0");
		rows.add(new Row(values));
		mytable = new Table("Data", rows, cols);
	    test = new ResultSet(mytable,new Statement(new Connection()));
	    
		}
	
	@Test
	public void testAFterLast ()
	{
		intializeTable();
		try {
			test.afterLast();
			assertTrue(test.isAfterLast());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean thrown = false;
		try {
			test.close();
			test.afterLast();

		} catch (SQLException e) {
              thrown = true;
		}
		assertTrue(thrown);
	}
	
	
	@Test
public void testBeforeFirst ()
	{
		intializeTable();
		try {
			test.beforeFirst();
			assertTrue(test.isBeforeFirst());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test 
	public void testAbsolute ()
	{
		intializeTable();
		try {
			assertTrue(test.absolute(2));
			assertFalse(test.absolute(10));
			assertTrue(test.absolute(-1));
			assertFalse(test.absolute(-10));
            assertFalse(test.absolute(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	@Test
	public void testFindCol ()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(1,test.findColumn("name") );
			assertEquals(3, test.findColumn("date"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 try {
			test.findColumn("names");
		} catch (SQLException e) {
              thrown= true;
              }
		 assertTrue(thrown);
	}

	@Test
	public void testFirst ()
	{
		intializeTable();
		try {
			assertTrue(test.first());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mytable = new Table();
		test = new ResultSet(mytable,new Statement(new Connection()));
		try {
			assertFalse(test.first());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
public void testGetArray ()
{
	intializeTable();
	boolean thrown = false;
	try {
		JDBC.Array x = (JDBC.Array) test.getArray(1) ;
        assertEquals("String", x.getType());
	} catch (SQLException e) {
          thrown = true;
	}
	assertTrue(thrown);
	try {
		test.next();
		JDBC.Array x = (JDBC.Array) test.getArray(1) ;
        assertEquals("String", x.getType());
        Object [] h = x.getArrayElements();
        assertEquals("Basma", h[0]);
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

	@Test 
	public void testGetArray1()
	{
		intializeTable();
		boolean thrown = false;
		try {
			JDBC.Array x = (JDBC.Array) test.getArray(0) ;
		} catch (SQLException e) {
	        thrown = true;
		}
		assertTrue(thrown);
		thrown = false;
		try {
			JDBC.Array x = (JDBC.Array) test.getArray(10) ;
		} catch (SQLException e) {
	        thrown = true;
		}
		assertTrue(thrown);
	}
	
	
	@Test
	public void testGetBoolean ()
	{
     	intializeTable();
     	boolean thrown = false;
     	try {
			assertTrue(test.getBoolean(5));
		} catch (SQLException e) {
            thrown = true;
		}
     	assertTrue(thrown);
     	try {
			test.next();
			assertTrue(test.getBoolean(5));
			test.next();
			assertFalse(test.getBoolean(5));
		} catch (SQLException e) {
			e.printStackTrace();
		}
     	
     	
	}
 
	@Test
	public void testGetBoolean2 ()
	{
		intializeTable();
		boolean thrown  = false;
     	try {
			assertTrue(test.getBoolean(-1));
		} catch (SQLException e) {
            thrown = true;
		 }
     	assertTrue(thrown);
     	thrown  = false;
     	try {
			assertTrue(test.getBoolean(10));
		} catch (SQLException e) {
            thrown = true;
		 }
     	assertTrue(thrown);
	}
	
	@Test
	public void testGetBoolean1 ()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertTrue(test.getBoolean("present"));
		} catch (SQLException e1) {
           thrown = true;
		}
		assertTrue(thrown);
		try {
			test.next();
			assertTrue(test.getBoolean("present"));
			test.next();
			assertFalse(test.getBoolean("present"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		thrown = false;
		try {
			assertTrue(test.getBoolean("salary"));
		} catch (SQLException e) {
             thrown = true;
		 }
		assertTrue(thrown);
	}
	
	 @Test
	   public void testGetDate ()
	   {
		   intializeTable();
		   boolean thrown = false;
		   try {
			assertEquals("1990-06-20", test.getDate(3).toString());
		} catch (SQLException e1) {
	            thrown = true;
		}
		   assertTrue(thrown);
		   try {
			test.next();
			assertEquals("1990-06-20", test.getDate(3).toString());
			test.next();
			assertEquals("1990-12-07", test.getDate(3).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   }
	 
	 @Test
	   public void testGetDate2 ()
	  {
		  intializeTable();
		 boolean thrown = false;
		   try {
			test.getDate(-1);
		} catch (SQLException e) {
	         thrown = true;
		}
		   assertTrue(thrown);
		   thrown = false;
		   try {
			test.getDate(10);
		} catch (SQLException e) {
	         thrown = true;
		}
		   assertTrue(thrown);
		   thrown = false;
		   try {
			test.getDate(1);
		} catch (SQLException e) {
	         thrown = true;
		}
		   assertTrue(thrown);
	  }

	  
	 @Test
	   public void testGetDate1 ()
	   {
		   intializeTable();
			boolean thrown = false;
				try {
					assertEquals(20 ,test.getDate("date").toString());
				} catch (SQLException e1) {
					thrown = true;
				}
			assertTrue(thrown);
			try {
				test.next();
				assertEquals("1990-06-20" ,test.getDate("date").toString());
				test.next();
				assertEquals("1990-12-07", test.getDate("date").toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	   }
	
	 @Test
	   public void testGetDate3 ()
	 {
		 intializeTable();
		 boolean thrown = false;
			try {
				assertEquals(20 ,test.getDate("name"));
			} catch (SQLException e) {
	            thrown = true;
			}
			assertTrue(thrown);
			 thrown = false;
			try {
				test.getDate("Hello");
			} catch (SQLException e) {
	            thrown = true;
			}
	 }
	
	
	@Test
	public void testGetDouble ()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(20.0 ,test.getDouble(2));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			//System.out.println(test.getDouble(2));
			assertEquals(20.0 ,test.getDouble(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
    public void testGetDouble2()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(20.0 ,test.getDouble(1));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getDouble(0);
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		thrown = false;
		try {
			test.getDouble(10);
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
	}
	
	
	@Test
    public void testGetDouble1()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(100.5 ,test.getDouble("salary"));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals(100.5 ,test.getDouble("salary"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void testGetDouble3 ()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(20 ,test.getDouble("name"));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getDouble("Hello");
		} catch (SQLException e) {
             thrown = true;
		}
	}
	
	
	@Test
	public void testFetchDirection ()
	{
		intializeTable();
		@SuppressWarnings("static-access")
		int x = test.FETCH_FORWARD;
		try {
			assertEquals(x, test.getFetchDirection());
			test.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean thrown = false;
		try {
			assertEquals(x, test.getFetchDirection());
		} catch (SQLException e) {
           thrown = true;
		}
		assertTrue(thrown);
	}
	
	
	@Test
	public void testGetFloat ()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(100.5 ,test.getFloat(4));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals((float)100.5 ,test.getFloat(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		thrown = false;
		try {
			assertEquals(100.5 ,test.getFloat(1));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testGetFloat2 ()
	{
		intializeTable();
		boolean  thrown = false;
			try {
				test.getFloat(0);
			} catch (SQLException e) {
	             thrown = true;
			}
			assertTrue(thrown);
			thrown = false;
			try {
				test.getFloat(10);
			} catch (SQLException e) {
	             thrown = true;
			}
			assertTrue(thrown);
	}
    
	@Test
    public void testGetFloat1()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(20.0 ,test.getFloat("age"));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals((float)20.0 ,test.getFloat("age"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
    public void testGetFloat3()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(20 ,test.getFloat("name"));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getFloat("Hello");
		} catch (SQLException e) {
             thrown = true;
		}
	}
	
	@Test
    public void testGetInt()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(20 ,test.getInt(2));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals(20 ,test.getInt(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void testGetInt2()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(20 ,test.getInt(1));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getInt(0);
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		thrown = false;
		try {
			test.getInt(10);
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
	}
	
	
	@Test
    public void testGetInt1()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(20 ,test.getInt("age"));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals(20 ,test.getInt("age"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
    public void testGetInt3()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(20 ,test.getInt("name"));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getInt("Hello");
		} catch (SQLException e) {
             thrown = true;
		}
	}
	
 	
	@Test
    public void testGetLong()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(20 ,test.getLong(2));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals(20 ,test.getLong(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void testGetLong2()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(20 ,test.getLong(1));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getLong(0);
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		thrown = false;
		try {
			test.getLong(10);
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
	}
	
	
	@Test
    public void testGetLong1()
	{
		intializeTable();
		boolean thrown = false;
			try {
				assertEquals(20 ,test.getLong("age"));
			} catch (SQLException e1) {
				thrown = true;
			}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals(20 ,test.getLong("age"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void testGetLong3()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals(20 ,test.getLong("name"));
		} catch (SQLException e) {
             thrown = true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getLong("Hello");
		} catch (SQLException e) {
             thrown = true;
		}
	}
	
	@Test
	public void testGetObject ()
	{
		intializeTable();
		boolean thrown  =false;
		try {
	         assertEquals("Basma", test.getObject(1));
		} catch (SQLException e) {
	          thrown =true;
		}
		assertTrue(thrown);
		try {
			test.next();
	         assertEquals("Basma", test.getObject(1));
	         assertEquals("20", test.getObject(2));
	         assertEquals("100.5", test.getObject(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetObject1 ()
	{
		intializeTable();
		 boolean thrown  =false;
			try {
		         test.getObject(-2);
			} catch (SQLException e) {
		          thrown =true;
			}
			assertTrue(thrown);
			 thrown  =false;
				try {
			         test.getObject(10);
				} catch (SQLException e) {
			          thrown =true;
				}
				assertTrue(thrown);
	}
	
	@Test
    public void testGetString()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals("Basma" ,test.getString(1));
		} catch (SQLException e1) {
           thrown = true;
		}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals("Basma" ,test.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 thrown = false;
		try {
			test.getString(0);
		} catch (SQLException e) {
           thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testGetString1()
	{
		intializeTable();
		boolean thrown = false;
		try {
			assertEquals("Basma" ,test.getString("name"));
		} catch (SQLException e) {
            thrown = true;
		}
		assertTrue(thrown);
		try {
			test.next();
			assertEquals("Basma" ,test.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 thrown = false;
			try {
				test.getString("Hello");
			} catch (SQLException e) {
	           thrown = true;
			}
			assertTrue(thrown);

	}
	
	 @Test 
	public void testIsAfterLast ()
	    {
	    	intializeTable();
	    	try {
				assertFalse(test.isAfterLast());
				testNext();
				assertTrue(test.isAfterLast());
				test.previous();
				assertFalse(test.isAfterLast());
				test.previous();
				assertFalse(test.isAfterLast());
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    }
	
	 @Test
	 public void testIsBeforeFirst ()
	    {
	    	intializeTable();
	    	try {
				assertTrue(test.isBeforeFirst());
				test.next();
				assertFalse(test.isBeforeFirst());
				test.next();
				assertFalse(test.isBeforeFirst());
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    }
	 
	@Test
    public void testIsFirst ()
	{
		intializeTable();
		try {
			assertFalse(test.isFirst());
			test.next();
			assertTrue(test.isFirst());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void testIsLast ()
	{
		intializeTable();
		testNext();
		try {
			assertFalse(test.isLast());
			test.previous();
			assertTrue(test.isLast());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testLast ()
	{
		intializeTable();
		try {
			assertTrue(test.last());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mytable = new Table();
		test = new ResultSet(mytable, new Statement(new Connection()));
		try {
			assertFalse(test.last());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testNext()
	{
		intializeTable();
		int n = mytable.getSize();
		for (int i = 0; i < n; i++) {
			try {
				assertEquals(true, test.next());
			} catch (SQLException e) {
			}
		}
		try {
			assertEquals(false, test.next());
		} catch (SQLException e) {
		}
	}
	
	@Test
	public void testPrevious (){
		intializeTable();
		testNext();
		
		for (int i = 0; i < mytable.getSize(); i++) {
			try {
				assertEquals(true, test.previous());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			assertEquals(false, test.previous());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	@Test
	public void testMetaData ()
	{
		intializeTable();
		try {
			test.next();
			ResultSetMetaData x = test.getMetaData();
			assertEquals("name", x.getColumnName(1));
			assertEquals("name", x.getColumnLabel(1));
			assertEquals(Types.INTEGER, x.getColumnType(2));
			assertEquals(5, x.getColumnCount());
			assertEquals("unknown", x.getTableName(2));
			assertFalse(x.isAutoIncrement(5));
			assertEquals(ResultSetMetaData.columnNullableUnknown, x.isNullable(1));
			assertFalse(x.isReadOnly(2));
	  		assertTrue(x.isSearchable(3));
			assertTrue(x.isWritable(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

  

@Test
public void testisClosed ()
{
         intializeTable();
         try {
        	 assertFalse(test.isClosed());
			test.close();
			assertTrue(test.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
}
    
