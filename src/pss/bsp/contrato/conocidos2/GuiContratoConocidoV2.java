package pss.bsp.contrato.conocidos2;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;

public class GuiContratoConocidoV2 extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiContratoConocidoV2() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizContratoConocidoV2(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Contrato conocido"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormContratoConocidoV2.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizContratoConocidoV2 GetcDato() throws Exception { return (BizContratoConocidoV2) this.getRecord(); }

  public void createActionMap() throws Exception {
   	this.addAction(10, "Detalle", null, 5012, false, false, false, "Group" );
 
  	super.createActionMap();
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {

  	return super.OkAction(a);
  }
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)  return new JActUpdate(getDetalle(),4);
		return super.getSubmitFor(a);
	}

	public GuiDetalle getDetalle() throws Exception {
		BizDetalle det = GetcDato().getObjDetalle();
		GuiDetalle win = det.getLogicaInstance(GetcDato().getTipoContrato()).getWin();
		win.setRecord(det);
		win.setDropListener(this);
		win.SetVision("VIEW");
		return win;
	}
 }
