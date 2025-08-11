package pss.common.event.mail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMailSender extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMailSender() throws Exception {
    setRecord           ( new BizMailSender() );
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 43; }
  @Override
	public String  GetTitle()    throws Exception  { return "Mail Configuración"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormMailSender.class; }
  @Override
	public String  getKeyField() throws Exception { return "ID"; }
  @Override
	public String  getDescripField()                { return "MAIL_from"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizMailSender GetcDato() throws Exception {
    return (BizMailSender) this.getRecord();
  }


}
