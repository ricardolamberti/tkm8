package pss.bsp.contrato.rutas.ms;

import java.awt.Dimension;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.forms.JBaseForm;

public class FormMS extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssEdit idC = new JPssEdit  ();
JPssEdit lineaC = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();

private JPssLabel lvariacion1 = null;
	JPssImage imagePane = new JPssImage();


  /**
   * Constructor de la Clase
   */
  public FormMS() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiMS getWin() { return (GuiMS) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(553, 370));
    
    imagePane.setBounds(10, 11, 533, 348);
    add(imagePane);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, OPT, "company" );
    AddItem( idC, CHAR, OPT, "id" );
    AddItem( linea, CHAR, OPT, "linea" );

    JFormImage image= AddItem( imagePane,  "image1" );
    image.setKeepHeight(true);
    image.setKeepWidth(true);
  } 
} 
