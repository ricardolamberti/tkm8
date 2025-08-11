package  pss.common.customList.config.field.campo;

import javax.swing.JTree;

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
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.win.GuiVirtuals;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCampoNewFiltro  extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;



	JPssLabelFormLov ambito = new JPssLabelFormLov();

	private JTree jTree = null;
/**
   * Constructor de la Clase
   */
  public FormCampoNewFiltro() throws Exception {
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

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
    AddItemEdit( null, UINT, OPT, "orden_padre" ).setHide(true);

    AddItemEdit( null, CHAR, OPT, "rel_id").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo").setHide(true);
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
    AddItemEdit( null, CHAR, OPT, "porc_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "calc_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo_diferencia").setHide(true);

  //  JFormColumnResponsive col1 = AddItemColumn(3);
    JFormColumnResponsive col2 = AddItemColumn(12);
//    col1.AddItemTree( "Campo", LONG, OPT, "id_gallery",15053, new JControlTree() {
//			public JWins getRecords(boolean one) throws Exception {
//				return getGallery(one);
//			}
//		}).setRefreshForm(true);
    col2.AddItemEdit( null, CHAR, OPT, "id_gallery").setHide(true);
    col2.AddItemEdit( "Campo", CHAR, OPT, "descr_campo").SetReadOnly(true);
    col2.AddItemEdit( null, CHAR, OPT, "formato").setHide(true);
    col2.AddItemEdit( "Nombre Col.", CHAR, OPT, "nombre_columna").setHide(true);
    col2.AddItemEdit( null, CHAR, OPT, "formato_param").setHide(true);//!BizUsuario.getUsr().getObjBusiness().isCustomListSimplify());
    col2.AddItemCheck( "Visible", CHAR, OPT, "visible", "S","N").SetValorDefault(false).setHide(true);
    col2.AddItemEdit( null, CHAR, OPT, "Porcentaje").setHide(true);
    col2.AddItemCombo( "Función", CHAR, OPT, "funcion", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getFunciones(one);
    	}
    });

 
  
    JFormPanelResponsive panelFiltro = col2.AddItemFieldset("Filtros");
    panelFiltro.AddItemCheck( "Incluye filtro", OPT, "has_filter").SetValorDefault(false).setHide(true);
    panelFiltro.AddItemCombo( "Operador", CHAR, OPT, "operador",  new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getOperadores(one);
    	}
    }).SetValorDefault("=").setRefreshForm(true);

    panelFiltro.AddItemDateTime( "Valor", DATE, OPT, "value_date" ).setVisible(false);
    panelFiltro.AddItemWinLov( "Valor", CHAR, OPT, "value_lov", new JControlWin() {
   		public JWins getRecords(boolean one) throws Exception {
   			return getRecordsLOV(one, 1);
   		}
   	}).setVisible(false);
    panelFiltro.AddItemCheck( "Valor", CHAR, OPT, "value_check", "S", "N" ).setVisible(false);
    panelFiltro.AddItemEdit( "Valor", CHAR, OPT, "value_edit" );

    panelFiltro.AddItemDateTime( "Valor", DATE, OPT, "value_date2" ).setVisible(false);
    panelFiltro.AddItemWinLov( "Valor", CHAR, OPT, "value_lov2", new JControlWin() {
   		public JWins getRecords(boolean one) throws Exception {
   			return getRecordsLOV(one, 2);
   		}
   	}).setVisible(false);
    panelFiltro.AddItemCheck( "Valor", CHAR, OPT, "value_check2", "S", "N" ).setVisible(false);
    panelFiltro.AddItemEdit( "Valor", CHAR, OPT, "value_edit2" );

    panelFiltro.AddItemEdit( "Descrip.cumple", CHAR, OPT, "descr_true").SetValorDefault("Cumple").setHide(true);
    panelFiltro.AddItemEdit( "Descrip.no cumple", CHAR, OPT, "descr_false").SetValorDefault("No cumple").setHide(true);

    panelFiltro.AddItemCheck( "No incluir en totalizador porcentajes", CHAR,  "no_incluir" );
    panelFiltro.AddItemCheck( "Solo incluir en totalizador porcentajes", CHAR,  "solo_tot" );

    JFormPanelResponsive panelDim = col2.AddItemFieldset("Filtros DiN°mico");
    panelDim.AddItemCheck( "Filtro DiN°mico", CHAR, REQ, "dinamico", "S", "N" );
    panelDim.AddItemEdit( "Descripción", CHAR, OPT, "nombre_filtro" );
    panelDim.AddItemCombo( "Valor inicial", CHAR, OPT, "campo_key", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizUsuario.getUsr().getObjBusiness().getCamposClavesParaCustomList());
    	}
    });
  }
  
  public JWins getGallery(boolean one) throws Exception {
  	return getCampos(one);
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
    	if (s!=null && (s.equals(JObject.JDATE) || s.equals(JObject.JDATETIME))) {
    		getControles().findControl("campo_key").SetReadOnly(false);
    	} else {
    		getControles().findControl("campo_key").SetReadOnly(true);
    	}	
   
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
//    		if (GetControles().findControl("orientacion").getValue().equals("C")) {
//    			GetControles().findControl("calc_diferencia").edit(GetModo());
//    			GetControles().findControl("porc_diferencia").edit(GetModo());
//    			GetControles().findControl("camp_diferencia").edit(GetModo());
//    		} else {
//    			GetControles().findControl("calc_diferencia").disable();
//    			GetControles().findControl("porc_diferencia").disable();
//    			GetControles().findControl("camp_diferencia").disable();
//    			
//    		}
    		
    		super.checkControls(sControlId);
    	}
