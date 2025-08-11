package pss.core.connectivity.messageManager.common.server.confMngr.requests.subscribeConfigUnit;

import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.common.message.ReqMessage;
import pss.core.connectivity.messageManager.server.confMngr.IMMConfigurationSubscriptor;

/**
 * <p>
 * Mensaje para anotarse como <b>unidad de configuracion</b> dependiente de otros modulos. Una <b>unidad de
 * configuracion</b> es un modulo que depende de otros modulos para poder configurarse. Por ejemplo: Los impuestos de un
 * producto depende de la existencia del producto. Se inscribe el impuesto como unidad de configuracion dependiendo de
 * los productos. Cuando un producto se crea se le avisa al modulo de impuestos (declarado en la unidad de
 * configuracion) para que este agregue los datos de configuracion propios.
 * </p>
 * <p>
 * Para subscribirse como unidad de configuracion se tiene que enviar en este mensaje el nombre de la clase que
 * implementa la interfaz {@link IMMConfigurationSubscriptor} y que representa un subscriptor a configurarse dependiendo
 * de otros modulos. Para setear el nombre de la clase hay que utilizar el metodo
 * {@link #setSubscriberClassName(String) setSubscriberClassName}. Tambien hay que setear en nombre de la unidad de
 * configuracion utilizando el metodo {@link #setConfigurationUnitName(String) setConfigurationUnitName}
 * </p>
 * <p>
 * Adicionalmente hay que enviar la lista de nombre de las classes a los que la unidad de configuracion esta interesado
 * en subscribirse, para agregar informacion de configuracion. Para agregar una clase que se esta interesado en
 * controlar hay que utilizar el metodo {@link #setSubscriptionsList(Queue) setSubscriptionsList}
 * </p>
 * <p>
 * Para ver un ejemplo de como utilizar este mensaje: {@code Queue<String> col = new LinkedList<String>();
 * col.add("BizXXX"); ReqSubscribeConfigUnit req = new ReqSubscribeConfigUnit();
 * req.setConfigurationUnitName(getConfigurationUnitName()); req.setSubscriberClassName( this.getClass().getName() );
 * req.setSubscriptionsList(col); sendMessage(req); * }
 * </p>
 * 
 * <b>Respuesta:</b> Se recibe un {@link MsgResponse} con el resultado del procesamiento, indicando el resultado del
 * mismo. No se recibe informacion adicional.
 * 
 * @author Pentaware S.A.
 */
public class ReqSubscribeConfigUnit extends ReqMessage {
	/**
   * 
   */
	private static final long serialVersionUID = 370042927253215037L;
	/**
	 * Nombre que identifica a la unidad de configuracion.
	 */
	private String configurationUnitName = null;
	/**
	 * Nombre de la clase que implementa la unidad de configuracion
	 */
	private String subscriberClassName = null;

	/**
	 * Lista de componentes que se desea controlar
	 */
	private Queue<String> subscriptionsList = null;

	public void setSubscriptionsList(Queue<String> subscriptionsList) {
		this.subscriptionsList = subscriptionsList;
	}

	/**
	 * Retorna la lista los componentes que fueron configurados para ser controlados por la unidad de configuracion
	 * especificada
	 * 
	 * @return Lista de nombres de los componentes
	 */
	public Queue<String> getSubscriptionsList() {
		if (subscriptionsList == null) {
			subscriptionsList = new LinkedList<String>();
		}
		return subscriptionsList;
	}

	/**
	 * Retorna Nombre de la clase que implementa la unidad de configuracion
	 * 
	 * @return Nombre de la clase
	 */
	public String getSubscriberClassName() {
		return subscriberClassName;
	}

	/**
	 * Setea el nombre de la clase que implementa la unidad de configuracion. Por ejemplo
	 * "pss.core.connectivity.messageManager.zTest.clienteTCP1.GuiMMClienteTCP1"
	 * 
	 * @param subscriberClassName
	 *          Nombre de la clase
	 */
	public void setSubscriberClassName(String subscriberClassName) {
		this.subscriberClassName = subscriberClassName;
	}

	/**
	 * Retorna el nombre que identifica a la unidad de configuracion
	 * 
	 * @return Nombre de la unidad de configuracion
	 */
	public String getConfigurationUnitName() {
		return configurationUnitName;
	}

	/**
	 * Setea el nombre de la unidad de configuracion
	 * 
	 * @param configurationUnitName
	 */
	public void setConfigurationUnitName(String configurationUnitName) {
		this.configurationUnitName = configurationUnitName;
	}

	/**
	 * Agrega un nuevo nombre de clase a la que se esta subscribiendo.
	 * 
	 * @param zSubscriptionId
	 */
	public void addToSubscriptionsList(String zSubscriptionId) {
		this.subscriptionsList = this.getSubscriptionsList();
		this.subscriptionsList.add(zSubscriptionId);
	}
}
