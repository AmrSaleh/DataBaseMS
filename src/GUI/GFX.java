package GUI;

import java.awt.AWTException;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.List;
import java.awt.Robot;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JRadioButtonMenuItem;

import DBMS.Column;
import DBMS.DBMS;
import DBMS.MyDBMS;
import DBMS.Table;
import DBMS.XML;
import DBMS.XmlInterface;
import Interface.Interface;
import Parser.Parser;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;

public class GFX {

	private JFrame frame;
	private static JTextField sqlCommandField;
	//private JTextField sqlCommandField_1;
	//private JTextField sqlCommandField_2;
	private JButton btnCalculate//, 
	//btnSaveTruthtable, 
	//btnCompare, 
	//btnEvaluateForA, 
	//btnEvaluate,
			//buttonSimplify
			;
	private JTextArea answer;
	private JTextArea tableInput;
	private static JComboBox<String> databaseList;
	private static JComboBox<String> tableList;
	private static JComboBox<String> columnList;
	private static JComboBox<String> valueList;
	static DefaultComboBoxModel<String> databaseModel;
	static DefaultComboBoxModel<String> tableModel;
	static DefaultComboBoxModel<String> columnModel;
	static DefaultComboBoxModel<String> valueModel;
	static DefaultComboBoxModel<String> datatypeModel;
	static DefaultComboBoxModel<String> propertyModel;
	private static String databaseName = "%databaseName%";
	private static String tableName = "%tableName%";
	private static JComboBox<String> datatypeList;
	private static JComboBox<String> propertyList;
	//private JTextField lblExpressionTypeWill;
	//private JTextField ComparisonResultWill;
	//private Interface sqlCommand;
	//private JTextField sqlCommandField_3;
	//private JRadioButtonMenuItem[] variables;

