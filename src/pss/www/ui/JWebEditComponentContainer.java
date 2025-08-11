/*
 * Created on 13-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.core.win.JWin;

/**
 * 
 * 
 * Created on 13-jun-2003
 * @author PSS
 */

public interface JWebEditComponentContainer {

  public String getFormName() throws Exception;
  public boolean isModoConsulta() throws Exception;
  public boolean isAlta() throws Exception;
  public boolean isInLine() throws Exception;
  public boolean isUploadData() throws Exception;
  public boolean isCard() throws Exception;
  public JWin getWin();

}
