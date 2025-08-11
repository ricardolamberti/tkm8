package pss.bsp.contrato.detalleAvianca;

import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormTableResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormEditDetalleAvianca extends JBaseForm {


	private static final long serialVersionUID = 1446860278358L;


	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleAvianca() throws Exception {
		  }

	  public GuiDetalleAvianca getWin() { return (GuiDetalleAvianca) getBaseWin(); }


	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
	    JFormComboResponsive c = AddItemCombo( "Aerolinea" , CHAR, REQ, "id_aerolinea" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAerolineas(one);
	    	}
	    });
	    c.setSizeColumns(8);
	    c.setPlaceHolder("Aerolinea del contrato");
	    JFormCheckResponsive p=AddItemCheck( "Sin Puntos",OPT, "pax" );
	    p.SetValorDefault(true);
	    p.setSizeColumns(2);
	    AddItemCheck( "Kicker",OPT, "kicker" ).setSizeColumns(2);

	    AddItemEdit( "Descripción", CHAR, OPT, "descripcion" );
	    AddItemEdit( "Limite Maximo", UFLOAT, OPT, "fms_max" ).setSizeColumns(3);
	    AddItemEdit( "Limite Minimo", UFLOAT, OPT, "fms_min" ).setSizeColumns(3);
	    AddItemEdit( "Limite", UFLOAT, OPT, "limite" ).setSizeColumns(3);
	    AddItemEdit( "Tourcode excluidos", CHAR, OPT, "tourcode_excluded" ).setSizeColumns(6);
	    AddItemEdit( "Clases excluidas", CHAR, OPT, "class_excluded" ).setSizeColumns(6);
	    AddItemEdit( "Mercados", CHAR, OPT, "mercados" );
	    AddItemEdit( "Iatas", CHAR, OPT, "iatas" );
			
	    JFormTableResponsive t=AddItemTable( "Niveles", "niveles", GuiNiveles.class);
	    t.setKeepHeight(true);
	    t.setKeepWidth(true);
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
	


	  @Override
	  	public void checkControls(String sControlId) throws Exception {
	  		super.checkControls(sControlId);
	  	}

	}  //  @jve:decl-index=0:visual-constraint="24,55"
