package  pss.bsp.pdf.cri.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCRiCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCRiCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCRiCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCRiCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizCRiCabecera GetcDato() throws Exception { return (BizCRiCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
