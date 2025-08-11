package  pss.common.dbManagement.synchro;

import java.io.File;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

/**
 * <p>
 * Representa un grupo de clases que representan una tabla fisica de bases de datos. Dichas clases
 * deben heredar de {@link pss.core.services.records.JRecord JRecord}}
 * </p>
 * <p>
 * Esta clase tiene la funcionalidad de recorrer un arbol de directorios desde un directorio inicial
 * buscando todas las clases java que representen una tabla de base de datos. Para ello se debe
 * llamar al metodo {@link #readAll(String) readAll}
 * </p>
 * <p>
 * Cada clase se abre y se saca solo la informacion referida a la tabla guardandose en una clase
 * {@link pss.common.dbManagement.synchro.JDBClassTable JDBTableClass}
 * </p>
 * 
 */
public class JDBClassTables {

	/**
	 * Mapa que almacena las informacion de cada clase que representa un base de datos indexada por el
	 * nombre de la tabla que representa
	 */
	JMap<String, JDBClassTable> tableClassesInfo;

	/**
	 * Constructor de la clase
	 * 
	 * @throws Exception
	 */
	public JDBClassTables() throws Exception {
	}

	/**
	 * <p>
	 * Lee todas la clases que representan tablas de las bases de datos buscando desde el directorio
	 * que se pasa como parametro y todos los subdirectorios.
	 * </p>
	 * 
	 * @param dirName
	 *          Especifica el directorio raiz desde donde se empieza a buscar las clases que
	 *          representan tablas
	 * @throws Exception
	 *           Si el directorio pasado por parametro no existe
	 */
	public void readAll(String dirName) throws Exception {
		File oFile = new File(dirName);
		File aFile[] = oFile.listFiles();

		if (aFile == null)
			JExcepcion.SendError("El directorio^ '" + dirName + JLanguage.translate("' no existe, pero esta configurado en el producto. Se deberá eliminarlo de la confiuración del producto."));

		// Inicializo el mapa que guarda las clases
		if (this.tableClassesInfo == null)
			this.tableClassesInfo = JCollectionFactory.createMap();

		// Por cada archivo encontrado
		for (int i = 0; i < aFile.length; i++) {
			if (!aFile[i].isDirectory()) { // Si el archivo no es un directorio

				// Proceso cada archivo (no directorio) encontrado. Solo lo almaceno si
				// es una clase que representa un tabla.
				JDBClassTable oDBTableClass = new JDBClassTable();
				if (!oDBTableClass.processJRecordClass(oFile.getPath(), aFile[i].getName()))
					continue;
				if (tableClassesInfo.containsKey(oDBTableClass.getTableName())) {
					JDBClassTable dbTableClass = this.tableClassesInfo.getElement(oDBTableClass.getTableName());
					if (dbTableClass != null) {
						dbTableClass.addOthersClassWithSameTable(oDBTableClass.getClassFullName());
					}
				} else {
					this.tableClassesInfo.addElement(oDBTableClass.getTableName(), oDBTableClass);
				}
				continue;
			} // end if

			// Si la el archivo leido es un directorio lo proceso y guardo el resultado siempre y cuando
			// haya encontrado alguna clase valida.
			JDBClassTables oTableClasses = new JDBClassTables();
			oTableClasses.readAll(oFile.getPath() + "/" + aFile[i].getName());

			if (oTableClasses.getAllDBTableClasses().size() > 0) {
				this.tableClassesInfo.addElements(oTableClasses.getAllDBTableClasses());
			} // end if
		} // end for
	}

	/**
	 * Retorna el mapa de clases almacenadas, si no se proceso ningun directorio o no se encontro
	 * ningun archivo valido retorna un mapa vacio pero no retorna null
	 * 
	 * @return Mapa con instancias de clases {@link JDBClassTable}
	 */
	public JMap<String, JDBClassTable> getAllDBTableClasses() {
		if (this.tableClassesInfo == null)
			this.tableClassesInfo = JCollectionFactory.createMap();
		return this.tableClassesInfo;
	}
}
