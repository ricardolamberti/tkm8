package pss.bsp.consolidador.iva;

import pss.bsp.consolidador.IConsolidador;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiImpositivo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiImpositivo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizImpositivo(); }
  public int GetNroIcono()   throws Exception { 
  	if (GetcDato().getStatus().equals(IConsolidador.OK))	return 943; 
  	if (GetcDato().getStatus().equals(IConsolidador.DISTINCT))	return 939; 
   	return 10027;
  }
  public String GetTitle()   throws Exception { return "Analisis Impositivo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormImpositivo.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "boleto"; }
  public BizImpositivo GetcDato() throws Exception { return (BizImpositivo) this.getRecord(); }
  @Override
  public String getFieldForeground(String zColName) throws Exception {
  	
  	return GetcDato().getFieldForeground(zColName);
  }
 

 }
