package pss.common.documentos.docLocal;

import pss.common.documentos.docHtmlBase.BizDocHtmlBase;
import pss.common.documentos.tipos.BizDocFisicoTipo;
import pss.core.services.fields.JHtml;

public class BizDocLocal extends BizDocHtmlBase {

		public static final String ADJUNTO = "A";
		public static final String COMUNICACION = "C";
		
		private JHtml pBody = new JHtml();

		 
		public void setBody(String doc) throws Exception {
			pBody.setValue(doc);
		}
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizDocLocal() throws Exception {
  }
  
//  public  String verDocumento( boolean paraImpresion ) throws Exception {
//  	return this.getFileElectronico();
//  } 

  public void createProperties() throws Exception {
    super.createProperties();
    this.addItem( "body", pBody );
//    this.addItem( "image_file", pImageFile);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    super.createFixedProperties();
    this.addFixedItem( FIELD, "body", "Tipo", true, true, 10000 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "DOC_LOCAL"; }
  
  public String getTipoDocumento() throws Exception {
  	return BizDocFisicoTipo.LOCAL;
  }

	@Override
  public void processInsert() throws Exception {
  	super.processInsert();
  }
	
	
}


