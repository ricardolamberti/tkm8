package pss.bsp.contrato.conocidos2;

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

public class FormContratoConocidoV2 extends JBaseForm {


private static final long serialVersionUID = 1477827540739L;

  /**
   * Propiedades de la Clase
   */



  /**
   * Constructor de la Clase
   */
  public FormContratoConocidoV2() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiContratoConocidoV2 getWin() { return (GuiContratoConocidoV2) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
     }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive rowA=AddItemRow(6);
  	JFormPanelResponsive rowB=AddItemRow(6);
  	rowA.AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
  	rowA.AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
  	rowA.AddItemCombo( "País", CHAR, REQ, "pais", new GuiPaisesLista() ).setSizeColumns(2);
  	rowA.AddItemCombo( "Aerolineas", CHAR, REQ, "aerolineas" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getAerolineas(bOneRow);
    	}
    }).setSizeColumns(2);;
  	rowA.AddItemCombo( "período", CHAR, REQ, "periodo" , JWins.createVirtualWinsFromMap(BizContratoConocidoV2.getTipoPeriodos())).setSizeColumns(2);;
  	rowA.AddItemCombo( "Contrato", CHAR, REQ, "tipo_contrato" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getTiposContratos(bOneRow);
    	}
    }).setSizeColumns(2).setRefreshForm(true).SetValorDefault(JContratoNormal.class.getName());

  	rowA.AddItemWinLov( "Icono"       , UINT, OPT, "icono",new JControlWin() {  @Override
  		public JWins getRecords()   throws Exception {return  new GuiIconos();};}).setSizeColumns(2);

  	rowA.AddItemCombo( "Tipo premio", CHAR, REQ, "tipo_premio" , JWins.createVirtualWinsFromMap(BizNivel.getTiposPremios())).setSizeColumns(6);
  	rowA.AddItemCombo( "Tipo objetivo", CHAR, REQ, "tipo_objetivo", JWins.createVirtualWinsFromMap(BizNivel.getTiposObjetivos()) ).setSizeColumns(6);
  	rowA.AddItemFile( "imagen menu", CHAR, OPT, "image_menu_filename");
  	rowA.AddItemFile( "imagen", CHAR, OPT, "image_filename");
  	
    rowA.AddItemCheck( "Objetivo extra", OPT, "objetivo_extra" );
    rowB.AddCardPanel(10);
    JFormControl c=rowA.AddItemHtml( "detalle", CHAR, OPT, "detalle" );

  } 
  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("tipo_contrato"))
  			getWin().GetcDato(). cleanObjDetalle();
  		super.checkControls(sControlId);
  	}
  
  JWins getTiposContratos(boolean one) throws Exception {
  	return JWins.createVirtualWinsFromMap(BizDetalle.getLogicasContrato());
  }

  JWins getAerolineas(boolean one) throws Exception {
  	GuiCarriers events = new GuiCarriers();
  	if (one) events.getRecords().addFilter("carrier",getWin().GetcDato().getAerolineas());
  	return events;
  }


}  //  @jve:decl-index=0:visual-constraint="14,3"
