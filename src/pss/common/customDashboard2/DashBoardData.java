package pss.common.customDashboard2;

import pss.common.regions.multilanguage.JLanguage;

public class DashBoardData {
	
	private int id;
	private int action;
	private int size=2;
	private String descrip;
	private String actionDescrip;
	private String icon;	
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

	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}

	public DashBoardData( int action, String field, String descrip, String actionDescrip, String icon) {
		setFields(  action,  field,  descrip,  actionDescrip,  icon,  "panel-primary",  2,  false) ;
	}
	
	public DashBoardData( int action, String field, String descrip, String actionDescrip, String icon, String level) {
		setFields(  action,  field,  descrip,  actionDescrip,  icon,  level,  2,  false) ;
	}
	
	public DashBoardData( int action, String field, String descrip, String actionDescrip, String icon, String level, int size) {
		setFields(  action,  field,  descrip,  actionDescrip,  icon,  level,  size,  false) ;
	}
	
	public DashBoardData( int action, String field, String descrip, String actionDescrip, String icon, String level, int size, boolean isImage) {
		setFields(  action,  field,  descrip,  actionDescrip,  icon,  level,  size,  isImage) ;
	}
	
	
	public DashBoardData( int action, String field, String descrip, String actionDescrip, boolean isImage) {
		setFields(  action,  field,  descrip,  actionDescrip,  null,  null,  12,  isImage) ;
	}
	
	
	private void setFields(int action, String field, String descrip, String actionDescrip, String icon, String level, int size, boolean isImage) {
		this.action = action;
		this.field = field;
		this.descrip =  JLanguage.translate(descrip);
		this.actionDescrip = JLanguage.translate(actionDescrip);
		this.level = level	;
		this.isImage=isImage;
		this.size=size;
		this.icon = icon;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}


	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
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
