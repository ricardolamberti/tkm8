package  pss.bsp.interfaces.copa.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCopaCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCopaCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCopaCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCopaCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizCopaCabecera GetcDato() throws Exception { return (BizCopaCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
