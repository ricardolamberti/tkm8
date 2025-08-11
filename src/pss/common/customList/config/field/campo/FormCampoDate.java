package pss.common.customList.config.field.campo;

import javax.swing.JTree;

import pss.common.customList.config.dynamic.GuiDynamics;
import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.customList.config.field.operadores.BizOperador;
import pss.common.customList.config.field.operadores.GuiOperadores;
import pss.common.customList.config.relation.BizCampoGallery;
import pss.common.customList.config.relation.GuiCamposGallery;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;

public class FormCampoDate  extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;



	private JTree jTree = null;
/**
   * Constructor de la Clase
   */
  public FormCampoDate() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

  }
  
  @Override
	public String getTitleRight() {
		return "";
	}

	public JMap<String, String> getOptions() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement( BizCampo.COLUMNA,getWin().GetcDato().getRolDescription(BizCampo.COLUMNA));
		map.addElement( BizCampo.FILA,getWin().GetcDato().getRolDescription(BizCampo.FILA));
		map.addElement( BizCampo.CAMPO,getWin().GetcDato().getRolDescription(BizCampo.CAMPO));
		if (getWin().GetcDato().getRolDescription(BizCampo.LINEA)!=null) map.addElement( BizCampo.LINEA,getWin().GetcDato().getRolDescription(BizCampo.LINEA));
		if (getWin().GetcDato().getRolDescription(BizCampo.LINEA)!=null) map.addElement( "-", "No Usar");
		map.addElement( "N", "Auto");
		return map;
	}
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
//    AddItem( jOrden, UINT, OPT, "orden" );
    AddItemEdit( null, UINT, OPT, "orden_padre" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "serial_deep").setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_source" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "rel_id").setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_gallery").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo").setHide(true);
    AddItemEdit( null, CHAR, OPT, "formato").setHide(true);
    AddItemEdit( null, CHAR, OPT, "nombre_columna").setHide(true);
    AddItemEdit( null, CHAR, OPT, "formato_param").setHide(true);
    AddItemEdit( null, CHAR, OPT, "porcentaje").setHide(true);
    AddItemEdit( null, CHAR, OPT, "ambito_porcentaje").setHide(true);
    AddItemEdit( null, CHAR, OPT, "colores").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_background").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_foreground").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_mayores").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_topbackground").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_top").setHide(true);
    AddItemEdit( null, CHAR, OPT, "mayores_a").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_menores").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_bottombackground").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_bottom").setHide(true);
    AddItemEdit( null, CHAR, OPT, "menores_a").setHide(true);
    AddItemEdit( null, CHAR, OPT, "calc_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "porc_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "descr_false").setHide(true);
    AddItemEdit( null, CHAR, OPT, "descr_true").setHide(true);
    AddItemEdit( null, CHAR, OPT, "dinamico").setHide(true);
    AddItemEdit( null, CHAR, OPT, "nombre_filtro").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo_key").setHide(true);
    AddItemEdit( null, CHAR, OPT, "descr_campo").setHide(true);
    AddItemEdit( null, CHAR, OPT, "visible").setHide(true);
