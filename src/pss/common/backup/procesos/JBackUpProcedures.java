package  pss.common.backup.procesos;

import java.util.Date;
import pss.JPath;
import pss.common.backup.settings.BizBackUpGroup;
import pss.common.backup.settings.BizBackUpGroupDetail;
import pss.common.backup.settings.BizBackUpGroupDetailCriterio;
import pss.common.backup.settings.BizMainSetting;
import pss.core.data.files.JStreamGZip;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;


public class JBackUpProcedures {
	
	private  final static String DEFAULT_PATH="\\BackUp" ;

	private String pCompany="";
	private Date pFdesde=null;
	private Date pFhasta=null;
	
	
	private BizMainSetting oBackUpSetting=null;
	private JRecords<BizBackUpGroup> oBackGroups=null;
	
	
  /**
   * Constructor de la Clase
   */
  public JBackUpProcedures() throws Exception {
  }

  public JBackUpProcedures(BizMainSetting zValue) throws Exception {
  	oBackUpSetting = zValue;
  	this.pCompany = oBackUpSetting.getCompany();
  	this.pFdesde = oBackUpSetting.getLastrun();
  	if (oBackUpSetting.getNow() != null)
  		this.pFhasta = oBackUpSetting.getNow();
  	else
  		this.pFhasta = JDateTools.StringToDateTime(JDateTools.CurrentDateTime());
  }

  public void setCompany(String zValue) {
  	this.pCompany = zValue;
  }
  
  public void setFdesde(Date zValue) {
  	this.pFdesde = zValue;
  }
  
  public void setFhasta(Date zValue) {
  	this.pFhasta = zValue;
  }
  
	private JRecords<BizBackUpGroup> getBackGroups() throws Exception{
		if (this.oBackGroups != null)
			return this.oBackGroups;
		this.oBackGroups = new JRecords<BizBackUpGroup>(BizBackUpGroup.class);
		return this.oBackGroups;
	}	

	public void BackUp()  throws Exception {
	
		try {
			this.getBackGroups().addFilter("company", oBackUpSetting.getCompany());
			this.getBackGroups().addOrderBy("grupo");
			this.getBackGroups().readAll();
			this.getBackGroups().toStatic();
			
			if (this.getBackGroups().sizeStaticElements() > 0) {
				while (this.getBackGroups().nextRecord()) { 
					// llenar todos los objetos para todos los procesos.
					// luego se insertarán todos en la nueva conexión
					BizBackUpGroup oBackGroup = this.getBackGroups().getRecord();
					JRecords<BizBackUpGroupDetail> oBackGroupDetails = new JRecords<BizBackUpGroupDetail>(BizBackUpGroupDetail.class);
					oBackGroupDetails.addFilter("company", oBackGroup.getCompany());
					oBackGroupDetails.addFilter("grupo", oBackGroup.getGrupo());
					oBackGroupDetails.readAll();
					while (oBackGroupDetails.nextRecord()) { 
						BizBackUpGroupDetail oBackGroupDet = oBackGroupDetails.getRecord();
						this.BackUpTableData(oBackGroupDet);
						PssLogger.logInfo("Se realizó BackUp de la tabla " + oBackGroupDet.getTabla());
					}
				} // end while
				String filenameToSend = "";
				filenameToSend = this.zipBackUp();
				if (filenameToSend != "") 
					this.sendMail(filenameToSend);
			}
			oBackUpSetting.Update();
		} catch (Exception e) {
				throw e;
		}
	}
	
