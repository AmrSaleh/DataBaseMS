package Parser;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Stack;

import DBMS.Column;
import DBMS.ComparisonStatement;
import DBMS.DBMS;
import DBMS.MyDBMS;
import DBMS.Table;
import JDBC.Statement;
import Logging.LogClass;

public class Parser implements ParserInterface {
	DBMS f = new MyDBMS();
	private static int primaryKey;
	private boolean execute = true;
	private Table table;

	public boolean getExecute() {
		return execute;
	}

	private String createDataBase(String s) {
		if (checkTime()){
			return "Operation Aborted";
		}
		if (s.startsWith(" ")) {
			s = s.substring(1, s.length());
		}
		if (s.endsWith(" ")) {
			s = s.substring(0, s.length() - 1);
		}
		if (s.charAt(s.length() - 2) == ' ') {
			char c = s.charAt(s.length() - 1);
			s = s.substring(0, s.length() - 2);
			s += c;
		}
		String[] str = s.split(" ");
		String d = "";
		if (str.length != 3 || str[2].charAt(str[2].length() - 1) != ';'
				|| !str[0].equalsIgnoreCase("create")
				|| !str[1].equalsIgnoreCase("database") || str[2].equals(";")) {
			execute = false;
			LogClass.log().info("Syntax Error !");
			d = "Syntax Error !";
		} else if (str[0].equalsIgnoreCase("create")
				&& str[1].equalsIgnoreCase("database")) {
			d = callDB(str[2].substring(0, str[2].length() - 1));
		}
		return d;
	}

	private String callDB(String s) {
		String d = "";
		try {
			f.createDatabase(s);
			d = "DataBase Created Successfully";
		} catch (Exception e) {
			execute = false;
			return e.getMessage();
		}
		return d;
	}

	private String createTable(String s) {
		if (checkTime()){
			return "Operation Aborted";
		}
		if (s.startsWith(" ")) {
			s = s.substring(1, s.length());
		}
		if (s.endsWith(" ")) {
			s = s.substring(0, s.length() - 1);
		}
		if (s.charAt(s.length() - 2) == ' ') {
			char c = s.charAt(s.length() - 1);
			s = s.substring(0, s.length() - 2);
			s += c;
		}
		execute = false;
		String d = "Syntax Error !";
		String[] h = s.split("\\{");
		String[] t = h[0].split(" ");
		if (t.length < 3) {
			return d;
		}
		ArrayList<Column> tableColumns = column(
				h[1].substring(0, h[1].length() - 2), t[2]);
		if (h.length != 2 || h[1].charAt(h[1].length() - 2) != '}'
				|| h[1].charAt(h[1].length() - 1) != ';' || t.length != 3
				|| !t[0].equalsIgnoreCase("create")
				|| !t[1].equalsIgnoreCase("table") || tableColumns == null) {
			LogClass.log().info("Syntax Error !");
			return d;
		} else if (h.length == 2) {
			execute = true;
			d = callTable(t[2], tableColumns);
		}
		return d;
	}

	private String callTable(String tableName, ArrayList<Column> columns) {
		String d = "";
		try {
			d = "Table Created Successfully";

			f.createTable(tableName, columns);
		} catch (Exception e) {
			execute = false;
			return e.getMessage();
		}
		return d;
	}

	public static String trimAndRemoveInterspaces(String s) { // remove
																// additional
																// spaces in the
																// string
		String result = s;
		result = result.trim();
		result = result.replaceAll("( )+", " ");

		return result;

	}

	private ArrayList<Column> column(String columnData, String tableName) {
		String[] singleColumn = columnData.split(",");
		ArrayList<Column> c = new ArrayList<Column>();
		for (int i = 0; i < singleColumn.length; i++) {
			singleColumn[i] = trimAndRemoveInterspaces(singleColumn[i]);
			String[] singleColumnSeparated = singleColumn[i].split(" ");
			if (singleColumnSeparated.length > 7
					|| singleColumnSeparated.length < 2) {
				return null;
			}
			for (int j = 0; j < singleColumnSeparated.length; j++) {
				if (validateSingleColumnSeparated(singleColumnSeparated[j], j) == false) {
					return null;
				}
			}
			if (!validateSingleColumnSeparatedProperties(singleColumnSeparated)) {
				return null;
			}
			Column col = new Column(singleColumnSeparated[0],
					singleColumnSeparated[1].toLowerCase());
			setColumnProperties(col, singleColumnSeparated, tableName);
			c.add(col);
			if (primaryKey > 1 || !(validColumns(c))) {
				return null;
			}
		}
		return c;
	}

