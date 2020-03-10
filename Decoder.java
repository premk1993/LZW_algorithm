/*
 * @author : Premkumar Sreedhar Vemula
 * @StudentID: 801101045
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Decoder {
	Map<Integer, String> dcdTABLE;
	/*
	 *This path has been hardcoded to avoid the default path, kindly change it as per your system config.
	 *Since the path doesn't change for this implementation it has been taken as a static final variable. 
	 * */
	static final String inputFilePath=Paths.get("").toAbsolutePath().toString().concat("/");
	
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Description
	 * In the constructor the initial table is created, which here is the ASCII table
	 * */
	public Decoder() {
		dcdTABLE = new HashMap<Integer,String>();
		for (int i = 0; i < 255 ; i++)
			dcdTABLE.put(i, "" + (char) i);
	}
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Description:
	 * The main methods takes the input arguments
	 * args[0] - name of the input file which is the encoded file in .lzw format
	 * args[1] - bitLength
	 * Passes these arguments as input to the readInputFiletoString()
	 * */ 
	
	public static void main(String[] args) throws IOException {
		
		String inputFilename=null;	
		inputFilename=args[0];
		if(!inputFilename.substring(inputFilename.lastIndexOf(".")).equals(".lzw")) {
			System.out.println("Please check the input file format, it should be in .lzw format");
		}
		else {
		double bitLength=Integer.parseInt(args[1]);
		Decoder dcd = new Decoder();
		if(Decoder.readInputFiletoString(inputFilename).isEmpty())
			System.out.println("Please check the input file, it is either empty or not in the same directory as the class file");
		else {
			dcd.decoder(Decoder.readInputFiletoString(inputFilename), bitLength, inputFilename);
			
		}
		}
	}
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Parameters:
	 * inputCodes - encoded integer codes from the input file which was read in the readInputFiletoString() method
	 * bitLength - bitLength
	 * fileName - encoded file name
	 * 
	 * @Description:
	 * This method follows the logic of Lempel–Ziv–Welch (LZW) algorithm 
	 * and decodes the encoded integer codes into a String which is stored in an Stringbuffer
	 * */
	
	public void decoder(ArrayList<Integer> inputCodes, double bitLength , String fileName ) throws IOException {
		double maxTblSize=Math.pow(2, bitLength);
		String prevString;
		String currString;
		prevString=dcdTABLE.get(inputCodes.get(0));
		StringBuffer dcdOutputString= new StringBuffer();
		dcdOutputString.append(prevString);
		for (int i=1; i<inputCodes.size();i++) {
			
			if(!dcdTABLE.containsKey(inputCodes.get(i)))
				currString=prevString.concat(String.valueOf((prevString.charAt(0))));
			else
				currString=dcdTABLE.get(inputCodes.get(i));
			dcdOutputString.append(currString);
			
			if(dcdTABLE.size()<maxTblSize)
				dcdTABLE.put(dcdTABLE.size()+1, prevString.concat(String.valueOf(currString.charAt(0))));
			prevString=currString;	
		}
		generateDecodedFile(dcdOutputString,fileName);
	}
	
	/* @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Parameters: 
	 * fileName - name of the encoded input file which is to be decoded
	 * 
	 * @Description:
	 * This method takes the filename as the input and reads the file from the path
	 * since the file is encoded as a 16-bit compressed file, it reads in the same format
	 * and puts the encoded integers into an ArrayList  
	 * */
	
	public static ArrayList<Integer> readInputFiletoString(String filename) {
		ArrayList<Integer> encodedCodes = new ArrayList<Integer>();
		 //String data = null;
		 try {
			 
			 BufferedReader br = null;
				InputStream inputStream  = new FileInputStream(inputFilePath.concat(filename));
				Reader inputStreamReader = new InputStreamReader(inputStream, "UTF-16BE"); // The Charset UTF-16BE is used to read the 16-bit compressed file.
				//Reader inputStreamReader = new InputStreamReader(inputStream);
				br = new BufferedReader(inputStreamReader);
				  
				double value=0;
		         while((value = br.read()) != -1)
		         {
		        	 encodedCodes.add((int) value);
		         }   	
		         br.close();
		     
		} 
		 catch(Exception e) {
			 System.out.println("Error while reading file to string. Error is as below: " +e);
		 }
		return encodedCodes;
	 }
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Parameters:
	 * decodedString - Stringbuffer which has the decoded string for the encoded input
	 * inFileName - name of the input encoded file.
	 * 
	 * @Description:
	 * This method is used to generate a .txt file which has the decoded data for the encoded .lzw file
	 * in the same path where the encoded file was present 
	 * it writes the whole stringbuffer as a string into the text file using the BufferedWriter class
	 * */
	public void generateDecodedFile(StringBuffer decodedString, String inFileName) throws IOException {
		
		String decodedFilePath= inputFilePath.concat(inFileName.substring(0, inFileName.indexOf("."))+"_decoded.txt");
		 BufferedWriter writer = new BufferedWriter(new FileWriter(decodedFilePath));
		    writer.write(decodedString.toString());
		    writer.flush();
		    writer.close();
		    
		 System.out.println("Decoded file generated at path: "+decodedFilePath);   	
	}
}
