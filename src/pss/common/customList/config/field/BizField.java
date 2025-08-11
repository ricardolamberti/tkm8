package pss.common.customList.config.field;

import java.net.URLDecoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharEncoding;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.customList.config.relation.JRelation;
import pss.common.regions.company.JCompanyBusiness;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.sentences.JRegSQL;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public abstract class BizField extends JRecord {
	
	public BizField() throws Exception {
		super();
	}

	public static final String FUNTION_FORMULA = "FORMULA";
	public static final String FUNTION_COUNT = "COUNT";
	public static final String FUNTION_SUM = "SUM";
	public static final String FUNTION_AVR = "AVR";
	public static final String FUNTION_MES = "MES";
	public static final String FUNTION_ANIO = "ANIO";
	public static final String FUNTION_MAX = "MAX";
	public static final String FUNTION_MIN = "MIN";
	public static final String FUNTION_LIT = "LIT";
	public static final String FUNTION_NULO = "NULO";
	public static final String FUNTION_NONULO = "NONULO";
	public static final String FUNTION_ANOMES = "ANIOMES";
	public static final String FUNTION_ANOSEM = "ANIOSEM";
	public static final String FUNTION_AYER = "AYER";
	public static final String FUNTION_BIMESTRE = "BIMESTRE";
	public static final String FUNTION_TRIMESTRE = "TRIMESTRE";
	public static final String FUNTION_CUATRIMESTRE = "CUATRIMESTRE";
	public static final String FUNTION_SEMESTRE = "SEMESTRE";
	public static final String FUNTION_DIA_SEMANA = "DIA_SEMANA";
	public static final String FUNTION_DIA_MES = "DIA_MES";
	public static final String FUNTION_DIA_ANO = "DIA_ANO";
	public static final String FUNTION_DEA1000 = "DEA1000";
	public static final String FUNTION_DEA10000 = "DEA10000";
	public static final String FUNTION_DEA100000 = "DEA100000";
	public static final String FUNTION_DEA1000000 = "DEA1000000";
	public static final String FUNTION_DEA10000000 = "DEA1000000";

	public static final String FORMAT_STR = "STR";
	public static final String FORMAT_DATE = "DATE";
	public static final String FORMAT_NUM = "NUM";

	public abstract String getFuncion() throws Exception;

	public abstract boolean hasFunction() throws Exception;
	public abstract boolean hasFunctionFormula() throws Exception;
	public abstract String getCampo() throws Exception;

	public abstract String getFormatParam() throws Exception;

	public abstract String getField() throws Exception;

	public abstract String getNameField() throws Exception;

	public abstract String getNameField(String field) throws Exception;

	public abstract String getNameFieldFormula(String field) throws Exception;

	public abstract boolean hasCampo() throws Exception;

	public abstract boolean isPorcentaje() throws Exception;
	public abstract boolean isOver() throws Exception;

	public abstract JRecord getObjRecordTarget() throws Exception;
	public abstract JRelation getObjRelation() throws Exception;
	public abstract String getRecordOwner() throws Exception;	
	public abstract JRelation getRelationGeoOwner() throws Exception;	

	public abstract String getDescrCampo() throws Exception;
	public abstract String getCompany() throws Exception;
	public abstract long getListId() throws Exception;

	public abstract String getOperador() throws Exception;
  public abstract boolean hasOperador() throws Exception ;
  public abstract String getValor() throws Exception;
  public abstract String getValor2() throws Exception;
  public abstract boolean hasValor2() throws Exception;
  public abstract String getDescrTrue() throws Exception;
  public abstract String getDescrFalse() throws Exception;
	public abstract JList<String> getAmbitoPorcentaje() throws Exception;
	public abstract String getCampoKey() throws Exception;
	public abstract String getCampoKey2() throws Exception;
	private BizCustomList customList;
	public BizCustomList getObjCustomList() throws Exception {
		if (this.customList != null) return this.customList;
		BizCustomList r = new BizCustomList();
		r.dontThrowException(true);
		if (!r.read(getCompany(), getListId())) {
				return null;
		}
		return (this.customList = r);
	}
	public void setObjCustomList(BizCustomList zCustomList) throws Exception {
		customList=zCustomList;
	}
	public void clean() throws Exception {
		customList=null;
	}
	public JProperty createFixedProp() throws Exception {
		JProperty p = null;
		if (this.isPorcentaje())
			p = this.getPorcentajeProperty();
		else if (this.getFuncion().equals(FUNTION_AYER))
			p = this.getAyerProperty();
		else if (this.getFuncion().equals(FUNTION_MES))
			p = this.getMesProperty();
		else if (this.getFuncion().equals(FUNTION_ANIO))
			p = this.getAnioProperty();
		else if (this.getFuncion().equals(FUNTION_ANOMES))
			p = this.getAnoMesProperty();
		else if (this.getFuncion().equals(FUNTION_ANOSEM))
			p = this.getAnoSemProperty();
		else if (this.getFuncion().equals(FUNTION_BIMESTRE))
			p = this.getBimestreProperty();
		else if (this.getFuncion().equals(FUNTION_TRIMESTRE))
			p = this.getTrimestreProperty();
		else if (this.getFuncion().equals(FUNTION_CUATRIMESTRE))
			p = this.getCuatrimestreProperty();
		else if (this.getFuncion().equals(FUNTION_SEMESTRE))
			p = this.getSemestreProperty();
		else if (this.getFuncion().equals(FUNTION_DIA_SEMANA))
			p = this.getDiaSemanaProperty();
		else if (this.getFuncion().equals(FUNTION_DIA_MES))
			p = this.getDiaMesProperty();
		else if (this.getFuncion().equals(FUNTION_DIA_ANO))
			p = this.getDiaAnoProperty();
		else if (this.getFuncion().equals(FUNTION_LIT))
			p = this.getLitProperty();
		else if (this.getFuncion().equals(FUNTION_FORMULA)||this.getCampo().equals(FUNTION_FORMULA))
			p = this.getFunctionProperty();
		else if (this.isCampoCantidad())
			p = this.getCantidadProperty();
		else if (!this.hasCampo())
			p = this.getCantidadProperty();
		else {
			JRecord r = this.getObjRecordTarget();
			if (r==null) return null;
			p = r.getFixedProp(this.getCampo());
		}
		JProperty p1 = new JProperty(p.getType(), this.getNameField(), p.GetDescripcion(), null, "", true, true, p.getSize(), p.GetPrecision(), null, null, null);
		return p1;
	}

//	public JProperty createGeoFixedProp() throws Exception {
//		JProperty p1 = new JProperty(FIELD, this.getObjRecordTarget().getRelationMap().getGeoField(getCampo()), "geo posicion", null, "", true, true, 50, 0, null, null, null);
//		return p1;
//	}
	
	public boolean isCampoCantidad() throws Exception {
		return this.getCampo().equals(BizField.FUNTION_COUNT)||this.getFuncion().equals(BizField.FUNTION_COUNT);
	}
	public boolean isCampoFormula() throws Exception {
		return this.getCampo().equals(BizField.FUNTION_FORMULA)||this.getFuncion().equals(BizField.FUNTION_FORMULA);
	}
	public boolean isCampoFormulaConFuncion() throws Exception {
		boolean formula = this.getCampo().equals(BizField.FUNTION_FORMULA)||this.getFuncion().equals(BizField.FUNTION_FORMULA);
		if (!formula) return false;
		if (this.hasFunctionFormula()) return true;
		return (this.getFormatParam().toUpperCase().indexOf("SUM(")!=-1 || 
				this.getFormatParam().toUpperCase().indexOf("AVG(")!=-1 ||
				this.getFormatParam().toUpperCase().indexOf("MAX(")!=-1 ||
				this.getFormatParam().toUpperCase().indexOf("MIN(")!=-1 ||
				this.getFormatParam().toUpperCase().indexOf("OVER(")!=-1 
				) ;
	}

	public JObject<?> createProp() throws Exception {
		if (isPorcentaje())
			return new JFloat().setPrec(2);
		if (this.getFuncion().equals(FUNTION_MES))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_ANIO))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_ANOMES))
			return new JString();
		if (this.getFuncion().equals(FUNTION_ANOSEM))
			return new JString();
		if (this.getFuncion().equals(FUNTION_AYER))
			return new JDate();
		if (this.getFuncion().equals(FUNTION_BIMESTRE))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_TRIMESTRE))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_CUATRIMESTRE))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_SEMESTRE))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_DIA_SEMANA))
			return new JString();
		if (this.getFuncion().equals(FUNTION_DIA_MES))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_DIA_ANO))
			return new JLong();
		if (this.getFuncion().equals(FUNTION_LIT))
			return new JString();
		if (this.getFuncion().equals(FUNTION_FORMULA)||this.getCampo().equals(BizCampo.FUNTION_FORMULA))
			return new JString();
		if (this.isCampoCantidad())
			return new JLong();
		if (!this.hasCampo())
			return new JFloat().setPrec(2);
		return this.getObjRecordTarget().getProp(this.getCampo());
	}

	public String getObjectType() throws Exception {
		if (this.getObjRecordTarget()==null) return "";
		if (this.getFuncion()!=null && this.getFuncion().equals("COUNT")) return JObject.JFLOAT;
		if (this.getCampo()==null) return "";
		if (this.getCampo().equals("")) return "";
		JObject object=this.getObjRecordTarget().getProp(this.getCampo(),false);
		if (object==null) return null;
		return object.getObjectType();
	}
	public String getObjectTypeInput() throws Exception {
		if (this.getObjRecordTarget()==null) return "";
		if (this.getFuncion()!=null && this.getFuncion().equals("COUNT")) return JObject.JFLOAT;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_ANIO)) return JObject.JLONG;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_MES)) return JObject.JLONG;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_ANOMES)) return JObject.JLONG;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_ANOSEM)) return JObject.JLONG;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_BIMESTRE)) return JObject.JLONG;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_TRIMESTRE)) return JObject.JLONG;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_CUATRIMESTRE)) return JObject.JLONG;
		if (this.getFuncion()!=null && this.getFuncion().equals(BizCampo.FUNTION_SEMESTRE)) return JObject.JLONG;
		if (this.getCampo()==null) return "";
		if (this.getCampo().equals("")) return "";
		JObject object=this.getObjRecordTarget().getProp(this.getCampo(),false);
		if (object==null) return null;
		return object.getObjectType();
	}


	public boolean functionReguireGroupBy() throws Exception {
		if (!hasFunction())
			return true;
		if (this.getFuncion().equals(FUNTION_LIT))
			return true;
		if (this.getFuncion().equals(FUNTION_MES))
			return true;
		if (this.getFuncion().equals(FUNTION_ANIO))
			return true;
		if (this.getFuncion().equals(FUNTION_ANOMES))
			return true;		
		if (this.getFuncion().equals(FUNTION_ANOSEM))
				return true;
		if (this.getFuncion().equals(FUNTION_BIMESTRE))
			return true;
		if (this.getFuncion().equals(FUNTION_TRIMESTRE))
			return true;
		if (this.getFuncion().equals(FUNTION_CUATRIMESTRE))
			return true;
		if (this.getFuncion().equals(FUNTION_SEMESTRE))
			return true;
		if (this.getFuncion().equals(FUNTION_DIA_SEMANA))
			return true;
		if (this.getFuncion().equals(FUNTION_DIA_MES))
			return true;
		if (this.getFuncion().equals(FUNTION_DIA_ANO))
			return true;
		if (this.getFuncion().equals(FUNTION_FORMULA)||this.getCampo().equals(BizField.FUNTION_FORMULA))
			return true;
		return false;
	}

	public static JMap<String, String> getFormatMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(FORMAT_STR, "Substring (desde,hasta)");
		map.addElement(FORMAT_DATE, "Formato Fecha (DMY)");
		map.addElement(FORMAT_NUM, "Formato Número (#0)");
		return map;
	}

	public String findFunction(boolean select) throws Exception {
		return	findFunction(select,null,false);
	}

  public String getValorDefault(String campo,Date hoy) throws Exception {
	  if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_MES)) return JDateTools.DateToString(((JDateTools.getFirstDayOfMonth(hoy))));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_MES)) return JDateTools.DateToString((JDateTools.getLastDayOfMonth(hoy)));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_ANO)) return JDateTools.DateToString((JDateTools.getFirstDayOfYear(hoy)));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_ANO)) return JDateTools.DateToString((JDateTools.getLastDayOfYear(hoy)));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_ACTUAL)) return JDateTools.DateToString(hoy);
	  return "";
  }
  public Date getValorDefaultDate(String campo,Date hoy) throws Exception {
	  if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_MES)) return (((JDateTools.getFirstDayOfMonth(hoy))));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_MES)) return ((JDateTools.getLastDayOfMonth(hoy)));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_ANO)) return ((JDateTools.getFirstDayOfYear(hoy)));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_ANO)) return ((JDateTools.getLastDayOfYear(hoy)));
	  else if (campo.equalsIgnoreCase(JCompanyBusiness.FECHA_ACTUAL)) return (hoy);
	  return new Date();
  }
	public String findFunction(boolean select,String sqlTotalize,boolean totalizador) throws Exception {
		if (getObjCustomList()==null) return "";
		JBaseRegistro bd = JBaseRegistro.recordsetFactory();
		String variable="";
		String function = getFuncion();
		String campo = "";
		String tipo="";
		String value =getValor();
		String value2 =getValor2();
		Date hoy = getObjCustomList().getHoy();
		if (value.equals("")) value = getValorDefault(getCampoKey(), hoy);
		if (value2.equals("")) value2 = getValorDefault(getCampoKey2(), hoy);

		if (this.isCampoCantidad())
			campo =  (this.getTargetAlias()==null?"":" "+this.getTargetAlias()+".")+"*";
		else if (this.getCampo().equals("FORMULA"))
			campo= formulaTOSQL(this.getFormatParam())  ;
		else {
			JObject obj = this.getObjRecordTarget().getProp(this.getCampo());
			if (obj!=null) {
				if (value!=null && hasOperador()) {
					obj.setValueFormUI(value);
					value=obj.toString();
				}
				if (hasValor2() && hasOperador()) {
					obj.setValueFormUI(value2);
					value2=obj.toString();
				}
				tipo=obj.getObjectType();
			}
			campo = (this.getTargetAlias()==null?"":this.getTargetAlias()+".")+this.getCampo();
		}
		
	
		if (function.equals(BizCampo.FUNTION_COUNT))
			variable= getExpresionWithOperator(bd, bd.fcount(/* ((getObjCustomList().isMatriz())?"distinct ":"")+ */campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_NULO))
			variable= getExpresionWithOperator(bd, bd.fnulo(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_NONULO))
			variable= getExpresionWithOperator(bd, bd.fnonulo(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_SUM))
			variable= getExpresionWithOperator(bd,bd.fsum(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_MAX))
			variable= getExpresionWithOperator(bd,bd.fmax(campo),tipo,value,value2);
		else if (function.equals(BizCampo.FUNTION_MIN))
			variable= getExpresionWithOperator(bd,bd.fmin(campo),tipo,value,value2);
		else if (function.equals(BizCampo.FUNTION_AVR))
			variable= getExpresionWithOperator(bd,bd.favg(campo),JObject.JFLOAT,value,value2);
		else if (function.equals(BizCampo.FUNTION_MES))
			variable= getExpresionWithOperator(bd,JTools.LPad(bd.fmes(campo), 2, "0"),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_ANIO))
			variable= getExpresionWithOperator(bd,bd.fanio(campo),JObject.JLONG,value,value2);
