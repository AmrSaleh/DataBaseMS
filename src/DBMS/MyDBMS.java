package DBMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Stack;

import JDBC.ResultSetMetaData;
import JDBC.Statement;
import Logging.LogClass;

public class MyDBMS implements DBMS {

	private String path; // hena esm el makan el 7a tetsagel feh
									// kol
									// 7aga
	private String myDatabase;
	private Table myTable;
	private XML myXmlObject;

	public MyDBMS() {
		// TODO Auto-generated constructor stub
		LogClass.log().info("configuring DBMS...");
//		XML.makeFolder("C:\\DBMS");
		path = "DBMS\\";
		myXmlObject = new XML(path, "initial");
		// initializeDBMSFolder();
		Properties prop = new Properties();
		try {
			// load a properties file

			prop.load(new FileInputStream("config.properties"));

			// get the property value and print it out
			path=(prop.getProperty("path"));
			XML.makeFolder(path);
			LogClass.log().info("DBMS configurations found  successfully");

		} catch (IOException ex) {
//			System.out.println("can't load properties");
			LogClass.log().error("couldn't find DBMS configurations, making new config file");
			try {
				// set the properties value
				prop.setProperty("path", "DBMS\\");
				XML.makeFolder(path);

				// save properties to project root folder
				prop.store(new FileOutputStream("config.properties"), null);
				LogClass.log().info("DBMS configurations created  successfully");
			} catch (IOException ex2) {
//				System.out.println("can't save properties");
				XML.makeFolder(path);
			}
		}

	}

	public void setPath(String newPath) throws Exception {
		// TODO Auto-generated method stub
		try {
			XML.makeFolder(newPath);
		} catch (Exception e) {
			throw new Exception("Path is not valid");
		}

		path = newPath;
		myXmlObject = new XML(path, myDatabase);

	}


	@Override
	public void createDatabase(String Name) throws Exception {
		// TODO Auto-generated method stub
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		LogClass.log().info("creating data base...");
		File f = new File(path + "\\DatabasesSchema.xml");
		if (f.exists()) {

		} else {
			try {
				myXmlObject.makeFile(path + "\\DatabasesSchema.xml");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogClass.log().error("Can not create data base file");
				throw new DataNotFound("Can not create data base file");
				
			}
		}

		ArrayList<String> databases = null;
		try {
			databases = myXmlObject.loadStrings(path + "\\DatabasesSchema.xml");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			LogClass.log().error("error, creating data base");
			throw new Exception("error, creating data base");
		}
		if (databases.contains(Name)) {
			LogClass.log().error("Duplicate data base");
			throw new Exception("Duplicate data base");

		}

		try {
			myXmlObject.appendStringToXML(path + "\\DatabasesSchema.xml", Name);
		} catch (Exception e) {
			LogClass.log().error("Can not create data base");
			throw new DataNotFound("Can not create data base");
		} // hena ba3mel el schema bta3et el data base fe folder sabet esmo
			// allschemas

		String folderPath = path + "\\" + Name; // bazabat el path bta3 el
												// folder

		XML.makeFolder(folderPath);

		try {
			myXmlObject.makeFile(path + "\\" + Name + "\\Schema.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogClass.log().error("Can not create data base");
			throw new DataNotFound("Can not create database");
		}

		setDatabase(Name);
		myDatabase = Name;
		myXmlObject = new XML(path, Name);
		LogClass.log().info("Data base created with name: "+Name);
	}

	@Override
	public void setDatabase(String database) throws Exception {
		// TODO Auto-generated method stub
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		LogClass.log().info("Setting data base...");
		ArrayList<String> databases = null;
		try {
			databases = myXmlObject.loadStrings(path + "\\DatabasesSchema.xml");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LogClass.log().error("Can not set data base");
			throw new Exception("error setting data base");
		}
		if (!databases.contains(database)) {
			LogClass.log().error("Database not found");
			throw new Exception("Database not found");

		}

		myDatabase = database;
		myXmlObject = new XML(path, database);
		
		LogClass.log().info("Data base set to : "+database);

	}

	@Override
	public void createTable(String tableName, ArrayList<Column> columns) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		LogClass.log().info("Creating table...");
		try {
			myXmlObject.createTAble(tableName, myDatabase, columns);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogClass.log().error("Can not create Table, " + e.getLocalizedMessage());
			throw new Exception("Can not create Table, " + e.getLocalizedMessage());
		}
		LogClass.log().info("Table "+tableName+" created in data base "+myDatabase);
	}

