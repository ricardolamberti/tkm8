/**
 * 
 */
package pss.core.services.fields;

/**
 * Implementa un campo que almacena un objeto tipo LOB. Los campos tipo LOB pueden ser implementados
 * por un String pero tienen que distinguirse para poder crear automaticamente el campo de la tabla.
 * 
 */
public class JLOB extends JString {

	/**
	 * 
	 */
	public JLOB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param val
	 */
	public JLOB(String val) {
		super(val);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getObjectType() {
		return JObject.JBLOB;
	}

}
