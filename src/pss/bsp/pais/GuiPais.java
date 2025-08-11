package  pss.bsp.pais;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPais extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPais() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPais(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Parseo por pais"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPais.class; }
  public String  getKeyField() throws Exception { return "pais"; }
  public String  getDescripField() { return "id_parser_pdf"; }
  public BizPais GetcDato() throws Exception { return (BizPais) this.getRecord(); }

 }
