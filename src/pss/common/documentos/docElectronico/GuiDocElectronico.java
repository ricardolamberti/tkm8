package pss.common.documentos.docElectronico;

import java.io.File;

import pss.JPath;
import pss.common.documentos.GuiDocumento;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActReport;
import pss.core.winUI.forms.JBaseForm;

public class GuiDocElectronico extends GuiDocumento {



  /**
   * Constructor de la Clase
   */
  public GuiDocElectronico() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDocElectronico(); }
  public String GetTitle()   throws Exception { return "Documento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDocElectronico.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormDocElectronicoNew.class; }
  public String  getKeyField() throws Exception { return "id_doc"; }
  public String  getDescripField() { return "titulo"; }
  public BizDocElectronico GetcDato() throws Exception { return (BizDocElectronico) this.getRecord(); }

  public void createActionMap() throws Exception {
		this.addAction(1, "Consultar", null, 117, false, false);
//			createActionUpdate();			
		this.addAction(10, "Ver Documento", null, 117, true, true).setNuevaVentana(true);
		this.createActionDelete();
	}
  
  @Override 
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActFileGenerate(this, 10) {
  		public String generate() throws Exception {
  			return verDocumento(); 
  		};
//  		public String getProvider() {
//  			return "abs_file";
//  		}

  	};
  	return super.getSubmitFor(a);
  }
  
  
//	  @Override
//		public JAct getSubmitQuery(BizAction a) throws Exception {
//		  return this.getActionPrintImage();
//	  }
  
  

//	  public JAct getQueryAction() throws Exception {
//	  	return new JActQuery(this);
//	  }
  
	public int GetDobleClick() throws Exception {
		return 10;
	}
	
	public String getTempDir() throws Exception {		
		String path = JPath.PssPathTempFiles();
		File dir = new File(path);
		if (!dir.exists()) dir.mkdir();
		path+="/"+this.GetcDato().getCompany();
		dir = new File(path);
		if (!dir.exists()) dir.mkdir();
		return path;
	}

	
	public String verDocumento() throws Exception {
		String source=this.GetcDato().getAbsoluteFileName();
		String target = this.GetcDato().getTitulo();
//		JTools.DeleteFile(this.getTempDir()+"/"+target);
//		PssLogger.logDebug("copiando..." + source + " to..." + this.getTempDir()+"/"+target);
		JTools.copyFile(source, this.getTempDir()+"/"+target);
		return this.GetcDato().getCompany()+"/"+target;
//		return this.GetcDato().makeFileElectronico();
	}

  public int GetNroIcono()   throws Exception {
		if (this.GetcDato().isTypePdf())
			return 928;
		if (this.GetcDato().isTypeExcel())
			return 524;
		if (this.GetcDato().isTypeDoc())
			return 523;
		if (this.GetcDato().isTypeTxt())
			return 989;
		if (this.GetcDato().isTypeImage())
			return 6094;

		return 989; 
  }

	public JAct findActionQuery() throws Exception {
		return this.findAction(10).getObjSubmit();
	}

  public boolean isNuevaVentana() throws Exception {
  	return true;
  }

//	public boolean isQueryNuevaVentana() throws Exception {
//		return true;
//	}
  
}
