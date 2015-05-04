package JDBC;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.junit.Test;

import DBMS.Column;

import junit.framework.TestCase;

public class ResultSetMetaDataTest extends TestCase {

	ArrayList<Column> cols ;
	ResultSetMetaData test ;
	
	public void intailizeCols ()
	{
		cols = new ArrayList<Column>();
		cols.add(new Column ("name" ,"String"));
		cols.get(0).setTableName("Data");
		Column x  =new Column("age" ,"int" );
		x.setAutoIncrement(true);
		x.setReadOnly(true);
		x.setSearchable(false);
		x.setNullableState(ResultSetMetaData.columnNullable);
		x.setTableName("Data1");
		cols.add(x);
		test = new ResultSetMetaData(cols);
	}
	
	@Test
	public void testGetColumnCount ()
	{
		intailizeCols();
		try {
			assertEquals(2, test.getColumnCount());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void testGetColumnLabel ()
	{
		intailizeCols();
		try {
			assertEquals("name", test.getColumnLabel(1));
			assertEquals("age", test.getColumnLabel(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testColumnLabel1 ()
	{
		intailizeCols();
		boolean thrown = false;
		try {
			test.getColumnLabel(10);
		} catch (SQLException e) {
             thrown =true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getColumnLabel(0);
		} catch (SQLException e) {
             thrown =true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testGetColumnName ()
	{
		intailizeCols();
		try {
			assertEquals("name", test.getColumnName(1));
			assertEquals("age", test.getColumnName(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetColumnName1 ()
	{
		intailizeCols();
		boolean thrown = false;
		try {
			test.getColumnName(10);
		} catch (SQLException e) {
             thrown =true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getColumnName(0);
		} catch (SQLException e) {
             thrown =true;
		}
		assertTrue(thrown);
	}

	
	
    @Test
    public void testGetColumnType ()
    {
    	intailizeCols();
  	 try {
		assertEquals(Types.CHAR, test.getColumnType(1));
		assertEquals(Types.INTEGER, test.getColumnType(2));
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

    @Test 
    public void testGetColumnType1 ()
    {
    	intailizeCols();
    	boolean thrown = false;
    	try {
    		test.getColumnType(10);
    	} catch (SQLException e) {
             thrown =true;
    	}
    	assertTrue(thrown);
    	 thrown = false;
    	try {
    		test.getColumnType(0);
    	} catch (SQLException e) {
             thrown =true;
    	}
    	assertTrue(thrown);
    }
    
    
    
    @Test
    public void testGetTableName ()
    {
    	intailizeCols();
    	try {
			assertEquals("Data", test.getTableName(1));
			assertEquals("Data1", test.getTableName(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    public void testGetTableName1 ()
    {
    	intailizeCols();
    	boolean thrown = false;
		try {
			test.getTableName(10);
		} catch (SQLException e) {
             thrown =true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.getTableName(-1);
		} catch (SQLException e) {
             thrown =true;
		}
		assertTrue(thrown);
    }
    
    

@Test
public void testIsAutoIncrement ()
{
     intailizeCols();
     try {
		assertTrue(test.isAutoIncrement(2));
		assertFalse(test.isAutoIncrement(1));
	} catch (SQLException e) {
		e.printStackTrace();
	}
 	
}

@Test
public void testIsAutoIncrement1 ()
{
	intailizeCols();
	boolean thrown = false;
	try {
		test.isAutoIncrement(10);
	} catch (SQLException e) {
         thrown =true;
	}
	assertTrue(thrown);
	 thrown = false;
	try {
		test.isAutoIncrement(-1);
	} catch (SQLException e) {
         thrown =true;
	}
	assertTrue(thrown);
}

  @Test
  public void testIsNullable ()
  {
	  intailizeCols();
	  try {
		assertEquals(ResultSetMetaData.columnNullableUnknown, test.isNullable(1));
		assertEquals(ResultSetMetaData.columnNullable, test.isNullable(2));
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  

  @Test
  public void testIsNullable1 ()
  {
	  intailizeCols();
	  boolean thrown = false;
		try {
			test.isNullable(10);
		} catch (SQLException e) {
	         thrown =true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.isNullable(-1);
		} catch (SQLException e) {
	         thrown =true;
		}
		assertTrue(thrown);
  }

  
  @Test
  public void testIsReadOnly ()
  {
	  intailizeCols();
	  try {
		assertFalse(test.isReadOnly(1));
		assertTrue(test.isReadOnly(2));
	} catch (SQLException e) {e.printStackTrace();}
	  boolean thrown = false;
		try {
			test.isReadOnly(10);
		} catch (SQLException e) {
	         thrown =true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.isReadOnly(-1);
		} catch (SQLException e) {
	         thrown =true;
		}
		assertTrue(thrown);
  }

@Test
public void testIsSearchable ()
{
      intailizeCols();
      try {
  		assertFalse(test.isSearchable(2));
  		assertTrue(test.isSearchable(1));
  	} catch (SQLException e) {e.printStackTrace();}
  	  boolean thrown = false;
  		try {
  			test.isSearchable(10);
  		} catch (SQLException e) {
  	         thrown =true;
  		}
  		assertTrue(thrown);
  		 thrown = false;
  		try {
  			test.isSearchable(-1);
  		} catch (SQLException e) {
  	         thrown =true;
  		}
  		assertTrue(thrown);
}

@Test
public void testIsWritable ()
{
	  intailizeCols();
	  try {
		assertFalse(test.isWritable(2));
		assertTrue(test.isWritable(1));
	} catch (SQLException e) {
		e.printStackTrace();
	}
	  boolean thrown = false;
		try {
			test.isWritable(10);
		} catch (SQLException e) {
	         thrown =true;
		}
		assertTrue(thrown);
		 thrown = false;
		try {
			test.isWritable(-1);
		} catch (SQLException e) {
	         thrown =true;
		}
		assertTrue(thrown);
}


}
