package  pss.common.dbManagement.synchro.base;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;

/**
 * 
 * 
 */
public abstract class JBaseSystemDBTable extends JRecord {
	/**
	 * 
	 */
	protected JString pTableName = new JString();
	protected JString pTableschema = new JString();

	/**
	 * 
	 */
	protected transient JBaseSystemDBIndexes systemDBIndexes = null;

	/**
	 * 
	 */
	protected transient JBaseSystemDBFields systemDBFields = null;

	/**
	 * @return
	 * @throws Exception
	 */
	public static JBaseSystemDBTable VirtualCreate() throws Exception {
		JBaseSystemDBTable oTabla = (JBaseSystemDBTable) JBaseSystemDBTable.VirtualClass().newInstance();
		return oTabla;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		String sClassName = JBDatos.getBaseMaster().getSystemDBTableImpl(); // .getMetadataTableImpl();
		return Class.forName(sClassName);
	}

	/**
	 * @throws Exception
	 */
	public JBaseSystemDBTable() throws Exception {
		super();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String getTableName() throws Exception {
		return pTableName.getValue();
	}
	public void setTableName(String zVal) throws Exception {
		pTableName.setValue(zVal);
	}
	/**
	 * @see pss.core.services.records.JRecord#read()
	 */
	@Override
	public boolean read() throws Exception {
		if (this.pTableName == null || this.pTableName.isEmpty()) {
			JExcepcion.SendError("Nombre de la tabla es nulo o vacio, no se puede leer la tabla");
		}
		
		// if (!super.read()) {
		// return false;
		// }

		checkSystemDBFields();
		this.systemDBFields.addFilter("table_name", this.pTableName);
		if (!this.systemDBFields.readAll()) {
			return false;
		}

		checkSystemDBIndexes();
		this.systemDBIndexes.setTableName(this.pTableName.toString());
		this.systemDBIndexes.readAll();

		return false;
	}
	

	/**
	 * @throws Exception
	 */
	protected void checkSystemDBFields() throws Exception {
		if (this.systemDBFields == null) {
			this.systemDBFields = JBaseSystemDBFields.VirtualCreate();
		}
	}

	/**
	 * @throws Exception
	 */
	protected void checkSystemDBIndexes() throws Exception {
		if (this.systemDBIndexes == null) {
			this.systemDBIndexes = JBaseSystemDBIndexes.VirtualCreate();
		}
	}

	/**
	 * @return the systemDBIndexes
	 */
	public JBaseSystemDBIndexes getSystemDBIndexes() {
		return systemDBIndexes;
	}

	/**
	 * @return the systemDBFields
	 */
	public JBaseSystemDBFields getSystemDBFields() {
		return systemDBFields;
	}
	
	public void applyDefaultFilters() throws Exception {
		addFilter("TABLE_SCHEMA", "public");
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// /**
	// * JDBBaseTabla
	// *
	// * Genera el SQL que describe el tipo y la longitud de un campo de una tabla, segun el formato
	// * especifico del motor de la base de datos donde se implementa.
	// *
	// * @param tableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que describe el tipo de la base de datos
	// * @throws Exception
	// */
	// protected abstract String makeSqlDataTypes(JDBClassTableField jdbTableField) throws Exception;

	// /**
	// * JDBBaseTable
	// *
	// * Realiza la conversion del tipo de datos generico al tipo de datos SQL especifico de cada
	// motor
	// * de base de datos.
	// *
	// * @param DataType -
	// * tipo de datos generico tipificado en
	// * {@link pss.common.dbManagement.synchro.JBaseVirtualDBField JBaseVirtualDBField}
	// * @return tipo de dato SQL
	// * @throws Exception
	// */
	// protected abstract String getDataTypeDescSQL(String dataType) throws Exception;
	//
	// /**
	// * Genera el SQL que agrega un campo a la tabla
	// *
	// * @param jdbTableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que crea un nuevo campo
	// * @throws Exception
	// */
	// public abstract String addColumn(JDBClassTableField jdbTableField) throws Exception;
	//
	// /**
	// * JDBBaseTable
	// *
	// * Genera el SQL que describe un campo de una tabla, segun el formato especifico del motor de la
	// * base de datos donde se implementa.
	// *
	// * @param tableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que describe el campo dependiendo del motor de base de
	// * datos
	// * @throws Exception
	// */
	// protected abstract String makeColumnDescSQL(JDBClassTableField jdbTableField) throws Exception;
	//
	// /**
	// * JDBBaseTable
	// *
	// * Genera el SQL que modifica los atributos de un campo a la tabla
	// *
	// * @param tableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que modificar un campo
	// * @throws Exception
	// */
	// protected abstract String modifyColumn(JDBClassTableField jdbTableField) throws Exception;

}
