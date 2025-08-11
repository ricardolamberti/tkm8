package pss.common.customList.config.field.campo;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.relation.BizCampoGallery;
import pss.common.customList.config.relation.GuiCampoGallery;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizCampos extends JRecords<BizCampo> {

//-------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizCampos() throws Exception {
	}

	
	@Override
	public Class<BizCampo> getBasedClass() {
		return BizCampo.class;
	}

	private BizCustomList customList;
	public void setObjCustomList(BizCustomList zCustomList) throws Exception {
		customList=zCustomList;
	}
	public BizCustomList getCustomList() {
		return customList;
	}

	private BizCampo filtroParent;
	public void setObjFiltroParent(BizCampo zParent) throws Exception {
		filtroParent=zParent;
	}
	public BizCampo getFiltroParent() {
		return filtroParent;
	}


	@Override
	public BizCampo createItem(JBaseRegistro zSet) throws Exception {
		BizCampo c = super.createItem(zSet);
		c.setObjCustomList(customList);
		c.setObjFiltroParent(filtroParent);
		return c;
	}
	
	public BizCampo buildNewRecord(JRecord parent,JRecord origen,String keyField,String keyFieldFuente,String keyFieldParent,String keyFieldFuenteParent, boolean table) throws Exception {
		BizCampo campo=new BizCampo();
		if (parent instanceof BizCustomList) {
			BizCustomList list = (BizCustomList) parent;
			setObjCustomList(list);
			BizCampoGallery campogal = (BizCampoGallery) origen;
			campogal.processAddCampo(list);
		} else if (parent instanceof BizCampo) {
			BizCampoGallery campogal = (BizCampoGallery) origen;
			campo = campogal.buildCampo(getCustomList());
			campo.copyKeysProperties(parent,false);
			campo.setNullToSecuencia();
			campo.getProp(keyField).setValue(origen.getPropDeep(keyFieldFuente));
			campo.setObjCustomList(this.getCustomList());
			campo.setObjFiltroParent((BizCampo)parent);
			campo.processDrop(this.getCustomList());
			campo.setOperacion(BizCampo.OPER_AND);
		}
		return campo;
	}
	
	public void processFillRecords(JRecord parent,JRecords fuentes, String keyFieldFuente, String keyField, String keyFieldFuenteParent, String keyFieldParent, String parentField) throws Exception {
	 	JMap<String,BizCampoGallery> map = fuentes.convertToHash(keyFieldFuente);
//	 	JMap<String,BizCampoGallery> mapD = fuentes.convertToHash(keyFieldFuente);

	 	JIterator<BizCampo> it = this.getStaticIterator();
  	int fila=0;
  	while (it.hasMoreElements()) {
  		try {
				fila++;
				BizCampo destino = it.nextElement();
				
				String key = destino.getPropDeep(keyField).toString();
				if (map.containsKey(key)) {
					BizCampoGallery gal = map.getElement(key);
					if (gal.getAttach()!=null && gal.getAttach().equals(destino)) {
						map.removeElement(key);
						continue; // ya existe
					}
				}
			} catch (Exception e) {
				PssLogger.logError(e);
				throw new Exception("FILA "+fila+": "+e.getMessage());
			}
  	}
  	
	 	JIterator<BizCampo> itd = this.getStaticIterator();
  	int filad=0;
  	while (itd.hasMoreElements()) {
  		try {
				filad++;
				BizCampo destino = itd.nextElement();
				boolean borrar=true;
				String key = destino.getPropDeep(keyField).toString();
				JIterator<BizCampoGallery> itg = fuentes.getStaticIterator();
				while (itg.hasMoreElements()) {
					BizCampoGallery gal = itg.nextElement();
					if (gal.getAttach()!=null && gal.getAttach().equals(destino)) {
						borrar=false;
						break; // ya existe
					}
				}
				//remover
				if (borrar)
					itd.remove();
			} catch (Exception e) {
				PssLogger.logError(e);
				throw new Exception("FILA "+filad+": "+e.getMessage());
			}
  	}
  	

  	// faltantes
  	JIterator<BizCampoGallery> itr = map.getValueIterator();
  	while (itr.hasMoreElements()) {
  		try {
				JRecord origen = (JRecord)itr.nextElement();
				BizCampo destino = this.buildNewRecord(parent,origen,keyField,keyFieldFuente,keyFieldParent,keyFieldFuenteParent);
			} catch (Exception e) {
				PssLogger.logError(e);
				throw new Exception("FILA "+fila+": "+e.getMessage());
			}
  	}
//		if (parent!=null)
//			parent.getProp(parentField).setValue(this);

	}
}
