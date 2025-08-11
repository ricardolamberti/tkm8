package pss.bsp.consolid.model.diferenciaLiq;

import pss.bsp.consolidador.IConsolidador;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiDiferenciaLiq extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDiferenciaLiq() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDiferenciaLiq(); }
  public int GetNroIcono()   throws Exception { 
  	if (GetcDato().getStatus().equals(IConsolidador.OK))	return 943; 
  	if (GetcDato().getStatus().equals(IConsolidador.DISTINCT))	return 939; 
   	return 10027;
  }
  public String GetTitle()   throws Exception { return "Diferencia Liq."; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDiferenciaLiq.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "boleto"; }
  public BizDiferenciaLiq GetcDato() throws Exception { return (BizDiferenciaLiq) this.getRecord(); }
  @Override
  public String getFieldForeground(String zColName) throws Exception {
  	
  	return GetcDato().getFieldForeground(zColName);
  }
  public void createActionMap() throws Exception {
		this.addAction(10, "Ver Boleto", null, 10020, true, true, true, "Group");
		
	
		this.createActionQuery();
	
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		
		if (a.getId()==10) return new JActQuery(getTicket());
		
		return super.getSubmitFor(a);
	}
	
	public GuiPNRTicket getTicket() throws Exception {
		GuiPNRTicket tkt = new GuiPNRTicket();
		tkt.GetcDato().dontThrowException(true);
		tkt.GetcDato().ReadByBoleto(GetcDato().getCompany(),GetcDato().getBoleto());
		return tkt;
	}
 }
