package pss.core.tools;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XExcelTextParser {

	public String toCSV(InputStream inputFile) {
		// For storing data into CSV files
		StringBuffer data = new StringBuffer();
		try {

			// Get the workbook object for XLSX file
			XSSFWorkbook wBook = new XSSFWorkbook(inputFile);

			// Get first sheet from the workbook
			XSSFSheet sheet = wBook.getSheetAt(0);
			Row row;
			Cell cell;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				StringBuffer line = new StringBuffer();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {

					cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						line.append((line.toString().equals("")?"":";")+cell.getBooleanCellValue());

						break;
					case Cell.CELL_TYPE_NUMERIC:
						line.append((line.toString().equals("")?"":";")+cell.getNumericCellValue());

						break;
					case Cell.CELL_TYPE_STRING:
						line.append((line.toString().equals("")?"":";")+cell.getStringCellValue());
						break;

					case Cell.CELL_TYPE_BLANK:
						line.append((line.toString().equals("")?"":";")+ " ");
						break;
					default:
						line.append((line.toString().equals("")?"":";")+ cell );

					}
				}
				data.append(line.toString()+"\r\n");

			}


		} catch (Exception ioe) {
			ioe.printStackTrace();
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
