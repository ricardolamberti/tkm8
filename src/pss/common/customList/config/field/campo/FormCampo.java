package pss.common.customList.config.field.campo;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.dynamic.GuiDynamics;
import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.customList.config.field.operadores.BizOperador;
import pss.common.customList.config.field.operadores.GuiOperadores;
import pss.common.customList.config.relation.BizCampoGallery;
import pss.common.customList.config.relation.GuiCamposGallery;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.GuiVirtuals;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormDivResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCampo extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;

/**
   * Constructor de la Clase
   */
  public FormCampo() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
  }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
		AddItemEdit( null, CHAR, OPT, "serial_deep").setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_source" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "rel_id").setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_gallery").setHide(true);
    AddItemEdit( null, UINT, OPT, "orden_padre" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo").setHide(true);
    AddItemEdit( null, CHAR, OPT, "formato").setHide(true);
    AddItemEdit( null, CHAR, OPT, "formato_param").setHide(true);//!BizUsuario.getUsr().getObjBusiness().isCustomListSimplify());

    JFormFieldsetResponsive fs = AddItemFieldset("Generales");
    fs.AddItemEdit( "Campo", CHAR, OPT, "descr_campo").setSizeColumns(5).SetReadOnly(true).setVisible(false);
    fs.AddItemEdit( "Nombre Col.", CHAR, OPT, "nombre_columna").setSizeColumns(5);
    fs.AddItemCheck( "Visible", CHAR, OPT, "visible", "S","N").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(2).SetValorDefault(true);
    
    
    JFormControlResponsive c = fs.AddItemRow().AddItemCombo( "Función", CHAR, OPT, "funcion", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getFunciones(one);
    	}
    });
    c.setRefreshForm(true);
    c.setSizeColumns(6);
    fs.AddItemCheck( "Porcentaje", CHAR, OPT, "porcentaje", "S","N").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(2).SetValorDefault(false);
    fs.AddItemWinLov("Ambito del porcentaje", CHAR, OPT, "ambito_porcentaje",new JControlWin() {
			@Override
			public JWins getRecords(boolean bOneRow) throws Exception {
				return getEjes(bOneRow);
			}
		},true,false, -1).setSizeColumns(4);

    JFormPanelResponsive row0 = AddItemRow(12).setGutter(true);
    JFormPanelResponsive row1 = AddItemRow(4);
    JFormPanelResponsive row2 = AddItemRow(4);
    JFormPanelResponsive row3 = AddItemRow(4);

    
    JFormPanelResponsive row4 = row0.AddItemPanel("");
    row4.setSizeColumns(6);
    JFormPanelResponsive row5 = row0.AddItemPanel("");
    row5.setSizeColumns(6);
    JFormPanelResponsive panelFiltro = row5.AddItemFieldset("Filtros");
    

    JFormPanelResponsive panelOrd = row4.AddItemFieldset("Ordenamiento");
    JFormPanelResponsive panel = row4.AddItemFieldset("Diferencia");

    
    c=panelFiltro.AddItemRow().AddItemCheck( "Incluye filtro", OPT, "has_filter").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(12);
    c.setRefreshForm(true);
    panelFiltro.AddItemRow().AddItemEdit( "Descripción", CHAR, OPT, "nombre_filtro" ).setSizeColumns(10);
    c= panelFiltro.AddItemRow().AddItemCombo( "Operador", CHAR, OPT, "operador",  new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getOperadores(one);
    	}
    });
    c.setRefreshForm(true);
    c.setSizeColumns(12);
    
    panelFiltro.AddItemDateTime( "Valor", DATE, OPT, "value_date" ).setSizeColumns(6).setVisible(false);
    panelFiltro.AddItemWinLov( "Valor", CHAR, OPT, "value_lov", new JControlWin() {
   		public JWins getRecords(boolean one) throws Exception {
   			return getRecordsLOV(one, 1);
   		}
   	}).setSizeColumns(6).setVisible(false);
    panelFiltro.AddItemThreeCheck( "Valor", CHAR, OPT, "value_check").setSizeColumns(6).setVisible(false);
    panelFiltro.AddItemEdit( "Valor", CHAR, OPT, "value_edit" ).setSizeColumns(6);

    
    
    panelFiltro.AddItemDateTime( "Valor hasta", DATE, OPT, "value_date2" ).setSizeColumns(6).setVisible(false);
    panelFiltro.AddItemWinLov( "Valor", CHAR, OPT, "value_lov2", new JControlWin() {
   		public JWins getRecords(boolean one) throws Exception {
   			return getRecordsLOV(one, 2);
   		}
   	}).setSizeColumns(6).setVisible(false);
    panelFiltro.AddItemThreeCheck( "Valor", CHAR, OPT, "value_check2").setSizeColumns(6).setVisible(false);
    panelFiltro.AddItemEdit( "Valor", CHAR, OPT, "value_edit2" ).setSizeColumns(6);
    panelFiltro.AddItemCombo( "Valor desde especial", CHAR, OPT, "campo_key", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizUsuario.getUsr().getObjBusiness().getCamposClavesParaCustomList());
    	}
    }).setSizeColumns(6);

    panelFiltro.AddItemCombo( "Valor hasta especial", CHAR, OPT, "campo_key2", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizUsuario.getUsr().getObjBusiness().getCamposClavesParaCustomList());
    	}
    }).setSizeColumns(6);

    panelFiltro.AddItemEdit( "Descrip.cumple", CHAR, OPT, "descr_true").SetValorDefault("Cumple");
    panelFiltro.AddItemEdit( "Descrip.no cumple", CHAR, OPT, "descr_false").SetValorDefault("No cumple");

    panelFiltro.AddItemCheck( "No incluir en totalizador porcentajes", CHAR,  "no_incluir" ).setSizeColumns(6);
    panelFiltro.AddItemCheck( "Solo incluir en totalizador porcentajes", CHAR,  "solo_tot" ).setSizeColumns(6);
