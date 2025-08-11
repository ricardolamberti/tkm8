package pss.core.winUI.responsiveControls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JScript;
import pss.core.services.fields.JString;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.lists.JFormFiltro;
import pss.www.ui.JWebIcon;

public class JFormWinLOVResponsive extends JFormControlResponsive {

	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	protected JWins oWins;
	private JWin oWinSel;
	private JWins oSelectedWins;
	private JBaseRecord oLastRec;
	private IControl oControlWins;
	private IControl oWellKnows;
	

	private JString zClave=new JString(); // denominacion en la ventana hija del campo que contiene la clave
	private boolean bMultiple;

	private boolean bShowsInMultipleLines;
	private boolean bSearcheable=true;
	private boolean bMoreOptions=false;
  private boolean bShowLupa=true;
	private boolean bGroupByKeys=false;
  private long maximumSelectionLength=0;
  private long minimumInputLength=0;
  private String rawValue="";

	private JList<JFormButtonResponsive> extrabuttons=null;
//  private boolean neverUseCache;
//	public boolean isNeverUseCache() {
//		return neverUseCache;
//	}
//
//	public JFormWinLOVResponsive setNeverUseCache(boolean neverUseCache) {
//		this.neverUseCache = neverUseCache;
//		return this;
//	}
//  
  

	public boolean isModifiedOnServer() {
		if (!isDetectChanges()) return false;
		if (!getProp().isModifiedOnServer()) return false;
		return true;
	}
	public boolean isShowLupa() {
		return bShowLupa;
	}

	public IControl getWellKnows() {
		return oWellKnows;
	}
	public JFormWinLOVResponsive setWellKnows(IControl oWellKnows) {
		this.oWellKnows = oWellKnows;
		return this;
	}
	public JFormWinLOVResponsive setShowLupa(boolean bShowLupa) {
		this.bShowLupa = bShowLupa;
		return this;
	}

	public long getMaximumSelectionLength() {
		return maximumSelectionLength;
	}

	public JFormWinLOVResponsive setMaximumSelectionLength(long maximumSelectionLength) {
		this.maximumSelectionLength = maximumSelectionLength;
		return this;
	}

	public long getMinimumInputLength() {
		return minimumInputLength;
	}

	public JFormWinLOVResponsive setMinimumInputLength(long minimumInputLength) {
		this.minimumInputLength = minimumInputLength;
		return this;
	}

	private JMap<String,JList<JWin>> aItems=JCollectionFactory.createOrderedMap();
	private BizAction action;
	private JWin winAction;
	private String searchString;
	private boolean showKey=false;
	private String searchKey;
	private String searchDescrip;
	private String descripToShow;
	private boolean useID=true;
	public boolean isUseID() {
		return useID;
	}


	public void setUseID(boolean useID) {
		this.useID = useID;
	}
	// private JMap selectMapping=null;

	public String getDescripToShow() {
		return descripToShow;
	}


	public JFormWinLOVResponsive setDescripToShow(String descripToShow) {
		this.descripToShow = descripToShow;
		return this;
	}


	public String getSearchKey() {
		return searchKey;
	}
	public String getSearchDescrip() {
		return searchDescrip;
	}
	public JFormWinLOVResponsive setSearchFields(String zSearchKey,String zSearchDescrip) {
		this.searchKey = zSearchKey;
		this.searchDescrip = zSearchDescrip;
		this.showKey = this.searchKey!=null;
		return this;
	}
	
	public boolean isShowKey() {
		return showKey;
	}

	public JFormWinLOVResponsive setShowKey(boolean showKey) {
		this.showKey = showKey;
		return this;
	}

	public boolean isGroupByKeys() {
		return bGroupByKeys;
	}

	public JFormWinLOVResponsive setGroupByKeys(boolean bGroupByKeys) {
		this.bGroupByKeys = bGroupByKeys;
		return this;
	}
	public String getSearchString() {
		return searchString;
	}
	
	String oDatoToShow;

	public void setSearchString(String searchString) {
		if (isMultiple()) {
			int pos =searchString.lastIndexOf(";");
			if (pos!=-1) {
				this.searchString = searchString.substring(pos+1);
			}
		}
		this.searchString = searchString;
	}

	public boolean isMoreOptions() {
		return bMoreOptions;
	}

