package pss.common.documentos;

import java.util.Date;

import pss.core.services.JExec;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JMap;


public abstract class BizDocumento extends JRecord {

  private JLong pIddoc = new JLong(); 
  private JString pTipoDoc = new JString();
  private JString pCompany = new JString();
  private JString pTitulo = new JString();
  private JDateTime pFecha = new JDateTime();
	private JString pUsuario = new JString();
	private JLong pIdDocPadre = new JLong();
	private JString pSourceTipo = new JString();
	private JLong pSourceCodigo = new JLong();
	private JLong pIdPlantilla = new JLong();
	
  BizDocum docum;
  public void setObjDocum(BizDocum value) throws Exception {    
  	this.docum=value;  
  }
  public void setIddoc(long zValue) throws Exception {    pIddoc.setValue(zValue);  }
  public long getIddoc() throws Exception {     return this.pIddoc.getValue();  }
  public void setTipoDoc(String zValue) throws Exception {    pTipoDoc.setValue(zValue);  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {   return this.pCompany.getValue();}
//  public void setSubTipo(String zValue) throws Exception {    pSubTipo.setValue(zValue);  }
  public void setTitulo(String zValue) throws Exception {    pTitulo.setValue(zValue);  }
  public String getTitulo() throws Exception {return pTitulo.getValue();}
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception { return this.pFecha.getValue();}
  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {return pUsuario.getValue();}
  public long getIdDocPadre() throws Exception {return pIdDocPadre.getValue();}
  public void setIdDocPadre(long v) throws Exception { pIdDocPadre.setValue(v);}
  public boolean hasFecha() throws Exception { return pFecha.isNotNull();  }
  public void setSourceCodigo(long v) throws Exception { pSourceCodigo.setValue(v);}
  public void setSourceTipo(String v) throws Exception { pSourceTipo.setValue(v);}
  public long getSourceCodigo() throws Exception {return pSourceCodigo.getValue();}
  public String getSourceTipo() throws Exception {return pSourceTipo.getValue();}
  public void setIdPlantilla(long zValue) throws Exception {    pIdPlantilla.setValue(zValue);  }
  public long getIdPlantilla() throws Exception {     return this.pIdPlantilla.getValue();  }
  public boolean isNullIdPlantilla() throws Exception {     return this.pIdPlantilla.isNull();  }
  

  public abstract String getTipoDocumento() throws Exception;
  
  public String getTipoDoc() throws Exception {     
  	return this.pTipoDoc.getValue();  
  }
 
//  public String getSubTipo() throws Exception {     
//  	return this.getObjDocFisico().getSubTipo();  
//  }
//
  
  /**
   * Constructor de la Clase
   */
  public BizDocumento() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_doc", pIddoc );
    this.addItem( "doc_tipo_doc", pTipoDoc );
    this.addItem( "doc_company", pCompany);
    this.addItem( "doc_titulo", pTitulo );
    this.addItem( "doc_fecha", pFecha );
    this.addItem( "doc_usuario", pUsuario);
    this.addItem( "doc_id_doc_padre", pIdDocPadre);
    this.addItem( "doc_source_tipo", pSourceTipo);
    this.addItem( "doc_source_codigo", pSourceCodigo);
    this.addItem( "doc_plantilla", pIdPlantilla);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_doc", "Id doc", true, true, 18 );
    this.addFixedItem( FOREIGN, "doc_tipo_doc", "Tipo doc", true, true, 50 );//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_company", "Company", true, true, 15 );//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_fecha", "Fecha Creacion", true, true, 10 );//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_titulo", "Titulo", true, false, 200);//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_usuario", "Usuario", true, false, 30);//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_id_doc_padre", "Id Doc PAdre", true, false, 18);//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_source_tipo", "Tipo Source", true, false, 5);//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_source_codigo", "Código Source", true, false, 5);//.setTabla("docum");
    this.addFixedItem( FOREIGN, "doc_plantilla", "plantilla", true, false, 18);//.setTabla("docum");
  } 
  
