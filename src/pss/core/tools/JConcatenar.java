package pss.core.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

public class JConcatenar {
	public static void concatenarPDF(String[] files,  String output)throws Exception {
			int i=0;
			PdfReader[] readers = new PdfReader[files.length];
			for (String file:files) 
				if (file!=null) readers[i++]=new PdfReader(file);
			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(output));
			for (PdfReader reader:readers) 
				if (reader!=null) copy.addDocument(reader);
			copy.close();
		}

	public static void concatenarHTML(String[] files,  String output)throws Exception {
		int i=0;

		JTools.DeleteFile(output);
		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			fstream = new FileWriter(output, true);
			 out = new BufferedWriter(fstream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
 
		for (String fs : files) {
			if (fs==null) continue;
			File f = new File(fs);
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				BufferedReader in = new BufferedReader(new InputStreamReader(fis));
 
				String aLine;
				while ((aLine = in.readLine()) != null) {
					out.write(aLine);
					out.newLine();
				}
				out.write("<p STYLE='page-break-after: always;'></p>");
 
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}

	}
