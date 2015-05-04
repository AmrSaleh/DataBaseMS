package JDBC;

import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
	
	private String URL = "URL";
	private String Name = "Nada";
	private String Password = "Nada";

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		if(URL.equals(url))
		    return true;
		else
			return false;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if(URL.equals(url) && info.size()==2 && info.getProperty("Name").equals(Name) && info.getProperty("Password").equals(Password)){
			//Connection con = DriverManager.getConnection(URL, Name, Password);
			Connection con = new JDBC.Connection();
		    return con;
		}
		else
			return null;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		if(URL.equals(url) && info.size()==2 && info.getProperty("Name").equals(Name) && info.getProperty("Password").equals(Password)){
		    DriverPropertyInfo[] propertyInfo = new DriverPropertyInfo[2];
		    propertyInfo[0] = new DriverPropertyInfo("Name", Name);
		    propertyInfo[1] = new DriverPropertyInfo("Password", Password);
		    return propertyInfo;
		}
		else
		   return null;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