	private boolean validColumns(ArrayList<Column> tableColumns) {
		for (int i = 0; i < tableColumns.size(); i++) {
			for (int j = i + 1; j < tableColumns.size(); j++) {
				if (tableColumns.get(i).getColName()
						.equalsIgnoreCase(tableColumns.get(j).getColName())) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean validateSingleColumnSeparatedProperties(
			String[] singleColumnSeparated) {
		for (int i = 2; i < singleColumnSeparated.length; i++) {
			if (singleColumnSeparated[i].equalsIgnoreCase("autoincrement")
					&& !(singleColumnSeparated[1].equalsIgnoreCase("int") || singleColumnSeparated[1]
							.equalsIgnoreCase("long"))) {
				return false;
			}
			for (int j = i + 1; j < singleColumnSeparated.length; j++) {
				if (singleColumnSeparated[i]
						.equalsIgnoreCase(singleColumnSeparated[j])
						|| (singleColumnSeparated[i]
								.equalsIgnoreCase("nullable") && singleColumnSeparated[j]
								.equalsIgnoreCase("notnullable"))
						|| (singleColumnSeparated[i]
								.equalsIgnoreCase("notnullable") && singleColumnSeparated[j]
								.equalsIgnoreCase("nullable"))
						|| (singleColumnSeparated[i]
								.equalsIgnoreCase("autoincrement") && singleColumnSeparated[j]
								.equalsIgnoreCase("nullable"))
						|| (singleColumnSeparated[i]
								.equalsIgnoreCase("nullable") && singleColumnSeparated[j]
								.equalsIgnoreCase("autoincrement"))
						|| (singleColumnSeparated[i]
								.equalsIgnoreCase("primarykey") && singleColumnSeparated[j]
								.equalsIgnoreCase("nullable"))
						|| (singleColumnSeparated[i]
								.equalsIgnoreCase("nullable") && singleColumnSeparated[j]
								.equalsIgnoreCase("primarykey"))) {
					return false;
				}
			}
		}
		return true;
	}

	private void setColumnProperties(Column col,
			String[] singleColumnSeparated, String tableName) {
		col.setTableName(tableName);
		for (int i = 2; i < singleColumnSeparated.length; i++) {
			singleColumnSeparated[i] = singleColumnSeparated[i].toLowerCase();
			if (singleColumnSeparated[i].equals("primarykey")) {
				primaryKey++;
				col.setPrimary(true);
			} else if (singleColumnSeparated[i].equals("autoincrement")) {
				col.setAutoIncrement(true);
				col.setReadOnly(true);
			} else if (singleColumnSeparated[i].equals("nullable")) {
				col.setNullableState(ResultSetMetaData.columnNullable);
			} else if (singleColumnSeparated[i].equals("notnullable")) {
				col.setNullableState(ResultSetMetaData.columnNoNulls);
			} else if (singleColumnSeparated[i].equals("readonly")) {
				col.setReadOnly(true);
			} else if (singleColumnSeparated[i].equals("notsearchable")) {
				col.setSearchable(false);
			}
		}
	}

	private boolean validateSingleColumnSeparated(String singleColumnSeparated,
			int indexInColumnSeparated) {
		singleColumnSeparated = singleColumnSeparated.toLowerCase();
		// Boolean, Date, String, Int, Long, Float, Double
		if (indexInColumnSeparated == 1
				&& !(singleColumnSeparated.equals("boolean")
						|| singleColumnSeparated.equals("date")
						|| singleColumnSeparated.equals("string")
						|| singleColumnSeparated.equals("int")
						|| singleColumnSeparated.equals("long")
						|| singleColumnSeparated.equals("float") || singleColumnSeparated
							.equals("double"))) {
			execute = false;
			return false;
		} else if ((indexInColumnSeparated == 2 || indexInColumnSeparated == 3
				|| indexInColumnSeparated == 4 || indexInColumnSeparated == 5 || indexInColumnSeparated == 6)
				&& !(singleColumnSeparated.equals("primarykey")
						|| singleColumnSeparated.equals("autoincrement")
						|| singleColumnSeparated.equals("nullable")
						|| singleColumnSeparated.equals("notnullable")
						|| singleColumnSeparated.equals("readonly") || singleColumnSeparated
							.equals("notsearchable"))) {
			execute = false;
			return false;
		}
		return true;
	}

	private boolean validateInsertInto(String[] str, String[] k, String[] t) {
		if (str.length != 2 || t.length != 3
				|| !t[0].equalsIgnoreCase("insert")
				|| !t[1].equalsIgnoreCase("into")
				|| str[1].charAt(str[1].length() - 2) != ')'
				|| str[1].charAt(str[1].length() - 1) != ';' || k.length > 2) {
			return false;
		}
		String[] data = getValues(str[1]);
		if (data == null || str[0].contains("'") || t[0].contains("'")
				|| t[1].contains("'") || t[2].contains("'")
				|| k[0].contains("'")) {
			return false;
		}
		if (k.length == 2 && k[1].contains("'")) {
			return false;
		}
		return true;
	}

	private String insertInto(String s) {
		if (checkTime()){
			return "Operation Aborted";
		}
		if (s.startsWith(" ")) {
			s = s.substring(1, s.length());
		}
		if (s.endsWith(" ")) {
			s = s.substring(0, s.length() - 1);
		}
		if (s.charAt(s.length() - 2) == ' ') {
			char c = s.charAt(s.length() - 1);
			s = s.substring(0, s.length() - 2);
			s += c;
		}
		String[] str = s.split("values");
		String[] k = str[0].split("\\(");
		String[] t = k[0].split(" ");
		String d = "";
		if (!validateInsertInto(str, k, t)) {
			execute = false;
			LogClass.log().info("Syntax Error !");
			d = "Syntax Error !";
		} else if (k.length == 1 || k.length == 2) {
			d = callInsertInto(str[1], k.length, t[2], k[k.length - 1]);
		}
		return d;
	}

	private String[] getValues(String str) {
		String[] data = str.split(",");

		for (int i = 0; i < data.length; i++) {
			if (!data[i].contains("'")) {
				return null;
			}
			int b = data[i].indexOf("'");
			int l = data[i].lastIndexOf("'");
			if (b == l) {
				return null;
			}
			String sub = data[i].substring(b + 1, l);
			if (sub.contains("'")) {
				return null;
			}
			data[i] = sub;
		}
		return data;
	}

	private String callInsertInto(String str, int length, String tableName,
			String k) {
		String d = "";
		// str = str.replace(" ", "");
		int openBracket = str.indexOf("(");
		int closedBracket = str.indexOf(")");
		str = str.substring(openBracket + 1, closedBracket);
		k = k.replaceAll(" ", "");
		String[] data = getValues(str);
		ArrayList<String> values = convert(data);
		if (length == 1) {
			d = insertAll(tableName, values);
		} else if (length == 2) {
			String[] col = (k.substring(0, k.length() - 1)).split(",");
			ArrayList<String> cols = convert(col);
			d = insert(tableName, cols, values);
		}
		return d;
	}

	private String insertAll(String tableName, ArrayList<String> values) {
		try {
			f.insert(tableName, values);
			return "Row inserted Successfully";
		} catch (Exception e) {
			execute = false;
			return e.getMessage();
		}
	}

	private String insert(String tableName, ArrayList<String> cols,
			ArrayList<String> values) {
		if (values.size() != cols.size()) {
			execute = false;
			LogClass.log().info("Syntax Error !");
			return "Syntax Error !";
		} else {
			try {
				f.insert(tableName, cols, values);
				return "Row inserted Successfully";
			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		}
	}

	private ArrayList<String> convert(String[] arr) {
		ArrayList<String> a = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			a.add(arr[i]);
		}
		return a;
	}

	private String toLowerCase(String s) {
		while (s.contains("  ")) {
			s = s.replace("  ", " ");
		}
		String[] str = s.split(" ");
		for (int i = 0; i < str.length; i++) {
			if (str[i].equalsIgnoreCase("use")
					|| str[i].equalsIgnoreCase("values")
					|| str[i].equalsIgnoreCase("select")
					|| str[i].equalsIgnoreCase("from")
					|| str[i].equalsIgnoreCase("delete")
					|| str[i].equalsIgnoreCase("where")
					|| str[i].equalsIgnoreCase("update")
					|| str[i].equalsIgnoreCase("set")
					|| str[i].equalsIgnoreCase("create")
					|| str[i].equalsIgnoreCase("database")
					|| str[i].equalsIgnoreCase("table")
					|| str[i].equalsIgnoreCase("insert")
					|| str[i].equalsIgnoreCase("into")) {
				str[i] = str[i].toLowerCase();
			}
		}
		return toString(str);
	}

	private String toString(String[] s) {
		String d = "";
		for (int i = 0; i < s.length; i++) {
			d += s[i] + " ";
		}
		d = d.substring(0, d.length() - 1);
		return d;
	}

	private String setDataBase(String s) {
		if (checkTime()){
			return "Operation Aborted";
		}
		if (s.startsWith(" ")) {
			s = s.substring(1, s.length());
		}
		if (s.endsWith(" ")) {
			s = s.substring(0, s.length() - 1);
		}
		if (s.charAt(s.length() - 2) == ' ') {
			char c = s.charAt(s.length() - 1);
			s = s.substring(0, s.length() - 2);
			s += c;
		}
		String[] str = s.split(" ");
		String d = "";
		if (str.length != 2 || str[1].charAt(str[1].length() - 1) != ';') {
			execute = false;
			LogClass.log().info("Syntax Error !");
			d = "Syntax Error !";
		} else {
			try {
				str[1] = str[1].substring(0, str[1].length() - 1);
				f.setDatabase(str[1]);
				d = "Done. Enter the first SQL command";
			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		}
		return d;
	}


	private boolean checkName(String name) {
		boolean found = false;
		String s = name.replace(" ", "");
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ';' && i != s.length() - 1)
				return false;
		}
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == ' '
					&& found == true
					&& !name.substring(i).replace(" ", "").replace(";", "")
							.equals("") || name.charAt(i) == ','
					|| name.charAt(i) == '=' || name.charAt(i) == '<'
					|| name.charAt(i) == '>') {
				return false;
			}
			if (name.charAt(i) != ' ' && found == false)
				found = true;
		}
		return true;
	}