//    panelFiltro.setSizeColumns(6);

//    panelOrd.AddItemEdit( "Orden", LONG, OPT, "orden_orden").setSizeColumns(2).SetReadOnly(true);
    panelOrd.AddItemRadio("Sentido", CHAR, OPT, "orden_ascdesc", getWin().GetcDato().getOptionsOrden()).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(12).SetValorDefault(BizCampo.ORDER_NO);
    panelOrd.AddItemCombo( "Ranking", LONG, OPT, "campo_orden", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getCamposCustomList(one);
			}
		}).setSizeColumns(4);
    panelOrd.AddItemEdit( "Limite", LONG, OPT, "orden_limite").setSizeColumns(2);
    panelOrd.setSizeColumns(12);
   
    JFormFieldsetResponsive fsc = row1.AddItemFieldset("Marcas");
    c=fsc.AddItemCheck( "Colorear",  OPT, "colores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setSizeColumns(12);
    
    c=fsc.AddItemColor( "Fondo", CHAR, OPT, "color_background");
    c.setSizeColumns(6);
    c=fsc.AddItemColor( "Frente", CHAR, OPT, "color_foreground");
    c.setSizeColumns(6);
    
    JFormFieldsetResponsive fsc1 = row2.AddItemFieldset("Máximos");
    c=fsc1.AddItemCheck( "Marcar máximos",  OPT, "marca_mayores");
    c.setSizeColumns(6);
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c=fsc1.AddItemCheck( "Top 10",  OPT, "marca_top");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setSizeColumns(6);
    c=fsc1.AddItemColor( "Color", CHAR, OPT, "color_topbackground");
    c.SetValorDefault("FF0000");
    c.setSizeColumns(6);
    c.setVisible(getWin().GetcDato().getObjCustomList()==null||getWin().GetcDato().getObjCustomList().isMatriz()?true:false);
    c=fsc1.AddItemEdit( "Mayores a", CHAR, OPT, "mayores_a");
    c.setSizeColumns(6);

    JFormFieldsetResponsive fsc2 = row3.AddItemFieldset("Mínimos");
    c=fsc2.AddItemCheck( "Marca menores",  OPT, "marca_menores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setSizeColumns(6);
    c=fsc2.AddItemCheck( "Top 10",  OPT, "marca_bottom");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setSizeColumns(6);
    c=fsc2.AddItemColor( "Color", CHAR, OPT, "color_bottombackground");
    c.SetValorDefault("00AA00");
    c.setSizeColumns(6);
    c.setVisible(getWin().GetcDato().getObjCustomList()==null||getWin().GetcDato().getObjCustomList().isMatriz()?true:false);
    c=fsc2.AddItemEdit( "Menores a", CHAR, OPT, "menores_a");
    c.setSizeColumns(6);


    panel.AddItemCheck( "Calcule diferencia entre campos", OPT, "calc_diferencia").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(12);
    panel.AddItemWinLov( "Campo", LONG, OPT, "campo_diferencia", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getCamposCustomList(one);
			}
		},true,true,-1).setSizeColumns(8);
    panel.AddItemCheck( "Porcentaje", OPT, "porc_diferencia").setSizeColumns(1);

    

//    JFormPanelResponsive panelDim = AddItemFieldset("Filtros DiN°mico");
//    panelDim.AddItemCheck( "Filtro DiN°mico", CHAR, REQ, "dinamico", "S", "N" );
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
  public JWins getCamposCustomList(boolean one) throws Exception {
  	GuiVirtuals campos = new GuiVirtuals();
  	campos.setRecords(getWin().GetcDato().getObjCustomList().getObjCamposAutoRef());

  	return campos; 	
  }
  