	/**
	 * Launch the application.
	 */
	private void drawTable()
	{
		XML readDatabase = new XML("C:\\DBMS", databaseName);
		try {
			String table = (readDatabase.loadTable(tableName)).toString();
			tableInput.setText(table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	private void changeDatabaseName()
	{
		String[] command = Parser.trimAndRemoveInterspaces(sqlCommandField.getText()).split(" ");
		if(command[command.length-1].contains(";"))
		{
			command = Parser.trimAndRemoveInterspaces(sqlCommandField.getText()).substring(0,Parser.trimAndRemoveInterspaces(sqlCommandField.getText()).length()-1).split(" ");
		}
		if(command.length==2&&command[0].equalsIgnoreCase("Use"))
		{
			databaseName = command[1];
		}
//		else
//		{
//			databaseName = "%databaseName%";
//		}
	}
	private void changeTableName()
	{
		String[] command = Parser.trimAndRemoveInterspaces(sqlCommandField.getText()).split(" ");
		if(command[command.length-1].equalsIgnoreCase(";"))
		{
			command = Parser.trimAndRemoveInterspaces(sqlCommandField.getText()).substring(0,Parser.trimAndRemoveInterspaces(sqlCommandField.getText()).length()-1).split(" ");
		}
		if(command.length>2&&(command[0].equalsIgnoreCase("create")
				&&command[1].equalsIgnoreCase("table"))||(command.length>2&&command[0].equalsIgnoreCase("Insert")
						&&command[1].equalsIgnoreCase("Into"))||(command.length>2&&command[0].equalsIgnoreCase("delete")
								&&command[1].equalsIgnoreCase("from")))
		{
			tableName = command[2];
		}
		else if(command.length>3&&(command[0].equalsIgnoreCase("select")))
		{
			for(int i = 0; i < command.length; i++)
			{
				if(command[i].equalsIgnoreCase("from")&&command.length>i+1)
				{
					tableName = command[i+1];
				}
			}
		}
		else if(command.length>2&&command[0].equalsIgnoreCase("update"))
		{
			tableName = command[1];
		}
		else
		{
			tableName="%tableName%";
		}
		//System.out.println(tableName);
	}
	private static String[] loadCommands()
	{
		String filePath = "C:\\DBMS\\commands.txt";
		String[] commandMenu = null;
		ArrayList<String> strings = new ArrayList<String>();
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(filePath));
			
			
			  String line = null;
			  try {
				while (( line = input.readLine()) != null){
				    strings.add(line);
				  }
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//return commandMenu;
			}
		}
		
		catch (FileNotFoundException e) {
		    return commandMenu;
		}
		//System.out.println("No erros found!");
		commandMenu = strings.toArray(new String[]{});
		return commandMenu;
	}
	private static int findIndexOfTable(String[] command)
	{
		for(int i = 0; i < command.length; i++ )
		{
			if(i>0&&command[i].equalsIgnoreCase(tableName)&&
					(command[i-1].equalsIgnoreCase("from")||
							command[i-1].equalsIgnoreCase("update")||
							command[i-1].equalsIgnoreCase("into")))
			{
				return i;
			}
		}
		return -1;
	}
	private static void activateDeadLists()
	{
		//System.out.println("HI");
		String command = Parser.trimAndRemoveInterspaces(sqlCommandField.getText());
		if(command.length()<2||command.charAt(command.length()-1)!=';')
		{
			deactivateDatabaseList();
			deactivateTableList();
			deactivateColumnList();
			return;
		}
		command=Parser.trimAndRemoveInterspaces(command.substring(0, command.length()-1));
		String[]databaseAndTableDetect = command.split(" ");
		int tableIndex = findIndexOfTable(databaseAndTableDetect);
		if(databaseAndTableDetect.length<2)
		{
			deactivateDatabaseList();
			deactivateTableList();
			deactivateColumnList();
			return;
		}
		//String[]tableDetect = command.split(" ");
		//String[]columnDetect = command.split(" ");
		if(databaseAndTableDetect.length==2&&command.toLowerCase().startsWith("use ")&&
				databaseAndTableDetect[1].equalsIgnoreCase(databaseName))
		{
			//System.out.println(databaseAndTableDetect[1]);
			activateDatabaseList();
		}
		else
		{
			deactivateDatabaseList();
		}
		if((command.toLowerCase().startsWith("insert ")||
				command.toLowerCase().startsWith("select ")||
						
				command.toLowerCase().startsWith("delete ")||
				command.toLowerCase().startsWith("update "))&&
				tableIndex!=(-1))
		{
			activateTableList(tableIndex);
		}
		else
		{
			deactivateTableList();
		}
		if(command.toLowerCase().startsWith("create table "))
		{
			activateDatatypeList();
			activatePropertyList();
		}
		else
		{
			deactivateDatatypeList();
			deactivatePropertyList();
		}
		if((!command.toLowerCase().startsWith("create ")&&!command.toLowerCase().startsWith("use ")&&!command.toLowerCase().startsWith("select * "))//&&command.toLowerCase().contains("%columnname%")
				)
		{
			activateColumnList();
		}
		else
		{
			deactivateColumnList();
		}
		if((command.toLowerCase().startsWith("select ")||						
				command.toLowerCase().startsWith("delete ")||
				command.toLowerCase().startsWith("update "))&&
				tableIndex!=(-1)&&
				command.toLowerCase().contains("%rowdata%"))
		{
			activateValueList();
		}
		else
		{
			deactivateValueList();
		}
	}
	private static void activateDatabaseList()
	{
		databaseList.setEnabled(true);
		MyDBMS origin = new MyDBMS();
		XML readDatabase = new XML(origin.getPath()+"\\DatabasesSchema.xml", origin.getDatabase());
		
		try {
			String[] columns = readDatabase.loadStrings(readDatabase.getPath()).toArray(new String[]{});
			ArrayList<String> database = new ArrayList<String>();
			database.add("Select database");
			for(int i = 0; i < columns.length; i++)
			{
				database.add(columns[i]);
			}
			databaseModel = new DefaultComboBoxModel<String>(database.toArray(new String[]{}));
			databaseList.setModel(databaseModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	private static void activateTableList(int index)
	{
		MyDBMS origin = new MyDBMS();
		tableList.setEnabled(true);
		XML readDatabase = new XML(origin.getPath()+"\\"+databaseName+"\\Schema.xml", databaseName);
		//System.out.println(databaseName);
		try {
			ArrayList<String> table = new ArrayList<String>();
			table.add("Select table");
			ArrayList<String> temp = readDatabase.loadStrings(readDatabase.getPath());
			for(int i = 0; i < temp.size(); i++)
			{
				table.add(temp.get(i));
			}
			tableModel = new DefaultComboBoxModel<String>(table.toArray(new String[]{}));
			tableList.setModel(tableModel);
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	private static void activateColumnList()
	{
		MyDBMS origin = new MyDBMS();
		//System.out.println(tableName);
		columnList.setEnabled(true);
		XML readDatabase = new XML(origin.getPath()+"\\"+databaseName+"\\"+tableName+"Schema.xml", databaseName);
		try {
			//System.out.println(readDatabase.getPath());
			ArrayList<Column> cols = readDatabase.loadCols(readDatabase.getPath());
			ArrayList<String> colNames = new ArrayList<String>();
			colNames.add("Select columm");
			for(int i = 0; i < cols.size(); i++)
			{
				colNames.add(cols.get(i).getColName());
			}
			columnModel = new DefaultComboBoxModel<String>(colNames.toArray(new String[]{}));
//			for(int i = 0; i < columnModel.getSize(); i++)
//			{
//				System.out.println(columnModel.getElementAt(i));
//			}
			columnList.setModel(columnModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

	}
	private static void activateValueList()
	{
		MyDBMS origin = new MyDBMS();
		//System.out.println(tableName);
		valueList.setEnabled(true);
		XML readDatabase = new XML(origin.getPath()+"\\"+databaseName+"\\"+tableName+".xml", databaseName);
		try {
		//	System.out.println(readDatabase.getPath());
			ArrayList<ArrayList<String>> values = readDatabase.loadRows(readDatabase.getPath());
			ArrayList<String> colValues = new ArrayList<String>();
			colValues.add("Select value");
			for(int i = 0; i < values.size(); i++)
			{
				//System.out.println(i);
				for(int j = 0; j < values.get(i).size(); j++)
				{
					//System.out.println(j);
					colValues.add(values.get(i).get(j));
				}
			}
			valueModel = new DefaultComboBoxModel<String>(colValues.toArray(new String[]{}));
//			for(int i = 0; i < columnModel.getSize(); i++)
//			{
//				System.out.println(columnModel.getElementAt(i));
//			}
			valueList.setModel(valueModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

	}
	private static void activateDatatypeList()
	{
		datatypeList.setEnabled(true);
	}
	private static void activatePropertyList()
	{
		propertyList.setEnabled(true);
	}
	private static void deactivateDatabaseList()
	{
		databaseList.setEnabled(false);
	}
	private static void deactivateTableList()
	{
		tableList.setEnabled(false);
	}
	private static void deactivateColumnList()
	{
		columnList.setEnabled(false);
	}
	private static void deactivateValueList()
	{
		valueList.setEnabled(false);
	}
	private static void deactivateDatatypeList()
	{
		datatypeList.setEnabled(false);
	}
	private static void deactivatePropertyList()
	{
		propertyList.setEnabled(false);
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GFX window = new GFX();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private void setDefaultModels()
	{
		String[] databaseModelDefault={"Select database"};
		String[] tableModelDefault={"Select table"};
		String[] columnModelDefault={"Select column"};
		String[] valueModelDefault={"Select value"};
		String[] datatypeModelDefault = {"Select datatype", 
							"Boolean", "Date", "String", "Int", "Long", "Float", "Double"};
		String[] propertyModelDefault = {"Select property", 
				"PrimaryKey", "AutoIncrement", "Nullable", "NotNullable", "Searchable", "ReadOnly"};
		databaseModel = new DefaultComboBoxModel<String>(databaseModelDefault);
		tableModel = new DefaultComboBoxModel<String>(tableModelDefault);
		columnModel = new DefaultComboBoxModel<String>(columnModelDefault);
		valueModel = new DefaultComboBoxModel<String>(valueModelDefault);
		datatypeModel = new DefaultComboBoxModel<String>(datatypeModelDefault);
		propertyModel = new DefaultComboBoxModel<String>(propertyModelDefault);
	}
	public GFX() {
		//Writer writer = null;
		setDefaultModels();
		
		
		
//		try {
//		    writer = new BufferedWriter(new OutputStreamWriter(
//		          new FileOutputStream("c:\\DBMS\\commands.txt"), "utf-8"));
//		    writer.write(txtFile);
//		} catch (IOException ex) {
//		  //ex.printStackTrace();
//		} finally {
//		   try {writer.close();} catch (Exception ex) {}
//		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 699, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("SQLCommander");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		JLabel lblEnterYourExpression = new JLabel("Enter your SQL command");

		sqlCommandField = new JTextField();
			
		sqlCommandField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				changeTableName();
				changeDatabaseName();
				activateDeadLists();
				drawTable();
				System.out.println(tableName);
				System.out.println(databaseName);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				changeTableName();
				changeDatabaseName();
				activateDeadLists();
				drawTable();
				System.out.println(tableName);
				System.out.println(databaseName);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 10){
					btnCalculate.doClick();
				}
			}
		});
		
		sqlCommandField.setToolTipText("separate every thing with spaces");
		sqlCommandField.setColumns(10);

		//lblExpressionTypeWill = new JTextField("Expression type will be shown after you click calculate");
		//lblExpressionTypeWill.setEnabled(false);

		final JPopupMenu popupMenu = new JPopupMenu();

		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(sqlCommandField.getText().contains("%"))
				{
					answer.setText("The character '%' is not allowed as a part of any name, value or command");
					return;
				}
				try {
					answer.setText(Interface.process(sqlCommandField.getText()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(Parser.trimAndRemoveInterspaces(sqlCommandField.getText().toLowerCase()).startsWith("use")&&
						answer.getText().startsWith("Done."))
				{
					String[] splitter = (Parser.trimAndRemoveInterspaces(sqlCommandField.getText().toLowerCase())).split(" ");
					databaseName=splitter[1].substring(0, splitter[1].length()-1);
					btnCalculate.setFocusable(false);
					try { 
						
						sqlCommandField.requestFocusInWindow();
					    Robot robot = new Robot(); 
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE); 
					    btnCalculate.setFocusable(true);
					} catch (Exception e) { 
						//e.printStackTrace();
					} 
				}
			}
		});
		btnCalculate.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				try { 
					
					sqlCommandField.requestFocusInWindow();
				    Robot robot = new Robot(); 
				    robot.keyPress(KeyEvent.VK_SPACE);
				    robot.keyPress(KeyEvent.VK_BACK_SPACE); 
				    btnCalculate.setFocusable(true);
				} catch (Exception e) { 
					//e.printStackTrace();
				} 
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		final JComboBox<String> commandListBox = new JComboBox<String>();
		String[] commands = {"Choose the SQL command\n",
			    "Create database;\n"
			    ,"Use database;\n"
			    ,"create table;\n"
			    ,"Insert Into all columns;\n"
			    ,"Insert Into some columns;\n"
			    ,"Select * from table;\n"
			    ,"Select some columns;\n"
			    ,"Select some columns where;\n"
			    ,"Delete all table;\n"
			    ,"Delete from table where;\n"
			    ,"Update column;\n"
			    ,"Update column where;"};
				commandListBox.setModel(new DefaultComboBoxModel<String>(commands));
//		comboBoxTraceModeSelection = new JComboBox<TraceMode>();
//		   comboBoxTraceModeSelection.setModel(new DefaultComboBoxModel<TraceMode>
//		(TraceMode.values()));
		commandListBox.setToolTipText("Select the approperiate command");
		commandListBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(commandListBox.getSelectedIndex()>0)
				{
					String[] commands = {"Choose the SQL command\n",
							"Create database ;\n"
							,"Use %databaseName% ;\n"
							,"create table %tableName% {columnName %columnDatatype% %columnProperty%, columnName %columnDatatype% %columnProperty%};\n"
							,"Insert Into %tableName% Values ('rowData', 'rowData');\n"
							,"Insert Into %tableName% () Values ('rowData', 'rowData');\n"
							,"Select * from %tableName% ;\n"
							,"Select from %tableName% ;\n"
							,"Select from %tableName% where %columnName% %=% '%rowData%';\n"
							,"Delete from %tableName% ;\n"
							,"Delete from %tableName% where %columnName% %=% '%rowData%';\n"
							,"Update %tableName% set ;\n"
							,"Update %tableName% set where %columnName% %=% '%rowData%';"};
					// TODO Auto-generated method stub
					sqlCommandField.setText(commands[(commandListBox.getSelectedIndex())]);
					sqlCommandField.requestFocusInWindow();
	
					try { 
					    Robot robot = new Robot(); 
	
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE);
					} catch (AWTException e) { 
					} 
				}
			}
		});
		
		databaseList = new JComboBox<String>(databaseModel);
		databaseList.setToolTipText("Select the approperiate database");
		databaseList.setEnabled(false);
		databaseList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(databaseList.getSelectedIndex()>0)
				{
					
					// TODO Auto-generated method stub
					sqlCommandField.setText(sqlCommandField.getText().replaceFirst(databaseName,String.valueOf(databaseList.getSelectedItem())));
					//sqlCommandField.setText(String.valueOf(databaseList.getSelectedItem()));
					sqlCommandField.requestFocusInWindow();
	
					try { 
					    Robot robot = new Robot(); 
	
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE);
					} catch (AWTException e) { 
					} 
				}
			}
		});
		tableList = new JComboBox<String>(tableModel);
		tableList.setToolTipText("Select the approperiate table");
		tableList.setEnabled(false);
		tableList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tableList.getSelectedIndex()>0)
				{
					// TODO Auto-generated method stub
					sqlCommandField.setText(sqlCommandField.getText().replaceFirst(tableName,String.valueOf(tableList.getSelectedItem())));
					//sqlCommandField.setText(String.valueOf(databaseList.getSelectedItem()));
					sqlCommandField.requestFocusInWindow();
	
					try { 
					    Robot robot = new Robot(); 
	
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE);
					} catch (AWTException e) { 
					} 
				}
			}
		});
		
		columnList = new JComboBox<String>(columnModel);
		columnList.setToolTipText("Select the approperiate column");
		columnList.setEnabled(false);
		columnList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(columnList.getSelectedIndex()>0)
				{
					// TODO Auto-generated method stub
					//insert write column
					if(sqlCommandField.getText().toLowerCase().startsWith("insert ")&&sqlCommandField.getText().contains(")")
							&&sqlCommandField.getText().indexOf(")")<sqlCommandField.getText().indexOf("Values")&&
							(!sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))
							||sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))>sqlCommandField.getText().indexOf(")")))
					{
						System.out.println("insert write column");
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("\\)",", "+String.valueOf(columnList.getSelectedItem())+")"));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("\\(, ","("));
					}
					//insert remove column
					else if (sqlCommandField.getText().toLowerCase().startsWith("insert ")&&sqlCommandField.getText().contains(")")
							&&sqlCommandField.getText().indexOf(")")<sqlCommandField.getText().indexOf("Values")&&
							sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))&&
							sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))<sqlCommandField.getText().indexOf(")")
							)
					{
						System.out.println("insert delete column");
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(", "+String.valueOf(columnList.getSelectedItem()),""));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(String.valueOf(columnList.getSelectedItem()),""));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("\\(, ","\\("));

					}
					//select write column
					else if (sqlCommandField.getText().toLowerCase().startsWith("select ")&&
							(!sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))
							||sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))>sqlCommandField.getText().indexOf("from"))	
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(" from",", "+String.valueOf(columnList.getSelectedItem())+" from"));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("Select,","Select"));
					}
					//select remove column
					else if (sqlCommandField.getText().toLowerCase().startsWith("select ")&&
							sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))&&
							sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))<sqlCommandField.getText().indexOf("from")
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(", "+String.valueOf(columnList.getSelectedItem()),""));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(" "+String.valueOf(columnList.getSelectedItem()),""));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("Select,","Select"));

					}
					//delete write column
					else if (sqlCommandField.getText().toLowerCase().startsWith("delete ")&&
							(!sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))
							||sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))>sqlCommandField.getText().indexOf("from"))	
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(" from",", "+String.valueOf(columnList.getSelectedItem())+" from"));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("Select,","Select"));
					}
					//delete remove column
					else if (sqlCommandField.getText().toLowerCase().startsWith("delete ")&&
							sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))&&
							sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))<sqlCommandField.getText().indexOf("from")
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(", "+String.valueOf(columnList.getSelectedItem()),""));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(" "+String.valueOf(columnList.getSelectedItem()),""));
					}
					//updateWithWhere write column
					else if (sqlCommandField.getText().toLowerCase().startsWith("update ")&&sqlCommandField.getText().toLowerCase().contains(" where ")&&
							(!sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))
							||sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))>sqlCommandField.getText().indexOf(" where "))	
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(" where",", "+String.valueOf(columnList.getSelectedItem())+" = '$rowData$' "));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("set,","set"));
					}
					//updateWithWhere remove column
					else if (sqlCommandField.getText().toLowerCase().startsWith("update ")&&sqlCommandField.getText().toLowerCase().contains(" where ")&&
							sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem())+" = '$rowData$'")&&
							//sqlCommandField.getText().toLowerCase().contains(String.valueOf(columnList.getSelectedItem()))&&
							sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))<sqlCommandField.getText().indexOf(" where ")
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(", "+String.valueOf(columnList.getSelectedItem())+" = '$rowData$'",""));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(" "+String.valueOf(columnList.getSelectedItem())+" = '$rowData$'",""));
					}
					//updateWithoutWhere write column
					else if (sqlCommandField.getText().toLowerCase().startsWith("update ")&&
							(!sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem()))
							||sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))>sqlCommandField.getText().indexOf(";"))	
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(";",", "+String.valueOf(columnList.getSelectedItem())+" = '$rowData$'"));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst("set,","set"));
					}
					//updateWithoutWhere remove column
					else if (sqlCommandField.getText().toLowerCase().startsWith("update ")&&
							sqlCommandField.getText().contains(String.valueOf(columnList.getSelectedItem())+" = '$rowData$'")&&
							//sqlCommandField.getText().toLowerCase().contains(String.valueOf(columnList.getSelectedItem()))&&
							sqlCommandField.getText().indexOf(String.valueOf(columnList.getSelectedItem()))<sqlCommandField.getText().indexOf(";")
							)
					{
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(", "+String.valueOf(columnList.getSelectedItem())+" = '$rowData$'",""));
						sqlCommandField.setText(sqlCommandField.getText().replaceFirst(" "+String.valueOf(columnList.getSelectedItem())+" = '$rowData$'",""));
					}
					//sqlCommandField.setText(String.valueOf(databaseList.getSelectedItem()));
					sqlCommandField.requestFocusInWindow();
	
					try { 
					    Robot robot = new Robot(); 
	
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE);
					} catch (AWTException e) { 
					} 
				}
			}
		});
		valueList = new JComboBox<String>(valueModel);
		valueList.setToolTipText("Select the approperiate value");
		valueList.setEnabled(false);
		valueList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(valueList.getSelectedIndex()>0)
				{
					// TODO Auto-generated method stub
					sqlCommandField.setText(sqlCommandField.getText().replaceFirst("%rowData%",String.valueOf(valueList.getSelectedItem())));
					//sqlCommandField.setText(String.valueOf(databaseList.getSelectedItem()));
					sqlCommandField.requestFocusInWindow();
	
					try { 
					    Robot robot = new Robot(); 
	
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE);
					} catch (AWTException e) { 
					} 
				}
			}
		});
		
		datatypeList = new JComboBox<String>(datatypeModel);
		datatypeList.setToolTipText("Select the approperiate datatype");
		datatypeList.setEnabled(false);
		datatypeList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("changed DT");
				// TODO Auto-generated method stub
				if(datatypeList.getSelectedIndex()>0)
				{
					sqlCommandField.setText(sqlCommandField.getText().replaceFirst("%columnDatatype%",String.valueOf(datatypeList.getSelectedItem())));
					//sqlCommandField.setText(String.valueOf(databaseList.getSelectedItem()));
					sqlCommandField.requestFocusInWindow();
	
					try { 
					    Robot robot = new Robot(); 
	
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE);
					} catch (AWTException e) { 
					} 
				}
				
			}
		});
		
		
		propertyList = new JComboBox<String>(propertyModel);
		propertyList.setToolTipText("Select the approperiate property");
		propertyList.setEnabled(false);
		propertyList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(propertyList.getSelectedIndex()>0)
				{
					sqlCommandField.setText(sqlCommandField.getText().replaceFirst("%columnProperty%",String.valueOf(propertyList.getSelectedItem())));
					//sqlCommandField.setText(String.valueOf(databaseList.getSelectedItem()));
					sqlCommandField.requestFocusInWindow();
	
					try { 
					    Robot robot = new Robot(); 
	
					    robot.keyPress(KeyEvent.VK_SPACE);
					    robot.keyPress(KeyEvent.VK_BACK_SPACE);
					} catch (AWTException e) { 
					} 
				}
				
			}
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
						.addComponent(sqlCommandField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(commandListBox, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
							.addComponent(btnCalculate))
						.addComponent(lblEnterYourExpression, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(databaseList, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tableList, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(datatypeList, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(columnList, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(valueList, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
								.addComponent(propertyList, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEnterYourExpression)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sqlCommandField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCalculate)
						.addComponent(commandListBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(databaseList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tableList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(columnList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(valueList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(datatypeList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(propertyList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		//addPopup(btnEvaluateForA, popupMenu);

		// JRadioButtonMenuItem rdbtnmntmA = new
		// JRadioButtonMenuItem("Aywaaaaa");
		// rdbtnmntmA.setName("");
		// popupMenu.add("Select the true variables");

		answer = new JTextArea();
		answer.setFont(new Font("Courier New", Font.PLAIN, 17));
		answer.setEditable(false);
		answer.setBorder(null);
		scrollPane.setViewportView(answer);
		tableInput = new JTextArea();
		tableInput.setFont(new Font("Courier New", Font.PLAIN, 17));
		tableInput.setEditable(false);
		tableInput.setBorder(null);
		scrollPane2.setViewportView(tableInput);
		frame.getContentPane().setLayout(groupLayout);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// if (e.isPopupTrigger()) {
				showMenu(e);
				// }
			}

			public void mouseReleased(MouseEvent e) {
				// if (e.isPopupTrigger()) {
				// showMenu(e);
				// }
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
