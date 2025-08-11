package  pss.common.regions.divitions;

import pss.common.components.JSetupParameters;
import pss.common.personalData.BizDomicilio;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizCiudad extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JString pPais=new JString();
	private JString pProvincia=new JString();
	private JString pCiudad=new JString();
	private JString pDescripcion=new JString();
	private JString pDescrAbrev=new JString();
	private JString pCodPostal=new JString();
	private JLong pDepartamentoId=new JLong() {
		public void preset() throws Exception {
			if (getObjCiudadDepartamento()!=null && (getFilterValue("departamento_id")==null))
				pDepartamentoId.setValue(getObjCiudadDepartamento().getDepartamentoId());
		};
	};
	private JBoolean pSinDpto=new JBoolean();
  private JString pDescrPais = new JString() {
  	public void preset() throws Exception {
  		pDescrPais.setValue(getDescrPais());
  	};
  };
  private JString pDescrProvincia =  new JString() {
  	public void preset() throws Exception {
  		pDescrProvincia.setValue(getDescrProvincia());
  	};
  };

	public void setPais(String zVal) throws Exception {
		pPais.setValue(zVal);
	}

	public String getPais() throws Exception {
		return pPais.getValue();
	}

	public void setProvincia(String zVal) throws Exception {
		pProvincia.setValue(zVal);
	}

	public String getProvincia() throws Exception {
		return pProvincia.getValue();
	}

	public void setCiudad(String zVal) throws Exception {
		pCiudad.setValue(zVal);
	}

	public String getCiudad() throws Exception {
		return pCiudad.getValue();
	}

	public void setDescripcion(String zVal) throws Exception {
		pDescripcion.setValue(zVal);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}
	public void setDescrAbrev(String zVal) throws Exception {
		pDescrAbrev.setValue(zVal);
	}

	public String getDescrAbrev() throws Exception {
		return pDescrAbrev.isNull()?pDescripcion.getValue():pDescrAbrev.getValue();
	}

  public String getDescrProvincia() throws Exception {     return getObjProvincia()==null?"":getObjProvincia().GetDescrip();  }
  public String getDescrPais() throws Exception {     return getObjPais()==null?"":getObjPais().GetDescrip();  }

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//

	public BizCiudad() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("pais", pPais);
		addItem("provincia", pProvincia);
		addItem("ciudad_id", pCiudad);
		addItem("ciudad", pDescripcion);
		addItem("abreviatura", pDescrAbrev);
		addItem("departamento_id", pDepartamentoId);
		addItem("sin_dpto", pSinDpto);
    addItem("cod_postal", pCodPostal );
    addItem("descr_pais", pDescrPais );
    addItem("descr_provincia", pDescrProvincia );
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "pais", "Pais", true, true, 15);
		addFixedItem(KEY, "provincia", "División", true, true, 15);
		addFixedItem(KEY, "ciudad_id", "Id Sub División", true, true, 15);
		addFixedItem(FIELD, "ciudad", "Descripción", true, true, 50);
		addFixedItem(FIELD, "abreviatura", "Abreviatura", true, false, 50);
    this.addFixedItem( VIRTUAL, "sin_dpto", "Filtro sin dpto", true, true, 1 );
    this.addFixedItem( VIRTUAL, "departamento_id", "Departamento Id", true, true, 15 );
    this.addFixedItem( VIRTUAL, "descr_pais", "Pais", true, true, 15 );
    this.addFixedItem( VIRTUAL, "cod_postal", "Codigo Postal", true, true, 15 );
    this.addFixedItem( VIRTUAL, "descr_provincia", "Provincia", true, true, 15 );
	}

	@Override
	public String GetTable() {
		return "REG_CIUDAD";
	}

	@Override
	public void processInsert() throws Exception {
		BizPais oPais=new BizPais();
		oPais.Read(getPais());
		// if (oPais.getUsaTablaCiudad().equalsIgnoreCase("S")){
		// BizPersona oPer = new BizPersona();
		// }

		if (pCiudad.isNull()) pCiudad.setValue(""+calcularSiguienteIdCiudad());
		pDescripcion.setValue(pDescripcion.getValue().toUpperCase());
		this.pDescrAbrev.setValue(pDescrAbrev.getValue().toUpperCase());

		super.processInsert();
		
		if (pDepartamentoId.isNotNull()) {
			BizDepartamento d = new BizDepartamento();
			d.dontThrowException(true);
			if (d.read(pDepartamentoId.getValue())) {
				d.processVincularCiudad(this);
			}
		}

		if (pCodPostal.isNotNull()) {
			BizCiudadCP ciudadcp = new BizCiudadCP();
			ciudadcp.setCiudad(pCiudad.getValue());
			ciudadcp.setPais(this.getPais());
			ciudadcp.setProvincia(this.getProvincia());
			ciudadcp.setCodPostal(pCodPostal.getValue());
			ciudadcp.processInsert();
		}
	}

	@Override
	public void processDelete() throws Exception {
		if (JRecords.existsComplete("pss.core.Personas.BizDomicilio", "pais", pPais.getValue(), "ciudad_id", pCiudad.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a un Domicilio.");
		}
		if (JRecords.existsComplete("pss.sj.fre.documentos.agencias.ampliatoria.BizAmpliatoria", "pais", pPais.getValue(), "ciudad_id", pCiudad.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a una ampliatoria.");
		}
		if (JRecords.existsComplete("pss.sj.tramites.tramiteContacto.BizTramiteContacto", "pais", pPais.getValue(), "ciudad_id", pCiudad.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a un contacto.");
		}
		if (JRecords.existsComplete("pss.sj.fre.documentos.agencias.asientos.parte.BizParte", "pais", pPais.getValue(), "ciudad_id", pCiudad.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a una Parte.");
		}
		if (JRecords.existsComplete("pss.sj.tramites.formulario.BizFormulario", "pais", pPais.getValue(), "ciudad_id", pCiudad.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a una Formulario.");
		}		
		ObtenerLocalidades().processDeleteAll();
		JRecords<BizCiudadDepartamento> d = new JRecords<BizCiudadDepartamento>(BizCiudadDepartamento.class);
		d.addFilter("pais", getPais());
		d.addFilter("provincia", getProvincia());
		d.addFilter("ciudad_id", getCiudad());
		d.toStatic();
		d.processDeleteAll();

		super.processDelete();
	}

	public JRecords<BizLocalidad> ObtenerLocalidades() throws Exception {
		JRecords<BizLocalidad> oLocalidades=new JRecords<BizLocalidad>(BizLocalidad.class);
		oLocalidades.addFilter("pais", pPais.getValue());
		oLocalidades.addFilter("provincia", pProvincia.getValue());
		oLocalidades.addFilter("ciudad_id", pCiudad.getValue());
		oLocalidades.readAll();
		return oLocalidades;
	}

	private int calcularSiguienteIdCiudad() throws Exception {
		BizCiudad oCiudad=new BizCiudad();
		oCiudad.dontThrowException(true);
		oCiudad.addFilter("pais", pPais.getValue());
		oCiudad.addFilter("provincia", pProvincia.getValue());
		oCiudad.addFixedOrderBy("to_number(REG_CIUDAD.ciudad_id, '9999999999') DESC");
		// busco próximo ID ciudad;
		// int max=oCiudad.SelectMaxInt("ciudad_id");
		if (oCiudad.read() == false)
			return 1;
		return Integer.valueOf(oCiudad.getCiudad()) +1;
	}
	public boolean readCP(String zCP) throws Exception {
		addFilter("cod_postal", zCP);
		return this.read();
	}
	public void clean() throws Exception {
		objCiudadDepartamento=null;
		objDepartamento=null;
	};
	
	public void execProcessDesvincularPartido() throws Exception {
		JExec oExec = new JExec(this, "execProcessDesvincularPartido") {

			@Override
			public void Do() throws Exception {
				processDesvincularPartido();
			}
		};
		oExec.execute();
	}
	
	public void processDesvincularPartido() throws Exception {
		BizCiudadDepartamento d =getObjCiudadDepartamento();
		if (d!=null) d.processDelete();
		clean();

	}
  BizPaisLista objPais;
	public BizPaisLista getObjPais() throws Exception {
		if (objPais!=null) return objPais;
		BizPaisLista d = new BizPaisLista();
		d.dontThrowException(true);
		if (!d.Read(getPais())) return null;
		return objPais=d;
	}
	BizProvincia objProvincia;
	public BizProvincia getObjProvincia() throws Exception {
		if (objProvincia!=null) return objProvincia;
		BizProvincia d = new BizProvincia();
		d.dontThrowException(true);
		if (!d.Read(getPais(),getProvincia())) return null;
		return objProvincia=d;
	}
	
	BizCiudadDepartamento objCiudadDepartamento;
	public BizCiudadDepartamento getObjCiudadDepartamento() throws Exception {
		if (objCiudadDepartamento!=null) return objCiudadDepartamento;
		BizCiudadDepartamento d = new BizCiudadDepartamento();
		d.dontThrowException(true);
		if (!d.readByCiudad(getPais(),getProvincia(),getCiudad())) return null;
		return objCiudadDepartamento=d;
	}
	BizDepartamento objDepartamento;
	public BizDepartamento getObjDepartamento() throws Exception {
		if (objDepartamento!=null) return objDepartamento;
		BizCiudadDepartamento cd = getObjCiudadDepartamento();
		if (cd==null) return null;
		
		BizDepartamento d = new BizDepartamento();
		d.dontThrowException(true);
		if (!d.read(cd.getDepartamentoId())) return null;
		return objDepartamento=d;
	}
	
	public boolean Read(String zPais, String zProvincia, String zCiudadId) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		return this.read();
	}

	public String GetCityByDesc(String zPais, String zProvincia, String zDescripcion) throws Exception {
		dontThrowException(true);
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad", zDescripcion);
		read();
		if (selectCount()==0) {
			return "0";
		} else {
			return getCiudad();
		}
	}

	public boolean Read(String zPais, String zProvincia, int zCiudadId) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		return this.read();
	}

	@Override
	public void processUpdate() throws Exception {
		// BizCiudad oCiudad = new BizCiudad();
		// oCiudad.SetNoExcSelect(true);
		// if (oCiudad.Read(this.getPais(), this.getProvincia(), Integer.parseInt(this.getCiudad()))){
		// BizDomicilio oDomicilio = new BizDomicilio();
		// oDomicilio.SetNoExcSelect(true);
		// oDomicilio.SetFiltros("pais", this.getPais());
		// oDomicilio.SetFiltros("provincia", this.getProvincia());
		// if (oDomicilio.Read()) {
		pDescripcion.setValue(pDescripcion.getValue().toUpperCase());
		this.pDescrAbrev.setValue(pDescrAbrev.getValue().toUpperCase());
		JRecords<BizDomicilio> oDomicilios=new JRecords<BizDomicilio>(BizDomicilio.class);
		oDomicilios.addFilter("pais", this.pPais.getValue());
		oDomicilios.addFilter("provincia", this.pProvincia.getValue());
		oDomicilios.addFilter("ciudad_id", this.pCiudad.getValue());
		oDomicilios.addField("ciudad", pDescripcion.getValue());
		// oDomicilios.ReadAll();
		oDomicilios.update();
		super.processUpdate();
		// } else {
		// JExcepcion.SendError("No existe la ciudad");
		// }
		// }
		BizDepartamento dep = this.getObjDepartamento();
		if (dep!=null && pDepartamentoId.isNull())
			this.processDesvincularPartido();
		else if (dep!=null) {
			if (dep.getDptoId()!=pDepartamentoId.getValue()) {
				this.getObjDepartamento().processVincularCiudad(this);
			}
		}
	}

	public static String GetDescripcionReporte(String zPais, String zPcia, int zCodigo, String zDesc) throws Exception {
		String sDesc="";
		if (zPais.equals("")) {
			sDesc="Sub División: ";
			sDesc+="< Todos >";
			return sDesc;
		}
		BizPais oPais=new BizPais();
		oPais.Read(zPais);
		sDesc=oPais.GetCiudad()+": ";
		if (oPais.ifUsaTablaCiudad()) {
			if (zCodigo==0) sDesc+="< Todos >";
			else {
				BizCiudad oCiu=new BizCiudad();
				oCiu.Read(zPais, zPcia, zCodigo);
				sDesc+=String.valueOf(zCodigo)+"-"+oCiu.getDescripcion();
			}
		} else {
			if (zDesc.equals("")) sDesc+="< Todos >";
			else {
				sDesc+=zDesc;
			}
		}
		return sDesc;
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setTruncateData(zParams.isLevelCountry());
		zParams.setExportData(zParams.isLevelCountry());
	}

	public JRecords<BizCiudadCP> getCodigosPostales() throws Exception {
		JRecords<BizCiudadCP> regs=new JRecords<BizCiudadCP>(BizCiudadCP.class);
		regs.addFilter("pais", pPais.getValue());
		regs.addFilter("provincia", pProvincia.getValue());
		regs.addFilter("ciudad_id", pCiudad.getValue());
		regs.readAll();
		return regs;
	}
	
}
