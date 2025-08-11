package pss.tourism.interfaceGDS.amadeus.record;

import java.util.ArrayList;
import java.util.List;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class MUCRecord extends AmadeusRecord {
	
		public static final String OFFICE="OFFICE";
		public static final String IATA="IATA";
		public static final String LOCATOR="LOCATOR";
		public static final String AGRESERVA="AGEMISION";

		public MUCRecord() {
			ID=MUC;
		}
		
		// ==========================================================================
		// AMD Record - Retrans && PNRLocator
		// ==========================================================================
//		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
//			
//			int i=0;
//			
//			if ( line.substring(i+=3).length() < 2 ) return line;
//
//			i+=3;
//			addFieldValue(LOCATOR, line.substring(i,i+=6) );
//			
//			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i),AmadeusRecord.FIELD_SEPARATOR);
//			tok.skipEmptyTokens(false);
//			
//			tok.nextToken(); //spare1
//			tok.nextToken(); //spare2
//			String agreserva =tok.nextToken();
//			tok.nextToken(); // espacio
//			tok.nextToken(); // dato
//			tok.nextToken(); // espacio
//			tok.nextToken(); // dato
//			tok.nextToken(); // espacio
//			String office = tok.nextToken();
//			String loc = tok.nextToken();
//
//			addFieldValue(AGRESERVA, agreserva );
//			addFieldValue(OFFICE, office );
//			addFieldValue(IATA, loc );
//
////  	MUC1A YOD747038;0402;BUEG121CF;;BUEG121CF;;BUEG121CF;;BUEG1222A;55502591;;;;;;;;;;;;;;;;;;;;;LA HN79B 
////		MUC1A X5JDTG018;0101;BUEG12240;;BUEG12240;;BUEG12240;;BUEG1222A;55502591;;;;;;;;;;;;;;;;;;;;;AR OYGOXP
//			return line.substring(i);
//		}
//		public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
//	    int i = 0;
//
//	    // Validar longitud mínima de la línea
//	    if (line.length() < i + 6) return line;
//
//	    if (line.substring(1+3,i+9).indexOf(" ")==-1) {
//		    i += 3;
//		    addFieldValue(LOCATOR, line.substring(i, i += 6));
//    	
//	    } else {
//		    i += 6;
//		    addFieldValue(LOCATOR, line.substring(i, i += 6));
//    	
//	    }
//
//	    // Tokenizar la línea usando el separador
//	    JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
//	        line.substring(i), AmadeusRecord.FIELD_SEPARATOR
//	    );
//	    tok.skipEmptyTokens(false); // Permitir tokens vacíos
//
//	    String agreserva = "";
//	    String office = "";
//	    String loc = "";
//
//	    int tokenPosition = 0;
//
//	    while (tok.hasMoreTokens()) {
//	        String token = tok.nextToken();
//
//	        // Solo sobrescribe si el token no está vacío
//	        if (token != null && !token.trim().isEmpty()) {
//	            switch (tokenPosition) {
//	                case 1: // agreserva
//	                    agreserva = token;
//	                    break;
//	               case 2: // office
//                    office = token;
//                    break;
//                case 3: // IATA loc
//                    loc = token;
//                    break;
//                case 5: // office
//	                    if (office.equals(""))office = token;
//	                    break;
//	                case 6: // IATA loc
//	                	if (loc.equals(""))loc = token;
//	                    break;
//	                default:
//	                    // Omitir otros tokens no necesarios
//	                    break;
//	            }
//	        }
//	        tokenPosition++;
//	    }
//
//	    // Asignar valores definitivos
//	    addFieldValue(AGRESERVA, agreserva);
//	    addFieldValue(OFFICE, office);
//	    addFieldValue(IATA, loc);
//
//	    return line.substring(i);
//	}
		public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		  int i = 0;

	    // Validar longitud mínima de la línea
	    if (line.length() < i + 6) return line;

	    // Procesar LOCATOR
	    if (line.substring(1 + 3, i + 9).indexOf(" ") == -1) {
	        i += 3;
	        addFieldValue(LOCATOR, line.substring(i, i += 6));
	    } else {
	        i += 6;
	        addFieldValue(LOCATOR, line.substring(i, i += 6));
	    }

	    
	    // Tokenizar la línea usando el separador
	    JStringTokenizer toks = JCollectionFactory.createStringTokenizer(
	        line.substring(i), AmadeusRecord.FIELD_SEPARATOR
	    );
	    toks.skipEmptyTokens(false); // Permitir tokens vacíos

	    // Listado de tokens procesados
	    List<String> lista = new ArrayList<>();
	    int consecutiveDelimiters = 0;

	    while (toks.hasMoreTokens()) {
	        String token = toks.nextToken().trim();

	        // Contar delimitadores consecutivos
	        if (token.isEmpty()) {
	            consecutiveDelimiters++;
	            if (consecutiveDelimiters >= 3) {
	                break; // Detener el procesamiento si se detectan `;;;`
	            }
	            continue; // Saltar tokens vacíos
	        } else {
	            consecutiveDelimiters = 0; // Reiniciar contador si el token no está vacío
	        }

	        // Filtrar tokens válidos (solo cadenas mayores a 4 caracteres)
	        if (token.length() > 4) {
	            lista.add(token);
	        }
	    }

	    // Asignar valores definitivos
	    String agreserva = lista.isEmpty() ? "" : lista.get(0); // Primer token
	    String office = lista.size() > 2 ? lista.get(lista.size() - 2) : ""; // Antepenúltimo token
	    String loc = lista.size() > 1 ? lista.get(lista.size() - 1) : ""; // Último token

	    addFieldValue(AGRESERVA, agreserva);
	    addFieldValue(OFFICE, office);
	    addFieldValue(IATA, loc);

	    return line.substring(i);
	}



		public String getPNRLocator() {
			return getFieldValue(LOCATOR);
		}

		public String getOffice() {
			return getFieldValue(OFFICE);
		}
		
		public String getIATA() {
			return getFieldValue(IATA);
		}

		public String getAgReserva() {
			return getFieldValue(AGRESERVA);
		}

}
