/*
 * Created on 25-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.input;

import java.text.Format;

public class JWebStringInputFormat extends JWebInputFormat {

  

  JWebStringInputFormat(int zTotalChars, String zInputAttributes) {
    super(zInputAttributes, false);
    this.iMaxLength = zTotalChars;
  }


  @Override
	protected void resolveValidationAttributes(String zInputAttributes) throws Exception {
    if (zInputAttributes==null || zInputAttributes.trim().length() < 1) {
      this.sValidationPattern = "";
    } else {
      this.sValidationPattern = zInputAttributes;
    }
  }


  @Override
	protected Format resolveFormatter(String zJavaDatePattern) throws Exception {
    return null;
  }

}
