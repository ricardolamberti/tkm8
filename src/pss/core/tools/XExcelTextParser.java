package pss.core.tools;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XExcelTextParser {


	public String toCSV(InputStream inputFile) {
	    // Para almacenar datos en formato CSV
	    StringBuilder data = new StringBuilder();
	    try {
	        // Carga el archivo XLSX en un objeto Workbook
	        Workbook workbook = new XSSFWorkbook(inputFile);

	        // Obtiene la primera hoja del libro
	        Sheet sheet = workbook.getSheetAt(0);
	        Row row;
	        Cell cell;

	        // Itera a través de cada fila de la hoja
	        Iterator<Row> rowIterator = sheet.iterator();
	        while (rowIterator.hasNext()) {
	            row = rowIterator.next();
	            StringBuilder line = new StringBuilder();

	            // Obtiene el número máximo de columnas en la fila
	            int maxColumns = row.getLastCellNum();

	            // Recorre las columnas de la fila
	            for (int col = 0; col < maxColumns; col++) {
	                cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // Manejo de celdas vacías

	                if (col > 0) {
	                    line.append(";"); // Agrega el separador antes de cada columna excepto la primera
	                }

	                switch (cell.getCellType()) {
	                    case BOOLEAN:
	                        line.append(cell.getBooleanCellValue());
	                        break;
	                    case NUMERIC:
	                        if (DateUtil.isCellDateFormatted(cell)) {
	                            // Si es una fecha, formatea el valor
	                            line.append(new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue()));
	                        } else {
	                            line.append(cell.getNumericCellValue());
	                        }
	                        break;
	                    case STRING:
	                        line.append(cell.getStringCellValue());
	                        break;
	                    case BLANK:
	                        // Celda vacía, no se agrega contenido
	                        break;
	                    default:
	                        line.append(" "); // Por defecto, agrega un espacio
	                }
	            }
	            data.append(line.toString()).append("\r\n");
	        }

	        workbook.close(); // Cierra el Workbook para liberar recursos

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return data.toString();
	}


	public static void main(String[] args) {
		try {
			System.out.println(new XExcelTextParser().toCSV(new FileInputStream("c:\\BOPantaleon.xlsx")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
