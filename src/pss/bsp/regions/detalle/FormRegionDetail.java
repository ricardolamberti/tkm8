package  pss.bsp.regions.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormRegionDetail extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lpais = new JPssLabel();
JPssLabelWinLov pais = new JPssLabelWinLov  ();
JPssEdit id = new JPssEdit  ();
JPssEdit idRegion = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();
/**
   * Constructor de la Clase
   */
  public FormRegionDetail() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiRegionDetail getWin() { return (GuiRegionDetail) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(484, 76));


    lpais.setText("Pais");
    lpais.setBounds(new Rectangle(16, 23, 37, 22)); 
    pais.setBounds(new Rectangle(60, 23, 327, 22)); 
    add(lpais, null);
    add(pais , null);


  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( pais, CHAR, REQ, "pais" , new GuiPaisesLista());
    AddItem( id, CHAR, OPT, "id" );
    AddItem( idRegion, CHAR, OPT, "id_region" );
    AddItem( company, CHAR, OPT, "company" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
