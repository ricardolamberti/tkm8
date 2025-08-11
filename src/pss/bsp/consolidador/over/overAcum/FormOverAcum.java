package pss.bsp.consolidador.over.overAcum;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormOverAcum extends JBaseForm {


private static final long serialVersionUID = 1247630958026L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lowner = new JPssLabel();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidpdf = new JPssLabel();
JPssEdit idpdf = new JPssEdit  ();
JPssLabel lid_aerolinea = new JPssLabel();
JPssEdit id_aerolinea = new JPssEdit  ();
JPssLabel lcount_boletos = new JPssLabel();
JPssEdit count_boletos = new JPssEdit  ();
JPssLabel lsum_over_ped = new JPssLabel();
JPssEdit sum_over_ped = new JPssEdit  ();
JPssLabel lsum_over_rec = new JPssLabel();
JPssEdit sum_over_rec = new JPssEdit  ();
JPssLabel lsum_dif = new JPssLabel();
JPssEdit sum_dif = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormOverAcum() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiOverAcum getWin() { return (GuiOverAcum) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+176));


    lcompany.setText( "Company" );
    lcompany.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    company.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lcompany, null);
    add(company , null);


    lowner.setText( "Owner" );
    lowner.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    owner.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lowner, null);
    add(owner , null);


    lidpdf.setText( "Idpdf" );
    lidpdf.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    idpdf.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lidpdf, null);
    add(idpdf , null);


    lid_aerolinea.setText( "Id aerolinea" );
    lid_aerolinea.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    id_aerolinea.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(lid_aerolinea, null);
    add(id_aerolinea , null);


    lcount_boletos.setText( "Count boletos" );
    lcount_boletos.setBounds(new Rectangle(40, 44+108, 123, 22)); 
    count_boletos.setBounds(new Rectangle(168, 44+108, 143, 22)); 
    add(lcount_boletos, null);
    add(count_boletos , null);


    lsum_over_ped.setText( "Sum over ped" );
    lsum_over_ped.setBounds(new Rectangle(40, 44+135, 123, 22)); 
    sum_over_ped.setBounds(new Rectangle(168, 44+135, 143, 22)); 
    add(lsum_over_ped, null);
    add(sum_over_ped , null);


    lsum_over_rec.setText( "Sum over rec" );
    lsum_over_rec.setBounds(new Rectangle(40, 44+162, 123, 22)); 
    sum_over_rec.setBounds(new Rectangle(168, 44+162, 143, 22)); 
    add(lsum_over_rec, null);
    add(sum_over_rec , null);


    lsum_dif.setText( "Sum dif" );
    lsum_dif.setBounds(new Rectangle(40, 44+189, 123, 22)); 
    sum_dif.setBounds(new Rectangle(168, 44+189, 143, 22)); 
    add(lsum_dif, null);
    add(sum_dif , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
//    AddItem( owner, CHAR, REQ, "owner" );
    AddItem( idpdf, CHAR, REQ, "idpdf" );
    AddItem( id_aerolinea, CHAR, REQ, "id_aerolinea" );
    AddItem( count_boletos, UFLOAT, REQ, "count_boletos" );
    AddItem( sum_over_ped, UFLOAT, REQ, "sum_over_ped" );
    AddItem( sum_over_rec, UFLOAT, REQ, "sum_over_rec" );
    AddItem( sum_dif, UFLOAT, REQ, "sum_dif" );

  } 
} 