	public void setMoreOptions(boolean bMoreOptions) {
		this.bMoreOptions = bMoreOptions;
	}
	// -------------------------------------------------------------------------//
	// Auxiliares Web
	// -------------------------------------------------------------------------//

	private boolean editado=false;

	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	public void setControlWins(IControl zValue) {
		oControlWins=zValue;
		if (oControlWins!=null)
			oControlWins.setFormControl(this);
	}
 	public JWins getWinsFromAction() throws Exception {
		return ((JActWinsSelect)winAction.getSubmit(action)).getWinsResult();
	}
 	
	private void getQueryForOptionsByKeys() throws Exception {
		JWin onlyOne=null;
		int count=0;
		int maxItems=200;
		JMap<String,String> checkList= JCollectionFactory.createMap();
	
		aItems.removeAllElements();
		JMap<String,JWins> allWins = JCollectionFactory.createMap();
		
		this.getQueryForOptions(allWins);
		
		JIterator<String> it = allWins.getKeyIterator();
		while (it.hasMoreElements()) {
			String group = it.nextElement();
			JWins aWins = allWins.getElement(group);
			JList<JWin>lista = JCollectionFactory.createList(); 
			aItems.addElement(group,lista);
			if (maxItems-count<=0) break;
			if (aWins.ifEstatico()) {
				JIterator<JWin> iter = aWins.getStaticIterator();
				while (iter.hasMoreElements()) {
					JWin oWin=iter.nextElement();
					if (checkList.getElement(oWin.GetValorItemClave())!=null) continue;
					checkList.addElement(oWin.GetValorItemClave(),getDescriptionValue(oWin));
					lista.addElement(oWin);
					if (count++==0) onlyOne=oWin;
					else onlyOne=null;
					if (count>maxItems) break;
				}
				
			} else {
				aWins.getRecords().setTop(maxItems-count);
				aWins.readAll();
				while (aWins.nextRecord()) {
					JWin oWin=aWins.getRecord();
					if (checkList.getElement(oWin.GetValorItemClave())!=null) continue;
					checkList.addElement(oWin.GetValorItemClave(),getDescriptionValue(oWin));
					lista.addElement(oWin);
					if (count++==0) onlyOne=oWin;
					if (count>maxItems) break;
					else onlyOne=null;
				}
				aWins.getRecords().closeRecord();
				
			}
		}
	
		setMoreOptions(count>=maxItems);
		if (onlyOne!=null) {
			setValue(onlyOne);
		}
	}
	

