package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiLoginTrace extends JWin {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //private static boolean bUserLogedIn;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//private JBaseForm oForm;
  //private boolean bLoginWasAccepted;
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public GuiLoginTrace() throws Exception {
  }
 
  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizLoginTrace(); }
  @Override
	public int GetNroIcono()       throws Exception { return GuiIcon.LOGIN_ICON; }
  @Override
	public String GetTitle()       throws Exception { return "Ingreso a la Aplicación"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormLoginTrace.class; }


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   superclass methods overridding
  //
  @Override
	public void createActionMap() throws Exception {
//    addActionQuery(1, "Ingresar" );
  }
  
  @Override
	public JAct getSubmit(BizAction a) throws Exception {
  	if (a.getId()==0) return new JActNew(this, 0) {
  		@Override
			public JAct nextAction() throws Exception { updateGlobal(); return null;}  		
  		@Override
			public JAct nextAction(Exception e) throws Exception { return getNextErrorAction(e);}
  	};
  	return null;
  }
  public BizLoginTrace GetcDato() throws Exception {
    return (BizLoginTrace) this.getRecord();
  }
  
  private JAct getNextErrorAction(Exception e) throws Exception {
  	if ( e instanceof BlankPasswordException) return new JActNew(this.getNewCambioPassword(), 0);
  	if ( e instanceof ExpiredPasswordException) return new JActNew(this.getNewCambioPassword(), 0);  	
  	return null;
  }

  private JWin getNewCambioPassword() throws Exception {
    JWin oCambioPassword = BizUsuario.getUsr().getObjBusiness().buildCambioClave(this.GetcDato().getLogin(),this.GetcDato().getObjUsuario().GetDescrip(),this.GetcDato().getObjUsuario().getPasswordDecrypt(),null,null);
//    oCambioPassword.GetcDato().pLogin.setValue(this.GetcDato().getLogin());
//    oCambioPassword.GetcDato().pDescrip.setValue(this.GetcDato().getObjUsuario().GetDescrip());
//    oCambioPassword.GetcDato().pPassActual.setValue(this.GetcDato().getObjUsuario().getPasswordDecrypt());
//    oCambioPassword.GetcDato().Read( GetcDato().GetUsuario() );
    return oCambioPassword;
  }
  
  //
  //  API
  //
//  public void FormLogIn() throws Exception {
//    this.FormLogIn(false);
//  }
//  public void FormLogIn() throws Exception {
//    try {
//      //this.bLoginWasAccepted = false;
//
//      boolean isMarkingSteps = this.isFirstLogIn() && UITools.desktop().isDesktopActive();
//
//      if (isMarkingSteps) {
//        JDebugPrint.logWait("Creando form de inicio de sesión...");
//      }
//
//      oForm = this.createNewForm(this.getSubmitFor(0));
//
//      if (isMarkingSteps) {
//        JDebugPrint.logInfo("Form creado");
//        UITools.desktop().getWaitingPane().markNextStep();
//      }
//
//      oForm.setModal(true);
//      oForm.Show();
//
//      this.updateGlobal();
//
//    } catch ( Exception ex ) {
//      JDebugPrint.logError( ex );
//      UITools.MostrarError(ex);
//    }
//  }
  
//  public JAct createProcessLogIn() throws Exception {
//		return new JAct() {
//	  	public void submit() throws Exception {
//	  		confirmAndLogIn();
//	  	}
//	  };
//	}

//  public void loginAsGUEST() throws Exception {
//    loginAsGUEST(true);
//  }

//  public void loginAsGUEST(boolean zConfirm) throws Exception {
//    //this.bLoginWasAccepted = false;
//    // log in as GUEST, and take the BizUsuario object
//    this.logoutAndLoginAsGUEST(zConfirm);
//    this.updateGlobal();
//  }

  /**
   * Login refreshing the desktop LEO LA CONCHA DE TU MAMI COMENTA ALGO!!!!!
   */
//  public void login(String zUser, String zPassword) throws Exception {
//    this.bLoginWasAccepted = false;
//    oRetUsr = this.GetcDato().login(zUser, zPassword);
//    this.bLoginWasAccepted = true;
//    this.updateGlobal();
//  }


//  public void setGlobalUserAfterLogInFailed() throws Exception {
//    this.setGlobalUserWin();
//  }
//  public void setGlobalUserAfterLogInSucceeded() throws Exception {
////    this.setGlobalUserWin();
//    bUserLogedIn = true;
//  }

  //
  //   login and re-login implementations
  //
//  private boolean isFirstLogIn() throws Exception {
//    return !bUserLogedIn;
//  }
//  private String getMessageLogIn() throws Exception {
//    if (this.isFirstLogIn() || !UITools.desktop().isDesktopActive()) return "";
//    String currUser = null;
//    if (BizUsuario.GetGlobal()!=null) {
//      currUser = BizUsuario.GetGlobal().GetUsuario();
//    }
//    String newUser = this.GetcDato().pLogin.getValue();
//    currUser = currUser==null ? "" : currUser.trim();
//    newUser = newUser==null ? "" : newUser.trim();
//    String msg = null;
//    if (currUser.equalsIgnoreCase(newUser)) {
//      msg = "Se cerrarán todas las ventanas abiertas y se cargará su perfil nuevamente. ¿Desea continuar?";
//    } else if (!newUser.equals("")) {
//      msg = "Se cerrarán todas las ventanas abiertas y se cargará el perfil para el usuario {"
//            + newUser + "}. ¿Desea continuar?";
//    }
////      if (msg!=null && UITools.MostrarYesNoMensaje("Inicio de sesión", msg) != JOptionPane.YES_OPTION) {
//    return msg;
////      }
//  }
//    BizUsuario oUsr=GetcDato().ExecLogIn();
//    GetcDato().ExecprocessInsert();

//    return oUsr;
  
//  private void logoutAndLoginAsGUEST(boolean zConfirm) throws Exception {
//    if (zConfirm && !this.isFirstLogIn() && UITools.desktop().isDesktopActive()) {
//      int answer = UITools.MostrarYesNoMensaje("Cierre de sesión", "Se cerrarán todas las ventanas abiertas. ¿Desea continuar?");
//      if (answer != JOptionPane.YES_OPTION) {
//        return;
//      }
//    }
//    GetcDato().execLogInAsGuest();
//    //this.bLoginWasAccepted = true;
////    return oUsr;
//  }

  private void updateGlobal() throws Exception {
    //if (!this.bLoginWasAccepted) return;

//    BizUsuario.SetGlobal(GetcDato().getLastLogedUser());
//    this.setGlobalUserWin();
//    if (UITools.desktop().isDesktopActive()) {
//      UITools.desktop().reloadForCurrentUser(false);
//    }
    //bUserLogedIn = true;
  }
  
  public static GuiLoginTrace createLoginGuest() throws Exception {
  	GuiLoginTrace login = new GuiLoginTrace();
  	login.SetBaseDato(BizLoginTrace.createLoginGuest());
  	return login;
  }
  


}
