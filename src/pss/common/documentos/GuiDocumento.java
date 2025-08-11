package pss.common.documentos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActUpdate;


public class GuiDocumento  extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDocumento() throws Exception {
  }
 

  public JRecord ObtenerDato()   throws Exception { return new BizDocum(); }
  public int GetNroIcono()   throws Exception { return 525; }
  public String GetTitle()   throws Exception { return "Documento Físico"; }
  public String  getKeyField() throws Exception { return "id_doc"; }
  public String  getDescripField() { return "titulo"; }
  public BizDocumento GetcDato() throws Exception { return (BizDocumento) this.getRecord(); }

	public void createActionMap() throws Exception {
	}
	
	public JAct getSubmitFor(BizAction a) throws Exception {
		return super.getSubmitFor(a);
	}
	
	public JAct findActionPostAlta() throws Exception {
		return null;
	}

	public JAct findActionQuery() throws Exception {
		return null;
	}

  public boolean isNuevaVentana() throws Exception {
  	return false;
  }
 
	public JAct getObjDocumentoEdit() throws Exception {
		return new JActUpdate(this,2);
	}	

  public JAct getActionPrint() throws Exception {
  	JAct act = new JActFileGenerate(this) {
  		 public String generate() throws Exception {
  			 return generatePrint() ;
  		 };
  	};
  	return act;
  }
  
  public String generatePrint() throws Exception {
  	return GetcDato().generatePrint() ;
 }

//	public boolean isQueryNuevaVentana() throws Exception {
//		return false;
//	}
	
}
