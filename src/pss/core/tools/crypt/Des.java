/*******************************************************************************
 * Copyright (c) 2005, 2006 smartShip Factory.
 * All rights reserved. This program and the accompanying materials
 * are property of smartShip Factory.
 * 
 * Contributors:
 *     sgalli@go-ssf.com - Santiago Galli - initial API and implementation
 *******************************************************************************/
package pss.core.tools.crypt;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * DES Management 
 * 
 * @author sgalli
 *
 */
public class Des {
	
	public static final String DEFAULT_KEY = "AF12DEB3C908FFAB";
	
  Cipher ecipher;
  Cipher dcipher;
  // 8-byte Salt
  byte[] salt = {
      (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
      (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
  };

  // Iteration count
  int iterationCount = 19;
  
  public static SecretKey stKey = null;  
  
  
  /**
   * Initialize DES with a phrase 
   * 
   * @param passPhrase
   */
  public Des(String passPhrase) {
    try {
      // Create the key
      if ( stKey == null ) {
	      KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), this.salt, this.iterationCount);
	      stKey = SecretKeyFactory.getInstance(
	      "PBEWithMD5AndDES").generateSecret(keySpec);
      }
      SecretKey key = stKey;	
      this.ecipher = Cipher.getInstance(key.getAlgorithm());
      this.dcipher = Cipher.getInstance(key.getAlgorithm());
      
      // Prepare the parameter to the ciphers
      AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
      
      // Create the ciphers
      this.ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
      this.dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
    } catch (java.security.InvalidAlgorithmParameterException e) {       /* nothing to do */
    } catch (java.security.spec.InvalidKeySpecException e) {       /* nothing to do */
    } catch (javax.crypto.NoSuchPaddingException e) {       /* nothing to do */
    } catch (java.security.NoSuchAlgorithmException e) {       /* nothing to do */
    } catch (java.security.InvalidKeyException e) {       /* nothing to do */
    }
  }

  /**
   * Initialize DES with a Key object
   * 
   * @param key
   */
  public Des(Key key) {
    // Create an 8-byte initialization vector
    byte[] iv = new byte[]{
        (byte)0x8E, 0x12, 0x39, (byte)0x9C,
        0x07, 0x72, 0x6F, 0x5A
    };
    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
    try {
        this.ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        this.dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        // CBC requires an initialization vector
        this.ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        this.dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
    } catch (java.security.InvalidAlgorithmParameterException e) {      /* nothing to do */
    } catch (javax.crypto.NoSuchPaddingException e) {/* nothing to do */
    } catch (java.security.NoSuchAlgorithmException e) {/* nothing to do */
    } catch (java.security.InvalidKeyException e) {/* nothing to do */
    }  }
  
  /**
   * Encrypt a string
   * 
   * @param str original string
   * @return encrypted string
   */
  public String encrypt(String str) {
    try {
      // Encode the string into bytes using utf-8
      byte[] utf8 = str.getBytes("UTF8");
      
      // Encrypt
      byte[] enc = this.ecipher.doFinal(utf8);
      
      // Encode bytes to base64 to get a string
      return java.util.Base64.getEncoder().encodeToString(enc);
    } catch (javax.crypto.BadPaddingException e) {/* nothing to do */
    } catch (IllegalBlockSizeException e) {/* nothing to do */
    } catch (UnsupportedEncodingException e) {/* nothing to do */
    } 
    return null;
  }

  /**
   * Encrypt to a stream
   * 
   * @param buf  input buffer
   * @param out output stream
   */
  public void encrypt(byte buf[], OutputStream out) {
    try {
      // Bytes written to out will be encrypted
      out = new CipherOutputStream(out, this.ecipher);
      out.write(buf);
      out.close();
    } catch (java.io.IOException e) {/* nothing to do */
    }
  }
  
  /**
   * @param in   input stream
   * @param buf  output buffer
   */
  public void decrypt(InputStream in, byte buf[]) {
    try {
      // Bytes read from in will be decrypted
      in = new CipherInputStream(in, dcipher);
      int numRead = 0, j = 0;
      byte buf2[] = new byte[1024];
      while ((numRead = in.read(buf2)) >= 0) {
        for ( int i=0; i<numRead ; i++ )
          buf[j++] = buf2[i];
      }
      in.close();
    } catch (java.io.IOException e) {/* nothing to do */
    }
  }
  
  public String decrypt(String str) {
    try {
      // Decode base64 to get bytes
      byte[] dec = Base64.getDecoder().decode(str);
      
      // Decrypt
      byte[] utf8 = dcipher.doFinal(dec);
      
      // Decode using utf-8
      return new String(utf8, "UTF8");
    } catch (javax.crypto.BadPaddingException e) {/* nothing to do */
    } catch (IllegalBlockSizeException e) {/* nothing to do */
    } catch (UnsupportedEncodingException e) {/* nothing to do */
    } catch (java.io.IOException e) {/* nothing to do */
    }
    return null;
  }  


  
}
