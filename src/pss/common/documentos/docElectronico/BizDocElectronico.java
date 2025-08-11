package pss.common.documentos.docElectronico;

import pss.common.documentos.BizDocumento;
import pss.common.documentos.biblioteca.BizBiblioteca;
import pss.common.documentos.tipos.BizDocFisicoTipo;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizDocElectronico extends BizDocBaseElectronico {

		public static final String ADJUNTO = "A";
		public static final String COMUNICACION = "C";
		public static final String IMG_EMBEDDED = "E";
		
		private JString pSubType = new JString();
	
		public void setSubType(String zValue) throws Exception {    pSubType.setValue(zValue);  }
		public boolean hasType() throws Exception {     return !pType.isEmpty();  }
		 
		 
	  BizBiblioteca biblio = null;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	  public void setNombreArchivo(String value) {
	  	this.pNombreArchivo.setValue(value);
	  }

  /**
   * Constructor de la Clase
   */
  public BizDocElectronico() throws Exception {
  }
  
//  public  String verDocumento( boolean paraImpresion ) throws Exception {
//  	return this.getFileElectronico();
//  } 

  public void createProperties() throws Exception {
    super.createProperties();
    this.addItem( "sub_type", pSubType);
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    super.createFixedProperties();
    this.addFixedItem( FIELD, "sub_type", "Sub Tipo", true, true, 2 );
//    this.addFixedItem( VIRTUAL, "image_file", "Image File", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "DOC_ELECTRONICO"; }
  
  public String getTipoDocumento() throws Exception {
  	return BizDocFisicoTipo.ELECT;
  }

  
	
	public static String getDescrTipo(String tipo) throws Exception {
		return BizDocElectronico.getTipos().getElement(tipo);
	}
	
	public static JMap<String, String> subtipos;
	public static JMap<String, String> getTipos() throws Exception {
		if (subtipos!=null) return subtipos;
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(ADJUNTO, "ADJUNTO");
		map.addElement(COMUNICACION, "COMUNICACION");
		return (subtipos=map);
	}

  public JMap<String, String> getSubtipos() throws Exception {
  	return BizDocElectronico.getTipos();
  }

	public String getAbsoluteFileName() throws Exception {
		if (this.hasIdBiblio())
			return this.getObjBiblioteca().getAbsoluteFileName();
		return this.findNombreArchivo();
	}


}


