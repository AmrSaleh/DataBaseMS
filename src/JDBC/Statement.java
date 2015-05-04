package JDBC;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;

import Parser.Parser;
import Parser.ParserInterface;

public class Statement implements java.sql.Statement {
	private int queryTimeOut = 0;
	private static ParserInterface parse = new Parser();
	private ArrayList<String> batch = new ArrayList<String>();
	private Connection con;
	private ResultSet resultSet;
	public static QueryTimeOut time = new QueryTimeOut();

	public Statement(Connection con) {
		this.con = con;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		batch.add(sql);

	}

	@Override
	public void cancel() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearBatch() throws SQLException {
		batch.clear();

	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws SQLException {
		con.close();
		con = null;
		if(resultSet != null)
		   resultSet.close();
		resultSet = null;
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean execute(String sql) throws SQLException {
		setStartTime();
		parse.parse(sql);
		if (time.getTimeOut())
			return false;
		if (parse.getTable() == null)
			return false;
		return true;
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		int[] execution = new int[batch.size()];
		for (int i = 0; i < batch.size(); i++) {
			parse.parse(batch.get(i));
			if (!parse.getExecute()) {
				execution[i] = 0;
			} else {
				execution[i] = 1;
			}
		}
		return execution;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		setStartTime();
		parse.parse(sql);
		if (time.getTimeOut())
			return null;
		if (parse.getTable() != null)
			resultSet = new ResultSet(parse.getTable(), this);
		return resultSet;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		setStartTime();
		System.out.println(parse.parse(sql));
		if (time.getTimeOut())
			return 0;
		if (parse.getExecute() && parse.getTable() == null)
			return 1;
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return con;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		queryTimeOut = seconds;
		time.setQueryTimeOut(queryTimeOut);
	}

	private void setStartTime() {
		long s = System.nanoTime();
		int startTime = (int) (s * Math.pow(10, -9));
		Statement.time.setStartTime(startTime);
	}
}