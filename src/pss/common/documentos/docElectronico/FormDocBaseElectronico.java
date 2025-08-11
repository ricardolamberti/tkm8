package pss.common.documentos.docElectronico;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDocBaseElectronico extends JBaseForm {

	private static final long serialVersionUID = 12218220792562L;


	  /**
	   * Constructor de la Clase
	   */
	  public FormDocBaseElectronico() throws Exception {
	  }


	  /**
	   * Inicializacion Grafica 
	   */

	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "doc_company").setHide(true);
	    AddItemFile( "Archivo", CHAR, REQ, "nombre_archivo");
	  }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
