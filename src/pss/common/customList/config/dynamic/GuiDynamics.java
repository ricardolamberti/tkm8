package pss.common.customList.config.dynamic;

import pss.JPath;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.GuiCampo;
import pss.common.customList.config.field.filtro.BizFiltroSQL;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.customList.config.relation.JRelation;
import pss.common.event.sql.GuiSqlEvent;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JObjectFactory;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.GuiVirtuals;
import pss.core.win.IControl;
import pss.core.win.JBaseWin;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.BizListExcludeCol;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JEjeMatrix;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.lists.JWinMatrix;

public class GuiDynamics extends JWins {

	GuiCustomList customList;
	private JFilterMap filterDynamicMap;
	boolean editable=false;

  public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public JFilterMap getFilterDynamicMap() {
		return filterDynamicMap;
	}
	
	@Override
	public String toExcel() throws Exception {
		if (getModeView().equals(MODE_MATRIX)) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(getCustomList().GetcDato());
			l.GetcDato().fillFilters(this.getRecords());
			String file = l.hashCode()+".xlsx";//.xls
			String content = l.getHtmlView(24,"excel",null);
			JTools.MakeDirectory(JPath.PssPathTempFiles());
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		return super.toExcel();
	}
  
  public JWins createClone() throws Exception {
  	GuiDynamics din = (GuiDynamics)super.createClone();
  	din.setCustomList(customList);
  	din.filterDynamicMap = getFilterDynamicMap();
  	return din;
  }

	public void addFilterDynamicMap( JFormControl control) throws Exception {
		if (this.filterDynamicMap == null) this.filterDynamicMap = new JFilterMap();

		this.filterDynamicMap.addFilterMap(control.getIdControl(), control.getValue());
	}

	public void setCustomList(GuiCustomList value) {
		this.customList=value;
	}
	public GuiCustomList getCustomList() {
		return this.customList;
	}
  /**
   * Constructor de la Clase
   */
  public GuiDynamics() throws Exception {
  }

//  public JWins getTargetWins() throws Exception {
//  	return this.customList.getTargetWins();
//  }

  public int     GetNroIcono() throws Exception  { return 10027; } 
  public String  GetTitle()    throws Exception  { return ( this.customList!=null?this.customList.GetcDato().getDescription():"Listas Dinámicas"); }
  public Class<? extends JWin>  GetClassWin() throws Exception { return GuiDynamic.class;}
	
//  public JWin createWin(JRecord zBD) throws Exception {
//		return new GuiDynamic(this.customList, this.getSourceWins().createJoinWin(zBD));
//	}

  public JWin getWinRef() throws Exception {
  	GuiDynamic g= (GuiDynamic)this.getRecord();
  	return g;
  }
  
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
  @Override
  public boolean matrixDisplayTotales() throws Exception {
  	return getCustomList().GetcDato().isTotalizar();
  }

  @Override
  public boolean matrixDisplaySubTotales() throws Exception {
   	return getCustomList().GetcDato().isSubTotalizar();
      }
  
