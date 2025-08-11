package pss.common.customDashboard;

public class DashBoardInfo {
	
	private DashBoardModule module;
	private int id;
	private int action;
	private int size=2;
	private String descrip;
	private String actionDescrip;
	private int icon;	
	private String field;
	private String level;
	private boolean isImage;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setModule(DashBoardModule v) {
		this.module = v;
	}
	
	public DashBoardModule getObjModule() throws Exception {
		return this.module;
	}

	public String getModuleId() throws Exception {
		return this.module.getModuleId();
	}

	//	public String findKey() throws Exception {
//		return this.getModule()+this.getAction();
//	}


	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}

//	public DashBoardInfo( int action, String field, String descrip, String actionDescrip, String icon) {
//		setFields(  action,  field,  descrip,  actionDescrip,  icon,  "panel-primary",  2,  false) ;
//	}
	
//	public DashBoardInfo( int action, String field, String descrip, String actionDescrip, String icon, String level) {
//		setFields(  action,  field,  descrip,  actionDescrip,  icon,  level,  2,  false) ;
//	}
	
	public DashBoardInfo( int action, String field, String descrip, String actionDescrip, int icon, String level, int size, boolean isimage) {
		this.action = action;
		this.field = field;
		this.descrip = descrip;
		this.actionDescrip = actionDescrip;
		this.icon=icon;
		this.level = level	;
		this.isImage= isimage;
		this.size=size;
//		setFields(  action,  field,  descrip,  actionDescrip,  icon,  level,  size,  false) ;
	}
	
//	public DashBoardInfo( int action, String field, String descrip, String actionDescrip, String icon, String level, int size, boolean isImage) {
//		setFields(  action,  field,  descrip,  actionDescrip,  icon,  level,  size,  isImage) ;
//	}
	
	
//	public DashBoardInfo( int action, String field, String descrip, String actionDescrip,int size, boolean isImage) {
//		setFields(  action,  field,  descrip,  actionDescrip,  null,  null,  size,  isImage) ;
//	}
	
//	
//	private void setFields(int action, String field, String descrip, String actionDescrip, int icon, String level, int size, boolean isImage) {
//	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}


	
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getActionDescrip() {
		return actionDescrip;
	}
	public void setActionDescrip(String actionDescrip) {
		this.actionDescrip = actionDescrip;
	}

}
