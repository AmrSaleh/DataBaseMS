package DBMS;

public class ComparisonStatement {

	private String colName;
	private String value;
	private String operation;
	private int operationInteger;

	public ComparisonStatement(String colName, String value, String operation) {
		this.colName = colName;
		this.value = value;
		this.operation = operation;
		if (operation.equals(">")){
			operationInteger=1;
		}
		else if (operation.equals("<")){
			operationInteger=-1;
		}
		else if (operation.equals("<>")){
			operationInteger=2;
		}
		else if (operation.equals("<=")){
			operationInteger=3;
		}
		else if (operation.equals(">=")){
			operationInteger=4;
		}
		else{
			operationInteger=0;
		}
	}

	public String getColName() {
		return colName;
	}

	public String getValue() {
		return value;
	}
	public String getOperation() {
		return operation;
	}
	public int getOperationInteger() {
		return operationInteger;
	}

	public boolean compare(String valueFromRow) {
		System.out.println("fffffffff"+colName+value+operation+operationInteger);
		if (valueFromRow.compareTo(value) == 0 && operationInteger == 0) {
			return true;
		} else if (valueFromRow.compareTo(value) < 0 && operationInteger ==-1) {
			return true;
		} else if (valueFromRow.compareTo(value) > 0 && operationInteger ==1) {
			return true;
		} else if ((valueFromRow.compareTo(value) != 0)  && operationInteger == 2) {
			return true;
		}  else if ((valueFromRow.compareTo(value) <= 0)  && operationInteger == 3) {
			return true;
		}else if ((valueFromRow.compareTo(value) >= 0)  && operationInteger == 4) {
			return true;
		}else {
			return false;
		}

	}
}
