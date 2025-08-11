package pss.common.customList.config.customlist.importJSON;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser {

    public static Report parseJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(extractJson(json), Report.class);
    }

    public static String extractJson(String input) {
      // Define the pattern to match text between '''json and '''
      String regex = "```json(.*?)```";
      Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
      Matcher matcher = pattern.matcher(input);

      // Find the first match and return the captured group
      if (matcher.find()) {
          return matcher.group(1).trim(); // group(1) contains the text between the delimiters
      } else {
          return null;  // Return null if no match is found
      }
  }

    public static void main(String[] args) {
        // Ejemplo de uso del Método parseJson
        String jsonString = "{ \"type\": \"grilla\", \"list_command\": [ { \"object\": \"campo\", \"field\": \"nombre\", \"function\": \"SUM\" }, { \"object\": \"filtro\", \"field\": \"edad\", \"operator\": \">\", \"value\": \"30\" } ] }";

        try {
            Report report = parseJson(jsonString);
            System.out.println("Tipo de reporte: " + report.getType());
            for (Command command : report.getListCommand()) {
                System.out.println("Objeto: " + command.getObject());
                System.out.println("Campo: " + command.getField());
                System.out.println("Función: " + command.getFunction());
                System.out.println("Operador: " + command.getOperator());
                System.out.println("Valor: " + command.getValue());
                System.out.println("Valor desde: " + command.getValueTo());
                System.out.println("Valor hasta: " + command.getValueFrom());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}