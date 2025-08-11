package pss.bsp.consolid.model.liquidacion.terrestres;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;

import pss.JPath;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.parseo.IParseo;
import pss.bsp.parseo.JParseoFactory;
import pss.common.security.BizUsuario;
import pss.core.data.files.JStreamGZip;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public class BizLiqTerrestre extends JRecord {

	
		public BizLiqTerrestre() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}


		private JString pCompany = new JString();
		private JLong pLiquidacionId = new JLong();
		private JString pFileName = new JString();
		private JBoolean pClean = new JBoolean();

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Getter & Setters methods
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		public void setCompany(String zValue) throws Exception { pCompany.setValue(zValue); }
		public String getCompany() throws Exception { return pCompany.getValue(); }

		public void setLiquidacionId(long zValue) throws Exception { pLiquidacionId.setValue(zValue); }
		public long getLiquidacionId() throws Exception { return pLiquidacionId.getValue(); }

		public void setClean(boolean zValue) throws Exception { pClean.setValue(zValue); }
		public boolean getClean() throws Exception { return pClean.getValue(); }

	

		
		public void createProperties() throws Exception {
		    this.addItem("company", pCompany);
		    this.addItem("liquidacion_id", pLiquidacionId);
		     this.addItem("filename", pFileName);
		     this.addItem("clean", pClean);
		 		    
		}

		/**
		 * Adds the fixed object properties
		 */
		public void createFixedProperties() throws Exception {
	    this.addFixedItem(FIELD, "company", "Company", true, true, 18);
	    this.addFixedItem(FIELD, "liquidacion_id", "Liquidacion ID", true, true, 18);
	    this.addFixedItem(FIELD, "filename", "Archivo", true, false, 2000);
	    this.addFixedItem(FIELD, "clean", "Limpiar", true, false,1);
		   
	}
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "linea",  zId ); 
    return read(); 
  } 
  
  @Override
  public void processInsert() throws Exception {
  	if (getClean())
  		procLimpiar();
  	
  	procFile();
  }
  static long id=0;
  public void procFile() throws Exception {
  	String filename=pFileName.getValue();
		if (filename.equals("")) throw new Exception("No se especifico archivo");
		filename=URLDecoder.decode(filename);
		id++;
		if (filename.toLowerCase().endsWith(".zip")) {
				JTools.DeleteFiles(JPath.PssPathInputZIP()+"/unzipped"+(id));
				JStreamGZip.unzipFileForDirectory(filename, null, JPath.PssPathInputZIP()+"/unzipped"+(id));
				File oFile=new File( JPath.PssPathInputZIP()+"/unzipped"+(id));
				generarDirTerrestre(oFile);
				JTools.DeleteFiles(JPath.PssPathInputZIP()+"/unzipped"+(id));
				oFile.delete();
		} else {
			generarTerrestre(filename);
		}
  }
	public void generarDirTerrestre(File dir) throws Exception {
		File[] oFiles=dir.listFiles();
		if (oFiles != null) {
			for (int lIndex = 0; lIndex < oFiles.length; lIndex++) {
				if (oFiles[lIndex].isDirectory()) {
					generarDirTerrestre(oFiles[lIndex]);
					continue;
				}
				if (oFiles[lIndex].isFile()) {
					generarTerrestre(oFiles[lIndex].getAbsolutePath());
				}
			}
		}
	}
	public void generarTerrestre(String filename) throws Exception {
		PssLogger.logDebug("Procesando: "+filename);
		IParseo parseo=tryParser(filename);
		
	
	  
	}
	public void procLimpiar() throws Exception {
		BizLiqDetail cons = new BizLiqDetail();
		cons.addFilter("company", getCompany());
		cons.addFilter("liquidacion_id", getLiquidacionId());
		cons.addFilter("origen", "XSLX");
		cons.deleteMultiple();
		
	}
	
	
	public IParseo tryParser(String filename) throws Exception {
		String idParser="PARSEO_TERRESTRE";
		IParseo[] parseos=JParseoFactory.getInstance(idParser,"XLSX");
		IParseo lastParser=null;
		for (IParseo parseo:parseos) {
			try {
				lastParser=parseo;
				parseo.setCompany(getCompany());
				parseo.setId(""+getLiquidacionId());
				FileInputStream file=new FileInputStream(filename);
				try {
					parseo.setFilename(filename);
					parseo.setInput(file);
					parseo.execute();
				} finally {
					file.close();
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lastParser;
	}

}