  @Override
  public long getMaxToOthers() throws Exception {
   	return getCustomList().GetcDato().getLimite();
	}
  @Override
  public String getModeView() throws Exception  {
  	if (this.customList.GetcDato().isMatriz()) return MODE_MATRIX;
  	if (this.customList.GetcDato().isAgrupado()) return MODE_JSON;
  	if (this.customList.GetcDato().isLista()) return MODE_JSON;
  	return MODE_FORM;
  }

  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//		BizAction a = addAction(710, "Imprimir",KeyEvent.VK_F7, null, 10050, true, true);
//		a.setNuevaVentana(true);
//		a.setInToolbar(true);
//		a.setImportance(1);
  	addAction(720, "Agr.Indicador manual", null, 10007, true, true);
  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==720) return BizUsuario.getUsr().IsAdminCompanyUser();
  	return super.OkAction(a);
  }

	public JAct getSubmitFor(BizAction a) throws Exception {
//		if (a.getId() == 710)
//			return new JActFileGenerate(this, a.getId()) {
//				@Override
//				public String generate() throws Exception {
//					return imprimir(this.getActionSource());
//				}
//			};
			if (a.getId() == 720) return new JActNew(getManualNewEvento(),0);
			
			return super.getSubmitFor(a);
		}
	
	public GuiSqlEvent getNewIndicador() throws Exception {
		GuiSqlEvent ind = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
		return ind;
	}
  public JWin getManualNewEvento() throws Exception {
  	GuiSqlEvent sqlEvent = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
  	BizCustomList biz = this.customList.GetcDato();
    sqlEvent.GetcDato().addFilter("company",biz.getCompany());
    sqlEvent.GetcDato().addFilter("custom_list",customList.GetcDato().getListId());
    sqlEvent.GetcDato().addFilter("class_detalle",this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass());
  	sqlEvent.GetcDato().setClassDetalle(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass());
  	sqlEvent.GetcDato().setCustomList(customList.GetcDato().getListId());
  	sqlEvent.GetcDato().setCompany(biz.getCompany());
  	sqlEvent.SetVision("M");
  	return sqlEvent;
  }  
  


  @Override
	public void ConfigurarFiltros(JFormFiltro filtros) throws Exception {
//   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//   	JFormFiltro winsfiltros = new JFormFiltro(wins);
//   	wins.ConfigurarFiltros(winsfiltros);
//    filtros.addFilter(winsfiltros);
  	if (true)return;
  	JList<String> lista = JCollectionFactory.createList();
  	int i=1;
 
  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjAllCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		JFormControl c=null; 
  		if (f.isOperOR()) {
  			filtros.addLabelResponsive(f.getDescrFiltroOR(true)).setIdControl(""+f.getOrden());
    		i++;
  			continue;
  		}
  		if (f.getCampo().equals(""))
  			continue;
  		else if (getFilterValue(""+f.getNameField())!=null) {
  			//getRecords().addFilter(f.getFiltro(), getFilterValue(""+f.getOrden()),f.getOperador());
  			continue;
  		}
  		String typeDefault="";

  		if (!f.hasFiltro()) {
  			if (f.hasStadistic()) {
    			c=filtros.addWinLovResponsive(f.getDescrCampoWithOp(), null, f.createProp(), f.createFixedProp(), f.getOptions(), f.getDescrCampoWithOp());
    			f.setDinamico(true);
    			c.setOperator("IN");
  			} else if (!f.isNumeric()) {
    			c=filtros.addEditResponsive(f.getDescrCampoWithOp(), JBaseForm.CHAR,null, f.createProp(), f.getExtraFilterProperty(), "like", true);
    			f.setDinamico(true);
    			f.setOperador("like");
  			}
   	  } else if (f.getFuncion().equals(BizField.FUNTION_COUNT) ) {
  			c=filtros.addEditResponsive(f.getDescrCampoWithOp(), JBaseForm.LONG,null,  f.createProp(), f.getExtraFilterProperty(), "=", true);
  		} else if (f.getOperador().equals(BizFuncion.FUNTION_INTERVALO)) {
  			c=filtros.addEditResponsive(f.getDescrCampoWithOp(), JBaseForm.CHAR,null,  f.createProp(), f.getExtraFilterProperty(), "=", true);
  		} else if (f.getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)||f.getFuncion().equals(BizFuncion.FUNTION_PROXIMOS) ||
  				f.getFuncion().equals(BizFuncion.FUNTION_ANIO) || f.getFuncion().equals(BizFuncion.FUNTION_BIMESTRE) ||
  				f.getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE) || f.getFuncion().equals(BizFuncion.FUNTION_SEMESTRE) ||
  				f.getFuncion().equals(BizFuncion.FUNTION_MES) || f.getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE) ) {
  			c=filtros.addEditResponsive(f.getDescrCampoWithOp(), JBaseForm.LONG,null,  f.createProp(), f.getExtraFilterProperty(), "=", true);
  		} else if (f.getOperador().equals(BizFuncion.FUNTION_INTERVALO)) {
  			c=filtros.addEditResponsive(f.getDescrCampoWithOp(), JBaseForm.CHAR,null,  f.createProp(), f.getExtraFilterProperty(), "=", true);
  		} else if (f.isDateInput() ) {
  			c = filtros.addCDateResponsive(f.getDescrCampoWithOp(),null, f.createProp(), f.createFixedProp());
  			if (f.getValor().equals("")) c.SetValorDefault(this.customList.GetcDato().getHoy()); //truch
  			typeDefault="F";
  		} else if (f.isDateTimeInput() && f.getOperador().equals("")) {
  			c=filtros.nuevoCDateTimeResponsive(f.getDescrCampoWithOp(),null, f.createProp(), f.createFixedProp());
  			if (f.getValor().equals("")) c.SetValorDefault(this.customList.GetcDato().getHoy()); //truch
  			typeDefault="F";
  		} else if (f.isCheckInput()) {
  			c=filtros.nuevoCheckResponsive(f.getDescrCampoWithOp(),null, f.createProp(), f.createFixedProp());
  		} else if (f.isLOVInput()) {
  			c=filtros.NuevoFormLov(f.getDescrCampoWithOp(), f.createProp(), f.createFixedProp(), this.createControlWinLOV(f));
  		} else {
  			c=filtros.addEditResponsive(f.getDescrCampoWithOp(), JBaseForm.CHAR,null,  f.createProp(), f.createFixedProp(), f.getOperador(), true);
  		}
  		if (c==null) continue;
  		if (f.getOperador().equals(BizFuncion.FUNTION_INTERVALO)) 
  			c.SetValorDefault(f.getValor()+", "+f.getValor2());
  		else if (typeDefault.equals("F") && !f.getValor().equals("")) c.SetValorDefault(JDateTools.StringToDate(f.getValor(), "dd/MM/yyyy"));
  		else if (!f.getValor().equals("")) c.SetValorDefault(f.getValor());
  		else if (f.getCampoClave().equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_MES)) c.SetValorDefault((JDateTools.getFirstDayOfMonth(BizUsuario.getUsr().todayGMT())));
  		else if (f.getCampoClave().equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_MES)) c.SetValorDefault((JDateTools.getLastDayOfMonth(BizUsuario.getUsr().todayGMT())));
  		else if (f.getCampoClave().equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_ANO)) c.SetValorDefault((JDateTools.getFirstDayOfYear(BizUsuario.getUsr().todayGMT())));
  		else if (f.getCampoClave().equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_ANO)) c.SetValorDefault((JDateTools.getLastDayOfYear(BizUsuario.getUsr().todayGMT())));
  		else if (f.getCampoClave().equalsIgnoreCase(JCompanyBusiness.FECHA_ACTUAL)) c.SetValorDefault(BizUsuario.getUsr().todayGMT());
  		c.setIdControl("filtro_"+f.getNameField()+"_"+f.getOrden());
  		c.setOperator(f.getOperador());
  		c.SetReadOnly( !f.isDinamico() );
