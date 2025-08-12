package pss.bsp.contrato.detalleLatamFamilia;

import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormEditDetalleDataminingTriFamilia extends JBaseForm {


	private static final long serialVersionUID = 1446860278358L;


	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleDataminingTriFamilia() throws Exception {
		  }

	  public GuiDetalleDataminingTriFamilia getWin() { return (GuiDetalleDataminingTriFamilia) getBaseWin(); }

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
	    JFormControl c = AddItemCombo("variable", CHAR, REQ, "variable", new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicador(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar si se alcanza objetivo");
	    c=AddItemCombo("variable_ganancia", CHAR, OPT, "variable_ganancia", new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicadorGanancia(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar el premio");
	    c=AddItemCombo("variable_aux", CHAR, OPT, "variable_aux", new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicadorAuxiliar(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar el valor auxiliar");
	   
	    AddItemCheck("kicker", OPT, "kicker");
	    c=AddItemCheck("acumulativo", OPT, "acumulativo");
	    c.SetValorDefault(false);
//	    c.setRefreshForm(true);
//	    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalle.getPeriodos()));
	    AddItemTable( "Niveles", "niveles", GuiNiveles.class);

	    AddItemEdit("valor_fcontrato", UFLOAT, OPT, "valor_fcontrato").SetReadOnly(true);
	    AddItemEdit("valor_totalcontrato", UFLOAT, OPT, "valor_totalcontrato").SetReadOnly(true);
	    AddItemEdit("ganancia_auxiliar", UFLOAT, OPT, "ganancia_auxiliar").SetReadOnly(true);
//	    AddItem( getValorObjetivo(), UFLOAT, OPT, "valor_objetivo" ).setVisible(false);
	    AddItemEdit("conclusion", CHAR, OPT, "conclusion").SetReadOnly(true);
	    AddItemEdit("descripcion", CHAR, OPT, "descripcion");
	    AddItemEdit("fms_global", FLOAT, OPT, "fms_global");
	    AddItemEdit("sharegap_global", FLOAT, OPT, "sharegap_global");
	    AddItemEdit("valor_global", FLOAT, OPT, "valor_global");
	    AddItemEdit("valor_reembolso", FLOAT, OPT, "valor_reembolso");
	
	  }
	  @Override
	  	public void OnPostShow() throws Exception {
	  		checkControls("");
	  		super.OnPostShow();
	  	}

	  private JWins getIndicador(boolean one) throws Exception {
	  	GuiSqlEvents g = new GuiBSPSqlEvents();
	  	if (one) {
	  		g.getRecords().addFilter("id", getWin().GetcDato().getVariable());
	  	} else {
	  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
	  	}
	  	g.addOrderAdHoc("descripcion", "ASC");

	  	return g;
	  }
	  private JWins getIndicadorGanancia(boolean one) throws Exception {
	  	GuiSqlEvents g = new GuiBSPSqlEvents();
	  	if (one) {
	  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableGanancia());
	  	} else {
	  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
	  	}
	  	g.addOrderAdHoc("descripcion", "ASC");
	  	return g;
	  }
	  private JWins getIndicadorAuxiliar(boolean one) throws Exception {
	  	GuiSqlEvents g = new GuiBSPSqlEvents();
	  	if (one) {
	  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableAuxiliar());
	  	} else {
	  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
	  	}
	  	g.addOrderAdHoc("descripcion", "ASC");
	  	return g;
	  }


	  @Override
	  	public void checkControls(String sControlId) throws Exception {
	  		if (sControlId.equals("variable")||sControlId.equals("variable_ganancia")) {
	  			getWin().GetcDato().clean();
	  			if (getWin().GetcDato().getObjEvent()!=null)
	  				getControles().findControl("descripcion").setValue(getWin().GetcDato().getObjEvent().getDescripcion());
	  		}
//	  		if (GetControles().findControl("acumulativo").getValue().equals("S")) {
//	  			GetControles().findControl("periodo").edit(GetModo());
//	  			//GetControles().findControl("valor").edit(GetModo());
//	  		} else {
//	  			GetControles().findControl("periodo").disable();
//	  			//GetControles().findControl("valor").disable();
//	  			GetControles().findControl("periodo").setValue("");
//	  			//GetControles().findControl("valor").setValue(""+getWin().GetcDato().getValor());
//	  		}
	  		
	  		if (sControlId.equals("")) {
	  			if (getWin().GetcDato().getObjEvent()!=null)
	  				this.getControles().findControl("valor_fcontrato").setValue(""+getWin().GetcDato().getObjEvent().getValor());
	  			else
	  				this.getControles().findControl("valor_fcontrato").setValue("");
	  			if (getWin().GetcDato().getObjEventGanancia()!=null)
	  				this.getControles().findControl("valor_totalcontrato").setValue(""+getWin().GetcDato().getObjEventGanancia().getValor());
	  			else
	  				this.getControles().findControl("valor_totalcontrato").setValue("");
	  		}
	  		
	  		if (getWin().GetccDato().needFMSGLobal()) {
	  			getControles().findControl("fms_global").edit();
	   		} else {
	  			getControles().findControl("fms_global").hide();
	  		}
	   		if (getWin().GetccDato().needShareGapGLobal()) {
	  			getControles().findControl("sharegap_global").edit();
	  		} else {
	  			getControles().findControl("sharegap_global").hide();
	  		}
	   		if (getWin().GetccDato().needValorRefGLobal()) {
	  			getControles().findControl("valor_global").edit();
	  		} else {
	  			getControles().findControl("valor_global").hide();
	  		}
	  		super.checkControls(sControlId);
	  	}

	}  //  @jve:decl-index=0:visual-constraint="24,55"
