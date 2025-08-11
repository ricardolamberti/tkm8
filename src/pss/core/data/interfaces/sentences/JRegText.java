package pss.core.data.interfaces.sentences;

import java.io.EOFException;
import java.io.RandomAccessFile;

import pss.JPath;
import pss.core.data.interfaces.connections.JBaseText;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JStringTokenizer;

//========================================================================== //
// Clase para Registro JDBC
//========================================================================== //
public class JRegText extends JBaseRegistro {

	// -------------------------------------------------------------------------- //
	// Propiedades
	// -------------------------------------------------------------------------- //
	// private oRSet pp;
	private boolean bEOF=false;
	private JList<String> aCampos;
	// private JList aValues;

	private long lHeaderIndex;

	private JStringTokenizer oLastTokenFound;
	private String sLastLineFound;
	private long lIdxLastRecFound;

	// -------------------------------------------------------------------------- //
	// Constructor
	// -------------------------------------------------------------------------- //
	public JRegText() {
	}

	// public ResultSet GetResultSet() { return oRSet; }
	// public Statement GetStatement() { return oStmt; }

	private JBaseText GetBaseText() {
		return (JBaseText) this.getDatabase();
	}

	// -------------------------------------------------------------------------- //
	// Ejecuto un Update
	// -------------------------------------------------------------------------- //
	@Override
	public void update() throws Exception {
		String sOldLine;
		String sNewLine;
		int iDiff;
		int iResto;

		RandomAccessFile oFileAux;

		if (this.aCampos==null) {
			this.openCursor();
			oFileAux=this.GetBaseText().GetFileObject();
		} else {
			oFileAux=this.GetBaseText().GetFileObject();
			oFileAux.seek(lHeaderIndex);
		}

		if (FindRecord()) {
			sOldLine=this.sLastLineFound;
			sNewLine=this.DatoToStrLine();

			iDiff=sOldLine.length()-sNewLine.length();

			if (iDiff==0) {
				oFileAux.seek(this.lIdxLastRecFound);
				oFileAux.write(sNewLine.getBytes());
				return;
			}

			iResto=(int) (oFileAux.length()-oFileAux.getFilePointer());
			byte cResto[]=new byte[iResto];
			oFileAux.read(cResto);
			oFileAux.seek(this.lIdxLastRecFound);
			oFileAux.write(sNewLine.getBytes());
			// oFileAux.write(0x0D);
			// oFileAux.write(0x0A);
			oFileAux.write(System.getProperty("line.separator").getBytes());
			oFileAux.write(cResto);
			if (iDiff>0) oFileAux.setLength(oFileAux.length()-iDiff);
		}

		return;
	} // update

	// -------------------------------------------------------------------------- //
	// Ejecuto un Delete
	// -------------------------------------------------------------------------- //
	@Override
	public void delete() throws Exception {
		long lLen;
		long lActualFileIndex;
		byte cFileBuffer[];
		RandomAccessFile oFileAux;

		if (this.aCampos==null) {
			this.openCursor();
			oFileAux=this.GetBaseText().GetFileObject();
		} else {
			oFileAux=this.GetBaseText().GetFileObject();
			oFileAux.seek(lHeaderIndex);
		}

		if (this.FindRecord()) {
			lActualFileIndex=oFileAux.getFilePointer();
			lLen=oFileAux.length()-lActualFileIndex;
			if (lLen!=0) {
				cFileBuffer=new byte[(int) lLen];
				oFileAux.seek(lActualFileIndex);
				oFileAux.read(cFileBuffer);
			} else {
				/*
				 * cFileBuffer = new byte[3]; cFileBuffer[0] = 0x0D; cFileBuffer[1] = 0x0A; cFileBuffer[2] = 0x00;
				 */
				cFileBuffer=System.getProperty("line.separator").getBytes();
			}

			oFileAux.seek(this.lIdxLastRecFound);
			oFileAux.write(cFileBuffer);
			oFileAux.setLength(oFileAux.length()-(lActualFileIndex-this.lIdxLastRecFound));
		}

		return;
	} // OpenCursor

