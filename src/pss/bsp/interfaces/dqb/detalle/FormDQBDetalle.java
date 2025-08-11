package  pss.bsp.interfaces.dqb.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.GridBagConstraints;

public class FormDQBDetalle extends JBaseForm {


private static final long serialVersionUID = 1245253775814L;



/**
   * Constructor de la Clase
   */
  public FormDQBDetalle() throws Exception {
   }

  public GuiDQBDetalle getWin() { return (GuiDQBDetalle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "idPDF" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
    AddItemEdit( "Placa", CHAR, OPT, "id_aerolinea" ).setSizeColumns(3).setReadOnly(true);
    AddItemDateTime( "Fecha", DATE, OPT, "fecha" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Gds", CHAR, OPT, "gds" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Tipo doc.", CHAR, OPT, "tipo_doc" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Boleto", CHAR, OPT, "boleto" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "PNR", CHAR, OPT, "pnr" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Status", CHAR, OPT, "status" ).setSizeColumns(3).setReadOnly(true);

  }



}  //  @jve:decl-index=0:visual-constraint="10,10" 
