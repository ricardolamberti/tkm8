package  pss.common.security;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssPasswordEdit;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class FormAutorizar extends JBaseForm {
  Border border1;
  JPssPasswordEdit Password = new JPssPasswordEdit();
  JPssEdit Login = new JPssEdit();
  JPssLabel jLabel2 = new JPssLabel();
  JPssLabel jLabel1 = new JPssLabel();

  public FormAutorizar() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void jbInit() throws Exception {
    jLabel1.setBounds(new Rectangle(14, 18, 82, 17));
    jLabel1.setText("Usuario");
    jLabel2.setBounds(new Rectangle(14, 59, 84, 17));
    jLabel2.setText("Clave");
    Login.setText(null);
    Login.setBounds(new Rectangle(104, 16, 168, 21));
    Password.setText(null);
    Password.setBounds(new Rectangle(104, 54, 168, 21));
    border1 = BorderFactory.createEmptyBorder();
    this.setLayout(null);
    setSize(new Dimension(310, 106));
    this.add(Login, null);
    this.add(jLabel1, null);
    this.add(jLabel2, null);
    this.add(Password, null);

  }

  public GuiAutorizar GetWin() throws Exception {
    return (GuiAutorizar) GetBaseWin();

  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    SetExitOnOk(true);
//    SetConfirmarAplicar(false);
    setModal(true);
  	
    jLabel1.setText("Usuario");
    jLabel2.setText("Clave");

    AddItem( Login    , CHAR, REQ, "login"    );
    AddItem( Password , CHAR, OPT, "password"  );
  }


}

