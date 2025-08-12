package pss.bsp.contrato.detalleCopaWS;

import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTableResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormEditDetalleCopaWS extends JBaseForm {


	private static final long serialVersionUID = 1446860278358L;


	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleCopaWS() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDetalleCopaWS getWin() { return (GuiDetalleCopaWS) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit("company", CHAR, REQ, "company").SetValorDefault(BizUsuario.getUsr().getCompany());
	    AddItemEdit("id", UINT, OPT, "id");
	    AddItemEdit("linea", UINT, OPT, "linea");
	    JFormControl c = AddItemCombo("id_aerolinea", CHAR, REQ, "id_aerolinea", new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAerolineas(one);
	    	}
	    });
	    c.setPlaceHolder("Aerolinea del contrato");
	    AddItemCheck("kicker", OPT, "kicker");
	    JFormTableResponsive t=AddItemTable( "Niveles", "niveles", GuiNiveles.class);

	    AddItemEdit("conclusion", CHAR, OPT, "conclusion").SetReadOnly(true);
	    AddItemEdit("descripcion", CHAR, OPT, "descripcion");
	    AddItemEdit("tourcode_excluded", CHAR, OPT, "tourcode_excluded");
	    AddItemEdit("class_excluded", CHAR, OPT, "class_excluded");
	    AddItemCheck("Tickets", CHAR, OPT, "pax", "S", "N");
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

	}  //  @jve:decl-index=0:visual-constraint="24,55"
