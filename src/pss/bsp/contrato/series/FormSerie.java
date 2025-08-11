package pss.bsp.contrato.series;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormSerie extends JBaseForm {


private static final long serialVersionUID = 1446860104669L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lvalor = new JPssLabel();
JPssEdit valor = new JPssEdit  ();
JPssLabel lperiodo = new JPssLabel();
JPssEdit periodo = new JPssEdit  ();
JPssLabel lvariable = new JPssLabel();
JPssEdit variable = new JPssEdit  ();
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormSerie() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiSerie getWin() { return (GuiSerie) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+88));


    lvalor.setText( "Valor" );
    lvalor.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    valor.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lvalor, null);
    add(valor , null);


    lperiodo.setText( "Periodo" );
    lperiodo.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    periodo.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lperiodo, null);
    add(periodo , null);


    lvariable.setText( "Variable" );
    lvariable.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    variable.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lvariable, null);
    add(variable , null);



  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( valor, UFLOAT, REQ, "valor" );
    AddItem( periodo, CHAR, REQ, "periodo" );
    AddItem( variable, CHAR, REQ, "variable" );
    AddItem( id, UINT, OPT, "id" );

  } 
} 
