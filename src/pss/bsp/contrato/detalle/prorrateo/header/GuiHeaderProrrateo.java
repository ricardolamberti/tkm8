package pss.bsp.contrato.detalle.prorrateo.header;

import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiHeaderProrrateo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiHeaderProrrateo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizHeaderProrrateo(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Liquidación"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormHeaderProrrateo.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "cliente"; }
  public BizHeaderProrrateo GetcDato() throws Exception { return (BizHeaderProrrateo) this.getRecord(); }
  public BizHeaderProrrateo GetxDato() throws Exception { 
  	return (BizHeaderProrrateo) this.getRelativeWin().getRecord(); 
  }
  JFilterMap filter;


  public JFilterMap getFilterMap() {
		return filter;
	}


	public void setFilterMap(JFilterMap action) {
		this.filter = action;
	}

  





 }
