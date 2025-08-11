package pss.bsp.pais;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormPais extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lpais = new JPssLabel();
JPssEdit pais = new JPssEdit  ();
JPssLabel lid_parser_pdf = new JPssLabel();
JPssEdit id_parser_pdf = new JPssEdit  ();
JPssLabel lid_parser_ho = new JPssLabel();
JPssEdit id_parser_ho = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormPais() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPais getWin() { return (GuiPais) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+66));


    lpais.setText( "Pais" );
    lpais.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    pais.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lpais, null);
    add(pais , null);


    lid_parser_pdf.setText( "Id parser pdf" );
    lid_parser_pdf.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    id_parser_pdf.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lid_parser_pdf, null);
    add(id_parser_pdf , null);


    lid_parser_ho.setText( "Id parser ho" );
    lid_parser_ho.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    id_parser_ho.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lid_parser_ho, null);
    add(id_parser_ho , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( pais, CHAR, REQ, "pais" );
    AddItem( id_parser_pdf, CHAR, REQ, "id_parser_pdf" );
    AddItem( id_parser_ho, CHAR, REQ, "id_parser_ho" );

  } 
} 
