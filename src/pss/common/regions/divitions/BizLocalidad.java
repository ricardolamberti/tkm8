package  pss.common.regions.divitions;

import pss.common.components.JSetupParameters;
import pss.common.personalData.BizDomicilio;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizLocalidad extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JString pPais=new JString();
	private JString pProvincia=new JString();
	private JString pCiudadId=new JString();
	private JString pLocalidad=new JString();
	private JString pLocalidadId=new JString();
	private JString pCodigoPostal=new JString();

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

	public void setCiudadId(String zVal) throws Exception {
		pCiudadId.setValue(zVal);
	}

	public String getCiudadId() throws Exception {
		return pCiudadId.getValue();
	}

	public void setLocalidad(String zVal) throws Exception {
		pLocalidad.setValue(zVal);
	}

	public String getLocalidad() throws Exception {
		return pLocalidad.getValue();
	}

	public String getLocalidadId() throws Exception {
		return pLocalidadId.getValue();
	}

	public void setLocalidadId(String zVal) throws Exception {
		pLocalidadId.setValue(zVal);
	}

	public void setCodigoPostal(String zVal) throws Exception {
		pCodigoPostal.setValue(zVal);
	}

	public String getCodigoPostal() throws Exception {
		return pCodigoPostal.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizLocalidad() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("pais", pPais);
		addItem("provincia", pProvincia);
		addItem("ciudad_id", pCiudadId);
		addItem("localidad_id", pLocalidadId);
		addItem("localidad", pLocalidad);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "pais", "Pais", true, true, 15);
		addFixedItem(KEY, "provincia", "División", true, true, 15);
		addFixedItem(KEY, "ciudad_id", "Sub División", true, true, 15);
		addFixedItem(KEY, "localidad_id", "Id Sub-Sub División", true, false, 15);
		addFixedItem(FIELD, "localidad", "Descripción", true, true, 50);
	}

	@Override
	public String GetTable() {
		return "REG_LOCALIDAD";

	}

	@Override
	public void processInsert() throws Exception {
		if (pLocalidadId.isNull()) pLocalidadId.setValue(calcularSiguienteIdLocalidad()); // ver esto, porque tira error cuando no son numericas
		super.processInsert();
	}

	@Override
	public void processDelete() throws Exception {

		if (JRecords.existsComplete("pss.core.Personas.BizDomicilio", "pais", pPais.getValue(), "ciudad_id", pCiudadId.getValue(), "localidad_id", pLocalidadId.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-sub-división porque está asociada a un Domicilio.");
		}
		super.processDelete();
	}

	private int calcularSiguienteIdLocalidad() throws Exception {
		BizLocalidad oLocalidad=new BizLocalidad();
		oLocalidad.addFilter("pais", pPais.getValue());
		oLocalidad.addFilter("provincia", pProvincia.getValue());
		oLocalidad.addFilter("ciudad_id", pCiudadId.getValue());
		// busco próximo ID localidad;

		int max=oLocalidad.SelectMaxInt("localidad_id");
		return max+1;
	}

	/*
	 * public boolean Read(String zPais, String zProvincia, int zCiudadId, int zLocalidadId) throws Exception { SetFiltros("pais", zPais); SetFiltros("provincia", zProvincia); SetFiltros("ciudad_id", zCiudadId); SetFiltros("localidad_id", zLocalidadId); return this.Read(); }
	 */

	public boolean Read(String zPais, String zProvincia, String zCiudadId, String zLocalidadId) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		addFilter("localidad_id", zLocalidadId);
		return this.Read();
	}

	public String GetLocalidadByDesc(String zPais, String zProvincia, String zCiudadId, String zDescripcion) throws Exception {
		dontThrowException(true);
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		addFilter("localidad", zDescripcion);
		Read();
		if (selectCount()==0) {
			return "0";
		}
		return getLocalidadId();
	}

	public boolean Read(String zPais, String zProvincia, int zCiudadId, int zLocalidadId) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		addFilter("localidad_id", zLocalidadId);
		return this.Read();
	}

	@Override
	public void processUpdate() throws Exception {
		// BizDomicilio oDomicilio = new BizDomicilio();
		// BizCiudad oCiudad = new BizCiudad ();
		// if (oCiudad.Read(getPais(), getProvincia(), Integer.parseInt(this.getCiudadId()))){
		// oDomicilio.SetFiltros("pais", getPais());
		// oDomicilio.SetFiltros("provincia", getProvincia());
		// oDomicilio.SetFiltros("ciudad", oCiudad.getDescripcion());
		// if (oDomicilio.Read()) {
		JRecords<BizDomicilio> oDomicilios=new JRecords<BizDomicilio>(BizDomicilio.class);
		oDomicilios.addFilter("pais", this.pPais.getValue());
		oDomicilios.addFilter("provincia", this.pProvincia.getValue());
		oDomicilios.addFilter("ciudad_id", this.pCiudadId.getValue());
		oDomicilios.addFilter("localidad_id", this.pLocalidadId.getValue());
		oDomicilios.addField("localidad", pLocalidad.getValue());
		// oDomicilios.ReadAll();
		oDomicilios.update();
		super.processUpdate();
		// }else JExcepcion.SendError("No existe la localidad");
		// }
	}

	public static String GetDescripcionReporte(String zPais, String zPcia, int zCiudad, int zCodigo, String zDesc) throws Exception {
		String sDesc="";
		if (zPais.equals("")) {
			sDesc="Sub-Sub División: ";
			sDesc+="< Todos >";
			return sDesc;
		}
		BizPais oPais=new BizPais();
		oPais.Read(zPais);
		sDesc=oPais.GetLocalidad()+": ";
		if (oPais.ifUsaTablaLocalidad()) {
			if (zCodigo==0) sDesc+="< Todos >";
			else {
				BizLocalidad oLoc=new BizLocalidad();
				oLoc.Read(zPais, zPcia, String.valueOf(zCiudad), String.valueOf(zCodigo));
				sDesc+=String.valueOf(zCodigo)+"-"+oLoc.getLocalidad();
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

}
