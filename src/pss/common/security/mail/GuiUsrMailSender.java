package  pss.common.security.mail;

import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiUsrMailSender extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiUsrMailSender() throws Exception {
    setRecord           ( new BizUsrMailSender() );
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 43; }
  @Override
	public String  GetTitle()    throws Exception  { return "Mail Configuración"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormUsrMailSender.class; }
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
		addAction(10, "Resetear fecha", null, 10026, true, true).setMulti(true);
		addAction(11, "Test", null, 10026, true, true);
		addAction(12, "Test", null, 10026, true, true);
		addAction(20, "Suspender", null, 10026, true, true).setMulti(true);
		addAction(30, "Ejecutar", null, 10026, true, true).setMulti(true);

  }
@Override
public boolean OkAction(BizAction a) throws Exception {
	if (a.getId()==11) return GetcDato().isReceiver();
	if (a.getId()==12) return GetcDato().isSender();
	if (a.getId()==20) return GetcDato().isOK();
	if (a.getId()==30) return !GetcDato().isOK();
	return super.OkAction(a);
}
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizUsrMailSender GetcDato() throws Exception {
    return (BizUsrMailSender) this.getRecord();
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActSubmit(this) {
			public void submit() throws Exception {GetcDato().execProcesarResetearFecha();}
		};
		if (a.getId()==11) return new JActSubmit(this) {
			public void submit() throws Exception {GetcDato().testReceive();throw new Exception("Recepcion del email OK");}
		};
		if (a.getId()==12) return new JActSubmit(this) {
			public void submit() throws Exception {GetcDato().testSend();}
		};
		if (a.getId()==20) return new JActSubmit(this) {
			public void submit() throws Exception {GetcDato().execProcesarChangeEstado(BizUsrMailSender.SUSPUSUARIO);}
		};
		if (a.getId()==30) return new JActSubmit(this) {
			public void submit() throws Exception {GetcDato().execProcesarChangeEstado(BizUsrMailSender.OK);}
		};
		return super.getSubmitFor(a);
  }

}
