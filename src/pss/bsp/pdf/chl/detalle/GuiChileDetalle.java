package  pss.bsp.pdf.chl.detalle;

import pss.bsp.pdf.chl.impuesto.GuiChileImpuestos;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiChileDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiChileDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizChileDetalle(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Detalle Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormChileDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "aerolinea"; }
  public BizChileDetalle GetcDato() throws Exception { return (BizChileDetalle) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		this.addAction(10, "Impuestos", null, 5008, false, false, true, "Group").setAccessToDetail(true);
  }
  
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getTaxs());
		return null;
	}
	public GuiChileImpuestos getTaxs() throws Exception {
		GuiChileImpuestos pdfs=new GuiChileImpuestos();
		pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addFilter("owner", this.GetcDato().getOwner());
		pdfs.getRecords().addFilter("idPDF", this.GetcDato().getIdpdf());
		pdfs.getRecords().addFilter("linea", this.GetcDato().getLinea());
		return pdfs;
	}

 }