	 private ArrayList<String> doChecks(String tableName,ArrayList<String> data,ArrayList<Column> cols) throws Exception{
		 for (int j = 0; j < cols.size(); j++) {
				if (!isValidData(data.get(j), cols.get(j))) {LogClass.log().error("Not valid dataType");
					throw new Exception("Not valid dataType");
				}
				if (!isValidParameters(tableName, data.get(j), cols.get(j))) {LogClass.log().error("error in insert, duplicate intery in a primary key column");
					throw new Exception("error in insert, duplicate intery in a primary key column");
				}
				if (cols.get(j).isAutoIncrement()) {
					data.set(j, autoIncrementInput(tableName, data.get(j), cols.get(j), cols));
				}
			}
			for (int i = 0; i < cols.size(); i++) {
				if (cols.get(i).isAutoIncrement()) {
					incrementColsSchema(tableName, cols.get(i), cols);
				}
			}
			
			return data;
	 }
	@Override
	public void insert(String tableName, ArrayList<String> data) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		// TODO Auto-generated method stub
		LogClass.log().info("Using simple insertion for data...");
		Row tempRow = new Row(data);

		ArrayList<Column> cols = null;
		try {
			cols = myXmlObject.loadCols(path + "\\" + myDatabase + "\\" + tableName + "Schema.xml");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LogClass.log().error("error in insert, pls review your input");
			throw new Exception("error in insert, pls review your input");
		}
		if (data.size() != cols.size()) {
			LogClass.log().error("inserted cols no. doesn't match their no. in the table");
			throw new Exception("inserted cols no. doesn't match their no. in the table");
		}
		data=doChecks( tableName,data,cols);
		try {
			myXmlObject.appendRowToXML(path + "\\" + myDatabase + "\\" + tableName + ".xml", tempRow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogClass.log().error("insert failed to append row to xml");
			throw new Exception("insert failed to append row to xml");
		}
		
		LogClass.log().info("Row data inserted in "+tableName);
	}

