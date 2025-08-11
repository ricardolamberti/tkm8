package pss.core.services.fields;

import java.awt.Color;

public class JColour extends JString {

  public JColour() {
    super();
  }
  
	public String getObjectType() { return JObject.JCOLOUR; }

	public Color getColor() throws Exception {
		String s = getValue();
		if (s==null) return null;
		
		Color c = new Color(Integer.parseInt(s.substring(0,2),16),Integer.parseInt(s.substring(2,4),16),Integer.parseInt(s.substring(4,6),16));
		return c; 
		}

}
