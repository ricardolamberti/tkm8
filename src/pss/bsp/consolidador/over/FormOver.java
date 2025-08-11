package pss.bsp.consolidador.over;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormOver extends JBaseForm {


private static final long serialVersionUID = 1247605020960L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidpdf = new JPssLabel();
JPssEdit idpdf = new JPssEdit  ();
JPssLabel llinea = new JPssLabel();
JPssEdit linea = new JPssEdit  ();
JPssLabel lstatus = new JPssLabel();
JComboBox status = new JComboBox  ();
JPssLabel lboleto = new JPssLabel();
JPssEdit boleto = new JPssEdit  ();
JPssLabel lfecha = new JPssLabel();
JPssCalendarEdit fecha = new JPssCalendarEdit();
JPssLabel lid_aerolinea = new JPssLabel();
JPssEdit id_aerolinea = new JPssEdit  ();
JPssLabel lover_ped = new JPssLabel();
JPssEdit over_ped = new JPssEdit  ();
JPssLabel lover_rec = new JPssLabel();
JPssEdit over_rec = new JPssEdit  ();
JPssLabel ldif = new JPssLabel();
JPssEdit dif = new JPssEdit  ();
JPssLabel lobservaciones = new JPssLabel();
JPssEdit observaciones = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormOver() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiOver getWin() { return (GuiOver) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(450, 299));


    company.setBounds(new Rectangle(4, 9, 11, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(4, 36, 11, 22)); 
    add(owner , null);


    lidpdf.setText( "Id BSP" );
    lidpdf.setBounds(new Rectangle(24, 9, 123, 22)); 
    idpdf.setBounds(new Rectangle(152, 9, 143, 22)); 
    add(lidpdf, null);
    add(idpdf , null);


    llinea.setText( "Linea" );
    llinea.setBounds(new Rectangle(24, 36, 123, 22)); 
    linea.setBounds(new Rectangle(152, 36, 70, 22)); 
    add(llinea, null);
    add(linea , null);


    lstatus.setText( "Status" );
    lstatus.setBounds(new Rectangle(24, 63, 123, 22)); 
    status.setBounds(new Rectangle(152, 63, 143, 22)); 
    add(lstatus, null);
    add(status , null);


    lboleto.setText( "Boleto" );
    lboleto.setBounds(new Rectangle(24, 90, 123, 22)); 
    boleto.setBounds(new Rectangle(152, 90, 143, 22)); 
    add(lboleto, null);
    add(boleto , null);


    lfecha.setText( "Fecha" );
    lfecha.setBounds(new Rectangle(24, 117, 123, 22)); 
    fecha.setBounds(new Rectangle(152, 117, 143, 22)); 
    add(lfecha, null);
    add(fecha , null);


    lid_aerolinea.setText( "Aerolinea" );
    lid_aerolinea.setBounds(new Rectangle(24, 144, 123, 22)); 
    id_aerolinea.setBounds(new Rectangle(152, 144, 143, 22)); 
    add(lid_aerolinea, null);
    add(id_aerolinea , null);


    lover_ped.setText( "Over pedido" );
    lover_ped.setBounds(new Rectangle(24, 171, 123, 22)); 
    over_ped.setBounds(new Rectangle(152, 171, 143, 22)); 
    add(lover_ped, null);
    add(over_ped , null);


    lover_rec.setText( "Over recibido" );
    lover_rec.setBounds(new Rectangle(24, 198, 123, 22)); 
    over_rec.setBounds(new Rectangle(152, 198, 143, 22)); 
    add(lover_rec, null);
    add(over_rec , null);


    ldif.setText( "Diferencia" );
    ldif.setBounds(new Rectangle(24, 225, 123, 22)); 
    dif.setBounds(new Rectangle(152, 225, 143, 22)); 
    add(ldif, null);
    add(dif , null);


    lobservaciones.setText( "Observaciones" );
    lobservaciones.setBounds(new Rectangle(24, 252, 123, 22)); 
    observaciones.setBounds(new Rectangle(152, 252, 285, 22)); 
    add(lobservaciones, null);
    add(observaciones , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idpdf, CHAR, REQ, "idpdf" ).SetReadOnly(true);
    AddItem( linea, UINT, REQ, "linea" ).SetReadOnly(true);
    AddItem( status, CHAR, OPT, "status" , JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosConsolidacion())).SetReadOnly(true);
    AddItem( boleto, CHAR, OPT, "boleto" ).SetReadOnly(true);
    AddItem( fecha, UINT, OPT, "fecha" ).SetReadOnly(true);
    AddItem( id_aerolinea, CHAR, OPT, "id_aerolinea" ).SetReadOnly(true);
    AddItem( over_ped, UFLOAT, OPT, "over_ped" ).SetReadOnly(true);
    AddItem( over_rec, UFLOAT, OPT, "over_rec" ).SetReadOnly(true);
    AddItem( dif, UFLOAT, OPT, "over_dif" ).SetReadOnly(true);
    AddItem( observaciones, CHAR, OPT, "observaciones" );

  } 
}  //  @jve:decl-index=0:visual-constraint="9,8" 