	@Override
	public void insert(String tableName, ArrayList<String> cols, ArrayList<String> data) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		LogClass.log().info("Using Columns insertion for data...");if (cols.size() != data.size()) {
			throw new Exception("col names and data are not equal");
		}
		ArrayList<Column> cols2 = null;
		try {
			cols2 = myXmlObject.loadCols(path + "\\" + myDatabase + "\\" + tableName + "Schema.xml");
		} catch (Exception e) {LogClass.log().error("can not loadCols in insert, no such table");
			throw new Exception("can not loadCols in insert, no such table");
		}
		ArrayList<String> colNames = new ArrayList<String>();
		for (int k = 0; k < cols2.size(); k++) {
			colNames.add(cols2.get(k).getColName());
		}
		if (cols.size() > cols2.size()) {LogClass.log().error("col names are more than the original cols");
			throw new Exception("col names are more than the original cols");
		}
		checkCorrectData(cols, colNames);
		ArrayList<String> newData = new ArrayList<String>();
		for (int i = 0; i < colNames.size(); i++) {
			newData.add("");
			for (int j = 0; j < cols.size(); j++) {
				if (colNames.get(i).equals(cols.get(j))) {
					newData.set(i, data.get(j));
					break;
				}
			}
		}
		insert(tableName, newData);
	}

	private void checkCorrectData(ArrayList<String> inputCols, ArrayList<String> OriginalCols) throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < inputCols.size(); i++) {
			for (int j = 0; j < inputCols.size(); j++) {
				if (i != j) {
					if (inputCols.get(i).equals(inputCols.get(j))) {LogClass.log().error("duplicate column names in insert!");
						throw new Exception("duplicate column names in insert!");
					}
				}
			}
		}

		for (int i = 0; i < inputCols.size(); i++) {
			if (!OriginalCols.contains(inputCols.get(i))) {LogClass.log().error("Column name is not found in original table!");
				throw new Exception("Column name is not found in original table!");
			}
		}

	}

	

	@Override
	public void delete(String tablename) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		LogClass.log().info("Deleting data...");
		ArrayList<String> tableNames = myXmlObject.loadStrings(path + "\\" + myDatabase + "\\Schema.xml");

		if (!tableNames.contains(tablename)) {LogClass.log().error("can't delete, table not found");
			throw new Exception("can't delete, table not found");
		}

		try {
			myXmlObject.deleteTable(tablename, myDatabase);
		} catch (Exception e) {
			LogClass.log().error("Table not Found");
			throw new DataNotFound("Table not Found");
		}
		
		LogClass.log().info("All data rows deleted from table : " + tablename);
	}

	@Override
	public void delete(String tableName, Stack<Object> postFixStk) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}

		try {
			myTable = myXmlObject.loadTable(tableName);
		} catch (Exception e) {
			LogClass.log().error(e.toString()+"Table not found");
			throw new DataNotFound(e.toString()+"Table not found");
		}
		if ( !colsExistInTable(myTable, postFixStk)) {LogClass.log().error("Column not found");
				throw new DataNotFound("Column not found");
		}
		ArrayList<Integer> indeces = myTable.getIndecesOfIntrsectingRowsWith(evalutaePostFix(myTable, postFixStk));

		ArrayList<Row> r= new ArrayList<>();
		System.out.println(indeces);
		for (int i = 0; i < myTable.getSize(); i++) {
			if (!indeces.contains(i)) {
				r.add(myTable.getRowInstance(i));
			}
		}
		Table newTable = new Table(tableName,r,myTable.getTableColumns());
		myTable = newTable;
//		System.out.println(myTable.toString());
		myXmlObject.SaveTAble(myTable, myTable.tableName, myDatabase);
		LogClass.log().info("rows deleted");
	}

	@Override
	public Table selectTable(String tableName, ArrayList<String> returnCols, Stack<Object> postFixStk) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		// TODO Auto-generated method stub
		try {
//			System.out.println("entered");
			myTable = myXmlObject.loadTable(tableName);
			//			System.out.println("loaded");
		} catch (Exception e) {
			LogClass.log().error("Cannot load table");
			throw new DataNotFound("Cannot load table");
			}
		Table newTable = new Table();
		if (returnCols == null && postFixStk == null) {
			newTable = myTable;
		} else if (postFixStk == null) {
			try {
				newTable = selectColumns(myTable, returnCols);
			} catch (Exception e) {LogClass.log().error("Cannot Select table");
				throw new DataNotFound("Cannot Select table");
			}
		} else if (returnCols == null) {
			try {
				newTable = selectRow(myTable, postFixStk);
			} catch (Exception e) {LogClass.log().error(e.toString()+"\nCannot Select table");
				throw new DataNotFound(e.toString()+"\nCannot Select table");
			}
		} else {
			try {
				newTable = selectRow(myTable, postFixStk);
//				System.out.println("passedddddddddddddddd");
				newTable = selectColumns(newTable, returnCols);
			} catch (Exception e) {
				LogClass.log().error("Cannot Select table");
				throw new DataNotFound("Cannot Select table");
			}
		}
		LogClass.log().info("table selected");
		return newTable;
		
	}
	
	@Override
	public void update(String tableName, ArrayList<String> cols, ArrayList<String> data) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		// TODO Auto-generated method stub

		try {
			myTable = myXmlObject.loadTable(tableName);
		} catch (Exception e) {
			LogClass.log().error("Table not found");
			throw new DataNotFound("Table not found");
		}
		if (!colsExistInTable(myTable, cols)){LogClass.log().error("Column not found");
			throw new DataNotFound("Column not found");
		}
