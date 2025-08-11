package pss.common.regions.currency.history;

import pss.common.regions.divitions.BizPais;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormMonedaCotizacion extends JBaseForm {


  /**
   * Constructor de la Clase
   */
  public FormMonedaCotizacion() throws Exception {
  }

  public GuiMonedaCotizacion getWin() { 
  	return (GuiMonedaCotizacion) getBaseWin(); 
  }
  public BizMonedaCotizacion getCotiz() throws Exception { 
  	return this.getWin().GetcDato(); 
  }
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	if (this.isAlta())
  		this.getCotiz().loadDefault();
  	JFormPanelResponsive panel = this.AddItemColumn(2);
  	panel.setLabelLeft();
  	panel.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
  	panel.AddItemEdit( "pais", CHAR, REQ, "pais" ).hide();
  	panel.AddItemEdit( "Origen", CHAR, REQ, "moneda_source" ).hide();
  	panel.AddItemEdit( "Destino", CHAR, REQ, "moneda_target" ).hide();

  	if (this.getCotiz().isModoFecha() && this.isAlta())
  		panel.AddItemDateTime( "Fecha", UFLOAT, REQ, "fecha" ).SetValorDefault(BizUsuario.getUsr().todayGMT());
  	
  	panel.AddItemEdit( "Compra", UFLOAT, REQ, "cotiz_compra" ).SetValorDefault(0d);
  	panel.AddItemEdit( "Venta", UFLOAT, REQ, "cotiz_venta" ).SetValorDefault(0d);
//  	panel.AddItemEdit( "Contab", UFLOAT, OPT, "cotiz_contab").SetValorDefault(0d);
  	if (BizPais.findPais(this.getCotiz().getPais()).isArgentina()) {
    	panel.AddDivPanel().setMinHeight(80);
  		panel.AddItemHtml(null, CHAR, OPT, "cotiz_dolar", true).setReadOnly(true);
  	}
//  	this.initCtz();
//    control = AddItem( FechaVigencia, DATE, REQ, "fecha_vigencia" );
//    control.SetValorDefault(new Date());
//    control = AddItem( HoraVigencia, HOUR, REQ, "hora_vigencia" );
//    control.SetValorDefault(JDateTools.CurrentTime());
  }

//  private void initCtz() throws Exception {
////  	if (this.isAlta()) return;
//    this.findControl("cotiz_compra").SetValorDefault(this.GetWin().GetcDato().getObjMonedaConver().getCotizCompra());
//    this.findControl("cotiz_venta" ).SetValorDefault(this.GetWin().GetcDato().getObjMonedaConver().getCotizVenta());
////  	if (this.GetWin().GetcDato().getObjMonedaConver().isMonedaLocal())
////  		this.findControl("cotiz_contab").SetValorDefault(this.GetWin().GetcDato().getObjMonedaConver().getCotizContab());
////  	else {
////  		this.findControl("cotiz_contab").hide();
////  		this.findLabel(jCtzContab).hide();
////  	}
//  }
//  
} 