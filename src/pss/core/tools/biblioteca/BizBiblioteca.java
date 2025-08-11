package pss.core.tools.biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizBiblioteca extends JRecord {

	private JLong pId = new JLong();
	private JString pTipo = new JString();
	private JLOB pContenido = new JLOB();
	private JLong pReferenceOID = new JLong();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setId(long zValue) throws Exception {
		pId.setValue(zValue);
	}

	public long getId() throws Exception {
		return pId.getValue();
	}

	public void setTipo(String zValue) throws Exception {
		pTipo.setValue(zValue);
	}

	public String getTipo() throws Exception {
		return pTipo.getValue();
	}

	public void setContents(byte[] zValue) throws Exception {
		pContenido.setValue(JTools.rjlEncode(zValue));
	}
	
	public void setContentFromByteArray(String content,String type) throws Exception {
		this.pTipo.setValue(type);
		String absfileNameLocal = JPath.PssPathImportExportFilesLocal() + "/" + getFileName(this.pTipo.getValue());
		
		JTools.writeStringToFile(content, absfileNameLocal);
		
		setContentByFilename(absfileNameLocal,type);
		
		JTools.DeleteFile(absfileNameLocal);

		
	}
	
	public String getFullFileName(String tipo) throws Exception {
		return JPath.PssPathTempFiles() + "/" + getFileName(tipo);
	}

	public byte[] getContentAsByteArray() throws Exception {
		String fileName = getFileName(this.pTipo.getValue());
		String absfileName = getFullFileName(this.pTipo.getValue());
		
		int iAux = absfileName.lastIndexOf("/");
		String fileDir = absfileName.substring(0, iAux);
		
		File imageDir = new File(fileDir);
		if (imageDir.exists() == false)
			imageDir.mkdirs();
		
		
		if (this.pReferenceOID.getValue() != 0) {
			JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
			String s_importar = " select lo_export(" + this.pReferenceOID.getValue() + ",'" + absfileName + "')";
			sentencia.ExecuteQuery(s_importar);
			
			byte[] a = JTools.readFileAsArrayByte(absfileName);
			JTools.DeleteFile(absfileName);
			return a;
			
		} else {
			return getContents();
		}
	}

	public void setContentByFilename(String fileName, String type) throws Exception {
		JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
		String newFileName =fileName;
		if (fileName.startsWith("T:")) {
			File f = new File(fileName);
			newFileName = JPath.PssPathTempFiles()+"/"+f.getName();
			JTools.copyFile(fileName, newFileName);
		}
		// el problema es que si se accede, desde una conexion remota, no funciona.
		// if
		// (JBDatos.GetBases().GetBaseDefault().GetConnection().getMetaData().getURL().indexOf("127.")!=-1||
		// JBDatos.GetBases().GetBaseDefault().GetConnection().getMetaData().getURL().indexOf("local")!=-1)
		// {
		if (pReferenceOID.isNotNull()) {
			String s_unlink = " select lo_unlink(" + pReferenceOID.getValue() + ")";
			sentencia.ExecuteQuery(s_unlink);
		}
		String s_importar = " select lo_import('" + newFileName + "') as retoid";
		sentencia.ExecuteQuery(s_importar);
		sentencia.next();
		Long a = sentencia.CampoAsLong("retoid");
		this.setReferenceOId(a);
		// } else {
		// this.setReferenceOId(0);
		// this.setContents(JTools.readFileAsArrayByte(fileName));
		// }
		this.setTipo(type);
	}

	public String getFileName(String tipo) throws Exception {
		String cpy="";
		try {
			cpy = BizUsuario.getUsr().getCompany();
			
		} catch (Exception ee) {}
		if (cpy==null) cpy="";
		if (cpy.trim().equals("")==false)
			cpy += "/";
		return  cpy + this.toString() + "." + tipo;
	}


	public byte[] getContents() throws Exception { 
			return JTools.rjlDecode(pContenido.getValue());
	}

	public void setReferenceOId(long zValue) throws Exception {
		pReferenceOID.setValue(zValue);
	}

	/**
	 * Constructor de la Clase
	 */
	public BizBiblioteca() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id", pId);
		this.addItem("tipo", pTipo);
		this.addItem("contenido", pContenido);
		this.addItem("reference", pReferenceOID);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "Id", false, false, 18);
		this.addFixedItem(FIELD, "tipo", "Tipo", true, true, 50);
		this.addFixedItem(FIELD, "contenido", "Contenido", true, false, 4000);
		this.addFixedItem(FIELD, "reference", "OID Refer", true, false, 18);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "JUD_BIBLIOTECA";
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
		JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
		if (pReferenceOID.getValue()==0) {
			if (JTools.byteVectorToString(getContents()).startsWith("FILE:")) {
				String origen = JTools.byteVectorToString(getContents()).substring(5);
				JTools.DeleteFile(origen);
			}
		} else if (pReferenceOID.isNotNull()) {
			String s_unlink = " select lo_unlink(" + pReferenceOID.getValue() + ")";
			sentencia.ExecuteQuery(s_unlink);
		}
		super.processDelete();
	}
	/**
	 * Default Read() methods
	 */
	public boolean read(long id) throws Exception {
		addFilter("id", id);
		return read();
	}
	public String getFileName(String company,String titulo,String tipo) throws Exception {
		if (titulo==null) return getFileName(company,tipo);
		return company + "/" + JTools.getValidFilename(titulo) + "." + tipo;
	}

	public String getFileName(String company,String tipo) throws Exception {
		return company + "/" + this.hashCode() + "." + tipo;
	}



	public void setContentByFilename(String company,String fileName, String type) throws Exception {
		JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
		String newFileName =fileName;
		String newRemoteFileName =fileName;

		if (BizPssConfig.getPssConfig().isUseImportExportFile()) {
			File f = new File(fileName);
			String directory = JPath.PssPathDataFiles()+"/db/"+f.getName().substring(0,1);
			String zDestiny = directory+"/"+f.getName();
			JTools.MakeDirectory(directory);
			JTools.MoveFile(fileName, zDestiny);
			this.setReferenceOId(0);
			this.setContents(("FILE:"+zDestiny).getBytes());
		} else if (BizPssConfig.getPssConfig().isUseImportExportDB()) {
			File f = new File(fileName);
			JTools.MakeDirectory(JPath.PssPathImportExportFilesLocal() + "/" +company);
			newFileName = JPath.PssPathImportExportFilesLocal() + "/" +company+"/"+ f.getName();
			newRemoteFileName = JPath.PssPathImportExportFilesRemote() + "/" +company+"/"+ f.getName();
			if (JPath.PssImportExportCopy()) {
				JTools.copyFile(fileName, newFileName);
			}
			if (pReferenceOID.isNotNull()) {
				String s_unlink = " select lo_unlink(" + pReferenceOID.getValue() + ")";
				sentencia.ExecuteQuery(s_unlink);
			}	
			String s_importar = " select lo_import('" + newRemoteFileName + "') as retoid";
			sentencia.ExecuteQuery(s_importar);
			sentencia.next();
			Long a = sentencia.CampoAsLong("retoid");
			this.setReferenceOId(a);
		} else {
			this.setReferenceOId(0);
			if (JTools.readSizeFile(fileName)<1000000)
				this.setContents(JTools.readFileAsArrayByte(fileName));
			else 
				this.setContents("Archivo muy grande, no se almacena".getBytes());
		}
		this.setTipo(type);
	}
	public String getContentAsFilename(String company) throws Exception {
		return getContentAsFilename(company,null);
	}
	public String getContentAsFilename(String company,String titulo) throws Exception {
		String name = titulo;
		if (name==null) name="rep_"+this.hashCode();
		String fileName = getFileName(company,titulo,this.pTipo.getValue());
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" +company);
		
		
		if (this.pReferenceOID.getValue() != 0) {
			String absfileNameRemote = JPath.PssPathImportExportFilesRemote() + "/" + getFileName(company,name,this.pTipo.getValue());
			String absfileNameLocal = JPath.PssPathImportExportFilesLocal() + "/" + getFileName(company,name,this.pTipo.getValue());
			
			int iAux = absfileNameLocal.lastIndexOf("/");
			String fileDir = absfileNameLocal.substring(0, iAux);
		
			
			File imageDir = new File(fileDir);
			if (imageDir.exists() == false)
				imageDir.mkdirs();

			JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
			String s_importar = " select lo_export(" + this.pReferenceOID.getValue() + ",'" + absfileNameRemote + "')";
			sentencia.ExecuteQuery(s_importar);
			
			File f = new File(absfileNameLocal);
			JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" +company);
			String newFileName = JPath.PssPathTempFiles() + "/" +fileName;
			if (JPath.PssImportExportCopy()) {
				JTools.copyFile(absfileNameLocal, newFileName);
			}

		} else {
		
			if (JTools.byteVectorToString(getContents()).startsWith("FILE:")) {
				String absfileName = JPath.PssPathTempFiles() + "/" + getFileName(company,titulo,this.pTipo.getValue());
				String origen = JTools.byteVectorToString(getContents()).substring(5);
				JTools.DeleteFile(absfileName);
				JTools.copyFile(origen, absfileName);
			} else {
				String absfileName = JPath.PssPathTempFiles() + "/" + getFileName(company,titulo,this.pTipo.getValue());
				
				int iAux = absfileName.lastIndexOf("/");
				String fileDir = absfileName.substring(0, iAux);
				File imageDir = new File(fileDir);
				if (imageDir.exists() == false)
					imageDir.mkdirs();
				File f = new File(absfileName);
				f.createNewFile();
				FileOutputStream write = new FileOutputStream(f, true);
				try {
					write.write(getContents());
				} finally {
					write.close();
				}
			}
		}
		return fileName;
	}

	public void setContentFromByteArray(String company, String content,String type) throws Exception {
		String absfileNameLocal = JPath.PssPathImportExportFilesLocal() + "/" + getFileName(company,this.pTipo.getValue());
		
		JTools.writeStringToFile(content, absfileNameLocal);
		
		setContentByFilename(company,absfileNameLocal,type);
		JTools.DeleteFile(absfileNameLocal);
			
	}
	public byte[] getContentAsByteArray(String company) throws Exception {
		if (this.pReferenceOID.getValue() != 0) {
			String absfileNameRemote = JPath.PssPathImportExportFilesRemote() + "/" + getFileName(company,this.pTipo.getValue());
			String absfileNameLocal = JPath.PssPathImportExportFilesLocal() + "/" + getFileName(company,this.pTipo.getValue());

			int iAux = absfileNameLocal.lastIndexOf("/");
			String fileDir = absfileNameLocal.substring(0, iAux);
			
			File imageDir = new File(fileDir);
			if (imageDir.exists() == false)
				imageDir.mkdirs();

			JBaseRegistro sentencia = JBaseRegistro.recordsetFactory();
			String s_importar = " select lo_export(" + this.pReferenceOID.getValue() + ",'" + absfileNameRemote + "')";
			sentencia.ExecuteQuery(s_importar);
			
			byte[] a = JTools.readFileAsArrayByte(absfileNameLocal);
			JTools.DeleteFile(absfileNameLocal);
			return a;
			
		} else {
		
			if (JTools.byteVectorToString(getContents()).startsWith("FILE:")) {
				return  JTools.readFileAsArrayByte(JTools.byteVectorToString(getContents()).substring(5));
			} else {
				return getContents();

			}
		}
	}

}
