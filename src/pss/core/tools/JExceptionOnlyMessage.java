package pss.core.tools;

public class JExceptionOnlyMessage  extends Exception {


	public JExceptionOnlyMessage(JMessageInfo message) {
		super();
		setMsg(message);
	}
	public JExceptionOnlyMessage(String message) {
	 super(message);
	 setMsg(new JMessageInfo(message));
	}
	public JExceptionOnlyMessage(String message,boolean zinfo) {
		 super(message);
		 setMsg(new JMessageInfo(zinfo?JMessageInfo.TYPE_ERROR:JMessageInfo.TYPE_INFO,null,message));
	}
	public JExceptionOnlyMessage(Exception e) {
		 super(e.getMessage());
		 setMsg(new JMessageInfo(e.getMessage()));
		 error=e;
	}
	public JExceptionOnlyMessage(Exception e,boolean zinfo) {
		 super(e.getMessage());
		 setMsg(new JMessageInfo(zinfo?JMessageInfo.TYPE_ERROR:JMessageInfo.TYPE_INFO,null,e.getMessage()));
		 error=e;
	}
	JMessageInfo msg;
	public JMessageInfo getMsg() {
		return msg;
	}
	public void setMsg(JMessageInfo msg) {
		this.msg = msg;
	}
	Exception error;
	public Exception getError() {
		return error;
	}
	public void setError(Exception error) {
		this.error = error;
	}

}