	private void BackUpTableData(BizBackUpGroupDetail oGroupDetail) throws Exception {
		boolean firstTime = true;
		String txtFieldNames = "";
		String aSql = "Select " + oGroupDetail.getTabla() + ".* from " + oGroupDetail.getTabla();
		if (oGroupDetail.isDetail()) {
			if (oGroupDetail.getHeaderTable() != "") {
				aSql += " " + this.getJoinCriteria(oGroupDetail.getCompany(), oGroupDetail.getGrupo(), oGroupDetail.getTabla(), oGroupDetail.getHeaderTable());
			}
		}
		aSql += " " + this.getWhereCriteria(oGroupDetail.getCompany(), oGroupDetail.getGrupo(), oGroupDetail.getTabla(), false) ;
		
		
		JBackUpWriteFile oGenerator;
		String sPath = this.determineFullPath(oBackUpSetting.getOutputPath());
		oGenerator = new JBackUpWriteFile( sPath );
		oGenerator.createFile(oGroupDetail.getCompany() + "_" + oGroupDetail.getTabla() + JDateTools.CurrentDate( "yyyyMMdd" ) +JDateTools.CurrentTime( "HHmmss" ));
		
		JBaseRegistro records = JBaseRegistro.recordsetFactory() ;
		records.ExecuteQuery(aSql);
		//records.first();
		
		while (records.next()) {
			String txtLine = "";
			JIterator<String> a = records.getFieldNameIterator();
			while (a.hasMoreElements()) {
				String colName = a.nextElement();
				if (firstTime) 
					txtFieldNames += colName + "|" ;
				txtLine += records.CampoAsStr(colName) + "|" ; 
			}
			if (firstTime) {
				firstTime = false;
				if (oGenerator.isNewFile()) 
					oGenerator.write(txtFieldNames);
			}
	
			oGenerator.write(txtLine);
		}
		oGenerator.close();
	}
	
	private String getWhereCriteria(String sCompany, String sGrupo, String sTabla, boolean bNoWhere) throws Exception {
		String tWhere = "";
		Boolean firsttime = true;
		JRecords<BizBackUpGroupDetailCriterio> oBackGroupDetailsCriterios = new JRecords<BizBackUpGroupDetailCriterio>(BizBackUpGroupDetailCriterio.class);
		oBackGroupDetailsCriterios.addFilter("company", sCompany);
		oBackGroupDetailsCriterios.addFilter("grupo", sGrupo);
		oBackGroupDetailsCriterios.addFilter("tabla", sTabla);
		oBackGroupDetailsCriterios.addFilter("type_of_field", BizBackUpGroupDetailCriterio.TYPE_RELATION, "<>");
		oBackGroupDetailsCriterios.readAll();
		
		if (bNoWhere)
			firsttime = false;
		
		while (oBackGroupDetailsCriterios.nextRecord()) {
			if (firsttime) {
				tWhere = " Where " ;
				firsttime = false;
			} else {
				tWhere += " AND " ;
			}
			
			BizBackUpGroupDetailCriterio oBackGroupDetCriterio = oBackGroupDetailsCriterios.getRecord();
			tWhere +=  " " + oBackGroupDetCriterio.getFieldName1();
			tWhere +=  " " + oBackGroupDetCriterio.getOperator1();
			tWhere +=  " '" + getCriteriaValue(oBackGroupDetCriterio.getTypeOffield(),oBackGroupDetCriterio.getValue1()) + "'";
			
		}
		return tWhere;
	}
	
