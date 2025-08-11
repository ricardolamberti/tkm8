package pss.core.tools.collections;

import java.util.Comparator;

public class JValueStringComparator<K> implements Comparator<K> {
	 
  JOrderedMap<K, String> map;

  public JValueStringComparator(JOrderedMap<K, String> base) {
      this.map = base;
  }

  public int compare(K a, K b) {
  		String val1 = map.getElement(a);
  		String val2 = map.getElement(b);
  	  if (val1==null) return 1;
  	  if (val2==null) return -1;
      return val1.compareTo(val2);
  }
}