	private void getQueryForOptions( JMap<String,JWins> list) throws Exception {
		String value = this.getValue(); 
		if (value.trim().equals("")) return; // todos son demasiados
		JWins aWins;
		if (oControlWins==null) {
			JActWinsSelect act= (JActWinsSelect)winAction.getSubmit(action);
			aWins = act.getWinsResult();
		}
		else {
			aWins=oControlWins.getRecords(false);
		}
		oWins.setExtraFilter(getExtraFilter());

		JWin win=aWins.getWinRef();

		StringTokenizer t = new StringTokenizer(win.getSearchFields(isShowKey(),getSearchKey(),getSearchDescrip()), ";");
		while (t.hasMoreTokens()) {
			String f = t.nextToken();
			JWins localWins = oWins.createClone();
//			if (oControlWins==null) {
//				JActWinsSelect act= (JActWinsSelect)winAction.getSubmit(action);
//				aWins = act.getWinsResult();
//			}
//			else {
//				aWins=oControlWins.getRecords(false);
//			}
			localWins.getRecords().clearDynamicFilters();
			JWins out= this.findRows(localWins, win, f, value); 
			if (out!=null)
				list.addElement(win.getRecord().getFixedPropDeep(f).GetDescripcion(),out);
		}
		return;
	}
	public boolean containsValueIgnoreCase(String zValue) throws Exception {
		if (GetWinsSelect()==null) return false;
		JIterator<JWin> oIt=GetWinsSelect().getStaticIterator();
		while (oIt.hasMoreElements()) {
			JWin oWin=oIt.nextElement();
			if (oWin.getRecord().getProp(oWin.getKeyField()).toString().equalsIgnoreCase(zValue)) {
				return true;
			}
		}
		return false;
	}
	private JFormFiltro getBarFiltros(JWins wins) throws Exception {
		JFormFiltro filtros = new JFormFiltro(wins);
		filtros.initialize();
//		filtros.convertToResponsive();
//		filtros.applyFilterMap(getAction().getSubmit(),false);
		return filtros;

	}
	private void getQueryForOptionsByWinsRules() throws Exception {
		JWin onlyOne=null;

		int count=0;
		int maxItems=100;
		boolean findKey=false;
		boolean findDescript=false;
		String value = this.getValue(); 
		JWins aWins;
		if (oControlWins==null) {
			JActWinsSelect act= (JActWinsSelect)winAction.getSubmit(action);
			aWins = act.getWinsResult();
		}
		else {
			aWins=oControlWins.getRecords(false);
		}

		JWins aWinsKey;
		if (oControlWins==null) {
			JActWinsSelect act= (JActWinsSelect)winAction.getSubmit(action);
			aWinsKey = act.getWinsResult().createClone();
			aWinsKey.getRecords().clearFilters();
		}
		else {
			aWinsKey=aWins.createClone();
		}	
		
				
		
		aWinsKey.asignFiltersFromFilterBar(getBarFiltros(aWinsKey));
		aWins.asignFiltersFromFilterBar(getBarFiltros(aWins));

		JWin win=aWins.getWinRef();
		List<String> fieldKeys = new ArrayList<String>();
		List<String> fieldSearch = new ArrayList<String>();
		String search = getSearchKey();
		search = search==null?"":search;
		boolean detectCantUseSql =aWins.ifEstatico();
		StringTokenizer t = new StringTokenizer(win.getSearchFields(isShowKey(),getSearchKey(),getSearchDescrip()), ";");
		while (t.hasMoreTokens()) {
			String f = t.nextToken();
			if ((!search.equals("")&&f.indexOf(search)!=-1)||win.getRecord().getFixedPropDeep(f).isKey())
				fieldKeys.add(f);
			else
				fieldSearch.add(f);
	    
			if (aWins.assignFilters(f,value)) continue;
			detectCantUseSql|= (win.getRecord().getFixedPropDeep(f).isVirtual());
		}			
				
		boolean first=true;
		int c=0;
		if (!detectCantUseSql) {
			long size=fieldKeys.size();
			for (String f:fieldKeys) {
				c++;
		    if (!aWins.assignFilters(f,value)){ 
			   	if (win.getRecord().getPropDeepOnlyRef(f).isString()){
			   		aWinsKey.getRecords().addFilter(f, value, JObject.JSTRING, "=", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
			   		findKey=true;
				   	first=false;
			   	} else if (win.getRecord().getPropDeepOnlyRef(f).isDate()){
			   		if (isDate(value)) {
							aWins.getRecords().addFilter(f, getDate(value), JObject.JDATE, "=", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
				   		findKey=true;
					   	first=false;
			   		}
					} else if (win.getRecord().getPropDeepOnlyRef(f).isDateTime()){
						if ( isDateTime(value)) {
							Date d =getDateTime(value);
							aWins.getRecords().addFilter(f, d, JObject.JDATETIME, "=", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
				   		findKey=true;
					   	first=false;
						}
					} else if (JTools.isNumber(value)){
			   		aWinsKey.getRecords().addFilter(f, value, JObject.JLONG, "=", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
			   		findKey=true;
				   	first=false;
					}
			  }
		  }
		}
		first=true; 
		c=0;
		if (!detectCantUseSql) {
			long size=fieldSearch.size();
			for (String f:fieldSearch) {
				c++;
				if (!aWins.assignFilters(f,value)){
			   	if (win.getRecord().getPropDeepOnlyRef(f).isString()){
						aWins.getRecords().addFilter(f, value, JObject.JSTRING, "ILIKE", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
			   		findDescript=true;
				   	first=false;
			   	} else if (win.getRecord().getPropDeepOnlyRef(f).isDate()){
						if (isDate(value)) {
							aWins.getRecords().addFilter(f, getDate(value), JObject.JDATE, "=", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
				   		findDescript=true;
					   	first=false;
						}
					} else if (win.getRecord().getPropDeepOnlyRef(f).isDateTime()){
						if (isDateTime(value)) {
								aWins.getRecords().addFilter(f, getDateTime(value), JObject.JDATETIME, "=", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
					   		findDescript=true;
						   	first=false;
						}
						else if (isDate(value)) {
							aWins.getRecords().addFilter(f, getDateTime(value), JObject.JDATE, "=", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
				   		findDescript=true;
					   	first=false;
						}
					} else if (JTools.isNumber(value)){
						aWins.getRecords().addFilter(f, value, JObject.JLONG, "LIKE", first||size==1?"AND":"OR", size==1?"":(first?"(":(c<size?"":")"))).setDynamic(true);
			   		findDescript=true;
				   	first=false;
					}
			  }
		  }
		}


		JList<JWin> grupoActual = null;
		String descrGrupoActual="";
		if (findKey) {
			aWinsKey.readAll();
			aWinsKey.firstRecord();
			while (aWinsKey.nextRecord()) {
				JWin winCand = aWinsKey.getRecord();
				StringTokenizer t2 = new StringTokenizer(win.getSearchFields(isShowKey(),getSearchKey(),getSearchDescrip()), ";");
				boolean add=false;
				while (t2.hasMoreTokens()) {
					String field = t2.nextToken();
					if (!detectCantUseSql) {
						if (!win.getRecord().getFixedProp(field).isVirtual()) {
							add=true;
							if (add) break;
						}
						if (aWins.assignFilters(field,value)) {
							add=true;
							if (add) break;
						}
					}
					if (winCand.getRecord().getProp(field).isString()) {
						if (winCand.getRecord().getProp(field).toString().trim().toUpperCase().indexOf(value.toUpperCase())!=-1) 
							add=true;
						if (add) break;
			   	} else {
						if (winCand.getRecord().getProp(field).toString().trim().toUpperCase().equalsIgnoreCase(value)) 
							add=true;
						if (add) break;
			   	}
			    
				}
				if (!add) continue;
				if (grupoActual==null||!descrGrupoActual.equals(winCand.getCorteControl(0)==null?"":winCand.getCorteControl(0))) {
					grupoActual = JCollectionFactory.createList();
					descrGrupoActual=winCand.getCorteControlDescription(0);
					aItems.addElement(descrGrupoActual, grupoActual);
				}
				grupoActual.addElement(winCand);
				
				if (count==0) 
					onlyOne=winCand;
				else
					onlyOne=null;
				
				if (count++>maxItems) 
					break;
			}
			aWinsKey.getRecords().closeRecord();
		}
		if (findDescript || detectCantUseSql) {
			aWins.readAll();
			aWins.firstRecord();
			while (aWins.nextRecord()) {
				JWin winCand = aWins.getRecord();
				StringTokenizer t2 = new StringTokenizer(win.getSearchFields(isShowKey(),getSearchKey(),getSearchDescrip()), ";");
				boolean add=false;
				while (t2.hasMoreTokens()) {
					String field = t2.nextToken();
					if (!detectCantUseSql) {
						if (!win.getRecord().getFixedProp(field).isVirtual()) {
							add=true;
							if (add) break;
						}
						if (aWins.assignFilters(field,value)) {
							add=true;
							if (add) break;
						}
					}
					if (winCand.getRecord().getProp(field).isString()) {
						if (winCand.getRecord().getProp(field).toString().trim().toUpperCase().indexOf(value.toUpperCase())!=-1) 
							add=true;
						if (add) break;
			   	} else {
						if (winCand.getRecord().getProp(field).toString().trim().toUpperCase().equalsIgnoreCase(value)) 
							add=true;
						if (add) break;
			   	}
			    
				}
				if (!add) continue;
				if (grupoActual==null||!descrGrupoActual.equals(winCand.getCorteControl(0)==null?"":winCand.getCorteControl(0))) {
					grupoActual = JCollectionFactory.createList();
					descrGrupoActual=winCand.getCorteControlDescription(0);
					aItems.addElement(descrGrupoActual, grupoActual);
				}
				grupoActual.addElement(winCand);
				
				
				
				if (count==0) 
					onlyOne=winCand;
				else
					onlyOne=null;
				
				if (count++>maxItems) 
					break;
			}
			aWins.getRecords().closeRecord();
		}
	
		setMoreOptions(count>=maxItems);
		if (onlyOne!=null) {
			setValue(onlyOne);
		}
		
	}
	private boolean isDate(String filter) throws Exception {
			Date d = null;
			d=JDateTools.StringToDateNonExec(filter, "dd/MM/yyyy");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yyyy");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yy");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yy");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyyyy");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyy");
			return (d!=null);
	}
	private boolean isDateTime(String filter) throws Exception {
			Date d = null;
			d=JDateTools.StringToDateNonExec(filter, "dd/MM/yyyy HH:mm");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yyyy HH:mm");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yy HH:mm");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yy HH:mm");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yyyy HH:mm:ss");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yyyy HH:mm:ss");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yy HH:mm:ss");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yy HH:mm:ss");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyyyy HH:mm");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyy HH:mm");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyyyy HH:mm:ss");
			if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyy HH:mm:ss");
			return (d!=null);
	}
	private Date getDate(String filter) throws Exception {
		Date d = null;
		d=JDateTools.StringToDateNonExec(filter, "dd/MM/yyyy");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yyyy");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yy");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yy");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyyyy");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyy");
		if (d.getYear()<2000) d.setYear(d.getYear()+2000);
		return d;
}
private Date getDateTime(String filter) throws Exception {
		Date d = null;
		d=JDateTools.StringToDateNonExec(filter, "dd/MM/yyyy HH:mm");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yyyy HH:mm");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yy HH:mm");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yy HH:mm");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yyyy HH:mm:ss");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yyyy HH:mm:ss");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd/MM/yy HH:mm:ss");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "dd-MM-yy HH:mm:ss");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyyyy HH:mm");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyy HH:mm");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyyyy HH:mm:ss");
		if (d==null) d=JDateTools.StringToDateNonExec(filter, "ddMMyy HH:mm:ss");
		if (d.getYear()<2000) d.setYear(d.getYear()+2000);
		return d;
}

	
	private JWins findRows(JWins wins, JWin win, String field, String value) throws Exception {
		if (value.equals("")) return null; // todos son demasiados
		if (win.getRecord().getFixedProp(field).isVirtual()) 
			return this.findRecord(wins, field, value, false);
		if (win.GetBaseDato().hasCatalog()) { 
			wins.getRecords().addFilter(win.getRecord().GetTable(), "*", "\"*"+value+"*\"", RFilter.OPERATOR_CONTAINS).setDynamic(true);
			return wins;
		}
		if (wins.ifEstatico()) {
			return this.findRecord(wins, field, value, false);
		}
		wins.getRecords().clearDynamicFilters();
    if (!wins.assignFilters(field,value)){
	   	if (win.getRecord().getProp(field).isString())
				wins.getRecords().addFilter(field, value, "ILIKE").setDynamic(true);
			else if (JTools.isNumber(value)){
				wins.getRecords().addFilter(field, value).setDynamic(true);
			}
			else {
				return null;
			}
    }
		return wins;
	}

	private void fillOptions() throws Exception {
		aItems.removeAllElements();
		setMoreOptions(false);
		
		if (isGroupByKeys()) {
			getQueryForOptionsByKeys();
		} else {
			getQueryForOptionsByWinsRules();
			
		}
	}

	public void fillSearch() {
		try {
			fillOptions();
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}

	
	public void SetWins(JWins zValue) {
		oWins=zValue;
	}

	// public void SetCampo( String zValue ) { zCampo = zValue; }
	public void SetClave(String zValue) {
		zClave.setValue(zValue);
	}

	// public void SetBaseForm( JBaseForm zValue ) { oBaseForm = zValue; }
	@Override
	public JWin GetWinSelect() throws Exception {
		fillFromProp(null);
		getValueDescription();
		return oWinSel;
	}
	
	public boolean hasWinsSelect() throws Exception {
		return GetWinsSelect()!=null;
	}

	public JWins buildWinsSelect() throws Exception {
		JWins options;
		if (oControlWins!=null)
			options=oControlWins.getRecords(true);
		else
			options=getWinsFromAction();
		oSelectedWins=options.createClone();
		oSelectedWins.getRecords().clearStructure();
		if (getExtraFilter()!=null)
			oSelectedWins.setExtraFilter(getExtraFilter());
		oSelectedWins.getRecords().setStatic(true);
	//	oSelectedWins.clearItems();

		return oSelectedWins;
	}
	
	public JWins GetWinsSelect() throws Exception {
		findValueDescription();
		return oSelectedWins;
	}

	public JWin GetFirstWinsSelect() throws Exception {
		if (GetWinsSelect()==null) return null;
		JIterator<JWin> it=GetWinsSelect().getStaticIterator();
		if (!it.hasMoreElements()) return null;
		return it.nextElement();
	}

	public JMap<String,JList<JWin>> getItemList() throws Exception {
		return this.aItems;
	}

	public boolean showsInMultipleLines() {
		return this.bMultiple&&this.bShowsInMultipleLines;
	}


	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormWinLOVResponsive(boolean zMultiple, boolean zShowsInMultipleLines) {
		this.bMultiple=zMultiple;
		this.bShowsInMultipleLines=zShowsInMultipleLines;
	}

	public boolean isMultiple() {
		return this.bMultiple;
	}
	public void setMultiple(boolean bMultiple) {
		this.bMultiple = bMultiple;
	}

	private boolean bModal=true;
	public boolean isModal() {
		return this.bModal;
	}
	public void setModal(boolean v) {
		this.bModal = v;
	}

	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void clear() throws Exception {
		getProp().setNull();
  	fillFromProp(null);

	}


	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	@Override
	public void edit(String zModo) throws Exception {
		editado=true;
		super.edit(zModo);
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() throws Exception {
		fillFromProp(null);
		return rawValue;
//		if (this.bMultiple) {
////			if (!hasWinsSelect()) return "";
////			JIterator oSelectedIt=GetWinsSelect().getStaticIterator();
////			String sValue="";
////			while (oSelectedIt.hasMoreElements()) {
////				JWin oWin=(JWin) oSelectedIt.nextElement();
////				sValue+=(sValue.equals("")?"":",")+oWin.GetValorItemClave();
////			}
//		
//			return rawValue;
//		} else {
//			if (rawValue!=null) return rawValue;
//			if (oWinSel==null) return rawValue;
//			return oWinSel.GetValorItemClave();
//		}
	}

	@Override
	public boolean hasValue() {
		try {
			return !getValue().equals("");
		} catch (Exception e) {
			return false;
		}
	}


	
  @Override
  public void setValue(JRecord rec) throws Exception {
  	super.setValue(rec);
  	fillFromProp(null);
  }
	public String getKeyFieldName(String defa) throws Exception {
		if (getSearchKey()==null) 
			return defa;
		return getSearchKey();
	}  
  
	public String getKeyValue(JWin win) throws Exception {
		if (getSearchKey()==null) 
			return win.GetValorItemClave();
		return win.getRecord().getPropAsString(getSearchKey());
	}
	@Override
	public void setValue(JWin zWin) throws Exception {
		if (getProp().isRecord())
			getProp().setValue(zWin.getRecord());
		else
			getProp().setValue(isUseID()?zWin.getRecord().getPropAsString(zWin.getKeyField()):getKeyValue(zWin));
  	fillFromProp(zWin);

	}

	public String getDescriptionValue(JWin win) throws Exception {
		if (win==null)
			return null;
		if (this.getDescripToShow()!=null && win.getRecord().getPropDeep(this.getDescripToShow())!=null)
			return win.getRecord().getPropDeep(this.getDescripToShow()).toString();
		if (this.getSearchDescrip()!=null && win.getRecord().getPropDeep(this.getSearchDescrip())!=null)
			return win.getRecord().getPropDeep(this.getSearchDescrip()).toString();
		return win.getDescripFieldValue();
	}
	
	@Override
	public void setValue(JWins zWins) throws Exception {
	  	super.setValue(zWins);
	  	fillFromProp(null);
	}
	@Override
	public void setValue(String zVal) throws Exception {
  	super.setValue(zVal);
  	fillFromProp(null);
	}

	public JWin BuscarWin(String zVal) throws Exception {
		boolean autoAddOneRow = true;
		
		if (oControlWins!=null) {
			oWins=oControlWins.getRecords(true);
			autoAddOneRow=oControlWins.autoAddOneRow();
		} else 
			oWins=getWinsFromAction();
		

		if (oWins==null) return null;
		
		
		if (oWins.getRecords().isStatic()) {
			return findOneRecord(oWins, getSearchKey(), zVal, true);
		}

		if (autoAddOneRow) {
			oWins.setExtraFilter(getExtraFilter());
			JWin refWin = oWins.createWin();
			this.verifyMainKey(refWin, zVal);
		}
		oWins.firstRecord();
		oWins.readAll();
		if (oWins.nextRecord()) {
			return oWins.getRecord();
		} else {
			return null;
		}
	}

	public void verifyMainKey(JWin refWin, String zVal) throws Exception {
		refWin.SetVision(oWins.GetVision());
		if (!isUseID()) {
			if (oWins.getRecords().filterHasValue(getSearchKey())) 
				oWins.getRecords().getFilters().removeElement(oWins.getRecords().getFilter(getSearchKey())); 
			oWins.getRecords().addFilter(getSearchKey(), zVal);
			return;
			
		}
		if (oWins.getRecords().filterHasValue(refWin.getKeyField())) 
			oWins.getRecords().getFilters().removeElement(oWins.getRecords().getFilter(refWin.getKeyField())); 
		oWins.getRecords().addFilter(refWin.getKeyField(), zVal);
	}
	
	public JWin findOneRecord(JWins wins, String zkey, String value, boolean exact) throws Exception {
		String key = zkey;
		if (key==null)
			key=wins.getWinRef().getKeyField();

	 JIterator<JWin> it=	wins.getStaticIterator();
		while (it.hasMoreElements()) {
			JWin win=it.nextElement();
			if (exact && win.getRecord().getProp(key).toString().trim().toUpperCase().equalsIgnoreCase(value)) 
				return win;
			if (!exact && win.getRecord().getProp(key).toString().trim().toUpperCase().indexOf(value.toUpperCase())!=-1) 
				return win;
		}
		return null;
	}
	public JWins findRecord(JWins wins, String key, String value, boolean exact) throws Exception {
		JWins outWins = wins.getClass().newInstance();
		outWins.getRecords().setStatic(true);
		JIterator<JWin> it = wins.getStaticIterator();
		while (it.hasMoreElements()) {
			JWin win=it.nextElement();
			if (exact && win.getRecord().getProp(key).toString().trim().toUpperCase().equalsIgnoreCase(value)) 
				outWins.addRecord(win);
			if (!exact && win.getRecord().getProp(key).toString().trim().toUpperCase().indexOf(value.toUpperCase())!=-1) 
				outWins.addRecord(win);
		}
		return outWins;
	}


	public IControl getControlWins() throws Exception {
		return oControlWins;
	}


	@Override
	public String getValueDescription() throws Exception {
		if (getProp().isRecords()) {
			return oDatoToShow;
		}	else if (getProp().isRecord()) {
			return oDatoToShow;
		} else {
			return findValueDescription();
		}
	}


	

	public boolean isSearcheable() {
		return bSearcheable;
	}

	public void setSearcheable(boolean searcheable) {
		bSearcheable=searcheable;
	}

	
	public BizAction getAction() {
		return action;
	}

	
	public void setAction(JWin winAction,BizAction action) {
		this.action=action;
		this.winAction = winAction;
	}
	
	public JList<JFormButtonResponsive> getExtraButtons() throws Exception {
		if (extrabuttons!=null) return extrabuttons;
		extrabuttons = JCollectionFactory.createList();
		return extrabuttons;
	}
	public JFormButtonResponsive addExtraButton(JWin win,String label,int zActionSource, boolean submit) throws Exception {
		return addExtraButton(win, label, null, zActionSource, submit, null);
	}
	public JFormButtonResponsive addExtraButton(JWin win,String label,JWebIcon image,int zActionSource, boolean submit,JScript script) throws Exception {
		JFormButtonResponsive oButton= new JFormButtonResponsive();
		oButton.initialize();
		oButton.setLabel(label);
		oButton.setImagen(image);
		oButton.setVisible(true);
		oButton.setResponsiveClass("btn btn-default");
		if (zActionSource!=-1) {
			BizAction oAction = win.findAction(zActionSource);
			oButton.setAction(oAction);	
			oButton.setSubmit(submit);
		} 
		oButton.setScript(script);
			
		
		getExtraButtons().addElement(oButton);
		return oButton;
	}
	
	
	private void  fillFromProp(JWin winAsoc) throws Exception {
		if (getProp().isRecords()) {
			fillFromPropRecords();
		}	else if (getProp().isRecord()) {
			fillFromPropRecord();
		} else
			fillFromPropString(winAsoc);
	}
	public boolean hasChanged(JBaseRecord rec,String zVal) throws Exception {
		if (zVal!=null && rawValue.equals(zVal)) // nothing change 
			return false;
		if (oLastRec!=null && rec!=null && oLastRec.equals(rec)) // nothing change 
			return false;
		return true;
		
	}
	private void  fillFromPropRecords() throws Exception {
		JRecords recs = (JRecords)getProp().getObjectValue();
	 	if (!hasChanged(recs,null)) return;
	 	oLastRec=recs;
		JWins wins = buildWinsSelect();
		wins.setRecords(recs);
	
		
		if (bMultiple) {
			String out="";
			buildWinsSelect();
			JIterator<JWin> it = wins.getStaticIterator();
			while (it.hasMoreElements()) {
				JWin win =it.nextElement();
				GetWinsSelect().addRecord(win);
				out+=(out.equals("")?"":";")+getDescriptionValue(win);
			}
			oDatoToShow=out;
			return;
		}

		JIterator<JWin> it = wins.getStaticIterator();
		while (it.hasMoreElements()) {
			JWin win =it.nextElement();
			oWinSel=win;
			SetClave(isUseID()?win.getRecord().getPropAsString(win.getKeyField()):getKeyValue(win));
			oDatoToShow=getDescriptionValue(oWinSel);
			rawValue=isUseID()?win.getRecord().getPropAsString(win.getKeyField()):getKeyValue(win);
			return;
		}
	}
	

	private void  fillFromPropRecord() throws Exception {
		JRecord rec = (JRecord)getProp().getObjectValue();
  	if (rec==null) {
  		oWinSel=null;
  		oSelectedWins=null;
  		oDatoToShow="";  		
  		rawValue="";
  		return;
  	}
	 	if (!hasChanged(rec,null)) return;
	 	oLastRec=rec;
		buildWinsSelect();
  	JWin win = GetWinsSelect().getWinRef();
  	rec.SetVision(win.GetVision());
  	win.setRecord(rec);
  	oWinSel=win;
  	rawValue=isUseID()?win.getRecord().getPropAsString(win.getKeyField()):getKeyValue(oWinSel);
		oDatoToShow=getDescriptionValue(oWinSel);
  }
	
	private void fillFromPropString(JWin winAsoc) throws Exception {
		String zVal = getProp().toString();
		if (zVal.equals("")) {
  		oWinSel=null;
  		oSelectedWins=null;
  		oDatoToShow="";  		
  		rawValue="";
  		return;
		}
		forceDescription(winAsoc);
  	if (!hasChanged(null,zVal)) return;

		oWinSel=null;
		oSelectedWins=null;
		oDatoToShow=null;
		rawValue=zVal;
	}		

	public void forceDescription(JWin winAsoc) throws Exception {
		if (winAsoc==null) return;
		if (bMultiple) return;
		oWinSel=winAsoc;
		oDatoToShow=getDescriptionValue(oWinSel);
	}
	
	public String findValueDescription() throws Exception {
		if (oDatoToShow!=null) return oDatoToShow;
		if (getProp().isRecords() || getProp().isRecord()) return oDatoToShow;

		String keyValue = getProp().toString();
		if (bMultiple) {
			StringTokenizer tok = new StringTokenizer(keyValue,",");
			JWins localWins=buildWinsSelect();
			String out="";
			while (tok.hasMoreTokens()) { 
				String key =tok.nextToken();
				JWin winSel=this.BuscarWin(key);
				if (winSel==null) continue;
				localWins.addRecord(winSel);
				out+=(out.equals("")?"":";")+getDescriptionValue(winSel);
			}
			localWins.SetEstatico(true);
			oDatoToShow=out;
			return oDatoToShow;
		}

		
		oWinSel=this.BuscarWin(keyValue);
		if (oWinSel==null) {
	 	  oDatoToShow="";  		
 		} else {
			oDatoToShow=getDescriptionValue(oWinSel);
		}
		return oDatoToShow;
	}

}
