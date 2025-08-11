/**
 * 
 */
package  pss.common.dbManagement.synchro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import pss.JPath;
import pss.common.dbManagement.JBaseDBManagement;
import pss.common.dbManagement.synchro.base.JBaseSystemDBTables;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBTable;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;

/**
 * Clase que ejecuta los pasos para sincronizar las clases que representan
 * tablas en la base de datos con las tablas fisicas.
 * 
 */
public class Synchro {
	public static final int ACTION_SYNCHRONIZE = 1;
	public static final int ACTION_PROCESS_FILE = 2;
	public static final int ACTION_EXPORT_DATA = 3;
	public static final int ACTION_IMPORT_DATA = 4;

	private static final String PRESQL = new String("Presql:");
	private static final String SQL = new String("Sql:");
	private static final String POSTSQL = new String("Postsql:");

	private JOrderedMap<String, JDBSqlActionList> sqlActionListMap = null;
	private int actionToExecute = 0;
	private String scriptFileName = null;
	private String javaDirectory = null;
	private File scriptFileObj = null;
	// private FileOutputStream scriptFileOutput = null;
	private FileWriter scriptFileOutput = null;
	private FileInputStream scriptFileInput = null;
	private String[] package_to_exclude = null;
	private boolean isExportAll = false;

	private Date now; // = new Date();
	private SimpleDateFormat dateFormat; // = new
																			 // SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	/**
	 * Metodo que ejecuta los pasos para sincronizar las clases y las tablas.
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void synchroDB() throws Exception {
	}

	/**
	 * @throws Exception
	 * 
	 */
	protected void processExportData() throws Exception {
		this.checkJavaDirectory();
		JDBClassTables oClassTables = new JDBClassTables();

		oClassTables.readAll(this.javaDirectory);

		JIterator<JDBClassTable> classTableIterator = oClassTables.getAllDBTableClasses().getValueIterator();
		JDBClassTable classTable;
		String className = "";
		JRecords<JRecord> regs;
		int indexAux;
		String outputDirectory = JBaseDBManagement.class.getName().replaceAll("\\.", "/");
		outputDirectory = outputDirectory.substring(0, outputDirectory.lastIndexOf("/"));
		outputDirectory = JPath.PssPath() + "/" + outputDirectory + "/data/" + BizPssConfig.getPssConfig().getInitializedData() + "/";
		outputDirectory = outputDirectory.replace("/bin/WEB-INF/classes/pss/", "/src/pss/");
		JTools.MakeDirectory(outputDirectory);

		while (classTableIterator.hasMoreElements()) {
			classTable = classTableIterator.nextElement();

			if (classTable.getClassFullName().indexOf("BizCierreConfigRegional") > 0)
				PssLogger.logDebug("Solo para debuguear");

			if (classTable.isExportData() || isExportAll) {
				try {
					PssLogger.logDebug("Exportando datos de la clase " + classTable.getClassFullName());
					className = classTable.getClassFullName();

					String outFileName = outputDirectory + className + ".xml";
					FileWriter fileWriter = new FileWriter(outFileName);

					regs = new JRecords(Class.forName(className));

					regs.clearFilters();
					regs.readAll();
					regs.toStatic();
					regs.setStatic(true);

					String data;
					data = regs.serialize();
					fileWriter.write(data);
					fileWriter.close();
				} catch (Exception e) {
					PssLogger.logError(e, "No se puede generar el xml en la siguiente clase :" + className);
				}
			}
		} // end while
	}

