package pss.core.tools.crypt;

/*
 * Copyright (c) 2000 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 2nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book (recommended),
 * visit http://www.davidflanagan.com/javaexamples2.
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * This class defines methods for encrypting and decrypting using the Triple DES
 * algorithm and for generating, reading and writing Triple DES keys. It also
 * defines a main() method that allows these methods to be used from the command
 * line.
 */
public class TripleDES {

	private static SecretKey tdesKey = null;
	
	private static byte defaultkey[] = {
    (byte)0xA9, (byte)0x9A, (byte)0xC8, (byte)0x31,
    (byte)0xAA, (byte)0x9B, (byte)0xC9, (byte)0x32,
    (byte)0xAB, (byte)0x9C, (byte)0xCA, (byte)0x33,
    (byte)0xAC, (byte)0x9D, (byte)0xCB, (byte)0x34,
    (byte)0xAD, (byte)0x9E, (byte)0xCC, (byte)0x35,
    (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
 };
 
	public static SecretKey getKey() throws Exception {
	  if ( TripleDES.tdesKey == null ) {
	  	TripleDES.setKey(TripleDES.defaultkey);
	  }
		return tdesKey;
	}
	
	public static void setKey( byte k[] ) throws Exception {
    // Convert the raw bytes to a secret key like this
    DESedeKeySpec keyspec = new DESedeKeySpec(k);
    SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
    tdesKey = keyfactory.generateSecret(keyspec);
	}
	
	/**
   * The program. The first argument must be -e, -d, or -g to encrypt,
   * decrypt, or generate a key. The second argument is the name of a file
   * from which the key is read or to which it is written for -g. The -e and
   * -d arguments cause the program to read from standard input and encrypt or
   * decrypt to standard output.
   */
  public static void main(String[] args) {
    try {
      // Check to see whether there is a provider that can do TripleDES
      // encryption. If not, explicitly install the SunJCE provider.


      // Now check the first arg to see what we're going to do
      if (args[0].equals("-g")) { // Generate a key
        System.out.print("Generating key. This may take some time...");
        System.out.flush();
        //SecretKey key = generateKey();
        //writeKey(key, keyfile);
        System.out.println("done.");
        System.out.println("Secret key written to " + args[1]
            + ". Protect that file carefully!");
      } else if (args[0].equals("-e")) { // Encrypt stdin to stdout
      	
      	String val = "0000dfasdfasdfsdf00000000345345345345000000000000";
      	
        byte res[] = TripleDES.encrypt(val.getBytes());
        
        System.out.println(new String( res ));

        res = TripleDES.encrypt(res);

        System.out.println(new String( res ) );
        
        res = TripleDES.decrypt(res);
        
        System.out.println(new String( res ) );
        
      }
    } catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java " + TripleDES.class.getName()
          + " -d|-e|-g <keyfile>");
    }
  }

  
  
  
  /**
   * Use the specified TripleDES key to encrypt bytes from the input stream
   * and write them to the output stream. This method uses CipherOutputStream
   * to perform the encryption and write bytes at the same time.
   */
  public static byte[] encrypt(byte in[]) throws Exception {
    // Create and initialize the encryption engine
   
   byte resp[] = crypt(Cipher.ENCRYPT_MODE, in);
  // BASE64Encoder base64Encoder = new BASE64Encoder();
  // String val=base64Encoder.encode(resp) ;
//   return val.getBytes();
   return resp;

  }
  
  private static byte[] crypt(int mode, byte in[] ) throws Exception {
    Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
    cipher.init(mode, getKey());
    byte b[] = cipher.doFinal(in);
    return b;
  }

  /**
   * Use the specified TripleDES key to decrypt bytes ready from the input
   * stream and write them to the output stream. This method uses uses Cipher
   * directly to show how it can be done without CipherInputStream and
   * CipherOutputStream.
   */
  public static byte[] decrypt(byte in[]) throws Exception  {
    // Create and initialize the decryption engine
    //BASE64Decoder base64Decoder = new BASE64Decoder(); 
  	//byte val[]=base64Decoder.decodeBuffer(new String(in));
    return crypt(Cipher.DECRYPT_MODE, in);
  }
  
  
  
}

