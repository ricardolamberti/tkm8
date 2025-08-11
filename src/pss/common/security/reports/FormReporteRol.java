package  pss.common.security.reports;

import java.awt.Dimension;

import javax.swing.JComboBox;

import pss.common.security.GuiRoles;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormReporteRol extends JBaseForm {
//  JComboBox Descripcion = new JComboBox();
  JComboBox ComboOrden = new JComboBox();

  JPssLabel jLabel1 = new JPssLabel();
  JPssLabel Rol = new JPssLabel();
  JComboBox COMBOROL = new JComboBox();

  public FormReporteRol() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(377, 128));
    jLabel1.setText("Ordenamiento");
    jLabel1.setBounds(new java.awt.Rectangle(18,68,99,17));

    ComboOrden.setBounds(new java.awt.Rectangle(129,67,228,21));
//    ComboOrden.addItem(BizReporteRol.vORDEN[0]);
//    ComboOrden.addItem(BizReporteRol.vORDEN[1]);
//    ComboOrden.addItemListener(new java.awt.event.ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        Orden_itemStateChanged(e);
//      }
//    });

    Rol.setText("Código de Rol");
    Rol.setBounds(new java.awt.Rectangle(18,30,104,14));
    COMBOROL.setBounds(new java.awt.Rectangle(129,27,228,21));
    this.setLayout(null);
    this.add(jLabel1, null);
    this.add(Rol, null);
    this.add(COMBOROL, null);
    this.add(ComboOrden, null);
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	this.SetReportMode();
  	this.AddItem( ComboOrden  , CHAR, REQ,  "ordenamiento", new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return JWins.CreateVirtualWins(BizReporteRol.getOrdenamientos());}});
    JFormControl oCtrl = AddItem( COMBOROL  , CHAR, OPT,  "Rol",new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return getRoles();}});
    oCtrl.SetPermitirTodo(true);
    oCtrl.SetValorDefault("");

//    ComboOrden.setSelectedIndex(1);
  }

  private GuiRoles getRoles()throws Exception{
    GuiRoles oRolesUsr = new GuiRoles();
    //oRolesUsr.SetItemDescrip("Rol");
//    if(this.findControl("orden").getValue().equals(String.valueOf(BizReporteRol.ORDEN_CODIGO)){
//      oRolesUsr.SetVision("descripcion");
//    }else{
//      oRolesUsr.SetVision("rol");
//    }
    return oRolesUsr;
  }
  

/*  public void Orden_itemStateChanged(ItemEvent e) {
    try{
      //String sOrden= new String();
      //sOrden= (String)ComboOrden.getSelectedItem();
      ((BizReporteRol)this.oWin.getRecord()).setOrden(ComboOrden.getSelectedIndex());
    }
    catch( Exception ex ){
    }
  }
*/
}