	@Override
	protected boolean loadForeignFields() throws Exception {
		return true;
	}

	  public BizDocum load(BizDocum doc) throws Exception {
			this.setIddoc(doc.getIddoc());
			this.setTitulo(doc.getTitulo());
			this.setTipoDoc(doc.getTipoDoc());
			this.setCompany(doc.getCompany());
//			this.setSubTipo(doc.getSubTipo());
			this.setFecha(doc.getFecha());
			this.setUsuario(doc.getUsuario());
			this.setIdDocPadre(doc.getIddocPadre());
			this.setSourceTipo(doc.getSourceTipo());
			this.setSourceCodigo(doc.getSourceCodigo());
			this.setIdPlantilla(doc.getPlantilla());
			this.docum=doc;
			return doc;
		}

  @Override
	public void processDelete() throws Exception {
		BizDocum doc = this.getObjDocum();
		doc.delete();

		super.delete();
	}
  
	
	public void clean() throws Exception {
  }
	
	public String getDocName() throws Exception {
		return this.getClass().getName();
  }
	
	
	
	@Override
	public void processInsert() throws Exception {
		BizDocum doc = new BizDocum();
		this.setTipoDoc(this.getTipoDocumento());
		this.copyTo(doc);
		doc.processInsert();
		this.setIddoc(doc.getIdentity("id_doc"));
		this.setObjDocum(doc);
		this.insert();
	}

	
	@Override
	public void processUpdate() throws Exception {
		BizDocum doc = new BizDocum();
		doc.read(this.getIddoc());
		this.copyTo(doc);
		doc.update();
		this.update();
		this.setObjDocum(doc);
	}

	
	private BizDocum copyTo(BizDocum doc) throws Exception {
		doc.setCompany(this.pCompany.getValue());
		doc.setTipoDoc(this.pTipoDoc.getValue());
//		doc.setSubtipo(this.pSubTipo.getRawValue());
		doc.setTitulo(this.pTitulo.getValue());
		doc.setFecha(this.pFecha.getValue());
		doc.setIddocPadre(this.pIdDocPadre.getValue());
		doc.setUsuario(this.pUsuario.getValue());
		doc.setSourceTipo(this.pSourceTipo.getValue());
		doc.setSourceCodigo(this.pSourceCodigo.getValue());
		doc.setPlantilla(this.pIdPlantilla.getValue());
		return doc;
	}


//  public BizDocum load(BizDocum doc) throws Exception {
//		this.setIddoc(doc.getIddoc());
//		this.setTitulo(doc.getTitulo());
//		this.setTipoDoc(doc.getTipoDoc());
//		this.setCompany(doc.getCompany());
//		this.setFecha(doc.getFecha());
//		this.setUsuario(doc.getUsuario());
//		this.setIdDocPadre(doc.getIddocPadre());
//		this.setSourceTipo(doc.getSourceTipo());
//		this.setSourceCodigo(doc.getSourceCodigo());
//		this.docum=doc;
//		return doc;
//	}
	
	public String getDocumFiels() throws Exception {
		return
			"doc.fecha as doc_fecha, "
		+ "doc.titulo as doc_titulo, "
		+ "doc.tipo_doc as doc_tipo_doc, "
		+ "doc.company as doc_company, "
		+ "doc.usuario as doc_usuario, "
		+ "doc.source_tipo as doc_source_tipo, "
		+ "doc.source_codigo as doc_source_codigo, "
		+ "doc.plantilla as doc_plantilla, "
		+ "doc.id_doc_padre as doc_id_doc_padre ";

	}
  
	@Override
	public String GetTableTemporal() throws Exception {
		return "(select my.*, "
				+ this.getDocumFiels() 
				+ " from "+this.GetTable()+" my"
				+ " join doc_docum doc on doc.id_doc=my.id_doc"
				+ ") "
				+ this.GetTable();
	}

