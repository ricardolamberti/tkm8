package pss.core.services.fields;

public class JHtml extends JString {

  public JHtml() {
    super();
  }
  
  public JHtml( String zVal ) {
    setValue( zVal );
  }

  
	public String getObjectType() { return JObject.JHTML; }

  public boolean hasHtml() {
    return true;
  }
  
  

}
