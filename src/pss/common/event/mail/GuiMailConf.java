package pss.common.event.mail;

import pss.common.documentos.docEmail.BizDocEmail;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMailConf extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMailConf() throws Exception {
    setRecord           ( new BizMailConf() );
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 10; }
  @Override
	public String  GetTitle()    throws Exception  { return "Servidor de Mail"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormMailConf.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField()                { return "Description"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  	this.addAction(380, "Casillas", null, 63, false, false);
  	this.addAction(400, "Start Listener", null, 63, true, true);
  	this.addAction(500, "Stop Listener", null, 63, true, true);
	}
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==3) return BizUsuario.getUsr().isAnyAdminUser();
  	if (a.getId()==400) return !BizDocEmail.isPolling() && BizUsuario.getUsr().isAnyAdminUser();
  	if (a.getId()==500) return BizDocEmail.isPolling() && BizUsuario.getUsr().isAnyAdminUser();
  	return super.OkAction(a);
  }

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {

  	if (a.getId()==380 ) return new JActWins(this.getCasillas());
  	if (a.getId()==400 ) return new JActSubmit(this) { 
  		public void submit() throws Exception {
  			BizDocEmail.startPollingThread();
  		}
  	};
  	if (a.getId()==500 ) return new JActSubmit(this) { 
  		public void submit() throws Exception {
  			BizDocEmail.stopPolling();
  		}
  	};
		return null;
	}

  public JWins getCasillas() throws Exception {
  	GuiMailCasillas	c = new GuiMailCasillas();
  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
  	c.getRecords().addFilter("ID_MAIL_CONF", this.GetcDato().getId());
  	return c;
  }



  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizMailConf GetcDato() throws Exception {
    return (BizMailConf) this.getRecord();
  }


}
