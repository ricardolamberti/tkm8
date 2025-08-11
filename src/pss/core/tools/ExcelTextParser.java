package pss.core.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class ExcelTextParser {

	public String toCSV(InputStream filename) {
		try {
			String result = "";

			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			Workbook w = Workbook.getWorkbook(filename, ws);

			// Gets the sheets from workbook
			for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++) {
				Sheet s = w.getSheet(sheet);

//				result += s.getName();
//				result += "\r\n";

				Cell[] row = null;

				// Gets the cells from sheet
				for (int i = 0; i < s.getRows(); i++) {
					row = s.getRow(i);

					if (row.length > 0) {
						String data = row[0].getContents();
						if (data.indexOf('\"')!=-1) data = data.replaceAll("\\\"", "");
						result += data.equals("")?" ":data;
						for (int j = 1; j < row.length; j++) {
							result += (';');
							data = row[j].getContents();
							if (data.indexOf('\"')!=-1) data = data.replaceAll("\\\"", "");
							result += data.equals("")?" ":data;
						}
					}
					result += "\r\n";
				}
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.toString());
		} catch (IOException e) {
			System.err.println(e.toString());
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return null;
	}

	// Extracts text from a PDF Document and writes it to a text file
	public static void main(String args[]) {

		if (args.length != 1) {
			System.out.println("Usage: java ExcelTextParser <InputPDFFilename> ");
			System.exit(1);
		}
		try {

			ExcelTextParser excelTextParserObj = new ExcelTextParser();
			String excelToText;
			excelToText = excelTextParserObj.toCSV(new FileInputStream(new File(args[0])));

			if (excelToText == null) {
				System.out.println("Excel to Text Conversion failed.");
			} else {
				System.out.println("\nThe text parsed from th eExcel Document....\n" + excelToText);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
