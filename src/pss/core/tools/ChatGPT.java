package pss.core.tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class ChatGPT {

	    public static String chatListCustom(String company,String user,String question,String campos, String entorno,String spec, boolean clearHistory) {
	        try {
	            // URL del servicio Flask
	           // URL url = new URL(BizPssConfig.chatIAServer()+"/request_reportgpt");
	            URL url = new URL("http://localhost:5000/request_reportgpt");
		  				String systemQuery="Completa las instrucciones para llenar un reporte, el reporte se construye ejecutando una lista de instrucciones, los tags son:" + 
							"            type: ' Los tipos de reporte son (" + 
							"                'grilla': contiene columnas y filas que se las intersecciones campos con alguna funcion de sumarizacion(SUM), maximo(MAX), minimo (MIN)" + 
							"                'agrupado': solo contiene campos, los campos que no tienen funcion funcionan como agrupador." + 
							"                'lista': solo contiene campos y nos adminte funciones de agrupacion, la informacion se muestra detallada" + 
							"                'dato': solo devuelve un dato que es un capo como funcion, el resto de los campos son filtros)'" + 
							"            object: 'Objeto al que se aplica la instruccion: campo, columna, fila, filtro'" + 
							"            accion: 'agregar o quitar'" + 
							"            porcentaje: 'si o no,  expresar valores en porcentajes'" + 
							"            Funcion: 'Funciones aplicables a los campos puede ser " + 
							"                 SUM, MAX, MIN, AVG, " + 
							"                 si es fecha tambien estas funciones:  HOY, AYER, MANIANA, ANIO, MES, BIMESTRE, TRIMESTRE, CUATRIMESTRE, SEMESTRE" + 
							"                 si es fecha hay funciones especiales que no requieren parametros value ni value_to, ni value _from: ANIOACTUAL, MESACTUAL , BIMESTREACTUAL " + 
							"                 TRIMESTREACTUAL, CUATRIMESTREACTUAL ,SEMESTREACTUAL, ANIOANTERIOR, MESANTERIOR, BIMESTREANTERIOR, TRIMESTREANTERIOR, " + 
							"                 CUATRIMESTREANTERIOR, SEMESTREANTERIOR, PASADO (antes de l fecha actual) , FUTURO (luego de la fecha actual)'" + 
							"            Operador: 'Operadores que se aplica si es un Filtro puede ser: >,<,=,<>, NOT_NULL, IS_NULL, NULL, IN, NOT IN, LIKE, NOT LIKE, INTERVAL'" + 
							"            value: 'Valor de la condicion del filtro'" + 
							"            value_to: 'Valor para el operador in, valor desde'" + 
							"            value_from: 'Valor para el operador in, valor hasta'" + 
							"            orden: 'N°mero de orden, 0 sin orden'" + 
							"            sentido_orden: 'ascendente o descendente'" + 
							"            limite: 'Limite, por ejemplo, 10, para un top 10'" + 
							"            field: (los campos posibles son:" + campos +") "+	entorno +". "+spec+
							"            El formato de fecha es DD/MM/YYYY. Si la lista pide una comparativa asegurarse de poner la funcion, por ejempplo: si pide comparar trimestre por fecha emision, ponerademas del filtro del periodo, una campo fecha emision, con funcion trimestre, para que agrupe por ese periodo. " +
							"            La respuesta es a un usuario que no se le muestra parte json, sino que ve sus resultados aplicados en el reporte, " +
							"            tener en cuenta esto en los comentarios que se hacen";
	           
				  	PssLogger.logInfo("Pregunta: "+systemQuery);

						// Establecer la conexión
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setDoOutput(true);

	            systemQuery=JTools.replace(systemQuery,"\"","'") ;
	            
	            try {
								JRecords<BizCompany> records = new JRecords<BizCompany>(BizCompany.class);
								JIterator<BizCompany> it = records.getStaticIterator();
								while(it.hasMoreElements()) {
									BizCompany comp = it.nextElement();
			            if (question.toUpperCase().indexOf("'"+comp.getCompany()+"'")!=-1) {
			            	question=JTools.replace(question.toUpperCase(), "'"+comp.getCompany()+"'", "'"+BizUsuario.getUsr().getCompany()+"'");
			            }
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            
	            // Cuerpo de la solicitud
	            String requestBody = "{\"company\": \""+company+"\",\"user_id\": \""+user+"\", \"question\": \""+JTools.replace(question, "\n", "")+"\", \"system_query\": \""+systemQuery+"\", \"clear_history\":\""+(clearHistory?"1":"0")+"\"}";
	            
	            // Enviar la solicitud
	            try (OutputStream os = conn.getOutputStream()) {
	                byte[] input = requestBody.getBytes("utf-8");
	                os.write(input, 0, input.length);
	            }
	            StringBuilder response = new StringBuilder();
		          
	            // Leer la respuesta
	            try (BufferedReader br = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
	                String responseLine = null;
	                while ((responseLine = br.readLine()) != null) {
	                    response.append(responseLine.trim());
	                }
	                System.out.println(response.toString());
	                
	            }

	            // Cerrar la conexión
	            conn.disconnect();
	            return new JSONObject(response.toString()).getString("response");
	            
	        } catch (IOException e) {
	            return e.getMessage();
	        }

	    }
	    
	    public static String chatSQLBoletos(String company,String user,String question, boolean clearHistory) {
        try {
            // URL del servicio Flask
            URL url = new URL(BizPssConfig.chatIAServer()+"/request_gpt");

            // Establecer la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // Cuerpo de la solicitud
            String requestBody = "{\"company\": \""+company+"\",\"user_id\": \""+user+"\", \"question\": \""+question+"\", \"clear_history\":\""+(clearHistory?"1":"0")+"\"}";

            // Enviar la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            StringBuilder response = new StringBuilder();
	          
            // Leer la respuesta
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                
            }

            // Cerrar la conexión
            conn.disconnect();
            return new JSONObject(response.toString()).getString("response");
            
        } catch (IOException e) {
            return e.getMessage();
        }

    }
	
//
//    public static void chatGPT(String text) throws Exception {
//        String url = "https://api.openai.com/v1/completions";
//        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
//
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Authorization", "Bearer YOUR-API-KEY");
//
//        JSONObject data = new JSONObject();
//        data.put("model", "text-davinci-003");
//        data.put("prompt", text);
//        data.put("max_tokens", 4000);
//        data.put("temperature", 1.0);
//
//        con.setDoOutput(true);
//        con.getOutputStream().write(data.toString().getBytes());
//
//        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
//                .reduce((a, b) -> a + b).get();
//
//        System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
//    }

    public static void main(String[] args) throws Exception {
        System.out.print(chatSQLBoletos("TEST","rjl","cual fue la ultima pregunta?",true));
    }
}