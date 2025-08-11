package  pss.bsp.pdf.ecu.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiEcuCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEcuCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEcuCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEcuCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizEcuCabecera GetcDato() throws Exception { return (BizEcuCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
