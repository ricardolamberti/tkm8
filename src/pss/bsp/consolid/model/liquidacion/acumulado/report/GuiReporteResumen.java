package pss.bsp.consolid.model.liquidacion.acumulado.report;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;

public class GuiReporteResumen extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiReporteResumen() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizReporteResumen(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Liquidacion"; }
//  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLiqAcum.class; }
  public String  getKeyField() throws Exception { return "liquidacion_id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizReporteResumen GetcDato() throws Exception { return (BizReporteResumen) this.getRecord(); }

	public void createActionMap() throws Exception {
		
	
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		
		return super.getSubmitFor(a);
	}
}
