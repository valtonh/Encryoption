/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestriVI.detIpd;

/**
 *
 * @author pc
 */


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import sun.misc.BASE64Encoder;



public class DesEnc {
	public static void main(String[] args) throws IOException {
		
		String strDataToEncrypt = new String();
		String strCipherText = new String();
		String strDecryptedText = new String();
		
		try{
		/**
		 *  Step 1. Generate a DES key using KeyGenerator 
		 * 
		 */
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGen.generateKey();
		
		/**
		 *  Step2. Create a Cipher by specifying the following parameters
		 * 			a. Algorithm name - here it is DES
		 * 			b. Mode - here it is CBC
		 * 			c. Padding - PKCS5Padding
		 */
		
		Cipher desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); /* Must specify the mode explicitly as most JCE providers default to ECB mode!! */
		
		/**
		 *  Step 3. Initialize the Cipher for Encryption 
		 */
		
		desCipher.init(Cipher.ENCRYPT_MODE,secretKey);
		
		/**
		 *  Step 4. Encrypt the Data
		 *  		1. Declare / Initialize the Data. Here the data is of type String
		 *  		2. Convert the Input Text to Bytes
		 *  		3. Encrypt the bytes using doFinal method 
		 */
		strDataToEncrypt = new String(Files.readAllBytes(Paths.get("AesT.mf")));;
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt); 
		strCipherText = new BASE64Encoder().encode(byteCipherText);
		System.out.println("Cipher Text generated using DES with CBC mode and PKCS5 Padding is " +strCipherText);
                 try {
            FileWriter writer = new FileWriter("DetDes.txt", true);
            writer.write(strCipherText);
            writer.write("\r\n");
            writer.write(strDataToEncrypt);
            
            writer.close();
        } catch (IOException e) {
        }
		
		/**
		 *  Step 5. Decrypt the Data
		 *  		1. Initialize the Cipher for Decryption 
		 *  		2. Decrypt the cipher bytes using doFinal method 
		 */
		desCipher.init(Cipher.DECRYPT_MODE,secretKey,desCipher.getParameters());
		 //desCipher.init(Cipher.DECRYPT_MODE,secretKey);
		byte[] byteDecryptedText = desCipher.doFinal(byteCipherText);
		strDecryptedText = new String(byteDecryptedText);
		System.out.println(" Decrypted Text message is " +strDecryptedText);
		}
		
		catch (NoSuchAlgorithmException noSuchAlgo)
		{
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}
		
			catch (NoSuchPaddingException noSuchPad)
			{
				System.out.println(" No Such Padding exists " + noSuchPad);
			}
		
				catch (InvalidKeyException invalidKey)
				{
					System.out.println(" Invalid Key " + invalidKey);
				}
				
				catch (BadPaddingException badPadding)
				{
					System.out.println(" Bad Padding " + badPadding);
				}
				
				catch (IllegalBlockSizeException illegalBlockSize)
				{
					System.out.println(" Illegal Block Size " + illegalBlockSize);
				}
				
				catch (InvalidAlgorithmParameterException invalidParam)
				{
					System.out.println(" Invalid Parameter " + invalidParam);
				}
	}

}