	private String getJoinCriteria(String sCompany, String sGrupo, String sTabla, String sHeaderTable) throws Exception {
		String sJoin = "";
		Boolean firsttime = true;
		JRecords<BizBackUpGroupDetailCriterio> oBackGroupDetailsCriterios = new JRecords<BizBackUpGroupDetailCriterio>(BizBackUpGroupDetailCriterio.class);
		oBackGroupDetailsCriterios.addFilter("company",sCompany);
		oBackGroupDetailsCriterios.addFilter("grupo", sGrupo);
		oBackGroupDetailsCriterios.addFilter("tabla", sTabla);
		oBackGroupDetailsCriterios.addFilter("type_of_field", BizBackUpGroupDetailCriterio.TYPE_RELATION);
		oBackGroupDetailsCriterios.readAll();
		
		while (oBackGroupDetailsCriterios.nextRecord()) {
			if (firsttime) {
				sJoin = " INNER JOIN " + sHeaderTable + " ON " ;
				firsttime = false;
			} else {
				sJoin += " AND " ;
			}
			
			BizBackUpGroupDetailCriterio oBackGroupDetCriterio = oBackGroupDetailsCriterios.getRecord();
			sJoin +=  " " + sTabla  + "." + oBackGroupDetCriterio.getFieldName1();
			sJoin +=  " " + oBackGroupDetCriterio.getOperator1();
			sJoin +=  " " + sHeaderTable  + "." + oBackGroupDetCriterio.getValue1() ;
			
			// Traigo el resto del join con los criterios de selección de la tabla padre
			sJoin += " " + this.getWhereCriteria(sCompany, sGrupo, sHeaderTable, true);
		}
		return sJoin;
	}
	
	private String getCriteriaValue(String typeOfCriteria, String zValue) {
		String rValue = null;
		if (typeOfCriteria.equals(BizBackUpGroupDetailCriterio.TYPE_COMPANY)) {
			rValue = this.pCompany ;
		} else if (typeOfCriteria.equals(BizBackUpGroupDetailCriterio.TYPE_FDESDE)) {	
			rValue = JDateTools.DateToString(this.pFdesde, "dd-MM-yyyy HH:mm:ss");
		} else if (typeOfCriteria.equals(BizBackUpGroupDetailCriterio.TYPE_FHASTA)) {	
			rValue = JDateTools.DateToString(this.pFhasta, "dd-MM-yyyy HH:mm:ss");
		} else if (typeOfCriteria.equals(BizBackUpGroupDetailCriterio.TYPE_MANUAL)) {	
			rValue = zValue;
		}
		return rValue;
	}
	
	 private String determineFullPath(String zPath) {
		 if (zPath == "")
			   zPath = this.DEFAULT_PATH;
	    String sPath = zPath.replace('.','/').replace('\\', '/');
	    if (sPath.startsWith("/")) {
	      sPath = sPath.substring(1);
	    }
	    if (sPath.startsWith("/")) {
	      sPath = sPath.substring(1);
	    }
	    if (sPath.endsWith("/")) {
	      sPath = sPath.substring(0, sPath.length()-1);
	    }
	    String psssrc = JPath.PssPath()+"/../..";
	    
	    return  psssrc + "/" + sPath;
	  }
	 
		public String zipBackUp() throws Exception {
			String returnFile = "";
      String fileName = "ZIPOutput" + JDateTools.CurrentDate( "yyyyMMdd" ) +JDateTools.CurrentTime( "HHmmss" )+".zip";
      String sDirectoryInput = this.determineFullPath(oBackUpSetting.getOutputPath());
      String sDirectoryFile =  sDirectoryInput +  "/" + JDateTools.CurrentDate( "yyyyMM" );
      JTools.MakeDirectory(sDirectoryFile);
      boolean bProcesoOk = JStreamGZip.zipFileForDirectory( sDirectoryInput , sDirectoryInput, sDirectoryFile , fileName, false,".backup");
      if (bProcesoOk) {
      		PssLogger.logInfo( "PROCESO DE ZIPEO FINALIZADO CORRECTAMENTE ");
      		returnFile = sDirectoryInput + "/" +  fileName ;
      }
      
      return returnFile;
    }
	
		private void sendMail(String filename) throws Exception {
			
			  // Acá registro el evento. Ahora tengo que ver cómo paso el String del archivo
//			BizEvent.registerEvent(oBackUpSetting.getCompany(), "1", Long.parseLong(JBackUpEventOperations.BACKUP_END) , "BackUp","BackUp Realizado",filename);

			/*BizMailSender ms = new BizMailSender();
			ms.dontThrowException(true);
			if ( ms.read((int)1) ) {
				ms.sendFile("BackUp","BackUp",filename);
			}*/
		}
}
