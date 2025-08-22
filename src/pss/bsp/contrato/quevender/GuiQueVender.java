package pss.bsp.contrato.quevender;

import pss.bsp.contrato.quevender.ms.GuiMSs;
import pss.bsp.contrato.rutas.GuiObjetivosRutas;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiQueVender extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiQueVender() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizQueVender(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return GetcDato().getDescripcion(); }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormQueVender.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizQueVender GetcDato() throws Exception { return (BizQueVender) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
 		this.addAction(30, "Reporte", null, 10020, false, false, true, "Group");
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==30) return new JActWins(getMS());
 	return super.getSubmitFor(a);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
	 public JWins getMS() throws Exception {
	  	GuiMSs	c = new GuiMSs();
	  	c.setParent(this);
	  	c.getRecords().SetSQL(GetcDato().getSqlWithDate());
	   	c.getRecords().setParent(this.GetcDato());
	    c.SetVision(""+GetcDato().getGrafico());
	  	return c;
	  	
	  }
	public GuiObjetivosRutas getRutas() throws Exception {
  	GuiObjetivosRutas	c = new GuiObjetivosRutas();
  	c.setParent(this);
  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
  	c.getRecords().addFilter("contrato", this.GetcDato().getIdContrato());
  	c.getRecords().addFilter("detalle", this.GetcDato().getIdDetalle());
  	c.getRecords().setParent(this.GetcDato());
  	return c;
  }
  
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
}
