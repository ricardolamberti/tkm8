package pss.bsp.contrato.evaluate;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.logica.JContratoNormal;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssHtmlTextArea;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.ui.components.JPssLabelWinLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormEvaluate extends JBaseForm {


private static final long serialVersionUID = 1477827540739L;

  /**
   * Propiedades de la Clase
   */



  /**
   * Constructor de la Clase
   */
  public FormEvaluate() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiEvaluate getWin() { return (GuiEvaluate) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
     }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit(null, CHAR, REQ,"id").setHide(true);
    
    JFormPanelResponsive panel = AddItemRow();
    panel.AddItemRow();
    panel.setLabelLeft();
    panel.AddItemEdit("Numero boleto", CHAR, REQ,"numero_boleto").setSizeColumns(9);
  
    panel.AddItemButton("Evaluar", 20, true).setSizeColumns(3);
    panel.AddItemEdit("Evaluación", CHAR, OPT,"diagnostico").setReadOnly(true);
    panel.AddItemList(10);
  }

}  //  @jve:decl-index=0:visual-constraint="14,3"
