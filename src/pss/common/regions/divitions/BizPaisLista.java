package  pss.common.regions.divitions;

import pss.common.components.JSetupParameters;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizPaisLista extends JRecord {
	
	public static final String DEFAULT_ICON = "mundo.gif";

	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
	JString pPais = new JString();
	JString pDescripcion = new JString();
	JString pIconoFile = new JString();
	JString pGentilicio = new JString();
	JString pRegion = new JString();
	JString pContinente = new JString();
	JLong   pCodigoPaisAfip = new JLong();
	
	public String GetDescrip() throws Exception {
		return pDescripcion.getValue();
	}
	public String GetRegion() throws Exception {
		return pRegion.getValue();
	}
	public String GetContinente() throws Exception {
		return pContinente.getValue();
	}
	public String GetPais() throws Exception {
		return pPais.getValue();
	}
	public String GetIcono() throws Exception {
		return (pIconoFile.isEmpty())?DEFAULT_ICON:pIconoFile.getValue();
	}
	public String GetGentilicio() throws Exception {
		return pGentilicio.getValue();
	}
	
	public long GetCodigoPaisAfip() throws Exception {
		return pCodigoPaisAfip.getValue();
	}
	

	public void setDescrip(String value) throws Exception {
		pDescripcion.setValue(value);
	}
	public void setRegion(String value) throws Exception {
		pRegion.setValue(value);
	}
	public void setContinente(String value) throws Exception {
		pContinente.setValue(value);
	}
	public void setPais(String value) throws Exception {
		pPais.setValue(value);
	}

	public void setGentilicio(String value) throws Exception {
		pGentilicio.setValue(value);
	}
	
	public void setCodigoPaisAfip(long value) throws Exception {
		pCodigoPaisAfip.setValue(value);
	}
	
	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public BizPaisLista() throws Exception {
		addItem("pais", pPais);
		addItem("descripcion", pDescripcion);
		addItem("icono_file", pIconoFile);
		addItem("gentilicio", pGentilicio);
		addItem("region", pRegion);
		addItem("continente", pContinente);
		addItem("pais_afip", pCodigoPaisAfip);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "pais", "Código", true, true, 2);
		addFixedItem(FIELD, "descripcion", "Nombre País", true, true, 50);
		addFixedItem(FIELD, "icono_file", "Icono", true, false, 50);
		addFixedItem(FIELD, "gentilicio", "Gentilicio", true, false, 50);
		addFixedItem(FIELD, "region", "Zona", true, false, 100);
		addFixedItem(FIELD, "continente", "Continente", true, false, 100);
		addFixedItem(FIELD, "pais_afip", "Codigo de Pais Afip", true, false, 18);
	}

	//-------------------------------------------------------------------------//
	// Metodos Estaticos
	//-------------------------------------------------------------------------//
	@Override
	public String GetTable() {
		return "reg_pais";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(zParams.isLevelCountry());
	}

	public boolean Read(String zPais) throws Exception {
		addFilter("pais", zPais);
		return read();
	}
	@Override
	public void processDelete() throws Exception {
		if (JRecords.existsComplete(BizPais.class, "pais", pPais.getValue())) {
			JExcepcion.SendError("Esta es un país configurado");
		}
		super.processDelete();
	}

	public static String GetDescripcionReporte(String zValor) throws Exception {
		String sDesc = "";
		sDesc = "País: ";
		if (zValor.equals(""))
			sDesc += "< Todos >";
		else {
			BizPaisLista oPais = new BizPaisLista();
			oPais.Read(zValor);
			sDesc += String.valueOf(zValor) + "-" + oPais.GetDescrip();
		}
		return sDesc;
	}
	
	static  JMap<String, String> objRegiones;
	static  JMap<String, String> objContinentes;
	
  public static JMap<String, String> getContinentes() throws Exception {
  	if (objContinentes!=null) return objContinentes;
  	JMap<String, String> map = JCollectionFactory.createMap();
  	map.addElement("Europe", "Europa" );
  	map.addElement("America", "America" );
  	map.addElement("Asia", "Asia" );
  	map.addElement("Oceania", "Oceania" );
  	map.addElement("Africa", "Africa" );
  	map.addElement("Antartida", "Antartida" );
  	map.addElement("Artico", "Artico" );
  	return objContinentes= map;
  }
  public static JMap<String, String> getRegiones() throws Exception {
  	if (objRegiones!=null) return objRegiones;
  	JMap<String, String> map = JCollectionFactory.createMap();
  	map.addElement("Europe", "Europa" );
  	map.addElement("America del sur", "America del sur" );
  	map.addElement("America central", "America central" );
  	map.addElement("America del norte", "America del norte" );
  	map.addElement("Caribe", "Caribe" );
  	map.addElement("Medio Oriente", "Medio Oriente" );
  	map.addElement("Asia", "Asia" );
  	map.addElement("Oceania", "Oceania" );
  	map.addElement("Africa", "Africa" );
  	map.addElement("Antartida", "Antartida" );
  	map.addElement("Artico", "Artico" );
  	return objRegiones=map;
  }

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.common.regions.divitions.GuiPaisesLista");
		rels.addRelationParent(33, "Continente Origen", BizPaisLista.getContinentes(),"continente");
		rels.addRelationParent(34, "Zona Origen", BizPaisLista.getRegiones(),"region");


		super.attachRelationMap(rels);
	}

  private static JMap<String, BizPaisLista> map;
	public static BizPaisLista findPais(String pais) throws Exception {
		if (map==null) map=JCollectionFactory.createMap();
		BizPaisLista p = map.getElement(pais);
		if (p!=null) return p;
		p = new BizPaisLista();
		p.dontThrowException(true);
		if (!p.Read(pais))
				return null;
		map.addElement(pais, p);
		return p;
	}

	
	@Override
	public void processInsert() throws Exception {
		super.processInsert();
		checkPais();
	}
	
	
	public void checkPais() throws Exception {
		BizPais p = new BizPais();
		p.dontThrowException(true);
		if (!p.Read(GetPais())) {
			BizPais pModelo = new BizPais();
			pModelo.dontThrowException(true);
			if (pModelo.Read(BizUsuario.getUsr().getBirthCountryId())) {
				p.copyProperties(pModelo);
				p.setPais(GetPais());
				p.setDescripcion(GetDescrip());
				p.processInsert();
			}
		}
	}
	
}
