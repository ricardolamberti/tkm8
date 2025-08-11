package pss.bsp.contrato.detalleAvianca.objetivos;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import pss.core.ui.components.JPssLabel;

public class FormObjetivosAvianca extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;

  /**
   * Propiedades de la Clase
   */
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel lvariacion = new JPssLabel();
JPssEdit variacion = new JPssEdit  ();
JPssLabel lfecha_hasta = new JPssLabel();
JPssCalendarEdit fecha_hasta = new JPssCalendarEdit  ();
JPssLabel lfecha_desde = new JPssLabel();
JPssCalendarEdit fecha_desde = new JPssCalendarEdit  ();
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssEdit idC = new JPssEdit  ();
JPssEdit lineaC = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();

private JPssLabel lvariacion1 = null;


  /**
   * Constructor de la Clase
   */
  public FormObjetivosAvianca() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiObjetivosAvianca getWin() { return (GuiObjetivosAvianca) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lvariacion1 = new JPssLabel();
    lvariacion1.setBounds(new Rectangle(313, 72, 19, 20));
    lvariacion1.setText("%");
    setLayout(null);
    setSize(new Dimension(357, 110+110));


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    descripcion.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);


    lvariacion.setText( "Variacion" );
    lvariacion.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    variacion.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lvariacion, null);
    add(variacion , null);


    lfecha_hasta.setText( "Fecha hasta" );
    lfecha_hasta.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    fecha_hasta.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lfecha_hasta, null);
    add(fecha_hasta , null);


    lfecha_desde.setText( "Fecha desde" );
    lfecha_desde.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    fecha_desde.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(lfecha_desde, null);
    add(fecha_desde , null);



    this.add(lvariacion1, null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( descripcion, CHAR, REQ, "descripcion" );
    AddItem( variacion, FLOAT, REQ, "variacion" );
    AddItem( fecha_hasta, DATE, REQ, "fecha_hasta" );
    AddItem( fecha_desde, DATE, REQ, "fecha_desde" );
    AddItem( linea, UINT, OPT, "linea" );
    AddItem( id, UINT, OPT, "id" );
    AddItem( idC, UINT, OPT, "id_contrato" );
    AddItem( lineaC, UINT, OPT, "linea_contrato" );
    AddItem( company, UINT, OPT, "company" );

  } 
} 
