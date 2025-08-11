package  pss.bsp.pdf.mex.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMexCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMexCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMexCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMexCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizMexCabecera GetcDato() throws Exception { return (BizMexCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
