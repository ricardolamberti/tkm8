package pss.core.tools;

public class JExceptionAndRefresh extends Exception {

// Genera que luego del error se refresque la pantalla, para usar reimplementar el nextaction(Exception) (no funciona para modales)
//		@Override
//		public JAct nextAction(Exception e) throws Exception {
//			// TODO Auto-generated method stub
//			return super.nextAction(new JExceptionAndRefresh(e));
//		}
	public JExceptionAndRefresh() {
	 
	}
	boolean info;
	public boolean isInfo() {
		return info;
	}
	public void setInfo(boolean info) {
		this.info = info;
	}
	Exception error;
	public Exception getError() {
		return error;
	}
	public void setError(Exception error) {
		this.error = error;
	}
	public JExceptionAndRefresh(Exception e) {
		 super(e.getMessage());
		 error=e;
	}
	public JExceptionAndRefresh(Exception e,boolean zinfo) {
		 super(e.getMessage());
		 error=e;
		 info=zinfo;
	}
}
