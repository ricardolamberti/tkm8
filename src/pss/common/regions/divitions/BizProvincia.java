package  pss.common.regions.divitions;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizProvincia extends JRecord {

	public static String ARG_BUENOS_AIRES = "1";
	public static String ARG_CABA = "2";
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	JString pPais=new JString();
	JString pProvincia=new JString();
	JString pDescripcion=new JString();
	JString pDescripcionAbrev=new JString();

	public String GetDescrip() throws Exception {
		return pDescripcion.getValue();
	}
	public String GetDescripAbrev() throws Exception {
		if (pDescripcionAbrev.isNull()) return GetDescrip();
		return pDescripcionAbrev.getValue();
	}

	public String GetPais() throws Exception {
		return pPais.getValue();
	}

	public String GetProvincia() throws Exception {
		return pProvincia.getValue();
	}

	public void SetDescrip(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}
	public void SetDescripAbrev(String zValue) throws Exception {
		pDescripcionAbrev.setValue(zValue);
	}
	public void SetPais(String zValue) throws Exception {
		pPais.setValue(zValue);
	}

	public void SetProvincia(String zValue) throws Exception {
		pProvincia.setValue(zValue);
	}

	public static final String ALL_PROVINCES="ALL";

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizProvincia() throws Exception {
		addItem("pais", pPais);
		addItem("provincia", pProvincia);
		addItem("descripcion", pDescripcion);
		addItem("descrip_abrev", pDescripcionAbrev);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "pais", "Pais", true, true, 15);
		addFixedItem(KEY, "provincia", "División", true, true, 15);
		addFixedItem(FIELD, "descripcion", "Descripción", true, true, 50);
		addFixedItem(FIELD, "descrip_abrev", "Descripción abreviada", true, true, 50);
	}

	// -------------------------------------------------------------------------//
	// Metodos Estaticos
	// -------------------------------------------------------------------------//
	public static boolean doesExist(boolean zWantException, String zPais, String zProvincia) throws Exception {
		if (BizProvincia.isAllProvinces(zProvincia)) return true;
		BizProvincia oProv=new BizProvincia();
		oProv.dontThrowException(true);
		boolean bEncontro=oProv.Read(zPais, zProvincia);
		if (!bEncontro&&zWantException) JExcepcion.SendError("Provincia Inexistente");
		return bEncontro;
	}

	public static boolean isAllProvinces(String zProvincia) throws Exception {
		return zProvincia.equalsIgnoreCase(BizProvincia.ALL_PROVINCES);
	}

	// -------------------------------------------------------------------------//
	// Metodos de instancia
	// -------------------------------------------------------------------------//

	@Override
	public String GetTable() {
		return "reg_provincia";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setTruncateData(zParams.isLevelCountry());
		zParams.setExportData(zParams.isLevelCountry());
	}

	@Override
	public void processInsert() throws Exception {
		if (pProvincia.isNull()) {
			BizProvincia max = new BizProvincia();
			max.addFilter("pais", this.pPais.getValue());
			this.pProvincia.setValue(max.SelectMaxIntFromString("provincia")+1);
		}
		this.insertRecord();
	}

	public boolean Read(String zPais, String zProv) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProv);
		return Read();
	}

	@Override
	public void processDelete() throws Exception {
//		if (JRecords.existsComplete(BizNodo.class, "pais", pPais.getValue(), "provincia", pProvincia.getValue())) {
//			JExcepcion.SendError("No se puede eliminar esta provincia porque está asociado a una Sucursal.");
//		}
		if (JRecords.existsComplete("pss.core.Personas.BizDomicilio", "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta provincia porque está asociada a un Domicilio.");
		}
		if (JRecords.existsComplete("pss.sj.fre.documentos.agencias.ampliatoria.BizAmpliatoria", "pais", pPais.getValue(),  "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a una ampliatoria.");
		}
		if (JRecords.existsComplete("pss.sj.tramites.tramiteContacto.BizTramiteContacto", "pais", pPais.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a un contacto.");
		}
		if (JRecords.existsComplete("pss.sj.fre.documentos.agencias.asientos.parte.BizParte", "pais", pPais.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a una Parte.");
		}
		if (JRecords.existsComplete("pss.sj.tramites.formulario.BizFormulario", "pais", pPais.getValue(), "provincia", pProvincia.getValue())) {
			JExcepcion.SendError("No se puede eliminar esta sub-división porque está asociada a una Formulario.");
		}	

		ObtenerCiudades().processDeleteAll();
		super.processDelete();
	}

	public JRecords<BizCiudad> ObtenerCiudades() throws Exception {
		JRecords<BizCiudad> oCiudades=new JRecords<BizCiudad>(BizCiudad.class);
		oCiudades.addFilter("pais", pPais.getValue());
		oCiudades.addFilter("provincia", pProvincia.getValue());
		oCiudades.readAll();
		return oCiudades;
	}

	public static String GetDescripcionReporte(String zPais, String zValor) throws Exception {
		String sDesc="";
		if (zPais.equals("")) {
			sDesc="División: ";
			sDesc+="< Todos >";
			return sDesc;
		}
		BizPais oPais=new BizPais();
		oPais.Read(zPais);
		sDesc=oPais.GetDivision()+": ";
		if (zValor.equals("")) sDesc+="< Todos >";
		else {
			BizProvincia oPcia=new BizProvincia();
			oPcia.Read(zPais, zValor);
			sDesc+=String.valueOf(zValor)+"-"+oPcia.GetDescrip();
		}
		return sDesc;
	}

}