//  		addFilterMap(c);
  		i++;
  	}
  	JIterator<BizFiltroSQL> iterF = this.customList.GetcDato().getFiltroSQLs().getStaticIterator();
  	while (iterF.hasMoreElements()) {
  		BizFiltroSQL f = iterF.nextElement();
  		JFormControl c; 
  		if (f.isDateInput()) {
  			c = filtros.addCDateResponsive(f.getFilterName(),null,  f.createProp(), f.createFixedProp());
  		} else if (f.isDateTimeInput()) {
  			c=filtros.nuevoCDateTimeResponsive(f.getFilterName(),null,  f.createProp(), f.createFixedProp());
  		} else if (f.isCheckInput()) {
  			c=filtros.nuevoCheckResponsive(f.getFilterName(),null,  f.createProp(), f.createFixedProp());
  		} else { 
  			c=filtros.addEditResponsive(f.getFilterName(),null,  JBaseForm.CHAR, f.createProp(), f.createFixedProp(),"=",true);
  		}
//  		c.setDisplayOnly(true);
  	  if (f.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_MES)) c.SetValorDefault((JDateTools.getFirstDayOfMonth(BizUsuario.getUsr().todayGMT())));
  		else if (f.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_MES)) c.SetValorDefault((JDateTools.getLastDayOfMonth(BizUsuario.getUsr().todayGMT())));
  		else if (f.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_ANO)) c.SetValorDefault((JDateTools.getFirstDayOfYear(BizUsuario.getUsr().todayGMT())));
  		else if (f.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_ANO)) c.SetValorDefault((JDateTools.getLastDayOfYear(BizUsuario.getUsr().todayGMT())));

  		c.setIdControl(f.getFiltro()+"_100"+f.getOrden());
  		i++;
  	}
  }
  public String imprimir(BizAction a) throws Exception {
  	return this.customList.GetcDato().imprimirResumen(a.getFilterMap(),false);
  }
	private JProperty getCantidadProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, "días", "Cantidad", null, "", true, true, 18, 0, null, null, null);
	}
	private JProperty getEmptyProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, "empty_field", "empty_field", null, "", true, true, 18, 0, null, null, null);
	}

	
	

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	//zLista.AddIcono("");
  	boolean findCampo=false;

  	JIterator<BizCampo> iterE = this.customList.GetcDato().getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo c = iterE.nextElement();
  		JColumnaLista l=null;
  		try {
  			l=zLista.AddColumnaLista(c.createProp().getObjectClass(), c.getNombreColumna(), c.createFixedProp());
  		} catch (Exception e) {
  			l=zLista.AddColumnaLista(JString.class, "Error", null);
  		}
			l.setVisible(true);
			l.setMatrix(false);
			
			if (c.getCalculeDiferencia()) {
				JIterator<BizCampo> itd=c.getObjCampoDiferencia().getIterator();
				while (itd.hasMoreElements()) {
					BizCampo campoDif = itd.nextElement();
		  		try {
		    		 l=zLista.AddColumnaLista(JFloat.class, "Dif."+campoDif.getForceNombreColumna(), c.getDifProperty(c.getNameFieldDif(campoDif.getCampo()),"Dif."+campoDif.getNombreColumna()));
		  		} catch (Exception e) {
		  			PssLogger.logError(e);
		  			l=zLista.AddColumnaLista(JString.class, "Error", null);
		  		}
					l.setVisible(true);
					l.setMatrix(false);
		  		if (c.getPorcentajeDiferencia()){
			  		try {
			    		 l=zLista.AddColumnaLista(JFloat.class, "Dif.Porc."+campoDif.getForceNombreColumna(), c.getDifProperty(c.getNameFieldDifPorc(campoDif.getCampo()),"Dif.porc."+campoDif.getNombreColumna()));
			  		} catch (Exception e) {
			  			PssLogger.logError(e);
			  			l=zLista.AddColumnaLista(JString.class, "Error", null);
			  		}
						l.setVisible(true);
						l.setMatrix(false);
		  			
		  		}
						
				}

			}
			
			
			BizCampo ranking = c.getObjCampoOrden();
			if (ranking!=null) {
	  		JColumnaLista lo=null;
				lo = zLista.AddColumnaLista(new JLong().getObjectClass(), "", ranking.getRankProperty());
				lo.setVisible(false);
				lo.setMatrix(false);
			}
  	}
  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjAllCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo c = iter.nextElement();
  		if (!c.isRolCampo()) continue;
  		if(c.getCampo()==null || c.getCampo().equals("")) continue;
  		JProperty prop = c.createFixedProp();
  		if (prop==null) continue;
  		JColumnaLista l=zLista.AddColumnaLista(c.createProp().getObjectClass(), c.getForceNombreColumna(), prop);
  		l.setVisible(c.getVisible());
  		l.setMatrix(customList.GetcDato().isMatriz()||customList.GetcDato().isGrafico());
  		if (c.getHasColores()) {
  			l.setColorBackground(c.getColorBackgorund());
  			l.setColorForeground(c.getColorForegorund());
  		}
  		l.setMarcaBottom10(c.getHasMarcaBottom10());
  		l.setMarcaTop10(c.getHasMarcaTop10());
  		l.setMarcaMayores(c.getHasMarcaMayores());
  		l.setPorcentaje(c.isPorcentaje());
  		l.setMarcaMenores(c.getHasMarcaMenores());
  		l.setColorTopBackground(c.getColorTopBackgorund());
  		l.setColorBottomBackground(c.getColorBottomBackgorund());
  		l.setMayoresA(c.getMayoresA());
  		l.setMenoresA(c.getMenoresA());
  		l.setFunctionTotalizadora(c.getFuncion());
  		if (c.getVisible()) findCampo=true;
  	}
  	if (!findCampo) {
  		zLista.AddColumnaLista(Long.class,"Agregue campo",null).setMatrix(true);
  	}
  }

  public void ConfigurarMatrix(JWinMatrix zMatrix) throws Exception {
  	boolean findFila =false;
  	boolean findColumna =false;
  	 JIterator<BizCampo> iter = this.customList.GetcDato().getObjEjes().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo c = iter.nextElement();
  		JProperty p = c.createFixedProp();
  		if (p==null) continue;
  		findFila|=c.isRolFila()?true:false;
  		findColumna|=c.isRolColumna()?true:false;
  		BizCampo ranking = c.getObjCampoOrden();
  	  JEjeMatrix eje =null;
  	  if (this.customList.GetcDato().isViewTableInvertido())
    	  eje=zMatrix.AddEjeMatrix(c.getForceNombreColumna(),c.isRolFila()?JEjeMatrix.COLUMNA:JEjeMatrix.FILA, p);
  	  else
 	  		eje=zMatrix.AddEjeMatrix(c.getForceNombreColumna(),c.isRolFila()?JEjeMatrix.FILA:JEjeMatrix.COLUMNA, p);
  	  eje.setEje(c);
  	  eje.setAsc(c.getOrdenAscDesc().equals("ASC"));
  	  eje.setLimite(c.getOrdenLimite());
  	  eje.setRankCampo(ranking==null?c.getNameField():ranking.getNameField());
//  	  if (c.hasOrdenAscDesc())
//  	  	eje.AddOrdenMatrix(c.getOrdenOrden(),new JOrdenMatrix( c.getNameField(), c.getOrdenLimite(), c.getOrdenAscDesc().equals("ASC")));
  		
  	}
 	if (!findFila) zMatrix.AddEjeMatrix("Datos",JEjeMatrix.FILA);
