package DBMS;


import java.util.ArrayList;


public class Row {

    int numberOfColumns;
    ArrayList<String> values;
    
    public Row() {
		// TODO Auto-generated constructor stub
	}
    public Row(Row r) {
		// TODO Auto-generated constructor stub
    	values= new ArrayList<>();
    	for (int i = 0; i < r.values.size(); i++) {
			this.values.add(r.values.get(i));
		}
	}
    public Row(ArrayList<String> newValues ){
       numberOfColumns=newValues.size();
       values=newValues;
    }
   
	public String getValueOf(int index){
		return values.get(index);
    }
	
	public void setValueOf(int index , String s){
		values.set(index, s);
    }
	
	public ArrayList<String> getvaluesList(){
		return values;
	}

	public void setValueList(ArrayList<String> newValue){
		values=newValue;
		 numberOfColumns=newValue.size();
    }
	
	public ArrayList<String> getValueList(){
		return values;
    }
	 public int size(){
		  return values.size();
		 }
	
}