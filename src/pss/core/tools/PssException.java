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
 * Default Exception class 
 * 
 * @author sgalli
 *
 */
public class PssException extends Exception {
  
    static final long serialVersionUID = 101010101016L;
    private String code        = "99";
    private String description = "System Error";

    
    private void setData( String zCode, String zDescription ) {
      this.code        = zCode;
      this.description = zDescription; 
    }
    
    /**
     * @param zCode Error Code
     * @param zDescription Error Description
     * @param e Error Exception
     */
    public PssException( String zCode, String zDescription, Throwable e ) {
      super(e);
      setData(zCode,zDescription);
    }
    
    /**
     * @param zCode Error Code
     * @param zDescription Error Description
     */
    public PssException(String zCode, String zDescription ) {
      super();
      setData(zCode,zDescription);
    }
    
    /**
     * @param e Error Throwable Exception 
     */
    public PssException(Throwable e ) {
      super(e);
    }
    
    /**
     * Constructor
     */
    public PssException() {
      super();
    }
    
    /**
     * @param zErrorMessage Error Description
     */
    public PssException(String zErrorMessage) {
      super(zErrorMessage);
      this.description = zErrorMessage;
    }

    /**
     * @return Error Code
     */
    public String getCode() {
      return this.code;
    }

    /**
     * @param code Error Code
     */
    public void setCode(String code) {
      this.code = code;
    }

    /**
     * @return Error Description
     */
    public String getDescription() {
      return this.description;
    }

    /**
     * @param description Error Description
     */
    public void setDescription(String description) {
      this.description = description;
    }

}
