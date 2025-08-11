package pss.core.tools.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import pss.core.tools.JTools;

public class AESCrypt {
	
	static byte[] key = {
			(byte)0x49,(byte)0x12,(byte)0x04,(byte)0x54,
			(byte)0x26,(byte)0x15,(byte)0x80,(byte)0x27,
			(byte)0x16,(byte)0x79,(byte)0x82,(byte)0x56,
			(byte)0x10,(byte)0x98,(byte)0x36,(byte)0x76,
			(byte)0x15,(byte)0x93,(byte)0x75,(byte)0x92,
			(byte)0x18,(byte)0x03,(byte)0x48,(byte)0x54,
			(byte)0x27,(byte)0x48,(byte)0x05,(byte)0x92,
			(byte)0x48,(byte)0x68,(byte)0x09,(byte)0x56 };
	
	static byte[] init = {
			(byte)0x18,(byte)0x62,(byte)0x53,(byte)0x12,
			(byte)0x66,(byte)0x30,(byte)0x15,(byte)0x42,
			(byte)0x16,(byte)0x94,(byte)0x60,(byte)0x68,
			(byte)0x94,(byte)0x53,(byte)0x73,(byte)0x81 };


	public static byte[] encrypt( byte[] value) throws Exception {
		try {
			
//			for (Object obj : java.security.Security.getAlgorithms("Cipher")) {
//				  System.out.println(obj);
//				}
//			SecretKeyFactory factory = SecretKeyFactory.getInstance("AES");
//	//		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//			byte[] salt=null;
//			char[] ckey = new char[key.length()];
//			key.getChars(0,key.length(),ckey,0);
//			KeySpec spec = new PBEKeySpec( ckey, salt, 65536, 256);
//			SecretKey tmp = factory.generateSecret(spec);
//			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
			System.out.println("INIT:"+ JTools.BinToHexString( new String( init ), init.length ) );
			System.out.println("KEY:"+  JTools.BinToHexString( new String(key ), key.length ) );
			
			IvParameterSpec iv = new IvParameterSpec(
					init );
			SecretKeySpec skeySpec = new SecretKeySpec(  key, "AES" );

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value);
			int len = encrypted.length;
//			System.out.println("encrypted string: "
//					+ Base64.encodeBase64(encrypted));

			return encrypted;
		} catch (Exception ex) {
			throw ex;
		}

	}

	public static byte[] decrypt( byte[] encrypted) throws Exception {
		try {
			IvParameterSpec iv = new IvParameterSpec(
					init);
			SecretKeySpec skeySpec = new SecretKeySpec(key,
					"AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(encrypted);

			return original;
		} catch (Exception ex) {
			throw ex;
		}

	}

	public static void main(String[] args) {
	
		String c = "303130307038000000810004313230303030303330313030303030303330303030303030303030303235343731353235303031383031343031303239303030303030303330333630333D317C30343D337C31323D317C31333D32303135313032397C32333D317C31373D3130303130";
		try {
		String c2 =new String(encrypt( JTools.HexStringToBin(c, c.length()).getBytes()));
		System.out.println(JTools.BinToHexString(c2, c2.length()) );
		System.out.println(decrypt(
				c2.getBytes()));
		} catch (Exception eee) {
			
			eee.printStackTrace();
		}
	}
}