	public String verDocumento(boolean paraImpresion) throws Exception {
		return "";
	}
  public String generatePrint() throws Exception {
  	return verDocumento(true) ;
 }


// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public BizDocum getObjDocum() throws Exception {
		if (this.docum!=null)return this.docum;
		BizDocum c = new BizDocum();
		c.dontThrowException(true);
		if (!c.read(this.getIddoc()))
			JExcepcion.SendError("Documento sin doc");
		c.setObjDocumento(this);
//		this.load(c);
		return (this.docum=c);//this.load(c);
	}
	
//	public boolean read() throws Exception {
//		if (!super.read()) 
//			return false;
//		if (this.hasIdDoc())
//			this.load(getObjDocum());
//		return true;
//	}

	public boolean Read( long idDoc ) throws Exception {
		this.addFilter("id_doc", idDoc);
		return this.read();
	}

  /*TODO Ademas deberia chequear que el pdf este
   */
  


//  public JMap<String, String> getSubtipos() throws Exception {
//  	return JCollectionFactory.createMap();
//  }

//  public boolean isEncarpetarAutomatico() {
//  	return false;
//  }

	public boolean isTemporary() throws Exception {
		return this.pIddoc.isNull();
	}
	

//	public boolean canEncarpetar() throws Exception {
//		return false;
//	}

//	public boolean canArchivar() throws Exception {
//		return false;
//	}

//	public boolean isProtocolizableObligatorio() throws Exception {
//		return false;
//	}


//	public boolean isVinculable() throws Exception {
//		return false;
//	}
	



  public boolean hasDocum() throws Exception {
  	return this.docum!=null;
  }

  public boolean hasIdDoc() throws Exception {
  	return this.pIddoc.getValue()!=0L;
  }

  public boolean hasIdDocPadre() throws Exception {
  	return this.pIdDocPadre.getValue()!=0L;
  }

  public boolean hasTitulo() throws Exception {   
  	if (!pTitulo.isEmpty()) return true;
  	if (!this.hasIdDoc()) return false;
  	return !this.getObjDocum().getTitulo().isEmpty();  
  }

  public BizDocumento createClone() throws Exception {
  	BizDocumento doc = (BizDocumento)this.createDefaultClone();
  	doc.setDatosLeidos(false);
//  	doc.load(this.getObjDocum());
  	return doc;
  }

  public boolean canEliminar() throws Exception { 
  	return true;
  }
  
  public boolean isReadOnly() throws Exception {
  	return false;
  }
  
  public void onNotifySource(BizDocumento doc) throws Exception {
  }
  
  public boolean isCorreo() throws Exception {
  	return this.getObjDocum().isCorreo();
  }
//  public boolean isCorreoExterno() throws Exception {
//  	return this.getObjDocum().isCorreoExterno();
//  }
  public boolean isElectronico() throws Exception {
  	return this.getObjDocum().isElectronico();
  }

  public String getResumen(String defa) throws Exception {
  	return defa;
  }
  
//	public void notifyPadre() throws Exception {
//		if (!this.getObjDocum().hasPadre()) return;
//		this.getObjDocum().getObjDocPadre().getObjDocumento().notifyPadre(this);
//	}
//	
//	public void notifyPadre(BizDocumento child) throws Exception {
//	}
//	
	public String execProcessExecute(JMap<String,Long> values) throws Exception {
		JExec oExec = new JExec(null, "processVincular") {

			@Override
			public void Do() throws Exception {
				setOutput(processExecute(values));
			}
		};
		oExec.execute();
		return (String) oExec.getOutput();
	}
	
	public String processExecute(JMap<String,Long> values) throws Exception {
		return null;
	}
   
	public boolean deleteOnDesvincular() throws Exception {
		return false;
	}
	
	public String execProcessRollback() throws Exception {
		JExec oExec = new JExec(null, "processVincular") {

			@Override
			public void Do() throws Exception {
				setOutput(processRollback());
			}
		};
		oExec.execute();
		return (String) oExec.getOutput();
	}
	
	public String processRollback() throws Exception {
		return null;
	}
   
}
