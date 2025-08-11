package pss.core.services.records;

import pss.core.tools.JExcepcion;


public class JDuplicatedException extends JExcepcion {
	
  public JDuplicatedException(String zMsg) {
  	 super(zMsg);
  }
	

  public static void RaiseError() throws JDuplicatedException {
  	JDuplicatedException oException;
    oException = new JDuplicatedException("ALTA duplicada. Revise la identificación del ítem que intenta ingresar");
    throw oException;
  } // RaiseError
  
}