	// -------------------------------------------------------------------------- //
	// Ejecuto un Insert
	// -------------------------------------------------------------------------- //
	@Override
	public void insert() throws Exception {
		String sLine;

		// Si no tiene cargados los campos los cargo ahora
		if (aCampos==null) {
			this.openCursor();
		} else {
			this.GetBaseText().GetFileObject().seek(lHeaderIndex);
		}

		sLine=DatoToStrLine();

		long lOffset=GetBaseText().GetFileObject().length()-1;
		byte cAux;

		while (true) {
			this.GetBaseText().GetFileObject().seek(lOffset);
			cAux=this.GetBaseText().GetFileObject().readByte();
			if (cAux!=0x0D&&cAux!=0x0A) break;
			lOffset-=2;
		} // end while
		this.GetBaseText().GetFileObject().write(System.getProperty("line.separator").getBytes());
		this.GetBaseText().GetFileObject().write(sLine.getBytes());
		this.GetBaseText().GetFileObject().write(System.getProperty("line.separator").getBytes());
	} // Insert

	@Override
	public String SelectMax(String zCampo) throws Exception {
		// return "1";
		String sMax=new String("");
		String sAux;

		this.openCursor();
		while (this.next()) {
			if (sMax.length()==0) sMax=this.CampoAsStr(zCampo);
			else {
				sAux=CampoAsStr(zCampo);
				if (sMax.compareToIgnoreCase(sAux)<0||(sMax.length()<sAux.length())) sMax=sAux;
			}
		}

		if (sMax.length()==0) sMax="0";

		return sMax;
	}

	// -------------------------------------------------------------------------- //
	// Init el Query
	// -------------------------------------------------------------------------- //
	@Override
	public void openCursor() throws Exception {
		RandomAccessFile oFile;
		String sFileName=new String(JPath.PssPath()+"/"+GetBaseText().GetPath()+"/"+oDato.GetTable()+"."+GetBaseText().GetFileExt());

		oFile=new RandomAccessFile(sFileName, "rw");

		GetBaseText().SetFileObject(oFile);
		String sLine=oFile.readLine();
		JStringTokenizer oTok=JCollectionFactory.createStringTokenizer(sLine, GetBaseText().GetDelimiter());
		aCampos=oTok.asList();
		this.lHeaderIndex=oFile.getFilePointer();
	}

	// -------------------------------------------------------------------------- //
	// Obtengo siguiente registro de un recordset
	// -------------------------------------------------------------------------- //
	@Override
	public boolean next() throws Exception {
		bEOF=true;
		if (this.FindRecord()) {
			bEOF=false;
			GetBaseText().SetLine(this.sLastLineFound);
			GetBaseText().SetTokensLine(this.oLastTokenFound);
		}

		return !bEOF;
	} // Next

	// -------------------------------------------------------------------------- //
	// Chequeo filtros
	// -------------------------------------------------------------------------- //
	public boolean OkFiltros(JStringTokenizer zTok) throws Exception {
		boolean bRc=true;
		JList<RFilter> aFiltros=oDato.getFilters();
		JStringTokenizer oTokenBackup=GetBaseText().GetTokensLine();

		GetBaseText().SetTokensLine(zTok);

		JIterator<RFilter> oIt=aFiltros.getIterator();
		while (oIt.hasMoreElements()) {
			RFilter oFiltro=oIt.nextElement();
			if (!this.oDato.compareFilters(oFiltro, this)) {
				bRc=false;
				break;
			}
		}

		GetBaseText().SetTokensLine(oTokenBackup);
		return bRc;
	}

	// -------------------------------------------------------------------------- //
	// cierro el recordset
	// -------------------------------------------------------------------------- //
	@Override
	public void close() throws Exception {
		// GetBaseText().GetFileStream().Close();
		GetBaseText().GetFileObject().close();
	}

