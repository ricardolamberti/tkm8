package pss.core.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.xml.sax.InputSource;

import com.steadystate.css.parser.CSSOMParser;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import pss.JPath;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.security.mail.BizUsrMailSender;
import pss.core.services.fields.JFloat;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.tools.crypt.Des;
import pss.core.tools.crypt.TripleDES;

public class JTools {

	private static Des stDes = null;
	private static int[] crcTable = { 0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241, 0xC601, 0x06C0,
			0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440, 0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81,
			0x0E40, 0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841, 0xD801, 0x18C0, 0x1980, 0xD941,
			0x1B00, 0xDBC1, 0xDA81, 0x1A40, 0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41, 0x1400,
			0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641, 0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1,
			0xD081, 0x1040, 0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240, 0x3600, 0xF6C1, 0xF781,
			0x3740, 0xF501, 0x35C0, 0x3480, 0xF441, 0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
			0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840, 0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01,
			0x2BC0, 0x2A80, 0xEA41, 0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40, 0xE401, 0x24C0,
			0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640, 0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080,
			0xE041, 0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240, 0x6600, 0xA6C1, 0xA781, 0x6740,
			0xA501, 0x65C0, 0x6480, 0xA441, 0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41, 0xAA01,
			0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840, 0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0,
			0x7A80, 0xBA41, 0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40, 0xB401, 0x74C0, 0x7580,
			0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640, 0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
			0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241, 0x9601, 0x56C0, 0x5780, 0x9741, 0x5500,
			0x95C1, 0x9481, 0x5440, 0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40, 0x5A00, 0x9AC1,
			0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841, 0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81,
			0x4A40, 0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41, 0x4400, 0x84C1, 0x8581, 0x4540,
			0x8701, 0x47C0, 0x4680, 0x8641, 0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040, };

	private static char BINTOCHAR[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	private static final char base62CharVector[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static String longToBase62String(long zValue, long zLong) throws Exception {

		String retVal = "";
		long aux = zValue;

		for (int i = 1; i <= zLong; ++i) {
			int index = (int) aux % 62;
			retVal = String.valueOf(base62CharVector[index]) + retVal;
			aux = aux / 62;
		}

		return retVal;
	}

	public static boolean checkStrongPass(String in) {
		int minus = 0;
		int mayus = 0;
		int letters = 0;
		int numbers = 0;
		int j = 0;
		if (in.length() < 8)
			return false;
		for (j = 0; j < in.length(); j++) {
			char ch = in.charAt(j);
			if (isNumber(ch))
				numbers++;
			else if (isAlpha("" + ch)) {
				letters++;
				if ((ch >= 'a') && (ch <= 'z'))
					minus++; // lowercase
				else if ((ch >= 'A') && (ch <= 'Z'))
					mayus++; // uppercase
			}

		}

		return minus > 0 && mayus > 0 && letters > 0 && numbers > 0;
	}
	public static boolean isExceptionLog(Throwable e) {
		if (e instanceof pss.common.security.LoginException)
			return false;
		if (e instanceof JExceptionRunAction)
			return false;
		if (e instanceof JExceptionAndRefresh) {
			return isExceptionLog(((JExceptionAndRefresh) e).getError());
		}

		String clase = e.getClass().getName();
		if (clase.indexOf("BusinessException") != -1)
			return false;// clase de emilio
		if (clase.indexOf("StorebookControlledExeption") != -1)
			return false;// clase de emilio

		return true;
	}
	public static String formatFileSize(long size) {
		String hrSize = null;

		double b = size;
		double k = size / 1024.0;
		double m = ((size / 1024.0) / 1024.0);
		double g = (((size / 1024.0) / 1024.0) / 1024.0);
		double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

		DecimalFormat dec = new DecimalFormat("0.00");

		if (t > 1) {
			hrSize = dec.format(t).concat(" TB");
		} else if (g > 1) {
			hrSize = dec.format(g).concat(" GB");
		} else if (m > 1) {
			hrSize = dec.format(m).concat(" MB");
		} else if (k > 1) {
			hrSize = dec.format(k).concat(" KB");
		} else {
			hrSize = dec.format(b).concat(" Bytes");
		}

		return hrSize;
	}

	public static String readFileAsString(File f) throws java.io.IOException {
		StringBuffer fileData = new StringBuffer((int) f.length());
		BufferedReader reader = new BufferedReader(new FileReader(f));
		char[] buf = new char[(int) f.length()];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			fileData.append(buf, 0, numRead);
		}
		reader.close();
		return fileData.toString();
	}

	
	public static String AsciiToHex(String in) {
		StringBuffer out = new StringBuffer("");
		for (byte c : in.getBytes())
			out.append(JTools.CharToHex((char) c));
		return out.toString();
	}
	public static String HexToAscii(String in) {
		return HexStringToBin(in, in.length());
	}
	public static String HexToAscii(byte[] in) {
		StringBuffer out = new StringBuffer("");
		for (byte b : in)
			out.append(((char) (b & 0x00ff)));

		return HexToAscii(out.toString());
	}
	public static String entrecomillar(String v) throws Exception {
		boolean any = false;
		StringBuffer b = new StringBuffer();
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(v, ',');
		while (tk.hasMoreTokens()) {
			String t = tk.nextToken();
			if (any)
				b.append(",");
			b.append("'" + t + "'");
			any = true;
		}
		return b.toString();
	}

	public static String ArrayToString(String[] vector) {
		String out = "";
		for (String c : vector)
			out += (out.equals("") ? "" : ",") + "\"" + c + "\"";
		return out;
	}

	public static boolean isOutAccess(String ip) throws Exception {
		if (ip.startsWith("127"))
			return false;
		String localip = java.net.InetAddress.getLocalHost().getHostAddress();
		if (ip.equals(""))
			return false;
		if (ip.indexOf(".") == -1)
			return false;
		String a = ip.substring(0, ip.indexOf("."));
		String b = localip.substring(0, localip.indexOf("."));
		return !a.equalsIgnoreCase(b);
	}

	/* Convierte el documento PDF a imagen con un tamaño grande */
	public static String convertirPDF2Img(String pathIn, String pdf, String pathOut, String outFile) {
		try {
			PDDocument document = null;
			// se carga el documento
			document = PDDocument.load(new File(pathIn + "/" + pdf));

			PDFRenderer pdfRenderer = new PDFRenderer(document);
			int pageCounter = 0;
			for (PDPage page : document.getPages()) {
				// note that the page number parameter is zero based
				BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 300,
						org.apache.pdfbox.rendering.ImageType.RGB);

				// suffix in filename will be used as the file format
				ImageIOUtil.writeImage(bim, pathOut + "/" + outFile + "-" + (pageCounter++) + ".png", 300);
			}
			document.close();

			/*
			 * //se obtiene el numero de paginas del PDF int numero_paginas =
			 * document.getNumberOfPages(); System.out.println("texto: " +
			 * document.getNumberOfPages()); //Se capturan todas las paginas PDPageTree
			 * pages = document.getDocumentCatalog().getPages(); //un ciclo repetitivo para
			 * crear todas las imagenes int i=0; System.out.println( "creando imagen - " +
			 * i); //se obtiene la pagina "i" de n paginas PDPage page = (PDPage)pages.get(
			 * i ); //se convierte la hoja pdf a imagen y se coloca en memoria BufferedImage
			 * image = page.convertToImage(); // se escribe a imagen en disco
			 * 
			 * document.close(); ImageIO.write(image, "png", new File( pathOut +"/"+
			 * outFile)); System.out.println( "imagen [" + i + "] creada");
			 * 
			 * document.close();//cerramos el pdf
			 */
			return outFile;
		} catch (IOException ex) {
			PssLogger.logError(ex);
			return null;
		}
	}

	public static String formatDouble(double num) {
		return formatDouble(num, "###,###,##0.00;-###,###,##0.00");
	}

	public static String formatDouble(double num, String format) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat(format, otherSymbols);

		return df.format(num);
	}

	public static String quitarExcluidas(String s, String[] excluidas, long palabraMaxTama) {
		if (excluidas == null)
			return s;
		StringTokenizer ssw = new StringTokenizer(s, ".,;\"'º()[]+-=_,- ");
		String sw[] = new String[100];
		int ls = 0, lt = 0;
		while (ssw.hasMoreTokens()) {
			String word = ssw.nextToken();
			sw[ls++] = word;
		}
		sw[ls] = null;

		String out = "";
		for (String word : sw) {
			boolean find = false;
			if (word == null)
				break;
			if (word.length() <= palabraMaxTama)
				find = true;
			else {
				for (String excl : excluidas) {
					if (word.equals(excl)) {
						find = true;
						break;
					}
				}
			}
			if (!find)
				out += (out.equals("") ? "" : " ") + word;
		}
		return out;
	}

	public static double editDistanceWords2(String s, String t, double distance) {
		StringTokenizer ssw = new StringTokenizer(s, ".,;\"'º()[]+-=_,- ");
		StringTokenizer stw = new StringTokenizer(t, ".,;\"'º()[]+-=_,- ");
		String sw[] = new String[100];
		String tw[] = new String[100];
		int ls = 0, lt = 0;
		while (ssw.hasMoreTokens()) {
			String word = ssw.nextToken();
			sw[ls++] = word;
		}
		sw[ls] = null;
		while (stw.hasMoreTokens()) {
			String word = stw.nextToken();
			tw[lt++] = word;
		}
		tw[lt] = null;

		double cantWords = Math.max(lt, ls);

		int m = ls;
		int n = lt;
		double[][] d = new double[n + 1][m + 1];

		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				d[j][i] = JTools.editDistance(sw[i], tw[j]);
			}
		}
		double tot = 0;
		if (n >= m) {
			for (int j = 0; j < n; j++) {
				for (int i = 0; i < m; i++) {
					d[j][m] = Math.max(d[j][i], d[j][m]);
				}
			}
			for (int j = 0; j < n; j++) {
				tot += d[j][m];
			}

		} else {
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					d[n][i] = Math.max(d[j][i], d[n][i]);
				}
			}
			for (int i = 0; i < m; i++) {
				tot += d[n][i];
			}
		}
		return tot / cantWords;
	}

	public static double editDistanceWords(String s, String t, double distance) {
		StringTokenizer ssw = new StringTokenizer(s, ".,;\"'º()[]+-=_,- ");
		StringTokenizer stw = new StringTokenizer(t, ".,;\"'º()[]+-=_,- ");
		String sw[] = new String[100];
		String tw[] = new String[100];
		int ls = 0, lt = 0;
		while (ssw.hasMoreTokens()) {
			String word = ssw.nextToken();
			sw[ls++] = word;
		}
		sw[ls] = null;
		while (stw.hasMoreTokens()) {
			String word = stw.nextToken();
			tw[lt++] = word;
		}
		tw[lt] = null;

		double cantWords = Math.max(lt, ls);

		int m = ls;
		int n = lt;
		int[][] d = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			d[i][0] = i;
		}
		for (int j = 0; j <= n; j++) {
			d[0][j] = j;
		}
		for (int j = 1; j <= n; j++) {
			for (int i = 1; i <= m; i++) {
				if (JTools.editDistance(sw[i - 1], tw[j - 1]) > distance) {
					d[i][j] = d[i - 1][j - 1];
				} else {
					d[i][j] = min((d[i - 1][j] + 1), (d[i][j - 1] + 1), (d[i - 1][j - 1] + 1));
				}
			}
		}
		return (100 - (((d[m][n]) * 100) / cantWords)) / 100;
	}

	// redondeo y quita de notacion cientifica
	public static String toPrinteableNumber(double num, int dec) throws Exception {
		return new JFloat(num).setPrec(dec).toFormattedString();
	}

	public static String toPrinteableNumberSinMiles(double num, int dec) throws Exception {

		String s = new JFloat(num).setPrec(dec <= 0 ? 2 : dec).toFormattedString();
		int comas = 0;
		int puntos = 0;
		for (int j = 0; j < s.length(); j++) {
			if (s.charAt(j) == '.')
				puntos++;
			if (s.charAt(j) == ',')
				comas++;
		}
		int posPunto = s.lastIndexOf(".");
		int posComa = s.lastIndexOf(",");
		if (comas > 1)
			s = JTools.replace(s, ",", "");
		if (puntos > 1)
			s = JTools.replace(s, ".", "");
		if (posPunto > posComa) {
			return JTools.replace(s, ",", "");
		}
		if (dec == 0) {
			s = new JFloat(num).setPrec(0).toFormattedString();
			s = JTools.replace(s, ".", "");
			s = JTools.replace(s, ",", "");
		}

		return JTools.replace(JTools.replace(s, ".", ""), ",", ".");

	}

	public static double getProximity(CharSequence cSeq1, CharSequence cSeq2, double mWeightThreshold, int mNumChars) {
		int len1 = cSeq1.length();
		int len2 = cSeq2.length();
		if (len1 == 0)
			return len2 == 0 ? 1.0 : 0.0;

		int searchRange = Math.max(0, Math.max(len1, len2) / 2 - 1);

		boolean[] matched1 = new boolean[len1];
		Arrays.fill(matched1, false);
		boolean[] matched2 = new boolean[len2];
		Arrays.fill(matched2, false);

		int numCommon = 0;
		for (int i = 0; i < len1; ++i) {
			int start = Math.max(0, i - searchRange);
			int end = Math.min(i + searchRange + 1, len2);
			for (int j = start; j < end; ++j) {
				if (matched2[j])
					continue;
				if (cSeq1.charAt(i) != cSeq2.charAt(j))
					continue;
				matched1[i] = true;
				matched2[j] = true;
				++numCommon;
				break;
			}
		}
		if (numCommon == 0)
			return 0.0;

		int numHalfTransposed = 0;
		int j = 0;
		for (int i = 0; i < len1; ++i) {
			if (!matched1[i])
				continue;
			while (!matched2[j])
				++j;
			if (cSeq1.charAt(i) != cSeq2.charAt(j))
				++numHalfTransposed;
			++j;
		}
		// System.out.println("numHalfTransposed=" + numHalfTransposed);
		int numTransposed = numHalfTransposed / 2;

		// System.out.println("numCommon=" + numCommon
		// + " numTransposed=" + numTransposed);
		double numCommonD = numCommon;
		double weight = (numCommonD / len1 + numCommonD / len2 + (numCommon - numTransposed) / numCommonD) / 3.0;

		if (weight <= mWeightThreshold)
			return weight;
		int max = Math.min(mNumChars, Math.min(cSeq1.length(), cSeq2.length()));
		int pos = 0;
		while (pos < max && cSeq1.charAt(pos) == cSeq2.charAt(pos))
			++pos;
		if (pos == 0)
			return weight;
		return weight + 0.1 * pos * (1.0 - weight);

	}

	public static long checkSimilitud(String newName, String oldName, String[] excluidas) throws Exception {
		String s1 = JTools.quitarExcluidas(newName.toLowerCase(), excluidas, 1);
		String s2 = JTools.quitarExcluidas(oldName.toLowerCase(), excluidas, 1);
		if (s1.equals("") && s2.equals(""))
			return 10000;
		String s3 = newName.toLowerCase().replaceAll(" ", "");
		String s4 = oldName.toLowerCase().replaceAll(" ", "");
		double m1 = (JTools.editDistance(s1, s2));
		double m2 = (JTools.editDistanceWords(s1, s2, 0.7));
		double m3 = (JTools.getProximity(s1, s2, 0.7, 4));
		double m4 = (JTools.getProximity(s3, s4, 0.7, 4));
		double p = (m1 + m2 + Math.max(m3, m4)) / 3;

		return (long) (p * 10000);
	}

	// checkea palabras parecidas sin importar el orden.
	public static long checkSimilitudWords(String newName, String oldName, String[] excluidas) throws Exception {
		String s1 = JTools.quitarExcluidas(newName.toLowerCase(), excluidas, 1);
		String s2 = JTools.quitarExcluidas(oldName.toLowerCase(), excluidas, 1);
		if (s1.equals("") && s2.equals(""))
			return 10000;
		double m2 = (JTools.editDistanceWords2(s1, s2, 0.7));

		return (long) (m2 * 10000);
	}

	public static String[] getWords(String s) {
		StringTokenizer ssw = new StringTokenizer(s, ".,;\"'º()[]+-=_,- ");
		String sw[] = new String[ssw.countTokens()];
		int ls = 0;
		while (ssw.hasMoreTokens()) {
			String word = ssw.nextToken();
			sw[ls++] = word;
		}
		sw[ls] = null;
		return sw;
	}

	public static double editDistance(String s, String t) {
		int m = s.length();
		int n = t.length();
		int[][] d = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			d[i][0] = i;
		}
		for (int j = 0; j <= n; j++) {
			d[0][j] = j;
		}
		for (int j = 1; j <= n; j++) {
			for (int i = 1; i <= m; i++) {
				if (s.charAt(i - 1) == t.charAt(j - 1)) {
					d[i][j] = d[i - 1][j - 1];
				} else {
					d[i][j] = min((d[i - 1][j] + 1), (d[i][j - 1] + 1), (d[i - 1][j - 1] + 1));
				}
			}
		}
		double cantWords = Math.max(m, n);

		return (100 - (((d[m][n]) * 100) / cantWords)) / 100;
	}

	public static int min(int a, int b, int c) {
		return (Math.min(Math.min(a, b), c));
	}

	public static String convertToFraction(float d) {
		long i = (long) Math.floor(d);
		float numerator = d - i;
		String frac = "" + numerator;
		frac = frac.substring(frac.indexOf('.') + 1);
		numerator = Float.parseFloat(frac);
		int power = frac.length();
		float denominator = (float) Math.pow(10, power);
		int gcd = findGCD((int) numerator, (int) denominator);
		numerator /= gcd;
		denominator /= gcd;
		return (i != 0) ? i + " " : "" + (long) numerator + "/" + (long) denominator;
	}

	public static int findGCD(int a, int b) {
		int r;
		if (a < b) {
			r = a;
			a = b;
			b = r;
		}
		if (b == 0)
			return a;
		return findGCD(b, a % b);
	}
	public static double rd(double zValue) {
		zValue = zValue * 1000000;
		zValue = (zValue + 0.9);
		zValue = Math.floor(zValue);
		zValue = zValue / 1000000;
		return zValue;
	}
	public static String ColorToString(Color c) {
		return JTools.LPad(JTools.CharToHex((char) c.getRed()), 2, "0")
				+ JTools.LPad(JTools.CharToHex((char) c.getGreen()), 2, "0")
				+ JTools.LPad(JTools.CharToHex((char) c.getBlue()), 2, "0");
	}

	public static Color StringToColor(String c) {
		int red = (int) JTools.hexaToDecimal(c.substring(0, 2));
		int green = (int) JTools.hexaToDecimal(c.substring(2, 4));
		int blue = (int) JTools.hexaToDecimal(c.substring(4, 6));
		return new Color(red, green, blue);
	}

	public static double rd(double zValue, int zRnd) {
		double signo = Math.signum(zValue);
		zValue = JTools.rd(zValue * Math.pow(10, zRnd));
		zValue = (zValue + (signo * 0.5d));
		zValue = Math.floor(Math.abs(zValue)) * signo;
		zValue = zValue / Math.pow(10, zRnd);
		return zValue;
	}

	public static double forceRd(double value, int decimals) {
		// a.- for integer results, use integer ceiling
		if (decimals == 0) {
			return Math.ceil(value);
		}
		// b.- for no integer results...

		double result = JTools.rd(value, decimals);
		// 3.- if the result is less than the original value, let it
		// incremented in 1 at the low meaning digit

		if (result < value) {
			double toAdd = 1 / Math.pow(10, decimals);
			result = JTools.rd(result + toAdd, decimals);
		}
		// 4.- return the sum of the integer part and the rounded
		// decimal part
		return result;
	}

	public static boolean isMultiplo(double value, double mult) throws Exception {
		return (JTools.rd(value / mult) == JTools.rd((int) (value / mult)));
	}

