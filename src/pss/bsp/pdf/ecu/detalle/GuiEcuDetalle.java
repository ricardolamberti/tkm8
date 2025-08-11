package  pss.bsp.pdf.ecu.detalle;

import pss.bsp.pdf.ecu.impuesto.GuiEcuImpuestos;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiEcuDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEcuDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEcuDetalle(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Detalle Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEcuDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "aerolinea"; }
  public BizEcuDetalle GetcDato() throws Exception { return (BizEcuDetalle) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		this.addAction(10, "Impuestos", null, 5008, false, false, true, "Group").setAccessToDetail(true);
  }
  
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getTaxs());
		return null;
	}
	public GuiEcuImpuestos getTaxs() throws Exception {
		GuiEcuImpuestos pdfs=new GuiEcuImpuestos();
		pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addFilter("owner", this.GetcDato().getOwner());
		pdfs.getRecords().addFilter("idPDF", this.GetcDato().getIdpdf());
		pdfs.getRecords().addFilter("linea", this.GetcDato().getLinea());
		return pdfs;
	}

 }
