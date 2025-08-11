package  pss.common.backup.settings;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiBackUpGroupDetailCriterio extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBackUpGroupDetailCriterio() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBackUpGroupDetailCriterio(); }
  public int GetNroIcono()   throws Exception { return 10052; }
  public String GetTitle()   throws Exception { return "Criterio"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBackUpGroupDetailCriterio.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "field_name1"; }
  public BizBackUpGroupDetailCriterio GetcDato() throws Exception { return (BizBackUpGroupDetailCriterio) this.getRecord(); }

 }
