package JDBC;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;


import DBMS.Table;

public class ResultSet implements java.sql.ResultSet {

	private Table myResultSet;
	private int currentRow;
	private int fetchDirection;
	private ResultSetMetaData meta;
	private Statement resultSetStatement;
	public ResultSet(Table mytable, Statement statement) {
		// TODO Auto-generated constructor stub
		myResultSet = mytable;
		currentRow = 0;
		fetchDirection = FETCH_FORWARD;
		meta = new ResultSetMetaData(myResultSet.getTableColumns());
		resultSetStatement=statement;
	}

	public Table getTable() {
		return myResultSet;
	}
	private String checkDateFormat(String s)
	{
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
	public boolean absolute(int row) throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		if(myResultSet.getSize()==0){
			return false;
		}
		if(row<0)
		{
			row += myResultSet.getSize()+1;
		}
		currentRow = row;
		if(currentRow>myResultSet.getSize())
		{
			currentRow = myResultSet.getSize()+1;
			return false;
		}
		if(currentRow<1)
		{
			currentRow = 0;
			return false;
		}
		return true;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterLast() throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		if(myResultSet.getSize()==0)
		{
			return;
		}
		currentRow = myResultSet.getSize()+1;
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeFirst() throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		if(myResultSet.getSize()==0)
		{
			return;
		}
		currentRow = 0;
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws SQLException {
		if(isClosed())
		{
			return;
		}
		myResultSet = null;
		
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int findColumn(String colName) throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		if(myResultSet.ColExist(colName))
		{
			return (myResultSet.getColID(colName)+1);
		}
		else
		{
			throw new SQLException();
		}
		// TODO Auto-generated method stub
	}

	@Override
	public boolean first() throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		if(myResultSet.getSize()==0)
		{
			return false;
		}
		currentRow = 1;
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Array getArray(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			String type = myResultSet.getTableColumns().get(colIndex-1).getType();
			if(type==null)
			{
				return null;
			}
			Object[] result={(myResultSet.getRow(currentRow-1).getValueOf(colIndex-1))};
			JDBC.Array output = new JDBC.Array(result, type);
			return output;
		}
		// TODO Auto-generated method stub
		throw new SQLException();
	}

