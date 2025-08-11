package pss.core.data.interfaces.sentences;

import pss.core.tools.JExcepcion;

public class JNoRowFoundException extends JExcepcion {

  /**
	 * 
	 */
	private static final long serialVersionUID = 5066581246350549093L;
	private String sTableName;

  public JNoRowFoundException() {
    super("02000", "Ninguna fila encontrada");
  }

  public JNoRowFoundException(String zErrorMessage) {
    super("02000", zErrorMessage);
  }

  public JNoRowFoundException(String zErrorMessage, String zTableName) {
    this(zErrorMessage);
    this.sTableName = zTableName;
  }

  public String getTableName() {
    return this.sTableName;
  }

}
