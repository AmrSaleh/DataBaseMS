package Interface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Logging.LogClass;
import Parser.Parser;
import Parser.ParserInterface;

public class Interface {
	static Connect parser = new Connect();
	Interface() throws SQLException
	{
		run();
		LogClass.log().info("Program ended");
	}
	public static void main(String[] args) throws SQLException
	{
		new Interface();
	}
	public void run() throws SQLException
	{
		LogClass.log().info("Program Started");
		String input="";  //contains user input
		System.out.println(surround("Welcome to the database management system!"));
		while (true) //the main infinite loop. once you get out of it, the program is closed
		{
			System.out.println(surround("Please type in SQL commands corresponding to the desired actions.\n" +
					"You can view the supported SQL statments by typing \"View\"\n"
			+"Type \"Exit\" to exit the program."));
			Scanner scan = new Scanner(System.in);
			input=scan.nextLine(); //input has the command
			if(input.toLowerCase().equals("exit")) //exit the main infinite loop
			{
				break;
			}
			System.out.println(process(input)); //else, identify the command
		}
		//exiting program
		System.out.println(surround("Thanks for using the system. Good luck."));
	}
	public static String process(String input) throws SQLException //check whether the command is a SQL statement
	{
		
		if(input.toLowerCase().equals("view"))
		{
			viewSQLCommands(); //views all available supported statements
			return "";
		}
		else
		{
			return(parser.connect(input));   //process the statement and print the result
		}
	}
	public static void viewSQLCommands()
	 {
	  int i = 1;
	  System.out.println(surround("Create database:\n" +
	    "\"Create DataBase db_name;\""));
	  System.out.println(surround("Set currnetly active database:\n" +
	    "\"Use db_name;\""));
	  System.out.println(surround("Create table:\n" +
	    "\"Create Table table_name {Column_name1 data_Type1,Column_name2 data_Type2,..........};\""));
	  System.out.println(surround("Insert into:\n" +
	    "\"Insert Into table_name Values ('value1', 'value2',......);\"\nOR\n"+
	   "\"Insert Into table_name (column_name1,column_name2,......) Values ('value1', 'value2',......);\""));
	  System.out.println(surround("Select:\n" +
	    "\"select colname1, colname2,... from tablename;\"\nOR\n"+
	    "\"select colname1, colname2,... from tablename where colname (=/</>) 'value';\""));
	  System.out.println(surround("Delete:\n" +
	    "\"delete from tablename;\"\nOR\n"+
	    "\"delete from tablename where colname (=/</>) \'value\';\""));
	  System.out.println(surround("Update:\n" +
	    "\"update tablename set colname1=\'value1\', colname = \'value2\',....;\"\nOR\n"+
	    "\"update tablename set colname1=\'value1\', colname = \'value2\',.... where colname (=/</>) \'value\';\""));
	  System.out.println(surround("Table names and column names must not contain spaces.\n" +
	    "Statements are not case sensetive. The following 2 formats are valid: (SELECT / select).))"));
	  //to be continued
	 }
	public static ArrayList <String> getLongestLine(String s)
	{
		ArrayList <String> longestLine = new ArrayList<String>();
		String temp = "";
		int count = 0;
		for(int i = 0; i<s.length(); i++)
		{
			count++;
			if ((s.charAt(i)=='\n')||(s.charAt(i)==' '&&count>70))
			{
				count = 0;
				longestLine.add(temp);
				temp="";
			}
			else
			{
				temp=temp+s.charAt(i);
			}
		}
		longestLine.add(temp);
		return longestLine;
	}
	public static int getBiggestRow(ArrayList<String> longestLine)
	{
		int maxRow = 0;
		for(int i = 0; i<longestLine.size(); i++)
		{
			if (maxRow<longestLine.get(i).length())
			{
				maxRow=longestLine.get(i).length();
			}
		}
		return maxRow;
	}
	public static String adjustCorner(String s, int maxRow, String corner, String top)
	{
		for(int i = 0; i<maxRow+2; i++)
		{
			if (i==0||i==maxRow+1)
			{
				s=s+corner;
			}
			else
			{
				s=s+top;
			}
		}
		s=s+"\n";
		return s;
	}
	public static String adjustBody(String s, int maxRow, String side, ArrayList<String> longestLine)
	{
		for(int i = 0 ; i<longestLine.size(); i++)
		{
			s=s+side;
			for(int j=0; j<maxRow; j++)
			{
				if (j<longestLine.get(i).length())
				{
					s=s+longestLine.get(i).charAt(j);
				}
				else
				{
					s=s+" ";
				}
			}
			s=s+side+"\n";
		}
		return s;
	}
	public static String surround(String s)
	{
		String top="-";
		String corner = "+";
		String side = "|";
		ArrayList <String> longestLine;
		int maxRow=0;
		longestLine=getLongestLine(s);
		s="";
		maxRow=getBiggestRow(longestLine);
		s=adjustCorner(s,maxRow, corner, top);
		s=adjustBody(s, maxRow, side, longestLine);
		s=adjustCorner(s,maxRow, corner, top);
		return s;
	}

}
