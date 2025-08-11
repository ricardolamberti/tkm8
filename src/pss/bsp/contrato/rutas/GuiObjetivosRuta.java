package pss.bsp.contrato.rutas;

import pss.bsp.contrato.rutas.ms.GuiMSs;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiObjetivosRuta extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosRuta() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizObjetivosRuta(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo Ruta"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormObjetivosRuta.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "ruta"; }
  public BizObjetivosRuta GetcDato() throws Exception { return (BizObjetivosRuta) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
 		this.addAction(10, "Market Share", null, 10020, false, false, true, "Group");
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(getMarketShare());
  	return super.getSubmitFor(a);
  }

  public GuiMSs getMarketShare() throws Exception {
  	GuiMSs mss= new GuiMSs();
  	mss.addFilterAdHoc("company", GetcDato().getCompany());
  	mss.getRecords().addFilter("detalle", GetcDato().getIdDetalle());
  	mss.getRecords().addFilter("ruta", GetcDato().getRuta());
  	mss.getRecords().addFilter("factor", GetcDato().getFactor());
  	mss.getRecords().addFilter("aerolinea", GetcDato().getIdAerolinea());
  	return mss;
  }
  
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
}
