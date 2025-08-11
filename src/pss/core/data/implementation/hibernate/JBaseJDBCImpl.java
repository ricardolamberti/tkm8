package pss.core.data.implementation.hibernate;

import pss.common.security.BizUsuario;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBaseJDBC;

/**
 * @author PSS
 */
public class JBaseJDBCImpl extends JBaseJDBC {

	private static final String DESCRIPTION = "Hibernate";
	private static final String CONNECTION_IMPL = "pss.core.data.implementation.hibernate.JPssConnectionImpl";
	private static final String ROW_IMPL = "pss.core.data.implementation.hibernate.JRegJDBCImpl";

	public JBaseJDBCImpl() throws Exception {
		super();
	}

	public void initializeConnectionByUser(BizUsuario usr) throws Exception {

	}

	public void testConnection() throws Exception {

	}

	@Override
	public String getDatabaseDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getMetadataColumnImpl() {
		return "";
	}

	@Override
	public String getMetadataColumnsImpl() {
		return "";
	}

	@Override
	public String getMetadataIndexImpl() {
		return "";
	}

	@Override
	public String getMetadataIndexesImpl() {
		return "";
	}

	@Override
	public String getMetadataTableImpl() {
		return "";
	}

	@Override
	public String getMetadataTablesImpl() {
		return "";
	}

	@Override
	protected int timeoutValidConnection() throws Exception {
		return 10;
	}

	@Override
	public String getDatabaseName() {
		return "";
	}

	@Override
	public String getSetupTableComparisonImpl() {
		return "";
	}

	@Override
	public String getConnectionInterface() {
		return CONNECTION_IMPL;
	}

	@Override
	public String getRowInterface() {
		return ROW_IMPL;
	}


	@Override
	public String getAdminUserFileName() {
		return "auth.dat";
	}

	@Override
	public String getCommonUserFileName() {
		return "auth.dat";
	}

	@Override
	public String getLowerCaseInstruction() {
		return "lower";
	}

	/**
	 * Creates the master database
	 */
	public void setParamsMaster(JBDato zBaseSource) throws Exception {
	}

	@Override
	public boolean isHibernate() {
		return true;
	}

	/**
	 * <p>
	 * Setear en cada variable que resuelve un tipo de objeto de la base de datos
	 * la clase que implementa en forma fisica o virtual el objeto de la base de
	 * datos correspondiente
	 * </p>
	 */
	protected void initializeVirtualClassesNames() {
	}

}
