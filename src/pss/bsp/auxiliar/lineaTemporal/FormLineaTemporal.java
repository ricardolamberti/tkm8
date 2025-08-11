package  pss.bsp.auxiliar.lineaTemporal;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.security.BizUsuario;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssCalendarEdit;

public class FormLineaTemporal extends JBaseForm {


private static final long serialVersionUID = 1245258187718L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idInterfaz = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssLabel ltipo_ruta = new JPssLabel();
JPssEdit tipo_ruta = new JPssEdit  ();
JPssLabel laerolinea = new JPssLabel();
JPssEdit aerolinea = new JPssEdit  ();
JPssLabel lfecha = new JPssLabel();
JPssCalendarEdit fecha = new JPssCalendarEdit  ();

private JPssLabel lfecha1 = null;

private JPssCalendarEdit fecha1 = null;
/**
   * Constructor de la Clase
   */
  public FormLineaTemporal() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiLineaTemporal getWin() { return (GuiLineaTemporal) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lfecha1 = new JPssLabel();
    lfecha1.setBounds(new Rectangle(15, 116, 123, 22));
    lfecha1.setText("Fecha hasta");
    this.setLayout(null);
    this.setSize(new Dimension(547, 146));
  

 


    ltipo_ruta.setText("Campaña");
    ltipo_ruta.setBounds(new Rectangle(15, 29, 123, 22)); 
    tipo_ruta.setBounds(new Rectangle(143, 29, 143, 22)); 
    add(ltipo_ruta, null);
    add(tipo_ruta , null);


    laerolinea.setText("Segmento");
    laerolinea.setBounds(new Rectangle(15, 56, 123, 22)); 
    aerolinea.setBounds(new Rectangle(143, 56, 378, 22)); 
    add(laerolinea, null);
    add(aerolinea , null);


    lfecha.setText("Fecha desde");
    lfecha.setBounds(new Rectangle(15, 87, 123, 22)); 
    fecha.setBounds(new Rectangle(143, 87, 143, 22)); 
    add(lfecha, null);
    add(fecha , null);


    this.add(lfecha1, null);
    this.add(getFecha1(), null);
    
    
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());

    AddItem( fecha, DATE, REQ, "fecha_serie" );
  
  }

	/**
	 * This method initializes fecha1	
	 * 	
	 * @return pss.core.ui.components.JPssCalendarEdit	
	 */
	private JPssCalendarEdit getFecha1() {
		if (fecha1 == null) {
			fecha1 = new JPssCalendarEdit();
			fecha1.setBounds(new Rectangle(143, 116, 143, 22));
		}
		return fecha1;
	} 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
