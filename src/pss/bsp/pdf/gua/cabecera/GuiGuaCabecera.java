package  pss.bsp.pdf.gua.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiGuaCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiGuaCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizGuaCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormGuaCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizGuaCabecera GetcDato() throws Exception { return (BizGuaCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
