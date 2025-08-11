package  pss.common.agenda.participantes;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormEventoParticipante extends JBaseForm {


private static final long serialVersionUID = 1352315123161L;



  /**
   * Constructor de la Clase
   */
  public FormEventoParticipante() throws Exception {
  }

  public GuiEventoParticipante getWin() { return (GuiEventoParticipante) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "id_evento" ).setHide(true);
    AddItemWinLov( "Persona", UINT, REQ, "id_persona" )/*, new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getPersonas(bOneRow);
    	}
    })*/;
    AddItemWinLov( "Rol en el evento", CHAR, OPT, "rol" )/*, new JControlWin() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getRoles(one);
    	}
    })*/;
    AddItemCombo( "Estado", CHAR, OPT, "estado")/* , JWins.createVirtualWinsFromMap(BizEventoParticipante.getEstados()))*/;

  } 
  
//  JWins getRoles(boolean one) throws Exception {
//  	JWins wins = new GuiEventoRoles();
//  	wins.setModeWinLov(true);
//  	wins.getRecords().addFilter("company", getWin().GetcDato().getCompany());
//  	if (one) wins.getRecords().addFilter("id_rol", getWin().GetcDato().getRol());
//  	return wins;
//  }
//  JWins getPersonas(boolean one) throws Exception {
//  	JWins wins = new GuiPersonas();
//  	wins.setModeWinLov(true);
////  	wins.getRecords().addFilter("company", getWin().GetcDato().getCompany());
//  	if (one) wins.getRecords().addFilter("persona", getWin().GetcDato().getIdpersona());
//  	return wins;
//  }
}  //  @jve:decl-index=0:visual-constraint="10,10" 
