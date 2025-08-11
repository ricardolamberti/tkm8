package pss.core.services.searchEngine;

/**
 * 
 * manipulacion de un catalogo, sistema de busqueda de la base de datos en jerga SQLServer
 * 
 */
public class JCatolog {

	String name;

	public JCatolog(String name) {
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
