package com.sds.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SysUtils {	 
	public static String fileNameDC = "";
	public static String fileNameDD = "";
	public static String fileNameDF = "";
	public static String fileNameR97 = "";
	public static String fileNameR41 = "";
	public static int totalTrxDC = 0;
	public static int totalTrxDD = 0;
	public static int totalTrxDF = 0;
	public static int totalTrxR97 = 0;
	public static int totalTrxR41 = 0;
	
	
	public static final String PATH_LOCAL = "C://FTP_ESTAPER//in//";
	public static String dateTemp = "";
	public static int count_all = 0;
	public static final String CURR_DIR = System.getProperty("user.dir") + "\\";
	public static final String WORK_DIR = "pbkpaperless";// + Config.getInstalmentWorkDir();
	String file = "";// + Config.getInstalmentFile();
	public static final String PATH_CURRENT_DIR = CURR_DIR + "\\" + WORK_DIR + "\\";
	public static final char PADDING_ZERO = '0';
	public static final char PADDING_SPACE = ' ';
	//public static final String SETTLEMENT_PATH = "C://pbkpaperless//DANAPLUS//upload//";
	
	public static final String FILE_NAME = "dummy_payment_";	
	public static final String WORDING_BNKI = "DUMMY PAYMENT SS AMT ";
	public static final String WORDING_BNKI_LAST_LINE = " IS DONE";
	public static final int PAGESIZE = 20;
	public static final String USERS_PASSWORD_DEFAULT = "123456";
		
	public static String encryptionCommand(String text)
			throws NoSuchAlgorithmException, Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte byteData[] = md5.digest(text.getBytes());

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}		
		return sb.toString();
	}
	
	public static void copyFile(String sourceFileName,String destinationFileName) { 
	      BufferedReader br = null;
	      BufferedWriter bw = null; 

	      try {
	      	br = new BufferedReader(new FileReader( sourceFileName ));
	      	bw = new BufferedWriter(new FileWriter( destinationFileName ));
	        int c;
	        while ((c = br.read()) != -1)  {
	           bw.write(c);
	        }
	        br.close();
	        bw.close();
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	}
	
	public static void main(String stArgs[]) {
		try {
			System.out.println("pass : "+encryptionCommand("a"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
