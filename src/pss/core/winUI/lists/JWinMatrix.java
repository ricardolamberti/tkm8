package pss.core.winUI.lists;

import java.util.Iterator;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.IControl;
import pss.core.win.JWins;

public class JWinMatrix extends JWinList{

	protected JList<JEjeMatrix> aEjeMatrix;
	protected JMap<Long,JOrdenMatrix> aOrdenMatrix;
	long filas;
	JFilaMatrix otros;

	JOrdenEjeMatrix lastFila=null;
	public JFilaMatrix buildOtros(JFilaMatrix fila ) {
		Iterator<JDataMatrix> it=fila.getData().valueIterator();
		otros=fila;
		boolean clean=false;
		while (it.hasNext()) {
			JDataMatrix data = it.next();
			if (data.getEje()==null) continue;
			if (!clean) {
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

	public JFilaMatrix getOtros() {
		return otros;
	}
	public void setOtros(JFilaMatrix otros) {
		this.otros = otros;
	}
	public boolean hasOtros() {
		return otros!=null;
	}
	public long getFilas() {
		return filas;
	}
	public void addFilas(JOrdenEjeMatrix eje) {
		if (lastFila==null || !eje.equals(lastFila))
			this.filas++;
		lastFila=eje;
	}
	
	public JOrdenMatrix AddOrdenMatrix(long id,JOrdenMatrix orden) {
		GetOrdenMatrix().addElement(id, orden);
		return orden;
	}
	
	public JMap<Long,JOrdenMatrix> GetOrdenMatrix() {
		if (aOrdenMatrix==null) aOrdenMatrix=JCollectionFactory.createOrderedMap();
		return aOrdenMatrix;
	}

	public JWinMatrix(JWins zWins) throws Exception {
		super(zWins);
	}
	public JWinMatrix(JWins wins, IControl zControlWins) throws Exception {
		super(wins,zControlWins);
	}
	public JList<JEjeMatrix> GetEjesMatrix() {
		return aEjeMatrix;
	}

	public JEjeMatrix getEje(String zFieldName) {
		JIterator<JEjeMatrix> oColsIt=this.aEjeMatrix.getIterator();
		for(int i=0; oColsIt.hasMoreElements(); i++) {
			JEjeMatrix oColumna=oColsIt.nextElement();
			if (oColumna.GetCampo()!=null&&oColumna.GetCampo().equalsIgnoreCase(zFieldName)) {
				return oColumna;
			}
		}
		return null;
	}


	public JEjeMatrix AddEjeMatrix(JEjeMatrix zCol) {
		if (aEjeMatrix==null) aEjeMatrix=JCollectionFactory.createList();
		if (zCol.getLevel()==0)
			zCol.setLevel(aEjeMatrix.size()+1);
		aEjeMatrix.addElement(zCol);
		return zCol;
	}
	public JEjeMatrix AddEjeMatrix(String zTitulo, int type) throws Exception {
		return this.AddEjeMatrix(zTitulo, type,getEmptyProperty()).setEmpty(true);
	}
	private JProperty getEmptyProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, "empty", "Empty", null, "", true, true, 18, 0, null, null, null);
	}
	public JEjeMatrix AddEjeMatrix(String zTitulo, int type, JProperty zFixedProp) throws Exception {
		return this.AddEjeMatrix(zTitulo, zFixedProp,type);
	}

//	public JEjeMatrix AddEjeMatrix(String zTitulo, int type, JProperty zFixedProp,BizCampo rank) throws Exception {
//		return this.AddEjeMatrix(zTitulo, zFixedProp,type,rank);
//	}

//	public JEjeMatrix AddEjeMatrix(String zTitulo, String zCampo, int type,BizCampo rank) throws Exception {
//		JProperty prop=getWinRef().getRecord().getFixedPropDeep(zCampo);
//		return this.AddEjeMatrix(zTitulo, prop,type,rank);
//	}
	public JEjeMatrix AddEjeMatrix(String zTitulo, String zCampo, int type) throws Exception {
		JProperty prop=getWinRef().getRecord().getFixedPropDeep(zCampo);
		return this.AddEjeMatrix(zTitulo, prop,type);
	}
	public JEjeMatrix AddEjeMatrix(String zTitulo, JProperty zFixedProp, int type) throws Exception {

		JEjeMatrix oColumna=new JEjeMatrix();
		oColumna.setFixedProp(zFixedProp);
		if (zTitulo==null) zTitulo=zFixedProp.GetDescripcion();
		oColumna.SetTitulo(JLanguage.translate(zTitulo));
		oColumna.setType(type);
//		if (rank!=null) {
//			oColumna.setRankCampo(rank.getNameField());
//			oColumna.setLimite(rank.getOrdenLimite());
//		} else {
//			oColumna.setRankCampo(null);
//			oColumna.setLimite(-1);
//		}
		return this.AddEjeMatrix(oColumna);
	}
	

}
