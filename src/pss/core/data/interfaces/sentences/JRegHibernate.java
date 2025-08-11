package pss.core.data.interfaces.sentences;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JStringTokenizer;

public class JRegHibernate extends JBaseRegistro {
	protected static final String COMMA=" , ";
	protected static final String EQUAL=" = ";
	protected static final String SPACE=" ";
	protected static final String COMILLA="'";
	protected static final String DOT=".";
	protected static final String DOT_STAR=".*";
	protected static final String SELECT="select ";
	protected static final String DISTINCT=" DISTINCT ";
	protected static final String INSERT_INTO="insert into ";
	protected static final String UPDATE="update ";
	protected static final String SET=" set ";
	protected static final String DELETE_FROM="delete from ";
	protected static final String AND=" AND ";

	// -------------------------------------------------------------------------- //
	// Propiedades
	// -------------------------------------------------------------------------- //
	boolean bEOF;
	// -------------------------------------------------------------------------- //
	// Constructor
	// -------------------------------------------------------------------------- //
	public JRegHibernate() {
	}


	// -------------------------------------------------------------------------- //
	// Ejecuto un Update
	// -------------------------------------------------------------------------- //
	@Override
	public void update() throws Exception {

		return;
	} // update

	// -------------------------------------------------------------------------- //
	// Ejecuto un Delete
	// -------------------------------------------------------------------------- //
	@Override
	public void delete() throws Exception {
	

		return;
	} // OpenCursor

	// -------------------------------------------------------------------------- //
	// Ejecuto un Insert
	// -------------------------------------------------------------------------- //
	@Override
	public void insert() throws Exception {
		} // Insert

	@Override
	public String SelectMax(String zCampo) throws Exception {
		// return "1";

		return "1";
	}

	// -------------------------------------------------------------------------- //
	// Init el Query
	// -------------------------------------------------------------------------- //
	@Override
	public void openCursor() throws Exception {
	}

	// -------------------------------------------------------------------------- //
	// Obtengo siguiente registro de un recordset
	// -------------------------------------------------------------------------- //
	@Override
	public boolean next() throws Exception {
		bEOF=true;
	

		return !bEOF;
	} // Next

	// -------------------------------------------------------------------------- //
	// Chequeo filtros
	// -------------------------------------------------------------------------- //
	public boolean OkFiltros(JStringTokenizer zTok) throws Exception {

		return true;
	}

	// -------------------------------------------------------------------------- //
	// cierro el recordset
	// -------------------------------------------------------------------------- //
	@Override
	public void close() throws Exception {
	
	}

	// -------------------------------------------------------------------------- //
	// Obtengo siguiente registro de un recordset
	// -------------------------------------------------------------------------- //
	@Override
	public boolean EOF() throws Exception {
		return bEOF;
	} // Next

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como String
	// -------------------------------------------------------------------------- //
	@Override
	public String CampoAsStr(String zCampo) throws Exception {
		return "";
	} // CampoAsStr

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como long
	// -------------------------------------------------------------------------- //
	@Override
	public Long CampoAsLong(String zCampo) throws Exception {
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null||sCampo.length()==0) return null;
		return Long.valueOf(sCampo);
	} // CampoAsLong

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como int
	// -------------------------------------------------------------------------- //
	@Override
	public Integer CampoAsInt(String zCampo) throws Exception {
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null||sCampo.length()==0) return null;
		return Integer.valueOf(sCampo);
	} // CampoAsInt

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como float
	// -------------------------------------------------------------------------- //
	@Override
	public Double CampoAsFloat(String zCampo) throws Exception {
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null) return null;
		return Double.valueOf(sCampo);
	} // CampoAsFloat

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como Date
	// -------------------------------------------------------------------------- //
	@Override
	public java.util.Date CampoAsDate(String zCampo) throws Exception {
		// java.util.Date oDate = (java.util.Date) oRSet.getDate( zCampo );
		// if ( oRSet.wasNull() )
		// return null;
		// return oDate;
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null) return null;
		return JDateTools.StringToDate(sCampo, "yyyy-mm-dd");

	} // CampoAsDate

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como DateTime
	// -------------------------------------------------------------------------- //
	@Override
	public java.sql.Time CampoAsTime(String zCampo) throws Exception {
		// java.sql.Time oTime = oRSet.getTime( zCampo );
		// if ( oRSet.wasNull() )
		return null;
		// return oTime;
	} // CampoAsDate

	

}
