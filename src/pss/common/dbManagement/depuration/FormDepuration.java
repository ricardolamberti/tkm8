package  pss.common.dbManagement.depuration;

import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormDepuration extends JBaseForm {

  public FormDepuration() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetExitOnOk(true);
    AddItemEdit("Pais",CHAR,REQ,"pais");
    AddItemCombo("Modulo",CHAR,REQ,"modulo", JWins.CreateVirtualWins(JPurgeList.getPurgeSets()));
    AddItemCombo("Entidad",CHAR,REQ,"entidad", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean zOneRow) throws Exception { 
    		return ObtenerEntidades();}
    	}
    );
    AddItemEdit("DiasSinDepurar",UINT,REQ,"dias_sin_depurar");
  }

  private JWins ObtenerEntidades() throws Exception {
    String purgeSet = getControles().findControl("modulo").getValue();
    if (purgeSet.equals("")) {
      return JWins.CreateVirtualWins(JRecords.createVirtualBDs());
    }
    return JWins.CreateVirtualWins(JPurgeList.getPurgeList(purgeSet));
  }

}

