package pss.core.winUI.lists;

import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.common.customList.config.field.campo.BizCampo;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JProperty;
import pss.core.tools.JTools;
import pss.core.win.JWin;

public class JEjeMatrix extends Object {
  public static final int FILA = 1;
  public static final int COLUMNA = 2;

	private JProperty oFixedProp;
	private String sTitulo;
	private String sRankCampo;

  private BizCampo eje; // para alguna funciones especiales con customlist
  
//  protected SortedMap<Long,JOrdenMatrix> aOrdenMatrix;
//	public JOrdenMatrix AddOrdenMatrix(long id,JOrdenMatrix orden) {
//		GetOrdenMatrix().put(id, orden);
//		return orden;
//	}
//	
//	public SortedMap<Long,JOrdenMatrix> GetOrdenMatrix() {
//		if (aOrdenMatrix==null) aOrdenMatrix=new<Long,JOrdenMatrix> TreeMap();
//		return aOrdenMatrix;
//	}


	private int type;
	private int level;
	private long limite;
	private boolean asc;
	
	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	private boolean empty=false;
	
	public boolean isEmpty() {
		return empty;
	}

	public JEjeMatrix setEmpty(boolean empty) {
		this.empty = empty;
		return this;
	}

	private JDataMatrix oldFila=null;
	public String getRankCampo() {
		return sRankCampo;
	}

	public void setRankCampo(String sRankCampo) {
		this.sRankCampo = sRankCampo;
	}
	public JDataMatrix getOldFila() {
		return oldFila;
	}

	public void setOldFila(JDataMatrix oldFila) {
		this.oldFila = oldFila;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public long getLimite() {
		return limite;
	}

	public void setLimite(long limite) {
		this.limite = limite;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	public boolean isFila() {
		return type==FILA;
	}
	public boolean isColumna() {
		return type==COLUMNA;
	}

	private boolean translated;
	private JGrupoColumnaLista grupo;

	public JGrupoColumnaLista getGrupo() {
		return grupo;
	}

	public void setGrupo(JGrupoColumnaLista grupo) {
		this.grupo = grupo;
	}

	public void setFixedProp(JProperty zValue) {
		oFixedProp=zValue;
	}

	public void SetTitulo(String zValue) {
		sTitulo=zValue;
	}

	public JProperty getFixedProp() {
		return oFixedProp;
	}

	public String GetTitulo() throws Exception {
		return ((!sTitulo.equals("")) ? sTitulo : eje.getDescrCampo());// (oFixedProp==null) ? "" : oFixedProp.GetDescripcion());
	}

	public String GetCampo() {
		return (oFixedProp==null) ? "" : oFixedProp.GetCampo();
	}

	public String GetTipo() {
		return (oFixedProp==null) ? "" : oFixedProp.getType();
	}

	public BizCampo getEje() {
		return eje;
	}

	public void setEje(BizCampo eje) {
		this.eje = eje;
	}

	public JEjeMatrix() {
		sTitulo=null;

	}
	public String GetColumnaTitulo() {
		try {
			return JLanguage.translate(this.GetTitulo());
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isVirtual(JWin zWin) {
		if (oFixedProp==null) return true;
		return oFixedProp.isVirtual();
	}

	public long getValueRank(JWin win) throws Exception {
		return ((JLong)win.getRecord().getPropRaw("rank_"+getRankCampo())).getValue();
	}
	public JObject getValue(JWin win) throws Exception {
		if (isEmpty()) return new JString("");
  	return win.getRecord().getPropRaw(GetCampo());
	}
	
	public String getOrdenValue(JWin win) throws Exception {
		String finalOut="";
		if (isEmpty()) return "";
		String campo = getRankCampo()==null?GetCampo():getRankCampo();
		finalOut+=getOneOrdenValue( win.getRecord().getPropRaw(campo),campo, isAsc());
  	return finalOut;
	}
	
	
	public String getOneOrdenValue(JObject obj ,String ordenCampo, boolean asc) throws Exception {
		String out = "";
		if (obj.isDate() || obj.isDateTime()) {
			Date d = (Date)obj.getObjectValue();
			Calendar dt = Calendar.getInstance();
			dt.setTime(d);
			out = JTools.lpad(JTools.lpad(""+dt.get(Calendar.YEAR),"0",4)+JTools.lpad(""+dt.get(Calendar.MONTH),"0",2)+JTools.lpad(""+dt.get(Calendar.DAY_OF_MONTH),"0",2)+JTools.lpad(""+dt.get(Calendar.HOUR_OF_DAY),"0",2)+JTools.lpad(""+dt.get(Calendar.MINUTE),"0",2)+JTools.lpad(""+dt.get(Calendar.SECOND),"0",2)+JTools.lpad(""+dt.get(Calendar.MILLISECOND),"0",4),"0",60);

		} else if(obj.isCurrency() || obj.isFloat() || obj.isInteger() || obj.isLong() ) {
			out = JTools.lpad(obj.toFormattedString(), "0", 60);
		} else {
			out = JTools.lpad(obj.toString(), "0", 60);
		}
		if (!asc) 
			out = JTools.invert(JTools.replaceForeignChars(out));
		return  out;
	}
	public boolean isTranslated() {
		return translated;
	}

	public void setTranslated(boolean b) {
		translated=b;
	}


}
