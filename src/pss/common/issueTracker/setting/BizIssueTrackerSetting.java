package pss.common.issueTracker.setting;

import pss.common.event.mail.BizMailCasilla;
import pss.common.issueTracker.issueHandlerUsers.BizIssueHandlerUser;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizIssueTrackerSetting extends JRecord {
	
	public static String STATUS_NEW = "NEW";
	public static String STATUS_MOREDATA = "MOREDATA";
	public static String STATUS_ARRIVED = "ARRIVED";
	public static String STATUS_ASSIGNED = "ASSIGNED";
	public static String STATUS_RESOLVED = "RESOLVED";
	public static String STATUS_CLOSED = "CLOSED";
	public static String STATUS_REOPENED = "REOPENED";
	 
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	private JString pCompany = new JString();
	private JString pHandlerUser = new JString();
	private JInteger pIdCasilla = new JInteger();
	private JBoolean pDefault = new JBoolean();
	private JString pCasillaDescr = new JString() {
		public void preset() throws Exception {
			pCasillaDescr.setValue( getObjMailCasilla().getMailFrom());
		}
	};
	
	public String getCompany() throws Exception { return pCompany.getValue();	}
	public String getHandlerUser() throws Exception {return pHandlerUser.getValue();}
	public int getIdCasilla() throws Exception{	return pIdCasilla.getValue();}
	public Boolean isDefault() throws Exception{	return pDefault.getValue();}
	
	
	public void setCompany(String value) throws Exception { this.pCompany.setValue(value);	}
	public void setHandlerUser(String value) throws Exception {	pHandlerUser.setValue(value);}
	public void setIdCasilla(int value) throws Exception{	this.pIdCasilla.setValue(value);}
	public void setDefault(Boolean value) throws Exception{	this.pDefault.setValue(value);}
	
	private static JMap<String,String> allStatus;
	private BizMailCasilla casilla;
	private BizIssueHandlerUser handle_user;		
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIssueTrackerSetting() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("handler_user", pHandlerUser);
		addItem( "id_casilla", pIdCasilla);
		addItem( "default_setting", pDefault);
		addItem( "casilla_descr", pCasillaDescr);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(FIELD, "handler_user", "Usuario Default", true, true, 100);
		addFixedItem(FIELD, "id_casilla", "Casilla", true, true, 18 );
		addFixedItem(FIELD, "default_setting", "Default", true, false, 1 );
		addFixedItem(VIRTUAL, "casilla_descr", "Casilla Mail", true, false, 200);		
	}

	@Override
	public String GetTable() {
		return "ISSUE_TRACK_SETTING";
	}

	public boolean Read(String company) throws Exception {
		addFilter("company", company);
		return this.read();
	}
	
	@Override
	public void processInsert()throws Exception{
		if (BizIssueTrackerSetting.getDefaultSetting()==null) {
			this.pDefault.setValue(true);
		}
		super.processInsert();
	}
	
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}

	@Override
	public void processDelete() throws Exception {
		super.processDelete();
	}

 
	public static String getStatusDescr(String aValue) throws Exception {
		return getAllStatus().getElement(aValue);
	}
	
  public static JMap<String, String> getAllStatus() throws Exception {
		if (allStatus!=null) return allStatus;
		JMap<String,String> elements= JCollectionFactory.createOrderedMap();
		elements.addElement(STATUS_NEW,"Nuevo");  	
		elements.addElement(STATUS_MOREDATA,"Mas Info");  	
		elements.addElement(STATUS_ARRIVED,"Recibido");  	
		elements.addElement(STATUS_ASSIGNED,"Asignado");  	
		elements.addElement(STATUS_RESOLVED,"Resuelto");  	
		elements.addElement(STATUS_CLOSED,"Cerrado");
		elements.addElement(STATUS_REOPENED,"Re-Abierto"); 
		
		return allStatus=elements;
  }
  
  public static boolean canResolver(String actual_status) throws Exception {
  	if (!BizIssueHandlerUser.isLoginedUserAHandlerUser()) return false;
  	return (actual_status.equals(STATUS_NEW)) ||
  	(actual_status.equals(STATUS_MOREDATA)) ||
  	(actual_status.equals(STATUS_ASSIGNED))||
  	(actual_status.equals(STATUS_ARRIVED))||
  	(actual_status.equals(STATUS_REOPENED)); 
  }

  public static boolean canAsignar(String actual_status) throws Exception {
  	if (!BizIssueHandlerUser.isLoginedUserAHandlerUser()) return false;
  	return (actual_status.equals(STATUS_NEW)) ||
  	(actual_status.equals(STATUS_MOREDATA)) ||
  	(actual_status.equals(STATUS_ASSIGNED))||
  	(actual_status.equals(STATUS_ARRIVED))||
  	(actual_status.equals(STATUS_REOPENED)); 
  }
  
  public static boolean canMoreData(String actual_status) throws Exception {
  	if (!BizIssueHandlerUser.isLoginedUserAHandlerUser()) return false;
  	return (actual_status.equals(STATUS_NEW)) ||
  	(actual_status.equals(STATUS_ASSIGNED))||
  	(actual_status.equals(STATUS_ARRIVED))||
  	(actual_status.equals(STATUS_REOPENED));
  }

  public static boolean canRecibir(String actual_status) throws Exception {
  	if (!BizIssueHandlerUser.isLoginedUserAHandlerUser()) return false;
  	return (actual_status.equals(STATUS_NEW)) ||
  	(actual_status.equals(STATUS_ASSIGNED)); 
  }

  public static boolean canReOpen(String actual_status) throws Exception {
  	if (BizIssueHandlerUser.isLoginedUserAHandlerUser()) return false;
  	return (actual_status.equals(STATUS_RESOLVED)); 
  }

  public static boolean canClose(String actual_status) throws Exception {
  	if (!BizIssueHandlerUser.isLoginedUserAHandlerUser()) return false;
  	return (!actual_status.equals(STATUS_CLOSED)); 
  }

  
  public BizMailCasilla getObjMailCasilla() throws Exception {
		if (this.casilla!=null) return this.casilla;
		BizMailCasilla t = new BizMailCasilla();
		t.read(this.pIdCasilla.getValue());
		return (this.casilla=t);
	}
	
	
	public void execDefault() throws Exception {
		JExec exec = new JExec() {
			public void Do() throws Exception {
				marcar_default();
			}
		};
		exec.execute();
	}
	
	public void marcar_default() throws Exception {
		BizIssueTrackerSetting def = BizIssueTrackerSetting.getDefaultSetting();
		if (def!=null) {
			def.setDefault(false);
			def.update();
		}
		BizIssueTrackerSetting p = (BizIssueTrackerSetting) this.getPreInstance();
		p.pDefault.setValue(true);
		p.update();
	}
	
	public static BizIssueTrackerSetting getDefaultSetting() throws Exception {
		JRecords<BizIssueTrackerSetting> recs = new JRecords<BizIssueTrackerSetting>(BizIssueTrackerSetting.class);
		recs.addFilter("default_setting", true);
		recs.toStatic();
		if (recs.sizeStaticElements()>0) {
			recs.firstRecord();
			recs.nextRecord();
			return recs.getRecord();
		}	
		return null;		
	}

	public static BizIssueTrackerSetting getCompanyOrDefaultSetting(String zCompany) throws Exception {
		JRecords<BizIssueTrackerSetting> recs = new JRecords<BizIssueTrackerSetting>(BizIssueTrackerSetting.class);
		recs.addFilter("company", zCompany);
		recs.toStatic();
		if (recs.sizeStaticElements()>0) {
			recs.firstRecord();
			recs.nextRecord();
			return recs.getRecord();
		}
		return BizIssueTrackerSetting.getDefaultSetting();
	}
	
	public BizIssueHandlerUser getObjHandleUser() throws Exception {
		if (this.handle_user!=null) return this.handle_user;
		BizIssueHandlerUser t = new BizIssueHandlerUser();
		t.Read(this.pHandlerUser.getValue());
		return (this.handle_user=t);
	}
	

}
