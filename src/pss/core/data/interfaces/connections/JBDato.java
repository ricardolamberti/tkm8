package pss.core.data.interfaces.connections;

import java.sql.Connection;

import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;

// ========================================================================== //
// Clase para manejo de una Conexion Generica
// ========================================================================== //
public abstract class JBDato extends JRecord {

	// -------------------------------------------------------------------------- //
	// Constantes de Base de Datos
	// -------------------------------------------------------------------------- //
	public final static String DATABASE_LIST = "DATABASE_LIST";
	public final static String DATABASES = "DATABASES";
	public final static String DATABASE_DEFAULT = "DATABASE_DEFAULT";
	public final static String DATABASE_TABLES = "DATABASE_TABLES";
	public final static String DATABASE_TYPE = "DATABASE_TYPE";
	public final static String DATABASE_SLAVE = "DATABASE_SLAVE";
	public final static String INTERFACE = "INTERFACE";
	public final static String REG_INTERFACE = "REG_INTERFACE";
	public final static String SQLSERVER = "SQLSERVER";
	public final static String SQLSERVER7 = "SQLSERVER7";
	public final static String ORACLE = "ORACLE";
	public final static String TEXT = "TEXT";

	// -------------------------------------------------------------------------- //
	// Propiedades
	// -------------------------------------------------------------------------- //
	private JString pName = new JString();
	private JString pInterface = new JString();
	private JString pRInterface = new JString();

	// private JString pDatabaseType = new JString();

	// -------------------------------------------------------------------------- //
	// Metodos de las propiedades
	// -------------------------------------------------------------------------- //
	public String GetName() throws Exception {
		return pName.getValue();
	}

	public String GetInterface() throws Exception {
		return pInterface.getValue();
	}

	public String GetRInterface() throws Exception {
		return pRInterface.getValue();
	}

	/*
	 * public String GetDatabaseType() throws Exception { return pDatabaseType.GetValor(); }
	 */
	public String getConnectionURL() {
		return "";
	}

	public String GetDriverName() {
		return "";
	}

	public String getDriverInterface() {
		return "";
	}

	public String getConnectionInterface() {
		return "";
	}

	public String getMetadataColumnImpl() {
		return "";
	}

	public String getMetadataColumnsImpl() {
		return "";
	}

	public String getMetadataIndexImpl() {
		return "";
	}

	public String getMetadataIndexesImpl() {
		return "";
	}

	public String getMetadataTableImpl() {
		return "";
	}

	public String getMetadataTablesImpl() {
		return "";
	}

	public String getDatabaseDescription() {
		return "";
	}

	public String getSetupTableComparisonImpl() {
		return "";
	}

	public String getLowerCaseInstruction() {
		return "";
	}

	public void SetName(String zValue) {
		pName.setValue(zValue.trim());
	}

	public void SetInterface(String zValue) {
		pInterface.setValue(zValue.trim());
	}

	public void SetRInterface(String zValue) {
		pRInterface.setValue(zValue.trim());
	}

	/*
	 * public void SetDatabaseType(String zValue) { pDatabaseType.SetValor(zValue.trim()); }
	 */
	public void setParamsReport() throws Exception {
	}

	/*
	 * public void setParamsMaster(JBDato zBaseSource) throws Exception { }
	 */
	// -------------------------------------------------------------------------- //
	// Constructor
	// -------------------------------------------------------------------------- //
	public JBDato() throws Exception {
		initializeVirtualClassesNames();
	}

	@Override
	public void createProperties() throws Exception {
		addItem("nombre", pName);
		addItem("inteface", pInterface);
		addItem("reg_inteface", pRInterface);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(JRecord.FIELD, "nombre", "Nombre", true, true, 100);
		addFixedItem(JRecord.FIELD, "inteface", "Interface", true, true, 100);
		addFixedItem(JRecord.FIELD, "reg_inteface", "Reg Interface", true, true, 100);
	}

	@Override
	public String GetTable() {
		return "";
	}

	public void synchronizeSystemAndDataBaseTime() throws Exception {
		return;
	}

	// -------------------------------------------------------------------------- //
	// Clases Base Genericas a sobreescribir
	// -------------------------------------------------------------------------- //
	
//	public boolean isOpen() throws Exception {
//		return false;
//	}
	
	public void open() throws Exception {
	}

