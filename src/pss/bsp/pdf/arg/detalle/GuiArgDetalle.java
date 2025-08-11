package  pss.bsp.pdf.arg.detalle;

import pss.bsp.pdf.arg.impuesto.GuiArgImpuestos;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiArgDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiArgDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizArgDetalle(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Detalle Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormArgDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "aerolinea"; }
  public BizArgDetalle GetcDato() throws Exception { return (BizArgDetalle) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		this.addAction(10, "Impuestos", null, 5008, false, false, true, "Group").setAccessToDetail(true);
  }
  
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getTaxs());
		return null;
	}
	public GuiArgImpuestos getTaxs() throws Exception {
		GuiArgImpuestos pdfs=new GuiArgImpuestos();
		pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addFilter("owner", this.GetcDato().getOwner());
		pdfs.getRecords().addFilter("idPDF", this.GetcDato().getIdpdf());
		pdfs.getRecords().addFilter("linea", this.GetcDato().getLinea());
		return pdfs;
	}

 }