	/**
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
  protected void processImportData() throws Exception {
		this.checkJavaDirectory();
		JDBClassTables oClassTables = new JDBClassTables();

		oClassTables.readAll(this.javaDirectory);

		JIterator<JDBClassTable> classTableIterator = oClassTables.getAllDBTableClasses().getValueIterator();
		JDBClassTable classTable;
		String className = "";
		JRecords<JRecord> regs;
		// int indexAux;
		String inputDirectory = JBaseDBManagement.class.getName().replaceAll("\\.", "/");
		inputDirectory = inputDirectory.substring(0, inputDirectory.lastIndexOf("/"));
		inputDirectory = JPath.PssPath() + "/" + inputDirectory + "/data/" + BizPssConfig.getPssConfig().getInitializedData() + "/";
		inputDirectory = inputDirectory.replace("/bin/WEB-INF/classes/pss/", "/src/pss/");
		// JTools.MakeDirectory(inputDirectory);
		File xmlFile;
		FileInputStream xmlFileInput;
		String xmlFileData;
		int byteReaded;

		while (classTableIterator.hasMoreElements()) {
			classTable = classTableIterator.nextElement();

			if (classTable.getClassFullName().indexOf("BizCierreConfigRegional") > 0)
				PssLogger.logDebug("Solo para debuguear");

			className = classTable.getClassFullName();
			String outFileName = inputDirectory + className + ".xml";
			xmlFile = new File(outFileName);
			if (xmlFile.exists() == false)
				continue;
			
			if (xmlFile.length() == 0)
				continue;

			xmlFileData = "";
			xmlFileInput = new FileInputStream(xmlFile.getAbsolutePath());
			while (true) {
				byteReaded = xmlFileInput.read();
				if (byteReaded == -1)
					break;
			
				xmlFileData += String.valueOf((char) byteReaded);
			} // end while true
			
			regs = new JRecords(Class.forName(className));
			regs.unSerialize(xmlFileData);
			// regs.insertAll();
			regs.firstRecord();
			while (regs.nextRecord()) {
				JRecord record = regs.getRecord();
				try {
					this.execTransaction(record.getSqlInsert());
//					record.insert();
				} catch (Exception e) {
					PssLogger.logError(e, "No se pudo insertar registro en la tabla ["+ regs.GetTable() +"]");
				}
			}
			
		} // end while
	}

	/**
	 * @throws Exception
	 * 
	 */
	protected void processSQLFile() throws Exception {
		this.checkScriptFile(false);
		String sqlFullFileName = null;
		Date date;
		Date lastDate = null;

		if (this.scriptFileObj.isFile() == false) {
			File files[] = this.scriptFileObj.listFiles();
			File sqlFile;

			for (int fileIndex = 0; fileIndex < files.length; ++fileIndex) {
				sqlFile = files[fileIndex];
				date = new Date(sqlFile.lastModified());

				if (lastDate == null || date.after(lastDate)) {
					lastDate = date;
					sqlFullFileName = sqlFile.getAbsolutePath();
				}
			} // end for

			if (sqlFullFileName == null || sqlFullFileName.isEmpty()) {
				throw new JSyntaxExcepcion("No se encontro archivo para leer comandos sql");
			}
		} else {
			sqlFullFileName = this.scriptFileObj.getAbsolutePath();
		}

		try {
			this.scriptFileInput = new FileInputStream(sqlFullFileName);
		} catch (FileNotFoundException e) {
			throw new JSyntaxExcepcion("No se encontro el archivo " + sqlFullFileName);
		} catch (Exception e) {
			throw new JSyntaxExcepcion("Error inesperado intentando abrir el archivo " + sqlFullFileName);
		}

		try {
			String sqlLine = new String("");
			int readByte;
			boolean lfcrDetected = false; // line feed or Carriage return detectado

			while (true) {
				readByte = this.scriptFileInput.read();
				if (readByte == -1) {
					break;
				}
				if (readByte == 10 || readByte == 13) {
					lfcrDetected = true;
					continue;
				}
				if (lfcrDetected == false) {
					sqlLine += String.valueOf((char) readByte);
					continue;
				}
				processSQLLine(sqlLine);
				lfcrDetected = false;
				sqlLine = String.valueOf((char) readByte);
			} // end while
		} catch (Exception e) {
			PssLogger.logError(e, "Error leyendo archivo " + sqlFullFileName);
			throw e;
		}

	}

