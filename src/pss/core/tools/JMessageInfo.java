package pss.core.tools;

import java.io.Serializable;

public class JMessageInfo implements Serializable {
	public static final String TYPE_ERROR = "err";
	public static final String TYPE_INFO = "inf";
	String message;
	String type=TYPE_INFO;
	String title;
	public JMessageInfo( String ztype, String ztitle, String zmessage) {
		title=ztitle;
		type=ztype;
		message=zmessage;
	}
	public JMessageInfo( String ztype, String zmessage) {
		type=ztype;
		message=zmessage;
	}
	public JMessageInfo(  String zmessage) {
		type=TYPE_INFO;
		message=zmessage;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
