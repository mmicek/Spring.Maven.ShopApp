package myDBFramework;

import java.util.List;
import java.util.Map;

public interface DataBaseObjectInterface {
	/**
	 * 
	 * 		@return map where keys are cloumn names from database
	 * 		and values are corresponding to them attributes from java class 
	 */
	Map<String,String> getMappedAttributes();  //Kolumna -> Atrybut | Pierwszy zawsze id
	/**
	 * 		@return name of database Table
	 */
	String getTable();
}