	/**
	 * @throws Exception
	 * 
	 */
	protected void processDBSynchronize() throws Exception {
		this.checkScriptFile(true);
		this.checkJavaDirectory();
		JBaseSystemDBTables oSystemTables = JBaseSystemDBTables.VirtualCreate();
		JDBClassTables oClassTables = new JDBClassTables();

		// Lee todas la tablas de la base de datos
		oSystemTables.readAll();

		// Lee todas la clases en el directorio configurado
		oClassTables.readAll(this.javaDirectory);

		JMap<String, JBaseVirtualDBTable> oVirtualClassTables = JCollectionFactory.createMap();
		JMap<String, JBaseVirtualDBTable> oVirtualDBTables = JCollectionFactory.createMap();

		// Cargo las Tablas basadas en clases Java
		JIterator<JDBClassTable> classTablesIterator = oClassTables.getAllDBTableClasses().getValueIterator();
		JBaseVirtualDBTable oVirtualDBTable;

		while (classTablesIterator.hasMoreElements()) {
			oVirtualDBTable = JBaseVirtualDBTable.VirtualCreate();
			JDBClassTable classTable = classTablesIterator.nextElement();
			boolean b_continue = false;
			if (this.package_to_exclude != null) {
				for(int i = 0; i < this.package_to_exclude.length; i++){
					if (classTable.getClassFullName().contains(this.package_to_exclude[i])) {
						b_continue = true ;
						break;
					}
				}
			}
			if (b_continue)
				continue;
			oVirtualDBTable.processClassTable(classTable);
			oVirtualClassTables.addElement(oVirtualDBTable.getTableName(), oVirtualDBTable);
		}

		// Cargo las Tablas declaradas en la base de datos
		oSystemTables.firstRecord();
		while (oSystemTables.nextRecord()) {
			oVirtualDBTable = JBaseVirtualDBTable.VirtualCreate();
			oVirtualDBTable.processSystemDBTable(oSystemTables.getRecord());
			oVirtualDBTables.addElement(oVirtualDBTable.getTableName(), oVirtualDBTable);
		}

		this.compareVirtualTables(oVirtualClassTables, oVirtualDBTables);
	}

