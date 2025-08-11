package  pss.common.regions.nodes;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRegionNodo extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pCompany = new JString();
  JLong   pRegion = new JLong  ();
  JString pNodo = new JString();
  JString pDescrNodo = new JString() { @Override
	public void preset() throws Exception {ObtenerDescrNodo();}};

  public JLong       getRegion() { return pRegion; }
  public JString     getNode() { return pNodo; }

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizRegionNodo() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "company",  pCompany );
    addItem( "region",  pRegion);
    addItem( "nodo", pNodo );
    addItem( "descr_nodo", pDescrNodo );
    addItem( "descripcion",pDescrNodo );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY,   "company",     "Empresa"                , true, true, 15 );
    addFixedItem( KEY,   "region",      "Región"              , true, true, 18 );
    addFixedItem( KEY,   "nodo",        "Sucursal"            , true, true, 50 );
    addFixedItem( VIRTUAL, "descr_nodo",  "Descripción del nodo", true, true, 50 );
    addFixedItem( VIRTUAL, "descripcion", "Descripción del nodo",true, true, 50 );
  }

  @Override
	public String GetTable() { return "nod_region_nodo";}

  @Override
	public void setupConfig(JSetupParameters zParams ) throws Exception {
    zParams.setExportData(zParams.isLevelNode());
  }


  public void ObtenerDescrNodo() throws Exception {
    BizNodo oNodo = new BizNodo();
    oNodo.Read(pCompany.getValue(), pNodo.getValue());
    this.pDescrNodo.setValue(oNodo.GetDescrip());

  }

}
