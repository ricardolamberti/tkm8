package pss.core.winUI.lists;

import java.util.Iterator;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.JTools;

public class JOrdenEjeMatrix extends Object {
  
	JEjeMatrix eje;
	double valueOrden;
	String order;
	String clave;
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	int span;
	JFilaMatrix otros;
	long filas;
	JOrdenEjeMatrix lastFila=null;

	public long getFilas() {
		return filas;
	}
	public void addFilas(JOrdenEjeMatrix eje) {
		if (lastFila==null || !eje.equals(lastFila))
			this.filas++;
		lastFila=eje;

	}
	public int getSpan() {
		return span;
	}
	public void setSpan(int span) {
		this.span = span;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public JEjeMatrix getEje() {
		return eje;
	}
	public void setEje(JEjeMatrix eje) {
		this.eje = eje;
	}

	
	public JOrdenEjeMatrix(JEjeMatrix zeje) {
		eje=zeje;
	}
	public double getValueOrden() {
		return valueOrden;
	}
	public void setValueOrden(double valueOrden) {
		this.valueOrden = valueOrden;
	}
	public void clearValueOrden() {
		 valueOrden=0;
	}
	
	public double acumValueOrden(double value) {
		 valueOrden+=value;
		 return valueOrden;
	}
	
	public JFilaMatrix buildOtros() {
		if (otros!=null) return otros;
		otros = new JFilaMatrix();
	
		return otros;
	}
	public JFilaMatrix getOtros() {
		return otros;
	}
	public void setOtros(JFilaMatrix otros) {
		this.otros = otros;
	}
	public boolean hasOtros() {
		return otros!=null;
	}
	
	public JFilaMatrix buildOtros(JFilaMatrix fila,JEjeMatrix ejeCorte ) {
		Iterator<JDataMatrix> it=fila.getData().valueIterator();
		otros=fila;
		boolean clean=false;
		while (it.hasNext()) {
			JDataMatrix data = it.next();
			if (data.getEje()==null) continue;
			if (data.getEje().equals(ejeCorte)) {
				data.setValue(JLanguage.translate("Otros"));
				data.setValueAcum(JLanguage.translate("Otros"));
				clean=true;
				continue;
				
			} 
			if (clean) {
				data.setValue("");
				data.setValueAcum("");
				clean=true;
				continue;
			}
			
		}
		return fila;
	}

	public void addOtros(JFilaMatrix otros,JFilaMatrix fila ) {
		
		
		Iterator<JDataMatrix> it=otros.getData().valueIterator();
		while (it.hasNext()) {
			JDataMatrix dataOtros = it.next();
			if (dataOtros.getEje()!=null) continue;
			Iterator<JDataMatrix> itd=fila.getData().valueIterator();
			while (itd.hasNext()) {
   			JDataMatrix dataAEliminar = itd.next();
  			if (dataAEliminar.getEje()!=null) continue;
				if (dataOtros.getColumna()==null) 
					continue;
				if (!dataOtros.getColumna().equals(dataAEliminar.getColumna())) continue;
				dataOtros.setValorOrdenador(dataOtros.getValorOrdenador()+dataAEliminar.getValorOrdenador());
				dataOtros.setValueAcum(""+dataOtros.getValorOrdenador());
				dataOtros.setValue(JTools.formatDouble(dataOtros.getValorOrdenador()));
				break;
			}
		}
			Iterator<JDataMatrix> itd=fila.getData().valueIterator();
			while (itd.hasNext()) {
   			JDataMatrix dataAEliminar = itd.next();
  			if (dataAEliminar.getEje()!=null) continue;
				if (otros.getData(dataAEliminar.getColumna())!=null) continue;
				otros.addData(dataAEliminar.getColumna(), dataAEliminar);
		}
	}
	
}
