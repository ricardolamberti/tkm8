/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     sgalli@go-ssf.com - Santiago Galli - initial API and implementation
 *******************************************************************************/
package pss.core.tools;


/**
 * @author sgalli
 *
 */
public class InvalidDateException extends PssException {
  
    static final long serialVersionUID = 101010101012L;

    /**
     * @param e Throwable exception 
     */
    public InvalidDateException(Throwable e ) {
      super(e);
    }
    
    

}
