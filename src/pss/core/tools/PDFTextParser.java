package pss.core.tools;
/*
 * PDFTextParser.java
 *
 * Created on January 24, 2009, 11:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PDFTextParser {
    
  PDFParser parser;
  String parsedText;
  PDFTextStripper pdfStripper;
  PDDocument pdDoc;
  COSDocument cosDoc;
  PDDocumentInformation pdDocInfo;
    
 
	// PDFTextParser Constructor 
  public PDFTextParser() {
  }
  
  // Extract text from PDF Document
  public String pdftoText(String fileName,boolean withSpaces, boolean sortedByPos) {
      
    System.out.println("Parsing text from PDF file " + fileName + "....");
    File f = new File(fileName);
    
    if (!f.isFile()) {
        System.out.println("File " + fileName + " does not exist.");
        return null;
    }
    
   try {
			FileInputStream in = new FileInputStream(f);
      return pdftoText(in,withSpaces,sortedByPos);//new ByteArrayInputStream(a));
   	} catch (Exception e) {
      System.out.println("Unable to open PDF Parser.");
      return null;
    }
  }        
  public String pdftoText(InputStream f) {
  	return pdftoText(f,true,false);
  }    

  public String pdftoText(InputStream f,boolean withOnlyOneSpaces, boolean sortedByPos) {
    try {
       RandomAccessRead r = new RandomAccessBufferedFileInputStream(f);
       parser = new PDFParser(r);
     } catch (Exception e) {
       System.out.println("Unable to open PDF Parser.");
       return null;
     }
    
    try {
      parser.parse();
      cosDoc = parser.getDocument();
      pdDoc = new PDDocument(cosDoc);
      if (withOnlyOneSpaces) {
				StringWriter outputStream = new StringWriter();
        pdfStripper = new PDFTextStripper();
      	pdfStripper.setSortByPosition(sortedByPos);
				pdfStripper.writeText(pdDoc, outputStream);
				parsedText = outputStream.toString().replace((char) 8722, '-');
      } else if(sortedByPos) {
        parsedText = this.addSpaces(pdDoc);
      } else {
        parsedText = this.extractNoSpaces(pdDoc);
      }
        
    } catch (Exception e) {
      System.out.println("An exception occured in parsing the PDF Document.");
      e.printStackTrace();
      try {
         if (cosDoc != null) cosDoc.close();
         if (pdDoc != null) pdDoc.close();
       } catch (Exception e1) {
       e.printStackTrace();
      }
      return null;
    }      
    System.out.println("Done.");
    return parsedText;
  }

  String extractNoSpaces(PDDocument document) throws Exception {
      PDFTextStripper stripper = new PDFTextStripper() {
          protected void processTextPosition(TextPosition text)
          {
              String character = text.getUnicode();
              if (character != null && character.trim().length() != 0)
                  super.processTextPosition(text);
          }
      };
      stripper.setSortByPosition(true);
      return stripper.getText(document);
  }
  String addSpaces(PDDocument document) throws Exception {
  	LayoutTextStripper stripper = new LayoutTextStripper();
  	stripper.setSortByPosition(true);
  	stripper.fixedCharWidth = 3; // ancho carater
//      PDFTextStripper stripper = new PDFTextStripper();
//      stripper.setSortByPosition(true);
      return stripper.getText(document);
  }
  // Write the parsed text from PDF to a file
  void writeTexttoFile(String pdfText, String fileName) {
  	
  	System.out.println("\nWriting PDF text to output text file " + fileName + "....");
  	try {
  		PrintWriter pw = new PrintWriter(fileName);
  		pw.print(pdfText);
  		pw.close();    	
  	} catch (Exception e) {
  		System.out.println("An exception occured in writing the pdf text to file.");
  		e.printStackTrace();
  	}
  	System.out.println("Done.");
  }
  
  //Extracts text from a PDF Document and writes it to a text file
  public static void main(String args[]) {
  	
  	if (args.length != 2) {
      System.out.println("Usage: java PDFTextParser <InputPDFFilename> <OutputTextFile>");
      System.exit(1);
    }
      
    PDFTextParser pdfTextParserObj = new PDFTextParser();
    String pdfToText = pdfTextParserObj.pdftoText(args[0],false,true);
      
    if (pdfToText == null) {
    	System.out.println("PDF to Text Conversion failed.");
    }
    else {
    	System.out.println("\nThe text parsed from the PDF Document....\n" + pdfToText);
    	pdfTextParserObj.writeTexttoFile(pdfToText, args[1]);
    }
  }  
  
  
  
  public class LayoutTextStripper extends PDFTextStripper {
    public float fixedCharWidth = 3;

    boolean endsWithWS = true;
    boolean needsWS = false;
    int chars = 0;

    PDRectangle cropBox = null;
    float pageLeft = 0;
    
    public LayoutTextStripper() throws Exception {
    }

    @Override
    protected void startPage(PDPage page) throws IOException {
      super.startPage(page);
      this.cropBox = page.getCropBox();
      this.pageLeft = cropBox.getLowerLeftX();
      this.beginLine();
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
      float recentEnd = 0;
      for (TextPosition textPosition: textPositions) {
        String textHere = textPosition.getUnicode();
        if (textHere.trim().length() == 0)
            continue;

        float start = textPosition.getX();
        boolean spacePresent = endsWithWS | textHere.startsWith(" ");
        if (needsWS | spacePresent | Math.abs(start - recentEnd) > 1) {
          int spacesToInsert = this.insertSpaces(chars, start, needsWS & !spacePresent);
          for (; spacesToInsert > 0; spacesToInsert--) {
          	this.writeString(" ");
          	chars++;
          }
        }

        writeString(textHere);
        chars += textHere.length();

        needsWS = false;
        endsWithWS = textHere.endsWith(" ");
       
        try {
          recentEnd = getEndX(textPosition);
        } catch (IllegalArgumentException  e) {
          throw new IOException("Failure retrieving endX of TextPosition", e);
        } catch (  IllegalAccessException  e) {
          throw new IOException("Failure retrieving endX of TextPosition", e);
        } catch (  NoSuchFieldException  e) {
          throw new IOException("Failure retrieving endX of TextPosition", e);
        } catch ( SecurityException e) {
          throw new IOException("Failure retrieving endX of TextPosition", e);
        }
      }
    }

    @Override
    protected void writeLineSeparator() throws IOException {
      super.writeLineSeparator();
      beginLine();
    }

      @Override
    protected void writeWordSeparator() throws IOException {
      	needsWS = true;
    }

    void beginLine() {
      endsWithWS = true;
      needsWS = false;
      chars = 0;
    }

    int insertSpaces(int charsInLineAlready, float chunkStart, boolean spaceRequired) {
      int indexNow = charsInLineAlready;
      int indexToBe = (int)((chunkStart - pageLeft) / fixedCharWidth);
      int spacesToInsert = indexToBe - indexNow;
      if (spacesToInsert < 1 && spaceRequired)
          spacesToInsert = 1;

      return spacesToInsert;
    }

    float getEndX(TextPosition textPosition) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
      Field field = textPosition.getClass().getDeclaredField("endX");
      field.setAccessible(true);
      return field.getFloat(textPosition);
    }


  }
}