//  	if (!findColumna) zMatrix.AddEjeMatrix("Agregue Columna",JEjeMatrix.COLUMNA);

//  	JIterator<BizCampo> iterO = this.customList.GetcDato().getObjAllCampos().getStaticIterator();
//  	while (iterO.hasMoreElements()) {
//  		BizCampo c = iterO.nextElement();
//  		if (!c.hasOrdenAscDesc()) continue;
//  		zMatrix.AddOrdenMatrix(c.getOrdenOrden(),new JOrdenMatrix(c.getRankNameField(),c.getOrdenLimite(), c.getOrdenAscDesc().equals("ASC")));
//  	}
  	
  }
  private IControl createControlWinLOV(final BizCampo f) throws Exception {
  	IControl c = new JControlWin() {
  		public JWins getRecords(boolean one) throws Exception {return createWinsByFiltro(false, f, 1);};
  	};
  	return c;
  }
  public static JWins createWinsByFiltro(boolean one, BizCampo f, int v) throws Exception {
		String claseWins = f.getClassWinsTarget();
		if (claseWins==null) return null;
		JWins wins = (JWins)Class.forName(claseWins).newInstance();
		if (wins instanceof GuiVirtuals) 
			return JWins.createVirtualWinsFromMap(f.getMapTarget());
	
		f.assingFiltersLOV(one, wins.getRecords(), v);
		return wins;
  }
  

  
  public static JWins createWinsByEje(boolean one, BizCampo f) throws Exception {
		String claseWins = f.getClassWinsTarget();
		if (claseWins==null) return null;
		JWins wins = (JWins)Class.forName(claseWins).newInstance();
		if (wins instanceof GuiVirtuals) 
			return JWins.createVirtualWinsFromMap(f.getMapTarget());
		else {
			return wins;
		}
  }


  public void readAll() throws Exception {
  	
  	try {
//  		Thread.sleep(1000000);
  		if (customList.GetcDato().getObjRelation()==null) return;
			JList<RFilter> f=(this.getRecords().getFilters());
			this.assignDynamicFilterSQLs();
			this.customList.GetcDato().setFiltrosDinamicos(f);
			JRecords<?> records = this.customList.GetcDato().getAllRecords();
			this.setRecords(records);
			super.readAll();
//			records.applyFiltersFromList(f); //  RJL porque falla el totalizador, pero para algo servia...
		} catch (Exception e) {
			PssLogger.logError(e);
			JBDatos.GetBases().getPrivateCurrentDatabase().GetConnection().commit();
			throw new Exception ("Error en SQL generado: ["+e.getMessage()+"]");
		}
  }
  
  
  
