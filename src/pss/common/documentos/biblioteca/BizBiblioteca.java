package pss.common.documentos.biblioteca;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import pss.JPath;
import pss.core.data.files.JBaseFile;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;

public class BizBiblioteca extends JRecord {

	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JString pTipo = new JString();
	private JLong pIdDocPadre = new JLong();
	private JLOB pContenido = new JLOB();
	private JString pFileName = new JString();


	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setId(long zValue) throws Exception {
		pId.setValue(zValue);
	}

	public long getId() throws Exception {
		return pId.getValue();
	}

	public boolean hasFileName() throws Exception {
		return !this.pFileName.isEmpty();
	}

	public void setIdDocPadre(long zValue) throws Exception {
		pIdDocPadre.setValue(zValue);
	}

	public void setTipo(String zValue) throws Exception {
		pTipo.setValue(zValue);
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public void setFileName(String zValue) throws Exception {
		pFileName.setValue(zValue);
	}

	public String getFileName() throws Exception {
		return pFileName.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public String getAbsoluteFileName() throws Exception {
		return this.getBibloPath()+this.getFileName();
	}

	public String getTipo() throws Exception {
		return pTipo.getValue();
	}


	public void setContenido(String value) throws Exception {
		pContenido.setValue(value);
//		pContenido.setValue(JTools.BinToHexString(value, value.length()));
	}
	
	public String getContenido() throws Exception {
		return this.pContenido.getValue();
	}


	public String findContents() throws Exception {
		if (this.hasFileName()) // el contenido esta en el file system
			return new String(JTools.readFileAsArrayByte(this.getAbsoluteFileName()));
		return JTools.HexStringToBin(pContenido.getValue(), pContenido.getValue().length());
	}

//	public void setReferenceOId(long zValue) throws Exception {
//		pReferenceOID.setValue(zValue);
//	}

	/**
	 * Constructor de la Clase
	 */
	public BizBiblioteca() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id", pId);
		this.addItem("company", pCompany);
		this.addItem("tipo", pTipo);
		this.addItem("contenido", pContenido);
//		this.addItem("reference", pReferenceOID);
		this.addItem("filename", pFileName);
		this.addItem("id_doc_padre", pIdDocPadre);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "Id", false, false, 18);
		this.addFixedItem(FIELD, "company", "Company", true, true, 15);
		this.addFixedItem(FIELD, "tipo", "Tipo", true, true, 50);
		this.addFixedItem(FIELD, "contenido", "Contenido", true, false, 10000);
//		this.addFixedItem(FIELD, "reference", "OID Refer", true, false, 18);
		this.addFixedItem(FIELD, "filename", "File Name", true, false, 100);
		this.addFixedItem(FIELD, "id_doc_padre", "Padre", true, false, 18);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "DOC_BIBLIOTECA";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void processInsert() throws Exception {		
		super.processInsert();
		setId(getIdentity("id"));

	}
	@Override
	public void processDelete() throws Exception {
//		JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
//		if (pReferenceOID.isNotNull()) {
//			String s_unlink = " select lo_unlink(" + pReferenceOID.getValue() + ")";
//			sentencia.ExecuteQuery(s_unlink);
//		}
		super.delete();
		JBaseFile.Delete(this.getAbsoluteFileName());
	}
	/**
	 * Default Read() methods
	 */
	public boolean read(long id) throws Exception {
		addFilter("id", id);
		return read();
	}

	public String getFileName(String tipo) throws Exception {
		return this.pCompany.getValue() + "/" + this.toString() + "." + tipo;
	}

	public String getFullFileName(String tipo) throws Exception {
		return JPath.PssPathTempFiles() + "/" + this.getFileName(tipo);
	}
	
//	public static String normalizeFilename(String file) throws Exception {
//		return file.replace("%3A", ":").replace("%20", " ");
//	}

	public String findFreeFile(String fileName) throws Exception {
		if (fileName == null || fileName.isEmpty()) JExcepcion.SendError("Error nombre archivo");
		fileName = JTools.scapeFileName(fileName);
		String name = JTools.cleanFileName(fileName);

		String path=this.getBibloPath();
		String subpath=this.getBibloSubPath();
		String unique = (new Date()).getTime()+"_";
		
		File file = new File(path+subpath+unique+name);
		while (file.exists()) {
			unique = (new Date()).getTime()+"_";
			file = new File(path+subpath+unique+name);
		}
		return subpath+unique+name;
	}	
	
	public void captureFile(String fileName) throws Exception {			
		String fileok=this.findFreeFile(fileName);
		JTools.copyFile(fileName, this.getBibloPath()+fileok);
		this.setFileName(fileok);
	}
	
	public void captureContent(String fileName) throws Exception {
		fileName=fileName.replaceAll(":", "_");
		String fileok=this.findFreeFile(fileName+"."+this.getTipo());
		this.pushContentToFile(fileok);
		this.setFileName(fileok); 
		this.pContenido.setNull();
	}

	public void updateContent() throws Exception {
		String fileok=this.getFileName();
		this.pushContentToFile(fileok);
		this.pContenido.setNull();
	}
		
	
//	public void assignContentByFilename(String fileName, String type) throws Exception {
//		this.setContents(new String(JTools.readFileAsArrayByte(fileName)));
//		this.setTipo(type);
//	}
	
	private void pushContentToFile(String name) throws Exception {
		String abs = this.getBibloPath()+name;
		File f = new File(abs);
		f.createNewFile();
		FileOutputStream write = new FileOutputStream(f, false);
		try {
			write.write(this.getContenido().getBytes());
		} finally {
			write.close();
		}
	}

//	public String getContentAsFilename(String nombreArch) throws Exception {
//		nombreArch=this.pCompany.getValue()+"/"+((nombreArch!=null)?nombreArch:this.hashCode());
//		String absfileName = JPath.PssPathTempFiles()+"/"+nombreArch;
//		String fileDir = absfileName.substring(0, absfileName.lastIndexOf("/"));
//		
//		File imageDir = new File(fileDir);
//		if (!imageDir.exists())
//			imageDir.mkdirs();
//		
//		
////		if (this.pReferenceOID.getValue() != 0) {
////			JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
////			String s_importar = " select lo_export(" + this.pReferenceOID.getValue() + ",'" + absfileName + "')";
////			sentencia.ExecuteQuery(s_importar);
////		} else {
//		File f = new File(absfileName);
//		f.createNewFile();
//		FileOutputStream write = new FileOutputStream(f, true);
//		try {
//			write.write(this.pContenido.getValue().getBytes());
//		} finally {
//			write.close();
//		}
//		
//		return nombreArch;
//	}
	
	public String getBibloPath() throws Exception {		
		String path = JPath.PssPathDataBiblo()+"/";
//		String dir = path +"/"+this.getCompany()+"/"+JDateTools.CurrentDate("yyyymm");
		File dir = new File(path);
		if (!dir.exists()) 
			dir.mkdirs();
		return path;
	}

	public String getBibloSubPath() throws Exception {		
		String path = this.getBibloPath();
		String subdir = this.getCompany()+"/"+JDateTools.CurrentDate("yyyyMM")+"/";
		File dir = new File(path+subdir);
		if (!dir.exists()) 
			dir.mkdirs();
		return subdir;
	}


//	public byte[] getContentAsByteArray() throws Exception {
//		String fileName = getFileName(this.pTipo.getValue());
//		String absfileName = getFullFileName(this.pTipo.getValue());
//		
//		int iAux = absfileName.lastIndexOf("/");
//		String fileDir = absfileName.substring(0, iAux);
//		
//		File imageDir = new File(fileDir);
//		if (imageDir.exists() == false)
//			imageDir.mkdirs();
//		
//		
//		if (this.pReferenceOID.getValue() != 0) {
//			JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
//			String s_importar = " select lo_export(" + this.pReferenceOID.getValue() + ",'" + absfileName + "')";
//			sentencia.ExecuteQuery(s_importar);
//			
//			byte[] a = JTools.readFileAsArrayByte(absfileName);
//			JTools.DeleteFile(absfileName);
//			return a;
//			
//		} else {
//			return getContents();
//		}
//	}
	
	public static String findType(String file) throws Exception {
		int lastPunto = file.lastIndexOf('.')+1;
		return file.substring(lastPunto);				
	}

	public String getUrl() throws Exception { 
		return "pss_data/biblo/"+this.getFileName();
	}
	

	public String getBibloFileName() throws Exception { 
		return "biblo/"+this.getFileName();
	}


}
