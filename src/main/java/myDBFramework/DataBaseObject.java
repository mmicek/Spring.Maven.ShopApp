package myDBFramework;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class DataBaseObject implements DataBaseObjectInterface{
	private static Connection connection = null;
	private List<String> primaryKeys = null;
	private DataBaseObject toUpdateObject = null;
	
	public DataBaseObject() {
		connection = DataBaseFactory.getConnection();
	}
	
	private Object parseAttribute(Object o,Class c) {
		if(o == null) 
			return "NULL";
		if(c.getName().equals("java.lang.String"))
			return "'" + o + "'";
		if(c.getTypeName().equals(null)) {
			return null;
		}
		return o;
	}
	
	private boolean isColumnMatching(String column) {
		return getMappedAttribute().containsKey(column);
	}
	
	private void replicate() {
		toUpdateObject = this;
	}
	
	//ID as a condition
	
	/**
	 * Searching all lines in database by PRIMARY KEY
	 * and setting their attributes equals to the atributes of
	 * object which is invoking this method  
	 */
	public void update() {
		//if(toUpdateObject == null)
		//	throw new IllegalArgumentException("Need to invoke 'replicate' method before updating object");
		toUpdateObject = this;
		
		try {
			
			this.primaryKeys = new LinkedList<>();
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet primaryKeys = meta.getPrimaryKeys(null, null
					,(String) this.getClass().getDeclaredMethod("getTable").invoke(this, null));
			while (primaryKeys.next()) {
                this.primaryKeys.add(primaryKeys.getString("COLUMN_NAME"));
            }
			
			String query = "UPDATE " + this.getTable() + " SET ";
			Map<String,String> mapped = this.getMappedAttribute();
			
			//Wstawiane kolumny
			query += updateColumns(mapped);
			query += " WHERE ";
			//Ustawianie warunkow wstawiania
			query += columnConditions(mapped);
			query += ";";
			Statement stm = connection.createStatement();
			stm.execute(query);	

			
		} catch (IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | SQLException | NoSuchFieldException e) {
			e.printStackTrace();
		}

		this.primaryKeys = null;
	}
	
	private String columnConditions(Map<String,String> mapped) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		String query = "";
		boolean first = true;
		
		for(Map.Entry<String, String> one : mapped.entrySet()) {
			if(this.primaryKeys.contains(one.getKey())) {
				if(!first)
					query += " AND ";
				Field field;
				try {
					field = this.getClass().getDeclaredField(one.getValue());
				}catch(Exception e) {
					field = this.getClass().getSuperclass().getDeclaredField(one.getValue());
				}
				field.setAccessible(true);
				query += one.getKey() + " = " + parseAttribute(field.get(this),field.getType()); 
				first = false;
			}
		}
		
		return query;
	}
	
	private String updateColumns(Map<String,String> mapped) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		String query = "";
		boolean first = true;
		
		for(Map.Entry<String, String> one : mapped.entrySet()) {
			if(!this.primaryKeys.contains(one.getKey())) {
				if(!first)
					query += ",";
				Field field;
				try {
					field = this.getClass().getDeclaredField(one.getValue());
				}catch(Exception e) {
					field = this.getClass().getSuperclass().getDeclaredField(one.getValue());
				}
				field.setAccessible(true);
				query += one.getKey() + " = " + parseAttribute(field.get(this),field.getType()); 
				first = false;
			}
		}
		
		return query;
	}
	
	/**
	 * Deleting all lines in database which have 
	 * the same PRIMARY KEY as this object
	 */
	public void delete() {
		
		try {
			
			String query = "DELETE FROM " + this.getTable() + " WHERE ";
			this.primaryKeys = new LinkedList<>();
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet primaryKeys = meta.getPrimaryKeys(null, null
					,(String) this.getClass().getDeclaredMethod("getTable").invoke(this, null));
			while (primaryKeys.next()) {
                this.primaryKeys.add(primaryKeys.getString("COLUMN_NAME"));
            }
			
			Map<String,String> mapped = this.getMappedAttribute();
			query += columnConditions(mapped);
			Statement stm = connection.createStatement();
			stm.execute(query);	
		
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | SQLException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * Executing any SQL query associated to this object
	 * @param slqQuery SQL query like "SELECT * ..."
	 * @return null if query does not returns table from database, DataBaseResult in other case
	 */
	public DataBaseResult executeQuery(String slqQuery){
		List<DataBaseObject> resultList = new ArrayList<>();
		
		try {
			
			//if(slqQuery.toLowerCase().contains("drop".toLowerCase()) || 
			//		slqQuery.toLowerCase().contains("delete"))
			//	throw new IllegalArgumentException("Cannot DROP or DELETE from Table");

			if(!slqQuery.toLowerCase().contains("SELECT".toLowerCase())) {
				Statement stm = connection.createStatement();
				stm.execute(slqQuery);
				return null;
			}
			
			List<String> columnNames = new LinkedList<>();
			List <Integer> columnIndex = new LinkedList<>();
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(slqQuery); //mozna sa
		 	ResultSetMetaData data = rs.getMetaData();
		 	for(int i=1;i<data.getColumnCount() + 1;i++) 
		 		if(isColumnMatching(data.getColumnName(i))) {
		 			columnNames.add(data.getColumnName(i));
		 			columnIndex.add(i);
		 		}
		 	
			while(rs.next()) {
				DataBaseObject mappedLine = this.getClass().getConstructor().newInstance();
				Object o;
				for(int i : columnIndex) {
					o = rs.getObject(i);
					Class c = this.getClass();
					c.getConstructors().getClass();
					String attributeName = getMappedAttribute().get(data.getColumnName(i));
					Field field;
					try{
						field = this.getClass().getDeclaredField(attributeName);
					}catch(Exception e) {
						field = this.getClass().getSuperclass().getDeclaredField(attributeName);
					}
					field.setAccessible(true);
					field.set(mappedLine,o);
					
				}
				resultList.add(mappedLine);
			}
			
			DataBaseResult result = new DataBaseResult();
			result.addList(resultList);
			result.addColumns(columnNames);
			return result;
			
		} catch (SQLException | InstantiationException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException 
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Inserts line to data base, additionally sets PRIMARY KEY. 
	 * Table must have IDENTITY_INSERT ON 
	 */
	public void saveWithID() { //OK
		try {
			
			String query = "INSERT INTO ";
			List<String> attributes = new LinkedList();
			List<String> columns = new LinkedList();

	    	String table = getDataBaseTable();
	    	query += table + " (";
			Map<String,String> mappedObjects = getMappedAttribute();
			
			for(Map.Entry<String, String> e : mappedObjects.entrySet()) {
				if(this.primaryKeys == null || !this.primaryKeys.contains(e.getKey())) {
					attributes.add(e.getValue());
					columns.add(e.getKey());
				}
			}
			for(int i=0;i<columns.size();i++) {
				query += columns.get(i);
				if(i+1 == columns.size())
					break;
				query += ",";
			}
			query += ") VALUES (";
			for(int i=0;i<attributes.size();i++) {
				Object value;
				Field field = this.getClass().getDeclaredField(attributes.get(i));
				field.setAccessible(true);
				value = parseAttribute(field.get(this),field.getType());
				
				query += value;
				if(i+1 == columns.size())
					break;
				query += ",";
			}
			query += ")";
			Statement stm = connection.createStatement();
			stm.execute(query);
		
		} catch (IllegalAccessException | IllegalArgumentException 
				| SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts line to data base. PRIMARY KEY is set automatically by database
	 * Table IDENTITY_INSERT is OFF 
	 */
	public void saveAutoID() {
	
		try {
			
			this.primaryKeys = new LinkedList<>();
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet primaryKeys = meta.getPrimaryKeys(null, null
					,(String) this.getClass().getDeclaredMethod("getTable").invoke(this, null));
			
			while (primaryKeys.next()) {
                this.primaryKeys.add(primaryKeys.getString("COLUMN_NAME"));
            }
			
			saveWithID(); 
			
		} catch (IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | SQLException e) {
			e.printStackTrace();
		}

		this.primaryKeys = null;
	}
	
	/**
	 * Returns result of "SELECT * FROM [TABLE]", 
	 * where table is specified by getTable() method
	 * @return null if the problem occurs, DataBaseResult in other case
	 */
	public DataBaseResult selectAll() {
		String query;
		try {
			query = "SELECT * FROM " + this.getClass().getDeclaredMethod("getTable").invoke(this, null);
			return executeQuery(query);
		} catch (NoSuchMethodException | SecurityException 
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Map<String,String> getMappedAttribute() {
		Map<String,String> mappedObjects = null;
		
		try {
			Method mappedM = this.getClass().getMethod("getMappedAttributes");
			mappedObjects = (Map<String,String>) mappedM.invoke(this, null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} 
		
		return mappedObjects;
	}
	
	private String getDataBaseTable() {
		String table = null;
		
		try {
			Method tableM = this.getClass().getMethod("getTable");
			table = (String) tableM.invoke(this,null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
    	
    	return table;
	}
	
}
