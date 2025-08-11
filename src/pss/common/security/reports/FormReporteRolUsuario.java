package  pss.common.security.reports;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.common.security.GuiRoles;
import pss.common.security.GuiUsuarios;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormReporteRolUsuario extends JBaseForm {
  JComboBox ROL = new JComboBox();
  JComboBox Descripcion = new JComboBox();
  JComboBox USUARIO = new JComboBox();
  JPssLabel jLabel1 = new JPssLabel();
  JPssLabel jLabel2 = new JPssLabel();
  private JComboBox ordenamiento = new JComboBox();
  private JPssLabel jLabel3 = new JPssLabel();

  public FormReporteRolUsuario() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(362, 132));
    this.setLayout(null);
    ROL.setBounds(new Rectangle(151, 18, 175, 21));
    USUARIO.setBounds(new Rectangle(90, 150, 125, 21));
    USUARIO.setBounds(new Rectangle(151, 54, 175, 21));
    jLabel1.setText("Nombre de Rol:");
    jLabel1.setBounds(new Rectangle(22, 20, 99, 17));
    jLabel2.setText("Nombre de Usuario");
    jLabel2.setBounds(new Rectangle(22, 56, 113, 17));
    ordenamiento.setBounds(new Rectangle(151, 54, 175, 21));
    ordenamiento.setBounds(new Rectangle(151, 88, 175, 21));
    jLabel3.setBounds(new Rectangle(22, 90, 113, 17));
    jLabel3.setText("Ordenar por:");
    this.add(USUARIO, null);
    this.add(jLabel1, null);
    this.add(ROL, null);
    this.add(jLabel2, null);
    this.add(ordenamiento, null);
    this.add(jLabel3, null);
    this.add(USUARIO, null);
  }

//  private GuiReporteRolUsuario GetWin() throws Exception {
//    return (GuiReporteRolUsuario) GetBaseWin();
//  }


  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetReportMode();
    JFormControl oCtrl = AddItem( ROL, CHAR, OPT,  "rol" , new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return new GuiRoles();}});
    oCtrl.SetPermitirTodo(true);
    oCtrl.SetValorDefault("");
    oCtrl = AddItem( USUARIO  , CHAR, OPT,  "usuario", new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return new GuiUsuarios();}});
    oCtrl.SetPermitirTodo(true);
    oCtrl.SetValorDefault("");
    AddItem(ordenamiento, CHAR, REQ, "ordenamiento", new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return JWins.CreateVirtualWins(BizReporteRolUsuario.getOrdenamientos());}});
  }


}

