package pss.bsp.contrato.detalleCopaPorRutas;

import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.carrier.GuiCarriers;

public class FormEditDetalleCopaPorRutas extends JBaseForm {


	private static final long serialVersionUID = 1446860278358L;

	  /**
	   * Propiedades de la Clase
	   */

	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleCopaPorRutas() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDetalleCopaPorRutas getWin() { return (GuiDetalleCopaPorRutas) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	   
	 
//	    getJPanel().setBorder(BorderFactory.createEtchedBorder());

	 
	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
	    JFormControl c = AddItemCombo( "Aerolinea", CHAR, REQ, "id_aerolinea" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAerolineas(one);
	    	}
	    });
	    c.setPlaceHolder("Aerolinea del contrato");
	    AddItemCheck( "Extra/kicker",OPT, "kicker" );
	 
	    AddItemEdit( "Conclusion", CHAR, OPT, "conclusion" ).SetReadOnly(true);
	    AddItemEdit( "Descripción", CHAR, OPT, "descripcion" );
	    AddItemEdit( "Tourcode excluidos", CHAR, OPT, "tourcode_excluded" );
	    AddItemEdit( "Clases excluidas", CHAR, OPT, "class_excluded" );
	    AddItemCheck( "Calculo" , CHAR,OPT,"pax", "S", "N").setDescripcionValorCheck("Pax").setDescripcionValorUnCheck("Tarifa");
	    AddItemEdit( "Mercados", CHAR, OPT, "mercados" ).setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US");
	    AddItemEdit( "Iatas", CHAR, OPT, "iatas" ).setPlaceHolder("Los iatas se dividen con coma");
	    AddItemTable( "Niveles", "niveles", GuiNiveles.class);
	  }
	  @Override
	  	public void OnPostShow() throws Exception {
	  		checkControls("");
	  		super.OnPostShow();
	  	}

	  private JWins getAerolineas(boolean one) throws Exception {
	  	GuiCarriers g = new GuiCarriers();
	  	if (one) {
	  		g.getRecords().addFilter("carrier", getWin().GetcDato().getIdAerolinea());
	  	} 
	  	g.addOrderAdHoc("description", "ASC");

	  	return g;
	  }
	  private JWins getIndicadorGanancia(boolean one) throws Exception {
	  	GuiSqlEvents g = new GuiBSPSqlEvents();
	  	if (one) {
	  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableGanancia());
	  	} else {
	  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
	  	}
	  	return g;
	  }


	  @Override
	  	public void checkControls(String sControlId) throws Exception {
	  		super.checkControls(sControlId);
	  	}
		
	}  //  @jve:decl-index=0:visual-constraint="24,55"
