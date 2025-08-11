package  pss.bsp.pdf.arg.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiArgCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiArgCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizArgCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormArgCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizArgCabecera GetcDato() throws Exception { return (BizArgCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
