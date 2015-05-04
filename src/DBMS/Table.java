package DBMS;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Table {

//	private int numberOfColumns;
//	public int numberOfRows;
	private ArrayList<Column> tableColumns;
	private ArrayList<Row> tableRows;
	String tableName;
	

	public Table() {
		tableColumns=new ArrayList<>();
		tableRows=new ArrayList<>();
		tableName="unKnownTable";
	}
	public Table(String s) {
		tableName=s;
	}
    public Table(String s,ArrayList<Row> Rows,ArrayList<Column> columns){
    	tableRows=Rows;
    	setTableColumns(columns);
    	tableName=s;
    }
    
    public String getTableName(){
    	return tableName;
    }
    
    public int getNumberOfColumns(){
    	return tableColumns.size();
    }
	public int getColID(String col_name) {

		for (int i = 0; i < getTableColumns().size(); i++) {
			if (col_name.equals(getTableColumns().get(i).getColName())){
				return i;
			}
		}
		return 0;

	}

	public String getAValueFromRow(int index, String colName) {
		return tableRows.get(index).getValueOf(getColID(colName));

	}
	public void setAValueInRow(int index, String colName , String newValue) {
	     tableRows.get(index).setValueOf(getColID(colName), newValue);
	}

	public boolean ColExist(String string) {
		boolean found=false;
		for (int i = 0; i < getTableColumns().size(); i++) {
			if (getTableColumns().get(i).getColName().equals(string)){
				return found=true;
			}
		}
		return found;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return tableRows.size();
	}

	public void add(Row row) {
		// TODO Auto-generated method stub
		tableRows.add(row);
	}

	public Row getRowInstance(int i) {
		// TODO Auto-generated method stub
		Row e = new Row(tableRows.get(i));
		return e;
	}

	public Row getRow(int i) {
		// TODO Auto-generated method stub
		return tableRows.get(i);
	}

	 public ArrayList<Row> getValuesList (){
	        return tableRows;
	    }
	 public Column getColumnInstance(String colName){
		 
		 String n=tableColumns.get(getColID(colName)).getColName();
		 String t=tableColumns.get(getColID(colName)).getType();
		 Column c= new Column(n,t);
		 return c;
	 }
	public ArrayList<Column> getTableColumns() {
		return tableColumns;
	}
	public void setTableColumns(ArrayList<Column> tableColumns) {
		this.tableColumns = tableColumns;
	}
	 public String toString(){
		 String table = "";
   		 int [] length = getLength();
   		 table += drawBorder(table, length);
   		 for(int i=0; i<tableColumns.size(); i++){
   			 table += tableColumns.get(i).getColName();
   			 if(length[i]>tableColumns.get(i).getColName().length()){
   				 for(int j=0; j<(length[i]-tableColumns.get(i).getColName().length()); j++)
   					 table +=" ";
   			 }
   			 table += "|";
   		 }
   		 table = drawBorder(table, length);
   		 table = drawRows(table, length);
   		 table = drawBorder(table, length);
    	 table = table.substring(0, table.length()-1);	
 
		 return table;
	 }
 
	 private String drawRows(String table, int[]length){
		 String S = table;
		 for(int i=0;i<tableRows.size(); i++){
			 for(int j=0; j<tableColumns.size(); j++){
				 S += tableRows.get(i).getValueOf(j);
				 if(length[j]>tableRows.get(i).getValueOf(j).length()){
					 for(int k=0; k<(length[j]-tableRows.get(i).getValueOf(j).length()); k++)
						 S +=" ";
				 }
				 S += "|";
			 }
			 S +="\n|";
		 }
		 S = S.substring(0, S.length()-2);
		 return S;
	 }
 
	 private int[] getLength(){
		 int [] length = new int[tableColumns.size()];
		 for(int i=0; i<tableColumns.size(); i++){
			 for(int j=0; j<tableRows.size(); j++){
				 if(tableRows.get(j).getValueOf(i).length() > length[i])
					 length[i] = tableRows.get(j).getValueOf(i).length();
			 }
		 }
		 for(int i=0; i<tableColumns.size(); i++){
			 if(tableColumns.get(i).getColName().length()> length[i])
				 length[i] = tableColumns.get(i).getColName().length();
		 }
		 return length;
	 }
 
	 private String drawBorder (String table, int[]length){
		 String S = table +"\n";
		 S += "+";
		 for(int i=0; i<length.length; i++){
			 for(int j=0; j<length[i]+1 ; j++)
				 S += "-";
		 }
		 S = S.substring(0, S.length()-1);
		 S+="+\n|";
		 return S;
	 }
	 public Column getColumn(String colName){
		   return tableColumns.get(getColID(colName));
		  }
	
	 
	 public boolean containsRowByData(Row r){
			if (tableRows.size()==0){
				return false;
			}
			if (r.size() != tableRows.get(0).size() ){
				return false;
			}
			for (int i = 0; i <tableRows.size(); i++) {
				boolean c= true;
				for (int j = 0; j < tableRows.get(i).size(); j++) {
					if(!tableRows.get(i).getValueOf(j).equals(r.getValueOf(j))){
						c=false;
						break;
					}
				}
				if (c==true){
					return c;
				}
			}
			return false;
		}
		public boolean isValidUpdate(ArrayList<String> colName ,ArrayList<String> update) throws Exception {
			for (int i = 0 ; i < colName.size() ; i ++){
				Column c=this.getColumn(colName.get(i));
				if (c.isReadOnly() || c.isAutoIncrement()){
					throw new Exception("Not valid update exception [Read only]");
				}
				else{
					String s= update.get(i);
					if (!validDataType(s, c)){
						throw new Exception("Not valid update exception [Data type]");
					}
					else if (c.isPrimary() &&( s.equals("") || valueExistInCol(s, c.getColName()))){
						throw new Exception("Not valid update exception [primary key]");
					}
					else if (c.getNullableState()==0 && s.equals("")){
						throw new Exception("Not valid update exception [Nallable state]");
					}
				}
			}
			return true;
		}
		public boolean isValidUpdate(ArrayList<String> colName ,ArrayList<String> update ,int i) throws Exception {
				Column c=this.getColumn(colName.get(i));
				
				if (c.isReadOnly() || c.isAutoIncrement()){
					throw new Exception("Not valid update exception [Read only]");
				}
				else{
					String s= update.get(i);
					if (!validDataType(s, c)){
						throw new Exception("Not valid update exception [Data type]");
					}
					else if (c.isPrimary() &&( s.equals("") || valueExistInCol(s, c.getColName()))){
						throw new Exception("Not valid update exception [primary key]");
					}
					else if (c.getNullableState()==0 && s.equals("")){
						throw new Exception("Not valid update exception [Nullable state]");
					}
				}
			return true;
		}
		private boolean valueExistInCol(String s ,String colName){
			for (int i = 0; i < this.tableRows.size(); i++) {
				if (s.equals(this.getAValueFromRow(i, colName))){
					return false;
				}
			}
			return true;
		}
		private boolean validDataType(String s, Column col) {
			String dataType = col.getType();
			//Integer
			if (dataType.equalsIgnoreCase("int")) {  return checkInteger(s);
			//boolean
			} else if (dataType.equalsIgnoreCase("boolean")){ return checkBoolean(s);
			//date dd/mm/yyyy
			}else if (dataType.equalsIgnoreCase("date")){ return checkDate(s);
		    //long
			}else if (dataType.equalsIgnoreCase("long")){ return checkLong(s);
			//float
			}else if (dataType.equalsIgnoreCase("float")){ return checkFloat(s);
			//double
			}else if (dataType.equalsIgnoreCase("double")){ return checkDouble(s); }
			//String
			return true;
		}
		
		private static boolean checkBoolean(String data){
		    
		    if(data.equals("1")|| data.equals("0")){
					return true;
				}
				else{
					return false;
				}
		    
		}
		
		private static boolean checkDate(String data){
			String dateFor=GetdateFormate(data);
			if (dateFor==null){
				return false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(dateFor);
			sdf.setLenient(false);
			try { 
				Date date = (Date) sdf.parse(data);
//				System.out.println(date+" in check date");
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
	 
			return true;
		   
		}
		
		private static boolean checkInteger(String data){
		    
		    	try {
					Integer.parseInt(data);
				} catch (NumberFormatException e) {
					return false;
				}
		    return true;
		}
		
		private static boolean checkLong(String data){
		    try {
					Long.parseLong(data);
				} catch (NumberFormatException e) {
					return false;
				}
		    return true;
		    
		    
		}
		
		private static boolean checkFloat(String data){
		    
		    
		   try {
					Float.parseFloat(data);
				} catch (NumberFormatException e) {
					return false;
				}
		    return true;
		}
		
		private static boolean checkDouble(String data){
		    
		    
		    try {
					Double.parseDouble(data);
				} catch (NumberFormatException e) {
					return false;
				}
		    return true;
		}
		
		private static String GetdateFormate(String s){
			String dateFormat=null;
			if (s.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
				dateFormat="yyyy-MM-dd";
			}
			else if (s.matches("\\d{4}")){
				dateFormat="yyyy";
			}
			else if (s.matches("\\d{2}")){
				dateFormat="yy";
			}
			else if (s.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}")){
				dateFormat="yyyy-MM-dd hh:mm:ss";
			}
			else if (s.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}")){
				dateFormat="yyyy-MM-dd hh:mm";
			}
			return dateFormat;
		}
		
		public ArrayList<Integer> getIndecesOfIntrsectingRowsWith(Table t){
			ArrayList<Integer> indeces = new ArrayList<>();
			for (int i = 0; i < this.getSize(); i++) {
				if (t.containsRowByData(this.getRow(i))){
					indeces.add(i);
				}
			}
			return indeces;
			
	}
}
