package  pss.bsp.pdf.col.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiColCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiColCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizColCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormColCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizColCabecera GetcDato() throws Exception { return (BizColCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