//		for (int i = 0; i < cols.size(); i++) {
//
//			if (!myTable.ColExist(cols.get(i))) {
//				throw new DataNotFound("Column not found");
//			}
//		}

		for (int i = 0; i < myTable.getSize(); i++) {
			for (int j = 0; j < cols.size(); j++) {
				try {
					myTable.isValidUpdate(cols, data, i);
				} catch (Exception e) {LogClass.log().error(e.toString() + "\nNot valid update");
					throw new Exception(e.toString() + "\nNot valid update");
				}
					myTable.setAValueInRow(i, cols.get(j), data.get(j));
			}
		}
		myXmlObject.SaveTAble(myTable, myTable.tableName, myDatabase);
		LogClass.log().info("table updated");
	}
	@Override
	public void update(String tableName, ArrayList<String> cols, ArrayList<String> data, Stack<Object> postFixStk) throws Exception {
		if(checkTime()){
			throw new Exception("Operation aborted, timeout reached");
		}
		// TODO Auto-generated method stub

		try {
			myTable = myXmlObject.loadTable(tableName);
		} catch (Exception e) {
			LogClass.log().error(e.toString()+"Table not found");
			throw new DataNotFound(e.toString()+"Table not found");
		}
		if (!colsExistInTable(myTable, cols) && !colsExistInTable(myTable, postFixStk)) {LogClass.log().error("Column not found");
				throw new DataNotFound("Column not found");
		}
		ArrayList<Integer> indeces = myTable.getIndecesOfIntrsectingRowsWith(evalutaePostFix(myTable, postFixStk));
		for (int i = 0; i < myTable.getSize(); i++) {
			for (int j = 0; j < cols.size(); j++) {
				if (indeces.contains(i))/* && isInteger(data.get(j), myTable.getTableColumns().get(myTable.getColID(cols.get(j))))*/{
					try {
						myTable.isValidUpdate(cols, data, i);
					} catch (Exception e) {LogClass.log().error(e.toString() + "\nNot valid update");
						throw new Exception(e.toString() + "\nNot valid update");
					}
					myTable.setAValueInRow(i, cols.get(j), data.get(j));
				}
			}
		}
		myXmlObject.SaveTAble(myTable, myTable.tableName, myDatabase);
		LogClass.log().info("table updated");
	}

	public String getDatabase() {
		return myDatabase;
	}

	public String getPath() {
		return path;
	}

	private Table selectColumns(Table myTable, ArrayList<String> returnCols) throws Exception {
		ArrayList<Column> c = new ArrayList<>();
		for (int i = 0; i < returnCols.size(); i++) {

			if (!myTable.ColExist(returnCols.get(i))) {LogClass.log().error("Column not found");
				throw new DataNotFound("Column not found");
			} else {

				c.add(myTable.getColumnInstance(returnCols.get(i)));
			}
		}
		ArrayList<Row> r = new ArrayList<>();
		for (int j = 0; j < myTable.getSize(); j++) {
			ArrayList<String> RowValues = new ArrayList<>();
			for (int i = 0; i < returnCols.size(); i++) {
				RowValues.add(myTable.getAValueFromRow(j, returnCols.get(i)));
			}
			r.add(new Row(RowValues));
		}
		Table newTable = new Table("SelecetdTabele", r, c);
		return newTable;
	}

	

	// @SuppressWarnings("serial")
	public class DataNotFound extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DataNotFound() {
			super();
		}

		public DataNotFound(String message) {
			super(message);
		}

		public DataNotFound(String message, Throwable cause) {
			super(message, cause);
		}

		public DataNotFound(Throwable cause) {
			super(cause);
		}
	}

	// public static void main(String[] args) throws Exception {
	// MyDBMS koko = new MyDBMS();
	// String databaseName="sheetooos";
	// // koko.createDatabase(databaseName);
	//
	// ArrayList<Column> col = new ArrayList<>();
	// koko.setDatabase(databaseName);
	//
	// col.add(new Column("Name", "String"));
	// col.add(new Column("ID", "int"));
	// koko.createTable("bassem", col);
	//
	// ArrayList<String> test = new ArrayList<String>();
	// test.add("amr");
	// test.add("7");
	//
	// ArrayList<String> test2 = new ArrayList<String>();
	// test2.add("amr2");
	// test2.add("8");
	//
	//
	// ArrayList<String> colnames = new ArrayList<String>();
	// colnames.add("Name");
	// colnames.add("ID");
	//
	// koko.insert("bassem", colnames, test);
	// koko.insert("bassem", colnames, test2);
	// koko.insert("bassem", colnames, test);
	// //koko.delete("bassem");
	//
	// ArrayList<ArrayList<ComparisonStatement>> comp =new ArrayList<>();
	// ArrayList<ComparisonStatement> incomp =new ArrayList<>();
	// incomp.add(new ComparisonStatement("ID", "8", "="));
	// comp.add(incomp);
	// koko.delete("bassem", comp);
	// XML xml =new XML("C:\\DBMS\\", databaseName);
	// Table testTable=xml.loadTable("bassem");
	// xml.saveTable("bassem", testTable);
	// testTable=xml.loadTable("bassem");
	// System.out.println(testTable.toString());
	// System.out.println(testTable.getTableColumns().size());
	// // System.out.println(testTable.tableRows.size());
	// System.out.println(testTable.tableName);
	//
	// System.out.println("done");
	// }

	public static boolean isValidData(String s, Column col) throws Exception {
		String dataType = col.getType();
		// empty (null)
		if (s.equals("") && (col.getNullableState() == ResultSetMetaData.columnNullableUnknown || col.getNullableState() == ResultSetMetaData.columnNullable)) {
			return true;
		}
		if (s.equals("")) {
			if (!col.isAutoIncrement()) {
				LogClass.log().error("Null value cannot be assigned to this column");
				throw new Exception("Null value cannot be assigned to this column");
			}
		}
		// Integer
		if (dataType.equalsIgnoreCase("int")) {
			return checkInteger(s);
			// boolean
		} else if (dataType.equalsIgnoreCase("boolean")) {
			return checkBoolean(s);
			// date dd/mm/yyyy
		} else if (dataType.equalsIgnoreCase("date")) {
			return checkDate(s);
			// long
		} else if (dataType.equalsIgnoreCase("long")) {
			return checkLong(s);
			// float
		} else if (dataType.equalsIgnoreCase("float")) {
			return checkFloat(s);
			// double
		} else if (dataType.equalsIgnoreCase("double")) {
			return checkDouble(s);
		}
		// String
		return true;
	}

	private static boolean checkBoolean(String data) {

		if (data.equals("1") || data.equals("0")) {
			return true;
		} else {
			return false;
		}

	}

	private static boolean checkDate(String data) {
		String dateFor = GetdateFormate(data);
		if (dateFor == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFor);
		sdf.setLenient(false);
		try {
			Date date = new Date(sdf.parse(data).getTime());
			// System.out.println(date+" in check date");
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	private static boolean checkInteger(String data) {

		try {
			Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static boolean checkLong(String data) {
		try {
			Long.parseLong(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;

	}

	private static boolean checkFloat(String data) {

		try {
			Float.parseFloat(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static boolean checkDouble(String data) {

		try {
			Double.parseDouble(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static String GetdateFormate(String s) {
		String dateFormat = null;
		if (s.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
			dateFormat = "yyyy-MM-dd";
		} else if (s.matches("\\d{4}")) {
			dateFormat = "yyyy";
		} else if (s.matches("\\d{2}")) {
			dateFormat = "yy";
		} else if (s.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}")) {
			dateFormat = "yyyy-MM-dd hh:mm:ss";
		} else if (s.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}")) {
			dateFormat = "yyyy-MM-dd hh:mm";
		}
		return dateFormat;
	}

	private boolean isValidParameters(String tableName, String string, Column column) throws Exception {
		// TODO Auto-generated method stub
		try {

			if (column.isPrimary() && !checkPrimaryKey(tableName, string, column)) {
				return false;
			}
		} catch (Exception e) {
			LogClass.log().error(e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}
		return true;
	}

	private boolean checkPrimaryKey(String tableName, String string, Column column) throws Exception {
		// TODO Auto-generated method stub

		if (string.equals("") && !column.isAutoIncrement()) {
			LogClass.log().error("cannot insert null value in a primary key column");
			throw new Exception("cannot insert null value in a primary key column");
		}
		Table theTable;
		try {
			theTable = myXmlObject.loadTable(tableName);
		} catch (Exception e) {
			LogClass.log().error("cannot load table while checking primary key");
			throw new Exception("cannot load table while checking primary key");
		}

		for (int i = 0; i < theTable.getSize(); i++) {

			if (theTable.getAValueFromRow(i, column.getColName()).equalsIgnoreCase(string)) {

				return false;
			}
		}

		return true;
	}

	private String autoIncrementInput(String tableName, String string, Column column, ArrayList<Column> allCols) throws Exception {
		// TODO Auto-generated method stub
		if (!string.equals("")) {	LogClass.log().error("inserting is not allowed in auto incremented column");
			throw new Exception("inserting is not allowed in auto incremented column");
		}

		int value = column.getIncrementNextValue();
		return value + "";
	}

	private void incrementColsSchema(String tableName, Column column, ArrayList<Column> allCols) throws Exception {
		int value = column.getIncrementNextValue();
		column.setIncrementNextValue(column.getIncrementNextValue() + 1);
		try {
			ArrayList<Column> oldCols = allCols;
			oldCols.get(oldCols.indexOf(column)).setIncrementNextValue(value + 1);
			myXmlObject.deleteFile(path + "\\" + myDatabase + "\\" + tableName + "Schema.xml");
			myXmlObject.serializeObjectToXML(path + "\\" + myDatabase + "\\" + tableName + "Schema.xml", oldCols);
		} catch (Exception e) {
			LogClass.log().error("An error happend while updating auto increment options");
			throw new Exception("An error happend while updating auto increment options");
		}
	}
	
	private Stack<Object> CSStkToTableStk(Table myTable ,Stack<Object> postFixStk){
		Stack<Object> newStk =new Stack<>();
		newStk=postFixStk;
		Stack<Object> retStk =new Stack<>();
		for (int i = newStk.size()-1; i >= 0; i--) {
		if (newStk.get(i).getClass().equals(ComparisonStatement.class)){
			retStk.push((Table)selectRowOneCondtion(myTable, (ComparisonStatement) newStk.get(i)));
//			System.out.println("7alwa");
		}
		else{
			retStk.push(newStk.get(i));
		}
		}
//		System.out.println("size = " +retStk.size());
		return retStk;
	}
	private Table selectRow(Table myTable, Stack<Object> postFixStk) throws Exception {
		if (!colsExistInTable(myTable, postFixStk)) {LogClass.log().error("Column not Found");
			throw new DataNotFound("Column not Found");
		}
		return evalutaePostFix(myTable, postFixStk);
	}
	private Table evalutaePostFix(Table myTable , Stack<Object> postFixStk) throws Exception{
		postFixStk=CSStkToTableStk(myTable, postFixStk);
		Stack<Table> AssisStk =new Stack<>();
		while (!postFixStk.isEmpty()){
			if (postFixStk.peek().getClass().getName().equalsIgnoreCase(Table.class.getName())){
				AssisStk.push((Table) postFixStk.pop());
//				System.out.println("sss1"+AssisStk.size());
			}
			else if (postFixStk.peek().getClass().equals(String.class)){
				String x= (String) postFixStk.pop();
//				System.out.println("sss2"+postFixStk.size());
				Table t=new Table();
				if (AssisStk.size() >1 && (x.equalsIgnoreCase("and") ||x.equalsIgnoreCase("or")) ){				
					Table t1=AssisStk.pop();
//					System.out.println("sss3"+postFixStk.size());
					Table t2=AssisStk.pop();
//					System.out.println("sss4"+postFixStk.size());
					if (x.equalsIgnoreCase("and")){
						t=IntesectionOfTwoTables(myTable, t1, t2);
					}
				     else if (x.equalsIgnoreCase("or")){
						t=UnionOfTwoTables(myTable, t1, t2);
					}
					AssisStk.push(t);
				}
				else if ( AssisStk.size() >0 && x.equalsIgnoreCase("not")){
						AssisStk.push(inverseOfTable(myTable, AssisStk.pop()));
				}
				else {
					LogClass.log().error("Not valid condtion(structure )");
					throw new Exception("Not valid condtion(structure )");
				}
			}
		}
		if (AssisStk.size()>1)
			throw new Exception("Not valid condtion(extra operands)");
		return AssisStk.peek();	
	}
	private Table inverseOfTable(Table myTable2, Table t1) {
//		System.out.println(t1.toString());
		ArrayList<Row> r=new ArrayList<>();
//		System.out.println(myTable2.getSize());
		for (int i = 0; i < myTable2.getSize(); i++) {
//			  System.out.println(myTable2.getRow(i).getvaluesList());
			if (!t1.containsRowByData(myTable2.getRow(i))){
				r.add(myTable2.getRowInstance(i));
			}
		}
		Table newTable = new Table("SelectedTable",r,myTable.getTableColumns());
		return newTable;
	}

	private Table selectRowOneCondtion(Table myTable, ComparisonStatement cs1){
		String colName1 = cs1.getColName();
		ArrayList<Row> r=new ArrayList<>();
		for (int i = 0; i < myTable.getSize(); i++) {
			if (cs1.compare(myTable.getAValueFromRow(i, colName1))) {
				r.add(myTable.getRowInstance(i));
			}
		}
		Table newTable = new Table("SelectedTable",r,myTable.getTableColumns());
		return newTable;
	}
	private Table IntesectionOfTwoTables(Table myTable,Table t1,Table t2){
		ArrayList<Row> r=new ArrayList<>();
//		System.out.println(t1.toString());
//		System.out.println(t2.toString());
		for (int i = 0; i < t1.getSize(); i++) {
//			System.out.println("ss"+t1.getRow(i).values);
			if (t2.containsRowByData(t1.getRow(i))) {
				r.add(t1.getRowInstance(i));
			}
		}
		Table newTable = new Table("SelectedTable",r,myTable.getTableColumns());
		return newTable;
	}
	private Table UnionOfTwoTables(Table myTable ,Table t1,Table t2){
		ArrayList<Row> r=new ArrayList<>();
		r=t1.getValuesList();
		for (int i = 0; i < t2.getSize(); i++) {
			if (!t1.containsRowByData(t2.getRow(i))) {
				r.add(t2.getRowInstance(i));
			}
		}
		Table newTable = new Table("SelectedTable",r,myTable.getTableColumns());
		return newTable;
	}
	private boolean colsExistInTable(Table myTable ,ArrayList<String> cols){
		for (int i = 0; i < cols.size(); i++) {
			if (!myTable.ColExist(cols.get(i))) {
				return false;
			}
		}
		return true;
	}
	private boolean colsExistInTable(Table myTable ,Stack<Object> stk){
		Stack<Object> stkCopy = new Stack<>();
			while(!stkCopy.isEmpty()){
				if (stkCopy.peek().getClass().equals(ComparisonStatement.class)){
					String colName=((ComparisonStatement) stkCopy.pop()).getColName();
					if (!myTable.ColExist(colName)){
						return false;
					}
				}
				else {
					stkCopy.pop();
				}
		}
		return true;
	}
	private boolean checkTime (){
		long e = System.nanoTime();
		int endTime = (int)(e*Math.pow(10, -9));
		Statement.time.setEndTime(endTime);
		return Statement.time.getTimeOut();
	}
	

}