public JWins getFunciones(boolean one) throws Exception {
	if (one) {
		return JWins.createVirtualWinsFromMap(BizCampo.getFunctionAllMap());
	}
	String s = ((BizField)getWin().GetcDato()).getObjectType();
	return JWins.createVirtualWinsFromMap(BizCampo.getFunctionMap(s));
	
}
  @Override
  	public void OnPostShow() throws Exception {
		checkControls("");
		verify(true);
		super.OnPostShow();
 	}
  @Override
  	public void checkControls(String sControlId) throws Exception {
  		 verify(false);
  		 
			String op = getControles().findControl("funcion").getValue();
			BizFuncion fx = BizFuncion.findFuncion(op);
			JFormControl c =this.findControl("operador");
			if (sControlId.equals("id_gallery")) { 
				this.getWin().GetccDato().assignFieldsByIdGallery();
				this.refreshDataNewMode();
			}
  		if (this.getControles().findControl("colores").getValue().equals("S")) {
  			this.getControles().findControl("color_background").edit(this.GetModo());
  			this.getControles().findControl("color_foreground").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_background").disable();
  			this.getControles().findControl("color_foreground").disable();
  		}
  		if (this.getControles().findControl("marca_mayores").getValue().equals("S")) {
  			this.getControles().findControl("color_topbackground").edit(this.GetModo());
  			this.getControles().findControl("marca_top").edit(this.GetModo());
    		if (this.getControles().findControl("marca_top").getValue().equals("S")) 
    			this.getControles().findControl("mayores_a").disable();
    		else
    			this.getControles().findControl("mayores_a").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_topbackground").disable();
  			this.getControles().findControl("marca_top").disable();
  			this.getControles().findControl("mayores_a").disable();
  		}
  		if (this.getControles().findControl("marca_menores").getValue().equals("S")) {
  			this.getControles().findControl("color_bottombackground").edit(this.GetModo());
  			this.getControles().findControl("marca_bottom").edit(this.GetModo());
    		if (this.getControles().findControl("marca_bottom").getValue().equals("S")) 
    			this.getControles().findControl("menores_a").disable();
    		else
    			this.getControles().findControl("menores_a").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_bottombackground").disable();
  			this.getControles().findControl("marca_bottom").disable();
  			this.getControles().findControl("menores_a").disable();
  		}
 
  		if (this.isCampoValue(1, sControlId))
    		this.getWin().GetccDato().setValor(this.getWin().GetccDato().getProp(sControlId).toString());
    	
    	if (this.isCampoValue(2, sControlId))
    		this.getWin().GetccDato().setValor2(this.getWin().GetccDato().getProp(sControlId).toString());

    	String s = getWin().GetccDato().getObjectType();
    	String f = getWin().GetccDato().getOperador();
  		BizOperador oper = BizOperador.findOperador(f);
  		

    	
   
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

  
  public JWins getEjesGallery(boolean one) throws Exception {
  	return getCampos(one);
  }
  private JWins getCampos(boolean one) throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
 	  g.setRecords(this.getWin().GetcDato().getObjCustomList().getAllCampos("", true));
  	return g;
  }
  private GuiCampos getEjes(boolean one) throws Exception {
  	GuiCampos g = new GuiCampos();
  	GuiCustomList w = new GuiCustomList();
  	w.setRecord(getWin().GetcDato().getObjCustomList());
  	g.setObjCustomList(w);
 	  g.setRecords(this.getWin().GetcDato().getObjCustomList().getEjes());
  	return g;
  }
	 
 private void verify(boolean onShow) throws Exception {
 	
		this.verifyOperador(onShow);
		
		this.verifyValues(onShow);

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
//		BizCampoGallery gall = this.findCampoGallery();
//		if (gall==null) return;
		String op = getControles().findControl("funcion").getValue();
		BizFuncion fx = BizFuncion.findFuncion(op);
		JFormControl c =this.findControl("operador");
		if (fx==null) {
			c.SetReadOnly(false);
		return;
		}
		
	
		if (fx.hasOnlyFunction()) {
			getControles().findControl("has_filter").setValue("S");
			getControles().findControl("visible").setValue("N");
			
		}
		
		if (fx.hasOperador()) { // sin operador
			c.SetReadOnly(false);
		} else {
			if (onShow) c.clear();
			c.SetReadOnly(true);
			getControles().findControl("has_filter").setValue("N");
		}
		
 }

 private BizCampoGallery findCampoGallery() throws Exception {
		JWins wins =  this.getCampos(true);
		BizCampoGallery gall = (BizCampoGallery)wins.getRecords().findInHash("id",""+this.findControl("id_gallery").getValue());
		return gall;
 }

 
 public void verifyValues(boolean onShow) throws Exception {
		String operador = this.findControl("operador").getValue();
		BizOperador oper = BizOperador.findOperador(operador);
		String op = getControles().findControl("funcion").getValue();
		BizFuncion fx = BizFuncion.findFuncion(op);
		
  	getControles().findControl("campo_key").setVisible(false);
		getControles().findControl("campo_key2").setVisible(false);


		this.cleanAll();
  	if (!this.findControl("operador").isEditable()) return;
		
		getControles().findControl("descr_true").setVisible(false);
		getControles().findControl("descr_false").setVisible(false);

		if (oper==null ||!oper.hasValores()) 
			return;
		
		if (!this.getWin().GetcDato().hasRelId()) 
			return;
		getControles().findControl("descr_true").setVisible(true);
		getControles().findControl("descr_false").setVisible(true);
	
 	String s =  getWin().GetccDato().getObjectType();
 	if (s.equals("FORMULA")) return;
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

		 getControles().findControl("campo_key").setVisible(true);
		
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
			getControles().findControl("campo_key2").setVisible(true);

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