//	public static void main(String[] args) {
//		try {
//			 int k =0;
//			 JTools.sendFile("c:\\proveido.pdf","http://127.0.0.1:8080/sitita/do-ftp");
////			buildCaptcha("c:\\dev\\test.png", 100, 50);
////				for(int i=10000;i<99999;i++) {
////					String num= ""+i;
////					boolean error = false;
////					boolean uno = false;
////					boolean dos = false;
////					boolean tres = false;
////					boolean cuatro = false;
////					for (int j=0;j<5;j++) {
////						if (num.charAt(j)-'0'>=5) error=true;
////						if (num.charAt(j)-'0'==1) uno=true;
////						if (num.charAt(j)-'0'==2) dos=true;
////						if (num.charAt(j)-'0'==3) tres=true;
////						if (num.charAt(j)-'0'==4) cuatro=true;
////					}
////					if (error) continue;
////					if (!uno) continue;
////					if (!dos) continue;
////					if (!tres) continue;
////					if (!cuatro) continue;
////
////
////					if (i%6!=0) continue;
////					System.out.println(""+i);
////					k++;
////				}
////				System.out.println("total: "+k);
////			
////			
////			@SuppressWarnings("unused")
////			String mac=JTools.getMacAddress();
//		} catch (Throwable e) {
//			PssLogger.logError(e);
//			System.exit(1);
//		}
//	}

	public static double rdCeiling(double value, int decimals) {
		double pot = Math.pow(10, decimals);
		value = value * pot;
		value = (value + 0.9);
		value = Math.floor(value);
		value = value / pot;
		return value;
	}

	public static double tr(double value, int decimals) {
		int pot = (int) Math.pow(10, decimals);
		value = rd(value * pot);
		value = Math.floor(value);
		value = value / pot;
		return value;
	}

	// -------------------------------------------------------------------------//
	// memcpy
	// -------------------------------------------------------------------------//
	public static void MemCopy(char zDest[], char zOrig[], int zLen) {
		int iIdx;
		for (iIdx = 0; iIdx < zLen; iIdx++) {
			zDest[iIdx] = zOrig[iIdx];
		}
	}

	public static String BooleanToStr(boolean zValue) {
		return zValue ? "S" : "N";
	}

	public static boolean StrToBoolean(String zValue) {
		if (zValue == "S" || zValue == "Y")
			return true;
		else
			return false;
	}

	public static boolean ifHost(String zHost) throws Exception {
		String host = InetAddress.getLocalHost().getHostName();
		return host.equals(zHost.trim());
	}

	public static String GetHost() throws Exception {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static String GetHostName() throws Exception {
		return InetAddress.getLocalHost().getHostName();
	}

	public static boolean ifHostOk(String zHost) throws Exception {
		if (zHost == null)
			return false;

		if (zHost.trim().equalsIgnoreCase(InetAddress.getLocalHost().getHostName().trim()))
			return true;

		return false;
	}

	public static String cleanFileName(String file) throws Exception {
		file = file.substring(file.lastIndexOf("/") + 1);
		int idx1 = file.indexOf(".");
		int idx2 = file.substring(idx1 + 1).indexOf(".");
		if (idx2 == -1)
			return file;
		return file.substring(0, idx1).concat(file.substring(idx1 + idx2 + 1));
	}

	public static String trimHTML(String html) {
		html = html.replaceAll("&nbsp;", " ");
		html = html.replaceAll("<br>", " ");
		StringBuffer out = new StringBuffer();
		int in = 0;
		for (int i = 0; i < html.length(); i++) {
			if (html.charAt(i) == '<')
				in++;
			else if (html.charAt(i) == '>')
				in--;
			else if (in == 0)
				out.append(html.charAt(i));
		}
		return out.toString();
//  	return JTools.replace(out.toString(),"&nbsp;"," ");
	}

	public static String decodeIsoFull(String text) throws Exception {
		text = text.replaceAll(",", "%2Fc%2F");
		text = text.replaceAll("\\+", "%2B");
		return JTools.decodeIso(text);
	}
	public static String decodeIso(String text) throws Exception {
		return (new URLCodec()).decode(text, "ISO-8859-1");
	}

	public static String encodeIsoFull(String text) throws Exception {
		String s = JTools.encodeIso(text);
		s = s.replaceAll("\\+", " ");
		s = s.replaceAll("%2B", "\\+");
		return s;
	}
	public static String encodeIso(String text) throws Exception {
		return (new URLCodec()).encode(text, "ISO-8859-1");
	}

	public static String findNameFile(String absFile) throws Exception {
		return absFile.substring(absFile.lastIndexOf('/') + 1);
	}

	public static String scapeFileName(String name) throws Exception {
		name = name.replace("%3A", ":");
		name = name.replace("%20", " ");
		name = name.replace("%28", "(");
		name = name.replace("%29", ")");
		name = name.replace("%E1", "á");
		name = name.replace("%E9", "é");
		name = name.replace("%ED", "í");
		name = name.replace("%F3", "ó");
		name = name.replace("%FA", "ú");
		name = name.replace("%3F", "?");
		name = name.replace("%40", "@");
		return name;
	}

	public static String[] bandToTracks(String band) throws Exception {
		String[] tracks = new String[2];
		@SuppressWarnings("unused")
		int lengthTotal = band.length();
		int begTrack1 = 5;
		int lengthTrack1 = band.charAt(3) - 2;
		int lengthTrack2 = band.charAt(begTrack1 + lengthTrack1);
		tracks[0] = band.substring(begTrack1, begTrack1 + lengthTrack1);
		tracks[1] = band.substring(7 + lengthTrack1, 7 + lengthTrack1 + lengthTrack2 - 2);
		return tracks;
	}

	public static String ReplaceToken(String zStr, String zTokori, String zTokdest) {
		return ReplaceToken(zStr, zTokori.charAt(0), zTokdest.charAt(0));
	}

	public static String ReplaceToken(String zStr, char zTokori, char zTokdest) {
		int i;
		int ilen = zStr.length();
		StringBuffer dest = new StringBuffer(zStr);

		for (i = 1; i < ilen; i++) {
			if (dest.charAt(i) == zTokori) {
				dest.setCharAt(i, zTokdest);
			}
		}
		return dest.toString();
	}

	public static String subStr(String zStr, long zPos, long zLon) {
		if (zLon < 0)
			return zStr;
		if (zPos < 0)
			return zStr;
		if (zStr.length() < (zPos + zLon))
			return zStr.substring((int) zPos);
		if (zStr.length() < zPos)
			return "";
		return zStr.substring((int) zPos, (int) (zPos + zLon));
	}

	public static String SubStr(String zStr, long zPos, long zLon) {
		return zStr.substring((int) zPos, (int) (zPos + zLon));
	}

	public static String SubStr(String zStr, int zPos, int zLon) {
		return zStr.substring(zPos, zPos + zLon);
	}

	public static int FindInStr(String zStr, String zTok) {
		int i;
		int iPos = -1;
		int ilen = zStr.length() - zTok.length();
		int iTokLen = zTok.length();
		@SuppressWarnings("unused")
		StringBuffer dest = new StringBuffer(zStr);

		for (i = 0; i <= ilen; i++) {
			String substr = zStr.substring(i, i + iTokLen);
			if (substr.equals(zTok)) {
				iPos = i;
			}
		}
		return iPos;
	}

	public static String StringField(String str, String car, int pos) {
		int iTocken;
		for (int iCont = 0; iCont < pos; iCont++) {
			iTocken = str.indexOf(car);
			if (iTocken >= 0) {
				str = str.substring(iTocken + 1);
			}
		}
		iTocken = str.indexOf(car);
		if (iTocken >= 0) {
			return str.substring(0, iTocken);
		} else {
			return "";
		}
	}

	public static int LenTo(int zLen, int zMult) {
		int nAux = zLen % zMult;

		if (nAux != 0) {
			nAux = zMult - nAux;
		}
		return zLen + nAux;
	}

	public static String LCutStr(String zDest, int zOriLen) {
		int iLen = zDest.length();
		int idiff = iLen - zOriLen;

		StringBuffer dest = new StringBuffer(zOriLen);

		if (idiff > 0) {

			dest.append(zDest.substring(idiff, zOriLen + idiff));
		}
		return dest.toString();
	}

	public static String RCutStr(String zDest, int zOriLen) {
		int idiff = zDest.length() - zOriLen;
		StringBuffer dest = new StringBuffer(zOriLen);

		if (idiff > 0) {
			dest.append(zDest.substring(0, zOriLen));
		}
		return dest.toString();
	}

	public static String RPad(String zStr, long zLon, String zRelleno) {
		return RPad(zStr, Integer.parseInt(String.valueOf(zLon)), zRelleno);
	}
	public static String RPad(String zStr, int zLon, String zRelleno) {
		return JTools.RPad(zStr, zLon, zRelleno, false);
	}

	public static String RPad(String zStr, int zLon, String zRelleno, boolean trunc) {
		if (zStr == null)
			zStr = "";
		int iLong = zLon - zStr.length();
		int iLongR = zRelleno.length();
		int i;
		int iRest;

		if (iLong < 0)
			return (trunc ? zStr.substring(0, zLon) : zStr);
		StringBuffer aux = new StringBuffer(iLong);
		StringBuffer dest = new StringBuffer(zStr);

		for (i = 0; i < iLong; i++) {
			iRest = i % iLongR;
			aux.append(zRelleno.charAt(iRest));
		}
		dest.append(aux);
		return dest.toString();
	}

	public static String LPad(String zStr, int zLon, String zRelleno) {
		return JTools.LPad(zStr, zLon, zRelleno, false);
	}

	public static String LPad(String zStr, long zLon, String zRelleno) {
		return LPad(zStr, Integer.parseInt(String.valueOf(zLon)), zRelleno, false);
	}

	public static String LPad(String zStr, int zLon, String zRelleno, boolean trunc) {
		if (zStr == null)
			zStr = "";
		int iLong = zLon - zStr.length();
		int iLongR = zRelleno.length();
		int i;
		int iRest;

		StringBuffer aux = new StringBuffer(zLon);

		for (i = 0; i < iLong; i++) {
			iRest = i % iLongR;
			aux.append(zRelleno.charAt(iRest));
		}
		aux.append(zStr);
		return (trunc ? aux.toString().substring(0, zLon) : aux.toString());
	}

	public static String InsStr(String zStrOrig, String zStrNew, int zPos) {
		StringBuffer aux = new StringBuffer(zStrOrig);
		if (zPos <= zStrOrig.length()) {

			aux.insert(zPos, zStrNew);
		}
		return aux.toString();
	}

	public static String encryptMessage(String msg) throws Exception {
		char zResponse[] = msg.toCharArray();
		int reminder = zResponse.length % 8;
		byte ans[] = JTools.charArrayToByteArray(zResponse);
		if (reminder != 0) {
			byte rest[] = new byte[8 - reminder];
			java.util.Arrays.fill(rest, (byte) 0x30);
			ans = JTools.concat(ans, rest);
		}
		byte dec[] = TripleDES.encrypt(ans);
		char decChar[] = JTools.byteToChar(dec);
		return JTools.BinToHexString(new String(decChar), decChar.length);
	}

	public static String decryptMessage(String msg) throws Exception {
		char in[] = HexStringToBin(msg, msg.length()).toCharArray();

		byte dec[] = TripleDES.decrypt(JTools.charArrayToByteArray(in));

		char outChar[] = JTools.byteToChar(dec);

		return new String(outChar);

	}


	public static String invert(String zStr) {
		if (zStr == null)
			zStr = "";
		String in = JTools.replaceForeignChars(zStr);
		String out = "";

		for (int i = 0; i < in.length(); i++) {
			if (isNumber("" + in.charAt(i)))
				out += String.valueOf(Character.toChars((((int) '9') - (int) (in.charAt(i))) + ((int) '0')));
			else
				out += String.valueOf(Character.toChars((((int) 'Z') - (int) (in.charAt(i))) + ((int) 'a')));
		}

		return out;
	}
	public static String Replace(String zStrOrig, String zStrNew, int zPos) {
		int iNewLon = zStrNew.length();
		int iOriLon = zStrOrig.length();
		StringBuffer aux = new StringBuffer(zStrOrig);
		int i;

		if (iOriLon >= zPos + iNewLon) {
			for (i = zPos; i < iNewLon + zPos; i++) {
				aux.setCharAt(i, zStrNew.charAt(i - zPos));
			}
		}
		return aux.toString();
	}


	public static int CalculoCRC(String zNroTarj) {
		int iIdx;
		int iCRCaux;
		int iCRC = 0;
		int iMult = 2;
		int iLonTarj = zNroTarj.length();
		String aux = new String();
		@SuppressWarnings("unused")
		Integer oInt;
		int iAux = 0;

		for (iIdx = iLonTarj - 2; iIdx >= 0; iIdx--) {
			aux = zNroTarj.charAt(iIdx) + "";
			iCRCaux = Integer.parseInt(aux);

			iAux = iCRCaux * iMult;
			if (iAux > 9) {
				String saux2 = Integer.toString(iAux);
				iAux = saux2.charAt(0) - 0x30 + saux2.charAt(1) - 0x30;
			}
			iCRC += iAux;
			if (iMult == 2) {
				iMult = 1;
			} else {
				iMult = 2;
			}

		}
		iCRC %= 10;

		return (iCRC == 0 ? 0 : (10 - iCRC));
	}

	public static String DelSubStr(String zStr, int zPos, int zLon) {
		StringBuffer aux = new StringBuffer();
		int iLonStr = zStr.length();
		int iPosFinal = zPos + zLon;

		if (zPos < iLonStr) {
			aux.append(zStr.substring(0, zPos - 1));
			if (iLonStr > iPosFinal) {
				aux.append(zStr.substring(zPos + zLon, iLonStr));
			}
		}
		return aux.toString();
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * @description:
	 * @deprecated use {@link #isNumber(String)}
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	@Deprecated
	public static boolean IsNumerico(String zStr, boolean bException) throws Exception {
		int i;
		boolean bResult = true;
		byte[] Byte = zStr.getBytes();

		try {
			for (i = 0; i < Byte.length; i++) {
				if (!(Byte[i] >= 48 && Byte[i] <= 57)) {
					if (!(Byte[i] == 43 || Byte[i] == 45 || Byte[i] == 46)) {
						bResult = false;
						break;
					}
				}
			}
		} catch (Exception E) {

		}
		return bResult;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * @description: Returns true if the String is a number else returns false
	 * @param: String
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public static boolean isNumber(String zMaybeANumber) {
		return isNumber(zMaybeANumber, true);
	}

	public static boolean isNumberPure(String zMaybeANumber) {
		return isNumber(zMaybeANumber, false);
	}

	public static boolean isIntegerNumber(String number) {
		int len = number.length();
		char[] charArray = new char[len];
		System.arraycopy(number.toCharArray(), 0, charArray, 0, len);
		for (int i = 0; i < len; i++) {
			if (charArray[i] < 0x30 || charArray[i] > 0x39) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(String zMaybeANumber, boolean zPermitirSignos) {
		if (zMaybeANumber == null)
			return false;
		int i;
		boolean bResult = true;
		byte[] bByte = zMaybeANumber.getBytes();
		if (zMaybeANumber.length() == 1 && (bByte[0] == 43 || bByte[0] == 45 || bByte[0] == 46)) {
			return false;
		}
		for (i = 0; i < bByte.length; i++) {
			if (!((bByte[i] >= 48 && bByte[i] <= 57) || bByte[i] == 46)) {
				if (!(zPermitirSignos && (bByte[i] == 43 || bByte[i] == 45 || bByte[i] == 'E'))) {
					bResult = false;
					break;
				}
			}
		}
		return bResult;
	}

	/**
	 * <p>
	 * Aks if the character param is a valid digit
	 * </p>
	 * <p>
	 * Includes:
	 * </p>
	 * 
	 * <pre>
	 * NUMBERS,
	 *  LOWERCASE LETTERS,
	 *  UPPERCASE LETTERS,
	 *  COMMON SYMBOLS (&quot;$%&amp;/())
	 *  WHITESPACE
	 * </pre>
	 * 
	 * @author PSS
	 * @param zChar the character to validate
	 */
	public static boolean isValidDigit(char zChar) {
		return (zChar >= 0x21 && zChar <= 0x7A) || zChar == 0x20 || zChar == 0xD1 || zChar == 0xF1; // mayus
		// ||
		// minuscula
	}

	public static boolean isValidCode(int zKeyCode) {
		return (zKeyCode != 222); // simbolo '
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * @description: Returns true if the Character is a number else returns false
	 * @param: char
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public static boolean isNumber(char zMaybeANumber) {
		if (zMaybeANumber > 0x2F && zMaybeANumber < 0x3A)
			return true;
		return false;
	}
	public static boolean IsHexa(String zStr) {
		try {
			return IsHexa(zStr, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean IsHexa(String zStr, boolean bException) throws Exception {
		int iIndex;
		int iLonStr = zStr.length();
		boolean bResult = true;
		try {
			for (iIndex = 0; iIndex < iLonStr; iIndex++) {
				if (zStr.charAt(iIndex) != '0' && zStr.charAt(iIndex) != '1' && zStr.charAt(iIndex) != '2'
						&& zStr.charAt(iIndex) != '3' && zStr.charAt(iIndex) != '4' && zStr.charAt(iIndex) != '5'
						&& zStr.charAt(iIndex) != '6' && zStr.charAt(iIndex) != '7' && zStr.charAt(iIndex) != '8'
						&& zStr.charAt(iIndex) != '9' && zStr.charAt(iIndex) != 'A' && zStr.charAt(iIndex) != 'B'
						&& zStr.charAt(iIndex) != 'C' && zStr.charAt(iIndex) != 'D' && zStr.charAt(iIndex) != 'E'
						&& zStr.charAt(iIndex) != 'F') {

					bResult = false;
				}
			}
		} catch (Exception E) {
			bResult = false;
		}
		if (bException && !bResult) {
			JExcepcion.SendError("Dato hexa inválido: " + zStr);
		}
		return bResult;
	}

	public static boolean IsBinario(String zStr, boolean bException) throws Exception {
		int i;
		boolean bResult = true;
		int iLonStr = zStr.length();

		try {
			for (i = 0; i <= iLonStr; i++) {
				if (zStr.charAt(i) != '0' && zStr.charAt(i) != '1') {
					bResult = false;
				}
			}
		} catch (Exception E) {
			bResult = false;
		}
		if (bException && !bResult) {
			JExcepcion.SendError("Dato binario inválido: " + zStr);
		}
		return bResult;
	}

	public static boolean IsAlfaNumerico(String zStr, boolean bException) throws Exception {
		int i;
		boolean bResult = true;
		byte[] Byte = zStr.getBytes();
		int iLonB = zStr.length();

		try {
			for (i = 0; i < iLonB; i++) {
				if (Byte[i] < 128) {

					if (!((Byte[i] >= 48 && Byte[i] <= 57) || (Byte[i] >= 65 && Byte[i] <= 90)
							|| (Byte[i] >= 97 && Byte[i] <= 122) || (Byte[i] == 32))) {

						bResult = false;
						break;
					}
				}
			}
			return bResult;
		} catch (Exception E) {
			return bResult;
		}
	}

	public static boolean isOnlyLetters(String zStr, boolean bException) throws Exception {
		int i;
		boolean bResult = true;
		byte[] Byte = zStr.getBytes();
		int iLonB = zStr.length();

		try {
			for (i = 0; i < iLonB; i++) {
				if (Byte[i] < 128) {

					if (!((Byte[i] >= 65 && Byte[i] <= 90) || (Byte[i] >= 97 && Byte[i] <= 122) || (Byte[i] == 32))) {

						bResult = false;
						break;
					}
				}
			}
			return bResult;
		} catch (Exception E) {
			return bResult;
		}
	}
	public static boolean isOnlyLettersWithinSpaces(String zStr, boolean bException) throws Exception {
		int i;
		boolean bResult = true;
		byte[] Byte = zStr.getBytes();
		int iLonB = zStr.length();

		try {
			for (i = 0; i < iLonB; i++) {
				if (Byte[i] < 128) {

					if (!(/* (Byte[i]>='0'&&Byte[i]<='9')|| */(Byte[i] >= 65 && Byte[i] <= 90)
							|| (Byte[i] >= 97 && Byte[i] <= 122))) {

						bResult = false;
						break;
					}
				}
			}
			return bResult;
		} catch (Exception E) {
			return bResult;
		}
	}
	public static String FirstLetterUpper(String zStr) {
		String sIni = zStr.substring(0, 1).toUpperCase();
		String sFin = zStr.substring(1, zStr.length()).toLowerCase();
		return sIni + sFin;
	}

	// --------------------------------------------------------------------------
	// //
	// Verifico si la fecha es valida
	// --------------------------------------------------------------------------
	// //
	public static boolean IsDateTime(String zStr, String zFormato, boolean zException) throws Exception {
		boolean bRc;

		// ----------------------------------
		// Inicializo las variables locales
		// ----------------------------------
		bRc = true;

		try {
			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern(zFormato);
			df.setLenient(false);
			df.parse(zStr);
		} catch (Exception e) {
			bRc = false;
		}

		// -------------------------------------------
		// Si el cliente espera exception la disparo
		// -------------------------------------------
		if (zException && !bRc)
			JExcepcion.SendError("Formato de fecha inválida: " + zStr);

		// --------------------
		// Seteo la respuesta
		// --------------------
		return bRc;

	}

	// --------------------------------------------------
	// Compara dos fechas sin tener en cuenta la hora
	// ---------------------------------------------------
	public static boolean CompareDate(Date f1, Date f2) throws Exception {
		Calendar oCalendar = Calendar.getInstance();
		oCalendar.setTime(f1);
		Calendar oCalendar2 = Calendar.getInstance();
		oCalendar2.setTime(f2);
		return oCalendar.get(Calendar.DAY_OF_YEAR) == oCalendar2.get(Calendar.DAY_OF_YEAR)
				&& oCalendar.get(Calendar.YEAR) == oCalendar2.get(Calendar.YEAR);
	}

	//
	// Por cada byte se devuelven dos bytes representando el valor
	// hexa de cada nibble en un byte ascii ej: el byte 8A se representa
	// como dos bytes 38 40
	//
	public static String BinaryToNibble(byte[] zByte) throws Exception {
		// byte BINTOCHAR[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		// 'A', 'B', 'C', 'D', 'E', 'F' };
		@SuppressWarnings("unused")
		int len = zByte.length;
		String sByte = "";
		int n1, n2;
		// CMalloc obj = new CMalloc(len * 2);

		for (int i = 0; i < zByte.length; i++) {
			n1 = ((zByte[i] >> 4) & 0x0f);
			n2 = (zByte[i] & 0x0f);
			sByte += String.valueOf(Character.forDigit(n1, 16)) + String.valueOf(Character.forDigit(n2, 16));
			// obj.setByte((i*2),BINTOCHAR[n1]);
			// obj.setByte((i*2)+1,BINTOCHAR[n2]);
		}
		// sByte = obj.getString(0);
		// obj.free();
		return sByte;

	}

	public static byte[] NibbleToBinary(String zStr) throws Exception {
		int len = zStr.length();
		int n1, n2, bint, k = 0;
		byte[] cByte = new byte[len / 2];

		for (int i = 0; i < len; i = i + 2) {
			n1 = Character.digit(zStr.charAt(i), 16);
			n2 = Character.digit(zStr.charAt(i + 1), 16);
			bint = (n1 << 4) | (n2 & 0x0f);
			cByte[k] = (byte) bint;
			k++;
		}
		return cByte;
	}

	// --------------------------------------------------------------------------
	// //
	// Convierto un string Binario a Hexa //
	// --------------------------------------------------------------------------
	// //
	public static String StrBinToStrHex(String zStr) throws Exception {
		String sValorHexa;
		String sValorBin;
		int iIndex;
		int iIndex2;
		int iLen;

		sValorHexa = "";
		iLen = LenTo(zStr.length(), 4);

		for (iIndex = 0; iIndex <= (iLen / 4) - 1; iIndex++) {
			iIndex2 = 1 + (iIndex * 4);

			sValorBin = zStr.substring(iIndex2, iIndex + 4);
			if (sValorBin.equals("0000"))
				sValorHexa += "0";
			else if (sValorBin.equals("0001"))
				sValorHexa += "1";
			else if (sValorBin.equals("0010"))
				sValorHexa += "2";
			else if (sValorBin.equals("0011"))
				sValorHexa += "3";
			else if (sValorBin.equals("0100"))
				sValorHexa += "4";
			else if (sValorBin.equals("0101"))
				sValorHexa += "5";
			else if (sValorBin.equals("0110"))
				sValorHexa += "6";
			else if (sValorBin.equals("0111"))
				sValorHexa += "7";
			else if (sValorBin.equals("1000"))
				sValorHexa += "8";
			else if (sValorBin.equals("1001"))
				sValorHexa += "9";
			else if (sValorBin.equals("1010"))
				sValorHexa += "A";
			else if (sValorBin.equals("1011"))
				sValorHexa += "B";
			else if (sValorBin.equals("1100"))
				sValorHexa += "C";
			else if (sValorBin.equals("1101"))
				sValorHexa += "D";
			else if (sValorBin.equals("1110"))
				sValorHexa += "E";
			else if (sValorBin.equals("1111"))
				sValorHexa += "F";
			else
				JExcepcion.SendError("Valor fuera de rango de un valor hexadecimal");
		}

		return sValorHexa;
	}

	// --------------------------------------------------------------------------
	// //
	// Convierto el valor de los caracteres ASCII de un String a un string en
	// Binario
	// Por cada Byte reibido devuelve 8
	// Ej: Si recibe la cadena "49AB" devuelve la cadena
	// "00110100001110010100000101000010"
	// --------------------------------------------------------------------------
	// //
	public static String StrToBinary(String zStr) {
		int iStrLen = zStr.length();
		int iStrIndex;
		int iByteIndex;
		int iAux;
		char cAux;
		String szRet = "";

		for (iStrIndex = 0; iStrIndex < iStrLen; iStrIndex++) {
			cAux = zStr.charAt(iStrIndex);
			for (iByteIndex = 7; iByteIndex >= 0; iByteIndex--) {
				iAux = 0x01 << iByteIndex;
				szRet = szRet + (((cAux & iAux) / iAux)); // + 0x30);
			} // end for iIndexByte
		} // end for iIndex

		return szRet;
	}

	// --------------------------------------------------------------------------
	// //
	// Convierto de los caracteres ASCII de un String a un string en Binario
	// Por cada Byte reibido devuelve 4
	// Ej: Si recibe la cadena "49AB" devuelve la cadena "0100100110101011"
	// --------------------------------------------------------------------------
	// //
	public static String AsciiToBinary(String zStr) {
		int iStrLen = zStr.length();
		int iIndex;
		String szRet = "";

		for (iIndex = 0; iIndex < iStrLen; iIndex++) {
			switch (zStr.charAt(iIndex)) {
			case '0':
				szRet = szRet + "0000";
				break;
			case '1':
				szRet = szRet + "0001";
				break;
			case '2':
				szRet = szRet + "0010";
				break;
			case '3':
				szRet = szRet + "0011";
				break;
			case '4':
				szRet = szRet + "0100";
				break;
			case '5':
				szRet = szRet + "0101";
				break;
			case '6':
				szRet = szRet + "0110";
				break;
			case '7':
				szRet = szRet + "0111";
				break;
			case '8':
				szRet = szRet + "1000";
				break;
			case '9':
				szRet = szRet + "1001";
				break;
			case 'a':
			case 'A':
				szRet = szRet + "1010";
				break;
			case 'b':
			case 'B':
				szRet = szRet + "1011";
				break;
			case 'c':
			case 'C':
				szRet = szRet + "1100";
				break;
			case 'd':
			case 'D':
				szRet = szRet + "1101";
				break;
			case 'e':
			case 'E':
				szRet = szRet + "1110";
				break;
			case 'f':
			case 'F':
				szRet = szRet + "1111";
				break;
			default:
				return null;
			} // end swtich
		} // end for

		return szRet;
	}

	public static boolean StringToBoolean(String zBol) {

		if (zBol == null)
			return false;
		if (zBol.equalsIgnoreCase("S"))
			return true;
		return false;

	}

	private static Des getDes() {
		if (stDes == null)
			stDes = new Des(Des.DEFAULT_KEY);
		return stDes;
	}

	public static String PasswordToString(String zPass) {
		return JTools.PasswordToString(Des.DEFAULT_KEY, zPass);
	}

	public static String PasswordToString(String zKey, String zPass) {
		if (zPass == null)
			return "";
		if (zPass.equalsIgnoreCase(""))
			return "";

		if (zKey != null) {
			if (zKey.length() > 16)
				zKey = zKey.substring(0, 16);
		}
		Des des = getDes();
		String dec = des.decrypt(JTools.HexStringToBin(zPass.substring(1), zPass.length() - 1));

		return dec;
	}

	public static String StringToPassword(String zPass) {
		return JTools.StringToPassword(Des.DEFAULT_KEY, zPass);
	}

	public static String StringToPassword(String zKey, String zPass) {

		if (zPass == null)
			return "";
		if (zPass.equalsIgnoreCase(""))
			return "";

		if (zKey != null) {
			if (zKey.length() > 16)
				zKey = zKey.substring(0, 16);
		}

		Des des = getDes();
		String enc = des.encrypt(zPass);
		String sHex = JTools.BinToHexString(enc, enc.length());

		return "*" + sHex;

	}

	public static char vs13(char c) {
		return (char) ((c + 128) % 256);
	}

	public static String StringToHour(String zStr) throws Exception {
		StringBuffer result = new StringBuffer(8);
		int iAux;
		String sAux;

		String value = zStr == null ? "" : zStr.trim();
		int length = value.length();

		// Si nulo retorno el valor equivalente al nulo
		if (length == 0)
			return "";

		if (length > 6)
			JExcepcion.SendError("Error en formateo de hora : Longitud incorrecta");

		value = LPad(value, 6, "0");

		// Valido la hora
		sAux = value.substring(0, 2);
		iAux = Integer.valueOf(sAux).intValue();
		if (iAux < 0 || iAux > 23)
			JExcepcion.SendError("Error en formateo de hora : Valor hora incorrecto");
		result.append(sAux).append(':');

		// Valido los minutos
		sAux = value.substring(2, 4);
		iAux = Integer.valueOf(sAux).intValue();
		if (iAux < 0 || iAux > 59)
			JExcepcion.SendError("Error en formateo de hora : Valor minutos incorrectos");
		result.append(sAux).append(':');

		// Valido los Segundos
		sAux = value.substring(4);
		iAux = Integer.valueOf(sAux).intValue();
		if (iAux < 0 || iAux > 59)
			JExcepcion.SendError("Error en formateo de hora : Valor segundos incorrectos");
		result.append(sAux);

		return result.toString();
	}

	public static String HourToString(String zStr) throws Exception {
		String hora = null;
		int iAux;

		// Si nulo retorno el valor equivalente al nulo
		if (zStr == null)
			return "";

		if (zStr.length() == 5)
			zStr += ":00"; // truchada para agregar los segundos

		if (zStr.length() == 8)
			hora = zStr.substring(0, 2) + zStr.substring(3, 5) + zStr.substring(6);
		else if (zStr.length() == 6 && isIntegerNumber(zStr))
			hora = zStr;
		else if (zStr.length() == 5 && zStr.indexOf(":") == 2)
			hora = zStr.substring(0, 2) + zStr.substring(3, 5) + "00";
		else
			JExcepcion.SendError("Error en obtencion de hora : Longitud incorrecta");

		if (!isIntegerNumber(hora)) {
			JExcepcion.SendError("Error en obtencion de hora : Formato incorrecto");
		}

		// Valido la hora
		iAux = Integer.valueOf(hora.substring(0, 2)).intValue();
		if (iAux < 0 || iAux > 23)
			JExcepcion.SendError("Error en obtencion de hora : Valor hora incorrecto");

		// Valido los minutos
		iAux = Integer.valueOf(hora.substring(2, 4)).intValue();
		if (iAux < 0 || iAux > 59)
			JExcepcion.SendError("Error en obtencion de hora : Valor minutos incorrectos");

		// Valido los Segundos
		iAux = Integer.valueOf(hora.substring(4, 6)).intValue();
		if (iAux < 0 || iAux > 59)
			JExcepcion.SendError("Error en obtencion de hora : Valor segundos incorrectos");

		return hora;
	}

	public static File[] listFilesAsArray(File directory, FilenameFilter filter, boolean recurse) {
		Collection<File> files = listFiles(directory, filter, recurse);
		if (files == null)
			return null;

		File[] arr = new File[files.size()];
		return files.toArray(arr);
	}

	public static Collection<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
		Vector<File> files = new Vector<File>();
		File[] entries = directory.listFiles();

		if (entries == null)
			return null;
		for (File entry : entries) {
			if (filter == null || filter.accept(directory, entry.getName())) {
				files.add(entry);
			}

			if (recurse && entry.isDirectory()) {
				files.addAll(listFiles(entry, filter, recurse));
			}
		}

		return files;
	}
	public static void copyFile(String szSource, String szDestiny) throws Exception {
		byte bBuffer[];
		File oFile = new File(szSource);
		RandomAccessFile oSource = new RandomAccessFile(szSource, "r");
		RandomAccessFile oDestiny = new RandomAccessFile(szDestiny, "rw");
		bBuffer = new byte[(int) oFile.length()];

		oSource.readFully(bBuffer, 0, (int) oFile.length());
		oDestiny.write(bBuffer);
		oSource.close();
		oDestiny.close();
	}

	public static void CopyFilesWithSub(String szDirSource, String szDirDestiny) throws Exception {
		File oFile = new File(szDirSource);
		File[] oFiles = oFile.listFiles();
		long lFileQty = oFiles.length;

		JTools.MakeDirectory(szDirDestiny);
		for (int i = 0; i < lFileQty; i++) {
			if (oFiles[i].isDirectory()) {
				JTools.MakeDirectory(szDirDestiny + "/" + oFiles[i].getName());
				CopyFilesWithSub(oFiles[i].getAbsolutePath(), szDirDestiny + "/" + oFiles[i].getName());
			}
		} // end for
		CopyFiles(szDirSource, szDirDestiny);
	}

	public static void CopyFiles(String sDirOrigen, String sDirDestino) throws Exception {
		File oFile = new File(sDirOrigen);
		File[] oFiles = oFile.listFiles();
		long lFileQty = oFiles.length;
		File oDestFile = null;
		byte cBuffer[] = null;

		RandomAccessFile InputFile = null;
		RandomAccessFile OutputFile = null;

		for (int lIndex = 0; lIndex < lFileQty; lIndex++) {
			if (oFiles[lIndex].isFile()) {
				String fileDest = sDirDestino + "/" + oFiles[lIndex].getName();
				JTools.createDirectories("", fileDest);
				oDestFile = new File(fileDest);
				InputFile = new RandomAccessFile(oFiles[lIndex], "r");
				OutputFile = new RandomAccessFile(oDestFile, "rw");
				cBuffer = new byte[(int) oFiles[lIndex].length()];

				InputFile.readFully(cBuffer, 0, (int) oFiles[lIndex].length());
				OutputFile.write(cBuffer);
				OutputFile.close();
				InputFile.close();
			}
		}
	}

	public static void CopyFilesWithMark(String sDirOrigen, String sDirDestino, String mark) throws Exception {
		File oFile = new File(sDirOrigen);
		File[] oFiles = oFile.listFiles();
		long lFileQty = oFiles.length;
		File oDestFile = null;
		byte cBuffer[] = null;

		RandomAccessFile InputFile = null;
		RandomAccessFile OutputFile = null;

		for (int lIndex = 0; lIndex < lFileQty; lIndex++) {
			if (oFiles[lIndex].isFile()) {
				oDestFile = new File(sDirDestino + "\\" + mark + oFiles[lIndex].getName());
				InputFile = new RandomAccessFile(oFiles[lIndex], "r");
				OutputFile = new RandomAccessFile(oDestFile, "rw");
				cBuffer = new byte[(int) oFiles[lIndex].length()];

				InputFile.readFully(cBuffer, 0, (int) oFiles[lIndex].length());
				OutputFile.write(cBuffer);
				OutputFile.close();
				InputFile.close();
			}
		}
	}
	public static void DeleteFile(String sDirOrigen) throws Exception {
		File oFile = new File(sDirOrigen);
		oFile.delete();
	}
	public static void DeleteFiles(String sDirOrigen) throws Exception {
		File oFile = new File(sDirOrigen);
		File[] oFiles = oFile.listFiles();
		if (oFiles == null)
			return;// dir not exist
		long lFileQty = oFiles.length;

		for (int lIndex = 0; lIndex < lFileQty; lIndex++) {
			if (oFiles[lIndex].isFile()) {
				oFiles[lIndex].delete();
			}
		}
	}

	public static String nvl(String zString, String zNulo) throws Exception {
		if (zString == null)
			return zNulo;
		return zString;
	}

	public static String DotToComma(String zString) {
		int i = zString.lastIndexOf('.');
		zString = zString.substring(0, i) + "," + zString.substring(i + 1, zString.length());
		return zString;
	}

	/** Agregado por Hernan * */
	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Funciones de manejo de buffers de memoria. Convierte una cadena de bytes a su
	 * representacion en hexadecimal y vice-versa
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/

	public static String BinToHexString(String binStr, int len) {
		int i;
		StringBuffer retStr = new StringBuffer(len);
		char cBin[] = binStr.toCharArray();

		for (i = 0; i < len; i++) {
			retStr.append(BINTOCHAR[(cBin[i] & 0xf0) >>> 4]);
			retStr.append(BINTOCHAR[cBin[i] % 0x10]);
		}

		return retStr.toString();
	}

	private static int HexCharToBin(char ch) {
		return (ch >= '0' && ch <= '9') ? ch - '0'
				: (ch >= 'a' && ch <= 'f') ? ch - 'a' + 10 : (ch >= 'A' && ch <= 'F') ? ch - 'A' + 10 : ch & 0x0f;
	}

	public static String CharToHex(char ch) {
		StringBuffer sRet = new StringBuffer();
		char one = (char) ((0xF0 & ch) >> 4);
		one = (char) (one + ((one > 0x09) ? 'A' - 10 : '0'));
		sRet.append(one);
		char two = (char) (0x0F & ch);
		two = (char) (two + ((two > 0x09) ? 'A' - 10 : '0'));
		sRet.append(two);
		return sRet.toString();
	}

	public static String HexStringToBin(String hexStr, int len) {
		int i;
		if (hexStr.length() % 2 != 0)
			hexStr += "0";
		char aHex[] = hexStr.toCharArray();
		StringBuffer retStr = new StringBuffer(aHex.length);
		for (i = 0; i < len && i < (aHex.length - 1); i += 2) {
			retStr.append((char) (HexCharToBin(aHex[i]) * 0x10 + HexCharToBin(aHex[i + 1])));
		}
		return retStr.toString();
	}

	public static int BCDToInt(String stBuff, int len) {
		int i, sum;

		for (i = 0, sum = 0; i < len && i < stBuff.length(); i++) {
			sum *= 100;
			sum += ((stBuff.charAt(i) & 0xf0) >>> 4) * 10 + (stBuff.charAt(i) & 0x0f);
		}
		return sum;
	}

	// --------------------------------------------------------------- //
	// MUEVE UN ARCHIVO DE ORIGEN A DESTINO, EL PATH DEBE SER COMPLETO //
	// --------------------------------------------------------------- //
	public static boolean MoveFile(String zOrigen, String zDestiny) {
		File oOrigin = new File(zOrigen);
		File oDestiny = new File(zDestiny);

		return oOrigin.renameTo(oDestiny);
	}

	public static String moveWorkFile(String source, String dirTarget) throws Exception {
		source = JTools.scapeFileName(source);
		File wfile = new File(source);

		// Get source File Name
		String uploadName = wfile.getName();
		int ix2 = uploadName.lastIndexOf('.');
		int ix1 = uploadName.substring(0, ix2).lastIndexOf('.');
		String fileName = uploadName.substring(0, ix1).concat(uploadName.substring(ix2));
		String absDir = JTools.makeDirTarget(dirTarget);

		File file = new File(absDir + fileName);
		int count = 1;
		while (file.exists()) {
			count++;
			fileName = uploadName.substring(0, ix1).concat("(" + count + ")").concat(uploadName.substring(ix2));
			file = new File(absDir + fileName);
		}
		JTools.copyFile(source, absDir + fileName);
		return absDir + fileName;
	}

	// -------------------------------------------------------------------- //
	// ELIMINA LOS ARCHIVOS DE UN DIRECTORIO. LA FECHA DEBE SER "yyyymmdd" //
	// -------------------------------------------------------------------- //
	public static boolean EliminarDirectoryFiles(String Directorio, String Extencion, String BeginingName,
			String Fecha) {
		return JTools.EliminarDirectoryFiles(Directorio, Extencion, BeginingName, Fecha, false);
	}

	public static boolean EliminarDirectoryFiles(String Directorio, String Extencion, String BeginingName, String Fecha,
			boolean recursivo) {
		String olist[];
		try {
			if (Directorio == null) {
				return false;
			}
			File oDirectory = new File(Directorio);
			olist = oDirectory.list();

			for (int i = 0; i < olist.length; i++) {

				File oFileName = new File(Directorio + "/" + olist[i]);
				if (oFileName.isDirectory()) {
					if (!recursivo)
						continue;
					JTools.EliminarDirectoryFiles(oFileName.getAbsolutePath(), Extencion, BeginingName, Fecha,
							recursivo);
					// Si quedo vacia boor el directorio
					if (oFileName.list().length <= 0)
						oFileName.delete();
					continue;
				}

				if (BeginingName != null) {
					if (!olist[i].substring(0, BeginingName.length()).equalsIgnoreCase(BeginingName)) {
						// Distinto nombre
						continue;
					}
				}

				if (Extencion != null) {
					if (!olist[i].toUpperCase().endsWith(Extencion.toUpperCase())) {
						// Distinta Extencion
						continue;
					}
				}

				if (Fecha != null) {
					String sLogModified = JDateTools.LongToString(oFileName.lastModified(), "yyyyMMdd");
					if (Long.valueOf(Fecha).longValue() < Long.valueOf(sLogModified).longValue()) {
						// Fecha del archivo mayor a la del parametro
						continue;
					}
				}

				oFileName.delete();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	} // end method

	public static boolean isVigente(Date zFecha, long zHora) throws Exception {
		long fechahoy = Long.parseLong(JDateTools.CurrentDate("yyyyMMdd"));
		long fecha = Long.parseLong(JDateTools.DateToString(zFecha, "yyyyMMdd"));

		if (fecha > fechahoy)
			return false;
		if (fecha == fechahoy) {
			long horahoy = Long.parseLong(JDateTools.CurrentTime("HHmmss"));
			long hora = zHora;
			if (horahoy < hora)
				return false;
			return true;
		}
		return true;
	}

	public static long hexaToDecimal(String hexa) {
		long lRet = 0;
		String ch;
		int valor;
		int len = hexa.length();
		for (int i = len; i > 0; i--) {
			ch = hexa.substring(i - 1, i).toUpperCase();
			valor = ch.charAt(0);
			if ((valor >= 'A') && (valor <= 'F')) {
				valor = valor - 'A' + 10;
				lRet = lRet + valor * (long) Math.pow(16, (double) len - i);
			}
			if ((valor >= '0') && (valor <= '9')) {
				valor = valor - '0';
				lRet = lRet + valor * (long) Math.pow(16, (double) len - i);
			}
		}
		return lRet;
	}
	private static final int sizeOfIntInHalfBytes = 8;
	private static final int numberOfBitsInAHalfByte = 4;
	private static final int halfByte = 0x0F;
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static String decToHex(int dec) {
		StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
		hexBuilder.setLength(sizeOfIntInHalfBytes);
		for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i) {
			int j = dec & halfByte;
			hexBuilder.setCharAt(i, hexDigits[j]);
			dec >>= numberOfBitsInAHalfByte;
		}
		return hexBuilder.toString();
	}

	// -------------------------------------------------------------------- //
	// CENTRA UN STRING EN EL LARGO DADO CON EL CHAR RELLENO O LO TRUNCA SI //
	// EXCEDE dataLen //
	// -------------------------------------------------------------------- //
	public static String centerString(String data, int totalLen, char refill) {
		if (data == null)
			data = "";
		int dataLen = data.length();
		if (dataLen == totalLen)
			return data;
		if (dataLen > totalLen)
			return data.substring(0, totalLen);
		int freeSpace = (totalLen - dataLen) / 2;
		int module = (totalLen - dataLen) % 2;
		char[] temp = new char[totalLen];
		data.getChars(0, dataLen, temp, freeSpace);
		for (int i = 0; i < freeSpace; i++)
			temp[i] = refill;
		for (int i = totalLen - 1, j = 0; j < freeSpace + module; i--, j++)
			temp[i] = refill;
		return new String(temp);
	}

	// ---------------------------------------------------------------------------------
	// DADO DOS STRINGS Y UN ANCHO lenTotal ARMA UN STRING SEGUN EL SIGUIENTE
	// EJEMPLO:
	// leftString -> "Hector"; rightString -> "Khrum"; lenTotal -> 20;
	// DEVUELVE EL STRING:
	// "Hector Khrum"
	// Pasando uno de los dos Strings como String vacio sirve para alinear a la
	// izq. o
	// la derecha en el largo dado
	// ---------------------------------------------------------------------------------
	public static String justifyStrings(String leftString, String rightString, int totalLen, char refill) {
		if (leftString == null)
			leftString = "";
		if (rightString == null)
			rightString = "";
		int leftLen = leftString.length(), rightLen = rightString.length();
		int bothLen = leftLen + rightLen;
		if (bothLen > totalLen)
			return leftString.concat(rightString).substring(0, totalLen);
		if (bothLen == totalLen)
			return leftString.concat(rightString);
		int refillLen = totalLen - bothLen;
		char[] resultingArray = new char[totalLen];
		if (leftLen > 0 && rightLen > 0) {
			leftString.getChars(0, leftLen, resultingArray, 0);
			int i;
			for (i = 0; i < refillLen; i++)
				resultingArray[leftLen + i] = refill;
			rightString.getChars(0, rightLen, resultingArray, leftLen + i);
			return new String(resultingArray);
		}
		if (leftLen > 0) {
			leftString.getChars(0, leftLen, resultingArray, 0);
			for (int i = 0; i < refillLen; i++)
				resultingArray[leftLen + i] = refill;
			return new String(resultingArray);
		}
		if (rightLen > 0) {
			rightString.getChars(0, rightLen, resultingArray, totalLen - rightLen);
			for (int i = 0; i < refillLen; i++)
				resultingArray[i] = refill;
			return new String(resultingArray);
		} else {
			for (int i = 0; i < totalLen; i++)
				resultingArray[i] = refill;
			return new String(resultingArray);
		}
	}

	// ------------------------------------------------------------------------------
	public static boolean hasDecimals(double zNumber) throws Exception {
		return ((zNumber - (long) zNumber) != 0d);
	}

	// ------------------------------------------------------------------------------
	public static String IntToStringBigEndian(int lVal) {
		char b1 = (char) (lVal & 0x000000ff);
		char b2 = (char) ((lVal & 0x0000ff00) >>> 8);
		char b3 = (char) ((lVal & 0x00ff0000) >>> 16);
		char b4 = (char) ((lVal & 0xff000000) >>> 24);
		char[] bArray = new char[] { b1, b2, b3, b4 };
		String string = new String(bArray);
		return string;
	}

	public static String removeSpaces(String s) {
		char cacheChar;
		StringBuffer temp = new StringBuffer(s.trim());
		for (int i = 0; i < temp.length(); i++) {
			cacheChar = temp.charAt(i);
			if (cacheChar == (char) 0x20 || cacheChar == (char) 0x09)
				temp.deleteCharAt(i);
		}
		return temp.toString();
	}
	public static String removeTwoOrMoreSpaces(String s) {
		char cacheChar;
		boolean space = false;
		StringBuffer temp = new StringBuffer(s.trim());
		StringBuffer tempOut = new StringBuffer("");
		for (int i = 0; i < temp.length(); i++) {
			cacheChar = temp.charAt(i);
			if ((cacheChar == (char) 0x20 || cacheChar == (char) 0x09)) {
				space = true;
				continue;
			}
			if (space) {
				tempOut.append(" ");
				space = false;
			}
			tempOut.append(cacheChar);
		}
		return tempOut.toString();
	}
	public static String replaceForeignChars(String s) {
		int len = s.length();
		char[] temp = new char[len];
		s.getChars(0, len, temp, 0);
		int len1 = temp.length;
		for (int i = 0; i < len1; i++) {
			temp[i] = replaceForeignChar(temp[i]);
		}
		return new String(temp);
	}

	public static char replaceForeignChar(char msg) {
		if (msg == 'á')
			return 'a';
		if (msg == 'é')
			return 'e';
		if (msg == 'í')
			return 'i';
		if (msg == 'ó')
			return 'o';
		if (msg == 'ú')
			return 'u';
		if (msg == 'Á')
			return 'A';
		if (msg == 'É')
			return 'E';
		if (msg == 'Í')
			return 'I';
		if (msg == 'Ó')
			return 'O';
		if (msg == 'Ú')
			return 'U';
		if (msg == 'ñ')
			return 'n';
		if (msg == 'Ñ')
			return 'N';
		if (msg == '¿')
			return ' ';
		if (msg == '¡')
			return ' ';
		// if (msg=='\'') return ' ';
		return msg;
	}

	public static String maskForeignChars(String s) {
		return convertForeignChars(s);
	}

	public static String convertForeignChars(String s) {
		int len = s.length();
		char[] temp = new char[len];
		s.getChars(0, len, temp, 0);
		int len1 = temp.length;
		for (int i = 0; i < len1; i++) {
			temp[i] = convertForeignChar(temp[i]);
		}
		return new String(temp);
	}

	public static char convertForeignChar(char msg) {
		if (msg == 'á')
			return 160;
		if (msg == 'é')
			return 130;
		if (msg == 'í')
			return 161;
		if (msg == 'ó')
			return 162;
		if (msg == 'ú')
			return 163;
		if (msg == 'Á')
			return 'A';
		if (msg == 'É')
			return 'E';
		if (msg == 'Í')
			return 'I';
		if (msg == 'Ó')
			return 'O';
		if (msg == 'Ú')
			return 'U';
		if (msg == 'ñ')
			return 164;
		if (msg == 'Ñ')
			return 165;
		if (msg == '¿')
			return ' ';
		if (msg == '¡')
			return ' ';
		return msg;
	}

	public static byte getByteAscii(char cCaract) {
		switch (cCaract) {
		case 'á':
			return -96;
		case 'é':
			return -126;
		case 'í':
			return -95;
		case 'ó':
			return -94;
		case 'ú':
			return -93;
		case 'ñ':
			return -92;
		case 'Ñ':
			return -91;
		}
		return (byte) (cCaract & 0x00FF);
	}

	public static byte[] getBytesAscii(String s) {
		int len = s.length();
		byte[] ret = new byte[len];
		for (int i = 0; i < len; i++) {
			ret[i] = getByteAscii(s.charAt(i));
		}
		return ret;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Dado un año y un mes, obtiene el dia maximo del mes en cuestion Mes de 1 a 12
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public static int getMaxMonthDays(int iYear, int iMonth) {
		boolean bBisciesto = ((iYear % 4) == 0);
		if (iMonth == 2) {
			if (bBisciesto) {
				return 29;
			}
			return 28;
		}
		if (iMonth == 1 || iMonth == 3 || iMonth == 5 || iMonth == 7 || iMonth == 8 || iMonth == 10 || iMonth == 12) {
			return 31;
		}
		return 30;
	}

	public static String getRootDir() {
		return System.getProperty("user.home").substring(0, 3);
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Crea el directorio zDir en caso que no exista.
	 * 
	 * @param zDir Directorio a crear con path completo
	 * @return true si el directorio existe, o si no existe y es creado
	 *         correctamente
	 * @return false en caso de error
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public static void createDirectories(String base, String file) throws Exception {
		JStringTokenizer t = JCollectionFactory.createStringTokenizer(file, '/');
		int cant = t.countTokens();
		String dir = "";
		while (t.hasMoreTokens()) {
			if (--cant <= 0)
				return;
			dir += "/" + t.nextToken();
			JTools.MakeDirectory(base + dir);
		}

	}

	public static String makeDirTarget(String dir) throws Exception {
		String absDir = JPath.PssPathData() + "/" + dir;
		File oDestFile = new File(absDir);
		if (!oDestFile.exists()) {
			oDestFile.mkdirs();
		}
		return absDir + "/";
	}

	public static boolean MakeDirectory(String zDir) throws Exception {
		boolean bResult = true;
		try {
			File oDirProc = new File(zDir);
			if (!oDirProc.isDirectory()) {
				oDirProc.mkdirs();
				PssLogger.logDebug("Directorio creado: " + zDir);
			}
		} catch (Exception e) {
			PssLogger.logError("Error al crear directorio: " + zDir + " [" + e.getMessage() + "]");
			bResult = false;
		}
		return bResult;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Verifica si existe un directorio
	 * 
	 * @param zDirectory Directorio a chequear
	 * @throws JException si el directorio no existe o no es un directorio
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public static void checkDirectory(String zDirectory) throws Exception {
		File oDir = new File(zDirectory);
		if (!oDir.isDirectory()) {
			JExcepcion.SendError("Directorio Inexistente: " + zDirectory);
		}
	}

	public static byte[] cloneByteArrayWithIndex(byte[] source, int from, int length) {
		byte[] buffer = new byte[length - from];
		for (int i = from; i < length; i++) {
			buffer[i - from] = source[i];
		}
		return buffer;
	}

	public static byte[] concatByteArray(byte[] Input1, int len1, byte[] Input2, int len2) throws Exception {
		byte[] Buffer = new byte[len1 + len2];
		int i = 0;
		for (; i < len1; i++) {
			Buffer[i] = Input1[i];
		}
		int j = i;
		for (i = 0; i < len2; i++) {
			Buffer[i + j] = Input2[i];
		}
		return Buffer;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Retorna el mismo String anteponiendo una comilla simple donde aparece una
	 * comilla simple
	 * 
	 * @return Un String con las comillas escapeadas
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public static String escapeQuote(String zValor) {
		StringBuffer sAux = new StringBuffer(0);
		int iLen = zValor.length();

		for (int iIdx = 0; iIdx < iLen; iIdx++) {
			if (zValor.charAt(iIdx) == '\'') {
				sAux.append('\'');
			} else if (zValor.charAt(iIdx) == '\\') {
				sAux.append('\\');
			}
			sAux.append(zValor.charAt(iIdx));
		}
		return sAux.toString();
	}

	public static boolean isInstalled(String zClass) throws Exception {
		try {
			Class.forName(zClass);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (NoClassDefFoundError e) {
			return false;
		}
	}

	/*
	 * Byte 1 2 3 4 SEEE EEEE EMMM MMMM MMMM MMMM MMMM MMMM Nibble 1 2 3 4 5 6 7 8 S
	 * = SIGN E = EXPONENT M = MANTISSA Exponent = 2 ^ (E - 127) Mantissa = 1.0 + (M
	 * / 8,388,608) // 8,388,608 == 2 ^ 23 DecimalValue = Exponent * Mantissa //
	 * with sign S
	 */
	public double IEEE_AsciiHexToFloat(String hexString) {
		String binString = JTools.AsciiToBinary(hexString);
		boolean positive = binString.charAt(0) == '0';
		double exponent = Math.pow(2, Integer.parseInt(binString.substring(1, 9), 2) - 127);
		double mantissa = 1.0 + (Integer.parseInt(binString.substring(9, 32), 2) / 8388608.0);
		double decimalValue = JTools.rd((exponent * mantissa) * (positive ? 1 : -1), 5);
		return decimalValue;
	}

	public static String generarString(Node doc) throws Exception {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		transformer.transform(source, result);
		String xmlString = sw.toString();
		return xmlString;
	}

	public static String getNumberWithOutSymbols(String zNumber) throws Exception {
		String number = "";
		String nroi = "";
		int i = 0;
		zNumber.trim();
		for (i = 0; i < zNumber.length(); i++) {
			nroi = String.valueOf(zNumber.charAt(i));
			if (isNumber(nroi) && !nroi.equals("-") && !nroi.equals(".")) {
				number += nroi;
			}
		}
		return number;
	}

	public static boolean isInstalledRetailStock() throws Exception {
		return JTools.isInstalled("pss.erp.Stock.BizStock");
	}

	public static String numberToWords(int zNro, boolean zUnidades) throws Exception {
		String sNro, sTexto;
		int uni, dec, cen;
		String sFormat = "000";
		sNro = String.valueOf(zNro);
		sNro = sFormat.substring(sNro.length()) + sNro;
		uni = Integer.parseInt(sNro.substring(2, 3));
		dec = Integer.parseInt(sNro.substring(1, 2));
		cen = Integer.parseInt(sNro.substring(0, 1));
		sTexto = "";

		switch (cen) {
		case 0: {
			sTexto = "";
			break;
		}
		case 1: {
			sTexto = ((dec == 0 && uni == 0) ? "cien " : "ciento ");
			break;
		}
		case 2: {
			sTexto = "doscientos ";
			break;
		}
		case 3: {
			sTexto = "trescientos ";
			break;
		}
		case 4: {
			sTexto = "cuatrocientos ";
			break;
		}
		case 5: {
			sTexto = "quinientos ";
			break;
		}
		case 6: {
			sTexto = "seiscientos ";
			break;
		}
		case 7: {
			sTexto = "setecientos ";
			break;
		}
		case 8: {
			sTexto = "ochocientos ";
			break;
		}
		case 9: {
			sTexto = "novecientos ";
			break;
		}
		}

		switch (dec) {
		case 0: {
			sTexto = sTexto + "";
			break;
		}
		case 1: {
			switch (uni) {
			case 0: {
				sTexto = sTexto + "diez ";
				break;
			}
			case 1: {
				sTexto = sTexto + "once ";
				break;
			}
			case 2: {
				sTexto = sTexto + "doce ";
				break;
			}
			case 3: {
				sTexto = sTexto + "trece ";
				break;
			}
			case 4: {
				sTexto = sTexto + "catorce ";
				break;
			}
			case 5: {
				sTexto = sTexto + "quince ";
				break;
			}
			case 6: {
				sTexto = sTexto + "dieciseis ";
				break;
			}
			case 7: {
				sTexto = sTexto + "diecisiete ";
				break;
			}
			case 8: {
				sTexto = sTexto + "dieciocho ";
				break;
			}
			case 9: {
				sTexto = sTexto + "diecinueve ";
				break;
			}
			}
			break;
		}
		case 2: {
			sTexto = sTexto + ((uni == 0) ? "veinte " : "veinti");
			break;
		}
		case 3: {
			sTexto = sTexto + ((uni == 0) ? "treinta " : "treinta y ");
			break;
		}
		case 4: {
			sTexto = sTexto + ((uni == 0) ? "cuarenta " : "cuarenta y ");
			break;
		}
		case 5: {
			sTexto = sTexto + ((uni == 0) ? "cincuenta " : "cincuenta y ");
			break;
		}
		case 6: {
			sTexto = sTexto + ((uni == 0) ? "sesenta " : "sesenta y ");
			break;
		}
		case 7: {
			sTexto = sTexto + ((uni == 0) ? "setenta " : "setenta y ");
			break;
		}
		case 8: {
			sTexto = sTexto + ((uni == 0) ? "ochenta " : "ochenta y ");
			break;
		}
		case 9: {
			sTexto = sTexto + ((uni == 0) ? "noventa " : "noventa y ");
			break;
		}
		}

		if (dec != 1) {
			switch (uni) {
			case 0: {
				sTexto = sTexto + "";
				break;
			}
			case 1: {
				sTexto = sTexto + ((zUnidades) ? "uno " : "un ");
				break;
			}
			case 2: {
				sTexto = sTexto + "dos ";
				break;
			}
			case 3: {
				sTexto = sTexto + "tres ";
				break;
			}
			case 4: {
				sTexto = sTexto + "cuatro ";
				break;
			}
			case 5: {
				sTexto = sTexto + "cinco ";
				break;
			}
			case 6: {
				sTexto = sTexto + "seis ";
				break;
			}
			case 7: {
				sTexto = sTexto + "siete ";
				break;
			}
			case 8: {
				sTexto = sTexto + "ocho ";
				break;
			}
			case 9: {
				sTexto = sTexto + "nueve ";
				break;
			}
			}
		}
		return sTexto;
	}

	public static String getPlural(String zSingular) throws Exception {
		/* ultima letra de la palabra singular para setear plural */
		String sLetra = zSingular.substring(zSingular.length() - 1, zSingular.length()).toUpperCase();
		String sPlural = zSingular;
		if (sLetra.equals("A") || sLetra.equals("E") || sLetra.equals("O"))
			sPlural = zSingular + "s";
		if (sLetra.equals("R"))
			sPlural = zSingular + "es";
		return sPlural;
	}

	static public boolean isIntersectionOfValues(double sDesde1, double sHasta1, double sDesde2, double sHasta2) {
		return (sDesde1 <= sDesde2 && sHasta1 >= sDesde2) || (sDesde2 <= sDesde1 && sHasta2 >= sDesde1);
	}

	@SuppressWarnings("unchecked")
	static public boolean isIntersectionOfValues(Comparable sDesde1, Comparable sHasta1, Comparable sDesde2,
			Comparable sHasta2) {
		return (sDesde2.compareTo(sDesde1) >= 0 && sDesde2.compareTo(sHasta1) <= 0)
				|| (sDesde1.compareTo(sDesde2) >= 0 && sDesde1.compareTo(sHasta2) <= 0);
	}

	static public double min(double a, double b) {
		return a < b ? a : b;
	}

	static public long min(long a, long b) {
		return a < b ? a : b;
	}

	/**
	 * Returns a string capitalized
	 * 
	 * @param zInput
	 * @return
	 */
	public static String capitalize(String zInput) {
		if (zInput.length() < 1)
			throw new RuntimeException("The String must not be empty - JTools.capitalize()");
		String sBuffer = zInput.substring(1, zInput.length());
		String sFirstLetter = String.valueOf(zInput.charAt(0)).toUpperCase();
		return sFirstLetter + sBuffer;
	}
	public static String capitalizeAll(String zInput) {
		if (zInput == null)
			return zInput;
		String name = "";
		StringTokenizer toks = new StringTokenizer(zInput.toLowerCase(), " ");
		while (toks.hasMoreTokens()) {
			String tok = toks.nextToken();
			name += (name.equals("") ? "" : " ") + capitalize(tok);
		}
		return name;
	}

	public static String byteVectorToString(byte[] vbVal) {
		StringBuffer ret = new StringBuffer(vbVal.length);
		for (int i = 0; i < vbVal.length; i++)
			ret.append(((char) (vbVal[i] & 0x00ff)));
		return ret.toString();
	}

	public static byte[] stringToByteVector(String sVal) {
		byte[] ret = new byte[sVal.length()];
		for (int i = 0; i < sVal.length(); i++) {
			ret[i] = 0;
			ret[i] |= sVal.charAt(i) & 0x00ff;
		}
		return ret;
	}

	public static String stringRepetition(String str, int times) {
		String sRet = "";
		for (int iCont = 0; iCont < times; iCont++) {
			sRet += str;
		}
		return sRet;
	}

	/*
	 * public static String trimEndDots(String dotted) { return trimEndChars(dotted,
	 * '.'); }
	 * 
	 * public static String trimEndChars(String myString, char toTrim) { if
	 * (myString == null || myString.equals("")) return myString; String trimed =
	 * myString.substring(0, myString.length() - countEndChars(myString, toTrim) );
	 * return trimed.trim(); }
	 */

	public static int countEndChars(String myString, char[] toTrim) {
		int counter = 0;
		int j;
		int len = toTrim.length;
		for (int i = myString.length(); i > 0; i--) {
			char c = myString.charAt(i - 1);
			for (j = 0; j < len; j++) {
				if (c == toTrim[j])
					break;
			}
			if (j == len)
				break;
			else
				counter++;
		}
		return counter;
	}
	public static String clearNumbers(String input) {
		if (input == null)
			return input;
		String output = "";
		input = input.trim();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (!(c >= 48 && c <= 57)) {
				output += c;
			}
		}
		return output;
	}

	public static String clearNonNumbers(String input) {
		if (input == null)
			return input;
		String output = "";
		input = input.trim();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c >= 48 && c <= 57) {
				output += c;
			}
		}
		return output;
	}
	public static String clearNonNumbersAndSign(String input) {
		if (input == null)
			return input;
		boolean one = false;
		String output = "";
		input = input.trim();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c >= 48 && c <= 57) {
				output += c;
			}
			if (c == '-' && !one) {
				output += c;
				one = true;
			}
		}
		return output;
	}
	public static boolean isUpperWord(String zWord) {
		// hay que mejorar esta trucha funcion, por ahora con esto es suficiente
		char c;
		int len = zWord.length();
		for (int i = 1; i < len; i++) {
			c = zWord.charAt(i);
			if (c >= 'a' && c <= 'z')
				return false;
		}
		return true;
	}

	public static JList parseNumberSequence(String sText, int max) throws Exception {
		// devuelve la lista de enteros desde un string como este: 1-5,7,7- retorna
		// 1,2,3,4,5,7,7,8,9,...,max
		int first, last, vez;
		JList<Integer> sequence = JCollectionFactory.createList(max == 0 ? 100 : max);
		JStringTokenizer t = JCollectionFactory.createStringTokenizer(sText, ',');
		while (t.hasMoreTokens()) {
			String subSequence = t.nextToken();
			JStringTokenizer ts = JCollectionFactory.createStringTokenizer(subSequence, '-');
			vez = 0;
			first = 0;
			last = max;
			while (ts.hasMoreTokens() && vez < 2) {
				String subsubSequence = ts.nextToken();
				if (vez++ == 0) {
					first = subsubSequence.length() == 0 ? 0 : Integer.parseInt(subsubSequence);
					last = first;
				} else
					last = subsubSequence.length() == 0 ? max : Integer.parseInt(subsubSequence);
			}
			for (vez = first; vez <= last; vez++) {
				sequence.addElement(new Integer(vez));
			}
		}
		return sequence;
	}

	public static String format(String zMascara, String zValor) throws Exception {
		String sResult = "", sOrigen = zValor.trim();
		int iPos = 0;
		for (int i = 0; i < zMascara.length(); i++) {
			if (zMascara.toCharArray()[i] == '#') {
				if (iPos < sOrigen.length())
					sResult += sOrigen.toCharArray()[iPos++];
			} else
				sResult += zMascara.toCharArray()[i];
		}
		return sResult;
	}

	public static double toFloatSafe(String sAlfaNumeric) throws Exception {
		if (sAlfaNumeric == null)
			return 0.0;
		if (sAlfaNumeric.equals(""))
			return 0.0;
		if (!JTools.isNumber(sAlfaNumeric, true))
			return 0.0;
		return Float.parseFloat(sAlfaNumeric);
	}

	public static String formatString(String zMascara, String zValor) throws Exception {
		StringBuffer sResult = new StringBuffer("");
		StringBuffer sOrigen = new StringBuffer(zValor).reverse();
		StringBuffer sMask = new StringBuffer(zMascara).reverse();
		int iPos = 0;
		for (int i = 0; i < sMask.length(); i++) {
			if (zMascara.toCharArray()[i] == '#') {
				if (iPos < sOrigen.length())
					sResult.append(sOrigen.charAt(iPos++));
				else
					break;
			} else
				sResult.append(sMask.charAt(i));
		}
		return sResult.reverse().toString();
	}
	public static String getValidFilename(String sAlfaNumeric) throws Exception {
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] >= 'A' && cAlfa[i] <= 'Z') {
				sRet.append(cAlfa[i]);
			}
			if (cAlfa[i] >= 'a' && cAlfa[i] <= 'z') {
				sRet.append(cAlfa[i]);
			}
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			}
		}
		return sRet.toString();
	}
	// Se busca obtener el número existente dentro de en un string alfanumérico
	// Se usa particularmente para obtener la parte numérica del nodo
	public static String getNumberEmbedded(String sAlfaNumeric) throws Exception {
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			}
		}
		return sRet.toString();
	}

	public static String getNumberFloatToLong(String sAlfaNumeric) {
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] == '.')
				break;
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			}
			if (cAlfa[i] == '-') {
				sRet.append(cAlfa[i]);
			}
		}
		return sRet.toString();
	}
	public static String getFirstNumberEmbedded(String sAlfaNumeric) throws Exception {
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			} else {
				if (cAlfa[i] != ' ')
					break;
			}
		}
		return sRet.toString();
	}
	public static String getNumberEmbeddedWithSep(String sAlfaNumeric) throws Exception {
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if ((cAlfa[i] >= '0' && cAlfa[i] <= '9') || cAlfa[i] == '.' || cAlfa[i] == ',') {
				sRet.append(cAlfa[i]);
			}
		}
		return sRet.toString();
	}

	public static String getNumberEmbeddedWithDecSigned(String sAlfaNumeric) {
		if (sAlfaNumeric == null)
			return "0";
		char cAlfa[] = sAlfaNumeric.toCharArray();
		int onePoint = 0;
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] == '.')
				onePoint++;
			if ((cAlfa[i] >= '0' && cAlfa[i] <= '9') || cAlfa[i] == '.' || cAlfa[i] == '-') {
				sRet.append(cAlfa[i]);
			}
		}
		if (onePoint > 1)
			return "0";
		return sRet.toString();
	}

	public static double getDoubleNumberEmbedded(String sAlfaNumeric) {
		String n = getNumberEmbeddedWithDecSigned(sAlfaNumeric);
		if (n == null || n.equals(""))
			return 0;
		return Double.parseDouble(n);
	}

	public static long getLongNumberEmbedded(String sAlfaNumeric) {
		if (sAlfaNumeric == null)
			return 0;
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] == ' ')
				continue;
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			}
		}
		if (sRet.toString().equals(""))
			return 0;
		return Long.parseLong(sRet.toString());
	}

	public static long getLongFirstNumberEmbedded(String sAlfaNumeric) {
		if (sAlfaNumeric == null)
			return 0;
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] == ' ')
				continue;
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			}
		}
		if (sRet.toString().equals(""))
			return 0;
		return Long.parseLong(sRet.toString());
	}

	public static String getFirstNumbersEmbedded(String sAlfaNumeric) {
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] == ' ')
				continue;
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			} else
				break;
		}
		return sRet.toString();
	}

	public static String getLastNumbersEmbedded(String sAlfaNumeric) {
		char cAlfa[] = sAlfaNumeric.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = cAlfa.length - 1; i <= 0; i--) {
			if (cAlfa[i] == ' ' && sRet.length() > 0)
				break;
			if (cAlfa[i] == ' ')
				continue;
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			} else
				break;
		}
		return sRet.toString();
	}

	public static String getAddressCalle(String sDomicilio) {
		char cAlfa[] = sDomicilio.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < cAlfa.length; i++) {
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9' && sRet.length() > 0)
				break;
			sRet.append(cAlfa[i]);
		}
		String s = sRet.toString().toLowerCase().replace("numero", "");
		s = s.replace("nro.", "");
		s = s.replace("nro", "");
		s = s.replace("nº", "");
		s = s.replace(" n ", "");
		return s.trim();
	}
	public static String getAddressNro(String sDomicilio) throws Exception {
		char cAlfa[] = sDomicilio.toCharArray();
		StringBuffer sRet = new StringBuffer();
		for (int i = cAlfa.length - 1; i >= 0; i--) {
			if (cAlfa[i] == ' ' && sRet.length() > 0)
				break;
			if (cAlfa[i] == 'º' && sRet.length() > 0)
				break;
			if (JTools.isAlpha("" + cAlfa[i]) && sRet.length() > 0)
				break;
			if (cAlfa[i] >= '0' && cAlfa[i] <= '9') {
				sRet.append(cAlfa[i]);
			} else
				break;
		}
		return JTools.getNumberEmbedded(sRet.reverse().toString());
	}
	/*
	 * public static boolean Zipfiles(String sZipfilename, String sFile, boolean
	 * bDelfilesrc) throws Exception { boolean bResult = false;
	 * 
	 * BufferedInputStream origin = null; int BUFFER_SIZE = 8192;
	 * 
	 * FileOutputStream dest = new FileOutputStream(sZipfilename); ZipOutputStream
	 * out = new ZipOutputStream( new BufferedOutputStream( dest ) );
	 * 
	 * byte[] data = new byte[ BUFFER_SIZE ]; FileInputStream fi = new
	 * FileInputStream(sFile); origin = new BufferedInputStream( fi, BUFFER_SIZE );
	 * 
	 * ZipEntry entry = new ZipEntry(sFile); out.putNextEntry( entry ); int count;
	 * while( ( count = origin.read(data, 0, BUFFER_SIZE ) ) != -1 ) {
	 * out.write(data, 0, count); }
	 * 
	 * origin.close(); out.close();
	 * 
	 * JDebugPrint.logDebug( "Zip File Generated: " + sZipfilename );
	 * 
	 * //Borra File si el Flag bDelfileorig es True JStreamFile oFile = new
	 * JStreamFile(); JBaseFile.Delete(sFile);
	 * 
	 * if(bDelfilesrc) oFile.Delete();
	 * 
	 * return bResult; }
	 */
	public static String replaceForeignCharsForWeb(String zValue) {
		StringBuffer buff = new StringBuffer(zValue);
		buff = replaceForeignCharsForWebAcute(buff, "á", "a");
		buff = replaceForeignCharsForWebAcute(buff, "é", "e");
		buff = replaceForeignCharsForWebAcute(buff, "í", "i");
		buff = replaceForeignCharsForWebAcute(buff, "ó", "o");
		buff = replaceForeignCharsForWebAcute(buff, "ú", "u");
		buff = replaceForeignCharsForWebAcute(buff, "Á", "A");
		buff = replaceForeignCharsForWebAcute(buff, "É", "E");
		buff = replaceForeignCharsForWebAcute(buff, "Í", "I");
		buff = replaceForeignCharsForWebAcute(buff, "Ó", "O");
		buff = replaceForeignCharsForWebAcute(buff, "Ú", "U");
		buff = replaceForeignCharsForWebTilde(buff, "ñ", "n");
		buff = replaceForeignCharsForWebTilde(buff, "Ñ", "N");
		buff = replaceForeignCharsForWeb(buff, "'", "middot");
		return buff.toString();
	}


	private static StringBuffer replaceForeignCharsForWebAcute(StringBuffer zValue, String zSearch, String zReplace) {
		return replaceForeignCharsForWeb(zValue, zSearch, zReplace + "acute");
	}
	private static StringBuffer replaceForeignCharsForWebTilde(StringBuffer zValue, String zSearch, String zReplace) {
		return replaceForeignCharsForWeb(zValue, zSearch, zReplace + "tilde");
	}

	private static StringBuffer replaceForeignCharsForWeb(StringBuffer zValue, String zSearch, String zReplace) {
		int pos = 0;
		if (zValue != null) {
			while (true) {
				pos = zValue.indexOf(zSearch, pos);
				if (pos < 0)
					break;
				zValue.replace(pos, pos + zSearch.length(), "&" + zReplace + ";");
			}
		}
		return zValue;
	}
	public static String replaceWebForForeignChars(String value) {
		StringBuffer zValue = new StringBuffer(value);
		zValue = replaceWebForForeignChars(zValue, "&aacute;", "á");
		zValue = replaceWebForForeignChars(zValue, "&eacute;", "é");
		zValue = replaceWebForForeignChars(zValue, "&iacute;", "í");
		zValue = replaceWebForForeignChars(zValue, "&oacute;", "ó");
		zValue = replaceWebForForeignChars(zValue, "&uacute;", "ú");
		zValue = replaceWebForForeignChars(zValue, "&Aacute;", "Á");
		zValue = replaceWebForForeignChars(zValue, "&Eacute;", "É");
		zValue = replaceWebForForeignChars(zValue, "&Iacute;", "Í");
		zValue = replaceWebForForeignChars(zValue, "&Oacute;", "Ó");
		zValue = replaceWebForForeignChars(zValue, "&Uacute;", "Ú");
		zValue = replaceWebForForeignChars(zValue, "&ntilde;", "ñ");
		zValue = replaceWebForForeignChars(zValue, "&Ntilde;", "Ñ");
		zValue = replaceWebForForeignChars(zValue, "&middot;", "'");
		return zValue.toString();
	}
	private static StringBuffer replaceWebForForeignChars(StringBuffer zValue, String zSearch, String zReplace) {
		int pos = 0;
		if (zValue != null) {
			while (true) {
				pos = zValue.indexOf(zSearch, pos);
				if (pos < 0)
					break;
				zValue.replace(pos, pos + zSearch.length(), zReplace);
			}
		}
		return zValue;
	}
	public static String replace(String zValue, String zSearch, String zReplace) {
		if (zSearch.equals(zReplace))
			return zValue;
		int pos = 0;
		StringBuffer out = new StringBuffer(zValue);
		if (out != null) {
			while (true) {
				pos = out.indexOf(zSearch, pos);
				if (pos < 0)
					break;
				out.replace(pos, pos + zSearch.length(), zReplace);
				pos += zReplace.length();
			}
		}
		return out.toString();
	}

	public static String replace(String zValue, String zSearch, String zReplace, int pos) {
		if (zSearch.equals(zReplace))
			return zValue;
		StringBuffer out = new StringBuffer(zValue);
		if (out != null) {
			while (true) {
				pos = out.indexOf(zSearch, pos);
				if (pos < 0)
					break;
				out.replace(pos, pos + zSearch.length(), zReplace);
				pos += zReplace.length();
			}
		}
		return out.toString();
	}
	public static boolean compareCond(Object value, String oper, String cond) throws Exception {
		if (value == null)
			value = "";
		if (cond == null)
			cond = "";
		// if ( oper.equals("=") ) return value.equals(cond);
		// if ( oper.equals("<>") ) return !value.equals(cond);
		if (oper.equals("="))
			return compareObjectsToString((Comparable) value, cond) == 0;
		if (oper.equals("<>"))
			return compareObjectsToString((Comparable) value, cond) != 0;
		if (oper.equals(">"))
			return compareObjectsToString((Comparable) value, cond) > 0;
		if (oper.equals("<"))
			return compareObjectsToString((Comparable) value, cond) < 0;
		if (oper.equals(">="))
			return compareObjectsToString((Comparable) value, cond) >= 0;
		if (oper.equals("<="))
			return compareObjectsToString((Comparable) value, cond) <= 0;
		if (oper.equals("NOT_NULL") && value instanceof String)
			return !"".equals(value);
		if (oper.equals("NOT_NULL") && value instanceof Date)
			return ((Date) value).getTime() > 0;
		if (oper.equals("NOT_NULL") && value instanceof Long)
			return ((Long) value).longValue() != 0;
		if (oper.equals("NOT_NULL") && value instanceof Double)
			return ((Double) value).doubleValue() != 0;
		if (oper.equals("NULL") && value instanceof String)
			return "".equals(value);
		if (oper.equals("NULL") && value instanceof Date)
			return ((Date) value).getTime() == 0;
		if (oper.equals("NULL") && value instanceof Long)
			return ((Long) value).longValue() == 0;
		if (oper.equals("NULL") && value instanceof Double)
			return ((Double) value).doubleValue() == 0;
		JExcepcion.SendError("Operador Invalido");
		return false;
	}

	@SuppressWarnings("unchecked")
	public static int compareObjectsToString(Comparable obj, String str) throws Exception {
		if (str.equals(""))
			return obj.equals("") ? 0 : 1;
		Object aux = null;
		if (obj instanceof Double)
			aux = new Double(str);
		else if (obj instanceof Long)
			aux = new Long(str);
		else if (obj instanceof Integer)
			aux = new Integer(str);
		else if (obj instanceof String)
			aux = str;
		else if (obj instanceof Short)
			aux = new Short(str);
		else if (obj instanceof Byte)
			aux = new Byte(str);
		else if (obj instanceof Float)
			aux = new Float(str);
		else if (obj instanceof Boolean)
			aux = new Boolean(str.equals("S") ? true : false);
		else
			JExcepcion.SendError("OBJETO NO COMPARABLE");
		return obj.compareTo(aux);
	}

	public static String[] stringToLines(final String zMsg, int lineLen, int maxLines, boolean centered,
			boolean cutWords) {
		String vect[] = new String[maxLines];
		int i = 0;
		int j = 0;
		char cMsg[] = (zMsg == null) ? new char[0] : zMsg.toCharArray();
		StringBuffer sLine = new StringBuffer(32);
		for (i = 0, j = 0; i < vect.length && j < cMsg.length; i++) {
			sLine.delete(0, sLine.length());
			int lineIncrement = 1;
			for (int ln = 0; ln < lineLen && j < cMsg.length; ln += lineIncrement) {
				lineIncrement = 1;
				switch (cMsg[j]) {
				case '\r':
				case '\n':
					if (ln == 0 && !(cMsg[j - 1] == '\n' || cMsg[j - 1] == '\r')) {
						lineIncrement = 0;
					} else
						ln = lineLen;
					break;
				case ' ':
				case '\t':
					if (ln == 0) {
						lineIncrement = 0;
						j++;
						continue;
					}
					if (cutWords)
						sLine.append(cMsg[j]);
					else if (ln < (lineLen / 4))
						sLine.append(cMsg[j]);
					else {
						int colsFree = lineLen - ln;
						if ((j + colsFree) >= cMsg.length)
							sLine.append(cMsg[j]);
						else {
							if (zMsg.substring(j + 1, j + colsFree).indexOf(' ') >= 0
									|| zMsg.substring(j + 1, j + colsFree).indexOf('\n') >= 0)
								sLine.append(cMsg[j]);
							else if ((j + colsFree + 1) <= cMsg.length
									&& (zMsg.charAt(j + colsFree) == ' ' || zMsg.charAt(j + colsFree) == '\n'))
								sLine.append(cMsg[j]);
							else
								ln = lineLen;
						}
					}
					break;
				default:
					sLine.append(cMsg[j]);
					break;
				}
				j++;
			}
			vect[i] = sLine.toString().trim();
			if (centered) {
				try {
					vect[i] = JTools.centerString(vect[i], lineLen, ' ');
				} catch (Exception e) {
				}
			} else {
				try {
					vect[i] = JTools.justifyStrings(vect[i], "", lineLen, ' ');
				} catch (Exception e) {
				}
			}
		}

		int vectLen = 0;
		for (vectLen = vect.length - 1; vectLen >= 0
				&& (vect[vectLen] == null || (vect[vectLen] != null && vect[vectLen].trim().length() <= 0)); vectLen--)
			;

		vectLen++;
		String ret[] = new String[vectLen];
		for (i = 0; i < vectLen; i++)
			ret[i] = vect[i];
		return ret;
	}

	public final static String getMacAddress() throws Exception {
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows")) {
			return JTools.windowsParseMacAddress(JTools.windowsRunIpConfigCommand());
		}
		// else if(os.startsWith("Linux")) {
		// return linuxParseMacAddress(linuxRunIfConfigCommand());
		// }
		JExcepcion.SendError("unknown operating system: " + os);
		return null;
	}

	private final static String windowsParseMacAddress(String ipConfigResponse) throws Exception {
		String localHost = JTools.GetHost();

		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;

		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();

			// see if line contains IP address
			if (line.endsWith(localHost) && lastMacAddress != null) {
				return lastMacAddress;
			}

			// see if line contains MAC address
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0)
				continue;

			String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
			if (macAddressCandidate.length() != 17)
				continue;
			lastMacAddress = macAddressCandidate;
			// continue;
			// }
		}
