/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.company.JCompanyBusinessModules;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.tools.formatters.JRegionalFormatterFactory;
import pss.www.ui.input.JWebDateInputFormat;

public class JGlobalStringsGenerator extends JXMLComponentGenerator {

	protected void doGenerate() throws Exception {
		this.startNode("strings");
		String[] strings=this.createStringsFixed();
		String strId;
		for(int i=0; i<strings.length; i++) {
			strId=strings[i];
			if (strId.startsWith("fmt_")) {
				this.setAttribute(strId, strings[i+1]);
			} else {
				this.setAttribute(strId, JLanguage.getTranslationFor(strings[i+1]));
			}
			i++;
		}
		strings=this.createStrings();
		for(int i=0; i<strings.length; i++) {
			strId=strings[i];
			this.startNode("string");
				this.setAttribute("variable",strId);
				if (strId.startsWith("fmt_")) {
					this.setAttribute("value", strings[i+1]);
				} else {
					this.setAttribute("value", JLanguage.getTranslationFor(strings[i+1]));
				}
			this.endNode("string");
			i++;
		}
		this.endNode("strings");
	}

	protected String getBaseContentName() {
		return "global.strings";
	}

	private String getDefaultDateInputPattern() throws Exception {
		if (this.getSession()!=null) {
			return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern();
		} else {
			return "dd-MM-yyyy";
		}
	}
// por compatibilidad, definir los nuevos en createString
	protected String[] createStringsFixed() throws Exception {
		BizUsuario usr = BizUsuario.getUsr();
		if (usr!=null) { 
			String[] strs= usr.getObjBusiness().createStringsFixed();
			if (strs!=null) return strs;
		}
		JCompanyBusiness cm = JCompanyBusinessModules.createBusinessDefault();
		if (cm!=null) {
			String[] strs= cm.createStringsFixed();
			if (strs!=null) return strs;	
		}
		return new String[] { 
				"msg_required_field", "Campo requerido: '{0}'.",
				"msg_max_field_length", "La longitud máxima del campo '{0}' es {1}.",
				"msg_invalid_date", "Fecha inválida en el campo '{0}'. Formato esperado: '{1}'.",
				"msg_invalid_time", "Hora inválida en el campo '{0}'. Formato esperado: '{1}'.",
				"msg_invalid_number", "Número inválido en el campo '{0}'. Formato esperado: '{1}'.",
				"msg_invalid_string", "Valor alfanumérico inválido en el campo '{0}'. Formato esperado: {1}.",
				"msg_invalid_number_no_format", "Número inválido en el campo '{0}'.",
				"str_day", "día",
				"str_month", "mes",
				"str_year", "año",
				"str_hour", "hora",
				"str_minute", "minuto",
				"str_second", "segundo",
				"str_with_no_spaces", "sin espacios",
				"str_only_letter_or_digit", "sólo letras o números",
				"str_prefix_tree_select", "Seleccionar: ",
				"str_prefix_tree_filter_by", "Filtrar por: ",
				"str_prefix_tree_no_selectable_option", "Esta opción no es seleccionable",
				"str_sunday", "Domingo",
				"str_monday", "Lunes",
				"str_tuesday", "Martes",
				"str_wednesday", "Miércoles",
				"str_thursday", "Jueves",
				"str_friday", "Viernes",
				"str_saturday", "Sábado",
				"str_january", "Enero", "str_february", "Febrero", "str_march", "Marzo", "str_april", "Abril", "str_may", "Mayo", "str_june", "Junio", "str_july", "Julio", "str_august", "Agosto", "str_september", "Septiembre", "str_october", "Octubre", "str_november", "Noviembre", "str_december", "Diciembre",
				"str_changes_first_wee_day", "Cambia el primer día de la semana", 
				"str_previous_year", "Año anterior (mantener para menu)", 
				"str_previous_month", "Mes anterior (mantener para menu)", 
				"str_go_today", "Ir a hoy", "str_next_month", 
				"Mes siguiente (mantener para menu)", 
				"str_next_year", "Año siguiente (mantener para menu)", 
				"str_select_date", "Seleccionar fecha", 
				"str_drag_to_move", "Arrastrar para mover", 
				"str_today_with_brackets", " (hoy)", 
				"str_show_monday_first", "Mostrar lunes primero", 
				"str_show_sunday_first", "Mostrar domingo primero", 
				"str_close", "Cerrar", 
				"str_today", "Hoy",
				"str_week_short", "sem",
				"fmt_default_date_format", JWebDateInputFormat.getClientDateFormat(this.getDefaultDateInputPattern()), 
				"msg_processing","Procesando ...",
				"msg_error","Mensaje de Error",
				"msg_info","Information",
				"msg_options","Opciones",
				"msg_selections","Selecciones",
				"msg_hideselections","Ocultos",
				"msg_others","Otros",
				"msg_close","Cerrar",
				"msg_confirmation_yes","Si",
				"msg_confirmation_no","No",
				"msg_lostdata","Los datos se perderan, confirma?",
				"msg_abmbuttoncancel","Cancelar"

			};
	}
	
		protected String[] createStrings() throws Exception {
			BizUsuario usr = BizUsuario.getUsr();
			if (usr!=null) { 
				String[] strs= usr.getObjBusiness().createStrings();
				if (strs!=null) return strs;
			}
			JCompanyBusiness cm = JCompanyBusinessModules.createBusinessDefault();
			if (cm!=null) {
				String[] strs= cm.createStrings();
				if (strs!=null) return strs;	
			}
			return new String[] { 
					"errorLoading", "No se pudieron cargar los resultados",
					"inputTooLong1", "Por favor, elimine ", 
					"inputTooLong2", " carácter", 
					"inputTooLong3", " caracteres",
					"inputTooShort1","Por favor, introduzca ", 
					"inputTooShort2", " carácter", 
					"inputTooShort3", " caracteres",
					"loadingMore", "Cargando más resultados...",
					"maximumSelected1","Sólo puede seleccionar ",
					"maximumSelected2"," elemento",
					"maximumSelected3","elementos",
					"noResults","No se encontraron resultados",
					"searching","Buscando...",
					"jsondtnodata","Sin datos",
					"jsondtinfo","_START_ a  _END_ de _TOTAL_ filas",
					"jsondtinfoempty","0 filas",
					"jsondtinfomax","(Filtradas _MAX_ filas)",
					"jsondtlines","#Lineas _MENU_",
					"jsondtloading","Cargando...",
					"jsondtzerorecords","Sin coincidencias",
					"jsondtrowselect","Filas %d seleccionadas",
					"jsondtrow0","Click para seleccionar",
					"jsondtrow1","Solo 1 fila seleccionada",
					"selectrow","Debe seleccionar una fila",
					"masinfo","Más Info",
					"menosinfo","Menos Info"
					
			};
	}

}
