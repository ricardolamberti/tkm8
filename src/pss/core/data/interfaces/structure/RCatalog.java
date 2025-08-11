package pss.core.data.interfaces.structure;

import java.io.Serializable;

public class RCatalog implements Serializable {

	String name;

	public RCatalog(String name) {
		super();
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

}
