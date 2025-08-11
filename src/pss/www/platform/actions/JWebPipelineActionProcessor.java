/*
 * Created on 27-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

import pss.www.ui.processing.JWebActionRequestProcessor;

/**
 * 
 * 
 * Created on 27-jun-2003
 * @author PSS
 */

public interface JWebPipelineActionProcessor extends JWebActionRequestProcessor {

  public void clearResultsMap();
}
