package  pss.bsp.interfaces.dqb.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiDQBCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDQBCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDQBCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDQBCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizDQBCabecera GetcDato() throws Exception { return (BizDQBCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
