package pss.common.event.mail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiNewMailSender extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNewMailSender() throws Exception {
    setRecord           ( new BizMailCasilla() );
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 43; }
  @Override
	public String  GetTitle()    throws Exception  { return "Enviar Mail"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormNewMailSender.class; }
  @Override
	public String  getKeyField() throws Exception { return "ID"; }
  @Override
	public String  getDescripField()                { return "MAIL_subject"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizMailCasilla GetcDato() throws Exception {
    return (BizMailCasilla) this.getRecord();
  }


}
