package  pss.common.personalData.personaRegistro;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPersonaRegistro extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPersonaRegistro() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPersonaRegistro(); }
  public int GetNroIcono()   throws Exception { return 10017; }
  public String GetTitle()   throws Exception { return "Registración"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPersonaRegistro.class; }
  public BizPersonaRegistro GetcDato() throws Exception { return (BizPersonaRegistro) this.getRecord(); }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
 }
