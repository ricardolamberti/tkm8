package pss.common.documentos.docElectronico;

import pss.common.documentos.BizDocumento;
import pss.common.documentos.biblioteca.BizBiblioteca;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;

public abstract class BizDocBaseElectronico  extends BizDocumento {

	
	protected JString pNombreArchivo = new JString();
	protected JLong pIdBiblio = new JLong();
	protected JString pType = new JString();

	public void setIdBiblio(long zValue) throws Exception {    pIdBiblio.setValue(zValue);  }
	public long getIdBiblio() throws Exception {     return pIdBiblio.getValue();  }
	public void setType(String zValue) throws Exception {    pType.setValue(zValue);  }
	public String getType() throws Exception {     return pType.getValue();  }
	public String getNombreArchivo() throws Exception {     return pNombreArchivo.getValue();  }
	public boolean hasType() throws Exception {     return !pType.isEmpty();  }
	public String findNombreArchivo() throws Exception {     return JTools.scapeFileName(pNombreArchivo.getValue());  }
	 
	 
  BizBiblioteca biblio = null;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void setNombreArchivo(String value) {
  	this.pNombreArchivo.setValue(value);
  }

	/**
	 * Constructor de la Clase
	 */
	public BizDocBaseElectronico() throws Exception {
	}


public void createProperties() throws Exception {
  super.createProperties();
  this.addItem( "nombre_archivo", pNombreArchivo );
  this.addItem( "id_biblio", pIdBiblio );
  this.addItem( "type", pType);
}
/**
 * Adds the fixed object properties
 */
public void createFixedProperties() throws Exception {
  super.createFixedProperties();
  this.addFixedItem( FIELD, "nombre_archivo", "Nombre Archivo", true, true, 300 );
  this.addFixedItem( FIELD, "id_biblio", "Id Biblioteca", true, true, 18 );
  this.addFixedItem( FIELD, "type", "Tipo", true, true, 10 );
}
/**
 * Returns the table name
 */

public BizBiblioteca getObjBiblioteca() throws Exception {
	if (this.biblio!=null) return this.biblio;
	BizBiblioteca b = new BizBiblioteca();
	b.read(this.pIdBiblio.getValue());
	return (this.biblio=b);
}

@Override
public void processDelete() throws Exception {
	BizBiblioteca biblo = new BizBiblioteca();
	biblo.dontThrowException(true);
	if (biblo.read(pIdBiblio.getValue())) {
		biblo.processDelete();
	}
	super.processDelete();
}



public boolean hasIdBiblio() throws Exception {
	return this.pIdBiblio.getValue()!=0L;
}

public String findType() throws Exception {
	return BizBiblioteca.findType(this.findNombreArchivo());
}

public String findName() throws Exception {
	return JTools.cleanFileName(this.findNombreArchivo());
}

@Override
public void processInsert() throws Exception {
	String absFile = this.findNombreArchivo();

	// Get source File Name
	BizBiblioteca biblo = new BizBiblioteca();
	biblo.setCompany(this.getCompany());
	biblo.setTipo(this.findType());
//	biblo.dontThrowException(true);
//	biblo.read(pIdBiblio.getValue());
	if (this.hasIdBiblio()) // viene de un biblio anterior clonado o de un reintento y el archivo se supone que ya esta 
		biblo.setFileName(this.getObjBiblioteca().getFileName());
	else
		biblo.captureFile(absFile);

	
	biblo.processInsert();
	this.biblio=biblo;
	
	this.pNombreArchivo.setValue(this.findName());
	if (!this.hasTitulo()) this.setTitulo(this.pNombreArchivo.getValue());
	if (!this.hasType()) this.pType.setValue(biblo.getTipo());
	this.pIdBiblio.setValue(biblo.getId());
	super.processInsert();
	
	biblo.setIdDocPadre(this.getIddoc());
	biblo.update();
}

public boolean isTypePdf() throws Exception {
	return this.getType().equalsIgnoreCase("pdf");
}

public boolean isTypeExcel() throws Exception {
	if (this.getType().equalsIgnoreCase("xls")) return true;
	if (this.getType().equalsIgnoreCase("xlsx")) return true;
	if (this.getType().equalsIgnoreCase("xlt")) return true;
	if (this.getType().equalsIgnoreCase("ods")) return true;
	return false;
}


public boolean isTypeDoc() throws Exception {
	if (this.getType().equalsIgnoreCase("doc")) return true;
	if (this.getType().equalsIgnoreCase("odt")) return true;
	if (this.getType().equalsIgnoreCase("docx")) return true;
	return false;
}

public boolean isTypeTxt() throws Exception {
	if (this.getType().equalsIgnoreCase("txt")) return true;
	if (this.getType().equalsIgnoreCase("log")) return true;
	return false;
}

public boolean isTypeImage() throws Exception {
	if (this.getType().equalsIgnoreCase("png")) return true;
	if (this.getType().equalsIgnoreCase("jpf")) return true;
	if (this.getType().equalsIgnoreCase("gif")) return true;
	if (this.getType().equalsIgnoreCase("jpeg")) return true;
	return false;
}


public String getAbsoluteFileName() throws Exception {
	if (this.hasIdBiblio())
		return this.getObjBiblioteca().getAbsoluteFileName();
	return this.findNombreArchivo();
}


}