	private boolean checkColumns(String columns) {
		if (!columns.replace(" ", "").equals("*")) {
			if (columns.replace(" ", "").charAt(0) == ','
					|| columns.replace(" ", "").charAt(
							columns.replace(" ", "").length() - 1) == ',')
				return false;
			String seperate[] = columns.split(",");
			for (int i = 0; i < seperate.length; i++) {
				if (seperate[i] == null || checkName(seperate[i]) == false)
					return false;
			}
		}
		return true;
	}

	private boolean validateSelect(String Statement) {
		if (Statement == null || !Statement.contains("select")
				|| !Statement.contains("from") || !Statement.contains(";"))
			return false;
		Statement = Statement.replace("select", "");
		if (Statement.replace(" ", "").charAt(
				Statement.replace(" ", "").length() - 1) != ';')
			return false;
		String seperation1[] = Statement.split("from");
		if (seperation1.length > 2 || seperation1[1] == null
				|| seperation1[0] == null)
			return false;
		if (checkColumns(seperation1[0])) {
			if (seperation1[1].contains("where")) {
				String seperation2[] = seperation1[1].split("where");
				if (seperation2.length > 2 || seperation2[0] == null)
					return false;
				if (checkName(seperation2[0]))
					return checkWhere(seperation2[1]);
			}
			return checkName(seperation1[1]);
		}
		return false;
	}