	/**
	 * 
	 */
	public static void showOptions() {
		PssLogger.logDebug("**********************************************************************************");
		PssLogger.logDebug("*        ** PSS - Sincronizador de base de datos - Version 1.0 **                *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* Syntaxis:                                                                      *");
		PssLogger.logDebug("* Synchro --{SYNCHRONIZE|PROCESS|EXPORT}                        	               *");
		PssLogger.logDebug("*         [--FILE filename]                                                      *");
		PssLogger.logDebug("*         [--DIRECTORY javaDirectory]                                            *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* Comandos:                                                                      *");
		PssLogger.logDebug("* --SYNCHRONIZE o --SYNCHO o --S                                                 *");
		PssLogger.logDebug("*   Sincroniza las clases java especificadas en la opcion \"--D\" con las tablas   *");
		PssLogger.logDebug("*   de la base de datos configurada en el archivo pps.ini generando un archivo   *");
		PssLogger.logDebug("*   con todas las instrucciones SQL para actualizar la base de datos.            *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* --PROCESS o --P                                                                *");
		PssLogger.logDebug("*   Procesa el archivo especificado y aplicas las instrucciones SQL que contiene *");
		PssLogger.logDebug("*   en la base de datos especificada en el archivo pss.ini                       *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* --EXPORT o --E                                                                 *");
		PssLogger.logDebug("*   Lee todas las clases Java especificadas con la opcion \"--D\" y exporta los  *");
		PssLogger.logDebug("*   datos de la tabla a un archivo con el mismo nombre que la clase en la misma  *");
		PssLogger.logDebug("*   ubicacion pero con terminacion xml. Solo se exportan las tablas cuya conf.   *");
		PssLogger.logDebug("*   asi lo indique                                                               *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* --EXPORT_ALL o --EA                                                            *");
		PssLogger.logDebug("*   Igual que --EXPORT o --EA pero ignora el parametro de configuracion e        *");
		PssLogger.logDebug("*   intenta leer informacion de la tabla y exportarla                            *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* --IMPORT o --I                                                                 *");
		PssLogger.logDebug("*   Lee todas las clases Java especificadas con la opcion \"--D\" busca los      *");
		PssLogger.logDebug("*   archivos xml relacionados con dichas clases y si existen intenta insertar    *");
		PssLogger.logDebug("*   la informacion en la tabla correspondiente a la clase.                       *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* Opciones:                                                                      *");
		PssLogger.logDebug("* --FILE o --F   Especifica el archivo (y directorio) de entrada o salida        *");
		PssLogger.logDebug("*                dependiendo de la opcion opcion de sincronizar o procesar que   *");
		PssLogger.logDebug("*                se haya seleccionado. Si no se especifica por defecto se usa    *");
		PssLogger.logDebug("*                el directorio actual + pssData\\SynchroFiles                     *");
		PssLogger.logDebug("*                Si la opcion --SYNCHRONIZE fue especificada el nombre del       *");
		PssLogger.logDebug("*                archivo sera synchro.yyyy.MM.dd.HH:mm.ss.sql										 *");
		PssLogger.logDebug("*                Si la opcion --PROCESS fue especificada se va a buscar el       *");
		PssLogger.logDebug("*                archivo mas nuevo dentro del directorio                         *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* --DIRECTORY o  Especifica el directorio a partir de donde se buscan las clases *");
		PssLogger.logDebug("* --DIR o --D    de java para comparar con las tablas de la base de datos        *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("* --EPKG   			 Especifica paquetes a excluir. 	  														 *");
		PssLogger.logDebug("*     					 Ejemplo1: pss.sj.migracion excluye paquete migracion      *");
		PssLogger.logDebug("*     					 completo de la sincronización														       *");
		PssLogger.logDebug("*     					 Ejemplo2: pss.sj.migracion;pss.lex excluye paquete        *");
		PssLogger.logDebug("*     					 miracion y paquete lex de la sincronización	  					       *");
		PssLogger.logDebug("*                                                                                *");
		PssLogger.logDebug("**********************************************************************************");
	}

	/**
	 * @param args
	 * @throws JExcepcion
	 */
	public static void main(String[] args) throws JExcepcion {
		Synchro oSynchro = new Synchro();

		try {
			if (args.length == 0) {
				throw new JSyntaxExcepcion("No se definieron parametros");
			}

			int argIndex;
			for (argIndex = 0; argIndex < args.length; ++argIndex) {
				if (args[argIndex].substring(0, 2).equalsIgnoreCase("--")) { // Next is
																																		 // argument
					if (args[argIndex].substring(2).equalsIgnoreCase("synchronize") || args[argIndex].substring(2).equalsIgnoreCase("synchro") || args[argIndex].substring(2).equals("S")) {
						oSynchro.setAction(Synchro.ACTION_SYNCHRONIZE);
						continue;
					}

					if (args[argIndex].substring(2).equalsIgnoreCase("process") || args[argIndex].substring(2).equals("P")) {
						oSynchro.setAction(Synchro.ACTION_PROCESS_FILE);
						continue;
					}

					if (args[argIndex].substring(2).equalsIgnoreCase("export_all") || args[argIndex].substring(2).equals("EA")) {
						oSynchro.isExportAll = true;
						oSynchro.setAction(Synchro.ACTION_EXPORT_DATA);
						continue;
					}
					
					if (args[argIndex].substring(2).equalsIgnoreCase("export") || args[argIndex].substring(2).equals("E")) {
						oSynchro.setAction(Synchro.ACTION_EXPORT_DATA);
						continue;
					}

					if (args[argIndex].substring(2).equalsIgnoreCase("import") || args[argIndex].substring(2).equals("I")) {
						oSynchro.setAction(Synchro.ACTION_IMPORT_DATA);
						continue;
					}

					if (args[argIndex].substring(2).equalsIgnoreCase("file") || args[argIndex].substring(2).equals("F")) {
						++argIndex;
						if (argIndex >= args.length) {
							throw new JSyntaxExcepcion("Parametro " + args[argIndex] + " invalido");
						}

						oSynchro.setFileName(args[argIndex]);
						continue;
					}
					if (args[argIndex].substring(2).equalsIgnoreCase("epkg") ) {
						++argIndex;
						if (argIndex >= args.length) {
							throw new JSyntaxExcepcion("Parametro " + args[argIndex] + " invalido");
						}

						oSynchro.setPackageToExclude(args[argIndex].split(";"));
						continue;
					}

					if (args[argIndex].substring(2).equalsIgnoreCase("directory") || args[argIndex].substring(2).equalsIgnoreCase("dir") || args[argIndex].substring(2).equals("D")) {
						++argIndex;
						if (argIndex >= args.length) {
							throw new JSyntaxExcepcion("Parametro " + args[argIndex] + " invalido");
						}

						oSynchro.setJavaDirectory(args[argIndex]);
						continue;
					}
				} // end if
				PssLogger.logDebug("WARNING: Ignorando el parametro: " + args[argIndex]);
			} // end for

			oSynchro.executeAction();
		} catch (JSyntaxExcepcion s) {
			PssLogger.logDebug("Error:" + s.getMessage());
			Synchro.showOptions();
			PssLogger.logError(s);
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}

	protected void checkSqlActionListMap() throws Exception {
		if (this.sqlActionListMap == null) {
			this.sqlActionListMap = JCollectionFactory.createOrderedMap();
		}
	}

	/**
	 * @param masterVirtualTable
	 * @param slaveVirtualTable
	 * @throws Exception
	 */
	protected void compareVirtualTables(JMap<String, JBaseVirtualDBTable> masterVirtualTableMap, JMap<String, JBaseVirtualDBTable> slaveVirtualTableMap) throws Exception {
		JBaseVirtualDBTable masterVirtualTable;
		JIterator<JBaseVirtualDBTable> virtualDBTableIterator;
		JDBSqlActionList sqlActionList = null;

		// String slaveVirtualTableName;
		JBaseVirtualDBTable slaveVirtualTable;

		virtualDBTableIterator = masterVirtualTableMap.getValueIterator();

		while (virtualDBTableIterator.hasMoreElements()) {
			masterVirtualTable = virtualDBTableIterator.nextElement();

			PssLogger.logDebug("------------------------------------------------------------------------------");
			PssLogger.logDebug("Buscando  Tabla: " + masterVirtualTable.getTableName() + " en la base de datos");

			// Si la tabla virtual master no existe en la slave tengo que crearla.
			if (slaveVirtualTableMap.containsKey(masterVirtualTable.getTableName()) == false) {
				PssLogger.logDebug("Como la tabla " + masterVirtualTable.getTableName() + " no existe se va a generar el sql de creacion");
				sqlActionList = masterVirtualTable.getCreateTableActionList();
				sqlActionList.setRelatedTable(masterVirtualTable.getTableName());
				checkSqlActionListMap();
				sqlActionList.setClassNames(masterVirtualTable.getClassNames());
				this.sqlActionListMap.addElement(sqlActionList.getRelatedTable(), sqlActionList);
				continue;
			}

			PssLogger.logDebug("La tabla se encontro en la base de datos se va a comparar con la virtual");
			slaveVirtualTable = slaveVirtualTableMap.getElement(masterVirtualTable.getTableName());
			sqlActionList = masterVirtualTable.getAlterTableActionList(slaveVirtualTable);

			if (sqlActionList.ifHasSqlActions()) {
				checkSqlActionListMap();
				sqlActionList.setRelatedTable(masterVirtualTable.getTableName());
				sqlActionList.setClassNames(masterVirtualTable.getClassNames());
				this.sqlActionListMap.addElement(sqlActionList.getRelatedTable(), sqlActionList);
			}
		} // end while (masterTableNameIterator.hasMoreElements())

		virtualDBTableIterator = slaveVirtualTableMap.getValueIterator();

		while (virtualDBTableIterator.hasMoreElements()) {
			slaveVirtualTable = virtualDBTableIterator.nextElement();

			if (masterVirtualTableMap.containsKey(slaveVirtualTable.getTableName()) == false) {
				PssLogger.logDebug("Se va a borrar la tabla : " + slaveVirtualTable.getTableName());
				sqlActionList = slaveVirtualTable.generateDropTableSQL();
				if (sqlActionList != null) {
					checkSqlActionListMap();
					sqlActionList.setRelatedTable(slaveVirtualTable.getTableName());
					this.sqlActionListMap.addElement(sqlActionList.getRelatedTable(), sqlActionList);
				}
			}

		} // end while

		SimpleDateFormat dtFrmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		// Abre el archivo de salida
		this.scriptFileOutput = new FileWriter(this.scriptFileObj);

		this.scriptFileOutput.write("-------------------------------------------------------------------" + "\r\n");
		this.scriptFileOutput.write("-- Fecha :" + dtFrmt.format(now) + "\r\n");
		this.scriptFileOutput.write("-- Base de Datos: " + JBDatos.getDefaultDatabase().getDatabaseName() + "\r\n");
		this.scriptFileOutput.write("-- ConnectionURL: " + JBDatos.getDefaultDatabase().getConnectionURL() + "\r\n");
		this.scriptFileOutput.write("-- Driver Name: " + JBDatos.getDefaultDatabase().GetDriverName() + "\r\n");
		this.scriptFileOutput.write("-- Driver Name: " + JBDatos.getDefaultDatabase().getDriverInterface() + "\r\n");

		if (sqlActionListMap == null || sqlActionListMap.isEmpty()) {
			PssLogger.logDebug("No hay acciones para ejecutar ...");
			return;
		}

		writeActionToFile(JDBSqlActionList.CREATE_TABLE);
		writeActionToFile(JDBSqlActionList.ALTER_TABLE);
		writeActionToFile(JDBSqlActionList.DROP_TABLE);

		writeActionToFile(JDBSqlActionList.CREATE_INDEX);
		writeActionToFile(JDBSqlActionList.ALTER_INDEX);
		writeActionToFile(JDBSqlActionList.DROP_INDEX);

		this.scriptFileOutput.close();
	}

	protected void writeActionToFile(String actionFilter) throws Exception {
		JDBSqlAction sqlAction;
		JDBSqlActionList sqlActionList = null;
		JIterator<JDBSqlActionList> sqlActionListIterator = null;
		JIterator<JDBSqlAction> sqlActionIterator = null;
		JIterator<String> classNamesIterator = null;
		JIterator<String> actionReasonsIterator = null;

		sqlActionListIterator = this.sqlActionListMap.getValueIterator();

		while (sqlActionListIterator.hasMoreElements()) {
			sqlActionList = sqlActionListIterator.nextElement();

			if (sqlActionList.getMainSqlAction().equalsIgnoreCase(actionFilter) == false) {
				continue;
			}

			classNamesIterator = sqlActionList.getClassNames().getIterator();
			actionReasonsIterator = sqlActionList.getSqlActionReason().getIterator();

			this.scriptFileOutput.write("\r\n");
			this.scriptFileOutput.write("-------------------------------------------------------------------" + "\r\n");
			this.scriptFileOutput.write("-------------------------------------------------------------------" + "\r\n");
			this.scriptFileOutput.write("-- Table: " + sqlActionList.getRelatedTable() + "\r\n");
			this.scriptFileOutput.write("-- Main Action: " + sqlActionList.getMainSqlAction() + "\r\n");

			if (classNamesIterator.hasMoreElements()) {
				this.scriptFileOutput.write("-- Clases relacionadas con la tabla: " + "\r\n");
				while (classNamesIterator.hasMoreElements()) {
					this.scriptFileOutput.write("-- " + classNamesIterator.nextElement() + "\r\n");
				} // end while
			}

			if (actionReasonsIterator.hasMoreElements()) {
				this.scriptFileOutput.write("-- Los motivos del sql corriente son: " + "\r\n");
				while (actionReasonsIterator.hasMoreElements()) {
					this.scriptFileOutput.write("-- " + actionReasonsIterator.nextElement() + "\r\n");
				} // end while
			}

			this.scriptFileOutput.write("-- Acciones a ejecutar:" + "\r\n");

			sqlActionIterator = sqlActionList.getActionIterator();

			while (sqlActionIterator.hasMoreElements()) {
				sqlAction = sqlActionIterator.nextElement();
				this.scriptFileOutput.write("-- Action type: " + sqlAction.getAction() + "\r\n");
				if (sqlAction.getPreSql() != null) {
					this.scriptFileOutput.write("-- " + PRESQL + "\r\n");
					this.scriptFileOutput.write(sqlAction.getPreSql() + ";\r\n");
				}
				if (sqlAction.getSql() != null) {
					this.scriptFileOutput.write("-- " + SQL + "\r\n");
					this.scriptFileOutput.write(sqlAction.getSql() + ";\r\n");
				}
				if (sqlAction.getPostSql() != null) {
					this.scriptFileOutput.write("-- " + POSTSQL + "\r\n");
					this.scriptFileOutput.write(sqlAction.getPostSql() + ";\r\n");
				}
			}
		}

	}

	/**
	 * @param action
	 */
	public void setAction(int action) {
		this.actionToExecute = action;
	}

	/**
	 * @param fileName
	 *          the fileName to set
	 */
	public void setFileName(String fileName) {
		this.scriptFileName = fileName;
	}

	public void setPackageToExclude(String[] value) {
		this.package_to_exclude = value;
	}

	/**
	 * @param javaDirectory
	 *          the javaDirectory to set
	 */
	public void setJavaDirectory(String javaDirectory) {
		this.javaDirectory = javaDirectory;
	}

	/**
	 * @throws Exception
	 */
	public void executeAction() throws Exception {
		// open a Dispatcher Application session
		JAplicacion.AbrirSesion();
		// Abre aplicación
		JAplicacion.GetApp().AbrirApp("Synchro", JAplicacion.AppService(), true);

		now = new Date();
		dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		this.actionToExecute = 1;

		switch (this.actionToExecute) {
		case Synchro.ACTION_PROCESS_FILE:
			this.processSQLFile();
			break;
		case Synchro.ACTION_SYNCHRONIZE:
			this.processDBSynchronize();
			break;
		case Synchro.ACTION_EXPORT_DATA:
			this.processExportData();
			break;
		case Synchro.ACTION_IMPORT_DATA:
			this.processImportData();
			break;
		default:
			throw new JSyntaxExcepcion("Accion invalida o no especificada");
		} // end switch
	}

	protected void checkJavaDirectory() throws Exception {
		boolean generateJavaDirName;

		if (this.javaDirectory != null && this.javaDirectory.isEmpty() == false) {
			File javaDirObj = new File(javaDirectory);

			if (javaDirObj.exists() == false) {
				throw new JSyntaxExcepcion("El directorio " + this.javaDirectory + " no existe");
			}

			if (javaDirObj.isDirectory() == false) {
				throw new JSyntaxExcepcion(this.javaDirectory + " no es un directorio valido");
			}
		} else {
			this.javaDirectory = JPath.PssPath() + "/" + "pss";
		}
	}

	/**
	 * @throws Exception
	 */
	protected void checkScriptFile(boolean generateScriptFileName) throws Exception {
		// Chequeo los prerequisitos para sincronizar la base de datos
		String scriptFullFileName = null;
		int sepIndex = -1;
		File dirFileObj = null;

		if (this.scriptFileName != null && this.scriptFileName.isEmpty() == false) {
			if (this.scriptFileName.substring(this.scriptFileName.length() - 4).equalsIgnoreCase(".sql")) {
				sepIndex = this.scriptFileName.lastIndexOf(File.separatorChar);
				if (sepIndex >= 0) {
					dirFileObj = new File(this.scriptFileName.substring(0, sepIndex - 1));
					scriptFullFileName = scriptFullFileName.substring(sepIndex + 1);
				}
			} else {
				dirFileObj = new File(this.scriptFileName);
			}
		}

		if (dirFileObj == null) {
			dirFileObj = new File(JPath.PssPathData() + "/" + "SynchroFiles" + "/");
		}

		if (dirFileObj.exists() == false) {
			dirFileObj.mkdirs();
		}

		if (scriptFullFileName == null || scriptFullFileName.isEmpty()) {
			scriptFullFileName = dirFileObj.getAbsolutePath() + File.separatorChar;

			if (generateScriptFileName) {
				// now = new Date();
				// SimpleDateFormat dateFormat = new
				// SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

				scriptFullFileName += "synchro.";
				scriptFullFileName += dateFormat.format(now);
				scriptFullFileName += ".sql";
			}
		}

		this.scriptFileObj = new File(scriptFullFileName);
	}

	/**
	 * Ejecuta una instruccion sql en la base de datos
	 * 
	 * @param zSql
	 * @throws Exception
	 */
	private void execTransaction(String zSql) throws Exception {
		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
		oReg.executeTransaction(zSql);
		oReg.getDatabase().commit();
		oReg.close();
	}

	/**
	 * @param lineToProcess
	 * @return
	 * @throws Exception
	 */
	protected String getSqlFromLine(String lineToProcess, String prefijo) throws Exception {
		if (lineToProcess.length() >= prefijo.length() && lineToProcess.substring(0, prefijo.length()).equalsIgnoreCase(prefijo)) {
			return lineToProcess.substring(prefijo.length());
		}

		return null;
	}

	// /**
	// * @param lineToProcess
	// * @return
	// * @throws Exception
	// */
	// protected String getSqlFromLine(String lineToProcess) throws Exception {
	// String sqlLine = null;
	//		
	// sqlLine = getSqlFromLine(lineToProcess, PRESQL);
	// if (sqlLine == null) {
	// sqlLine = getSqlFromLine(lineToProcess, SQL);
	// if (sqlLine == null) {
	// sqlLine = getSqlFromLine(lineToProcess, POSTSQL);
	// }
	// }
	// return sqlLine;
	// }

	/**
	 * @param lineToProcess
	 * @throws Exception
	 */
	protected void processSQLLine(String lineToProcess) throws Exception {
		if (lineToProcess.length() < 2)
			return;

		if (lineToProcess.substring(0, 2).equalsIgnoreCase("--"))
			return;

		try {
			execTransaction(lineToProcess);
		} catch (Exception e) {
			PssLogger.logError(e, "Error procesando sql :" + lineToProcess);
		}
	}
}
