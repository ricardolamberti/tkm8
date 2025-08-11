package  pss.common.dbManagement.synchro.base;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;

/**
 * 
 *
 */
public class JBaseSystemDBTables extends JRecords<JBaseSystemDBTable> {

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		// String sClassName=JBDatos.getBaseMaster().getMetadataTablesImpl();
		String sClassName=JBDatos.getBaseMaster().getSystemDBTablesImpl();
		return Class.forName(sClassName);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static JBaseSystemDBTables VirtualCreate() throws Exception {
		JBaseSystemDBTables oTabla=(JBaseSystemDBTables) JBaseSystemDBTables.VirtualClass().newInstance();
		return oTabla;
	}


	/**
	 * Constructor
	 * @throws Exception
	 */
	public JBaseSystemDBTables() throws Exception {
	}

	/**
	 * @see pss.core.services.records.JRecords#getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBTable> getBasedClass() {
		return JBaseSystemDBTable.class;
	}

	
	/**
	 * @see pss.core.services.records.JRecords#GetTable()
	 */
	@Override
	public String GetTable() {
		return "";
	}

	/**
	 * @see pss.core.services.records.JRecords#readAll()
	 */
	@Override
	public boolean readAll() throws Exception {
		this.endStatic();
		super.readAll();
		this.toStatic();
		this.setStatic(true);
		
		JBaseSystemDBTable oSystemTable;
		
		// Recorro todas las tablas leidas y fuerzo a que cada una lea 
		// sus campos y sus indices.
		while (nextRecord()) {
			oSystemTable = getRecord();
			oSystemTable.read();
		} // end while
		this.firstRecord();
		return true;
	}

	public void applyDefaultFilters() throws Exception {
	}
	
}
