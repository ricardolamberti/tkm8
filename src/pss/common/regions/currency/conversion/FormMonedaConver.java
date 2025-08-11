package pss.common.regions.currency.conversion;

import pss.common.regions.currency.GuiMonedaPaises;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;


public class FormMonedaConver extends JBaseForm {
  public FormMonedaConver() throws Exception {
  }

  public GuiMonedaConver GetWin() { 
  	return (GuiMonedaConver) getBaseWin(); 
  }

  /**
   * Linkeo los campos con la variables del form
   */
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
//    SetExitOnOk(true);
  	this.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
  	this.AddItemEdit( "pais", CHAR, REQ, "pais" ).hide();
  	this.AddItemCombo( "Origen", CHAR, REQ, "moneda_source", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getMonedas(one, true);
    	}
    }, 2);
  	this.AddItemCombo( "Destino", CHAR, REQ, "moneda_target", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getMonedas(one, false);
    	}
    }, 2);
  	this.AddItemEdit( "Compra", UFLOAT, OPT, "cotiz_compra", 2 ).SetReadOnly(true);
  	this.AddItemEdit( "Venta", UFLOAT, OPT, "cotiz_venta",2 ).SetReadOnly(true);
  	this.AddItemCheck( "Modo Diario", REQ, "modo_fecha", 2 );
//    AddItem( jCotizContab, UFLOAT, OPT, "cotiz_contab" ).SetReadOnly(true);
  	this.AddItemTabPanel().AddItemListOnDemand(10);
    this.initCtz();
  }
  
  private void initCtz() throws Exception {
//		if (!this.GetWin().GetcDato().isMonedaLocal()) {
//			this.findControl("cotiz_contab").hide();
//			this.findLabel(lContab).hide();
//		}
  }

  
  private JWins getMonedas(boolean one, boolean source) throws Exception {
  	GuiMonedaPaises p = new GuiMonedaPaises();
  	if (one) {
  		p.getRecords().addFilter("company", this.GetWin().GetcDato().getCompany());
  		p.getRecords().addFilter("pais", this.GetWin().GetcDato().getPais());
  		p.getRecords().addFilter("moneda", source?this.GetWin().GetcDato().getMonedaSource():this.GetWin().GetcDato().getMonedaTarget());
  	} else {
  		p.getRecords().addFilter("company", this.valueControl("company"));
  		p.getRecords().addFilter("pais", this.valueControl("pais"));
  	}
  	return p;
  }

}