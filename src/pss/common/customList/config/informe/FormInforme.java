package pss.common.customList.config.informe;

import pss.common.customList.config.customlist.GuiCustomLists;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormInforme extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;


	/**
   * Constructor de la Clase
   */
  public FormInforme() throws Exception {
   }

  public GuiInforme getWin() { return (GuiInforme) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "informe" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "descripcion" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "orden" ).setHide(true);
    if (getWin().GetVision().equals("EMBEDDED"))
    	AddCardPanel(40).setWithActions(false);
    else
    	AddCardPanel(30).setDiferido(true).setWithActions(false);

  }
  @Override
  	public void OnPostShow() throws Exception {
  		super.OnPostShow();
  	}
  
  private JWins getInformes(boolean one) throws Exception {
	  return new GuiCustomLists().addFilterAdHoc("company", getWin().GetcDato().getCompany());
  }




}  //  @jve:decl-index=0:visual-constraint="-1,16"