	public void initializeConnectionByUser(BizUsuario usr) throws Exception {
	}
	
	public void close() throws Exception {
	}

	public void cancel() throws Exception {
	}

	public void commit() throws Exception {
	}

	public void rollback() throws Exception {
	}

	public void beginTransaction() throws Exception {
	}

	public void clearAllTransactions() throws Exception {
	}

	public String getDatabaseName() throws Exception {
		return "";
	}

	public String getHostName() throws Exception {
		return "";
	}

	public void asignParams(int zUserType) throws Exception {
	}

	protected void setCommonUser() throws Exception {
	}

	protected void setAdminUser() throws Exception {
	}

	public String GetUsername() throws Exception {
		return "";
	}

	public String GetUserService() throws Exception {
		return "";
	}

	public Connection GetConnection() throws Exception {
		return null;
	}

	public String getAuthenticationMode() throws Exception {
		return "";
	}

	public boolean isAuthenticationMode() throws Exception {
		return false;
	}

	public boolean isTransactionInProgress() throws Exception {
		return false;
	}

	public String getPassword() throws Exception {
		return "";
	}

	public String getRowInterface() {
		return "";
	}

	public JBaseRegistro createRowInterface() throws Exception {
		JBaseRegistro reg = (JBaseRegistro) Class.forName(this.getRowInterface()).newInstance();
		reg.setDatabase(this);
		return reg;
	}

	public boolean isSQLServer() {
		return false;
	}
	public boolean isPostGreSQL() {
		return false;
	}
	public boolean isOragle9() {
		return false;
	}
	public boolean isHibernate() {
		return false;
	}

	/**
	 * Nombre de la clase que representa un campo real de la tabla
	 */
	protected String systemDBFieldImpl = null;
	/**
	 * Nombre de la clase que representa varios campos reales de una tabla
	 */
	protected String systemDBFieldsImpl = null;
	/**
	 * Nombre de la clase que representa un indice real de una tabla
	 */
	protected String systemDBIndexImpl = null;
	/**
	 * Nombre de la clase que representa varios indices reales de una tabla
	 */
	protected String systemDBIndexesImpl = null;
	/**
	 * Nombre de la clase que representa una tabla real
	 */
	protected String systemDBTableImpl = null;
	/**
	 * Nombre de la clase que representa un grupo de tablas reales
	 */
	protected String systemDBTablesImpl = null;

	/**
	 * Nombre de la clase que representa un campo virtual
	 */
	protected String virtualDBFieldImpl = null;
	/**
	 * Nombre de la clase que representa varios campos virtuales de una tabla
	 */
	protected String virtualDBFieldsImpl = null;
	/**
	 * Nombre de la clase que representa un indice virtual de una tabla
	 */
	protected String virtualDBIndexImpl = null;
	/**
	 * Nombre de la clase que representa varios indices virtuales de una tabla
	 */
	protected String virtualDBIndexesImpl = null;
	/**
	 * Nombre de la clase que representa una tabla virtual
	 */
	protected String virtualDBTableImpl = null;

	/**
	 * <p>
	 * Este metodo tiene que ser implementado por cada clase que hereda de esta clase y que
	 * implementan un motor de base de datos. Tiene que setear en cada variable que resuelve un tipo
	 * de objeto de la base de datos la clase que implementa en forma fisica o virtual el objeto de la
	 * base de datos correspondiente
	 * </p>
	 * <p>
	 * Ej. systemDBFieldImpl tiene que contener el nombre de la clase que implementa un campo de una
	 * tabla fisica para un motor de base de datos determinado
	 * </p>
	 */
	protected abstract void initializeVirtualClassesNames();