	// -------------------------------------------------------------------------- //
	// Obtengo siguiente registro de un recordset
	// -------------------------------------------------------------------------- //
	@Override
	public boolean EOF() throws Exception {
		return bEOF;
	} // Next

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como String
	// -------------------------------------------------------------------------- //
	@Override
	public String CampoAsStr(String zCampo) throws Exception {
		JList<String> aTokens=GetBaseText().GetTokensLine().asList();
		int iIdx=BuscarCampo(zCampo);
		if (iIdx<0) return null;
		if (iIdx>=aTokens.size()) return "";
		if ((aTokens.getElementAt(iIdx)).equalsIgnoreCase("½")) return "";
		return aTokens.getElementAt(iIdx);
	} // CampoAsStr

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como long
	// -------------------------------------------------------------------------- //
	@Override
	public Long CampoAsLong(String zCampo) throws Exception {
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null||sCampo.length()==0) return null;
		return Long.valueOf(sCampo);
	} // CampoAsLong

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como int
	// -------------------------------------------------------------------------- //
	@Override
	public Integer CampoAsInt(String zCampo) throws Exception {
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null||sCampo.length()==0) return null;
		return Integer.valueOf(sCampo);
	} // CampoAsInt

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como float
	// -------------------------------------------------------------------------- //
	@Override
	public Double CampoAsFloat(String zCampo) throws Exception {
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null) return null;
		return Double.valueOf(sCampo);
	} // CampoAsFloat

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como Date
	// -------------------------------------------------------------------------- //
	@Override
	public java.util.Date CampoAsDate(String zCampo) throws Exception {
		// java.util.Date oDate = (java.util.Date) oRSet.getDate( zCampo );
		// if ( oRSet.wasNull() )
		// return null;
		// return oDate;
		String sCampo=CampoAsStr(zCampo);
		if (sCampo==null) return null;
		return JDateTools.StringToDate(sCampo, "yyyy-mm-dd");

	} // CampoAsDate

	// -------------------------------------------------------------------------- //
	// Obtengo el valor de una columna como DateTime
	// -------------------------------------------------------------------------- //
	@Override
	public java.sql.Time CampoAsTime(String zCampo) throws Exception {
		// java.sql.Time oTime = oRSet.getTime( zCampo );
		// if ( oRSet.wasNull() )
		return null;
		// return oTime;
	} // CampoAsDate

	public int BuscarCampo(String sCampo) {
		int iIdx=0;
		JIterator<String> oIt=aCampos.getIterator();
		while (oIt.hasMoreElements()) {
			String sFld=oIt.nextElement();
			if (sFld.equalsIgnoreCase(sCampo)) return iIdx;
			iIdx++;
		}
		return -1;
	}

	String ReadLine() throws Exception {
		return ReadLine(this.GetBaseText().GetFileObject());
	}

	String ReadLine(RandomAccessFile zFile) throws Exception {
		try {
			String sAux=new String("");
			char cAux;

			while (true) {
				cAux=(char) zFile.readByte();
				if (cAux=='\n') break;
				if (cAux==0x0D) {
					zFile.readByte();
					break;
				}
				sAux=sAux+String.valueOf(cAux);
			} // end while

			return sAux;
		} catch (EOFException e) {
			return null;
		}
	}

	boolean FindRecord() throws Exception {
		String sLine;
		long lRecordIndex=0;
		boolean bFound=false;

		while (true) {
			lRecordIndex=GetBaseText().GetFileObject().getFilePointer();
			sLine=GetBaseText().GetFileObject().readLine();

			if (sLine==null) break;
			if (sLine.length()==0) break;

			JStringTokenizer oTok=JCollectionFactory.createStringTokenizer(sLine, GetBaseText().GetDelimiter());
			if (OkFiltros(oTok)) {
				bFound=true;
				lIdxLastRecFound=lRecordIndex;
				oLastTokenFound=oTok;
				sLastLineFound=sLine;
				break;
			}
		} // end while

		return bFound;
	}

	String DatoToStrLine() throws Exception {
		/*
		 * String sLine = new String(""); String sValorCampo; JPropiedad oProp; JMap oProps = ((JBD) oDato).GetPropiedades(); JIterator oIt = aCampos.getIterator(); while (oIt.hasMoreElements()) { oProp = (JPropiedad) oProps.FindKey(((String)oIt.nextElement()).toLowerCase()); sValorCampo = oProp.AsString(); if (sValorCampo == null || sValorCampo.length() == 0) sValorCampo = GetBaseText().GetNullValue(); if (sLine.length() > 0) sLine = sLine + GetBaseText().GetDelimiter();
		 * 
		 * if ( oProp.GetType().equals( JObject.JPASSWORD ) ) sLine = sLine + JTools.StringToPassword( sValorCampo ); else sLine = sLine + sValorCampo; } // end while return sLine;
		 */
		return null;
	}
}
