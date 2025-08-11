package pss.bsp.contrato.consolidado;

import java.net.URLEncoder;

import pss.JPath;
import pss.bsp.GuiModuloBSP;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.contrato.BizContrato;
import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.event.action.GuiSqlEventAction;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.action.history.GuiSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.core.services.records.JFilterMap;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormLabel;
import pss.core.winUI.lists.JEjeMatrix;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.lists.JWinMatrix;
import pss.core.winUI.responsiveControls.JFormLabelResponsive;

public class GuiContratoConsolidados extends JWins implements IContenidoCarpeta {



  /**
   * Constructor de la Clase
   */
  public GuiContratoConsolidados() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiContratoConsolidado.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Objetivo" );
  	addAction(50, "Compartir", null, 10078, true, true);
  	addAction(55, "Imprimir", null, 10050, true, true).setImprimir(true);
  }

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==18) return true;
		if (a.getId()==20) return true;
		if (a.getId()==50) return true;
		if (a.getId()==55) return true;
		
		return a.getId()<5;
	}

  @Override
  public JAct getSubmitFor(final BizAction a) throws Exception {
  	if (a.getId()==18) return new JActWins(this);
  	if (a.getId()==20) return new JActWins(this);
  	if (a.getId()==50) return new JActNew(this.getCompartir(a), 4);
  	if (a.getId()==55) return new JActFileGenerate(this) { 
			public String generate() throws Exception {
				return imprimir(a);
			};
		};

  	return super.getSubmitFor(a);
  }
  
  public String imprimir(BizAction a) throws Exception {
		String file = this.hashCode()+".html";
		String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(70,"html",a.getFilterMap());
		JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
		return file; 	
  }

	public JWin getCompartir(BizAction a) throws Exception {
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("class_evento", BizBSPCompany.class.getCanonicalName());
		sql.getRecord().addFilter("id_evento", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("descripcion", "Informe contratos");
  	BizPlantilla pl = BizCompany.getObjPlantilla(BizBSPUser.getUsrBSP().getCompany(), "sys_informe");
		if (pl!=null) sql.getRecord().addFilter("id_plantilla",pl.getId());
		sql.setDropListener(this);
		this.setFilterMap(a.getFilterMap());
		return sql;
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {

  }
  JFilterMap filter;


  public JFilterMap getFilterMap() {
		return filter;
	}


	public void setFilterMap(JFilterMap action) {
		this.filter = action;
	}
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	JFormLabelResponsive c=zFiltros.addLabelResponsive("Resumen objetivos anuales");
  //	c.setSkinStereotype("big_label");
  	c.addBreak();
  	zFiltros.addEditResponsive("año", "anio").SetValorDefault(JDateTools.getAnioActual());
  	zFiltros.addCheckResponsive("Activos", "activo").SetValorDefault(BizContrato.ACTIVE);
  	super.ConfigurarFiltros(zFiltros);
  }

@Override
protected void asignFilterByControl(JFormControl control) throws Exception {
	if (control.getIdControl().equals("activo") && control.hasValue()) {
		if (control.getValue().equals("S") ) {
			this.getRecords().addFilter("activo","N","<>").setDynamic(true);
			return;
		} else {
			return;
		}
	}
  	super.asignFilterByControl(control);
  }
	public String getModeView() throws Exception {
		return JBaseWin.MODE_MATRIX;
	}
  @Override
  public void ConfigurarMatrix(JWinMatrix zLista) throws Exception {
   	 zLista.AddEjeMatrix("Contrato", "descr_contrato",JEjeMatrix.FILA);
  	 zLista.AddEjeMatrix("Objetivo", "descripcion",JEjeMatrix.FILA);
  	 zLista.AddEjeMatrix("Mes", "mes_literal",JEjeMatrix.COLUMNA);
     zLista.AddColumnaLista("html_data").setMatrix(true);

  }
  public String getHtmlTitulo() throws Exception {
  	String s="";
  	
  	s+="<table width=\"100%\">";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(255, 255, 255); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Sig.Objetivo</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 200, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Nivel</font></td>";
  	s+="</tr>";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(255, 255, 255); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Valor</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(125, 128, 200); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Ganancia</font></td>";
  	s+="</tr>";
  	s+="</table>";
  	return s;
  }
  
  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
   	if (zBaseWin instanceof GuiSqlEventAction) {
  		GuiSqlEventAction action = (GuiSqlEventAction)zBaseWin;
  		if (action.GetcDato().isAccionDOWNLOAD()) {
  			return new JActFileGenerate(action) { 
  				public String generate() throws Exception {
  		  		BizSqlEventHistory hist=((GuiSqlEventAction)getResult()).GetcDato().getObjSqlEvent().generarAviso(getFilterMap(),((GuiSqlEventAction)getResult()).GetcDato(),false);
  					return hist.getArchivoAsociado();
  				};
  			};
  		}
  		BizSqlEventHistory hist = action.GetcDato().execProcessExecute(this.getFilterMap());
  		if (hist==null) return new JActBack(this);
  		GuiSqlEventHistory ghist = new GuiSqlEventHistory();
  		ghist.setRecord(hist);
  		return new JActQuery(ghist);
  	}
   	return super.Drop(zBaseWin);
  }


	@Override
	public boolean read(String company, String id) throws Exception {
		this.addFilterAdHoc("company", company);
		return true;
	}


	@Override
	public JAct getActResultPreview(BizAction a, boolean inform) throws Exception {
		return new JActWins(this);
	}
	
	 public boolean isSystemProtect() throws Exception {
	  	return false;
	  }

}
