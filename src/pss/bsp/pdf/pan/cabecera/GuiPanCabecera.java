package  pss.bsp.pdf.pan.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPanCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPanCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPanCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPanCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizPanCabecera GetcDato() throws Exception { return (BizPanCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
