package pss.core.winUI.lists;

import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.win.JWin;

public class JDataMatrix {

	String columna;
	String value;
	String valueAcum;

	String key;

	JEjeMatrix eje;

	Class type;
	int rowspan=1;
	int colspan=1;
	boolean visible=true;
	boolean multiple=false;

	JWin win;
	double acumulador=0;
	long cantidad=0;
	double valorOrdenador=0;
	
	String colorForeground;
	String colorBackground;
	String classResponsive;
	public String getClassResponsive() {
		return classResponsive;
	}
	public void setClassResponsive(String classResponsive) {
		this.classResponsive = classResponsive;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public double getValorOrdenador() {
		return valorOrdenador;
	}
	public void setValorOrdenador(double valorOrdenador) {
		this.valorOrdenador = valorOrdenador;
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
	public double getAcumulador() {
		return acumulador;
	}
	public void setAcumulador(double acumulador) {
		this.acumulador = acumulador;
	}
	public long getCantidad() {
		return cantidad;
	}
	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}
	public JWin getWin() {
		return win;
	}
	public void setWin(JWin win) {
		this.win = win;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int cspan) {
		this.colspan = cspan;
	}
	public String getColumna() {
		return columna;
	}
	public void setColumna(String columna) {
		this.columna = columna;
	}
	public String getValue() {
		return value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValueAcum() {
		return valueAcum;
	}
	public void setValueAcum(String valueAcum) {
		this.valueAcum = valueAcum;
	}
	public JEjeMatrix getEje() {
		return eje;
	}
	public void setEje(JEjeMatrix eje) {
		this.eje = eje;
	}
	public void setValue(String value) {
//		if (type!=null && (type.equals(JFloat.class)||type.equals(JLong.class)||type.equals(JInteger.class)||type.equals(JCurrency.class))) {
//			if (this.value==null) 
//				this.value=value;
//			else {
//				this.value += " "+value;
//				multiple = true;
//			}
//		}
//		else 
			this.value=value;
	}
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}
	public void setType(String type) {
		if (type.equals(JObject.JFLOAT)) this.type = JFloat.class;
		else if (type.equals(JObject.JCURRENCY)) this.type = JCurrency.class;
		else if (type.equals(JObject.JINTEGER)) this.type = JInteger.class;
		else if (type.equals(JObject.JLONG)) this.type = JLong.class;
		else if (type.equals(JObject.JSTRING)) this.type = JString.class;
		else if (type.equals(JObject.JDATE)) this.type = JDate.class;
		else if (type.equals(JObject.JDATETIME)) this.type = JDateTime.class;
		else 
			this.type = JString.class;;
	}

	// filtros
}
