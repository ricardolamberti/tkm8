package  pss.common.dbManagement.synchro;

import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

/**
 * <p>
 * Representa un grupo de indices relacionados con una tabla.
 * </p>
 * <p>
 * La informacion de cada indice se almacena en una instancia de la clase
 * {@link pss.common.dbManagement.synchro.JDBClassIndex JDBTableIndice}
 * </p>
 * 
 */
public class JDBClassIndexes {

	/**
	 * Mapa de indices de una tabla. El mapa esta indexado por el nombre del indice
	 */
	private JMap<String, JDBClassIndex> oIndices = null;

	/**
	 * Constructor
	 */
	public JDBClassIndexes() {
	}

	/**
	 * Chequea que el mapa se haya creado antes de ser usado
	 */
	private void checkForIndices() {
		if (oIndices == null) {
			oIndices = JCollectionFactory.createMap();
		}
	}

	/**
	 * Retorna un {@link pss.core.tools.collections.JIterator JIterator} q apunta a los indices
	 * guardados en el mapa
	 * 
	 * @return Retorna iterador
	 */
	public JIterator<JDBClassIndex> getDBFieldsIterator() {
		checkForIndices();
		return this.oIndices.getValueIterator();
	}

	/**
	 * Retorna un indice en base a su nombre
	 * 
	 * @param pIndexName
	 *          Nombre del indice que se busca
	 * @return Instancia de la clase
	 *         {@link pss.common.dbManagement.synchro.JDBClassIndex JVirtualIndex} si existe un indice
	 *         con el nombre indicado. Si no encuentra el indice retorna null;
	 */
	public JDBClassIndex getVirtualIndex(String pIndexName) {
		checkForIndices();
		if (this.oIndices.containsKey(pIndexName) == false) {
			return null;
		}
		return oIndices.getElement(pIndexName);
	}

	/**
	 * Agrega un indice al mapa de indices.
	 * 
	 * @param pIndex
	 *          Instancia de la clase
	 *          {@link pss.common.dbManagement.synchro.JDBClassIndex JVirtualIndex} que representa un
	 *          indice.
	 * @throws Exception
	 *           Si ya existe el indice
	 */
	public void addVirtualIndex(JDBClassIndex pIndex) throws Exception {
		if (pIndex == null){
			return;
		}
		checkForIndices();
		if (this.oIndices.containsKey(pIndex.getIndexName()) != false) {
			JExcepcion.SendError("Nombre de indice " + pIndex.getIndexName() + " duplicado");
		}
		oIndices.addElement(pIndex.getIndexName(), pIndex);
	}

	/**
	 * Agrega todos los indices que contiene otra clase contenedora de indices.
	 * 
	 * @param pIndexes
	 */
	public void addVirtualIndexes(JDBClassIndexes pIndexes) {
		if (pIndexes == null) {
			return;
		}
		if (pIndexes.oIndices == null) {
			return;
		}		
		checkForIndices();
		oIndices.addElements(pIndexes.oIndices);
	}

	public boolean ifHasPrimaryKey() {
		JIterator<JDBClassIndex> oIndexIterator;
		JDBClassIndex indexClass;
		
		this.checkForIndices();
		
		oIndexIterator = this.oIndices.getValueIterator();
		
		while (oIndexIterator.hasMoreElements()) {
			indexClass = oIndexIterator.nextElement();
			if (indexClass.isIndexIsPrimaryKey()) {
				return true;
			}
		} // end while
		
		return false;
	}
	
}
