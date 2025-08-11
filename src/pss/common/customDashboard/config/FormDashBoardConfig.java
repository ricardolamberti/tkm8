package pss.common.customDashboard.config;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDashBoardConfig extends JBaseForm {


private static final long serialVersionUID = 1563965359048L;



  /**
   * Constructor de la Clase
   */
  public FormDashBoardConfig() throws Exception {
  }

  public GuiDashBoardConfig getWin() { return (GuiDashBoardConfig) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
	  JFormPanelResponsive panel = this.AddItemColumn(3);
	  panel.setLabelLeft(3);
	  
	  panel.AddItemEdit("id", CHAR, OPT, "id").hide();
	  panel.AddItemEdit("company", CHAR, REQ, "company").hide();
	  panel.AddItemEdit("userid", CHAR, REQ, "userid").hide();
	  panel.AddItemEdit("Dash", INT, REQ, "dash_description").hide();
	  panel.AddItemEdit("Elemento", CHAR, REQ, "dash_descr").setReadOnly(true);
//	  , new JControlCombo() {
//	  	@Override
//	  	public JWins getRecords(boolean one) throws Exception {
//	  		return DashBoardModule.getDataElements();
//	  	}
//	  }).setReadOnly(true);
//	  panel.AddItemEdit("Orden", CHAR, OPT, "dash_order").setReadOnly(true);
//	  panel.AddItemEdit("Descripción", CHAR, OPT, "dash_descr").setReadOnly(true);
	  panel.AddItemCombo("Período", ULONG, REQ, "historico", new JControlCombo() {
	  	@Override
	  	public JWins getRecords(boolean one) throws Exception {
	  		return JWins.createVirtualWinsFromMap(BizDashBoardConfig.getHistoricoMap());
	  	}
	  });

	  panel.AddItemCombo("Tamaño", UINT, REQ, "dash_size", new JControlCombo() {
	  	@Override
	  	public JWins getRecords(boolean one) throws Exception {
	  		return JWins.createVirtualWinsFromMap(BizDashBoardConfig.getSizeMap());
	  	}
	  });

  } 
} 
