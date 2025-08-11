package pss.common.documentos.docElectronico;

import pss.core.services.records.JRecord;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiDocAdjunto extends GuiDocElectronico {


//	private GuiDocEmail mail;
//	
//	public void setDocEmail(GuiDocEmail e) { 
//		this.mail=e;
//	}
	
	  /**
   * Constructor de la Clase
   */
  public GuiDocAdjunto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDocAdjunto(); }
  public String GetTitle()   throws Exception { return "Adjunto"; }
  public BizDocAdjunto GetccDato() throws Exception { return (BizDocAdjunto) this.getRecord(); }
  public Class<? extends JBaseForm> getFormFlat() throws Exception {
  	return FormDocAdjuntoFlat.class;
  }
  public void createActionMap() throws Exception {
		super.createActionMap();
		this.addAction(15, "Incrustar Imagen", null, 15058, true, true);
	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==3) return this.GetccDato().getObjDocEmail().isSaliente() && !this.GetccDato().getObjDocEmail().isEnviado();
		if (a.getId()==15) return !this.GetccDato().hasIdDoc(); // en proceso de creacion
		return super.OkAction(a);
	}
	
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==15) return new JActSubmit(this, 15) {
			public void submit() throws Exception {
				GetccDato().embeddedImage(); 
			};
		};
		return super.getSubmitFor(a);
	}

  
 }
