package pss.www.ui.views;

import pss.www.platform.actions.resolvers.JAbstractLoginResolver;
import pss.www.platform.actions.results.JWebActionResult;

/**
 * El generator genera la vista a mostrar, esta viaja al usuario y luego vuelve el resultado de la accion
 * Es comodo en algunos casos acercar lo que se hace con los resultados a la clase generadora.
 * Los generadores que quieran recibirlo deben implementar esta interfaz y ser llamados de sus resolvers
 * OJO! no es el mismo objeto, por lo que los datos hay que buscarlos en el request!
 *
 */
public interface IActionPerform {
	public JWebActionResult perform(JAbstractLoginResolver loginResolver) throws Throwable ;
}
