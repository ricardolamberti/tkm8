package  pss.bsp.pdf.chl.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiChileCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiChileCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizChileCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormChileCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizChileCabecera GetcDato() throws Exception { return (BizChileCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
