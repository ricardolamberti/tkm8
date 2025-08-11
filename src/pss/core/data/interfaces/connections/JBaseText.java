package pss.core.data.interfaces.connections;

import java.io.File;
import java.io.RandomAccessFile;

import pss.JPath;
import pss.core.data.BizPssConfig;
import pss.core.data.files.JStreamFile;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JStringTokenizer;

//========================================================================== //
// Clase para manejo de Conexiones JDBC
//========================================================================== //
public class JBaseText extends JBDato {

	//-------------------------------------------------------------------------- //
	// Constantes
	//-------------------------------------------------------------------------- //
	public final static String DRIVER_NAME = "DRIVER_NAME";
	public final static String PATH = "PATH";
	public final static String FILE_EXTENSION = "FILE_EXTENSION";
	public final static String FLD_DELIMITER = "FLD_DELIMITER";
	public final static String DEFAULT_NULL_VALUE = "NULL_VALUE";
	public final static String USER = "USER";
	public final static String PASSWORD = "PASSWORD";
	public final static String NULL_VALUE = "½";
	public final static char DEFAULT_DELIMITER = '¾'; //"€";
	public final static String DEFAULT_EXTENSION = "txt";

	//-------------------------------------------------------------------------- //
	// Propiedades
	//-------------------------------+--------------------------------------------- //
	private String pNullValue = "";
	private String pPath = "";
	private String pFileExt = "";
	private char pDelim = ' ';
	private String pFile = "";
	private String pLine;
	private RandomAccessFile poFile;
	private JStringTokenizer pTok;

	public String GetPath() {
		return pPath;
	}
	public void SetPath(String zPath) {
		pPath = zPath;
	}
	public String GetFileExt() {
		return pFileExt;
	}
	public void SetFileExt(String zExt) {
		pFileExt = zExt;
	}
	public char GetDelimiter() {
		return pDelim;
	}
	public void SetDelimiter(char zDelim) {
		pDelim = zDelim;
	}
	public String GetFile() {
		return pFile;
	}
	public void SetFile(String zFile) {
		pFile = zFile;
	}
	public String GetLine() {
		return pLine;
	}
	public void SetLine(String zFile) {
		pLine = zFile;
	}

	public String GetNullValue() {
		return pNullValue;
	}
	public void SetNullValue(String zNullValue) {
		pNullValue = zNullValue;
	}

	public JStringTokenizer GetTokensLine() {
		return pTok;
	}
	public void SetTokensLine(JStringTokenizer zTok) {
		pTok = zTok;
	}

	// public JStreamFile GetFileStream()               { return poFile;    }
	// public void        SetFileStream( JStreamFile zFile ) { poFile = zFile;   }
	public RandomAccessFile GetFileObject() {
		return poFile;
	}
	public void SetFileObject(RandomAccessFile zFile) {
		poFile = zFile;
	}

	//-------------------------------------------------------------------------- //
	// Constructor
	//-------------------------------------------------------------------------- //
	public JBaseText() throws Exception {}

	//-------------------------------------------------------------------------- //
	// Seteo los parametros para la clase JDBC
	//-------------------------------------------------------------------------- //
	public void SetParams(String zBaseDatos, BizPssConfig zParam) throws Exception {

		pPath = zParam.getCachedStrictValue(zBaseDatos, PATH);

		try {
			pDelim = zParam.getCachedStrictValue(zBaseDatos, FLD_DELIMITER).charAt(0);
		} catch (Exception E) {
			pDelim = DEFAULT_DELIMITER;
		} // catch

		try {
			pFileExt = zParam.getCachedStrictValue(zBaseDatos, FILE_EXTENSION);
		} catch (Exception E) {
			pFileExt = DEFAULT_EXTENSION;
		} // catch

		try {
			pNullValue = zParam.getCachedStrictValue(zBaseDatos, DEFAULT_NULL_VALUE);
		} catch (Exception E) {
			pNullValue = NULL_VALUE;
		} // catch

		//    SetDatabaseType( zParam.GetValor( zBaseDatos, this.DATABASE_TYPE ) );
	}

	//-------------------------------------------------------------------------- //
	// Abro la base
	//-------------------------------------------------------------------------- //
	@Override
	public void open() throws Exception {
		try {
			File oDataBase = new File(this.pPath);

			if (!oDataBase.exists())
				JExcepcion.SendError("No esta instalada la base de datos^ " + this.pPath);

			if (!oDataBase.isDirectory())
				JExcepcion.SendError("Base de datos erronea");

			System.out.println("Driver de Texto inicializado.");
		} catch (Exception e) {
			JExcepcion.SendError("Error : Abriendo base de datos en modo texto. Error^ (" + e.getMessage() + ")");
		}
	}

	//-------------------------------------------------------------------------//
	// Cierro la base de datos
	//-------------------------------------------------------------------------//
	@Override
	public void close() throws Exception {
		try {
			System.out.println("Driver de Texto Terminado.");
		} // try
		catch (Exception E) {
			JExcepcion.ProcesarError(E, this.getClass().getName() + "#Cerrar");
		} // catch
	} // Cerrar

	public void cancel() throws Exception {
		try {
			System.out.println("Driver de Texto Cancelado.");
		} // try
		catch (Exception E) {
			JExcepcion.ProcesarError(E, this.getClass().getName() + "#Cerrar");
		} // catch
	} // Cerrar

	//---------------------------------------------------------------------------//
	//  Comienzo una transaccion de Base de Datos                                //
	//---------------------------------------------------------------------------//
	@Override
	public void beginTransaction() throws Exception {
		try {
			String sDirOrigen = JPath.PssPath() + "\\" + this.pPath;
			String sDirDestino = sDirOrigen + "\\temp";

			File oFile = new File(sDirDestino);
			long lFileQty = oFile.listFiles().length;

			if (lFileQty > 0)
				return;

			JTools.CopyFiles(sDirOrigen, sDirDestino);
		} // try
		catch (Exception E) {
			JExcepcion.ProcesarError(E, this.getClass().getName() + "BeginTransaction");
		} // catch
	} // BeginTransaction

	//---------------------------------------------------------------------------//
	//  Commit de una transaccion de Base de Datos                               //
	//---------------------------------------------------------------------------//
	@Override
	public void commit() throws Exception {
		String sDir = JPath.PssPath() + "\\" + this.pPath + "\\temp";
		JTools.DeleteFiles(sDir);
	}

	//---------------------------------------------------------------------------//
	//  Rollback de una transaccion de Base de Datos                             //
	//---------------------------------------------------------------------------//
	@Override
	public void rollback() throws Exception {
		String sDirDestino = JPath.PssPath() + "\\" + this.pPath;
		String sDirOrigen = sDirDestino + "\\temp";

		JTools.CopyFiles(sDirOrigen, sDirDestino);
		JTools.DeleteFiles(sDirOrigen);
	}

	public JStreamFile ExecuteQuery(String zSQL) throws Exception {
		return null;
	}

	boolean IfEmpty(File zFile) {
		String oFiles[];
		oFiles = zFile.list();
		return (oFiles == null || oFiles.length == 0);
	}

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
	protected void initializeVirtualClassesNames() {
	}
	
}
