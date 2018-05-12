package myDBFramework;

import java.util.List;

public final class DataBaseResult {
	private List<DataBaseObject> list;
	private List<String> attributes;
	
	DataBaseResult(){}
	
	void addList(List<DataBaseObject> list) {
		this.list = list;
	}
	
	void addColumns(List<String> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * @return returns result of SQL query as a list of DataBaseObject
	 */
	public List<DataBaseObject> getMappedObjects(){
		return this.list;
	}
	
	/**
	 * @param String name of column from database 
	 */
	public boolean isColumnMapped(String columnName) {
		return attributes.contains(columnName);
	}
	
	/**
	 * @return returns list of columns returned by SQL query
	 */
	public List<String> getMappedColumns(){
		return attributes;
	}
	
}
