package pss.core.services.records;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;


/**
 * Clase que representa indices logicos
 *
 */
public class JIndex {

	public String  table;
	public boolean unique=false;
	public JList<String> columns=JCollectionFactory.createList();
	
	public JIndex(String table) {
		this.table = table;
	}
	
	public JIndex(String table, boolean unique) {
		this.table = table;
		this.unique = unique;
	}
	
	public void addColumn(String column) {
		columns.addElement(column);
	}
	

}
