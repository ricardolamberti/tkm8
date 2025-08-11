package pss.bsp.contrato.detalleBackendDobleRutas;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.bsp.contrato.detalle.variaciones.GuiVariacionParticulares;
import pss.bsp.contrato.detalleCopa.objetivos.GuiObjetivosCopas;
import pss.bsp.contrato.detalleUpfrontRutas.FormEditDetalleUpfrontRuta;
import pss.bsp.contrato.series.calculado.GuiSerieCalculadas;
import pss.bsp.event.GuiBSPSqlEvent;
import pss.common.event.sql.GuiSqlEvent;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiDetalleBackendDobleRuta extends GuiDetalle {



  /**
   * Constructor de la Clase
   */
  public GuiDetalleBackendDobleRuta() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDetalleBackendDobleRuta(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String getTitle()   throws Exception { return "Objetivo Backend Ruta"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  	if (GetVision().equals("VIEW" ))
  		 return FormEditDetalleBackendDobleRuta.class;
  	return FormDetalleBackendDobleRuta.class; 
  }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormEditDetalleBackendDobleRuta.class; }
  public Class<? extends JBaseForm> getFormUpdate() throws Exception { return FormEditDetalleBackendDobleRuta.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "variable_ganancia"; }
  public BizDetalle GetcDato() throws Exception { return (BizDetalle) this.getRecord(); }
  public BizDetalleBackendDobleRuta GetccDato() throws Exception { return (BizDetalleBackendDobleRuta) this.getRecord(); }

 

	public GuiSqlEvent getIndicador() throws Exception {
		GuiSqlEvent var = new GuiBSPSqlEvent();
		var.setRecord(GetcDato().getObjEvent());
		return var;
	}
	public JWin getIndicadorGanancia() throws Exception {
		GuiSqlEvent var = new GuiBSPSqlEvent();
		var.setRecord(GetcDato().getObjEventGanancia());
		return var;
	}
	 public JWins getEstimadores() throws Exception {
	  	GuiVariacionParticulares	c = new GuiVariacionParticulares();
	  	c.getRecords().addFilter("id_contrato", this.GetcDato().getId());
	  	c.getRecords().addFilter("linea_contrato", this.GetcDato().getLinea());
	  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
	  	c.getRecords().addOrderBy("fecha_desde");
	  	
	  	c.getRecords().setParent(this.GetcDato());
	  	return c;
	  	
	  }
	 public JWins getNiveles() throws Exception {
	  	GuiNiveles	c = new GuiNiveles();
	  	c.getRecords().addFilter("id", this.GetcDato().getId());
	  	c.getRecords().addFilter("linea", this.GetcDato().getLinea());
	  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
	  	c.getRecords().addOrderBy("valor");
	  	c.getRecords().setParent(this.GetcDato());
	  	return c;
	  	
	  }
	  @Override
		public boolean canConvertToURL() throws Exception {
			return false;
		}
 }