//		else if (function.equals(BizCampo.FUNTION_AYER))
//			variable= getExpresionWithOperator(bd,bd.fayer(campo,hoy),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_ANOMES))
			variable= getExpresionWithOperator(bd,bd.faniomes(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_ANOSEM))
			variable= getExpresionWithOperator(bd,bd.faniosem(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_BIMESTRE))
			variable= getExpresionWithOperator(bd,bd.fbimestre(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_TRIMESTRE))
			variable= getExpresionWithOperator(bd,bd.ftrimestre(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_CUATRIMESTRE))
			variable= getExpresionWithOperator(bd,bd.fcuatrimestre(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_SEMESTRE))
			variable= getExpresionWithOperator(bd,bd.fsemestre(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_DIA_SEMANA))
			variable= getExpresionWithOperator(bd,bd.fdiasemana(campo),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_DIA_MES))
			variable= getExpresionWithOperator(bd,JTools.LPad(bd.fdiames(campo), 2, "0"),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_DIA_ANO))
			variable= getExpresionWithOperator(bd,JTools.LPad(bd.fdiaano(campo), 3, "0"),JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_LIT))
			variable= getExpresionWithOperator(bd,select ? this.getFormatParam() : campo,JObject.JLONG,value,value2);
		else if (function.equals(BizCampo.FUNTION_FORMULA)||this.getCampo().equals(BizField.FUNTION_FORMULA))
			variable= getExpresionWithOperator(bd,formulaTOSQL(this.getFormatParam()) ,JObject.JSTRING,value,value2);
		else 
			variable = getExpresionWithOperator(bd,campo,tipo,value,value2); 
		if (!totalizador && isPorcentaje()) {
			if (sqlTotalize==null || (getAmbitoPorcentaje()!=null&&getAmbitoPorcentaje().size()>0))
				variable= bd.fporcWithOver(variable,getBuildAmbitoPorcentaje(getAmbitoPorcentaje()));
			else
				variable= bd.fporc(variable,sqlTotalize);
		}
		if (totalizador) {
//			variable= bd.fsumover(variable);
			if (this.getFuncion().equals(""))
				variable= bd.fsum(variable);
		}

		return variable;
	}
	public JList<String>  getBuildAmbitoPorcentaje(JList<String> lista) throws Exception {
		JList<String> out = JCollectionFactory.createList();
		JIterator<String> it =lista.getIterator();
		while(it.hasMoreElements()) {
			String key = it.nextElement();
			BizCampo eje = new BizCampo();
			eje.dontThrowException(true);
			if (!eje.read(getCompany(), getListId(), Long.parseLong(key))) continue;
			out.addElement(eje.findFunction(true));
		}
		return out;
	}
	
	public String entrecomillar(String v) throws Exception {
		boolean any=false;
		StringBuffer b = new StringBuffer();
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(v, ',');
		while (tk.hasMoreTokens()) {
			String t = tk.nextToken();
			if (any) b.append(",");
			b.append("'"+t+"'");
			any=true;
		}
		return b.toString();
	}
	 public String getExpresionWithOperator(JBaseRegistro bd,String field,String tipo,String value,String value2) throws Exception {
		 if (!hasOperador())
			 return field;
		 if (this.getOperador().equals("NOT_NULL")) {
				return bd.fbooleanValue(field+" is not null",getDescrTrue(),getDescrFalse());
			}
			if (this.getOperador().equals("IS_NULL")) {
				return bd.fbooleanValue(field+" is null",getDescrTrue(),getDescrFalse());
			}
			if (this.getOperador().equals("NULL")) {
				return bd.fbooleanValue(field+" is null",getDescrTrue(),getDescrFalse());
			}
			if (this.getOperador().equals("in")) {
				return bd.fbooleanValue(field + " in ("+ this.entrecomillar(value) +")",getDescrTrue(),getDescrFalse());
			}	
			if (this.getOperador().equals("not in")) {
				return bd.fbooleanValue(field + " not in ("+ this.entrecomillar(value) +")",getDescrTrue(),getDescrFalse());
			}		
			String dato = value;
			if (this.getOperador().equals("like")) 
				value="%"+value+"%";
			if (this.getOperador().equals("not like")) 
				value="%"+value+"%";
			if (tipo!=null)
				dato = new JRegSQL().ArmarDato(null, tipo, value);
			if (this.getOperador().equals(BizFuncion.FUNTION_INTERVALO)) {
				String dato2 = value2;
				if (tipo!=null) {
					dato2 = new JRegSQL().ArmarDato(null, tipo, dato2);
				}
				return bd.fbooleanValue(bd.fintervalo(field,dato,dato2),getDescrTrue(),getDescrFalse());
			}
			return bd.fbooleanValue(field+" "+getOperador()+" "+dato,getDescrTrue(),getDescrFalse());
	  }
	protected String findGeoFunction(String alias,String geoCampo) throws Exception {
		JBaseRegistro bd = JBaseRegistro.recordsetFactory();
		String variable="";
		String campo = "";
		campo = (alias==null?"":alias+".")+geoCampo;
		
		variable= bd.fmax(campo);

		return variable;
	}
	public String formulaTOSQL(String formula) throws Exception {
		String out=formula;
		// | R2139 01/03/07 |
		Pattern p = Pattern.compile("\\{\\*([a-z,_,0-9}]){1,35}\\}");
		// Pattern p = Pattern.compile("\\.*");
		Matcher m = p.matcher(URLDecoder.decode(formula, CharEncoding.ISO_8859_1));
		while (m.find()) {
			String detect = m.group();
			out=JTools.replace(out, detect,  this.getNameFieldFormula(detect.substring(2, detect.length()-1)));
		};
    if (out.equals(""))
    	return "''";
		return out;
	}
