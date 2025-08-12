package pss.bsp.contrato.detalle;

import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormDetalle extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

 

/**
   * Constructor de la Clase
   */
  public FormDetalle() throws Exception {
  }

  public GuiDetalle getWin() { return (GuiDetalle) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit("company", CHAR, REQ, "company").SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit("id", UINT, OPT, "id");
    AddItemEdit("linea", UINT, OPT, "linea");
    AddItemCombo("variable", CHAR, REQ, "variable", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getIndicador(one);
    	}
    }).setRefreshForm(true);
    AddItemCombo("variable_ganancia", CHAR, REQ, "variable_ganancia", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getIndicadorGanancia(one);
    	}
    }).setRefreshForm(true);
    
    JFormControl c=AddItemCheck("acumulativo", OPT, "acumulativo");
    c.SetValorDefault(false);
//    c.setRefreshForm(true);
    AddItemCombo("periodo", CHAR, OPT, "periodo", JWins.createVirtualWinsFromMap(BizDetalle.getPeriodos()));
//    AddItem( getValorActual(), FLOAT, OPT, "valor_actual" ).SetReadOnly(true);
    AddItemEdit("valor_fcontrato", FLOAT, OPT, "valor_fcontrato").SetReadOnly(true);
//    AddItem( getValorObjetivo(), FLOAT, OPT, "valor_total" ).setVisible(true);
    AddItemEdit("valor_totalcontrato", FLOAT, OPT, "valor_totalcontrato").setVisible(true);
    AddItemEdit("conclusion", CHAR, OPT, "conclusion").SetReadOnly(true);
    AddItemEdit("descripcion", CHAR, OPT, "descripcion").SetReadOnly(true);
//    AddItem( getNivel1(), CHAR, OPT, "nivel_alcanzado" ).SetReadOnly(true);
//    AddItem( getEvalua1(), FLOAT, OPT, "ganancia" ).SetReadOnly(true);
    AddItemEdit("nivel_alcanzado_estimada", CHAR, OPT, "nivel_alcanzado_estimada").SetReadOnly(true);
    AddItemEdit("ganancia_estimada", FLOAT, OPT, "ganancia_estimada").SetReadOnly(true);
    JFormImageResponsive i=AddItemImage( "", "imagen1" );
    i.setSource(JPssImage.SOURCE_SCRIPT);


    i=AddItemImage( "", "imagen2" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
	  i.setKeepHeight(true);
	  i.setKeepWidth(true);

    AddItemCheck("kicker", OPT, "kicker");
    
    JFormTabPanelResponsive tabs = new JFormTabPanelResponsive();
    tabs.AddItemList(35);
    tabs.AddItemList(30);

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
  	
  		if (sControlId.equals("variable")) {
  			getWin().GetcDato().clean();
  		}
  		if (sControlId.equals("fecha_calculo"))
  			getWin().GetcDato().calcule(false);
  		if (sControlId.equals("fecha_desde_calculo"))
  			getWin().GetcDato().calcule(false);
   	
//  		if (GetControles().findControl("acumulativo").getValue().equals("S")) {
//  			GetControles().findControl("periodo").edit(GetModo());
//  			//GetControles().findControl("valor").edit(GetModo());
//  		} else {
//  			GetControles().findControl("periodo").disable();
//  			//GetControles().findControl("valor").disable();
//  			GetControles().findControl("periodo").setValue("");
//  			//GetControles().findControl("valor").setValue(""+getWin().GetcDato().getValor());
//  		}
//  		if (sControlId.equals("")) {
//  			getWin().GetcDato().calcule();
//  			this.GetControles().findControl("valor").setValue(""+getWin().GetcDato().getObjEvent().getValor());
//  		}
  		super.checkControls(sControlId);
  	}

}  //  @jve:decl-index=0:visual-constraint="7,4"
