package DBMS;

import java.util.ArrayList;

public interface XmlInterface {
	
	
	public void insertRow(Row r , String TableName,String DataBaseName) throws Exception;
	public void deleteTable(String TableName,String DataBaseName) throws Exception;
	public void createTAble(String tableName, String DataBaseName,ArrayList<Column> columns) throws Exception;
	public void SaveTAble(Table table, String tableName, String DataBaseName)throws Exception;
}
