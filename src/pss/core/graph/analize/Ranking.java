package pss.core.graph.analize;

// estructura libre, que se usa para solucionar diferentes problematicas de pasar el data set a los datos del grafico
public 	class Ranking {
	public double orden;



	public String cadena;
	public String color;
	public String link;


	public long code;




	public double dato1;
	public double dato2;
	public double dato3;
	public double dato4;
	public double dato5;
	
	public String dato6;
	public String dato7;
	public String dato8;
	
	public Ranking(double o,String c) {
		orden=o;
		cadena=c;
	}
	public Ranking(double o,String c,String col) {
		orden=o;
		cadena=c;
		color=col;
	}
	public Ranking(String c,String col) {
		cadena=c;
		color=col;
	}

	public Ranking(double o,String c,String col,String l) {
		orden=o;
		cadena=c;
		color=col;
		link=l;
	}
	public Ranking(String c,String col,String l) {
		cadena=c;
		color=col;
		link=l;
	}
	public double getDato1() {
		return dato1;
	}
	public void setDato1(double dato1) {
		this.dato1 = dato1;
	}
	public double getDato2() {
		return dato2;
	}
	public void setDato2(double dato2) {
		this.dato2 = dato2;
	}
	public double getDato3() {
		return dato3;
	}
	public void setDato3(double dato3) {
		this.dato3 = dato3;
	}
	public double getDato4() {
		return dato4;
	}
	public void setDato4(double dato4) {
		this.dato4 = dato4;
	}
	public double getDato5() {
		return dato5;
	}
	public void setDato5(double dato5) {
		this.dato5 = dato5;
	}

	
	public String getDato6() {
		return dato6;
	}
	public void setDato6(String dato6) {
		this.dato6 = dato6;
	}
	public String getDato7() {
		return dato7;
	}
	public void setDato7(String dato7) {
		this.dato7 = dato7;
	}
	public String getDato8() {
		return dato8;
	}
	public void setDato8(String dato8) {
		this.dato8 = dato8;
	}


	
	public double getOrden() {
		return orden;
	}
	public void setOrden(double orden) {
		this.orden = orden;
	}


	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	
	@Override
	public String toString() {
		return orden+":"+cadena;
	}
}
