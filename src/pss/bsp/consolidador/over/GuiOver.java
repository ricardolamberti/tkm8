package pss.bsp.consolidador.over;

import pss.bsp.consolidador.IConsolidador;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiOver extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiOver() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizOver(); }
  public int GetNroIcono()   throws Exception { 
  	if (GetcDato().getStatus().equals(IConsolidador.OK))	return 943; 
  	if (GetcDato().getStatus().equals(IConsolidador.DISTINCT))	return 939; 
   	return 10027;
  }
  public String GetTitle()   throws Exception { return "Analisis Over"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormOver.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "boleto"; }
  public BizOver GetcDato() throws Exception { return (BizOver) this.getRecord(); }

  public void createActionMap() throws Exception {
		createActionQuery();
		createActionDelete();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		return null;
	}

	
 }
