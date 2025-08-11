package  pss.bsp.pdf.bol.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiBolCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBolCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBolCabecera(); }
  public int GetNroIcono()   throws Exception { return 10042; }
  public String GetTitle()   throws Exception { return "Resultado Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBolCabecera.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizBolCabecera GetcDato() throws Exception { return (BizBolCabecera) this.getRecord(); }
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 }