	private boolean validateDelete(String Statement) {
		if (Statement == null || !Statement.contains("delete")
				|| !Statement.contains("from") || !Statement.contains(";"))
			return false;
		if (Statement.charAt(Statement.length() - 1) != ';')
			return false;
		String seperation1[] = Statement.split("from");
		if (seperation1.length > 2 || seperation1[1] == null
				|| !seperation1[0].replace(" ", "").equals("delete"))
			return false;
		if (seperation1[1].contains("where")) {
			String seperation2[] = seperation1[1].split("where");
			if (seperation2.length > 2 || seperation2[0] == null)
				return false;
			if (checkName(seperation2[0]))
				return checkWhere(seperation2[1]);
		}
		return checkName(seperation1[1]);
	}

	private boolean validateUpdate(String Statement) {
		if (Statement == null || !Statement.contains("update")
				|| !Statement.contains("set") || !Statement.contains(";"))
			return false;
		Statement = Statement.replace("update", "");
		String seperation1[] = Statement.split("set");
		if (seperation1[0] == null || checkName(seperation1[0]) == false)
			return false;
		if (seperation1.length > 2 || seperation1[1] == null
				|| seperation1[0] == null)
			return false;
		if (seperation1[1].contains("where")) {
			String seperation2[] = seperation1[1].split("where");
			if (checkWhere(seperation2[0]))
				return checkWhere(seperation2[1]);
		}
		return checkWhere(seperation1[1]);
	}

