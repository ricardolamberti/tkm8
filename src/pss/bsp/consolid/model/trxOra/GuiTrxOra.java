package pss.bsp.consolid.model.trxOra;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActExternalLink;
import pss.core.winUI.forms.JBaseForm;

public class GuiTrxOra extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTrxOra() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTrxOra(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Trx Oracle"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTrxOra.class; }
  public String  getKeyField() throws Exception { return "factura"; }
  public String  getDescripField() { return "factura"; }
  public BizTrxOra GetcDato() throws Exception { return (BizTrxOra) this.getRecord(); }

  public void createActionMap() throws Exception {
 		this.addAction(10, "Descargar", null, 10020, true, true).setNuevaVentana(true);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActExternalLink(descargar());
		return super.getSubmitFor(a);
	}

	public String descargar() throws Exception {
		if (GetcDato().getTipoDocumento().equals("FCE"))
			return "http://129.146.75.114/CNS/YSDBFGHHJKIEDFGGTAXCCVB987SG/PDF/FCE"+GetcDato().getFactura()+".pdf";
		else if (GetcDato().getTipoDocumento().equals("STE"))
			return "http://129.146.75.114/CNS/YSDBFGHHJKIEDFGGTAXCCVB987SG/STE/STE"+GetcDato().getFactura()+".pdf";
		else 
			return "http://129.146.75.114/CNS/YSDBFGHHJKIEDFGGTAXCCVB987SG/PDF/"+GetcDato().getTipoDocumento()+GetcDato().getFactura()+".pdf";
	}
  
 }
