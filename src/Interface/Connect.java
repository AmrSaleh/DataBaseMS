package Interface;


import java.sql.SQLException;
import java.util.Properties;

import JDBC.Connection;
import JDBC.ResultSet;
import JDBC.Statement;



public class Connect {
	
	private boolean connect = false;
	private Statement statement;
	private Connection con;
	
	public String connect(String statement) throws SQLException{
		statement = toLower(statement);
		if(statement.replace(" ", "").startsWith("connect")){
			if(connect)
				return "Please Close The Current Connection First!";
			if(!valideConnect(statement))
		    	return"Syntax Error !";
		    String [] input = getInput(statement);
		    createConnection(input);
		    if(connect)
		    	return "Connection done successfully";
		    return "Connection Error !";
		}
		else{
			if(connect){
				String statement1 = statement.toLowerCase().replace(" ", ""); 
				if(statement.toLowerCase().replace(" ", "").equals("close;"))
	    			return close();
		    	this.statement = con.createStatement();
		    	if(statement1.startsWith("select"))
		    	   return select(statement);
		    	return update(statement);
			}
			return "Please Connect first!";
		}
	}
	
	private String close() throws SQLException{
		this.statement.close();
		this.statement = null;
		con.close();
		con = null;
		connect = false;
		return "Connection Closed !";
	}
	
	private String toLower(String statement){
		String newStatement="";
		String[] array = statement.split(" ");
	    for(int i=0; i<array.length; i++){
	    	if(array[i].equalsIgnoreCase("connect"))
	    		array[i] = "connect";
	    	newStatement += array[i]+" ";
	    }
		return newStatement;
	}
	
	private boolean valideConnect(String statement){
		statement = statement.replace("connect", "");
		if(!statement.replace(" ", "").startsWith("(") || !statement.replace(" ", "").endsWith(");"))
			return false;
		statement = statement.replace("(", "");
		statement = statement.replace(");", "");
		String[]array = statement.split(",");
		if(array.length!=3)
			return false;
		return true;
	}
	
	private String[] getInput(String statement){
		String[] input = new String[3];
		statement = statement.replace("connect", "").replace("(", "").replace(");", "").replace(" ", "").replace("\"", "");
		String [] array = statement.split(",");
		input[0] = array[0];
		input[1] = array[1];
		input[2] = array[2];
		return input;
	}
	
	private void createConnection(String[]input) throws SQLException{
		JDBC.Driver driver = new JDBC.Driver();
		Properties info = new Properties();
		info.put("Name", input[1]);
		info.put("Password", input[2]);
		con = driver.connect(input[0], info);
		if(con != null){
			statement = con.createStatement(); 
			connect = true;
		}
	}
	
	private String select(String statement) throws SQLException{
		ResultSet resultSet = this.statement.executeQuery(statement);
		if(resultSet != null)
		    return ((ResultSet) resultSet).getTable().toString();
		else
			return "Error!";
	}
	
	private String update(String statement) throws SQLException{
		int result = this.statement.executeUpdate(statement);
		if(result == 1)
		     return "Data Updated Successfully";
		return "Error!";
	}

}
