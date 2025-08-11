package pss.bsp.contrato.detalle.prorrateo;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiProrrateo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiProrrateo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizProrrateo(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormProrrateo.class; }
  public String  getKeyField() throws Exception { return "id_prorrateo"; }
  public String  getDescripField() { return "cliente"; }
  public BizProrrateo GetcDato() throws Exception { return (BizProrrateo) this.getRecord(); }
  public BizProrrateo GetxDato() throws Exception { 
  	return (BizProrrateo) this.getRelativeWin().getRecord(); 
  }

  
  public void createActionMap() throws Exception {
		super.createActionMap();
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		return super.getSubmitFor(a);
	}
 
	  @Override
		public boolean canConvertToURL() throws Exception {
			return false;
		}
 }
