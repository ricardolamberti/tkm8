/**
 * 
 */
package pss.common.mercadopago;

import java.util.Date;

import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

import com.mercadopago.MP;

/**
 * @author sgalli
 * 
 */
public class Manager {

	static JMap<String, Date> mMsgs = JCollectionFactory.createMap();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Manager.process();
	}

	public static void process() {
		while (Thread.currentThread().isInterrupted() == false) {
			try {
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("mercadoPago", "process", true);
				JBDatos.GetBases().beginTransaction();
				Manager mgr = new Manager();
				mgr.start();
				JBDatos.GetBases().commit();
				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				PssLogger.logDebug("Interruption received");
				break;
			} catch (Exception e) {
				PssLogger.logError(e);
			}
		}
	}

	private void start() throws Exception {
		// TODO Auto-generated method stub
		MP mp = new MP("7842366619812611", "TmDTvUVC9E70JoVmGdfCoceLjfWgZm2m");

		String accessToken = mp.getAccessToken();

		System.out.println(accessToken);
	}

}