	private boolean checkComparison(String where) {
		if (where.equals("") || where.charAt(0) == ','
				|| where.charAt(where.length() - 1) == ',')
			return false;
		String seperation[] = where.split(",");
		for (int i = 0; i < seperation.length; i++) {
			if (seperation[i] == null
					|| (!seperation[i].contains("=")
							&& !seperation[i].contains("<") && !seperation[i]
								.contains(">") && !seperation[i].contains("<=") && !seperation[i].contains(">=") && !seperation[i].contains("<>")))
				return false;
			String seperate[] = null;
			boolean split = true;
			if (seperation[i].contains("<=")){
				seperate = seperation[i].split("<=");
				split = false;
			}
			if (seperation[i].contains(">=")){
				split = false;
				seperate = seperation[i].split(">=");
			}
			if (seperation[i].contains("<>")){
				split = false;
				seperate = seperation[i].split("<>");
			}
			if (seperation[i].contains("=") && split)
				seperate = seperation[i].split("=");
			if (seperation[i].contains("<") && split)
				seperate = seperation[i].split("<");
			if (seperation[i].contains(">") && split)
				seperate = seperation[i].split(">");
			if (checkName(seperate[0])) {
				if (seperate[1].replace(" ", "").charAt(0) != '\''
						|| (seperate[1].replace(" ", "").charAt(
								seperate[1].replace(" ", "").length() - 1) != '\'' && !seperate[1]
								.replace(" ", "").contains(";"))
						|| (seperate[1].replace(" ", "").charAt(
								seperate[1].replace(" ", "").length() - 2) != '\'' && seperate[1]
								.replace(" ", "")
								.charAt(seperate[1].replace(" ", "").length() - 1) == ';'))
					return false;
			}
		}
		return true;
	}
	
	private boolean checkWhere(String where){
		String comparison = "";
		String[] array = where.replace(";", "").replace(")", "").replace("(", "").split(" ");
		for(int i=0; i<array.length; i++){
			if(!array[i].equalsIgnoreCase("and") && !array[i].equalsIgnoreCase("or") && !array[i].equalsIgnoreCase("not"))
				comparison += array[i];
			else{
				if(!array[i].equalsIgnoreCase("not") && !checkComparison(comparison))
					return false;
				comparison = "";
			}
		}
		if(comparison.equals(""))
			return false;
		if(!checkComparison(comparison))
			return false;
		return checkBrackets(where);
	}
	
