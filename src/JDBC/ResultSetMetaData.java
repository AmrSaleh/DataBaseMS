package JDBC;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import DBMS.Column;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {

	private ArrayList<Column> myColumns;
	

	public ResultSetMetaData(ArrayList<Column> ResultCols) {
		// TODO Auto-generated constructor stub
		
		myColumns = ResultCols;
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCatalogName(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnClassName(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() throws SQLException {
		// TODO Auto-generated method stub
		
		return myColumns.size();
	}

	@Override
	public int getColumnDisplaySize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
		return myColumns.get(column-1).getColName(); //col name for now until the label is implemented
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
		return myColumns.get(column-1).getColName(); 
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
		if(myColumns.get(column-1).getType().equalsIgnoreCase("int")){
			return Types.INTEGER;
		}else if(myColumns.get(column-1).getType().equalsIgnoreCase("string")){
			return Types.CHAR;
		}else if(myColumns.get(column-1).getType().equalsIgnoreCase("boolean")){
			return Types.BIT;
		}else if(myColumns.get(column-1).getType().equalsIgnoreCase("date")){
			return Types.DATE;
		}else if(myColumns.get(column-1).getType().equalsIgnoreCase("double")){
			return Types.DOUBLE;
		}else if(myColumns.get(column-1).getType().equalsIgnoreCase("float")){
			return Types.FLOAT;
		}else if(myColumns.get(column-1).getType().equalsIgnoreCase("long")){
			return Types.INTEGER;
		}
		return Types.OTHER;
	}

	@Override
	public String getColumnTypeName(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrecision(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScale(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSchemaName(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
		return myColumns.get(column-1).getTableName();//remember to set the table name for columns at instantiation at other classes  
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
			return myColumns.get(column-1).isAutoIncrement();
		
		
	}

	@Override
	public boolean isCaseSensitive(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCurrency(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int isNullable(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
		return myColumns.get(column-1).getNullableState();
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
			return myColumns.get(column-1).isReadOnly();
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
			return myColumns.get(column-1).isSearchable();
	}

	@Override
	public boolean isSigned(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		if(column<1  || column > myColumns.size()){
			throw new SQLException();
		}
		
			return !(myColumns.get(column-1).isReadOnly());
	}

}
