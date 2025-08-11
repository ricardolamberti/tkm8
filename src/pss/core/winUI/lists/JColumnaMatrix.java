package pss.core.winUI.lists;

import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;

public class JColumnaMatrix  {

	String clave;
	String campo;

	String titulo;
	Class clase;
	boolean eje;
	boolean xdif;
	
	public boolean isXdif() {
		return xdif;
	}
	public void setXdif(boolean xdif) {
		this.xdif = xdif;
	}
	double valor;
	
	String rankOrder;
	

	long cantidad;
	String function;
  String colorBackground;
  String colorForeground;
  String classResponsive;
  
  public String getClassResponsive() {
		return classResponsive;
	}
	public void setClassResponsive(String classResponsive) {
		this.classResponsive = classResponsive;
	}
	boolean isPorcentaje;
  
	public boolean isPorcentaje() {
		return isPorcentaje;
	}
	public void setPorcentaje(boolean isPorcentaje) {
		this.isPorcentaje = isPorcentaje;
	}
	public String getColorBackground() {
		return colorBackground;
	}
	public void setColorBackground(String colorBackground) {
		this.colorBackground = colorBackground;
	}
	public String getColorForeground() {
		return colorForeground;
	}
	public void setColorForeground(String colorForeground) {
		this.colorForeground = colorForeground;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public long getCantidad() {
		return cantidad;
	}
	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public boolean isEje() {
		return eje;
	}
	public void setEje(boolean eje) {
		this.eje = eje;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public Class getClase() {
		return clase;
	}
	public void setClase(Class clase) {
		this.clase = clase;
	}
	private int alignment=-1;
	JMap<Long,String> grupos;
	
	public JMap<Long, String> getGrupos() {
		if (grupos==null) return grupos=JCollectionFactory.createOrderedMap();
		return grupos;
	}
	public void setGrupos(JMap<Long, String> grupos) {
		this.grupos = grupos;
	}
	JMap<Long,JOrdenEjeMatrix> gruposEjes;
	
	public JMap<Long, JOrdenEjeMatrix> getGruposEjes() {
		if (gruposEjes==null) return gruposEjes=JCollectionFactory.createOrderedMap();
		return gruposEjes;
	}
	public void setGruposEjes(JMap<Long, JOrdenEjeMatrix> grupos) {
		this.gruposEjes = grupos;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getAlignment() {
		return alignment;
	}
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}
	public String getRankOrder() {
		return rankOrder;
	}		
	public void setRankOrder(JWin win,JColumnaLista dato,JDataMatrix data, double actualValue)  throws Exception {
		JIterator<JOrdenEjeMatrix> it = getGruposEjes().getValueIterator();
		while (it.hasMoreElements()) {
			JOrdenEjeMatrix eje = it.nextElement();

			if ( (eje.getEje().getRankCampo()!=null) && (eje.getEje().getRankCampo().equals(dato.GetCampo()))) {
				String orden = eje.getEje().getOneOrdenValue(new JString(JTools.formatDouble(eje.acumValueOrden(actualValue))),eje.getEje().getRankCampo(),eje.getEje().isAsc());
				eje.setOrder(  orden+eje.getEje().GetCampo());
			}
			else {
				String orden = eje.getEje().getOrdenValue(win);
				eje.setOrder(  orden+JTools.LPad(eje.getEje().GetCampo(),60,"0"));
			}
		}
		
		
	}
	public void setRankOrder(JWin win,JColumnaLista dato,JDataMatrix data, String actualValue)  throws Exception {
		JIterator<JOrdenEjeMatrix> it = getGruposEjes().getValueIterator();
		while (it.hasMoreElements()) {
			JOrdenEjeMatrix eje = it.nextElement();

			if ( (eje.getEje().getRankCampo()!=null) && (eje.getEje().getRankCampo().equals(dato.GetCampo()))) {
				String orden = eje.getEje().getOneOrdenValue(new JString(actualValue),eje.getEje().getRankCampo(),eje.getEje().isAsc());
				eje.setOrder(  orden+eje.getEje().GetCampo());
			}
			else {
				String orden = eje.getEje().getOrdenValue(win);
				eje.setOrder(  orden+JTools.LPad(eje.getEje().GetCampo(),60,"0"));
			}
		}
		
		
	}
	public String rebuildRankOrder()  throws Exception {
		String order = "";
		if (isXdif())
			order+="zzzzzzzzxdif";
		JIterator<JOrdenEjeMatrix> it = getGruposEjes().getValueIterator();
		while (it.hasMoreElements()) {
			JOrdenEjeMatrix eje = it.nextElement();
			order += eje.getOrder();
		}

		order+=getClave();
//		PssLogger.logDebug("ORDEN "+ order);
		this.rankOrder = order;
		return this.rankOrder;
	}
	public void setAlignmentFromType(String zType) {
		if (zType.equals(JObject.JSTRING)||zType.equals(JObject.JPASSWORD)||zType.equals("JSTRING")) {
			this.setAlignment(JColumnaLista.ALIGNMENT_LEFT);
		} else if (zType.equals(JObject.JINTEGER)||zType.equals(JObject.JFLOAT)||zType.equals(JObject.JLONG)||zType.equals(JObject.JCURRENCY)||zType.equals(JObject.JAUTONUMERICO)) {
			this.setAlignment(JColumnaLista.ALIGNMENT_RIGHT);
		} else {
			this.setAlignment(JColumnaLista.ALIGNMENT_CENTER);
		}
	}
	
}
