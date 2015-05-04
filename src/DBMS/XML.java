package DBMS;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class XML implements XmlInterface {

	private String path;
	private String database;
	public XML(String thePath,String databaseNam) {
		// TODO Auto-generated constructor stub
		path=thePath;
		database=databaseNam;
	}
	
	public  void serializeObjectToXML2(String xmlFileLocation, ArrayList<String> objectToSerialize) throws Exception {
		FileOutputStream os = new FileOutputStream(xmlFileLocation);
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(objectToSerialize);
		encoder.close();
		os.close();
	}

	public  void deleteFile(String xmlFileLocation) throws Exception {
		File f = new File(xmlFileLocation);
		if (f.exists()) {
			FileOutputStream os = new FileOutputStream(xmlFileLocation);
			os.close();
			System.gc();
			Thread.sleep(10);
			if (f.delete()) {
				// System.out.println("Deleted");
			} else {
				throw new IOException("Unable to delete file: " + f.getAbsolutePath());
			}
		} else {
			throw new FileNotFoundException();
		}
	}

	public  void editFile(String xmlFileLocation, String name) throws Exception {
		ArrayList<String> deSerializedObject = loadStrings(xmlFileLocation);
		deSerializedObject.remove(name);
		serializeObjectToXML2(xmlFileLocation, deSerializedObject);
	}

	public  void serializeObjectToXML(String xmlFileLocation, ArrayList<Column> objectToSerialize) throws Exception {
	
		FileOutputStream os = new FileOutputStream(xmlFileLocation);
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(objectToSerialize);
		encoder.close();
		os.close();
	}

	public static  void makeFolder(String folderPath) {
		// Create a directory; all non-existent ancestor directories are
		// automatically created
		Boolean success = (new File(folderPath)).mkdirs();
		if (!success) {
			// Directory creation failed
		}
	}

	public  void makeFile(String xmlFileLocation) throws Exception {
		
		FileOutputStream os = new FileOutputStream(xmlFileLocation);
		os.close();
	}

	public  void appendStringToXML(String xmlFileLocation, String objectToSerialize) throws Exception {
		ArrayList<String> old = loadStrings(xmlFileLocation);
		old.add(objectToSerialize);
		FileOutputStream os = new FileOutputStream(xmlFileLocation);
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(old);
		encoder.close();
		os.close();
	}

	public   ArrayList<String> loadStrings(String xmlFileLocation) throws Exception {
		
		ArrayList<String> deSerializedObject = new ArrayList<String>();
		File f = new File(xmlFileLocation);
		if (f.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(xmlFileLocation));
			if (!(br.read() == -1)) {
				
				FileInputStream os = new FileInputStream(xmlFileLocation);
				XMLDecoder decoder = new XMLDecoder(os);
				deSerializedObject = (ArrayList<String>) decoder.readObject();
				decoder.close();
//				os.close();
			}
//			br.close();
		} else {
			throw new FileNotFoundException();
		}

		return deSerializedObject;
	}
	
	
	public  ArrayList<Column> loadCols(String xmlFileLocation) throws Exception {
		ArrayList<Column> deSerializedObject = new ArrayList<Column>();
		File f = new File(xmlFileLocation);
		if (f.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(xmlFileLocation));
			if (!(br.read() == -1)) {
				FileInputStream os = new FileInputStream(xmlFileLocation);
				XMLDecoder decoder = new XMLDecoder(os);
				deSerializedObject = (ArrayList<Column>) decoder.readObject();
				decoder.close();
				os.close();
			}
			br.close();
		} else {
			throw new FileNotFoundException("cannot find the columns file");
		}

		return deSerializedObject;
	}
	
	

	public  ArrayList<ArrayList<String>> loadRows(String xmlFileLocation) throws Exception {
		FileInputStream os = new FileInputStream(xmlFileLocation);

		XMLDecoder decoder = new XMLDecoder(os);
		ArrayList<ArrayList<String>> deSerializedObject = new ArrayList<ArrayList<String>>();

		BufferedReader br = new BufferedReader(new FileReader(xmlFileLocation));
		if (!(br.read() == -1)) {

			try {
//				System.out.println("there");

				deSerializedObject = (ArrayList<ArrayList<String>>) decoder.readObject();
//				System.out.println("here");

			} catch (Exception e) {
				// TODO: handle exception
//				System.out.println("catched");
//				e.printStackTrace();
				throw new Exception("can not load rows");
			}
		}
//		Thread.sleep(50);
//		decoder.close();
		os.close();
		return deSerializedObject;
	}

	public  void appendRowToXML(String xmlFileLocation, Row objectToSerialize) throws Exception {
		 ArrayList<ArrayList<String>> old = loadRows(xmlFileLocation);
		 old.add(objectToSerialize.getvaluesList());
				
//		System.out.println(old);
		
		 FileOutputStream os = new FileOutputStream(xmlFileLocation);
		 XMLEncoder encoder = new XMLEncoder(os);
		 encoder.writeObject(old);
//		 Thread.sleep(50);
		 encoder.close();
		 os.close();
	}

	public  void saveTable(String tableName, Table myTable) throws Exception {
		ArrayList<Row> old = new ArrayList<Row>();
				if(myTable.getValuesList()!=null)
					old =myTable.getValuesList();
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for(int i=0;i<old.size();i++){
			data.add(old.get(i).getvaluesList());
		}
		
		
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(path + "\\" + database + "\\" + tableName + ".xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(data);
		encoder.close();
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public  Table loadTable(String tableName) throws Exception {
		// hena aho
		ArrayList<Column> deSerializedObject = loadCols(path + "\\" + database + "\\" + tableName + "Schema.xml");
		
		
		ArrayList<ArrayList<String>> temp = loadRows(path + "\\" + database + "\\" + tableName + ".xml");
		ArrayList<Row> rows = new ArrayList<Row>();
		
		for(int i=0;i<temp.size();i++){
			
			rows.add( new Row(temp.get(i)));
		}
		
		return (new Table(tableName, rows, deSerializedObject));
	}

	@Override
	public void createTAble(String tableName, String DataBaseName, ArrayList<Column> columns) throws Exception {

		
			ArrayList<String> temp = null;
			try {
				
				temp = loadStrings(path + "\\" + DataBaseName + "\\Schema.xml");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				
				throw new Exception("Can not load existing tables");
			}
			
			if (temp.contains(tableName)) {
			
				throw new Exception("Duplicate table");

			}

			try{
			appendStringToXML(path + "\\" + DataBaseName + "\\Schema.xml", tableName);
			}catch(Exception e1){
				throw new Exception("Can not append table name to tables");
			}


		try {
			makeFile(path + "\\" + DataBaseName + "\\" + tableName + ".xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			throw new Exception("Can no creat table");
		} // masalan de method be te3mel new file bel esm wel path dah

		try {
			serializeObjectToXML(path + "\\" + DataBaseName + "\\" + tableName + "Schema.xml", columns);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			throw new Exception("Can no creat table");
		}
	}

	@Override
	public void SaveTAble(Table table, String tableName, String DataBaseName) throws Exception {
		// TODO Auto-generated method stub
		saveTable(tableName, table);
	}

	@Override
	public void insertRow(Row r, String TableName, String DataBaseName) throws Exception {
		// TODO Auto-generated method stub
		String fileLoc = path + "\\" + DataBaseName + "\\" + TableName + ".xml";
		appendRowToXML(fileLoc, r);
	}

	@Override
	public void deleteTable(String TableName, String DataBaseName) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> t = loadRows(path + "\\" + DataBaseName + "\\" + TableName + ".xml");
		t.clear();
		FileOutputStream os = new FileOutputStream(path+ "\\" + DataBaseName + "\\" + TableName + ".xml");
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(t);
		encoder.close();
		os.close();
		
		// delete file
		/*try {
			XML.deleteFile(MyDBMS.getPath() + "\\" + DataBaseName + "\\" + TableName + ".xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// delete tabel name from database schema
		try {
			XML.editFile(MyDBMS.getPath() + "\\" + DataBaseName + "\\Schema.xml", TableName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// delete tabel schema
		try {
			XML.deleteFile(MyDBMS.getPath() + "\\" + DataBaseName + "\\" + TableName + "Schema.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}
	
	public String getPath() {
		return path;
	}

	

}
