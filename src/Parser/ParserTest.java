package Parser;

import junit.framework.TestCase;

public class ParserTest extends TestCase{
	
	public void testParse(){
		ParserInterface parser = new Parser();
		assertEquals("Syntax Error !", parser.parse("bjubhii"));
	    assertEquals("Table Not Exist !", parser.parse("select name from students where id = '5';"));
	    assertEquals("Syntax Error !", parser.parse("select name from students where id = 5';"));
	    assertEquals("Table not found", parser.parse("delete from students;"));
	    assertEquals("Syntax Error !", parser.parse("delete student from students;"));
	    assertEquals("Syntax Error !", parser.parse("udate students from students;"));
	    assertEquals("Table not found", parser.parse("update students set student = 'nada';"));
            assertEquals("DataBase Created Successfully",
				parser.parse("create database db;"));
		assertEquals("Syntax Error !", parser.parse("createdatabase db;"));
		assertEquals("Syntax Error !", parser.parse("create database ;"));
		assertEquals("Syntax Error !", parser.parse("create database db"));
		assertEquals("Syntax Error !", parser.parse("hfj database db;"));

		assertEquals("Table Created Successfully",
				parser.parse("create table db {c v,c2 v2,c3 v3,c4 v4};"));
		assertEquals("Syntax Error !", parser.parse("createtable db;"));
		assertEquals("Syntax Error !", parser.parse("create table ;"));
		assertEquals("Syntax Error !", parser.parse("create table db"));
		assertEquals("Syntax Error !",
				parser.parse("create table db {c v,c2 v2,c3 v3,c4};"));
		assertEquals("Syntax Error !", parser.parse("create table db {  };"));
		assertEquals("Syntax Error !",
				parser.parse("create table db {c v,c2 v2,c3 v3,c4 v4;"));
		assertEquals("Syntax Error !",
				parser.parse("create table db {c v,c2 v2,c3 v3,c4 v4"));
		assertEquals("Syntax Error !",
				parser.parse("create table db {c v,c2 v2,c3 v3,c4 v4}"));

		assertEquals(
				"Row inserted Successfully",
				parser.parse("INSERT INTO table_name VALUES (value1, 'value2', value3,...);"));
		assertEquals(
				"Row inserted Successfully",
				parser.parse("INSERT INTO table_name (column1,column2,column3) VALUES (value1, value2, value3);"));
		assertEquals(
				"Syntax Error !",
				parser.parse("INSERT INTO table_name (column1, column2) VALUES (value1,value2,value3);"));
		assertEquals(
				"Syntax Error !",
				parser.parse("INSERT INTO table_name (column1, column2, column3,) ;"));
		assertEquals("Syntax Error !", parser.parse("hfj insert into db;"));

	    
	    
	}
	

}