//		JExcepcion.SendError("No se encontro MAC Address");
		return "unknow";
	}

	private final static String windowsRunIpConfigCommand() throws Exception {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();

		stdoutStream.close();

		return outputText;
	}

	public static String writeStringToFile(String fileData, String filePath) throws Exception {
		JTools.createDirectories("", filePath);
		FileOutputStream oOutput = new FileOutputStream(new File(filePath));
		try {
			oOutput.write(JTools.stringToByteArray(fileData));
		} finally {
			oOutput.close();
		}
		return filePath;
	}

	public static String readStringFromFile(String filePath) throws java.io.IOException {
		File f = new File(filePath);
		byte[] array = new byte[(int) f.length()];
		FileInputStream oInput = new FileInputStream(f);
		try {
			oInput.read(array);
		} finally {
			oInput.close();
		}
		return JTools.byteVectorToString(array);
	}

	public static String readFileAsString(String filePath) throws Exception {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		try {
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				fileData.append(buf, 0, numRead);
			}
		} finally {
			reader.close();
		}
		return fileData.toString();
	}

	public static byte[] readFileAsArrayByte(String filePath) throws java.io.IOException {
		File f = new File(filePath);
		FileInputStream reader = new FileInputStream(f);
		byte[] buf;
		try {
			buf = new byte[(int) f.length()];
			reader.read(buf);
		} finally {
			reader.close();
		}
		return buf;
	}

	public static byte[] readResourceAsArrayByte(URL urlPath) throws java.io.IOException {
		InputStream reader = urlPath.openStream();

		byte buf[] = IOUtils.toByteArray(reader);
		return buf;
	}

	public static long readSizeFile(String filePath) throws java.io.IOException {
		File f = new File(filePath);

		return f.length();
	}

	/**
	 * Left Pad a string received
	 * 
	 * @param valueToPad string to left pad
	 * @param filler     pad with this string
	 * @param size       size to pad
	 * @return padded string
	 */
	public static String lpad(String valueToPad, String filler, int size) {
		while (valueToPad.length() < size) {
			valueToPad = filler + valueToPad;
		}
		return valueToPad;
	}
	public static String getUniqueFileName(String directory, String extension) {
		return new File(directory, new StringBuilder().append("prefix").append(new Date().getTime())
				.append(UUID.randomUUID()).append(".").append(extension).toString()).getAbsolutePath();
	}

	// OJO! Phantom No esta implementado multiplataforma! aunque tiene versiones
	// para unix y habria que hacerlo
	// Panthom es browser ciego que devuelve el contenido de html como imagen o pdf
	public final static byte[] convertHtml2PDF(InputStream inputStream) throws Exception {
		String outputFile = new File(getUniqueFileName(JPath.PssPathTempFiles(), "pdf")).getCanonicalPath();
		convertHtml2JPGorPDF(inputStream, outputFile);
		byte[] out = JTools.readFileAsArrayByte(outputFile);
//	  JTools.DeleteFile(outputFile);
		return out;
	}

	public final static String convertHtml2JPG(InputStream inputStream, int w, int h) throws Exception {
		String outputFile = new File(getUniqueFileName(JPath.PssPathTempFiles(), "jpg")).getCanonicalPath();
		convertHtml2JPGorPDF(inputStream, outputFile, w, h);
		byte[] out = JTools.readFileAsArrayByte(outputFile);
		JTools.DeleteFile(outputFile);
		return "data:image/jpg;base64," + (java.util.Base64.getEncoder().encodeToString(out).replaceAll("\r\n", ""));
	}

	public final static String convertHtml2JPGorPDF(String url, String output) throws Exception {
		File f = new File(output);
		String outputFile = JTools.replace(f.getCanonicalPath(), "\\", "/");
		if (outputFile.indexOf(":") != -1)
			outputFile = outputFile.substring(outputFile.indexOf(":") + 1);
		String scriptFile = getUniqueFileName(JPath.PssPathTempFiles(), "js");
		String script = "";
		script += "		var page = require('webpage').create(), \r\n";
		script += "    system = require('system');\r\n";
		script += "    page.open('" + url + "', function (status) {\r\n";
		script += "        if (status !== 'success') {\r\n";
		script += "            console.log('Unable to load the address!');\r\n";
		script += "            phantom.exit(1);\r\n";
		script += "        } else {\r\n";
		script += "            window.setTimeout(function () {\r\n";
		script += "                page.render('" + outputFile + "');\r\n";
		script += "                phantom.exit();\r\n";
		script += "            }, 200); \r\n";
		script += "        }\r\n";
		script += "    }); \r\n";
		JTools.writeStringToFile(script, scriptFile);
		String command = JPath.PssPathBin() + "/phantomjs.exe " + scriptFile;
		PssLogger.logInfo(command);
		Process p = Runtime.getRuntime().exec(command);
		int exitVal = p.waitFor();
		PssLogger.logInfo("exit with val [" + exitVal + "]");
		JTools.DeleteFile(scriptFile);
		return output;
	}

	public final static String convertHtml2JPGorPDF(InputStream inputStream, String output) throws Exception {
		return convertHtml2JPGorPDF(inputStream, output, -1, -1);
	}

	public final static String convertHtml2JPGorPDF(InputStream inputStream, String output, int w, int h)
			throws Exception {
		File f = new File(output);

		String outputFile = JTools.replace(f.getCanonicalPath(), "\\", "/");
		if (outputFile.indexOf(":") != -1)
			outputFile = outputFile.substring(outputFile.indexOf(":") + 1);// probar agregar file:///C:
		String inputFile = new File(getUniqueFileName(JPath.PssPathTempFiles(), "html")).getCanonicalPath();
		inputFile = JTools.replace(inputFile, "\\", "/");
		if (inputFile.indexOf(":") != -1)
			inputFile = inputFile.substring(inputFile.indexOf(":") + 1);// probar agregar file:///C:
		String scriptFile = getUniqueFileName(JPath.PssPathTempFiles(), "js");
		JTools.InputStream2OutputFile(inputStream, inputFile);
		String script = "";
		script += "		var page = require('webpage').create(), \r\n";
		script += "    system = require('system');\r\n";
		if (w != -1 && h != -1)
			script += "    page.viewportSize = { width: " + w + ", height: " + h + " };";
//		else {
//			script+="    page.paperSize = { ";
//			script+="        format: 'A4',";
//			script+="        orientation: 'landscape',";
//			script+="        margin: {";
//			script+="            top: '1cm',";
//			script+="            left: '1,6cm',";
//			script+="            right: '1,6cm',";
//			script+="            bottom: '1cm'";
//			script+="        }";
//			script+="    };";
//		}
		script += "    page.open('" + inputFile + "', function (status) {\r\n";
		script += "        if (status !== 'success') {\r\n";
		script += "            console.log('Unable to load the address!');\r\n";
		script += "            phantom.exit(1);\r\n";
		script += "        } else {\r\n";
		script += "            window.setTimeout(function () {\r\n";
		script += "                page.render('" + outputFile + "');\r\n";
		script += "                phantom.exit();\r\n";
		script += "            }, 1000); \r\n";
		script += "        }\r\n";
		script += "    }); \r\n";
		JTools.writeStringToFile(script, scriptFile);
		String command = JPath.PssPathBin() + "/phantomjs.exe " + scriptFile;
		PssLogger.logInfo(command);
		Process p = Runtime.getRuntime().exec(command);
		int exitVal = p.waitFor();
		PssLogger.logInfo("exit with val [" + exitVal + "]");
//		JTools.DeleteFile(scriptFile);
//		JTools.DeleteFile(inputFile);
		return output;
	}
	public final static String convertScript2Image(String scriptWithImage) throws Exception {
		return convertScript2Image(scriptWithImage, -1, -1);
	}

	public final static String convertScript2Image(String scriptWithImage, int w, int h) throws Exception {
		String pathToResources = "file:///" + JPath.PssPathResourceHtml();
		String i = "";
		i += " <link rel=\"stylesheet\" href=\"" + pathToResources
				+ "/vendor/bootstrap/css/bootstrap.min.css\"></link>";
		i += " <link href=\"" + pathToResources
				+ "/css/nv.d3.css\" media=\"all\" type=\"text/css\" rel=\"stylesheet\"></link>";
		i += " <link href=\"" + pathToResources
				+ "/vendor/jqueryui/jquery-ui.min.css\" media=\"all\" type=\"text/css\" rel=\"stylesheet\"></link>";
		i += " <link type=\"text/css\" rel=\"stylesheet\" href=\"" + pathToResources
				+ "/vendor/font-awesome/js/all.min.js\"></link>";
		i += " <script type=\"text/javascript\" src=\"https://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false&amp;language=es\"></script>";
		i += " <script type=\"text/javascript\" src=\"" + pathToResources + "/js/map.js\"></script>";
		i += " <script src=\"" + pathToResources + "/vendor/jquery/jquery.min.js\"></script>";
		i += " <script src=\"" + pathToResources + "/vendor/raphael/raphael.min.js\"></script>";
		i += " <script src=\"" + pathToResources + "/vendor/moment/moment-with-locale.js\"></script>";
		i += " <script type=\"text/javascript\" src=\"" + pathToResources + "/vendor/d3/d3.min.js\"></script>";
		i += " <script type=\"text/javascript\" src=\"" + pathToResources + "/vendor/d3/d3.v5.min.js\"></script>";
		i += " <script type=\"text/javascript\" src=\"" + pathToResources + "/vendor/d3/nv.d3.js\"></script>";
		i += " <script type=\"text/javascript\" src=\"" + pathToResources + "/vendor/d3/d3.tip.v0.6.3.js\"></script>";
		i += " <script type=\"text/javascript\" src=\"" + pathToResources + "/js/three.min.js\"></script>";
		i += " <script type=\"text/javascript\" src=\"" + pathToResources + "/js/three-text2d.js\"></script>";
		i += " <script src=\"" + pathToResources + "/js/gauge.js\"></script>";
		i += " <script src=\"" + pathToResources + "/js/gaugev5.js\"></script>";
		String html = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"><html><head>";
		html += i;
//		html+="<style type=\"text/css\">" + s + "</style>";
		html += "</head><body>";
		html += scriptWithImage;
		html += "</body></html>";

		String outputFile = JTools.replace(getUniqueFileName(JPath.PssPathTempFiles(), "jpg"), "\\", "/");
		if (outputFile.indexOf(":") != -1)
			outputFile = outputFile.substring(outputFile.indexOf(":") + 1);// probar agregar file:///C:
		String inputFile = new File(getUniqueFileName(JPath.PssPathTempFiles(), "html")).getCanonicalPath();
		inputFile = JTools.replace(inputFile, "\\", "/");
		if (inputFile.indexOf(":") != -1)
			inputFile = inputFile.substring(inputFile.indexOf(":") + 1);// probar agregar file:///C:

		String scriptFile = getUniqueFileName(JPath.PssPathTempFiles(), "js");
		JTools.writeStringToFile(html, inputFile);
		String script = "";
		script += "		var page = require('webpage').create(), \r\n";
		script += "    system = require('system');\r\n";
		if (w != -1 && h != -1)
			script += "    page.viewportSize = { width: " + w + ", height: " + h + " };";
		script += "    page.open('" + inputFile + "', function (status) {\r\n";
		script += "        if (status !== 'success') {\r\n";
		script += "            console.log('Unable to load the address!');\r\n";
		script += "            phantom.exit(1);\r\n";
		script += "        } else {\r\n";
		script += "            window.setTimeout(function () {\r\n";
		script += "                page.render('" + outputFile + "');\r\n";
		script += "                phantom.exit();\r\n";
		script += "            }, 200); \r\n";
		script += "        }\r\n";
		script += "    }); \r\n";
		JTools.writeStringToFile(script, scriptFile);
		String command = JPath.PssPathBin() + "/phantomjs.exe " + scriptFile;
		PssLogger.logInfo(command);
		Process p = Runtime.getRuntime().exec(command);
		int exitVal = p.waitFor();
		PssLogger.logInfo("exit with val [" + exitVal + "]");
		JTools.DeleteFile(scriptFile);
		// JTools.DeleteFile(inputFile);
		byte[] output = JTools.readFileAsArrayByte(outputFile);
		// TJTools.DeleteFile(outputFile);
		return "data:image/jpg;base64," + (java.util.Base64.getEncoder().encodeToString(output).replaceAll("\r\n", ""));
	}
	public static void main(String[] args) {
		try {
//			FileInputStream i = new FileInputStream(new File(JPath.PssPathTempFiles()+"/a.html"));
//			convertHtml2JPGorPDF(i,JPath.PssPathTempFiles()+"/output.jpg");
			crc16_prueba("@0100000001000065F300F112211221122112211220D", 0);
		} catch (Throwable e) {
			PssLogger.logError(e);
			System.exit(1);
		}
	}

	public static String crc16_prueba(String data, int start) {
		byte[] bytes = data.getBytes(Charset.forName("ISO-8859-1"));
		int crc = start;
		int crcdiv = 0x8005;
		int data_store;
		for (byte b : bytes) {
			data_store = b;
			data_store <<= 8;
			for (int i = 0; i < 8; i++) {
				if (((data_store ^ crc) & 0x8000) != 0) {
					crc <<= 1;
					crc ^= crcdiv;
				} else {
					crc <<= 1;
				}
				data_store <<= 1;
			}
		}
		crc &= 0xFFFF;

		System.out.println("" + Integer.toHexString(crc));

		return "" + crc;
	}

	public static boolean InputStream2OutputFile(InputStream inputStream, String outputFileName)
			throws FileNotFoundException {
		FileOutputStream outputStream = new FileOutputStream(new File(outputFileName));
		return InputStream2OutputStream(inputStream, outputStream);

	}

	public static boolean InputStream2OutputStream(InputStream inputStream, OutputStream outputStream) {

		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return true;
	}

	public static List<JarEntry> getJarContent(String jarPath, String directory) throws IOException {
		List<JarEntry> content = new ArrayList<JarEntry>();
		String jarName = jarPath;
		jarName = jarPath;
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarName);
			Enumeration<JarEntry> e = jarFile.entries();
			while (e.hasMoreElements()) {
				JarEntry entry = (JarEntry) e.nextElement();
				if (!entry.getName().startsWith(directory))
					continue;
				if (entry.getName().substring(directory.length() + 1).equals(""))
					continue;
				if (!entry.getName().endsWith("/")) {
					if (entry.getName().substring(directory.length() + 1).indexOf("/") != -1)
						continue;
				} else if (entry.getName().substring(directory.length() + 1, entry.getName().lastIndexOf("/"))
						.indexOf("/") != -1)
					continue;
				content.add(entry);
			}
		} finally {
			if (jarFile != null)
				jarFile.close();
		}
		return content;
	}

	/**
	 * @param valueToPad
	 * @param filler
	 * @param size
	 * @return
	 */
	public static String rpad(String valueToPad, String filler, int size) {
		while (valueToPad.length() < size) {
			valueToPad = valueToPad + filler;
		}
		return valueToPad;
	}

	private static int lento(int zLen, int zMult) {
		int nAux = zLen % zMult;

		if (nAux != 0) {
			nAux = zMult - nAux;
		}
		return zLen + nAux;
	}

	public static String bin2hexStr(String binStr, int len) {
		char charbin[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		String ret = "";

		for (int i = 0; i < len; i++) {
			ret += "" + charbin[(binStr.charAt(i) & 0xf0) >>> 4] + "" + charbin[binStr.charAt(i) % 0x10];
		}

		return ret;
	}

	public static String bin2hex(String zStr) throws Exception {
		String sValorHexa;
		String sValorBin;
		int iIndex;
		int iIndex2;
		int iLen;

		sValorHexa = "";
		iLen = lento(zStr.length(), 4);

		for (iIndex = 0; iIndex <= (iLen / 4) - 1; iIndex++) {
			iIndex2 = 1 + (iIndex * 4);

			sValorBin = zStr.substring(iIndex2, iIndex + 4);
			if (sValorBin.equals("0000"))
				sValorHexa += "0";
			else if (sValorBin.equals("0001"))
				sValorHexa += "1";
			else if (sValorBin.equals("0010"))
				sValorHexa += "2";
			else if (sValorBin.equals("0011"))
				sValorHexa += "3";
			else if (sValorBin.equals("0100"))
				sValorHexa += "4";
			else if (sValorBin.equals("0101"))
				sValorHexa += "5";
			else if (sValorBin.equals("0110"))
				sValorHexa += "6";
			else if (sValorBin.equals("0111"))
				sValorHexa += "7";
			else if (sValorBin.equals("1000"))
				sValorHexa += "8";
			else if (sValorBin.equals("1001"))
				sValorHexa += "9";
			else if (sValorBin.equals("1010"))
				sValorHexa += "A";
			else if (sValorBin.equals("1011"))
				sValorHexa += "B";
			else if (sValorBin.equals("1100"))
				sValorHexa += "C";
			else if (sValorBin.equals("1101"))
				sValorHexa += "D";
			else if (sValorBin.equals("1110"))
				sValorHexa += "E";
			else if (sValorBin.equals("1111"))
				sValorHexa += "F";
			else
				throw new Exception("invalid hex value");
		}

		return sValorHexa;
	}

	public static String hexStr2bin(String hexStr, int len) {
		int i;
		StringBuffer retStr = new StringBuffer("");
		if (hexStr.length() % 2 != 0)
			hexStr += "0";
		for (i = 0; i < len && i < (hexStr.length() - 1); i += 2) {
			retStr.append((char) (hexchar2bin(hexStr.charAt(i)) * 0x10 + hexchar2bin(hexStr.charAt(i + 1))));
		}
		return retStr.toString();
	}

	private static int hexchar2bin(char ch) {
		return (ch >= '0' && ch <= '9') ? ch - '0'
				: (ch >= 'a' && ch <= 'f') ? ch - 'a' + 10 : (ch >= 'A' && ch <= 'F') ? ch - 'A' + 10 : ch & 0x0f;
	}

	public static Object nvl(Object a, Object b) {
		if (a == null)
			return b;
		else
			return a;
	}

	public static String getNroCuitFormateado(String nrocuit) throws Exception {
		if (nrocuit.length() < 11)
			return nrocuit;
		if (nrocuit.indexOf("-") != -1)
			return nrocuit;
		return nrocuit.substring(0, 2) + "-" + nrocuit.substring(2, 2 + 8) + "-" + nrocuit.substring(10);
	}
	public static char[] byteToChar(byte bytearr[]) {
		int iTotalBytesRead = bytearr.length;
		char charArray[] = new char[iTotalBytesRead];
		for (int i = 0; i < iTotalBytesRead; i++)
			charArray[i] = (char) (bytearr[i] & 0xFF);
		return charArray;
	}

	public static byte[] stringToByteArray(String zMsg) {
		char cArray[] = zMsg.toCharArray();
		return JTools.charArrayToByteArray(cArray);
	}

	public static byte[] charArrayToByteArray(char cArray[]) {
		byte[] msgAux = new byte[cArray.length];

		for (int i = 0; i < cArray.length; i++) {
			msgAux[i] = (byte) (cArray[i]);
		}
		return msgAux;
	}

	public static char[] concat(char[] a, char[] b) {
		char[] c = java.util.Arrays.copyOfRange(a, 0, a.length + b.length);
		for (int i = a.length; i < a.length + b.length; i++) {
			c[i] = b[i - a.length];
		}
		return c;
	}

	public static byte[] concat(byte[] a, byte[] b) {
		byte[] c = java.util.Arrays.copyOfRange(a, 0, a.length + b.length);
		for (int i = a.length; i < a.length + b.length; i++) {
			c[i] = b[i - a.length];
		}
		return c;
	}

	public static boolean hasValue(String value) {
		if (value == null)
			return false;
		return !value.equals("");
	}

	public static boolean isAlphaNumeric(final String s) {
		final char[] chars = s.toCharArray();
		for (int x = 0; x < chars.length; x++) {
			final char c = chars[x];
			if ((c >= 'a') && (c <= 'z'))
				continue; // lowercase
			if ((c >= 'A') && (c <= 'Z'))
				continue; // uppercase
			if ((c >= '0') && (c <= '9'))
				continue; // numeric
			return false;
		}
		return true;
	}
	public static boolean isAlpha(final String s) {
		final char[] chars = s.toCharArray();
		for (int x = 0; x < chars.length; x++) {
			final char c = chars[x];
			if ((c >= 'a') && (c <= 'z'))
				continue; // lowercase
			if ((c >= 'A') && (c <= 'Z'))
				continue; // uppercase
			if ((c == ' '))
				continue; // uppercase
			return false;
		}
		return true;
	}
	public static void checkXMLLine(String line) throws Exception {
		line = line.replace("<", "&lt;");
		line = line.replace(">", "&gt;");
		line = line.replace("\"", "&quot;");
		line = line.replace("\\", "\\\\");
	}

	public static final byte[] rjlDecode(String b) {
		if (b.indexOf("|") == -1) {
			b = "0" + JTools.HexToAscii(b.substring(1));
		}
		int i = 10;
		int j = 0;
		int count = Integer.parseInt(b.substring(0, 10));
		byte[] buffer = new byte[count];
		while (i < b.length()) {
			char c1 = b.charAt(i);
			if (c1 == '|') {
				buffer[j++] = (byte) (Integer.parseInt(b.substring(i + 1, i + 3), 16) & 0x00ff);
				i += 3;
			} else {
				buffer[j++] = (byte) c1;
				i++;
			}

		}
		return buffer;
	}

	public static final String rjlEncode(byte[] b) {
		// Each byte in b is represented by 3 characters
		StringBuffer buffer = new StringBuffer();
		long count = b.length;
		buffer.append(JTools.LPad("" + count, 10, "0"));
		for (int i = 0; i < count; i++) {
			char ch = (char) (b[i] & 0x00ff);
			if (Character.isLetterOrDigit(ch))
				buffer.append(ch);
			else
				buffer.append("|" + CharToHex(ch));
		}
		return buffer.toString();
	}
	public static String generateBusqueda(String texto) throws Exception {
		return generateBusqueda(texto, false);
	}

	public static String generateBusqueda(String texto, boolean generate) throws Exception {
		String out = texto.toUpperCase();
		out = buscarSinonimo(out, generate);
		if (generate)
			out += texto.toUpperCase().replace('\\', ' ');
		return out;
	}

	public static String buscarSinonimo(String texto, boolean generate) throws Exception {
		Set<String> list = new TreeSet<String>();
		String out = "";
		String busqueda = "(";
		StringTokenizer toks = new StringTokenizer(texto, " ,;");
		while (toks.hasMoreTokens()) {
			String palabra = toks.nextToken();
			String sinonimo = buscarSinonimosPalabra(palabra);
			out += sinonimo + " ";
			busqueda += "(.*" + sinonimo + ".*)";
			list.add(sinonimo);
		}
		busqueda += "|";
		for (String l : list) {
			busqueda += "(.*" + l + ".*)";
			out += l + " ";
		}
		busqueda += ")";

		return generate ? out : busqueda;
	}
	public static String buscarSinonimosPalabra(String texto) throws Exception {
		if (texto.trim().equals("SA"))
			return "ANONSOC";
		if (texto.trim().equals("S.A"))
			return "ANONSOC";
		if (texto.trim().equals("S.A."))
			return "ANONSOC";
		if (texto.trim().equals("SOC.ANON."))
			return "ANONSOC";
		if (texto.trim().equals("SRL"))
			return "LIMRESPSOC";
		if (texto.trim().equals("S.R.L"))
			return "LIMRESPSOC";
		if (texto.trim().equals("S.R.L."))
			return "LIMRESPSOC";
		if (texto.trim().equals("ASOCIASION"))
			return "ASOC";
		if (texto.trim().equals("ASOCIACION"))
			return "ASOC";
		if (texto.trim().equals("COMERCIAL"))
			return "COM";
		if (texto.trim().equals("SOCIEDAD"))
			return "SOC";
		if (texto.trim().equals("SOC."))
			return "SOC";
		if (texto.trim().equals("ANONIMA"))
			return "ANON";
		if (texto.trim().equals("ANON."))
			return "ANON";
		if (texto.trim().equals("CAPITAL FEDERAL"))
			return "CAPFED";
		if (texto.trim().equals("C.F"))
			return "CAPFED";
		if (texto.trim().equals("C.F."))
			return "CAPFED";
		if (texto.trim().equals("CF"))
			return "CAPFED";
		if (texto.trim().equals("RESPONSABILIDAD"))
			return "RESP";
		if (texto.trim().equals("RESPONS"))
			return "RESP";
		if (texto.trim().equals("LIMITADA"))
			return "LIM";
		return limpiarSimbolos(texto.trim());
	}
	public static String limpiarSimbolos(String texto) throws Exception {
		StringBuilder out = new StringBuilder();
		for (int c = 0; c < texto.length(); c++) {
			if (texto.charAt(c) == 'á' && texto.charAt(c) == 'Á')
				out.append('A');
			else if (texto.charAt(c) == 'é' || texto.charAt(c) == 'É')
				out.append('E');
//			else if (texto.charAt(c)=='S' || texto.charAt(c)=='Z' || texto.charAt(c)=='X') out.append('S');
			else if (texto.charAt(c) == 'í' || texto.charAt(c) == 'í')
				out.append('I');
			else if (texto.charAt(c) == 'ó' || texto.charAt(c) == 'Ó')
				out.append('O');
			else if (texto.charAt(c) == 'ú' || texto.charAt(c) == 'Ú' || texto.charAt(c) == 'ü'
					|| texto.charAt(c) == 'Ü')
				out.append('U');
//			else if (texto.charAt(c)=='Y' || texto.charAt(c)=='y') out.append('I');
			else if (texto.charAt(c) == 'ñ' || texto.charAt(c) == 'Ñ')
				out.append('N');
			else if (texto.charAt(c) == 'B' || texto.charAt(c) == 'b')
				out.append('V');
			else if (texto.charAt(c) >= '0' && texto.charAt(c) <= '9')
				out.append(texto.charAt(c));
			else if (texto.charAt(c) >= 'A' && texto.charAt(c) <= 'Z')
				out.append(texto.charAt(c));
			else if (texto.charAt(c) >= 'a' && texto.charAt(c) <= 'z')
				out.append(texto.charAt(c));
		}
		return out.toString();
	}

	public static String rTrim(String value) {
		int len = value.length();
		char[] val = value.toCharArray(); /* avoid getfield opcode */

		while (len > 0 && val[len - 1] == ' ') {
			len--;
		}
		return value.substring(0, len);
	}

	public static String lTrim(String value) {
		int len = value.length();
		char[] val = value.toCharArray(); /* avoid getfield opcode */
		int i = 0;
		while (i < len && val[i] == ' ')
			i++;
		return value.substring(i);
	}

	public static boolean isEncodeURL(String value) throws Exception {
		if (value.indexOf("%") == -1)
			return false;
		if (value.indexOf("% ") == -1)
			return false;
		try {
			URLDecoder.decode(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String decodeURLIfNecesary(String value) throws Exception {
		if (value.indexOf("%") == -1)
			return value;
		if (value.indexOf("% ") != -1)
			return value;
		try {
			return decodeURL(value);
		} catch (Exception e) {
		}
		return value;
	}

	public static String encodeURL(String decoded) throws Exception {
		return URLEncoder.encode(decoded, "UTF-8").replace("+", "%20");
	}

	public static String decodeURL(String encoded) throws Exception {
		return URLDecoder.decode(encoded, "UTF-8");
	}

	public static String encodeString2(String line) {
		if (line == null)
			return "";
		line = line.replace("<", "&lt;");
		line = line.replace(">", "&gt;");
//		line = line.replace("'", "&apos;");
//		line = line.replace("\"", "&quot;");
		line = line.replace("\\", "\\\\");
		line = line.replace("á", "&aacute;");
		line = line.replace("é", "&eacute;");
		line = line.replace("í", "&iacute;");
		line = line.replace("ó", "&oacute;");
		line = line.replace("ú", "&uacute;");
		line = line.replace("ñ", "&ntilde;");
		line = line.replace("Á", "&Aacute;");
		line = line.replace("É", "&Eacute;");
		line = line.replace("Í", "&Iacute;");
		line = line.replace("Ó", "&Oacute;");
		line = line.replace("Ú", "&Uacute;");
		line = line.replace("ñ", "&ntilde;");
		line = line.replace("Ñ", "&Ntilde;");

		return line;
	}

	public static String encodeStringfull(String line) {
		if (line == null)
			return "";
		return StringEscapeUtils.escapeHtml4(line);
//		line = line.replace("<", "&lt;");
//		line = line.replace(">", "&gt;");
//		line = line.replace("'", "&apos;");
//		line = line.replace("\"", "&quot;");
//		line = line.replace("\\", "\\\\");
//		line = line.replace("á", "&aacute;");
//		line = line.replace("é", "&eacute;");
//		line = line.replace("í", "&iacute;");
//		line = line.replace("ó", "&oacute;");
//		line = line.replace("ú", "&uacute;");
//		line = line.replace("ñ", "&ntilde;");
//		line = line.replace("Á", "&Aacute;");
//		line = line.replace("É", "&Eacute;");
//		line = line.replace("Í", "&Iacute;");
//		line = line.replace("Ó", "&Oacute;");
//		line = line.replace("Ú", "&Uacute;");
//		line = line.replace("ñ", "&ntilde;");
//		line = line.replace("Ñ", "&Ntilde;");

//		return line;
	}

	public static String encodeString(String line) {
		if (line == null)
			return "";
		line = line.replace("&", "&amp;");
		line = line.replace("<", "&lt;");
		line = line.replace(">", "&gt;");
		line = line.replace("\"", "&quot;");
		line = line.replace("'", "&apos;");
		line = line.replace("\\", "\\\\");
//		line = line.replace("á", "&aacute;");
//		line = line.replace("é", "&eacute;");
//		line = line.replace("í", "&iacute;");
//		line = line.replace("ó", "&oacute;");
//		line = line.replace("ú", "&uacute;");
//		line = line.replace("ñ", "&ntilde;");
//		line = line.replace("Á", "&Aacute;");
//		line = line.replace("É", "&Eacute;");
//		line = line.replace("Í", "&Iacute;");
//		line = line.replace("Ó", "&Oacute;");
//		line = line.replace("Ú", "&Uacute;");

//		line = line.replace("á", "&aacute;");
//		line = line.replace("é", "&eacute;");
//		line = line.replace("í", "&iacute;");
//		line = line.replace("ó", "&oacute;");
//		line = line.replace("ú", "&uacute;");
//		line = line.replace("ñ", "&ntilde;");
//		line = line.replace("Á", "&Aacute;");
//		line = line.replace("É", "&Eacute;");
//		line = line.replace("Í", "&Iacute;");
//		line = line.replace("Ó", "&Oacute;");
//		line = line.replace("Ú", "&Uacute;");

		return line;
	}

	public static String unencodeString(String line) throws Exception {
		line = line.replace("&lt;", "<");
		line = line.replace("&gt;", ">");
		line = line.replace("&quot;", "\"");
		line = line.replace("\\\\", "\\");
		line = line.replace("&amp;", "&");
		return line;
	}

	// Substring inteligente, que no da error de indice fuera de rango
	public static String SubStrClever(String value, int desde, int hasta) throws Exception {
		String ret = value;
		int largo = value.length();
		if (largo <= hasta)
			return ret.substring(desde, largo);
		return ret.substring(desde, hasta);
	}

	public static double findMin(double m1, double m2) throws Exception {
		if (m1 == 0)
			return 0d;
		if (m2 == 0)
			return 0d;
//		if (m1<0 && m2>0) return m1;
//		if (m2<0 && m1>0) return m2;
		if (m1 < 0 && m2 > 0)
			return m1; // si le tengo que pagar 100 pero me ponen -10, debe quedar -10
		if (m2 < 0 && m1 > 0)
			return m2;
		if (m2 < 0 && m1 < 0)
			return Math.max(m1, m2);
		if (m2 > 0 && m1 > 0)
			return Math.min(m1, m2);
		return m2; // el faltante
//		if (Math.abs(m1)<Math.abs(m2)) return m1;
//		if (Math.abs(m2)<Math.abs(m1)) return m2;
//		return Math.min(m1, m2);
	}

	// Sirve para truncar un string, a una longitud dada, empezando por la izquierda
	public static String trunc(String value, int longitud) throws Exception {
		return SubStrClever(value, 0, longitud);
	}

	public static boolean pingODBC(String direccion) throws IOException {
		int init = direccion.indexOf("//");
		if (init == -1)
			return true; // no lo interpreto no lo puedo controlar
		int fin = direccion.substring(init).indexOf(":");
		if (fin == -1) {
			fin = direccion.substring(init + 2).indexOf("/");
			if (fin == -1)
				return true; // no lo interpreto no lo puedo controlar
			fin += 2;
		}
		String host = direccion.substring(init + 2, init + fin);
		InetAddress in;
		in = InetAddress.getByName(host);
		return in.isReachable(2000);
	}

	public static boolean ping(String direccion) throws IOException {
		InetAddress in;
		in = InetAddress.getByName(direccion);
		return in.isReachable(5000);
	}
	public static String justificar(String texto, int lenTotal) throws Exception {
		int faltan = lenTotal - texto.length();
		if (faltan == 0)
			return texto;
//		if (texto.trim().charAt(texto.trim().length()-1)=='.') return texto; // si termina en . no se justifica
		StringBuffer buf = new StringBuffer();
		String textoPuro = JTools.lTrim(texto);
		int start = texto.length() - textoPuro.length();
		buf.append(JTools.RPad("", start, " "));
		int len = textoPuro.length();
		for (int i = 0; i < len; i++) {
			buf.append(textoPuro.charAt(i));
			if (textoPuro.charAt(i) == ' ') {
				buf.append(' ');
				faltan--;
			}
			if (faltan <= 0) {
				buf.append(textoPuro.substring(i + 1));
				break;
			}
		}
		return buf.toString();
	}

	public static String encodeUTF8(String zValue) {
		return new String(zValue.getBytes(Charset.forName("UTF-8")), Charset.forName("Windows-1252"));
	}

	private static final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";

	public static final GeoPosition getGeoPositionFromAdress(String address) throws Exception {
		URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address="
				+ (URLEncoder.encode(address, "UTF-8").replace("+", "%20")) + "&sensor=false");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document geocoderResultDocument = null;
		try {
			conn.connect();
			InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
			geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(geocoderResultInputSource);
		} finally {
			conn.disconnect();
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList resultNodeList = null;

		resultNodeList = ((NodeList) xpath.evaluate("/GeocodeResponse/status", geocoderResultDocument,
				XPathConstants.NODESET));
		if (resultNodeList.item(0).getChildNodes().item(0).getNodeValue().equals("ZERO_RESULTS")) {
			return null;
		}

		// c) extract the coordinates of the first result
		resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/geometry/location/*",
				geocoderResultDocument, XPathConstants.NODESET);
		float lat = Float.NaN;
		float lng = Float.NaN;
		int i = 0;
		for (; i < resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if ("lat".equals(node.getNodeName()))
				lat = Float.parseFloat(node.getChildNodes().item(0).getNodeValue());
			if ("lng".equals(node.getNodeName()))
				lng = Float.parseFloat(node.getChildNodes().item(0).getNodeValue());
		}
		if (i == 0)
			throw new Exception("Fin del servicio");
		return new GeoPosition(lat, lng);

	}

	// Haversine Formula

	private static int calculateDistance(GeoPosition p1, GeoPosition p2) {
		double lat1, lon1, lat2, lon2;
		double earthRadius = 6371; // km

		lat1 = Math.toRadians(p1.getLatitude());
		lon1 = Math.toRadians(p1.getLongitude());
		lat2 = Math.toRadians(p2.getLatitude());
		lon2 = Math.toRadians(p2.getLongitude());

		double dlon = (lon2 - lon1);
		double dlat = (lat2 - lat1);

		double sinlat = Math.sin(dlat / 2);
		double sinlon = Math.sin(dlon / 2);

		double a = (sinlat * sinlat) + Math.cos(lat1) * Math.cos(lat2) * (sinlon * sinlon);
		double c = 2 * Math.asin(Math.min(1.0, Math.sqrt(a)));

		double distanceInMeters = earthRadius * c * 1000;

		return (int) distanceInMeters;

	}

	public static String generateCaptcha() throws Exception {
		String[] token = new String[] { "3", "R", "c", "t", "9", "n", "X", "a", "j", "8", "d", "p", "F", "y", "7", "h",
				"k" };
		java.util.List l = Arrays.asList(token);
		Collections.shuffle(l);

		String captcha = token[0] + token[1] + token[2] + token[3];
		return captcha;
	}

public static String buildCaptcha(String captcha, int w,int h) throws Exception {

		 
		 
		 int width = w, height = h;

		 // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
		 // into integer pixels
		 BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		 Graphics2D ig2 = bi.createGraphics();

		 Font font = new Font("TimesRoman", Font.PLAIN, h-15);
		 if (font==null) font = new Font("Arial", Font.PLAIN, h-15);
		 if (font==null) font = new Font("FreeSerif", Font.PLAIN, h-15);
		 if (font==null) font = new Font("Kalimati", Font.PLAIN, h-15);
		 ig2.setFont(font);
		 FontMetrics fm = ig2.getFontMetrics();

		 Random r = new Random();
		 int oj=0,oi=0;
		 ig2.setColor(new Color(100,100,100));
		 for(int j=0;j<w;j+=1)
		 
		 for(int i=0;i<w;i+=3) {
		 if (r.nextInt(10)>2) { 
		 oi=i;
		 continue;
		 }
		 if (r.nextInt(10)>4) { 
		 oj=j;
		 continue;
		 }
		 if (r.nextInt(10)>8) {
		 ig2.drawLine(oj, oi, i, j);
		 continue;
		 }
		 if (r.nextInt(10)>8) { 
		 ig2.drawLine(oi, oj, j, i);
		 continue;
		 };
		 if (r.nextInt(10)==1) { 
		 ig2.drawOval(r.nextInt(w), r.nextInt(h),r.nextInt(w/2), r.nextInt(h/2));
		 continue;
		 };
		 }
		 
		 ig2.setColor(new Color(0,0,0));
		 int posX =5;
		 int a = r.nextInt(4)-4;
		 int b = r.nextInt(4)-4;
		 int c = r.nextInt(4)-4;
		 int d = r.nextInt(4)-4;
		 ig2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		 ig2.drawString(""+captcha.charAt(0), posX, fm.getHeight() + a);
		 posX+=fm.charWidth(captcha.charAt(0));
		 ig2.drawString(""+captcha.charAt(1), posX, fm.getHeight() + b);
		 posX+=fm.charWidth(captcha.charAt(1));
		 ig2.drawString(""+captcha.charAt(2), posX, fm.getHeight() + c);
		 posX+=fm.charWidth(captcha.charAt(2));
		 ig2.drawString(""+captcha.charAt(3), posX, fm.getHeight() + d);
		 
		 posX =3;
		 ig2.setColor(Color.YELLOW); // new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		 ig2.drawString(""+captcha.charAt(0), posX, fm.getHeight() + a);
		 posX+=fm.charWidth(captcha.charAt(0));
		 ig2.setColor(Color.YELLOW); // new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		 ig2.drawString(""+captcha.charAt(1), posX, fm.getHeight() + b);
		 posX+=fm.charWidth(captcha.charAt(1));
		 ig2.setColor(Color.YELLOW); // new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		 ig2.drawString(""+captcha.charAt(2), posX, fm.getHeight() + c);
		 posX+=fm.charWidth(captcha.charAt(2));
		 ig2.setColor(Color.YELLOW); // new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		 ig2.drawString(""+captcha.charAt(3), posX, fm.getHeight() + d);
		 
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 ImageIO.write(bi, "PNG", out);
		 return "data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(out.toByteArray()).replaceAll("\r\n", ""));


	}
public static int crc16(String data) throws Exception {
	byte[] bytes = data.getBytes(Charset.forName("ISO-8859-1"));
	int crc = 0x0000;
	for (byte b : bytes) {
		crc = (crc >>> 8) ^ crcTable[(crc ^ b) & 0xff];
	}
	System.out.println("CRC16 = " + Integer.toHexString(crc));
	return crc;
}

public static int crc16(byte[] bytes) throws Exception {

	int crc = 0x0000;
	for (byte b : bytes) {
		crc = (crc >>> 8) ^ crcTable[(crc ^ b) & 0xff];
	}
	System.out.println("CRC16 = " + Integer.toHexString(crc));
	return crc;
}
   

  /**
   * Returns a pseudo-random number between min and max, inclusive.
   * The difference between min and max can be at most
   * <code>Integer.MAX_VALUE - 1</code>.
   *
   * @param min Minimum value
   * @param max Maximum value.  Must be greater than min.
   * @return Integer between min and max, inclusive.
   * @see java.util.Random#nextInt(int)
   */
  public static int randInt(int min, int max) {

      // NOTE: Usually this should be a field rather than a method
      // variable so that it is not re-seeded every call.
      Random rand = new Random();

      // nextInt is normally exclusive of the top value,
      // so add 1 to make it inclusive
      int randomNum = rand.nextInt((max - min) + 1) + min;

      return randomNum;
  }
  public static void generateQRCode(String code,OutputStream fout,ImageType type,int width,int height) throws Exception {
		ByteArrayOutputStream out = QRCode.from(code).withSize(width,height).to(type).stream();
		fout.write(out.toByteArray());
		fout.flush();
		fout.close();
	}

	public static void generateQRCode(String code, ImageType type, String imageFile, String fondo, int posX, int posY,
			int width, int height) throws Exception {
		if (fondo == null) {
			FileOutputStream oOutput = new FileOutputStream(new File(imageFile));
			oOutput.write(QRCode.from(code).withSize(width, height).to(type).stream().toByteArray());
			oOutput.close();
			return;
		}
		File file1 = new File(fondo);
		BufferedImage img1 = ImageIO.read(file1);
		BufferedImage img2 = ImageIO.read(
				new ByteArrayInputStream(QRCode.from(code).withSize(width, height).to(type).stream().toByteArray()));

		int widthImg1 = img1.getWidth();
		int heightImg1 = img1.getHeight();

		if (widthImg1 == 0)
			return;
		BufferedImage img = new BufferedImage(widthImg1, heightImg1, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = img.createGraphics();

		gr.drawImage(img1, 0, 0, null); // 0, 0 are the x and y positions
		gr.drawImage(img2, posX, posY, null); // here width is mentioned as width of

		gr.dispose();

		FileOutputStream oOutput = new FileOutputStream(new File(imageFile));
		ImageIO.write(img, (type.equals(ImageType.PNG) ? "png" : type.equals(ImageType.JPG) ? "jpg" : "gif"), oOutput);
		oOutput.close();

	}

	public static String getBodyHTML(String html) throws Exception {
		org.jsoup.nodes.Document document = Jsoup.parse(html);
		return document.getElementsByClass("body").html();
	}

	public static String inlineStyles(String html, String css, boolean removeClasses) throws IOException {
		org.jsoup.nodes.Document document = Jsoup.parse(html);
		CSSOMParser parser = new CSSOMParser();
		org.w3c.css.sac.InputSource source = new org.w3c.css.sac.InputSource(new StringReader(css));
		CSSStyleSheet stylesheet = parser.parseStyleSheet(source, null, null);

		CSSRuleList ruleList = stylesheet.getCssRules();
		Map<Element, Map<String, String>> allElementsStyles = new HashMap();
		for (int ruleIndex = 0; ruleIndex < ruleList.getLength(); ruleIndex++) {
			CSSRule item = ruleList.item(ruleIndex);
			if (item instanceof CSSStyleRule) {
				CSSStyleRule styleRule = (CSSStyleRule) item;
				String cssSelector = styleRule.getSelectorText();
				if (cssSelector.indexOf(":") != -1)
					continue;
				Elements elements = document.select(cssSelector);
				for (int elementIndex = 0; elementIndex < elements.size(); elementIndex++) {
					Element element = elements.get(elementIndex);
					Map<String, String> elementStyles = allElementsStyles.get(element);
					if (elementStyles == null) {
						elementStyles = new LinkedHashMap<String, String>();
						allElementsStyles.put(element, elementStyles);
					}
					CSSStyleDeclaration style = styleRule.getStyle();
					for (int propertyIndex = 0; propertyIndex < style.getLength(); propertyIndex++) {
						String propertyName = style.item(propertyIndex);
						String propertyValue = style.getPropertyValue(propertyName);
						elementStyles.put(propertyName, propertyValue);
					}
				}
			}
		}

		for (Map.Entry<Element, Map<String, String>> elementEntry : allElementsStyles.entrySet()) {
			Element element = elementEntry.getKey();
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<String, String> styleEntry : elementEntry.getValue().entrySet()) {
				builder.append(styleEntry.getKey()).append(":").append(styleEntry.getValue()).append(";");
			}
			builder.append(element.attr("style"));
			element.attr("style", builder.toString());
			if (removeClasses) {
				element.removeAttr("class");
			}
		}
		String s = null;
		try {
			s = document.html();
		} catch (OutOfMemoryError e) {
			s = "Documento muy extenso";
		}

		return s;
	}

	public static String enmascararTarjeta(String nro) throws Exception {
		if (nro == null)
			return "";
		if (nro.length() < 14)
			return nro;
		return nro.substring(0, 6).concat("XXXXXX").concat(nro.substring(nro.length() - 4));
	}

	public static Serializable unserialize(String cadena) throws Exception {
		ByteArrayInputStream bs = new ByteArrayInputStream(JTools.stringToByteArray(cadena));
		ObjectInputStream is = new ObjectInputStream(bs);
		Serializable object = (Serializable) is.readObject();
		is.close();
		return object;
	}

	public static String serialize(Serializable object) throws Exception {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bs);
		os.writeObject(object);
		os.close();
		byte[] bytes = bs.toByteArray(); // devuelve byte[]
		return JTools.byteVectorToString(bytes);
	}

	public static String normalizeDouble(String val) {
		String con = JTools.getNumberEmbeddedWithDecSigned(val);
		if (con.indexOf(',') == -1)
			return con;
		int posComa = con.lastIndexOf(",");
		int posOtherComa = posComa == -1 ? -1 : con.lastIndexOf(",", posComa);
		int posPunto = con.lastIndexOf(".");
		int posOtherPunto = posPunto == -1 ? -1 : con.lastIndexOf(",", posPunto);
		if (posComa == -1 && posPunto == -1)
			return con; // "10"
		if (posComa == -1 && posPunto != -1 && posOtherPunto != -1)
			return con;// "10.0"
		if (posComa == -1 && posPunto != -1 && posOtherPunto == -1)
			return JTools.replace(con, ".", "");// "10.000.0"
		if (posPunto == -1 && posComa != -1 && posOtherComa != -1)
			return JTools.replace(con, ",", ".");
		;// "10,0"
		if (posPunto == -1 && posComa != -1 && posOtherComa == -1)
			return JTools.replace(con, ",", "");// "10,000,000"
		if (posPunto > posComa) { // "10,000.000"
			return JTools.replace(con, ",", "");
		} else { // "10.000,000"
			return JTools.replace(JTools.replace(con, ".", ""), ",", ".");
		}
	}

	public static String normalizeDoubleWithComma(String val) {
		String con = JTools.getNumberEmbeddedWithDecSigned(JTools.replace(val, ",", "."));
		if (con.indexOf(',') == -1)
			return con;
		int posComa = con.lastIndexOf(",");
		int posOtherComa = posComa == -1 ? -1 : con.lastIndexOf(",", posComa);
		int posPunto = con.lastIndexOf(".");
		int posOtherPunto = posPunto == -1 ? -1 : con.lastIndexOf(",", posPunto);
		if (posComa == -1 && posPunto == -1)
			return con; // "10"
		if (posComa == -1 && posPunto != -1 && posOtherPunto != -1)
			return con;// "10.0"
		if (posComa == -1 && posPunto != -1 && posOtherPunto == -1)
			return JTools.replace(con, ".", "");// "10.000.0"
		if (posPunto == -1 && posComa != -1 && posOtherComa != -1)
			return JTools.replace(con, ",", ".");
		;// "10,0"
		if (posPunto == -1 && posComa != -1 && posOtherComa == -1)
			return JTools.replace(con, ",", "");// "10,000,000"
		if (posPunto > posComa) { // "10,000.000"
			return JTools.replace(con, ",", "");
		} else { // "10.000,000"
			return JTools.replace(JTools.replace(con, ".", ""), ",", ".");
		}
	}

	public static String convertImageToBase24(BufferedImage bi) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bi, "PNG", out);
		return (java.util.Base64.getEncoder().encodeToString(out.toByteArray()).replaceAll("\r\n", "").replaceAll("\n",
				""));
	}

	public static String toHtmlColor(String color) {
		if (color == null)
			return "";
		if (color.length() != 6 && color.length() != 8)
			return color;

		if (!JTools.IsHexa(color.toUpperCase()))
			return color;
		return "#" + color;
	}

	public static Serializable[] addItemArray(Serializable[] array, Serializable record, int pos) throws Exception {
		Serializable[] oResultSetSerialize = new Serializable[array.length + 1];
		int i = 0;
		int j;
		boolean added = false;
		for (j = 0; j < array.length; j++) {
			Serializable rec = array[j];
			if (i == pos) {
				oResultSetSerialize[i++] = record;
				added = true;
			}
			oResultSetSerialize[i++] = rec;

		}
		if (!added)
			oResultSetSerialize[i++] = record;
		return oResultSetSerialize;
	}

	public static List<String> getTokens(String line, char openSep, char closeSep) {
		List<String> l = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		int p = 0;
		boolean find = false;
		char[] lineInChars = line.toCharArray();
		for (char c : lineInChars) {
			if (c == openSep) {
				p++;
				find = true;
				continue;
			} else if (c == closeSep) {
				p--;
			}
			if (p == 0 && find) {
				l.add(sb.toString());
				sb.setLength(0);
				find = false;
			}
			if (find)
				sb.append(c);

		}
		return l;
	}

	public static String getFirstTokens(String line, char openSep, char closeSep) {
		StringBuilder sb = new StringBuilder();
		int p = 0;
		boolean find = false;
		char[] lineInChars = line.toCharArray();
		for (char c : lineInChars) {
			if (c == openSep) {
				p++;
				find = true;
				continue;
			} else if (c == closeSep) {
				p--;
			}
			if (p == 0 && find) {
				return sb.toString();
			}

			sb.append(c);

		}
		return null;
	}
	public static Date getLastModified(File directory) {
		File[] files = directory.listFiles();
		if (files.length == 0)
			return new Date(directory.lastModified());
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return new Long(o2.lastModified()).compareTo(o1.lastModified()); // latest 1st
			}
		});
		return new Date(files[0].lastModified());
	}

	public static double getMemoryUsage() {
		double gbytes = 1024 * 1024 * 1024;
		Runtime runtime = Runtime.getRuntime();
		double allocatedMemory = runtime.totalMemory();
		double freeMemory = runtime.freeMemory();
		return JTools.rd((allocatedMemory - freeMemory) / gbytes, 2);
	}

	public static double getMaxMemory() {
		double gbytes = 1024 * 1024 * 1024;
		Runtime runtime = Runtime.getRuntime();
		double maxMemory = runtime.maxMemory();
		return JTools.rd((maxMemory) / gbytes, 2);
	}
	public static String logMemoryUsage() {
		double gbytes = 1024 * 1024 * 1024;
		Runtime runtime = Runtime.getRuntime();
		StringBuilder sb = new StringBuilder();
		double maxMemory = runtime.maxMemory();
		double allocatedMemory = runtime.totalMemory();
		double freeMemory = runtime.freeMemory();
		sb.append("Memory: used=" + JTools.rd((allocatedMemory - freeMemory) / gbytes, 2) + "gb ");
		sb.append("free=" + JTools.rd(freeMemory / gbytes, 2) + "gb ");
		sb.append("allocated=" + JTools.rd(allocatedMemory / gbytes, 2) + "gb ");
		sb.append("max=" + JTools.rd(maxMemory / gbytes, 2) + "gb ");
		sb.append("total free=" + JTools.rd((freeMemory + (maxMemory - allocatedMemory)) / gbytes, 2) + "gb");
		return sb.toString();
	}

	public static void sendMail(BizUsrMailSender mailSender, String company, String model, String title,
			String destination, JRecord docRelacionado) throws Exception {
		BizPlantilla objPlantilla = BizCompany.getObjPlantilla(company, model);
		if (objPlantilla == null)
			throw new Exception("Plantilla inexistente");
		String mensaje = objPlantilla.generateDocSimple(docRelacionado);
		if (mensaje.startsWith("%"))
			mensaje = URLDecoder.decode(mensaje, CharEncoding.ISO_8859_1);

		mailSender.send(destination, title, mensaje);
	}


  public static String encriptarAES(String textoPlano, String skey, String siv) {
      try {
          // Crear IV y clave de encriptación
          IvParameterSpec iv = new IvParameterSpec(siv.getBytes(StandardCharsets.UTF_8));
          SecretKeySpec skeySpec = new SecretKeySpec(skey.getBytes(StandardCharsets.UTF_8), "AES");

          // Configurar Cipher para encriptar
          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
          cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

          // Encriptar texto plano
          byte[] encrypted = cipher.doFinal(textoPlano.getBytes(StandardCharsets.UTF_8));

          // Convertir a Base64 para su representación legible
          return Base64.getEncoder().encodeToString(encrypted);
      } catch (Exception ex) {
          ex.printStackTrace();
      }
      return null;
  }

  public static String desencriptarAES(String textoEncriptado, String skey, String siv) {
      try {
          // Crear IV y clave de desencriptación
          IvParameterSpec iv = new IvParameterSpec(siv.getBytes(StandardCharsets.UTF_8));
          SecretKeySpec skeySpec = new SecretKeySpec(skey.getBytes(StandardCharsets.UTF_8), "AES");

          // Configurar Cipher para desencriptar
          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
          cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

          // Desencriptar texto cifrado
          String cleanedEncryptedText = textoEncriptado.replaceAll("[^A-Za-z0-9+/=]", "");
          byte[] decodedEncrypted = Base64.getDecoder().decode(cleanedEncryptedText);
          byte[] original = cipher.doFinal(decodedEncrypted);

          // Convertir a texto plano
          return new String(original, StandardCharsets.UTF_8);
      } catch (Exception ex) {
          ex.printStackTrace();
      }
      return null;
  }

}
