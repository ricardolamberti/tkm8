package pss.bsp.contrato.detalle;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalleAvianca.GuiDetalleAvianca;
import pss.bsp.contrato.detalleBackendDobleRutas.GuiDetalleBackendDobleRuta;
import pss.bsp.contrato.detalleBackendRutas.GuiDetalleBackendRuta;
import pss.bsp.contrato.detalleCopa.GuiDetalleCopa;
import pss.bsp.contrato.detalleCopaPorRutas.GuiDetalleCopaPorRutas;
import pss.bsp.contrato.detalleCopaWS.GuiDetalleCopaWS;
import pss.bsp.contrato.detalleDatamining.GuiDetalleDatamining;
import pss.bsp.contrato.detalleLatamFamilia.GuiDetalleDataminingTriFamilia;
import pss.bsp.contrato.detalleObjetivo.GuiDetalleObjetivo;
import pss.bsp.contrato.detalleRutas.GuiDetalleRuta;
import pss.bsp.contrato.detalleUpfront.GuiDetalleUpfront;
import pss.bsp.contrato.detalleUpfrontRutas.GuiDetalleUpfrontRuta;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JEjeMatrix;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.lists.JWinMatrix;

public class GuiDetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDetalle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Objetivo Datamining" ).setInToolbarMore(true);
    addActionNew( 10, "Nuevo Objetivo Copa FMS" ).setInToolbarMore(true);
    addActionNew( 12, "Nuevo Objetivo Copa WS" ).setInToolbarMore(true);
    addActionNew( 20, "Nuevo Objetivo Avianca" ).setInToolbarMore(true);
    addActionNew( 30, "Nuevo Objetivo Upfront" ).setInToolbarMore(true);
    addActionNew( 40, "Nuevo Objetivo Rutas" ).setInToolbarMore(true);
    addActionNew( 50, "Nuevo Objetivo Upfront Rutas" ).setInToolbarMore(true);
    addActionNew( 60, "Nuevo Objetivo Backend Rutas" ).setInToolbarMore(true);
    addActionNew( 70, "Nuevo Objetivo Backend Doble Rutas" ).setInToolbarMore(true);
    addActionNew( 80, "Nuevo Objetivo Latam familia" ).setInToolbarMore(true);
    addActionNew( 90, "Nuevo Objetivo Copa por rutas" ).setInToolbarMore(true);
    addActionNew(100, "Nuevo Objetivo" ).setInToolbarMore(true);
   }

