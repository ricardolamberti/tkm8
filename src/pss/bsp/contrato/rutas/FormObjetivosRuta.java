package pss.bsp.contrato.rutas;

import java.awt.Dimension;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.forms.JBaseForm;
import javax.swing.JTabbedPane;

public class FormObjetivosRuta extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssEdit idC = new JPssEdit  ();
JPssEdit lineaC = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();

private JPssLabel lvariacion1 = null;
	JPssImage imagePane = new JPssImage();
  JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);


  /**
   * Constructor de la Clase
   */
  public FormObjetivosRuta() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiObjetivosRuta getWin() { return (GuiObjetivosRuta) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(553, 370));
    
    imagePane.setBounds(262, 11, 281, 348);
    add(imagePane);
    
    tabbedPane.setBounds(10, 11, 242, 348);
    add(tabbedPane);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, OPT, "company" );
    AddItem( idC, CHAR, OPT, "id" );
    AddItem( linea, CHAR, OPT, "linea" );

    JFormControl c=AddItem(tabbedPane,10);
    c.setKeepHeight(true);
    c.setKeepWidth(false);
    
    JFormImage image= AddItem( imagePane, 10, 1);
    image.setKeepHeight(true);
    image.setKeepWidth(true);
  } 
} 