//	public boolean hasFiltersProcess(BizAction a) throws Exception {
//		if (!this.GetcDato().hasFiltrosManuales()) return true;
//		if (!a.hasFilterMap()) return false;
//		return a.getFilterMap().hasFilters();
//	}
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().startsWith("filtro"))
			addFilterDynamicMap(control);
		super.asignFilterByControl(control);
	}

  private void assignDynamicFilters(JRecords<?> r) throws Exception {
		customList.GetcDato().fillFilters(this.getRecords());
//  	if (this.getRecords().getFilters()==null) return;
//  	JIterator<RFilter> iter = this.getRecords().getFilters().getIterator();
//  	while (iter.hasMoreElements()) {
//  	pwr	RFilter f = iter.nextElement();
//  		if (!f.isDynamic()) continue;
//  		if (f.isVirtual()) {
//    		BizFiltro ft = this.customList.GetcDato().findDynamicFilter(f.getField(), f.getOperator());
//    		if (ft==null) continue;
//    		ft.setValor(f.getValue());
//    		r.addFixedFilter(ft.getFixedFilter(false,false));
//    		continue;
//  		}
//  		BizFiltro ft = this.customList.GetcDato().findDynamicFilter(f.getField(), f.getOperator());
//  		if (ft==null) continue;
//  		ft.setValor(f.getValue());
//  		r.addFixedFilter(ft.getFixedFilter(false,false));
//  	}
  }
  private void assignDynamicFilterSQLs() throws Exception {
  	if (this.getRecords().getFilters()==null) return;
  	JIterator<RFilter> iter = this.getRecords().getFilters().getIterator();
  	while (iter.hasMoreElements()) {
  		RFilter f = iter.nextElement();
 	 		BizFiltroSQL ftsql = this.customList.GetcDato().findDynamicFilterSQL(f.getField(), f.getOperator());
 	 		if (ftsql!=null) {
 	 			ftsql.setValor(f.getValue());
	  		continue;
 	 		}
  	}
  }
  
  public JWin getRecord() throws Exception {
  	GuiDynamic w =new GuiDynamic(this.customList);
  	w.setFilterDynamicMap(this.getFilterDynamicMap());
  	w.setCustomList(this.customList);
  	JBaseRegistro set = this.getRecords().RecordSet();
  	this.loadKeysFormRecordSet(w, set);
  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!f.isRolCampo()) continue;
  		if(f.getCampo()==null || f.getCampo().equals("")) continue;

			JRecord source = f.getObjRelation().getClassTarget().newInstance();
			JObject<?> obj;
			JObject<?> geoobj=new JGeoPosition();
			JProperty p=null;

			if (f.isPorcentaje())
				obj = new JFloat().setPrec(2); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_MES))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_ANIO))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_AYER))
				obj = new JDate(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_ANOMES))
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_ANOSEM))
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_BIMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_TRIMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_CUATRIMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_SEMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_DIA_MES))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_DIA_ANO))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_DIA_SEMANA))
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_LIT))
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_FORMULA)||f.getCampo().equals(BizCampo.FUNTION_FORMULA))
				obj = new JString(); 
			else if (f.isCampoCantidad())
				obj = new JLong(); 
			else if (!f.hasCampo())
				obj = new JFloat(); 
			else {
				p = source.getFixedProp(f.getCampo());
				obj = f.hasFormat()?new JString():source.getProp(f.getCampo());
			}
  		if (p!=null && !p.isTableBased() ) {
  			if (set==null) continue;
  			boolean read = false;
  			boolean empty = p.getDependiente()==null;
  			JStringTokenizer t = JCollectionFactory.createStringTokenizer(empty?"":p.getDependiente(), ';');
  			while (t.hasMoreTokens()) {
  				String c = t.nextToken();
  				JObject<?> depObj = source.getProp(c);
  				if (source.getFixedProp(c).isKey()) read=true;
  				source.loadFieldFromDataSet(f.getNameField(c), depObj, set);
  				w.getRecord().addItem(f.getNameField(c), depObj);
  			}
  			try {
  				if (read) source.readByKeys();
  				if (empty) {
  					p = source.getFixedProp(f.getCampo());
  					obj = new JString();
  				} else if (p.isRecord()) {
	  				JObject deep = source.getPropDeep(f.getCampo());
	  				if (deep.getClass().isAnonymousClass())
	  					obj = (JObject)deep.getClass().getSuperclass().newInstance();
	  				else
	  					obj = (JObject)deep.getClass().newInstance();
	  					
	  				obj.setValue(deep.asObject());
  				} else
  					obj.setValue(source.getProp(f.getCampo()).toString());
  			} catch (Exception e) {
  				PssLogger.logError(e);
  				obj = new JString();
  				obj.setValue("error");
  			}
  		} else {
  				source.loadFieldFromDataSet(f.getNameField(), obj, set);
  		}
  		
	  	w.getRecord().addItem(f.getNameField(), obj);
	  	if (this.customList.GetcDato().isWithGeo() && f.getGeoCampo()!=null && !f.getGeoCampo().equals("")) {
	    	String table = f.getObjGeoRelation().getTargetAlias(f.getGeoCampo());
	    	if (table.equals("")) 
	    		table=f.getTargetAlias();
         
	    	JRecord sourceGeo =f.getObjRecordGeoOwner();
	  		sourceGeo.loadFieldFromDataSet(f.getGeoNameField(table,f.getGeoCampo()), geoobj, set);
		  	w.getRecord().addItem(f.getGeoNameField(table,f.getGeoCampo()), geoobj);
	  	}
	  	

  	}
  	w.getRecord().setDatosLeidos(true);
  	
  	JIterator<BizCampo> iterE = this.customList.GetcDato().getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		JRelation rel = f.getObjRelation();
  		if (rel==null) continue;
			JRecord source = rel.getClassTarget().newInstance();
			JObject<?> obj;
			JObject<?> geoobj=new JGeoPosition();
			JProperty p=null;
			if (f.isCampoCantidad())
				obj = new JLong(); 
			else if (f.isCampoFormula())
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_MES))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_ANIO))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_AYER))
				obj = new JDate(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_ANOMES))
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_ANOSEM))
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_BIMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_TRIMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_CUATRIMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_SEMESTRE))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_DIA_MES))
				obj = new JLong(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_DIA_ANO))
				obj = new JLong();
			else if (f.getFuncion().equals(BizCampo.FUNTION_DIA_SEMANA))
				obj = new JString(); 
			else if (f.getFuncion().equals(BizCampo.FUNTION_LIT))
				obj = new JString(); 
			else if (!f.hasCampo())
				obj = new JFloat(); 
			else {
				p = source.getFixedProp(f.getCampo());
				obj = f.hasFormat()?new JString():source.getProp(f.getCampo());
			}
			if (f.getCalculeDiferencia()) {
					
				JIterator<BizCampo> itd=f.getObjCampoDiferencia().getIterator();
				while (itd.hasMoreElements()) {
					JFloat objDif = new JFloat();
					JFloat objPorcDif = new JFloat();
					JString objDescrDif = new JString();
					BizCampo campoDif = itd.nextElement();
		 			source.loadFieldFromDataSet(f.getNameFieldDif(campoDif.getCampo()), objDif, set);
	 				source.loadFieldFromDataSet(f.getNameColFieldDif(campoDif.getCampo()) , objDescrDif, set);

	 				w.getRecord().addItem(f.getNameFieldDif(campoDif.getCampo()), objDif);
					w.getRecord().addItem(f.getNameColFieldDif(campoDif.getCampo()), objDescrDif);
		 			
					if (f.getPorcentajeDiferencia()) {
		 				source.loadFieldFromDataSet(f.getNameFieldDifPorc(campoDif.getCampo()), objPorcDif, set);
						w.getRecord().addItem(f.getNameFieldDifPorc(campoDif.getCampo()), objPorcDif);
		 			}
				}


	 		}
			if (p!=null && !p.isTableBased()) {
  			boolean read=false;
  			boolean empty = p.getDependiente()==null;
  			JStringTokenizer t = JCollectionFactory.createStringTokenizer(empty?"":p.getDependiente(), ';');
  			while (t.hasMoreTokens()) {
  				String c = t.nextToken();
  				JObject<?> depObj = source.getProp(c);
  				if (source.getFixedProp(c).isKey()) read=true;
  				source.loadFieldFromDataSet(f.getNameField(c), depObj, set);
  				w.getRecord().addItem(f.getNameField(c), depObj);
  			}
  			try {
  				if (read) source.readByKeys();
  				if (empty) {
	  				obj.setValue("");
  				} if (p.isRecord()) {
	  				JObject deep = source.getPropDeep(f.getCampo());
	  				if (deep.getClass().isAnonymousClass())
	  					obj = (JObject)deep.getClass().getSuperclass().newInstance();
	  				else
	  					obj = (JObject)deep.getClass().newInstance();
	  				obj.setValue(deep.asObject());
  				} else
  					obj.setValue(source.getProp(f.getCampo()).toString());
  			} catch (Exception e) {
  				obj = new JString();
  				obj.setValue("error");
  			}
  		}
  		else {
  			source.loadFieldFromDataSet(f.getNameField(), obj, set);
  		}
	  	w.getRecord().addItem(f.getNameField(), obj);
	  	if (customList.GetcDato().isWithGeo() && f.getGeoCampo()!=null && !f.getGeoCampo().equals("")) {
	    	String table = f.getObjGeoRelation().getTargetAlias(f.getGeoCampo());
	    	if (table.equals("")) 
	    		table=f.getTargetAlias();

	    	JRecord sourceGeo =f.getObjRecordGeoOwner();
	  		sourceGeo.loadFieldFromDataSet(f.getNameField(table,f.getGeoCampo()), geoobj, set);
		  	w.getRecord().addItem(f.getNameField(table,f.getGeoCampo()), geoobj);
	  	}
