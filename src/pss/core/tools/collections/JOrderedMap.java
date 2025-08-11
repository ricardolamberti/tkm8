package pss.core.tools.collections;

import java.io.Serializable;
import java.util.Comparator;


/**
 * A JMap which keeps the order in which the keys were added to it.
 * <p>
 *
 * @author  Berrio & Pronzolino
 * @see JMap interface for method documentation
 */

public interface JOrderedMap<K,E>  extends JMap<K,E>, Serializable  {

  public E getElementAt(int keyIndex);
  public E removeElementAt(int keyIndex);
	public void SortByValue(Comparator<K> vc);

}