//    AddItemEdit( null, CHAR, OPT, "orientacion").setHide(true);
//    AddItemEdit( null, CHAR, OPT, "has_filter").setHide(true);
    
  	AddItemRadio("Tipo: "+getWin().GetcDato().getRolDescription(), CHAR, OPT, "orientacion", getOptions()).setSizeColumns(12).SetValorDefault("N").setRefreshForm(true).setVisible(!getWin().GetVision().equals("FILTROS"));
		
  	AddItemCheck("Agr.filtro", OPT, "has_filter").setSizeColumns(6).setRefreshForm(true).setVisible(!getWin().GetVision().equals("FILTROS"));//.setVisible(!getWin().GetcDato().getFixedProp().isVirtual());

  	
    AddItemCombo( "Función", CHAR, OPT, "funcion", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getFunciones(one);
    	}
    }).setRefreshForm(true);;
     

    JFormControl c= AddItemCombo( "Operador", CHAR, OPT, "operador",  new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getOperadores(one);
    	}
    });
    c.SetValorDefault(BizFuncion.FUNTION_INTERVALO);
    c.setRefreshForm(true);

    JFormCDatetimeResponsive form1 = AddItemDateTime( "Valor", DATE, OPT, "value_date" );
    form1.setVisible(false);
    form1.SetValorDefault(getWin().GetcDato().getDefaultValueDate1());
    if (getWin().GetcDato().getValor().equals("")) {
    	getWin().GetcDato().setValor(getWin().GetcDato().getDefaultValueDate1());
    	form1.setValue(getWin().GetcDato().getDefaultValueDate1());
    }
    AddItemWinLov( "Valor", CHAR, OPT, "value_lov", new JControlWin() {
   		public JWins getRecords(boolean one) throws Exception {
   			return getRecordsLOV(one, 1);
   		}
   	}).setVisible(false);
    AddItemCheck( "Valor", CHAR, OPT, "value_check", "S", "N" ).setVisible(false);
    AddItemEdit( "Valor", CHAR, OPT, "value_edit" );

    JFormCDatetimeResponsive form2 = AddItemDateTime( "Valor", DATE, OPT, "value_date2" );
    form2.setVisible(false);
    form2.SetValorDefault(getWin().GetcDato().getDefaultValueDate2());
    if (getWin().GetcDato().getValor2().equals("")) {
    	getWin().GetcDato().setValor2(getWin().GetcDato().getDefaultValueDate2());
    	form2.setValue(getWin().GetcDato().getDefaultValueDate2());
    }  
    AddItemWinLov( "Valor", CHAR, OPT, "value_lov2", new JControlWin() {
   		public JWins getRecords(boolean one) throws Exception {
   			return getRecordsLOV(one, 2);
   		}
   	}).setVisible(false);
    AddItemCheck( "Valor", CHAR, OPT, "value_check2", "S", "N" ).setVisible(false);
    AddItemEdit( "Valor", CHAR, OPT, "value_edit2" );

  


  }
  @Override
	public void OnPostShow() throws Exception {
		checkControls("");
		verify(true);
		super.OnPostShow();
	}
  private void verify(boolean onShow) throws Exception {
  	
		this.verifyOperador(onShow);
		
		this.verifyValues(onShow);

	}
  
 
  private JWins getRecordsLOV(boolean one, int v) throws Exception {
  	JWins wins = GuiDynamics.createWinsByEje(one, this.getWin().GetccDato());
  	wins.setModeWinLov(true);
  	return wins;
  }
  
  public JWins getOperadores(boolean one) throws Exception {
  	GuiOperadores w = new GuiOperadores();
//  	if (this.getWin().GetcDato().isRestringido())
//    	w.setRecords(BizOperador.getOperadoresLOV());
//  	else
  		w.setRecords(BizOperador.getOperadores());
  	return w;
//  	return JWins.CreateVirtualWins(BizFiltro.getOperadores());
  	
  }
  public JWins getFunciones(boolean one) throws Exception {
  	if (one) {
  		return JWins.createVirtualWinsFromMap(BizCampo.getFunctionAllMap());
  	}
  	String s = ((BizField)getWin().GetcDato()).getObjectType();
  	return JWins.createVirtualWinsFromMap(BizCampo.getFunctionMap(s));
  	
  }
  
  @Override
	public void checkControls(String sControlId) throws Exception {
		if (sControlId.equals("id_gallery")) { 
			this.getWin().GetccDato().assignFieldsByIdGallery();
			this.refreshDataNewMode();
		}

		boolean hasFilter = true;//(GetControles().findControl("has_filter").getValue().equals("S"));
		BizFuncion fx = BizFuncion.findFuncion(getControles().findControl("funcion").getValue());
		if (fx != null) {
			if (fx.hasOnlyFunction()) {
				hasFilter=false;
				getControles().findControl("operador").setValue("");
			}
		}

		
		
		if (hasFilter) {
  		getControles().findControl("operador").setVisible(true);
		} else {
  		getControles().findControl("operador").setVisible(false);
		}
		
		if (this.isCampoValue(1, sControlId))
  		this.getWin().GetccDato().setValor(this.getWin().GetccDato().getProp(sControlId).toString());
  	
  	if (this.isCampoValue(2, sControlId))
  		this.getWin().GetccDato().setValor2(this.getWin().GetccDato().getProp(sControlId).toString());

  	String s = getWin().GetccDato().getObjectType();
  	String f = getWin().GetccDato().getOperador();

 
  	if (s!=null) {
  		if (s.equals(JObject.JDATE) || s.equals(JObject.JDATETIME)) {
    		if (f.equals("in")) {
    			getControles().findControl("value_date").setPlaceHolder("Lista de valores separados con ,");
    			getControles().findControl("value_date2").setPlaceHolder("Lista de valores separados con ,");
    		}
    		else {
    			getControles().findControl("value_date").setPlaceHolder("(fecha)");
    			getControles().findControl("value_date2").setPlaceHolder("(fecha)");
    		}
  		} else if (s.equals(JObject.JLONG) || s.equals(JObject.JINTEGER)) {
    		if (f.equals("in")) {
    			getControles().findControl("value_edit").setPlaceHolder("Lista de valores separados con ,");
    			getControles().findControl("value_edit2").setPlaceHolder("Lista de valores separados con ,");
    		}
    		else {
	  			getControles().findControl("value_edit").setPlaceHolder("(Valor numérico)");
	  			getControles().findControl("value_edit2").setPlaceHolder("(Valor numérico)");
    		}
    	} else if (s.equals(JObject.JFLOAT) ) {
    		if (f.equals("in")) {
    			getControles().findControl("value_edit").setPlaceHolder("Lista de valores separados con ,");
    			getControles().findControl("value_edit2").setPlaceHolder("Lista de valores separados con ,");
    		}
    		else {
	  			getControles().findControl("value_edit").setPlaceHolder("(Valor numérico)");
	  			getControles().findControl("value_edit2").setPlaceHolder("(Valor numérico)");
	   		}
    	} else if (s.equals(JObject.JSTRING) ) {
    		if (f.equals("in")) {
    			getControles().findControl("value_edit").setPlaceHolder("Lista de valores separados con ,");
    			getControles().findControl("value_edit2").setPlaceHolder("Lista de valores separados con ,");
    		}
    		else {
	  			getControles().findControl("value_edit").setPlaceHolder("(Valor cadena XXXXX)");
	  			getControles().findControl("value_edit2").setPlaceHolder("(Valor cadena XXXXX)");
    		}
    	} 
  	}
  	if (sControlId.equals("id_gallery")) { 
  			this.getWin().GetccDato().assignFieldsByIdGallery();
  			this.refreshDataNewMode();
  	}
  		super.checkControls(sControlId);
  	}
  
  private boolean isCampoValue(int type, String campo) throws Exception {
   	String t=type==2?"2":"";
   	if (campo.equals("value_lov"+t)) return true;
   	if (campo.equals("value_date"+t)) return true;
   	if (campo.equals("value_check"+t)) return true;
   	if (campo.equals("value_edit"+t)) return true;
   	return false;
   }

   
	private void verifyOperador(boolean onShow) throws Exception {
		String func = getControles().findControl("funcion").getValue();
		BizFuncion fx = BizFuncion.findFuncion(func);
		JFormControl c = this.findControl("operador");
		if (fx==null) {
			c.SetReadOnly(false);
			return;
		}

		if (fx.hasOperador()) { // sin operador
			c.SetReadOnly(false);
		} else {
			c.clear();
			c.SetReadOnly(true);
			getControles().findControl("has_filter").setValue("N");
		}

	}
   private JWins getCampos(boolean one) throws Exception {
   	GuiCamposGallery g = new GuiCamposGallery();
  	  g.setRecords(this.getWin().GetcDato().getObjCustomList().getAllCampos("", true));
   	return g;
   }
   private BizCampoGallery findCampoGallery() throws Exception {
  		JWins wins =  this.getCampos(true);
  		BizCampoGallery gall = (BizCampoGallery)wins.getRecords().findInHash("id",""+this.findControl("id_gallery").getValue());
  		return gall;
   }
   public void verifyValues(boolean onShow) throws Exception {
  		String operador = this.findControl("operador").getValue();
  		BizOperador oper = BizOperador.findOperador(operador);
  		
  		this.cleanAll();
  		
  		getControles().findControl("descr_true").setVisible(false);
  		getControles().findControl("descr_false").setVisible(false);

  		
  		if (oper==null ||!oper.hasValores()) 
  			return;
  		
  		if (!this.getWin().GetcDato().hasRelId()) 
  			return;
  		getControles().findControl("descr_true").setVisible(true);
  		getControles().findControl("descr_false").setVisible(true);
  	
   	String s =  getWin().GetccDato().getObjectType();
   	if (s==null||s.equals("FORMULA")) return;
  		String field = null;
  		if (this.getWin().GetcDato().isDateInput()) 
  			field="value_date";
  		else if (this.getWin().GetcDato().isDateTimeInput())
  			field="value_date";
  		else if (this.getWin().GetcDato().isLOVInput())
  			field="value_lov";
  		else if (this.getWin().GetcDato().isCheckInput())
  			field="value_check";
  		else  
  			field="value_edit";
  		
  		boolean isNumber=false;
  		if (s.equals("JDATE") && !this.getWin().GetcDato().getFuncion().equals("") && !this.getWin().GetcDato().getFuncion().equals(BizField.FUNTION_ANOMES)&& !this.getWin().GetcDato().getFuncion().equals(BizField.FUNTION_ANOSEM))
  			isNumber=true;
  		if (s.equals("JLONG") || s.equals("JINTEGER") ||s.equals("JFLOAT") || s.equals("COUNT"))
  			isNumber=true;
  		if (this.getWin().GetcDato().getOperador().equals("in"))
  			isNumber=false;
  		if (this.getWin().GetcDato().getOperador().equals("not in"))
  			isNumber=false;

  		
  		JFormControl c=this.findControl(field);
  		c.setVisible(true);
  		c.SetReadOnly(false);
  		try {
  			if (field.equals("value_date"))
  				JDateTools.StringToDate(this.getWin().GetcDato().getValor());
  			
  			if (isNumber) {
  				if (this.getWin().GetcDato().getValor().equals("")) 
  					throw new Exception("Debe especificar un valor");
  				else if (!JTools.isNumber(this.getWin().GetcDato().getValor())) 
  					throw new Exception("Debe especificar un valor numérico");
  			}
  			c.setValue(this.getWin().GetcDato().getValor());
  		} catch (Exception e) {
  			if (isNumber)
  				c.setValue("0");
  			else 
  				c.setValue("");
  		}

  		
  		if (oper.getCantValores()==2) {
  			c = this.findControl(field+"2");
  			c.setVisible(true);
  			c.SetReadOnly(false);
  			c.setValue(this.getWin().GetcDato().getValor2());

  		}

   }
   
   private void cleanAll() throws Exception {
   	this.cleanAll("");
   	this.cleanAll("2");
   }

   private void cleanAll(String type) throws Exception {
  		this.findControl("value_date"+type).hide();
  		this.findControl("value_lov"+type).hide();
  		this.findControl("value_check"+type).hide();
  		this.findControl("value_edit"+type).hide();
  		
  		this.findControl("value_date"+type).clear();
  		this.findControl("value_lov"+type).clear();
  		this.findControl("value_check"+type).clear();
  		this.findControl("value_edit"+type).clear();
   }  	

}  //  @jve:decl-index=0:visual-constraint="-1,16"