//			BizCampo ranking = f.getObjRanking();
//			if (ranking!=null) {
//				JLong objRank=new JLong();
//		  	w.getRecord().addItem(ranking.getRankNameField(), objRank);
//  			source.loadFieldFromDataSet(ranking.getRankNameField(), objRank, set);
//			}

	  }
  	
  	if (customList.GetcDato().isMatriz()) {
    	JIterator<BizCampo> iterO = this.customList.GetcDato().getObjCampos().getStaticIterator();
    	while (iterO.hasMoreElements()) {
    		BizCampo o = iterO.nextElement();
    		if (!o.hasOrdenAscDesc()) continue;
    		JRelation rel = o.getObjRelation();
    		if (rel==null) continue;
  			JRecord source = rel.getClassTarget().newInstance();
  			JFloat objRank=new JFloat();
  	  	w.getRecord().addItem(o.getNameField(), objRank);
  			source.loadFieldFromDataSet(o.getNameField(), objRank, set);
    	}
  		
  	}
  	
  	w.getRecord().setDatosLeidos(true);
  	this.joinData(w);
  	

  	return w;
  }

  private void loadKeysFormRecordSet(JWin w, JBaseRegistro set) throws Exception {
  	if (this.customList.GetcDato().isAgrupado()) return;
  	if (this.customList.GetcDato().isMatriz()) return;
  	if (this.customList.GetcDato().isGrafico()) return;
  	if (set==null) return;
  	JRecord source = this.customList.GetcDato().getObjRelation().getObjRecordTarget();
  	JIterator<JProperty> iter = source.getFixedProperties().getValueIterator();
  	while (iter.hasMoreElements()) {
  		JProperty p = iter.nextElement();
  		if (!p.isKey()) continue;
			JObject obj = JObjectFactory.virtualCreate(source.getProp(p.GetCampo()).getObjectType());
			if (source.getProp(p.GetCampo()).getObjectType().equals("JDATE")) {
				try {
					JDateTools.StringToDate(set.CampoAsStr(p.GetCampo().replaceAll( "\"" , "")), JDateTools.DEFAULT_DATE_FORMAT);
					obj.setValue(set.CampoAsStr(p.GetCampo().replaceAll( "\"" , "")));
				} catch (Exception e) { //si viene de la serie temporal pruebo ese formato
					obj.setValue(JDateTools.YYYYMMDDToDefault(set.CampoAsStr(p.GetCampo().replaceAll( "\"" , ""))));
				}
			} else
				obj.setValue(set.CampoAsStr(p.GetCampo().replaceAll( "\"" , "")));
  		w.getRecord().addItem(p.GetCampo(), obj);
  	}
  }

