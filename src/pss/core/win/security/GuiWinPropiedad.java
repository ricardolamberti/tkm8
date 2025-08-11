package pss.core.win.security;

import pss.common.security.GuiListOpers;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;


public class GuiWinPropiedad extends JWin {

  public GuiWinPropiedad() throws Exception {
    super();
    setRecord(new BizWinPropiedad());
    SetNroIcono( 43 );
  }
  
  @Override
	public Class<? extends JBaseForm> getFormBase() {
    return FormWinPropiedad.class;
  }
  
  @Override
	public String GetTitle() {
    return "Propiedades";
  }

  @Override
	public boolean acceptsSecurityAction() {
    return false;
  }

  @Override
	public void createActionMap() throws Exception {
//    addAction(3, "Cambiar Icono", new JAct() { @Override
//		public void Do() throws Exception { FormCambioIcono(); }}, 50, true, true);
    addAction(10, "Funciones", null, 1, false, false);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(GetAccionesLista());
  	return super.getSubmitFor(a);
  }

  public BizWinPropiedad GetcDato() throws Exception {
    return (BizWinPropiedad) this.getRecord();
  }

//  public void FormCambioIcono() throws Exception {
//    GuiIconos.GetGlobal().buscarIcono(((BizWinPropiedad)getRecord()).pNroIcono.getValue()).showQueryForm();
//  }

  public JWins GetAccionesLista() throws Exception {
    GuiListOpers oActions = new GuiListOpers();
    oActions.setRecords(GetcDato().getAccionesLista());
    return oActions;
  }
  
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }


}



