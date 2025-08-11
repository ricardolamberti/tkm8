package  pss.bsp.bo.gen.cabecera;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiGenCabecera extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiGenCabecera() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizGenCabecera(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Interfaz BackOffice"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormGenCabecera.class; }
  public String  getKeyField() throws Exception { return "idInterfaz"; }
  public String  getDescripField() { return "id_formato"; }
  public BizGenCabecera GetcDato() throws Exception { return (BizGenCabecera) this.getRecord(); }

  public void createActionMap() throws Exception {
		createActionQuery();
	}

 }
