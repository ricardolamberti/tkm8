package pss.core.services.fields;

/**
 * Description: Se utiliza como propiedad en el BD contraparte del control JFormMultiple,
 * una lista de selección múltiple.
 *
 *
 */

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JStringTokenizer;

public class JMultiple extends JObject<JList<String>> {

  public JMultiple() {
  }

  @SuppressWarnings("unchecked")
	public JList<String> getValue() throws Exception {
    if( getObjectValue() == null ) {
      return JCollectionFactory.<String>createList();
    }

    return (JList<String>)getObjectValue() ;
  }

  public void addValue( String zValue ) throws Exception {
    if ( this.isNull() ) setValue(JCollectionFactory.<String>createList());
    getValue().addElement( zValue );
  }


  public void setValue( JList<String> zVal ) {
    super.setValue(zVal);
  }

  // Agrega un valor a la colección!
  @Override
	public void setValue( String zVal ) throws Exception {
//    if (JAplicacion.GetApp().ifAppFrontEndWeb()) {
//      addValue(zVal);
//    } else {
  	setEstablecida(true);
  	this.getValue().removeAllElements();
    if (zVal==null) return;
    JStringTokenizer oTokenizer = JCollectionFactory.createStringTokenizer(zVal, ',');
    while (oTokenizer.hasMoreTokens()) {
      addValue(oTokenizer.nextToken().trim());
    }
//    }
  }

	public String toRawString() throws Exception {
		if (!this.hasValue()) return "";
		return asPrintealbleObject();
	}
	
  public void removeValue( String zValue ) throws Exception {
    if ( this.isNotNull() ) getValue().removeElement( zValue );
  }

  public void removeAllValues() throws Exception {
    if ( this.isNotNull() ) getValue().removeAllElements();
  }

  public JIterator<String> getIterator() throws Exception {
    return getValue().getIterator();
  }

  public boolean hasValues() throws Exception {
    return !getValue().isEmpty();
  }

  public int size() throws Exception { return getValue().size(); }

  @Override
	public String getObjectType() { return JObject.JMULTIPLE; }

	public String asPrintealbleObject() throws Exception {
		String s = "";
		JIterator<String> it = getValue().getIterator();
		while (it.hasMoreElements()) {
			s+=(s.equals("")?"":",")+it.nextElement();
		}
		return s;
	}
}
