package pss.common.customList.config.relation;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizCampoGallery extends JRecord {

	public static final long TYPE_FIELD = 1;
	public static final long TYPE_FILTER = 2;
	
	private JLong pId = new JLong();
	private JLong pType = new JLong();
	private JString pRecordOwner = new JString();
	private JString pRecordSource = new JString();
	private JString pRelId = new JString();
	private JString pRelIdDescr = new JString();
	private JString pGrupo = new JString();
	private JString pOrden= new JString();
	private JString pCampo = new JString();
	private JString pFunction = new JString();
	private JString pDescripcion = new JString();
	private JString pDescripcionSinFuncion = new JString();
	private JString pDescripcionCompleta = new JString();
	private JString pSerialDeep = new JString();	
	private JString pUse = new JString();
	private JBoolean pSimple = new JBoolean();
	private JBoolean pFolder = new JBoolean();
	private JString pDescrCompleta = new JString() {
		public void preset() throws Exception {
			setValue(getDescrCompleta());
		};
	};
	private JString pCampoFunction = new JString() {
		public void preset() throws Exception {
			setValue(getCampoFunction());
		};
	};
	private JString pCampoSerial = new JString() {
		public void preset() throws Exception {
			setValue(getCampoSerial());
		};
	};
	private JRelation relacion;
	public void setId(long value) throws Exception {
		this.pId.setValue(value);
	}
	public void setTipo(long value) throws Exception {
		this.pType.setValue(value);
	}
	public void setRecordOwner(String value) throws Exception {
		this.pRecordOwner.setValue(value);
	}
	public void setUse(String value) throws Exception {
		this.pUse.setValue(value);
	}
	public void setSimple(boolean value) throws Exception {
		this.pSimple.setValue(value);
	}
	public void setFolder(boolean value) throws Exception {
		this.pFolder.setValue(value);
	}
	public void setRecordSource(String value) throws Exception {
		this.pRecordSource.setValue(value);
	}
	public void setRelId(String value) throws Exception {
		this.pRelId.setValue(value);
	}
	public void setRelIdDescr(String value) throws Exception {
		this.pRelIdDescr.setValue(value);
	}
	public void setSerialDeep(String value) throws Exception {
		this.pSerialDeep.setValue(value);
	}
	public void setCampo(String value) throws Exception {
		this.pCampo.setValue(value);
	}
	public void setGrupo(String value) throws Exception {
		this.pGrupo.setValue(value);
	}
	public void setOrden(String value) throws Exception {
		this.pOrden.setValue(value);
	}
	public String getOrden() throws Exception {
		return this.pOrden.getValue();
	}
	public void setFunction(String value) throws Exception {
		this.pFunction.setValue(value);
	}
	public void setDescripcion(String value) throws Exception {
		this.pDescripcion.setValue(value);
		if (pDescripcionCompleta.isNull()||pDescripcionCompleta.getValue().equals("")) 
			this.pDescripcionCompleta.setValue(value);
	}
	public String getDescripcion() throws Exception {
		return this.pDescripcion.getValue();
	}
	public void setDescripcionSinFuncion(String value) throws Exception {
		this.pDescripcionSinFuncion.setValue(value);
	}
	public String getDescripcionSinFuncion() throws Exception {
		return this.pDescripcionSinFuncion.getValue();
	}
	public void setDescrCompleta(String value) throws Exception {
		this.pDescripcionCompleta.setValue(value);
	}
	public String getUse() throws Exception {
		return this.pUse.getValue();
	}
	public boolean getSimple() throws Exception {
		return this.pSimple.getValue();
	}
	public boolean getFolder() throws Exception {
		return this.pFolder.getValue();
	}
	public boolean isFila() throws Exception {
		return getUse().indexOf("FILA")!=-1;
	}
	public boolean isColumna() throws Exception {
		return getUse().indexOf("COLUMNA")!=-1;
	}
	public boolean isCampo() throws Exception {
		return getUse().indexOf("CAMPO")!=-1;
	}
	public boolean isFiltro() throws Exception {
		return getUse().indexOf("FILTRO")!=-1;
	}
	public boolean isOrden() throws Exception {
		return getUse().indexOf("ORDEN")!=-1;
	}
	public String getDescrCompleta() throws Exception {
//		return JLanguage.translate(pRelIdDescr.getValue())+"->"+JLanguage.translate(pDescripcion.getValue());
		return JLanguage.translate(pDescripcionCompleta.getValue()) ;//+" "+JLanguage.translate("en")+" "+ JLanguage.translate(pRelIdDescr.getValue());
	}
	public String getDescrSinFuncion() throws Exception {
//	return JLanguage.translate(pRelIdDescr.getValue())+"->"+JLanguage.translate(pDescripcion.getValue());
	return JLanguage.translate(pDescripcionSinFuncion.getValue()) ;//+" "+JLanguage.translate("en")+" "+ JLanguage.translate(pRelIdDescr.getValue());
	}
	public String getCampoFunction() throws Exception {
//	return pRelIdDescr.getValue()+"->"+pDescripcion.getValue();
		return this.pSerialDeep.getValue()+ "|"+pCampo.getValue() + "|"+ pFunction.getValue();
	}
	public String getCampoSerial() throws Exception {
//	return pRelIdDescr.getValue()+"->"+pDescripcion.getValue();
		return this.pSerialDeep.getValue()+ "|"+pCampo.getValue();
	}
	public String getRecordOwner() throws Exception {
		return this.pRecordOwner.getValue();
	}
	public String getRecordSource() throws Exception {
		return this.pRecordSource.getValue();
	}
	public String getRelId() throws Exception {
		return this.pRelId.getValue();
	}
	public String getRelIdDescr() throws Exception {
		return this.pRelIdDescr.getValue();
	}
	public String getCampo() throws Exception {
		return this.pCampo.getValue();
	}
	public String getGrupo() throws Exception {
		return this.pGrupo.getValue();
	}
	public String getFunction() throws Exception {
		return this.pFunction.getValue();
	}
	public boolean hasFunction() throws Exception {
		return !this.pFunction.isEmpty();
	}
	public String getSerialDeep() throws Exception {
		return this.pSerialDeep.getValue();
	}
	public long getId() throws Exception {
		return this.pId.getValue();
	}
	public void setObjRelacion(JRelation value) throws Exception {
		this.relacion=value;
	}
	public JRelation getObjRelacion() throws Exception {
		return this.relacion;
	}
	
	public BizCampoGallery() throws Exception {
	}

	
	@Override
	public void createProperties() throws Exception {
		this.addItem("id", pId);
		this.addItem("tipo", pType);
		this.addItem("record_owner", pRecordOwner);
		this.addItem("record_source", pRecordSource);
		this.addItem("rel_id", pRelId);
		this.addItem("rel_id_descr", pRelIdDescr);
		this.addItem("campo", pCampo);
		this.addItem("grupo", pGrupo);
		this.addItem("orden", pOrden);
		this.addItem("function", pFunction);
		this.addItem("descripcion", pDescripcion);
		this.addItem("descr_completa", pDescrCompleta);		
		this.addItem("descripcion_completa", pDescripcionCompleta);		
		this.addItem("descripcion_sf", pDescripcionSinFuncion);
		this.addItem("serial_deep", pSerialDeep);		
		this.addItem("campo_function", pCampoFunction);
		this.addItem("campo_serial", pCampoSerial);
		this.addItem("simple", pSimple);
		this.addItem("folder", pFolder);
	}
	
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "id", "Id", false, false, 18);
		this.addFixedItem(FIELD, "tipo", "Tipo", false, false, 18);
		this.addFixedItem(FIELD, "record_owner", "Record Owner", true, true, 15);
		this.addFixedItem(FIELD, "rel_id", "Rel Id", true, true, 200);
		this.addFixedItem(FIELD, "record_source", "Record Source", true, true, 15);
		this.addFixedItem(FIELD, "rel_id_descr", "Relacion", true, true, 200);
		this.addFixedItem(FIELD, "campo", "Campo", true, true, 100);
		this.addFixedItem(FIELD, "grupo", "Grupo", true, true, 100);
		this.addFixedItem(FIELD, "orden", "Orden", true, true, 400);
		this.addFixedItem(FIELD, "function", "Funcion", true, true, 100);
		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 200);
		this.addFixedItem(FIELD, "descripcion_sf", "Descripción", true, true, 200);
		this.addFixedItem(FIELD, "serial_deep", "Deep", true, true, 1000);
		this.addFixedItem(FIELD, "campo_function", "campo_function", true, true, 1000);
		this.addFixedItem(FIELD, "campo_serial", "campo_serial", true, true, 1000);
		this.addFixedItem(FIELD, "simple", "Version simple", true, true, 1);
		this.addFixedItem(FIELD, "folder", "Folder", true, true, 1);
		this.addFixedItem(VIRTUAL, "descr_completa", "Descripción", true, true, 200);
		this.addFixedItem(VIRTUAL, "descripcion_completa", "Descripción", true, true, 200);
	}
	
	@Override
	public String GetTable() throws Exception {
		return "";
	}

	public boolean hasDescription() throws Exception {
		if (this.getDescripcion()==null) return false;
		if (this.getDescripcion().equals("")) return false;
		return true;
	}
	
	public void execProcessDeleteColumna(final BizCustomList list) throws Exception {
			JExec oExec = new JExec(this, "processDeleteColumna") {

				@Override
				public void Do() throws Exception {
					processDeleteColumna(list);
				}
			};
			oExec.execute();
	}
	public void execProcessDeleteFila(final BizCustomList list) throws Exception {
		JExec oExec = new JExec(this, "processDeleteFila") {

			@Override
			public void Do() throws Exception {
				processDeleteFila(list);
			}
		};
		oExec.execute();
}
	public void execProcessDeleteCampo(final BizCustomList list) throws Exception {
		JExec oExec = new JExec(this, "processDeleteCampo") {

			@Override
			public void Do() throws Exception {
				processDeleteCampo(list);
			}
		};
		oExec.execute();
	}


	public void execProcessAddCampo(final BizCustomList list) throws Exception {
		JExec oExec = new JExec(this, "processAddCampo") {

			@Override
			public void Do() throws Exception {
				processAddCampo(list);
			}
		};
		oExec.execute();
}

	public BizCampo getCampo(BizCustomList list) throws Exception {
		BizCampo eje = new BizCampo();
		eje.dontThrowException(true);
		if (!eje.read(list.getCompany(), list.getListId(),this.getSerialDeep(),this.getCampo(),this.getFunction())) return null;
		return eje;
	}

	public void processDeleteColumna(BizCustomList list) throws Exception {
		BizCampo eje = new BizCampo();
		eje.dontThrowException(true);
		if (!eje.read(list.getCompany(), list.getListId(),this.getSerialDeep(),this.getCampo(),this.getFunction())) return;
		eje.processDelete();
	}
	public void processDeleteFila(BizCustomList list) throws Exception {
		BizCampo eje = new BizCampo();
		eje.dontThrowException(true);
		if (!eje.read(list.getCompany(), list.getListId(),this.getSerialDeep(),this.getCampo(),this.getFunction())) return;
		eje.processDelete();
	}
	public void processDeleteCampo(BizCustomList list) throws Exception {
		BizCampo campo= new BizCampo();
		campo.dontThrowException(true);
		if (!campo.read(list.getCompany(), list.getListId(),this.getSerialDeep(),this.getCampo(),this.getFunction())) return;
		campo.processDelete();
	}

	

	public void processAddCampo(BizCustomList list) throws Exception {
		BizCampo campo = buildCampo(list);
		list.addCampo(campo);
		campo.processInsert();

	}
	public BizCampo buildCampo(BizCustomList list) throws Exception {
		BizCampo campo = new BizCampo();
		campo.setCompany(list.getCompany());
		campo.setListId(list.getListId());
		campo.setCampo(this.getCampo());
		campo.setFuncion(this.getFunction());
		campo.setRelId(this.getRelId());
		campo.setOrigenDatos(list.getRelId());
		campo.setOrdenPadre("0");
		campo.setObjCustomList(list);
		campo.setVisible(true);
		campo.setRecordOwner(this.getRecordOwner());
		campo.setRecordSource(this.getRecordSource());
		campo.setSerialDeep(this.getSerialDeep());

		if (campo.isFilter()) {
			campo.setVisible(false);
			campo.setHasFiltro(true);
			campo.setOperador(campo.getOperadorDefault());
			campo.setValor(campo.getValorFiltroDefault());
		}
		if (campo.isOnlyLista()) {
			if (list.isMatriz())
				list.setAgrupado(BizCustomList.PRESENTATION_TYPE_LIST);
		}
		if (!list.isLista() && !campo.isCampoFormula()) {
			if ((/*campo.getObjectType()!=null && (campo.getObjectType().equals(JObject.JLONG) ||*/// suelen ser forain key
					campo.getObjectType().equals(JObject.JINTEGER) ||
					campo.getObjectType().equals(JObject.JFLOAT) ||
					campo.getObjectType().equals(JObject.JCURRENCY)))
				campo.setFuncion(pFunction.isNull()?BizCampo.FUNTION_SUM:this.getFunction());
			else {
				if (!this.getFunction().equals("")) {
					campo.setFuncion(this.getFunction());
				} else if (campo.isDateInput()) {
					campo.setVisible(false);
					campo.setFuncion(BizFuncion.FUNTION_MESACTUAL);
					
				} else	if (campo.isDateTimeInput()) {
					campo.setVisible(false);
					campo.setFuncion(BizFuncion.FUNTION_MESACTUAL);
				
				}			
			}
		}
		return campo;
	}

	public JRecords<BizCampoGallery> getOperacionesEjes() throws Exception {
		JRecords<BizCampoGallery> recs = new  JRecords<BizCampoGallery>(BizCampoGallery.class);
		recs.setStatic(true);
		recs.addItem(this);
		JRelation rel = getObjRelacion();
		JRecord rt = rel.getObjRecordTarget();
		JProperty property = rt.getFixedPropNoExeption(getCampo());
		if (property==null) return null;
		if (property.isVirtual()) return null;
		if (property.isRecord()) return null;
		if (property.isRecords()) return null;

		JObject prop = rt.getProp(getCampo(),false);

		BizCampoGallery g;
		JMap<String,String> map =BizCampo.getFunctionMap(prop.getObjectType());
		JIterator<String> itF = map.getKeyIterator();
		while (itF.hasMoreElements()) {
			String func = itF.nextElement();
			String descr = map.getElement(func);
				g = rel.createCampoGallery();
  		g.setCampo(getCampo());
  		g.setFunction(func);
  		g.setDescripcion(JLanguage.translate(getDescripcion())+", "+JLanguage.translate(""+descr));
  		g.setDescripcionSinFuncion(JLanguage.translate(getDescripcion()));
  		recs.addItem(g);
			
		}
		return recs;
	}
	public JRecords<BizCampoGallery> getOperacionesCampos() throws Exception {
		JRecords<BizCampoGallery> recs = new  JRecords<BizCampoGallery>(BizCampoGallery.class);
		recs.setStatic(true);
		recs.addItem(this);
		JRelation rel = getObjRelacion();
		JRecord rt = rel.getObjRecordTarget();
		JProperty property = rt.getFixedPropNoExeption(getCampo());
		if (property==null) return null;
		if (property.isVirtual()) return null;
		if (property.isRecord()) return null;
		if (property.isRecords()) return null;

		JObject prop = rt.getProp(getCampo(),false);

		BizCampoGallery g;
		JMap<String,String> map =BizCampo.getFunctionMap(prop.getObjectType());
		JIterator<String> itF = map.getKeyIterator();
		while (itF.hasMoreElements()) {
			String func = itF.nextElement();
			String descr = map.getElement(func);
				g = rel.createCampoGallery();
  		g.setCampo(getCampo());
  		g.setFunction(func);
  		g.setDescripcion(JLanguage.translate(getDescripcion())+", "+JLanguage.translate(""+descr));
  		g.setDescripcionSinFuncion(JLanguage.translate(getDescripcion()));
  		recs.addItem(g);
			
		}
		return recs;
	}

	public JRecords<BizCampoGallery> getOperacionesFiltros() throws Exception {
		JRecords<BizCampoGallery> recs = new  JRecords<BizCampoGallery>(BizCampoGallery.class);
		recs.setStatic(true);
		recs.addItem(this);
		JRelation rel = getObjRelacion();
		JRecord rt = rel.getObjRecordTarget();
		JProperty property = rt.getFixedPropNoExeption(getCampo());
		if (property==null) return null;
		if (property.isVirtual()) return null;
		if (property.isRecord()) return null;
		if (property.isRecords()) return null;

		JObject prop = rt.getProp(getCampo(),false);

		BizCampoGallery g;
		JIterator<BizFuncion> itF = BizFuncion.getFunctionMapByType(prop.getObjectType()).getStaticIterator();
		while (itF.hasMoreElements()) {
			BizFuncion func = itF.nextElement();
			g = rel.createCampoGallery();
  		g.setCampo(getCampo());
  		g.setFunction(func.getFuncion());
  		g.setDescripcion(JLanguage.translate(getDescripcion())+", "+JLanguage.translate(""+JLanguage.translate(func.getDescripcion())));
  		g.setDescripcionSinFuncion(JLanguage.translate(getDescripcion()));
  		recs.addItem(g);
			
		}
		return recs;
	}
	
	@Override
	public JRecord createDefaultClone() throws Exception {
		JRecord clone = this.getClass().newInstance();
		clone.copyProperties(this,false);
		return clone;
	}
}