	@Override
	public Array getArray(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			if((myResultSet.getRow(currentRow-1).getValueOf(colIndex-1).equals("")||myResultSet.getRow(currentRow-1).getValueOf(colIndex-1).equals("0")))
			{
				return false;
			}
			if(myResultSet.getRow(currentRow-1).getValueOf(colIndex-1).equals("1"))
			{
				return true;
			}
		}
		// TODO Auto-generated method stub
		throw new SQLException();
	}

	@Override
	public boolean getBoolean(String colName) throws SQLException {
		if(myResultSet.ColExist(colName))
		{
			return getBoolean(myResultSet.getColID(colName)+1);
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public byte getByte(int colIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow!=myResultSet.getSize()+1&&currentRow!=0&&myResultSet.getRow(currentRow-1).getValueOf(colIndex-1)==null)
		{
			return null;
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			String element = myResultSet.getRow(currentRow-1).getValueOf(colIndex-1);
			String dateFormat=checkDateFormat(element);
			if(dateFormat!=null)
			{
				try{
					DateFormat formatter1 = new SimpleDateFormat(dateFormat);
					Date date = new Date(formatter1.parse(element).getTime());
					return date;
					} catch (ParseException e) {
						throw new SQLException();
					}
			}
		}
		throw new SQLException();
	}

	@Override
	public Date getDate(String colName) throws SQLException {
		if(myResultSet.ColExist(colName))
		{
			return getDate(myResultSet.getColID(colName)+1);
		}
		throw new SQLException();
	}

	@Override
	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDouble(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow!=myResultSet.getSize()+1&&currentRow!=0&&myResultSet.getRow(currentRow-1).getValueOf(colIndex-1)==null)
		{
			return 0;
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			try{
				  Double num = Double.parseDouble(myResultSet.getRow(currentRow-1).getValueOf(colIndex-1));
				  return num;
				} catch (NumberFormatException e) {
					throw new SQLException();
				  // not an integer!
				}		
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public double getDouble(String colName) throws SQLException {
		if(myResultSet.ColExist(colName))
		{
			return getDouble(myResultSet.getColID(colName)+1);
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public int getFetchDirection() throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		// TODO Auto-generated method stub
		return this.fetchDirection;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow!=myResultSet.getSize()+1&&currentRow!=0&&myResultSet.getRow(currentRow-1).getValueOf(colIndex-1)==null)
		{
			return 0;
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			try{
				  float num = Float.parseFloat(myResultSet.getRow(currentRow-1).getValueOf(colIndex-1));
				  return num;
				} catch (NumberFormatException e) {
					throw new SQLException();
				  // not an integer!
				}		
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public float getFloat(String colName) throws SQLException {
		if(myResultSet.ColExist(colName))
		{
			return getFloat(myResultSet.getColID(colName)+1);
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow!=myResultSet.getSize()+1&&currentRow!=0&&myResultSet.getRow(currentRow-1).getValueOf(colIndex-1)==null)
		{
			return 0;
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			try{
				  int num = Integer.parseInt(myResultSet.getRow(currentRow-1).getValueOf(colIndex-1));
				  return num;
				} catch (NumberFormatException e) {
					throw new SQLException();
				  // not an integer!
				}		
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public int getInt(String colName) throws SQLException {
		if(myResultSet.ColExist(colName))
		{
			return getInt(myResultSet.getColID(colName)+1);
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public long getLong(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow!=myResultSet.getSize()+1&&currentRow!=0&&myResultSet.getRow(currentRow-1).getValueOf(colIndex-1)==null)
		{
			return 0;
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			try{
				  long num = Long.parseLong(myResultSet.getRow(currentRow-1).getValueOf(colIndex-1));
				  return num;
				} catch (NumberFormatException e) {
					throw new SQLException();
				  // not an integer!
				}		
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public long getLong(String colName) throws SQLException {
		if(myResultSet.ColExist(colName))
		{
			return getLong(myResultSet.getColID(colName)+1);
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		return meta;
		// TODO Auto-generated method stub
	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int colIndex) throws SQLException {
		if(isClosed()||colIndex>myResultSet.getNumberOfColumns()||colIndex<1)
		{
			throw new SQLException();
		}
		if(currentRow!=myResultSet.getSize()+1&&currentRow!=0&&myResultSet.getRow(currentRow-1).getValueOf(colIndex-1)==null)
		{
			return 0;
		}
		if(currentRow>0&&currentRow<=myResultSet.getSize())
		{
			Object result =(myResultSet.getRow(currentRow-1).getValueOf(colIndex-1));
			return result;
		}
		throw new SQLException();
		// TODO Auto-generated method stub
	}

	@Override
	public Object getObject(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int arg0, Map<String, Class<?>> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Statement getStatement() throws SQLException {
		if(isClosed())
		{
			throw new SQLException();
		}
		return resultSetStatement;
		// TODO Auto-generated method stub
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub

		if (isClosed() || columnIndex > myResultSet.getNumberOfColumns() || columnIndex <= 0) {
			throw new SQLException();
		}
		// this.previous();
		// if(this.next()==false){
		// throw new SQLException();
		// }

		if (currentRow == 0 || currentRow > this.getTable().getSize()) {

			throw new SQLException();
		}

		return this.getTable().getRow(currentRow - 1).getValueOf(columnIndex - 1);

		// return null;
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		if (currentRow == 0 || currentRow > this.getTable().getSize() || isClosed()) {
			throw new SQLException();
		}
		
		int temp=-1;
		for (int i = 0; i < (myResultSet.getTableColumns().size()); i++) {
			if(myResultSet.getTableColumns().get(i).getColName().equalsIgnoreCase(columnLabel)){
				temp=i;
			}
		}
		
		
		if(temp==-1){
			throw new SQLException();
		}
		
		return this.getTable().getRow(currentRow - 1).getValueOf(temp);
//		return null;
	}

	@Override
	public Time getTime(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public URL getURL(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		if(isClosed()){
			throw new SQLException();
		}
		
		if(myResultSet.getSize()==0){
			return false;
		}
		
		if(currentRow<=myResultSet.getSize()){
			return false;
		}
	
//		if(curruntRow> myResultSet.getSize()){
//			return true;
//		}
		
		
		
		return true;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		if(isClosed()){
			throw new SQLException();
		}
		
		if(myResultSet.getSize()==0){
			return false;
		}
		
		if(currentRow <=0){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		if(myResultSet==null){
			return true;
		}
		
		
		return false;
	}

	@Override
	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		if(isClosed()){
			throw new SQLException();
		}
		
		if(currentRow == 1){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		if(isClosed()){
			throw new SQLException();
		}
		
		if(currentRow == myResultSet.getSize()){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		if(isClosed()){
			throw new SQLException();
		}
		
		if( myResultSet.getSize()==0){
			return false;
		}
		
		currentRow = myResultSet.getSize();
		
		return true;
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean next() throws SQLException {
		// TODO Auto-generated method stub
		
		if(isClosed()){
			throw new SQLException();
		}
		
		if(isAfterLast()){
			
			return false;
			
		}
		currentRow++;
		
		if(isAfterLast()){
			
			return false;
			
		}
		return true;
	}

	@Override
	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		if(isClosed()){
			throw new SQLException();
		}
		
		if(isBeforeFirst()){
			
			return false;
			
		}
		currentRow--;
		
		if(isBeforeFirst()){
			
			return false;
			
		}
		return true;
	}

	@Override
	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean relative(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(int arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(String arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(int arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(String arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(int arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(String arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(int arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(String arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(int arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(String arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(int arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(String arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(String arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(int arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(String arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(int arg0, Object arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(String arg0, Object arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(int arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(String arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(int arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(String arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(int arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(String arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(String arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