//	public boolean isEOF() throws Exception {
//		return this.getSourceWins().isEOF();
//	}
	@Override
	public void ConfigurarGraficos(JWinList lista) throws Exception {
			try {
				if (!this.customList.GetcDato().isGrafico()) return;
				lista.addGrafico(this.customList.GetcDato().buildGraph().getGraph());
			} catch (Exception e) {
				PssLogger.logError(e);
			}
	}
	
	public boolean hasGrafico(JWinList oWinlist) throws Exception {
		return this.getCustomList().GetcDato().isGrafico();// this.customList.GetcDato().getGraficos().sizeStaticElements()!=0;
	}
	
	public boolean canReadAll(BizAction a) throws Exception {
  	if (this.customList.GetcDato().hasFiltrosManuales())
//  		return a.hasFilterMap();
  		return !a.waitMoreFilters();
  	return true;
	}

	public long selectSupraCount() throws Exception {
		return -1L;
	}

	public String getDragForeground(String zone) throws Exception {
		if (!GetVision().equals("PREVIEW")) return null;
		return "000000";
	}
	public String getDragBackground(String zone) throws Exception {
		if (GetVision().equals("PREVIEW") && !getCustomList().acceptDragMode()){
			 if (zone.startsWith("column")) 
				 return "6bb8cf";
			 if (zone.startsWith("campo")) 
				 return "aebabd";
			 if (zone.startsWith("fila")) 
				 return "6bb8cf";
			 if (zone.startsWith("total")) 
				 return "bad2d9";
			 if (zone.startsWith("filtro")) 
				 return "6bb8cf";
			 return null;
			
		}
		if (!GetVision().equals("PREVIEW")) {
			return null;
		}
		 if (zone.startsWith("column")) 
			 return "04a76f";
		 if (zone.startsWith("campo")) 
			 return "e99d4b";
		 if (zone.startsWith("fila")) 
			 return "c4c601";
		 if (zone.startsWith("total")) 
			 return "2ab116";
		 if (zone.startsWith("filtro")) 
			 return "b7da7f";
		 return null;
	}
	
	@Override
	public JBaseWin getListenerForDragAndDropWeb(String zone,JBaseWin all) throws Exception {
		if (zone.startsWith("column") || zone.startsWith("fila") || zone.startsWith("campo") || zone.startsWith("filtro")) {
			JBaseWin obj= getObjectDrageable(zone);
			if (obj!=null) return obj;
		}
		return customList;
	}

	public boolean acceptDrop(String zone) throws Exception {
		return (getCustomList().acceptDragMode())
;
	}

	public String getZoneDrop(String zone) throws Exception {
		if (zone.startsWith("filterPanel")) return "filtro";
		if (zone.startsWith("data")) return "filtro";
		if (zone.startsWith("total")) return null;
		return zone;
	}

	@Override
	public JBaseWin getObjectDrageable(String zone) throws Exception {
		if (!getCustomList().acceptDragMode()) return null;
		if (zone.startsWith("data")) return null;
		if (zone.startsWith("fila") && !JTools.getNumberEmbedded(zone).equals("")) return null;
		if (zone.startsWith("column") && !JTools.getNumberEmbedded(zone).equals("")) return null;
		if (zone.startsWith("fila")) {
			JIterator <BizCampo> it = customList.GetcDato().getObjEjes().getStaticIterator();
			String title= zone.substring(5);
			while (it.hasMoreElements()) {
				BizCampo c = it.nextElement();
				if (!c.isRolFila()) continue;
				if (c.getDescrCampo().equals(title)) {
					GuiCampo eje = new GuiCampo();
					c.keysToFilters();
					c.dontThrowException(true);
					if (!c.read()) return null;
					eje.setRecord(c);
					return eje;
				}
			}
		}
		if (zone.startsWith("column")) {
			JIterator <BizCampo> it = customList.GetcDato().getObjEjes().getStaticIterator();
			String title= zone.substring(7);
			while (it.hasMoreElements()) {
				BizCampo c = it.nextElement();
				if (!c.getRol().equals(BizCampo.COLUMNA)) continue;
				if (c.getDescrCampo().equals(title)) {
					GuiCampo eje = new GuiCampo();
					c.keysToFilters();
					c.dontThrowException(true);
					if (!c.read()) return null;
					eje.setRecord(c);
					return eje;
				}
			}
		}
		if (zone.startsWith("campo")) {
			JIterator <BizCampo> it = customList.GetcDato().getObjCampos().getStaticIterator();
			String title= zone.substring(6);
			while (it.hasMoreElements()) {
				BizCampo c = it.nextElement();
				if (!c.isRolCampo()) continue;
				if (c.getNameField().equals(title)) {
					GuiCampo campo = new GuiCampo();
					c.keysToFilters();
					c.dontThrowException(true);
					if (!c.read()) return null;
					campo.setRecord(c);
					return campo;
				}
			}
		}
		if (zone.startsWith("filtro")) {
			JIterator <BizCampo> it = customList.GetcDato().getObjFiltros().getStaticIterator();
			String title= zone.substring(7);
			while (it.hasMoreElements()) {
				BizCampo c = it.nextElement();
				if ((c.isOperOR() && ((""+c.getOrden()).equals(title)))
				 || (!c.isOperOR() &&c.getNameField().equals(title))
				 || (!c.isOperOR() &&("filtro"+c.getOrden()).equals(title))) {
					GuiCampo campo = new GuiCampo();
					c.keysToFilters();
					c.dontThrowException(true);
					if (!c.read()) return null;
					campo.setRecord(c);
					return campo; 
				}
			}
		}
		return null;
	}

	public JBaseWin getDblClickObjectDrag(String zone,JBaseWin all) throws Exception {
		return getObjectDrageable(zone);
	}
	public int getDblClickDragAction(String zone,JBaseWin all) throws Exception {
		if (GetVision().equals("PREVIEW"))
			return BizAction.UPDATE;
		return BizAction.QUERY;
	}
	
	@Override
	public void createTotalizer(JWinList oWinlist) throws Exception {
		if ((!GetVision().equals("PREVIEW"))&&(getCustomList().GetcDato().isLista()||getCustomList().GetcDato().isAgrupado())&&getCustomList().GetcDato().isTotalizar()) {
			boolean first=true;
			
			JIterator<BizCampo> it = getCustomList().GetcDato().getObjCampos().getStaticIterator();
			while (it.hasMoreElements()) {
				BizCampo c = it.nextElement();
	  		if(c.getCampo()==null || c.getCampo().equals("")) continue;
				if (first) {
					oWinlist.addTotalizerText(c.getNameField(), "Totales"); // un texto
					first=false;
					continue;
				}
	  		if (!c.isRolCampo()) continue;
	  		if (c.getTipoCampo().equalsIgnoreCase("") || c.getTipoCampo().equalsIgnoreCase("JFLOAT") || c.getTipoCampo().equalsIgnoreCase("JCURRENCY") || c.getTipoCampo().equalsIgnoreCase("JLONG"))
					oWinlist.addTotalizer(c.getNameField(), 2, JTotalizer.OPER_SUM); // la suma del
			}
			
		}
		super.createTotalizer(oWinlist);
	}
	public void configureColumnsByTable(JWinList zLista) throws Exception {
		this.ConfigurarColumnasLista(zLista);

	}

}

