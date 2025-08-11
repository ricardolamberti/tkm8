package  pss.bsp.pdf.mex.detalle;

import pss.bsp.pdf.mex.impuesto.GuiMexImpuestos;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMexDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMexDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMexDetalle(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Detalle Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMexDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "aerolinea"; }
  public BizMexDetalle GetcDato() throws Exception { return (BizMexDetalle) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		this.addAction(10, "Impuestos", null, 5008, false, false, true, "Group").setAccessToDetail(true);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId() == 10)	return !GetVision().equals("INTERFAZ_6");
		return super.OkAction(a);
  }
  
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getTaxs());
		return null;
	}
	public GuiMexImpuestos getTaxs() throws Exception {
		GuiMexImpuestos pdfs=new GuiMexImpuestos();
		pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addFilter("owner", this.GetcDato().getOwner());
		pdfs.getRecords().addFilter("idPDF", this.GetcDato().getIdpdf());
		pdfs.getRecords().addFilter("linea", this.GetcDato().getLinea());
		return pdfs;
	}

 }
