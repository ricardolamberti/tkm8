package pss.bsp.consolidador.consolidacionOracle;

import pss.bsp.consolidador.IConsolidador;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiConsolidacionOracle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiConsolidacionOracle() throws Exception {
  }



  public JRecord ObtenerDato()   throws Exception { return new BizConsolidacionOracle(); }
  public int GetNroIcono()   throws Exception { 
  	if (GetcDato().getStatus().equals(IConsolidador.OK))	return 943; 
  	if (GetcDato().getStatus().equals(IConsolidador.DISTINCT))	return 940; 
  	if (GetcDato().getStatus().equals(IConsolidador.ONLY_BSP))	return 939; 
  	if (GetcDato().getStatus().equals(IConsolidador.ONLY_BO))	return 941; 
  	return 10027;
  }
  public String GetTitle()   throws Exception { return "Consolidacion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormConsolidacionOracle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "status"; }
  public BizConsolidacionOracle GetcDato() throws Exception { return (BizConsolidacionOracle) this.getRecord(); }
  public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		this.addAction(10, "Ver boleto", null, 5129, true, true, true, "Group");
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==10) return GetcDato().hasBoleto();
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmit(BizAction a) throws Exception {
   	if (a.getId()==10) return new JActQuery(getBoleto());
     	return super.getSubmit(a);
  }
  
  private GuiPNRTicket getBoleto() throws Exception {
  	GuiPNRTicket pnr = new GuiPNRTicket();
  	pnr.setRecord(GetcDato().getObjBoleto());
  	return pnr;
  }
  @Override
  public String getFieldForeground(String zColName) throws Exception {
  	
  	return GetcDato().getFieldForeground(zColName);
  }
 }
