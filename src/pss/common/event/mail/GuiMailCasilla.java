package pss.common.event.mail;

import pss.common.security.BizUsuario;
import pss.core.services.records.JBaseRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiMailCasilla extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMailCasilla() throws Exception {
  }


	@Override
	public JBaseRecord ObtenerBaseDato() throws Exception {
		return new BizMailCasilla();
	}

  @Override
	public int     GetNroIcono() throws Exception  { return 63; }
  @Override
	public String  GetTitle()    throws Exception  { return "Casilla"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormMailCasilla.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField()                { return "mail_from"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
    this.addAction(10, "Probar Envio", null, 63, true, true);
    this.addAction(11, "Probar Recibir", null, 63, true, true);
    this.addAction(30, "Habilitar", null, 49, true, true);
    this.addAction(32, "Deshabilitar", null, 48, true, true);
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizMailCasilla GetcDato() throws Exception {
    return (BizMailCasilla) this.getRecord();
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==3) return BizUsuario.getUsr().isAnyAdminUser();
  	if (a.getId()==30) return !this.GetcDato().isOK();
  	if (a.getId()==32) return this.GetcDato().isOK();
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActSubmit(this) { 
  		public void execSubmit() throws Exception {
  			GetcDato().processEnvioTest();
  		}
  	};
		if (a.getId()==11) return new JActSubmit(this) {
				public void submit() throws Exception {
	  			GetcDato().testReceive();
				}
		};
  	if (a.getId()==30) return new JActSubmit(this) { 
  		public void execSubmit() throws Exception {
  			GetcDato().processHabilitar();
  		}
  	};
  	if (a.getId()==32) return new JActSubmit(this) { 
  		public void execSubmit() throws Exception {
  			GetcDato().processDesHabilitar();
  		}
  	};

  	return super.getSubmitFor(a);
  }


}
