package pss.core.winUI.lists;

import java.util.Date;

import javax.swing.SwingConstants;

import pss.common.customList.config.field.campo.BizCampo;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JObject;
import pss.core.services.records.JProperty;
import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;

public class JColumnaLista extends Object {

	//
	// alignment costants
	//
	public static final int ALIGNMENT_LEFT=0;
	public static final int ALIGNMENT_CENTER=1;
	public static final int ALIGNMENT_RIGHT=2;
	private static final String NO_ROW_FOUND_VALUE="**Deleted**";



	private JProperty oFixedProp;
	// private String sCampo ;
	private String sTitulo;
	private boolean visible=true;
	private JFormControl control=null;
	// private String sTipo ;
	private Class cClase;
	private String width=null;
	private int alignment=-1;
	private boolean translated;
	private JGrupoColumnaLista grupo;
	private boolean matrix = false;
	JDataMatrix[] top10 =new JDataMatrix[100];
	private String colorForeground=null;
	private String colorBackground=null;
	private String colorTopBackground="CC0000";
	private String colorBottomBackground="00AA00";

	private boolean marcaMayores;
	private boolean marcaMenores;
	private boolean marcaTop10;
	private boolean marcaBottom10;
	private boolean isPorcentaje;
	private boolean agrupado;
	private boolean sortable=true;
	private boolean forceTitle=false;


	private int nroIcono;
	private String selector=null;

	private String mayoresA;
	private String menoresA;
	private String functionTotalizadora="";

	public String actions;
	public String actionOnClick;
	
	public String getFunctionTotalizadora() {
		return functionTotalizadora;
	}
	public boolean isPorcentaje() {
		return isPorcentaje;
	}

	public void setPorcentaje(boolean isPorcentaje) {
		this.isPorcentaje = isPorcentaje;
	}
	public boolean isSortable() {
		return sortable;
	}
	public JColumnaLista setSortable(boolean sortable) {
		this.sortable = sortable;
		return this;
	}
	public boolean isAgrupado() {
		return agrupado;
	}
	public void setAgrupado(boolean agrupado) {
		this.agrupado = agrupado;
	}

	public void setFunctionTotalizadora(String functionTotalizadora) {
		this.functionTotalizadora = functionTotalizadora;
	}

	public int getNroIcono() {
		return nroIcono;
	}
	public void setNroIcono(int nroIcono) {
		this.nroIcono = nroIcono;
	}
	public boolean isMarcaMayores() {
		return marcaMayores;
	}

	public void setMarcaMayores(boolean marcaMayores) {
		this.marcaMayores = marcaMayores;
	}

	public boolean isMarcaMenores() {
		return marcaMenores;
	}

	public void setMarcaMenores(boolean marcaMenores) {
		this.marcaMenores = marcaMenores;
	}

	public boolean isMarcaTop10() {
		return marcaTop10;
	}

	public void setMarcaTop10(boolean marcaTop10) {
		this.marcaTop10 = marcaTop10;
	}

	public boolean isMarcaBottom10() {
		return marcaBottom10;
	}

	public void setMarcaBottom10(boolean marcaBottom10) {
		this.marcaBottom10 = marcaBottom10;
	}
	public boolean isForceTitle() {
		return forceTitle;
	}
	public void setForceTitle(boolean useTitle) {
		this.forceTitle = useTitle;
	}
	public String getMayoresA() {
		return mayoresA;
	}

	public void setMayoresA(String mayoresA) {
		this.mayoresA = mayoresA;
	}

	public String getMenoresA() {
		return menoresA;
	}

	public void setMenoresA(String menoresA) {
		this.menoresA = menoresA;
	}

	public String getColorTopBackground() {
		return colorTopBackground;
	}

	public void setColorTopBackground(String colorTopBackground) {
		this.colorTopBackground = colorTopBackground;
	}

	public String getColorBottomBackground() {
		return colorBottomBackground;
	}

	public void setColorBottomBackground(String colorBottompBackground) {
		this.colorBottomBackground = colorBottompBackground;
	}

	
	public String getColorForeground() {
		return colorForeground;
	}

	public void setColorForeground(String colorForeground) {
		this.colorForeground = colorForeground;
	}

