package pss.common.terminals.drivers.Hasar;

import java.util.StringTokenizer;

import pss.core.tools.jnidisp.CFunc;
import pss.core.tools.jnidisp.CMalloc;

public class HasarDriver {

	protected static final String dllFile="HasarDriver.dll";

	protected CFunc jniCrearImpresora=new CFunc(dllFile, "jniCrearImpresora");
	protected CFunc jniDestruirImpresora=new CFunc(dllFile, "jniDestruirImpresora");
	protected CFunc jniEnviarComandoFiscal=new CFunc(dllFile, "jniEnviarComandoFiscal");

	static final String SEP		=  String.valueOf((char)0x1c);
  String lastOperation = "";
	// __declspec(dllexport) int __cdecl jniCrearImpresora( int modelo );
	// __declspec(dllexport) void __cdecl jniDestruirImpresora( int idIF );
	// __declspec(dllexport) int __cdecl jniEnviarComandoFiscal( int idIF,char comando,char *params,char *resp,int sResp );

	public int crearImpresora(int model) throws Exception {
		Object[] obj=new Object[1];
		obj[0]=model;
		return jniCrearImpresora.callInt(obj);
	}

	public void destruirImpresora(int idIF) throws Exception {
		Object[] obj=new Object[1];
		obj[0]=idIF;
		jniDestruirImpresora.callVoid(obj);
	}

	public String enviarComandoFiscal(int idIF, int zComando, String params) throws Exception {
		String resp=null;
		CMalloc cbuff;
		int size = 1024;
		lastOperation = ""+idIF+"|"+zComando+"|"+params;
		cbuff=new CMalloc(size);
		try {
			Object[] obj=new Object[5];
			obj[0]=idIF;
			obj[1]=zComando;
			obj[2]=params;
			obj[3]=cbuff;
			obj[4]=size;
			jniEnviarComandoFiscal.callInt(obj);
			resp=cbuff.getString(0);
			lastOperation += "---"+resp;
		} catch (RuntimeException e) {
			lastOperation += "---Exception:"+e.getMessage();
			cbuff.free();
			throw e;
		}
		cbuff.free();
		return resp;
	}

	public static String getParamString(String str, int pos) throws Exception {
		int count=0;
		StringTokenizer tok=new StringTokenizer(str, SEP);
		while (tok.hasMoreElements()) {
			String element=tok.nextToken();
			if (count==pos) return element;
			count++;
		}
		throw new Exception("parameter ["+pos+"] no encontrado en ["+str+"]");
	}

	public static int getParamInt(String str, int pos) throws Exception {
		int count=0;
		StringTokenizer tok=new StringTokenizer(str,  SEP);
		while (tok.hasMoreElements()) {
			String element=tok.nextToken();
			if (count==pos) return Integer.parseInt(element);
			count++;
		}
		throw new Exception("parameter ["+pos+"] no encontrado en ["+str+"]");
	}

	public static double getParamDouble(String str, int pos) throws Exception {
		int count=0;
		StringTokenizer tok=new StringTokenizer(str, SEP);
		while (tok.hasMoreElements()) {
			String element=tok.nextToken();
			if (count==pos) return Double.parseDouble(element);
			count++;
		}
		throw new Exception("parameter ["+pos+"] no encontrado en ["+str+"]");
	}

	public static boolean getParamBoolean(String str, int pos) throws Exception {
		int count=0;
		StringTokenizer tok=new StringTokenizer(str, SEP);
		while (tok.hasMoreElements()) {
			String element=tok.nextToken();
			if (count==pos) return element.charAt(0)=='1'||element.charAt(0)=='S'||element.charAt(0)=='s'||element.charAt(0)=='Y'||element.charAt(0)=='y';
			count++;
		}
		throw new Exception("parameter ["+pos+"] no encontrado en ["+str+"]");
	}

	public String getLastOperation() {
		return "";
	}
}
