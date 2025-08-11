package  pss.common.layoutWysiwyg.permisos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiOwnerPlantilla extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiOwnerPlantilla() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizOwnerPlantilla(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Relacion Tipo documento-Expediente"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
return FormOwnerPlantilla.class; 
  }
  public String  getKeyField() throws Exception { return "id_plantilla"; }
  public String  getDescripField() { 
    	return "nombre"; 
  }
  public BizOwnerPlantilla GetcDato() throws Exception { return (BizOwnerPlantilla) this.getRecord(); }

 }
