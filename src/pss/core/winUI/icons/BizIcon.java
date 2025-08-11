package pss.core.winUI.icons;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizIcon extends JRecord {
	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
//	private static JMap hProps = null;
	public JInteger pNroIcono = new JInteger();
	public JString pDescrip = new JString();
	public JString pFile = new JString();
	public JString pFile2 = new JString();

	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public BizIcon() throws Exception {}
	@Override
	public void createProperties() throws Exception {
		addItem("nro_icono", pNroIcono);
		addItem("descripcion", pDescrip);
		addItem("nombre_file", pFile);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "nro_icono", "Nro Icono", true, true, 6);
		addFixedItem( FIELD, "descripcion", "Descripción", true, true, 100);
		addFixedItem( FIELD, "nombre_file", "Archivo 16x16", true, true, 100);
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public boolean clearAllOnFinalize() {
		return true;
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(true);
		zParams.setTruncateData(true);
	}

	public boolean Read(int zIcono) throws Exception {
		this.addFilter("nro_icono", zIcono);
		return this.read();
	}

	@Override
	public void processUpdate() throws Exception {
	}

	// Force filename to lowercase!
	@Override
	public void processInsert() throws Exception {
	}


	
}
