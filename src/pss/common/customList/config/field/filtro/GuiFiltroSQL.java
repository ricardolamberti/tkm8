package  pss.common.customList.config.field.filtro;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiFiltroSQL extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFiltroSQL() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFiltroSQL(); }
  public int GetNroIcono()   throws Exception { return 10063; }
  public String GetTitle()   throws Exception { return "Filtro SQL"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  		return FormFiltroSQL.class; 
  }
  public String  getKeyField() throws Exception { return "filtro"; }
  public String  getDescripField() { return "filtro_nombre"; }
  public BizFiltroSQL GetcDato() throws Exception { return (BizFiltroSQL) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		super.createActionMap();
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }
  
 }