	public String getColorBackground() {
		return colorBackground;
	}

	public void setColorBackground(String colorBackground) {
		this.colorBackground = colorBackground;
	}

	JDataMatrix[] bottom10=new JDataMatrix[100];
	public JDataMatrix[] getTop10() {
		return top10;
	}

	public void setTop10(JDataMatrix[] top10) {
		this.top10 = top10;
	}

	public JDataMatrix[] getBottom10() {
		return bottom10;
	}

	public void setBottom10(JDataMatrix[] bottom10) {
		this.bottom10 = bottom10;
	}

	public boolean isMatrix() {
		return matrix;
	}

	public JColumnaLista setMatrix(boolean matrix) {
		this.matrix = matrix;
		return this;
	}

	public JGrupoColumnaLista getGrupo() {
		return grupo;
	}

	public void setGrupo(JGrupoColumnaLista grupo) {
		this.grupo = grupo;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public JFormControl getControl() {
		return control;
	}

	public void setControl(JFormControl zControl) {
		this.control = zControl;
	}


	public void setFixedProp(JProperty zValue) {
		oFixedProp=zValue;
	}

	// public void SetCampo(String zValue) { sCampo = zValue; }
	public void SetTitulo(String zValue) {
		sTitulo=zValue;
	}

	// public void SetTipo(String zValue) { sTipo = zValue; }
	public JProperty getFixedProp() {
		return oFixedProp;
	}

	public String GetTitulo() {
		if (sTitulo==null) return "";
		return JLanguage.translate(((!sTitulo.equals("")) ? sTitulo : (oFixedProp==null) ? "" : oFixedProp.GetDescripcion()));
	}
	public String GetTituloWithFunction() {
		String s=  GetTitulo();
		if (isPorcentaje()) s+="(%)";
		else if (getFunctionTotalizadora().equals(BizCampo.FUNTION_AVR)) s+="(Prom.)";
		else if (getFunctionTotalizadora().equals(BizCampo.FUNTION_MAX)) s+="(Max.)";
		else if (getFunctionTotalizadora().equals(BizCampo.FUNTION_MIN)) s+="(Min.)";
		else if (getFunctionTotalizadora().equals(BizCampo.FUNTION_COUNT) && !s.equalsIgnoreCase("cantidad"))s+="(Cant.)";
		else if (getFunctionTotalizadora().equals(BizCampo.FUNTION_SUM)) s+="(Acum.)";
		else if (getFunctionTotalizadora().equals(BizCampo.FUNTION_NULO)) s+="(Sin valor)";
		else if (getFunctionTotalizadora().equals(BizCampo.FUNTION_NONULO)) s+="(Con valor)";
		return JLanguage.translate(s);
	}

	public String GetCampo() {
		return (oFixedProp==null) ? "" : oFixedProp.GetCampo();
	}

	public String GetTipo() {
		return (oFixedProp==null) ? "" : oFixedProp.getType();
	}

	public int getAlignment() {
		if (alignment==-1) {
			return ALIGNMENT_CENTER;
		}
		return alignment;
	}

	public JColumnaLista setAlignment(int zAlignment) {
		if (zAlignment!=ALIGNMENT_LEFT&&zAlignment!=ALIGNMENT_CENTER&&zAlignment!=ALIGNMENT_RIGHT) {
			throw new RuntimeException("Invalid column alignment");
		}
		this.alignment=zAlignment;
		return this;
	}

	public boolean hasAlignment() {
		return this.alignment!=-1;
	}

	public void setAlignmentFromType(String zType) {
		if (zType==null) {
			this.setAlignment(ALIGNMENT_CENTER);
		} else if (zType.equals(JObject.JSTRING)||zType.equals(JObject.JPASSWORD)) {
	 		this.setAlignment(ALIGNMENT_LEFT);
		} else if (zType.equals(JObject.JINTEGER)||zType.equals(JObject.JLONG)||zType.equals(JObject.JAUTONUMERICO)) {
			this.setAlignment(ALIGNMENT_LEFT);
		} else if (zType.equals(JObject.JINTEGER)||zType.equals(JObject.JFLOAT)||zType.equals(JObject.JLONG)||zType.equals(JObject.JCURRENCY)||zType.equals(JObject.JAUTONUMERICO)) {
			this.setAlignment(ALIGNMENT_RIGHT);
		} else if (zType.equals(JObject.JBDS)||zType.equals(JObject.JBD)) {
			this.setAlignment(ALIGNMENT_LEFT);
		} else {
			this.setAlignment(ALIGNMENT_CENTER);
		}
	}

	public boolean IfIcono() {
		return cClase==null;
	}

	public boolean IfBoolean() {
		return Boolean.class==cClase;
	}
	
	public boolean ifHtml() {
		return JHtml.class==cClase;
	}
	
	public boolean IfAction() {
		return BizAction.class.isAssignableFrom(cClase);
	}

	public Class GetClase() {
		return cClase;
	}

	public JColumnaLista(Class zClase) {
		// sCampo = null;
		sTitulo=null;
		// sTipo = "campo";
		cClase=zClase;
		for (int k1=0;k1<100;k1++) top10[k1]=bottom10[k1]=null;

		this.deduceDefaultAlignent();
	}
	public String getAction() {
		return actions;
	}
	// actions separadas por , ej: 1,2,5,6 o para todas: all
	public void setAction(String actions) {
		this.actions = actions;
	}
	public String getActionOnClick() {
		return actionOnClick;
	}
	public JColumnaLista setActionOnClick(int actionOnClick) {
		this.actionOnClick = ""+actionOnClick;
		return this;
	}
	public void setActionOnClick(String actionOnClick) {
		this.actionOnClick = actionOnClick;
	}

	private void deduceDefaultAlignent() {
		if (cClase==null) {
			this.alignment=-1;
		} else if (Float.class.isAssignableFrom(cClase)) {
			this.alignment=ALIGNMENT_RIGHT;
		} else if (Double.class.isAssignableFrom(cClase)) {
			this.alignment=ALIGNMENT_RIGHT;
		} else if (Number.class.isAssignableFrom(cClase)) {
			this.alignment=ALIGNMENT_LEFT;
		} else if (Boolean.class==cClase) {
			this.alignment=ALIGNMENT_CENTER;
		} else if (Date.class.isAssignableFrom(cClase)) {
			this.alignment=ALIGNMENT_LEFT;
		} else if (cClase==String.class) {
			this.alignment=ALIGNMENT_LEFT;
		} else {
			this.alignment=-1;
		}
	}

	public String GetColumnaTitulo() {
		try {
			return JLanguage.translate(this.GetTitulo());
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isVirtual(JWin zWin) {
		if (oFixedProp==null) return true;
		return oFixedProp.isVirtual();
	}

//	public Object getValue(JWin zWin) {
//		try {
//			// si es ícono
//			if (IfIcono()) {
//				if (zWin.getRecord().wasRead()) return zWin.GetIconImage();
//				else return GuiIconos.GetGlobal().buscarIcono(GuiIcon.MAS_ICON).GetImage();
//			}
//			if (IfAction()) {
//				return "";
//			}
//			// if ( !zWin.GetDato().getDatosLeidos() ) return null;
//			// si no...
//			JObject oProp=zWin.getRecord().getProp(this.GetCampo());
//			oProp.preset();
//			if (oProp==null||oProp.isRawNull()) {
//				return null;
//			} else if (oProp.isBoolean()) {
//				return oProp.asRawObject(); // ya se hizo el Pre() con el IsNull()
//			} else if (oProp.isString()&&this.translated) {
//				return JLanguage.translate(oProp.toRawFormattedString());
//			} else {
//				return oProp.toRawFormattedString(); // ya se hizo el Pre() con el IsNull()
//			}
//		} catch (JNoRowFoundException e) {
//			return NO_ROW_FOUND_VALUE;
//		} catch (JExcepcion e) {
//			return JLanguage.translate(e.getMessage());
//		} catch (Throwable e) {
//			PssLogger.logDebug(e, "Could not get value for field: "+this.GetCampo());
//			return null;
//		}
//	}




//	public Object getCastValue(JWin zWin) {
//		try {
//			// si es ícono
//			if (IfIcono()) {
//				if (zWin.getRecord().wasRead()) return zWin.GetIconImage();
//				else return GuiIconos.GetGlobal().buscarIcono(GuiIcon.MAS_ICON).GetImage();
//			}
//			if (IfAction()) {
//				return "";
//			}
//			// si no...
//			JObject oProp=zWin.getRecord().getProp(this.GetCampo());
//			if (oProp==null||oProp.isNull()) {
//				return null;
//			} else if (oProp.isString()&&this.translated) {
//				return JLanguage.translate(oProp.toRawString()); // ya se hizo el Pre() con el IsNull()
//			} else {
//				return oProp.asRawObject(); // ya se hizo el Pre() con el IsNull()
//			}
//		} catch (JNoRowFoundException e) {
//			return NO_ROW_FOUND_VALUE;
//		} catch (JExcepcion e) {
//			return e.getMessage();
//		} catch (Throwable e) {
//			PssLogger.logDebug(e, "Could not get value for field: "+this.GetCampo());
//			return null;
//		}
//	}

	public void resetWidth() {
		width=null;
	}

	

	public JColumnaLista setWidth(String maxWidth) {
		this.width = maxWidth;
		return this;
	}
	public String getWidth() {
		return width;
	}


//	public TableCellRenderer createRenderer() {
//		PssTableCellRenderer renderer;
//		if (IfBoolean()) {
//			renderer=PssTableCellRenderer.booleanRenderer();
//		} else {
//			renderer=PssTableCellRenderer.defaultRenderer();
//		}
//		renderer.setHorizontalAlignment(getSwingHorizontalAlignment());
//		return renderer;
//	}

	public int getSwingHorizontalAlignment() {
		int columnAlignment=this.alignment;
		switch (columnAlignment) {
		case JColumnaLista.ALIGNMENT_LEFT:
			return SwingConstants.LEFT;
		case JColumnaLista.ALIGNMENT_CENTER:
			return SwingConstants.CENTER;
		case JColumnaLista.ALIGNMENT_RIGHT:
			return SwingConstants.RIGHT;
		default:
			return SwingConstants.CENTER;
		}
	}

	// public String GetTabla(JWin zWin) throws Exception {
	// if (sTabla!=null && !sTabla.equals("")) return sTabla;
	// sTabla = (zWin==null)?"":zWin.GetDato().getFixedProp(sCampo).GetTabla();
	// return sTabla;
	// }

	public boolean hasShowListColumn(JWin zWin) throws Exception {
		if (this.GetClase().getName().equals("java.util.Date")) return false;
		if (this.GetClase().getName().equals("java.lang.Boolean")) return false;
		if (this.GetCampo()==null||this.GetCampo().equals("")) return false;
		if (zWin!=null&&zWin.getRecord().getProp(this.GetCampo()).getObjectType().equals(JObject.JHOUR)) return false;
		return true;
	}

	public boolean isTranslated() {
		return translated;
	}

	public void setTranslated(boolean b) {
		translated=b;
	}
	
	private String colSpan=null;
	public void setSpanWith(String c) {
		colSpan=c;
	}
	public String getSpan() {
		return colSpan;
	}
	public boolean hasSpan() {
		return colSpan!=null;
	}

	private String tagHtml=null;
	public void setTagHtml(String c) {
		this.tagHtml=c;
	}
	public String makeHtml(String value) {
		return "<"+this.tagHtml+">"+JTools.encodeString2(value)+"</"+this.tagHtml+">";
	}
	public boolean hasHtml() {
		return this.tagHtml!=null;
	}
	public boolean hasHtmlTitulo() {
		return this.GetColumnaTitulo().startsWith("<")&&this.GetColumnaTitulo().endsWith("/>");
	}
	private int idAction=0;
	public void setIdAction(int v) {
		this.idAction=v;
	}
	public int getIdAction() {
		return this.idAction;
	}
	public boolean hasIdAction() {
		return this.idAction!=0;
	}

	private String headerBackground;
	public void setHeaderBackground(String v) {
		this.headerBackground=v;
	}
	public String getHeaderBackground() {
		return this.headerBackground;
	}
	public boolean hasHeaderBackground() {
		return this.headerBackground!=null;
	}
	
	public boolean hasSelector() {
		return selector!=null;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}

}
