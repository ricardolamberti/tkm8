package pss.core.data.interfaces.sentences;

import pss.core.tools.JExcepcion;

public class JNoFieldException extends JExcepcion {

  /**
	 * 
	 */
	private static final long serialVersionUID = 6252748139532704787L;
	private String sTableName;

  public JNoFieldException() {
    super("02001", "Ningun campo seleccionado");
  }

  public JNoFieldException(String zErrorMessage) {
    super("02001", zErrorMessage);
  }

  public JNoFieldException(String zErrorMessage, String zTableName) {
    this(zErrorMessage);
    this.sTableName = zTableName;
  }

  public String getTableName() {
    return this.sTableName;
  }

}
