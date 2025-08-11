package pss.common.documentos.docLocal;

import pss.common.documentos.GuiDocumento;
import pss.core.services.records.JRecord;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiDocLocal extends GuiDocumento {



  /**
   * Constructor de la Clase
   */
  public GuiDocLocal() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDocLocal(); }
  public String GetTitle()   throws Exception { return "Documento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDocLocal.class; }
  public String  getKeyField() throws Exception { return "id_doc"; }
  public String  getDescripField() { return "titulo"; }
  public BizDocLocal GetcDato() throws Exception { return (BizDocLocal) this.getRecord(); }
	public int GetNroIcono() throws Exception {
		return 724;
	}
  
  public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
	} 
  
//  @Override
//  public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return new JActFileGenerate(this, 10) {
//  		public String generate() throws Exception {
//  			return verDocumento(); 
//  		};
//  		public String getProvider() {
//  			return "abs_file";
//  		}
//
//  	};
//  	return super.getSubmitFor(a);
//  }
  
  
//	  @Override
//		public JAct getSubmitQuery(BizAction a) throws Exception {
//		  return this.getActionPrintImage();
//	  }
  
  

//	  public JAct getQueryAction() throws Exception {
//	  	return new JActQuery(this);
//	  }
  
	public int GetDobleClick() throws Exception {
		return 1;
	}
	


	public JAct findActionQuery() throws Exception {
		return this.findAction(1).getObjSubmit();
	}

  public boolean isNuevaVentana() throws Exception {
  	return false;
  }

}
