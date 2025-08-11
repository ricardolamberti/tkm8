package pss.common.regions.currency.conversion;

import pss.common.regions.currency.history.GuiMonedaCotizacion;
import pss.common.regions.currency.history.GuiMonedaCotizaciones;
import pss.common.regions.currency.history.GuiMonedaFechas;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMonedaConver extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiMonedaConver() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizMonedaConver(); }
  @Override
	public int GetNroIcono()   throws Exception { return 5041; }
  @Override
	public String GetTitle()   throws Exception { return "Cotización Moneda"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMonedaConver.class; }
  @Override
	public Class<? extends JBaseForm> getFormFlat() throws Exception { return FormMonedaConverFlat.class; }
  @Override
	public String  getKeyField() throws Exception { return "cotizacion_id"; }
  @Override
	public String  getDescripField() { return "descr_cotiz"; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
		this.createActionDelete();
		this.addAction(10, "Histórico", null, 54, false, false);
  	this.addAction(20, "Cambiar Cotización", null, 5003, true, true);
  }
  
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getHistoricos());
  	if (a.getId()==20) return new JActNew(this.getNewHistorico(), 0);
  	return null;
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==2) return BizUsuario.getUsr().isAnyAdminUser();
  	if (a.getId()==3) return BizUsuario.getUsr().isAnyAdminUser();
  	return super.OkAction(a);
  }
  
	@Override
	public boolean checkActionOnGlobal(BizAction a) throws Exception {
		if (this.GetVision().equals(BizMonedaConver.VISION_VIEW))
			return false;
		return true;
	}

  public JWins getHistoricos() throws Exception {
  	if (this.GetcDato().isModoFecha()) {
  		GuiMonedaFechas wins = new GuiMonedaFechas();
    	wins.getRecords().addFilter("company", this.GetcDato().getCompany());
    	wins.getRecords().addFilter("pais", this.GetcDato().getPais());
    	wins.getRecords().addFilter("moneda_source", this.GetcDato().getMonedaSource());
    	wins.getRecords().addFilter("moneda_target", this.GetcDato().getMonedaTarget());
    	wins.getRecords().addOrderBy("fecha", "desc");
	  	wins.setPreviewFlag(JWins.PREVIEW_MAX);
    	return wins;
  	} else {
  		GuiMonedaCotizaciones wins = new GuiMonedaCotizaciones();
	  	wins.getRecords().addFilter("company", this.GetcDato().getCompany());
	  	wins.getRecords().addFilter("pais", this.GetcDato().getPais());
	  	wins.getRecords().addFilter("moneda_source", this.GetcDato().getMonedaSource());
	  	wins.getRecords().addFilter("moneda_target", this.GetcDato().getMonedaTarget());
	  	wins.getRecords().addOrderBy("fecha_hora", "desc");
	  	wins.setPreviewFlag(JWins.PREVIEW_MAX);
	  	return wins;
  	}
  }

  public GuiMonedaCotizacion getNewHistorico() throws Exception {
  	GuiMonedaCotizacion h = new GuiMonedaCotizacion();
  	h.getRecord().addFilter("company", this.GetcDato().getCompany());
  	h.getRecord().addFilter("pais", this.GetcDato().getPais());
  	h.getRecord().addFilter("moneda_source", this.GetcDato().getMonedaSource());
  	h.getRecord().addFilter("moneda_target", this.GetcDato().getMonedaTarget());
  	return h;
  }
  /**
   * Devuelvo el dato ya casteado
   */
  public BizMonedaConver GetcDato() throws Exception { return (BizMonedaConver) this.getRecord(); }

  @Override
  public int GetSimpleClick() throws Exception {
  	return 10;
  }
  
 }
