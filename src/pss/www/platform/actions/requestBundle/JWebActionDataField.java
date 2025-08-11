/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.requestBundle;

import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.processing.JXMLRepresentable;


/**
 * 
 * 
 * Created on 17-jun-2003
 * @author PSS
 */

public class JWebActionDataField implements JXMLRepresentable {

  public static final int OPERATOR_EQUAL            = 1;
  public static final int OPERATOR_LESSER           = 2;
  public static final int OPERATOR_GREATER          = 3;
  public static final int OPERATOR_LESSER_OR_EQUAL  = 4;
  public static final int OPERATOR_GREATER_OR_EQUAL = 5;
  public static final int OPERATOR_NOT_EQUAL        = 6;

  private static final String COMMA_ESCAPE = "/c/";
  private static final String EQUAL_ESCAPE = "/eq/";

  private String sName;
  private String sOperator;
  private String sValue;
  private boolean bEncrypted;

  JWebActionDataField(String zName, String zOperator, String zValue) {
    this(zName, zOperator, zValue, false);
  }
  JWebActionDataField(String zName, String zOperator, String zValue, boolean zEncrypted) {
    this.sName = zName;
    this.sOperator = zOperator;
    this.setValue(zValue);
    this.bEncrypted = zEncrypted;
  }

  public void destroy() {
    this.sName = null;
    this.sOperator = null;
    this.sValue = null;
  }

  
  public boolean isEncrypted() {
    return this.bEncrypted;
  }

  public void toXML(JXMLContent zContent) throws Exception {
    zContent.startNode("field");
    zContent.setAttribute("name", this.sName);
    if (this.sOperator != null && this.sOperator.trim().length() > 0) {
      zContent.setAttribute("op_code", getOperatorCode(this.sOperator.trim()));
    } else {
      zContent.setAttribute("op_code", OPERATOR_EQUAL);
    }
    if (this.bEncrypted) {
      zContent.setAttribute("encrypted", true);
    } else {
      zContent.setNodeText(this.getEncodedValue());
    }
    zContent.endNode("field");
  }

  public static int getOperatorCode(String zOp) {
    if (zOp.equals( "=" )) {
      return OPERATOR_EQUAL;
    } else if (zOp.equals( "<" )) {
      return OPERATOR_LESSER;
    } else if (zOp.equals( ">" )) {
      return OPERATOR_GREATER;
    } else if (zOp.equals( "<=" )) {
      return OPERATOR_LESSER_OR_EQUAL;
    } else if (zOp.equals( ">=" )) {
      return OPERATOR_GREATER_OR_EQUAL;
    } else if (zOp.equals( "<>" )) {
      return OPERATOR_NOT_EQUAL;
    } else {
      return OPERATOR_EQUAL;
    }
  }

  public static String getOperatorFromCode(int zOpCode) {
    switch (zOpCode) {
      case OPERATOR_EQUAL: return "=";
      case OPERATOR_LESSER: return "<";
      case OPERATOR_GREATER: return ">";
      case OPERATOR_LESSER_OR_EQUAL: return "<=";
      case OPERATOR_GREATER_OR_EQUAL: return ">=";
      case OPERATOR_NOT_EQUAL: return "<>";
      default: return "=";
    }
  }


  public String getName() {
    return this.sName;
  }
  
  public boolean hasValue() {
  	return !this.getValue().equals("");
  }


  public String getOperator() {
    return this.sOperator;
  }

  public String getEncodedValue() {
    if (this.sValue==null) {
      return "";
    }
    String sResult = this.sValue;
    if (sResult.indexOf(',') != -1) {
      sResult = sResult.replaceAll(",", COMMA_ESCAPE);
    }
    if (sResult.indexOf('=') != -1) {
      sResult = sResult.replaceAll("=", EQUAL_ESCAPE);
    }
    return sResult;
  }

  private void setValue(String zValue) {
    this.sValue = zValue;
    if (this.sValue==null) {
      return;
    }
    if (this.sValue.indexOf(COMMA_ESCAPE) != -1) {
      this.sValue = this.sValue.replaceAll(COMMA_ESCAPE, ",");
    }
    if (this.sValue.indexOf(EQUAL_ESCAPE) != -1) {
      this.sValue = this.sValue.replaceAll(EQUAL_ESCAPE, "=");
    }
  }


  public String getValue() {
    return this.sValue;
  }

}