	private boolean checkBrackets(String where){
		Stack<Character> stack = new Stack<Character>();
		where = where.toLowerCase().replace(" ", "");
		if(where.contains("(and") || where.contains("(or") || where.contains("and)") || where.contains("or)") || where.contains("()") || where.contains(")not") || where.contains("not;"))
			return false;
		for(int i=0; i<where.length(); i++){
			if(where.charAt(i) == '(')
				stack.push('(');
			if(where.charAt(i) == ')' && stack.isEmpty())
				return false;
			if(where.charAt(i) == ')')
				stack.pop();
		}
		if(!stack.isEmpty())
			return false;
		return true;
	}
	
	private Stack<Object> getPostfix(String where){
		Stack<Object> postfix = new Stack<Object>();
		Stack<String> post = new Stack<String>();
		String [] array = where.replace(";", "").replace("(", " ( ").replace(")", " ) ").split(" ");
		String s = "";
		for(int i=0; i<array.length; i++){
			if(array[i].equalsIgnoreCase("not"))
				post.push("not");
			if(array[i].equalsIgnoreCase("("))
				post.push("(");
			if(array[i].equalsIgnoreCase(")")){
				if(!s.equals("")){
				    postfix.push(whereOperations(s));
				    while(!post.isEmpty() && post.peek().equals("not"))
				    	postfix.push(post.pop());
				}
				while(post.peek() != "(")
					postfix.push(post.pop());
				post.pop();
				while(!post.isEmpty() && post.peek().equals("not"))
			    	postfix.push(post.pop());
				s = "";
			}
			if(array[i].equalsIgnoreCase("and")){
				if(!s.equals("")){
				    postfix.push(whereOperations(s));
				    while(!post.isEmpty() && post.peek().equals("not"))
				    	postfix.push(post.pop());
				}
				if(post.isEmpty() || post.peek().equalsIgnoreCase("or") || post.peek().equalsIgnoreCase("(") || post.peek().equalsIgnoreCase("not"))
					post.push("and");
				else
					postfix.push("and");
				s = "";
			}
			if(array[i].equalsIgnoreCase("or")){
				if(!s.equals("")){
				    postfix.push(whereOperations(s));
				    while(!post.isEmpty() && post.peek().equals("not"))
				    	postfix.push(post.pop());
				}
				if(!post.isEmpty() && post.peek().equalsIgnoreCase("and"))
					postfix.push(post.pop());
				if(!post.isEmpty() && post.peek().equalsIgnoreCase("or"))
					postfix.push("or");
				if(post.isEmpty() || post.peek().equalsIgnoreCase("(") || post.peek().equalsIgnoreCase("not"))
				    post.push("or");
				s = "";
			}
			if(!array[i].equalsIgnoreCase("and") && !array[i].equalsIgnoreCase("or") && !array[i].equalsIgnoreCase(")") && !array[i].equalsIgnoreCase("(") && !array[i].equalsIgnoreCase("not"))
				s += array[i];
		}
		if(!s.equals(""))
		    postfix.push(whereOperations(s));
		while(!post.isEmpty())
			postfix.push(post.pop());
		return postfix;
	}

