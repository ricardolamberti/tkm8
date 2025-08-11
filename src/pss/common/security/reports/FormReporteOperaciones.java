package  pss.common.security.reports;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;

import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormReporteOperaciones extends JBaseForm {
  JComboBox Descripcion = new JComboBox();
  JComboBox ComboOrden = new JComboBox();
  JPssLabel jLabel1 = new JPssLabel();


  public FormReporteOperaciones() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(377, 69));
    jLabel1.setText("Ordenamiento: ^");
    jLabel1.setBounds(new Rectangle(12, 28, 99, 17));

    ComboOrden.setBounds(new Rectangle(108, 26, 188, 21));
    ComboOrden.addItem(BizReporteOperaciones.vORDEN[0]);
    ComboOrden.addItem(BizReporteOperaciones.vORDEN[1]);
    ComboOrden.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        Orden_itemStateChanged(e);
      }
    });

    this.setLayout(null);
    this.add(jLabel1, null);
    this.add(ComboOrden, null);
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetReportMode();
    ComboOrden.setSelectedIndex(1);
  }



  public void Orden_itemStateChanged(ItemEvent e) {
    try{
      //String sOrden= new String();
      //sOrden= (String)ComboOrden.getSelectedItem();
      ((BizReporteOperaciones)this.oWin.getRecord()).setOrden(ComboOrden.getSelectedIndex());
    }
    catch( Exception ex ){
    }
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

}