//	@Override
//	public JWin createWin(JRecord zBD) throws Exception {
//		if (zBD==null) return super.createWin(zBD);
//		JWin oWin=((BizDetalle)zBD).getObjLogicaInstance().getWin();
//		JRecord oRec = ((BizDetalle)zBD).getObjLogicaInstance().getBiz();
//		oRec.copyProperties(zBD);
//		oWin.setRecord(oRec);
//		return oWin;
//	}
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==1) return BizUsuario.getUsr().IsAdminCompanyUser() && GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==10) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==12) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==20) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==30) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==40) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==50) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==60) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==70) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==80) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==90) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==100) return BizUsuario.getUsr().IsAdminCompanyUser()&& GetVision().equals(BizContrato.OBJETIVOS);
		return super.OkAction(a);
	}
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.createDatamining(), 0);
		if (a.getId()==10) return new JActNew(this.createCopa(), 0);
		if (a.getId()==12) return new JActNew(this.createCopaWS(), 0);
		if (a.getId()==20) return new JActNew(this.createAvianca(), 0);
		if (a.getId()==30) return new JActNew(this.createUpfront(), 0);
		if (a.getId()==40) return new JActNew(this.createRutas(), 0);
		if (a.getId()==50) return new JActNew(this.createUpfrontRutas(), 0);
		if (a.getId()==60) return new JActNew(this.createBackendRutas(), 0);
		if (a.getId()==70) return new JActNew(this.createBackendDobleRutas(), 0);
		if (a.getId()==80) return new JActNew(this.createLatamFamilia(), 0);
		if (a.getId()==90) return new JActNew(this.createCopaPorRutas(), 0);
		if (a.getId()==100) return new JActNew(this.createObjetivo(), 0);
		return super.getSubmit(a);
	}
	
	public JWin createDatamining() throws Exception {
		JWin oWin=new GuiDetalleDatamining();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createCopa() throws Exception {
		JWin oWin=new GuiDetalleCopa();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createCopaWS() throws Exception {
		JWin oWin=new GuiDetalleCopaWS();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createCopaPorRutas() throws Exception {
		JWin oWin=new  GuiDetalleCopaPorRutas();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createObjetivo() throws Exception {
		JWin oWin=new  GuiDetalleObjetivo();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createAvianca() throws Exception {
		JWin oWin=new GuiDetalleAvianca();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createUpfront() throws Exception {
		JWin oWin=new GuiDetalleUpfront();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createRutas() throws Exception {
		JWin oWin=new GuiDetalleRuta();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createUpfrontRutas() throws Exception {
		JWin oWin=new GuiDetalleUpfrontRuta();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createBackendRutas() throws Exception {
		JWin oWin=new GuiDetalleBackendRuta();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createBackendDobleRutas() throws Exception {
		JWin oWin=new GuiDetalleBackendDobleRuta();
		this.joinData(oWin);
		return oWin;
	}
	public JWin createLatamFamilia() throws Exception {
		JWin oWin=new GuiDetalleDataminingTriFamilia();
		this.joinData(oWin);
		return oWin;
	}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		if (zLista.isForExport() || isInMobile()) {
	    zLista.AddColumnaLista("descr_variable");
	    zLista.AddColumnaLista("detalle_nivel");
	    zLista.AddColumnaLista("conclusion");
	    zLista.AddColumnaLista("valor_objetivo");
	    zLista.AddColumnaLista("valor_fcontrato");
	    zLista.AddColumnaLista("valor_totalcontrato");
	    zLista.AddColumnaLista("nivel_alcanzado_estimada");
	    zLista.AddColumnaLista("ganancia_estimada");
	    zLista.AddColumnaLista("kicker");
		} else {
			zLista.addColumnWinAction("Flat", 1);
		}
  }

  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  //	zFiltros.NuevoEdit("año", "mes").SetValorDefault(JDateTools.getAnioActual());
  	super.ConfigurarFiltros(zFiltros);
  }
  
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {

  	super.asignFilterByControl(control);
  }
 
	public String getModeView() throws Exception {
		return JBaseWin.MODE_MATRIX;
	}
  @Override
  public void ConfigurarMatrix(JWinMatrix zLista) throws Exception {
  	 zLista.AddEjeMatrix("Contrato", "descr_contrato",JEjeMatrix.FILA);
//  	 zLista.AddEjeMatrix("F.desde", "fdesde",JEjeMatrix.FILA);
//  	 zLista.AddEjeMatrix("F.hasta", "fhasta",JEjeMatrix.FILA);
  	 zLista.AddEjeMatrix("Objetivo", "descr_variable",JEjeMatrix.FILA);
  	 zLista.AddEjeMatrix("Mes", "mes",JEjeMatrix.COLUMNA);
 //    zLista.AddColumnaLista("detalle_nivel").setMatrix(true);
//     zLista.AddColumnaLista("acumulativo").setMatrix(true);
//     zLista.AddColumnaLista("sig_valor_objetivo").setMatrix(true);
//     zLista.AddColumnaLista("valor_actual").setMatrix(true).setColorForeground("00DD00");
//     zLista.AddColumnaLista("nivel_alcanzado").setMatrix(true);
//     zLista.AddColumnaLista("ganancia").setMatrix(true).setColorForeground("0000FF");
     zLista.AddColumnaLista("html_data").setMatrix(true);
         // zLista.AddColumnaLista("nivel_alcanzado_estimada").setMatrix(true);
    // zLista.AddColumnaLista("ganancia_estimada").setMatrix(true).setColorForeground("00000FF");
  //   zLista.AddColumnaLista("conclusion").setMatrix(true);

  }
  
  @Override
  public boolean isWebPageable() {
  	return false;
  }
}
