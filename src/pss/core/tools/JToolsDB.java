package pss.core.tools;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JHour;
import pss.core.win.GuiVirtual;
import pss.core.win.GuiVirtuals;
import pss.core.win.JWins;


public class JToolsDB {

	// devuelve el la fecha de fin de mes de una fecha en particular
	public static JDate getEndMonth(Date now) {
		int lastDay[]= { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		Calendar oCalendar=Calendar.getInstance();
		oCalendar.setTime(now);
		if (oCalendar.get(Calendar.YEAR)%4==0) lastDay[2]=29;
		oCalendar.set(oCalendar.get(Calendar.YEAR), oCalendar.get(Calendar.MONTH), lastDay[oCalendar.get(Calendar.MONTH)]);
		return new JDate(oCalendar.getTime());
	}

	// devuelve el la fecha de comienzo de mes de una fecha en particular
	public static JDate getInitMonth(Date now) {
		Calendar oCalendar=Calendar.getInstance();
		oCalendar.setTime(now);
		oCalendar.set(oCalendar.get(Calendar.YEAR), oCalendar.get(Calendar.MONTH), 1);
		return new JDate(oCalendar.getTime());
	}

	public static boolean isAfterDate(JDate oDateAfter, JHour oHourAfter, JDate oDateBefore, JHour oHourBefore) throws Exception {
		if (oDateAfter.after(oDateBefore)) {
			return true;
		} else if (oDateAfter.equalsDate(oDateBefore)) {
			return oHourAfter.after(oHourBefore);
		} else {
			return false;
		}
	}

	public static String convertNumberToWords(Serializable zNro) throws Exception {
		JFloat jNro=new JFloat();
		jNro.setValue(zNro);
		return JToolsDB.convertCurrencyToWords(jNro, "", "", false, false);
	}

	public static String convertCurrencyToWords(JFloat zNro, String zMoneda, String zFraccion, boolean zisMoneda, boolean zisFraccion) throws Exception {
		// Ejemplo de llamada
		// JTools.convertNumberToWords(xx,"dolar","centavo");
		String sNro=zNro.toRawString();
		String sFormat="000000000000";
		String sDecimalIni="";
		String sDecimal="";
		if ((sNro.lastIndexOf(".")==-1)&&(sNro.lastIndexOf(",")==-1)) {
			if (sNro.lastIndexOf(".")==-1) {
				sNro=sNro+".00";
				sDecimalIni=sNro.substring(sNro.lastIndexOf(".")+1, sNro.length());
				sNro=sNro.substring(0, sNro.lastIndexOf("."));
			} else if (sNro.lastIndexOf(",")==-1) {
				sNro=sNro+",00";
				sDecimalIni=sNro.substring(sNro.lastIndexOf(",")+1, sNro.length());
				sNro=sNro.substring(0, sNro.lastIndexOf(","));
			}
		} else {
			if (sNro.lastIndexOf(".")!=-1) {
				sDecimalIni=sNro.substring(sNro.lastIndexOf(".")+1, sNro.length());
				sNro=sNro.substring(0, sNro.lastIndexOf("."));
			} else if (sNro.lastIndexOf(",")!=-1) {
				sDecimalIni=sNro.substring(sNro.lastIndexOf(",")+1, sNro.length());
				sNro=sNro.substring(0, sNro.lastIndexOf(","));
			}
		}
	
		String sTexto="";
		int iBillones, iMillones, iMiles, iUnidades;
		@SuppressWarnings("unused")
		String unidad_1_s, unidad_2_s;
		String sMonedaPlural, sFraccionPlural;
		sNro=sFormat.substring(0, sFormat.length()-sNro.length())+sNro;
		sDecimal=(sDecimalIni+((sDecimalIni.length()<2) ? "00".substring(0, 2-sDecimalIni.length()) : "")).substring(0, 2);
		iBillones=Integer.parseInt(sNro.substring(0, 3));
		iMillones=Integer.parseInt(sNro.substring(3, 6));
		iMiles=Integer.parseInt(sNro.substring(6, 9));
		iUnidades=Integer.parseInt(sNro.substring(9, 12));
	
		if (iBillones!=0) sTexto=JTools.numberToWords(iBillones, false)+"mil ";
	
		if (iMillones==1) {
			sTexto=sTexto+JTools.numberToWords(iMillones, false)+"millón ";
		} else if (iMillones>1) {
			sTexto=sTexto+JTools.numberToWords(iMillones, false)+"millones ";
		}
	
		if (iMiles!=0) sTexto=sTexto+JTools.numberToWords(iMiles, false)+"mil ";
	
		if (iUnidades!=0) sTexto=sTexto+JTools.numberToWords(iUnidades, true);
		if (zisMoneda) {
			sMonedaPlural=JTools.getPlural(zMoneda);
	
			if (zNro.getValue()==1) {
				sTexto=sTexto+zMoneda.trim()+" ";
			} else if (zNro.getValue()>0) {
				sTexto=sTexto+sMonedaPlural.trim()+" ";
			}
		}
		if (Integer.parseInt(sDecimal)>0) {
			if (sTexto.length()==0) sTexto="Cero ";
			sTexto=sTexto+(zNro.getValue()>0 ? "con " : "")+JTools.numberToWords(Integer.parseInt(sDecimal), false);
		}
	
		if (zisFraccion) {
			sFraccionPlural=JTools.getPlural(zFraccion);
			if (Integer.parseInt(sDecimal)>0) {
				sTexto=sTexto+(Integer.parseInt(sDecimal)==1 ? zFraccion : sFraccionPlural);
			}
		}
		/*
		 * emilio begin if (sTexto.equals("")) sTexto = "Cero "; if (zisMoneda && !zMoneda.trim().equals("")) { sMonedaPlural = getPlural(zMoneda);
		 * 
		 * if (zNro.GetValor() == 1) { sTexto = sTexto + zMoneda.trim() + " "; } else if (zNro.GetValor() > 0) { sTexto = sTexto + sMonedaPlural.trim() + " "; } } sTexto = sTexto + " " + sDecimal+"/100 "; if (zisFraccion && !zFraccion.trim().equals("")) { sFraccionPlural = getPlural(zFraccion); sTexto = sTexto + (Integer.parseInt(sDecimal) == 1 ? zFraccion : sFraccionPlural); } emilio end
		 */
	
		if (sTexto.length()>0) {
			sTexto=sTexto.substring(0, 1).toUpperCase()+sTexto.substring(1);
		}
		return sTexto;
	}
	public static String convertCurrencyToWords(double zNro, String moneda) throws Exception {
		JFloat jNro=new JFloat();
		double nro = JTools.forceRd(zNro,2);
		jNro.setValue(nro);
		return convertCurrencyToWords(jNro, moneda, "", !moneda.equals(""), false);
	}

	public static String convertCurrencyToWords(double zNro, String zMoneda, String zFraccion) throws Exception {
		JFloat jNro=new JFloat();
		jNro.setValue(zNro);
		return convertCurrencyToWords(jNro, zMoneda, zFraccion, true, true);
	}
	
	public static JWins getYesOrNot(String vision) throws Exception {
			GuiVirtuals v = new GuiVirtuals();
			v.SetVision(vision);
			v.SetEstatico(true);
			GuiVirtual si=new GuiVirtual();
			si.SetVision(vision);
			si.GetcDato().setValor("S");
			si.GetcDato().setDescripcion(BizUsuario.getMessage("Si", null));
			v.addRecord(si);
			GuiVirtual no=new GuiVirtual();
			no.SetVision(vision);
			no.GetcDato().setValor("N");
			no.GetcDato().setDescripcion(BizUsuario.getMessage("No", null));
			v.addRecord(no);		
			
			return v;
		}
	public static JWins getYesOrNotOrCancel(String vision) throws Exception {
		GuiVirtuals v = new GuiVirtuals();
		v.SetVision(vision);
		v.SetEstatico(true);
		GuiVirtual si=new GuiVirtual();
		si.SetVision(vision);
		si.GetcDato().setValor("S");
		si.GetcDato().setDescripcion(BizUsuario.getMessage("Si", null));
		v.addRecord(si);
		GuiVirtual no=new GuiVirtual();
		no.SetVision(vision);
		no.GetcDato().setValor("N");
		no.GetcDato().setDescripcion(BizUsuario.getMessage("No", null));
		v.addRecord(no);		
		GuiVirtual cancel=new GuiVirtual();
		cancel.SetVision(vision);
		cancel.GetcDato().setValor("C");
		cancel.GetcDato().setDescripcion(BizUsuario.getMessage("Cancelar", null));
		v.addRecord(cancel);
		return v;
	}
}
