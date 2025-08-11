package pss.core.services;



import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JConnectionBroken;
import pss.core.services.records.JRecord;
import pss.core.tools.CanceledByUserException;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public class JExec {

	boolean bMessage = true;
	JRecord oBD = null;
	String sMetodo = null;
	Exception eExcep = null;
	String sThreadName = "";
	boolean bBeginTrx = true;
	Object output;

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	public void SetMessage(boolean zValue) {
		bMessage = zValue;
	}

	public void SetThreadName(String zValue) {
		sThreadName = zValue;
	}

	public JExec() {
	}

	public JExec(JRecord zDato, String zMetodo) {
		oBD = zDato;
		sMetodo = zMetodo;
	}

	public void Do() throws Exception {
	}

//  //-------------------------------------------------------------------------//
//  // Ejecuto accion correspondiente al evento recibido
//  //-------------------------------------------------------------------------//
//  public void actionPerformed( ActionEvent zEvt ) {
//   try { execute(); } catch (Exception e) {UITools.MostrarError(e);}
//  }

	public void execute() throws Exception {
		try {
			if (JBDatos.isDatabaseOpen())
				JBDatos.GetBases().beginTransaction();
			if (oBD != null)
				oBD.doBeginTransaction();
			Do();

			if (oBD != null) {
				try {
					oBD.PropagarEjecucion(sMetodo, null);
				} catch (Exception E) {
				}
			}

			if (oBD != null)
				oBD.doCommitTransaction();
			if (JBDatos.isDatabaseOpen())
				JBDatos.GetBases().commit();

		} catch (Throwable e) {// e.getCause()
			if (oBD != null)
				oBD.doRollBackTransaction();

			boolean logExcep=false;
			if (e instanceof java.lang.reflect.InvocationTargetException) {
				if (e.getCause() instanceof Exception) {
					e = (Exception) e.getCause();
				}
			}

			if (!(e instanceof InterruptedException) && !(e instanceof JExcepcion) && !(e instanceof CanceledByUserException)) 
				logExcep=logError(e,logExcep);
			

			if (!(e instanceof JConnectionBroken)) {
				if (JBDatos.isDatabaseOpen())
					try {
						logExcep=logError(e,logExcep);
						JBDatos.GetBases().rollback();
					} catch (Exception r) {
						PssLogger.logError(r);
						if (e instanceof Exception)
							throw (Exception) e;
						JExcepcion.SendError(e.getMessage());
					}
			}

			if (e instanceof Exception)
				throw (Exception) e;
			JExcepcion.SendError(e.getMessage());
		}
	}
	public boolean logError(Throwable e, boolean logged) {
		if (logged) return true;
		if (JTools.isExceptionLog(e))
			PssLogger.logError(e);
		return true;
		
	}
	public void executeWithNewConnection() throws Exception {

		final BizUsuario oUsuario = BizUsuario.getUsr();
		final JBDatos oBDatos = JBDatos.GetBases();

		Thread oThread = new Thread() {
			@Override
			public synchronized void run() {
				try {
					eExcep = null;
					String sForzedDatabase = JAplicacion.GetApp().getForzedDatabase();

					String sId = JAplicacion.GetApp().GetAppId();
					JAplicacion.openSession();
					JAplicacion.GetApp().setForzedDatabase(sForzedDatabase);
					JAplicacion.GetApp().openApp(sId, JAplicacion.AppTipoThread(), true);
					BizUsuario.SetGlobal(oUsuario);
					JBDatos.GetBases().setNotifyEvents(oBDatos.isNotifyEvents());
					execute();
				} catch (Exception e) {
					eExcep = e;
				}
				try {
					JAplicacion.GetApp().closeApp();
				} catch (Exception ex) {
					PssLogger.logError("Error en Exec thread: " + ex.getMessage());
				}
				notify();
			}
		};
		synchronized (oThread) {
			if (!sThreadName.equals(""))
				oThread.setName(sThreadName);
			oThread.start();
			oThread.wait();
		}

		if (eExcep != null)
			throw eExcep;

	}

}
