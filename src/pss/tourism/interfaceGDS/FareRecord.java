package pss.tourism.interfaceGDS;


import java.io.Serializable;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class FareRecord implements Serializable{

	public class ConnectionRecord implements Serializable{
		public String airport_to;
		public String carrier;
		public String airport_from;
	}	
	public class TaxRecord implements Serializable {
		public String connection;
		public String impuesto;
	}	
	
	public JList<ConnectionRecord> escalas;

	public long id;
	public String airport_to;
	public String airport_from;
	public JList<TaxRecord> impuestos;
	public String importe;
	public String ruta;
	public double impuesto;
	public String clase;
	public String tipoClase;
	public long prioridad;
	public boolean emitido=false;
		
	public boolean inEscala(String dst) {
		if (escalas==null) escalas=JCollectionFactory.createList();
		JIterator<ConnectionRecord> it = escalas.getIterator();
		while (it.hasMoreElements()) {
			ConnectionRecord conn = it.nextElement();
			if (conn.airport_to.equals(dst)) return true;
		}
		return false;
	}
	public void addEscala(String org, String carrier,String dst) {
		if (escalas==null) escalas=JCollectionFactory.createList();
		ConnectionRecord conn=new ConnectionRecord();
		conn.airport_from=org;
		conn.airport_to=dst;
		conn.carrier=carrier;
		escalas.addElement(conn);
	}
	public void addTax(String tipo,String val) {
		if (impuestos==null) impuestos=JCollectionFactory.createList();
		TaxRecord conn=new TaxRecord();
		conn.connection=tipo;
		conn.impuesto=val;
		impuestos.addElement(conn);
	}
}
