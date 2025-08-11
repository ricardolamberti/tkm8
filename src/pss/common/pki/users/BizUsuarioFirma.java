package  pss.common.pki.users;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import pss.JPath;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizUsuarioFirma extends JRecord {
  public static final String VIGENTE="VIGENTE";
  public static final String VENCIDO="VENCIDO";

	public BizUsuarioFirma() throws Exception {
		super();
	}

	JString pUsuario = new JString();
	JInteger pId = new JInteger();
	JDate pFechaAlta = new JDate();
	JDate pFechaBaja = new JDate();
	JString pEstado = new JString();
	JString pFilename = new JString();
	JLOB pFirma = new JLOB();


	@Override
	public void createProperties() throws Exception {
		this.addItem("usuario", pUsuario);
		this.addItem("id", pId);
		this.addItem("fecha_alta", pFechaAlta);
		this.addItem("fecha_baja", pFechaBaja);
		this.addItem("estado", pEstado);
		this.addItem("filename", pFilename);
		this.addItem("firma", pFirma);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "usuario", "Usuario", true, true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH);
		this.addFixedItem(FIELD, "id", "Id", true, true, 5);
		this.addFixedItem(FIELD, "fecha_alta", "Fecha alta", true, false, 50);
		this.addFixedItem(FIELD, "fecha_baja", "Fecha baja", true, false, 50);
		this.addFixedItem(FIELD, "estado", "Estado", true, true, 50);
		this.addFixedItem(FIELD, "firma", "Firma", true, false, 4000);
		this.addFixedItem(VIRTUAL, "filename", "Filename", true, false, 2000);
	}
	
	public boolean isVigente() throws Exception {
		return pEstado.equals(VIGENTE);
	}

	@Override
	public String GetTable() {
		return "SEG_USUARIO_FIRMA";
	}
		
	public static BizUsuarioFirma readVigente(String zUsuario) throws Exception {
		BizUsuarioFirma f = new BizUsuarioFirma();
		f.addFilter("usuario", zUsuario);
		f.addFilter("estado", VIGENTE);
		f.dontThrowException(true);
		if (!f.read()) return null;
		return f;
	}

	public boolean Read(String zUsuario,long id) throws Exception {
		this.clearFilters();
		this.addFilter("usuario", zUsuario);
		this.addFilter("id", id);
		return read();
	}

	@Override
	public void processInsert() throws Exception {
		if (this.pId.isNull()) {
			BizUsuarioFirma max = new BizUsuarioFirma();
			max.addFilter("usuario", this.pUsuario.getValue());
			this.pId.setValue(max.SelectMaxInt("id")+1);
		}
  	String fileorigen = pFilename.getValue().replace("%3A", ":");
  	fileorigen = fileorigen.replace("%20", " ");
	
  	this.pFirma.setValue(JTools.rjlEncode(JTools.readFileAsArrayByte(fileorigen)));

  	pEstado.setValue(VIGENTE);
		pFechaAlta.setValue(new Date());
		super.processInsert();
	}
	
  public String getFileName() throws Exception {
  	return BizUsuario.getUsr().getCompany()+"/"+this.toString() + ".SGN";
  }
  
  public  String getFullFileName() throws Exception {
  	return JPath.PssPathTempFiles() + "/" +getFileName();
  }
  
	public static String obtainSignFile(String usuario) throws Exception {
		BizUsuarioFirma uf = BizUsuarioFirma.readVigente(usuario);
		if (uf==null) return null;
		String file = uf.getFullFileName();
		uf.obtainSignToFile(file);
		return file;
	}
	
	public void obtainSignToFile(String filename) throws Exception {
			if (!isVigente()) throw new Exception("Firma NO VIGENTE!");
		  File f= new File(filename);
	  	f.createNewFile();
			FileOutputStream write = new FileOutputStream(f,true);
 			write.write(JTools.rjlDecode(pFirma.getValue()));
			write.close();
	}
	
	@Override
	public void processDelete() throws Exception {
		pEstado.setValue(VENCIDO);
		pFechaBaja.setValue(new Date());
		super.processUpdate();
	}

	public boolean hasFirma() throws Exception {
		return !this.pFilename.isEmpty();
	}
	
}
