package DBMS;

import JDBC.ResultSetMetaData;

public class Column {

	private String name;
	private String type;
	private boolean isPrimary;
	private boolean isAutoIncrement;
	private int NullableState;
	private boolean isReadOnly;
	private boolean isSearchable;
	private String tableName;
	private int incrementNextValue;

//	ResultSetMetaData.columnNoNulls => 0
//	ResultSetMetaData.columnNullable => 1
//	ResultSetMetaData.columnNullableUnknown => 2
	
	public Column() {
		// TODO Auto-generated constructor stub
		NullableState = ResultSetMetaData.columnNullableUnknown;
		isSearchable = true;
		tableName = "unknown";
		incrementNextValue = 1;
	}

	public Column(String n, String t) {
		name = n;
		setType(t);

		NullableState = ResultSetMetaData.columnNullableUnknown;
		isSearchable = true;
		tableName = "unknown";
		incrementNextValue = 1;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String s) {
		tableName = s;
	}

	public String getColName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setColName(String s) {
		// TODO Auto-generated method stub
		name = s;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public int getNullableState() {
		return NullableState;
	}

	public void setNullableState(int Nullable) {
		this.NullableState = Nullable;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public boolean isSearchable() {
		return isSearchable;
	}

	public void setSearchable(boolean isSearchable) {
		this.isSearchable = isSearchable;
	}

	public int getIncrementNextValue() {
		return incrementNextValue;
	}

	public void setIncrementNextValue(int incrementNextValue) {
		this.incrementNextValue = incrementNextValue;
	}

}