	private String select(String statement) {
		if (checkTime()){
			return "Operation Aborted";
		}
		ArrayList<String> seperatedStatement = getSeperatedStatementOfSelect(statement);
		seperatedStatement.set(0, seperatedStatement.get(0).replace(" ", ""));
		ArrayList<String> columns = getColumns(seperatedStatement.get(0));
		if (columns.get(0).equals("*"))
			columns = null;

		if (seperatedStatement.size() == 3) {
			//ArrayList<ArrayList<ComparisonStatement>> operation = new ArrayList<ArrayList<ComparisonStatement>>();
			//operation.add(new ArrayList<ComparisonStatement>());
			//operation.get(0).add(whereOperations(seperatedStatement.get(2)));
			for(int i=0; i<getPostfix(seperatedStatement.get(2)).size(); i++)
				System.out.println(getPostfix(seperatedStatement.get(2)).get(i));
			try {
				//table = f.selectTable(seperatedStatement.get(1)
					//	.replace(" ", ""), columns, operation);
				table = f.selectTable(seperatedStatement.get(1)
					.replace(" ", ""), columns, getPostfix(seperatedStatement.get(2)));
			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		} else {
			try {
				table = f.selectTable(seperatedStatement.get(1)
						.replace(" ", ""), columns, null);
			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		}
		if (table == null){
			execute = false;
			return "Table Not Exist !";
		}
		else
			return table.toString();

	}
	
	public Table getTable(){
		return table;
	}

	private String delete(String statement) {
		if (checkTime()){
			return "Operation Aborted";
		}
		ArrayList<String> seperatedStatement = getSeperatedStatementOfDelete(statement);
		if (seperatedStatement.size() == 2) {
			//ArrayList<ArrayList<ComparisonStatement>> operation = new ArrayList<ArrayList<ComparisonStatement>>();
			//operation.add(new ArrayList<ComparisonStatement>());
			//operation.get(0).add(whereOperations(seperatedStatement.get(1)));
			try {
				//f.delete(seperatedStatement.get(0).replace(" ", ""), operation);
				f.delete(seperatedStatement.get(0).replace(" ", ""), getPostfix(seperatedStatement.get(1)));
			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		} else {
			try {
				f.delete(seperatedStatement.get(0).replace(" ", ""));
			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		}
		return "Data Deleted Successfully";
	}

	private String update(String statement) {
		if (checkTime()){
			return "Operation Aborted";
		}
		ArrayList<String> seperatedStatement = getSeperatedStatementOfUpdate(statement);
		ArrayList<ArrayList<String>> newValues = getNewValues(seperatedStatement
				.get(1));
		if (seperatedStatement.size() == 3) {
			//ArrayList<ArrayList<ComparisonStatement>> operation = new ArrayList<ArrayList<ComparisonStatement>>();
			//operation.add(new ArrayList<ComparisonStatement>());
			//operation.get(0).add(whereOperations(seperatedStatement.get(2)));
			try {
				//f.update(seperatedStatement.get(0).replace(" ", ""),
						//newValues.get(0), newValues.get(1), operation);
				f.update(seperatedStatement.get(0).replace(" ", ""),
						newValues.get(0), newValues.get(1), getPostfix(seperatedStatement.get(2)));

			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		} else {
			try {
				f.update(seperatedStatement.get(0).replace(" ", ""),
						newValues.get(0), newValues.get(1));
			} catch (Exception e) {
				execute = false;
				return e.getMessage();
			}
		}
		return "Table Updated Successfully";
	}

	private ArrayList<String> getSeperatedStatementOfUpdate(String statement) {
		String newStatement = statement.replace("update", "");
		String[] seperation1 = newStatement.split("set");
		ArrayList<String> seperatedStatement = new ArrayList<String>();
		seperatedStatement.add(seperation1[0].replace(" ", ""));
		if (seperation1[1].contains("where")) {
			String[] seperation2 = seperation1[1].split("where");
			seperatedStatement.add(seperation2[0]);
			seperation2[1] = seperation2[1].replace(";", "");
			seperatedStatement.add(seperation2[1]);
		} else {
			seperation1[1] = seperation1[1].replace(";", "");
			seperatedStatement.add(seperation1[1]);
		}
		return seperatedStatement;
	}

	private ArrayList<ArrayList<String>> getNewValues(String statement) {
		String[] seperate = statement.split(",");
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 0; i < seperate.length; i++) {
			String[] operation = seperate[i].split("=");
			columns.add(operation[0].replace(" ", ""));
			int m = 0, n = operation[1].length() - 1;
			while (operation[1].charAt(m) != '\''
					|| operation[1].charAt(n) != '\'') {
				if (operation[1].charAt(m) != '\'')
					m++;
				if (operation[1].charAt(n) != '\'')
					n--;
			}
			values.add(operation[1].substring(m + 1, n));
		}
		ArrayList<ArrayList<String>> value = new ArrayList<ArrayList<String>>();
		value.add(columns);
		value.add(values);
		return value;
	}

	private ArrayList<String> getSeperatedStatementOfDelete(String statement) {
		String[] seperation1 = statement.split("from");
		ArrayList<String> seperatedStatement = new ArrayList<String>();
		if (seperation1[1].contains("where")) {
			String[] seperation2 = seperation1[1].split("where");
			seperatedStatement.add(seperation2[0].replace(" ", ""));
			seperation2[1] = seperation2[1].replace(";", "");
			seperatedStatement.add(seperation2[1]);
		} else {
			seperation1[1] = seperation1[1].replace(";", "");
			seperatedStatement.add(seperation1[1].replace(" ", ""));
		}
		return seperatedStatement;
	}

	private ArrayList<String> getSeperatedStatementOfSelect(String statement) {
		String newStatement = statement.replace("select", "");
		String[] seperation1 = newStatement.split("from");
		ArrayList<String> seperatedStatement = new ArrayList<String>();
		seperatedStatement.add(seperation1[0]);
		if (seperation1[1].contains("where")) {
			String[] seperation2 = seperation1[1].split("where");
			seperatedStatement.add(seperation2[0]);
			seperation2[1] = seperation2[1].replace(";", "");
			seperatedStatement.add(seperation2[1]);
		} else {
			seperation1[1] = seperation1[1].replace(";", "");
			seperatedStatement.add(seperation1[1]);
		}
		return seperatedStatement;
	}

	private ArrayList<String> getColumns(String columns) {
		String[] c = columns.split(",");
		ArrayList<String> C = new ArrayList<String>();
		for (int i = 0; i < c.length; i++)
			C.add(c[i]);
		return C;
	}

	private ComparisonStatement whereOperations(String where) {
		String colName = "", value = "", operation = "";
		int i;
		for (i = 0; i < where.length(); i++) {
			if (where.charAt(i) != '=' && where.charAt(i) != '<'
					&& where.charAt(i) != '>' && where.charAt(i) != ' ')
				colName += where.charAt(i);
			else {
				if (where.charAt(i) != ' ')
					break;
			}
		}
		operation = where.charAt(i) + "";
		i++;
		int j = where.length() - 1;
		if(where.charAt(i) == '=' || where.charAt(i) == '<' || where.charAt(i) == '>'){
			operation += where.charAt(i);
			i++;
		}
		while (where.charAt(i) != '\'' || where.charAt(j) != '\'') {
			if (where.charAt(i) != '\'')
				i++;
			if (where.charAt(j) != '\'')
				j--;
		}
		value = where.substring(i + 1, j);

		return new ComparisonStatement(colName, value, operation);
	}

	@Override
	public String parse(String statement) {
		if (checkTime()){
			return "Operation Aborted";
		}
		table = null;
		execute = true;
		statement = toLowerCase(statement);
		if (statement.startsWith("create database ")
				|| statement.startsWith(" create database "))
			return createDataBase(statement);
		if (statement.startsWith("create table ")
				|| statement.startsWith(" create table "))
			return createTable(statement);
		if (statement.startsWith("insert into ")
				|| statement.startsWith(" insert into "))
			return insertInto(statement);
		if (statement.startsWith("use ") || statement.startsWith(" use "))
			return setDataBase(statement);
		if ((statement.startsWith("select ") || statement
				.startsWith(" select ")) && validateSelect(statement))
			return select(statement);
		if ((statement.startsWith("delete ") || statement
				.startsWith(" delete ")) && validateDelete(statement))
			return delete(statement);
		if ((statement.startsWith("update ") || statement
				.startsWith(" update ")) && validateUpdate(statement))
			return update(statement);
		execute = false;
		LogClass.log().error("Syntax Error !");
		return "Syntax Error !";
	}
	
	private boolean checkTime (){
		long e = System.nanoTime();
		int endTime = (int)(e*Math.pow(10, -9));
		Statement.time.setEndTime(endTime);
		return Statement.time.getTimeOut();
	}
}