	/**
	 * Retorna el nombre de la clase que implementa un campo en el motor de bases de datos configurado
	 * actualmente
	 * 
	 * @return Nombre de la clase que implementa un campo en el motor de base de datos actual
	 * @throws Exception
	 *           Si no es configurada ninguna clase
	 */
	public String getSystemDBFieldImpl() throws Exception {
		if (this.systemDBFieldImpl == null || this.systemDBFieldImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva systemDBField para el motor de base de datos actual");
		}
		return this.systemDBFieldImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un grupo de campos en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return nombre de la clase que implementa un grupo de campos en el motor de bases de datos
	 * @throws Exception
	 *           si no esta configurada ninguna clase
	 */
	public String getSystemDBFieldsImpl() throws Exception {
		if (this.systemDBFieldsImpl == null || this.systemDBFieldsImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva systemDBFields para el motor de base de datos actual");
		}
		return this.systemDBFieldsImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un indice en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return Nombre de la clase que implementa un indice en el motor de base de datos actual
	 * @throws Exception
	 *           Si no es configurada ninguna clase
	 */
	public String getSystemDBIndexImpl() throws Exception {
		if (this.systemDBIndexImpl == null || this.systemDBIndexImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva systemDBIndexImpl para el motor de base de datos actual");
		}
		return this.systemDBIndexImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un grupo de indices en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return nombre de la clase que implementa un grupo de indices en el motor de bases de datos
	 * @throws Exception
	 *           si no esta configurada ninguna clase
	 */
	public String getSystemDBIndexesImpl() throws Exception {
		if (this.systemDBIndexesImpl == null || this.systemDBIndexesImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva systemDBIndexesImpl para el motor de base de datos actual");
		}
		return this.systemDBIndexesImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa una tabla en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return Nombre de la clase que implementa una tabla en el motor de base de datos actual
	 * @throws Exception
	 *           Si no es configurada ninguna clase
	 */
	public String getSystemDBTableImpl() throws Exception {
		if (this.systemDBTableImpl == null || this.systemDBTableImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva systemDBTableImpl para el motor de base de datos actual");
		}
		return this.systemDBTableImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un grupo de tablas en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return nombre de la clase que implementa un grupo de tablas en el motor de bases de datos
	 * @throws Exception
	 *           si no esta configurada ninguna clase
	 */
	public String getSystemDBTablesImpl() throws Exception {
		if (this.systemDBTablesImpl == null || this.systemDBTablesImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva systemDBTablesImpl para el motor de base de datos actual");
		}
		return this.systemDBTablesImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un campo en el motor de bases de datos configurado
	 * actualmente
	 * 
	 * @return Nombre de la clase que implementa un campo en el motor de base de datos actual
	 * @throws Exception
	 *           Si no es configurada ninguna clase
	 */
	public String getVirtualDBFieldImpl() throws Exception {
		if (this.virtualDBFieldImpl == null || this.virtualDBFieldImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva virtualDBField para el motor de base de datos actual");
		}
		return this.virtualDBFieldImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un grupo de campos en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return nombre de la clase que implementa un grupo de campos en el motor de bases de datos
	 * @throws Exception
	 *           si no esta configurada ninguna clase
	 */
	public String getVirtualDBFieldsImpl() throws Exception {
		if (this.virtualDBFieldsImpl == null || this.virtualDBFieldsImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva virtualDBFields para el motor de base de datos actual");
		}
		return this.virtualDBFieldsImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un indice en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return Nombre de la clase que implementa un indice en el motor de base de datos actual
	 * @throws Exception
	 *           Si no es configurada ninguna clase
	 */
	public String getVirtualDBIndexImpl() throws Exception {
		if (this.virtualDBIndexImpl == null || this.virtualDBIndexImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva virtualDBIndexImpl para el motor de base de datos actual");
		}
		return this.virtualDBIndexImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa un grupo de indices en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return nombre de la clase que implementa un grupo de indices en el motor de bases de datos
	 * @throws Exception
	 *           si no esta configurada ninguna clase
	 */
	public String getVirtualDBIndexesImpl() throws Exception {
		if (this.virtualDBIndexesImpl == null || this.virtualDBIndexesImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva virtualDBIndexesImpl para el motor de base de datos actual");
		}
		return this.virtualDBIndexesImpl;
	}

	/**
	 * Retorna el nombre de la clase que implementa una tabla en el motor de bases de datos
	 * configurado actualmente
	 * 
	 * @return Nombre de la clase que implementa una tabla en el motor de base de datos actual
	 * @throws Exception
	 *           Si no es configurada ninguna clase
	 */
	public String getVirtualDBTableImpl() throws Exception {
		if (this.virtualDBTableImpl == null || this.virtualDBTableImpl.isEmpty()) {
			JExcepcion.SendError("No se definio clase que resuelva virtualDBTableImpl para el motor de base de datos actual");
		}
		return this.virtualDBTableImpl;
	}

}