//  private JWins getCampos(boolean one) throws Exception {
//  	GuiCamposGallery g = new GuiCamposGallery();
//  	if (one) {
//  		g.setRecords(this.getWin().GetcDato().getCamposGallery());
//  	} else {
//  		if (!this.findControl("rel_id").hasValue()) return null;
//  		this.getWin().GetcDato().setRecordOwner(this.findControl("record_owner").getValue());
//  		this.getWin().GetcDato().setRelId(this.findControl("rel_id").getValue());
//  		g.setRecords(this.getWin().GetcDato().getCamposGallery());
//  	}
//  	return g;
//  }
  
  public JWins getEjesGallery(boolean one) throws Exception {
  	return getCampos(one);
  }
  
  private JWins getCampos(boolean one) throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
 	  g.setRecords(this.getWin().GetcDato().getObjCustomList().getAllCampos("", true));
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
		BizCampoGallery gall = this.findCampoGallery();
		if (gall==null) return;
		BizFuncion fx = BizFuncion.findFuncion(gall.getFunction());
		
		JFormControl c =this.findControl("operador");

		if (fx==null || fx.hasOperador()) { // sin operador
			c.SetReadOnly(false);
		} else {
			c.clear();
			c.SetReadOnly(true);
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
	
 	String s =  getWin().GetccDato().isCampoCantidad()?"JLONG":getWin().GetccDato().getObjectType();
 	if (s!=null && (s.equals("") || s.equals("FORMULA"))) return;
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
		if (s!=null && s.equals("JDATE") && !this.getWin().GetcDato().getFuncion().equals("") && !this.getWin().GetcDato().getFuncion().equals(BizField.FUNTION_ANOMES) && !this.getWin().GetcDato().getFuncion().equals(BizField.FUNTION_ANOSEM))
			isNumber=true;
		if (s!=null && (s.equals("JLONG") || s.equals("JINTEGER") ||s.equals("JFLOAT") || s.equals("COUNT")))
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

//  private JWins getGeoCampos(boolean one) throws Exception {
//  	GuiCamposGallery g = new GuiCamposGallery();
//  	if (one) {
//  		return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getCamposGallery());
//  	} else {
//  		if (!this.findControl("rel_id").hasValue()) return null;
//  		this.getWin().GetcDato().setRecordOwner(this.findControl("record_owner").getValue());
//  		this.getWin().GetcDato().setRelId(this.findControl("rel_id").getValue());
//  		return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getCamposGallery());
//  	}
//  }

//  private JWins getRecordSets(boolean one) throws Exception {
//  	return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getObjCustomList().getRelationGallery(false));
//  }

//  /**
//	 * This method initializes jCampo	
//	 * 	
//	 * @return javax.swing.JComboBox	
//	 */
//	private JComboBox getJCampo() {
//		if (jCampo == null) {
//			jCampo = new JComboBox();
//			jCampo.setBounds(new Rectangle(94, 28, 298, 22));
//		}
//		return jCampo;
//	}

}  //  @jve:decl-index=0:visual-constraint="-1,16"
