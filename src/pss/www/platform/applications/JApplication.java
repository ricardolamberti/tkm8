/*
 * Created on 26-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.applications;

/**
 * 
 * 
 * Created on 26-jun-2003
 * @author PSS
 */

public interface JApplication {
  public abstract void startUp(String[] args) throws Throwable;
  public abstract JAbstractApplicationContext getApplicationContext();
  public abstract String getName();
  public abstract String getType();
}
