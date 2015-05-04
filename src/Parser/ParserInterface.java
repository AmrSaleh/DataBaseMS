package Parser;

import DBMS.Table;

public interface ParserInterface {
	
	String parse(String statement);
	Table getTable();
	boolean getExecute();

}
