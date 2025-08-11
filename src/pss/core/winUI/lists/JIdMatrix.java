package pss.core.winUI.lists;

public class JIdMatrix implements Comparable<JIdMatrix>{

	
	
	boolean top;
	boolean bottom;
	
	Comparable criteria;
	int level; 

	boolean asc;
	
	String key;
	public JIdMatrix( String zkey) {
		top = false;
		bottom = false;
		key = zkey;
		level = -1;
		criteria = null;
		asc = true;
	}	
	public JIdMatrix(boolean ztop,boolean zbottom, String zkey, int zlevel, Comparable zcriteria, boolean zasc) {
		top = ztop;
		bottom = zbottom;
		key = zkey;
		criteria = zcriteria;
		asc = zasc;
		level = zlevel;
	}
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	public Comparable getCriteria() {
		return criteria;
	}

	public void setCriteria(Comparable criteria) {
		this.criteria = criteria;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	@Override
	public int compareTo(JIdMatrix o) {
		if (key!=null && o.key!=null && key.equals(o.key)) return 0;
		if (top && !o.top) return -1;
		if (bottom && !o.bottom) return 1;
		if (!top && o.top) return 1;
		if (!bottom && o.bottom) return -1;
		if (criteria==null && o.criteria==null) return 0;
		if (criteria!=null && o.criteria==null) return -1;
		if (criteria==null && o.criteria!=null) return 1;
		
		int res =  criteria.compareTo(o.criteria);
		return asc?res:res*-1;
	}
	
}
