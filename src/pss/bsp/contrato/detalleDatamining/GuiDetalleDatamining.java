package pss.bsp.contrato.detalleDatamining;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.bsp.contrato.detalle.variaciones.GuiVariacionParticulares;
import pss.bsp.contrato.series.calculado.GuiSerieCalculadas;
import pss.bsp.event.GuiBSPSqlEvent;
import pss.common.event.sql.GuiSqlEvent;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiDetalleDatamining extends GuiDetalle {



  /**
   * Constructor de la Clase
   */
  public GuiDetalleDatamining() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDetalleDatamining(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String getTitle()   throws Exception { return "Objetivo datamining"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	if (GetVision().equals("VIEW" ))
 		 return FormEditDetalleDatamining.class;
  	return FormDetalleDatamining.class; 
  }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormEditDetalleDatamining.class; }
  public Class<? extends JBaseForm> getFormUpdate() throws Exception { return FormEditDetalleDatamining.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "variable"; }
  public BizDetalle GetcDato() throws Exception { return (BizDetalle) this.getRecord(); }
  public BizDetalleDatamining GetccDato() throws Exception { return (BizDetalleDatamining) this.getRecord(); }

 
  
 

	public GuiSqlEvent getIndicador() throws Exception {
		GuiSqlEvent var = new GuiBSPSqlEvent();
		var.setRecord(GetcDato().getObjEvent());
		var.GetcDato().setFDesde(GetcDato().getObjContrato().getFechaDesde());
		var.GetcDato().setFHasta(GetcDato().getObjContrato().getFechaHasta());
		return var;
	}
	public JWin getIndicadorGanancia() throws Exception {
		GuiSqlEvent var = new GuiBSPSqlEvent();
		var.setRecord(GetcDato().getObjEventGanancia());
		var.GetcDato().setFDesde(GetcDato().getObjContrato().getFechaDesde());
		var.GetcDato().setFHasta(GetcDato().getObjContrato().getFechaHasta());
		return var;
	}

	 public JWins getHistorico() throws Exception {
	  	GuiSerieCalculadas	c = new GuiSerieCalculadas();
	  	c.getRecords().addFilter("modelo", this.GetcDato().getModelo());
	  	c.getRecords().addFilter("variable", this.GetcDato().getVariable());
	  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
	  	c.getRecords().addFilter("fecha", this.GetcDato().getObjContrato().getFechaHasta(),"<=").setDynamic(true);
	  	c.getRecords().addFilter("fecha", this.GetcDato().getObjContrato().getFechaDesde(),">=").setDynamic(true);
	  	c.getRecords().addOrderBy("fecha","DESC");
	  	c.getRecords().setParent(this.GetcDato());
	  	return c;
	  	
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
