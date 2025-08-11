package  pss.common.security.reports;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.common.security.GuiUsuarios;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormReporteUsuario extends JBaseForm {
//  JComboBox Descripcion = new JComboBox();
  JComboBox ComboOrden = new JComboBox();
  JPssLabel jLabel1 = new JPssLabel();
  JPssLabel Usr = new JPssLabel();
  JComboBox COMBOUSR = new JComboBox();

  public FormReporteUsuario() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(358, 120));

    jLabel1.setText("Ordenar por");
    jLabel1.setBounds(new Rectangle(28, 61, 77, 17));

    ComboOrden.setBounds(new Rectangle(120, 59, 204, 21));
//    ComboOrden.addItem(BizReporteUsuario.vORDEN[0]);
//    ComboOrden.addItem(BizReporteUsuario.vORDEN[1]);
//    ComboOrden.addItemListener(new java.awt.event.ItemListener() {
//      public void itemStateChanged(ItemEvent e) {
//        Orden_itemStateChanged(e);
//      }
//    });

    Usr.setText("Usuario");
    Usr.setBounds(new Rectangle(28, 28, 59, 17));
    COMBOUSR.setBounds(new Rectangle(120, 26, 204, 21));

    this.setLayout(null);
    this.add(Usr, null);
    this.add(jLabel1, null);
    this.add(COMBOUSR, null);
    this.add(ComboOrden, null);

  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	this.SetReportMode();
    JFormControl oCtrl = AddItem( COMBOUSR, CHAR, OPT,  "usuario",new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return new GuiUsuarios();}});
    oCtrl.SetPermitirTodo(true);
    oCtrl.SetValorDefault("");
    this.AddItem( ComboOrden  , CHAR, REQ,  "ordenamiento",new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return JWins.CreateVirtualWins(BizReporteUsuario.getOrdenamientos());}});
    
//    oCtrl.SetValorDefault("");
 //   ComboOrden.setSelectedIndex(1);
  }

//  public void WebPostBind( JPssSession session, JPssRequest request, JWin win, String modo ) throws Exception {
//    if( session.getFormatoReportes().equals( JConstantes.REPORTES_HTML ) ) {
//      this.SetAccionAplicar( win.findAction( JConstantes.Pss_WIN_ACCION_PROCESAR_REPORTE_HTML ) );
//    } else if( session.getFormatoReportes().equals( JConstantes.REPORTES_PDF ) ) {
//      this.SetAccionAplicar( win.findAction( JConstantes.Pss_WIN_ACCION_PROCESAR_REPORTE_PDF ) );
//    } else {
//      JExcepcion.SendError( "No se reconoce el formato de reportes '" + session.getFormatoReportes() + "' como válido." );
//    }
//    this.SetAccionCancelar( null );
//  }
/*  
  public void Orden_itemStateChanged(ItemEvent e) {
    try{
//      String sOrden= new String();
//      sOrden= (String)ComboOrden.getSelectedItem();
      ((BizReporteUsuario)this.oWin.getRecord()).setOrden(ComboOrden.getSelectedIndex());
    }
    catch( Exception ex ){
    }
  }
*/
 }

