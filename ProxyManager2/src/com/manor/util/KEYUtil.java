package com.manor.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;




import android.content.Context;


public class KEYUtil {

	private static Key AESKey = null;
	private static Key RSApubkey = null;
	
	public static void resetAES()
	{
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecureRandom random = new SecureRandom();
			keygen.init(128, random);
			AESKey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static byte[] wrappedKey()
	{
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.WRAP_MODE, RSApubkey);
			byte[] wrappedkey = cipher.wrap(AESKey);
			return wrappedkey;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static Key unwrapRSAKey(Context context)
	{
		String keystr = "OTRqZmtkd2wycjJmamR3a2YyOTAyamYyMzBmZmp3ZmtsMjAyZndqZqztAAVzcgAUamF2YS5zZWN1"+
		"cml0eS5LZXlSZXC9+U+ziJqlQwIABEwACWFsZ29yaXRobXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sA"+
		"B2VuY29kZWR0AAJbQkwABmZvcm1hdHEAfgABTAAEdHlwZXQAG0xqYXZhL3NlY3VyaXR5L0tleVJl"+
		"cCRUeXBlO3hwdAADUlNBdXIAAltCrPMX+AYIVOACAAB4cAAAAVcwggFTAgEAMA0GCSqGSIb3DQEB"+
		"AQUABIIBPTCCATkCAQACQQCZTBg2d4M+f/3G/peDADjqlbHZPOBoADVZ6MzaJsxcrayUDUJTMuLm"+
		"ulc9OaIX1Cis+Izer8pZKS9FNh9u5h/PAgMBAAECQG0az5DUdsCg78oVoVhHbDrp1+65eS4MF+yp"+
		"2ELbok/C39AMRHgj8tosbGcuiT7f0xFjEHAFMErrOI/9HYm8guECIQDhulPzjNh7Gcgyl40i0a30"+
		"XMs+FHHJVQ6uZcZA2vjuvQIhAK3bFaQxmAM4R9Xm7O2rqAx7nH4J/H4xxP9Otc4TOUd7AiApGN+I"+
		"DDCt2PfWHjmYCUz+nJZ0awx8d3wAzI7X6AZCkQIgNOjtAChbs/cn3ZBG4NoR7aKc8LrHTfyFkJF6"+
		"wPmWwAsCIFHjUgLZZ4HEwI15Kaf5AKkwrOCx5pRo8k+kGlnt/dP9dAAGUEtDUyM4fnIAGWphdmEu"+
		"c2VjdXJpdHkuS2V5UmVwJFR5cGUAAAAAAAAAABIAAHhyAA5qYXZhLmxhbmcuRW51bQAAAAAAAAAA"+
		"EgAAeHB0AAdQUklWQVRFZHNmZHNmZGZld2ZqaGdrZmRsbnZteG12bjMyNDMyNTYzNDI4NDcxMjRr"+
		"Z3Nna2Zhc2tmaDM=";
		byte[] encodekey = Base64Coder.decodeLines(keystr);
		byte[] keybyte = new byte[602];
		for(int i=40; i<642; i++)
		{
			keybyte[i-40] = encodekey[i];
		}
		Key privatekey = null;
		try {
			ObjectInputStream keyin = new ObjectInputStream(new ByteArrayInputStream(keybyte));
			privatekey = (Key)keyin.readObject();
			keyin.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return privatekey;
	}
	
	public static void setRSApubkey(Key rSApubkey) {
		RSApubkey = rSApubkey;
	}

	public static boolean unwrapkey(byte[] wrappedkey, Context context)
	{
		try {
			Key privatekey = unwrapRSAKey(context);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.UNWRAP_MODE, privatekey);
			AESKey = cipher.unwrap(wrappedkey, "AES", Cipher.SECRET_KEY);
			System.out.println("aeskey: " + AESKey.getEncoded());
			return true;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	public static String wrapMsg(String msg)
	{	
		System.out.println(AESKey.getEncoded().length);
		if(AESKey == null)
			System.out.println("null key");
		byte[] bytemsg = null;
		try {
			bytemsg = msg.getBytes("UTF8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		int mode = Cipher.ENCRYPT_MODE;
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(mode, AESKey);
			byte[] outbytes = cipher.doFinal(bytemsg);
			char[] outstr2 = Base64Coder.encode(outbytes);
			String outstr = new String(outstr2);
			return outstr;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String unwrapMsg(String msg)
	{		
		int mode = Cipher.DECRYPT_MODE;
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(mode, AESKey);
			byte[] bytemsg = Base64Coder.decode(msg);		
			byte[] outbytes = cipher.doFinal(bytemsg);
			String outmsg = new String(outbytes, "UTF8");
			return outmsg;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] wrapData(byte bytemsg[])
	{	
		System.out.println(AESKey.getEncoded().length);
		if(AESKey == null)
			System.out.println("null key");	
		int mode = Cipher.ENCRYPT_MODE;
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(mode, AESKey);
			
			byte[] outbytes = cipher.doFinal(bytemsg);
			return outbytes;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String wrapimg(byte[] data)
	{
		byte[] wrappeddata = wrapData(data);
		String str = Base64Coder.encodeLines(wrappeddata);
		return str;
	}
}