//  public JMap<String, String> getGeoCamposGallery() throws Exception {
//  	JRecord r = this.getObjRecordTarget();
//  	JOrderedMap<String, String> map = JCollectionFactory.createOrderedMap();
//  	JIterator<JProperty> iter = r.getFixedProperties().getValueIterator();
//  	while (iter.hasMoreElements()) {
//  		JProperty p = iter.nextElement();
//  	//	if (p.ifForeign()) continue;
//  		if (p.isVirtual()) continue;
//  	//	if (p.isRecord()) continue;
//  	//	if (p.isRecords()) continue;
//  		if (!(r.getProp(p.GetCampo()) instanceof JGeoPosition)) continue;
//  		if (r.getRelationMap().isFieldHided(p.GetCampo())) continue;
//  		map.addElement(p.GetCampo(), p.GetDescripcion());
//  	}
//  	JValueStringComparator<String> comp = new JValueStringComparator<String>(map);
//  	map.SortByValue(comp);
//
//  	return map;
//  }

	public JProperty getRankProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, "rank_"+this.getNameField(), "Ranking "+getDescrCampo(), null, "", true, true, 18, 0, null, null, null);
	}
	private JProperty getPorcentajeProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Porcentaje", null, "", true, true, 18, 2, null, null, null);
	}
	private JProperty getCantidadProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Cantidad", null, "", true, true, 18, 2, null, null, null);
	}
	public JProperty getDifProperty(String field,String titulo) throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, field,titulo, null, "", true, true, 18, 2, null, null, null);
	}
	private JProperty getFunctionProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Formula", null, "", true, true, 18, 2, null, null, null);
	}
	private JProperty getAyerProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Ayer", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getMesProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Mes", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getAnioProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "año", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getAnoMesProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "año/mes", null, "", true, true, 18, 0, null, null, null);
	}
	private JProperty getAnoSemProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "año/sem", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getBimestreProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Bimestre", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getTrimestreProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Trimestre", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getCuatrimestreProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Cuatrimestre", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getSemestreProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "Semestre", null, "", true, true, 18, 0, null, null, null);
	}

	private JProperty getDiaSemanaProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "día semana", null, "", true, true, 50,0, null, null, null);
	}

	private JProperty getDiaMesProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "día mes", null, "", true, true, 18, 0, null, null, null);
	}
	private JProperty getDiaAnoProperty() throws Exception {
		// trucho!
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), "día año", null, "", true, true, 18, 0, null, null, null);
	}
	private JProperty getLitProperty() throws Exception {
		// trucho
		return new JProperty(JRecord.VIRTUAL, this.getNameField(), getDescrCampo(), null, "", true, true, 50, 0, null, null, null);
	}
	

  public static JRelation createRelParent(String deep) throws Exception {
		if (deep==null) return null;
		if (deep.equals("")) return null;
		int o=1;
		JRelation p = null;
		JRelation anterior = null;
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(deep, '|');
		int cant = tk.countTokens(); int i=1;
		while (tk.hasMoreTokens()) {
			String c = tk.nextToken();
			if (cant==i) continue; // me salteo el ultimo proque no es parent
			i++;
			int idx = c.indexOf("_");
			JRecord rec = (JRecord)Class.forName(c.substring(0, idx)).newInstance();
			p= rec.getRelationMap().findRel(c.substring(idx+1));
			p.setObjRelParent(anterior);
			anterior=p;
  	}
		return p;
  }
	public String getTargetAlias(String field) throws Exception {

		return this.getObjRelation().getTargetAlias(field);
	}  

	public String getTargetAlias() throws Exception {

		return this.getObjRelation().getTargetAlias(getCampo());
	}  
	public String getGeoTargetAlias() throws Exception {

		return this.getObjRelation().getTargetAlias(getGeoCampo());
	}  
  public String getGeoCampo() throws Exception {
  	return this.getObjRecordTarget().getRelationMap().getGeoField(getCampo());
  }

  public boolean hasGeoCampo() throws Exception {
  	return this.getObjRecordTarget().getRelationMap().getGeoField(getCampo())!=null;
  }
  public boolean isOnlyLista() throws Exception {
  	if (hasFunction()) return false;
  	if (this.getObjRecordTarget()==null) return false;
  	if (this.getObjRecordTarget().getRelationMap()==null) return false;
  	return this.getObjRecordTarget().getRelationMap().isOnlyList(getCampo());
  }
  public boolean isFilter() throws Exception {
  	return this.getObjRecordTarget().getRelationMap().getFiltro(getCampo())!=null;
  }
  
  public String getOperadorDefault() throws Exception {
  	return this.getObjRecordTarget().getRelationMap().getFiltro(getCampo()).firstObject().toString();
  }
  public String getValorFiltroDefault() throws Exception {
  	return this.getObjRecordTarget().getRelationMap().getFiltro(getCampo()).secondObject().toString();
  }
  public JObject<?> createGeoProp() throws Exception {
  	String geoCampo=   getGeoCampo() ;
  	if (geoCampo==null) return null;
  	return this.getObjRecordTarget().getProp(geoCampo);
  }
}
