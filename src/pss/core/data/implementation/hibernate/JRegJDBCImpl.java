package pss.core.data.implementation.hibernate;

import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JRegHibernate;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JRegJDBCImpl extends JRegHibernate {

	// private int wherelen = 0;
	protected long pagesize = -1;
	protected long offset = -1;
	protected boolean withUse = false;

	public boolean isWithUse() {
		return withUse;
	}

	public void setWithUse(boolean useUse) {
		this.withUse = useUse;
	}

	public void setPageSize(long zRows) {
		pagesize = zRows;
	}

	public void setOffset(long zRows) {
		offset = zRows;
	}


	
	@Override
	public String ArmarSelect() throws Exception {
	
		return "";
	} // TRegSQL.Where


		@Override
	public String toNumber(String zFieldname) throws Exception {
		// return "to_number(" + (zFieldname) + ")";
		return "cast (" + zFieldname + " as numeric)";
	}

	@Override
	public String fsum(String zFieldname) throws Exception {
		return "coalesce(sum(" + (zFieldname) + "),0)";
	}
	@Override
	public String fnulo(String zFieldname) throws Exception {
		return "(" + (zFieldname) + " is null)";
	}
	@Override
	public String fnonulo(String zFieldname) throws Exception {
		return "(" + (zFieldname) + " is not null)";
	}
	@Override
	public String fsumover(String zFieldname) throws Exception {
		return "sum(" + (zFieldname) + ") over()"; // se usa para por porcentajes
	}
	@Override
	public String fmax(String zFieldname) throws Exception {
		return "max(" + (zFieldname) + ")";
	}
	@Override
	public String fmin(String zFieldname) throws Exception {
		return "min(" + (zFieldname) + ")";
	}

	@Override
	public String favg(String zFieldname) throws Exception {
		return "avg(" + (zFieldname) + ")";
	}

	@Override
	public String fcount(String zFieldname) throws Exception {
		return "count( " + (zFieldname) + ")";
	}

	public String fsubstring(String zFieldname, int desde, int hasta) throws Exception {
		return "substr(" + (zFieldname) + "," + desde + "," + hasta + ")";
	}

	public String ftoDate(String zFieldname, String format) throws Exception {
		return "to_date(" + (zFieldname) + ", '" + format + "')";
	}
	
	@Override
	public String fmes(String zFieldname) throws Exception {
		return "date_part('month'::text," + (zFieldname) + ")";
	}
	public String fbooleanValue(String zFieldname,String strue,String sfalse) throws Exception {
		return "(case " + (zFieldname) + " when true then '"+strue+"' else '"+sfalse+"' end)";
	}
	@Override
	public String fanio(String zFieldname) throws Exception {
		return "date_part('year'," + (zFieldname) + ")";
	}
	public String fbimestre(String zFieldname) throws Exception {
		return "floor(((extract (month from " + (zFieldname) + "))-1 ) / 2) +1";
	}
	public String ftrimestre(String zFieldname) throws Exception {
		return "floor(((extract (month from " + (zFieldname) + "))-1 ) / 3) +1";
	}
	public String fcuatrimestre(String zFieldname) throws Exception {
		return "extract(QUARTER  from " + (zFieldname) + ")";
	}
	public String fsemestre(String zFieldname) throws Exception {
		return "floor(((extract (month from  " + (zFieldname) + "))-1 ) / 6) +1";
	}
	public String fdiasemana(String zFieldname) throws Exception {
		return "case extract(dow from " + (zFieldname) + ") when 1 then '1.Lunes' when 2 then '2.Martes' when 3 then '3.Miercoles' when 4 then '4.Jueves' when 5 then '5.Viernes' when 6 then '6.Sabado' else '7.Domingo' end";
	}
	public String fdiames(String zFieldname) throws Exception {
		return "extract('day' from " + (zFieldname) + ")";
	}
	@Override
	public String faniomes(String zFieldname) throws Exception {
		return "(CAST(date_part('year'," + (zFieldname) + ") as Text) || '/' || lpad(CAST(date_part('month'::text," + (zFieldname) + ") as text),2,'0'))";
	}
	@Override
	public String faniosem(String zFieldname) throws Exception {
		return "(CAST(date_part('year'," + (zFieldname) + ") as Text) || '/' || lpad(CAST(date_part('week'::text," + (zFieldname) + ") as text),2,'0'))";
	}
	public String fporcWithOver(String zFieldname,JList<String> partition) throws Exception {
		String campos ="";
		if (partition!=null) {
			JIterator<String> it = partition.getIterator();
			while (it.hasMoreElements()) {
				campos += (campos.equals("")?"PARTITION BY ":",")+it.nextElement();
			}
		}
		return "case sum( " + (zFieldname) + ") over() when 0 then 0 else ((100* " + (zFieldname) + ") / sum( " + (zFieldname) + ") over("+campos+") ) end ";
	}
	public String fporc(String zFieldname,String zTotalize) throws Exception {
		return "case ("+zTotalize+") when 0 then 0 else ((100.0* " + (zFieldname) + ") / ("+zTotalize+") ) end ";
	}

	public String ftoChar(String zFieldname, String format) throws Exception {
		return "to_char(" + (zFieldname) + ", '" + format + "')";
	}
	public boolean hoyEsAhora(Date hoy) throws Exception  {
		if (BizUsuario.getUsr()==null)
			return (hoy==null||JDateTools.getDateStartDay(hoy).equals(JDateTools.getDateStartDay(new Date())));
		return (hoy==null||JDateTools.getDateStartDay(hoy).equals(JDateTools.getDateStartDay(BizUsuario.getUsr().todayGMT())));

	}
	public String fanioactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return  "date_part('year'," + (zFieldname) + ") = date_part('year', now())";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return  "date_part('year'," + (zFieldname) + ") = "+c.get(Calendar.YEAR);
	}
	public String fmesactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return  "date_part('year'," + (zFieldname) + ") = date_part('year', now()) and date_part('month'," + (zFieldname) + ") = date_part('month', now()) ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return  "date_part('year'," + (zFieldname) + ") = "+c.get(Calendar.YEAR)+" and date_part('month'," + (zFieldname) + ") = "+(c.get(Calendar.MONTH)+1)+" ";
	}
	public String fhoy(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date = 'today'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " "+zFieldname + "::date = '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String ffuturo(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date > 'today'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " "+zFieldname + "::date > '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fpasado(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date < 'today'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " "+zFieldname + "::date < '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fayer(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date = 'yesterday'::date ";
		if (hoy==null)
			hoy=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return " "+zFieldname + "::date = '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fmaniana(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date = 'tomorrow'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return " "+zFieldname + "::date = '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fultimos(String zFieldname,Date hoy,long valorDias) throws Exception {
		if (hoyEsAhora(hoy))
			return " ("+zFieldname + "::date > now() - '"+valorDias+" days'::interval and "+zFieldname + "::date < now()::date) ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " ("+zFieldname + "::date > '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date - '"+valorDias+" days'::interval and "+zFieldname + "::date < '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date) ";
	}
	public String fproximos(String zFieldname,Date hoy,long valorDias) throws Exception {
		if (hoyEsAhora(hoy))
			return " ("+zFieldname + "::date < now() + '"+valorDias+" days'::interval and "+zFieldname + "::date > now()::date) ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " ("+zFieldname + "::date < '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date + '"+valorDias+" days'::interval and "+zFieldname + "::date > '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date) ";
	}
//	public String fintervalo(String zFieldname,String valorDias1,String valorDias2) throws Exception {
//		return " ("+zFieldname + "::date > now() + '"+valorDias1+" days'::interval and "+zFieldname + "::date < now() + '"+valorDias2+" days'::interval) ";
//	}
	public String fintervalo(String zFieldname,String valor1,String valor2) throws Exception {
		return " ("+zFieldname + " between "+valor1+" and "+valor2+" )";
	}
	public String fbimestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 2) +1 = floor(((extract (month from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date))-1 ) / 2) +1) and "+fanioactual(zFieldname,hoy);
	}
	public String ftrimestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 3) +1 = floor(((extract (month from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date))-1 ) / 3) +1) and "+fanioactual(zFieldname,hoy);
	}
	public String fcuatrimestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (extract(QUARTER  from " + (zFieldname) + ")  = extract(QUARTER  from now())) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (extract(QUARTER  from " + (zFieldname) + ")  = extract(QUARTER  from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date)) and "+fanioactual(zFieldname,hoy);
	}
	public String fsemestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 6) +1 = floor(((extract (month from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date))-1 ) / 6) +1) and "+fanioactual(zFieldname,hoy);
	}
	@Override
	public String ArmarInsert() throws Exception {
			return "";
	}



	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como BLob
	// --------------------------------------------------------------------------
	// //
	@Override
	public String CampoAsBlob(String zCampo) throws Exception {

		return "";
	}

	@Override
	public long GetIdentity(String zCampo) throws Exception {

		return 0;
	}


	public  String ArmarDato(String zTabla, String zTipo, String zValor) throws Exception {
		return ArmarDato(zTabla, zTipo, zValor,true);
	}
	
	public static String ArmarDato(String zTabla, String zTipo, String zValor, boolean escape) throws Exception {
		String sTipo;

		if (zValor==null) return "null";
		sTipo=zTipo;

		if ( zTabla.length()<=6 && (zValor.trim().equalsIgnoreCase("null")||zValor.length()==0)) {
			return "''";
		}
		
		if (zValor.trim().equalsIgnoreCase("null")||zValor.length()==0) {
			return "NULL";
		}
			
		if (sTipo.equals(JObject.JDATETIME)) {
			if ( zValor.charAt(zValor.length()-4) == '.' )
				zValor = zValor.substring(0, zValor.length()-4);
			return COMILLA+zValor+COMILLA;
		}

		if (sTipo.equals(JObject.JSTRING)) return COMILLA+JTools.escapeQuote(zValor)+COMILLA;
		else if (sTipo.equals(JObject.JINTEGER)) return zValor;
		else if (sTipo.equals(JObject.JFLOAT)) return zValor;
		else if (sTipo.equals(JObject.JCURRENCY)) return zValor;
		else if (sTipo.equals(JObject.JLONG)) return zValor;
		else if (sTipo.equals(JObject.JPASSWORD)) return COMILLA+JTools.escapeQuote(JTools.StringToPassword(zValor))+"' ";
		else if (sTipo.equals("campo")) return zTabla+DOT+zValor;
		else if (sTipo.equals(JObject.JDATE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JHOUR)) return COMILLA+JTools.HourToString(zValor)+COMILLA;
		else if (sTipo.equals(JObject.JCOLOUR)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JIMAGE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JBOOLEAN)) return COMILLA+zValor+COMILLA;
//		else if (sTipo.equals(JObject.JDATETIME)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JGEOPOSITION)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JMULTIPLE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JBLOB)) {
			if ( escape )
			  return COMILLA+JTools.escapeQuote(zValor)+COMILLA;
			else
				return COMILLA+zValor+COMILLA;
		}
		return "";

	}

	
}
