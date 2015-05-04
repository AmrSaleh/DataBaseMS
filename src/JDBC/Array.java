package JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Array implements java.sql.Array{
	private Object[] arrayElements;
	private String type;
	Array(Object[] arrayElements, String type)
	{
		this.arrayElements=arrayElements;
		this.type=type;
	}
	@Override
	public void free() throws SQLException {
		// TODO Auto-generated method stub
		arrayElements=null;
		type = null;
	}

	@Override
	public Object getArray() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(long index, int count) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(long index, int count, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBaseType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBaseTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(long index, int count) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(long index, int count,
			Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	public Object[] getArrayElements()
	{
		return arrayElements;
	}
	public void setArrayElements(Object[] arrayElements)
	{
		this.arrayElements=arrayElements;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type=type;
	}

}
