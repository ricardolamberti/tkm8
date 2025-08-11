package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.subscribeMdlConfigMngr;

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
public class ReqSubscribeMdlConfigMngr extends ReqMessage {
	/**
   * 
   */
	private static final long serialVersionUID = -1125064223764629881L;

	private String company = null;
	private String country = null;
	private String node = null;	
	private String moduleId = null;
	private String configType = null;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

}
