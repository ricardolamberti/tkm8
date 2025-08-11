package pss.bsp.contrato.detalleLatamFamilia.calculo;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.GuiVirtuals;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.tourism.carrier.GuiCarriers;
import pss.core.ui.components.JPssLabel;
import javax.swing.JTextArea;
import pss.core.ui.components.JPssEdit;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class FormFamiliaLatamDetail extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */
JPssEdit id = new JPssEdit  ();
JPssEdit idDetalle = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();
JPssEdit idContrato = new JPssEdit  ();
JPssEdit textField= new JPssEdit  ();
JPssEdit comboBox= new JPssEdit  ();

/**
   * Constructor de la Clase
   */
  public FormFamiliaLatamDetail() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiFamiliaLatamDetail getWin() { return (GuiFamiliaLatamDetail) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(484, 132));
    
    comboBox = new JPssEdit();
    comboBox.setBounds(107, 11, 316, 27);
    add(comboBox);
    
    textField = new JPssEdit();
    textField.setBounds(107, 42, 174, 27);
    add(textField);
    textField.setColumns(10);
    
    JLabel lblFamilia = new JLabel("Familia");
    lblFamilia.setBounds(24, 17, 46, 14);
    add(lblFamilia);
    
    JLabel lblPeso = new JLabel("Peso");
    lblPeso.setBounds(24, 48, 46, 14);
    add(lblPeso);


  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {

    AddItem( id, LONG, OPT, "id" );
    AddItem( company, CHAR, OPT, "company" );
    AddItem( idDetalle, LONG, OPT, "id_detalle" );
    AddItem( idContrato, LONG, OPT, "id_contrato" );
    AddItem( comboBox, CHAR, REQ, "familia").setToolTip("clases separadas por ,");;
    AddItem( textField, FLOAT, REQ, "peso");
  }
  
//  JWins getFamilias(boolean one) throws Exception {
//  	GuiVirtuals virtuals = new GuiVirtuals();
//  	virtuals.getRecords().SetSQL("Select  aerolineas as valor,(case when max(icono) is not null then  '' else MAX(TUR_CARRIER.description)end) as descripcion ,max(icono) as icono from bsp_contratosconocidos join TUR_CARRIER on bsp_contratosconocidos.aerolineas = TUR_CARRIER.carrier where bsp_contratosconocidos.pais='"+getWin().GetcDato().getPais()+ "' group by aerolineas");
//  	if (one) 
//  		virtuals.getRecords().addFilter("familia",getWin().GetcDato().getFamilia());
//  	return virtuals;
//  }
}  //  @jve:decl-index=0:visual-constraint="10,10" 
