/*
 * @author : Premkumar Sreedhar Vemula
 * @StudentID: 801101045
 * */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Encoder {
	Map<String, Integer> encTABLE;
	 
	//static final String inputFilePath="/Users/premkumar/eclipse-workspace/LZW Algorithm/Encoded Files/";
	static final String inputFilePath=Paths.get("").toAbsolutePath().toString().concat("/");
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Description
	 * In the constructor the initial table is created, which here is the ASCII table
	 * */
	
	public Encoder() {
		encTABLE = new HashMap<String, Integer>();
		for (int i = 0; i < 255 ; i++)
			encTABLE.put("" + (char) i, i);
		
	}
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Parameters:
	 * inputString - String of characters to be encoded
	 * bitLength - bitLength
	 * fileName - input file name
	 * 
	 * @Description:
	 * This method follows the logic of Lempel–Ziv–Welch (LZW) algorithm 
	 * and encodes the input string into integer codes which is stored in an ArrayList
	 * */
	
	public void encoder(String inputString, double bitLength, String fileName) {
		
		ArrayList<Integer> encodedCodes=new ArrayList<Integer>();
		char[] ipchar=inputString.toCharArray();
		String iniString="";
	    int tblsize=encTABLE.size();
	    double maxTblSize=Math.pow(2, bitLength);
		for(int i=0;i<ipchar.length;i++) {
			
			try {
				if (encTABLE.containsKey(iniString.concat(String.valueOf(ipchar[i])))) {
					iniString += ipchar[i];
				} else {
					encodedCodes.add(encTABLE.get(iniString));
					if(tblsize<maxTblSize) {
						tblsize+=1;
					encTABLE.put(iniString.concat(String.valueOf(ipchar[i])), tblsize);
					}
					iniString = String.valueOf(ipchar[i]);
				} 
			} catch (Exception e) {
				System.out.println("Error in the for block while executing the encode algo. Error as follows  ------->"+e);
			}	
		}	
		encodedCodes.add(encTABLE.get(iniString));
		generateEncodedOutputFile(encodedCodes,fileName);
		
	}
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Parameters:
	 * encodedCode - arraylist which consists of the encoded codes for the input text
	 * inFileName - name of the input file.
	 * 
	 * @Description:
	 * This method is used to generate a compressed output 16-bit file (.lzw) 
	 * in the same path where the input file was with .lzw extension 
	 * it reads the encoded codes in for each and writes it to the file.
	 * */

	public void generateEncodedOutputFile(ArrayList<Integer> encodedCode,String inFileName) 
	{
		  OutputStream outputStream = null; 
		  String outputFilename=inputFilePath.concat(inFileName.substring(0, inFileName.indexOf(".")).concat(".lzw"));  
		  try { outputStream = new
				    FileOutputStream(outputFilename);
		  //Writer outputStreamWriter = new OutputStreamWriter(outputStream);
		  Writer outputStreamWriter = new OutputStreamWriter(outputStream,"UTF_16BE");		//The charset is used to generate a 16-bit file   
        encodedCode.forEach(code -> {
			try {
				if(code==null)
					outputStreamWriter.write(0); // this block is for cases if the initial table has no values in it or if the first char wont find a code from the table which wouldnt happen because ASCII table has mostly all the char.
				else
					outputStreamWriter.write(code);
			} catch (IOException e) {
				System.out.println("Error while creating encoded file. "+e);
				
				
			}
		});
        outputStreamWriter.close();  
			
        
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found error, kindly check if the path exists. "+e);
		}  
		catch (IOException e) {
			System.out.println("Error while closing the file after done writing. "+e);
			e.printStackTrace();
			
		}  
		  finally {
			  
			  System.out.println("Encoded file generated at the path: " +outputFilename);
		} 
	}
	
	/*
	 * @Authors: Premkumar Sreedhar Vemula
	 * 
	 * @Description:
	 * The main methods takes the input arguments
	 * args[0] - name of the input file which is a text file that has to be encoded
	 * args[1] - bitLength
	 * Passes these arguments as input to the readInputFiletoString()
	 * */ 

	public static void main(String[] args) throws IOException {
		String inputFilename=null;	
		inputFilename=args[0];
		if(!inputFilename.substring(inputFilename.lastIndexOf(".")).equals(".txt")) {
			System.out.println("Please check the input file format, it should be in .txt format");
		}
		else {
		double bitLength=Integer.parseInt(args[1]);
		Encoder enc = new Encoder();

		String fileData=readInputFiletoString(inputFilename);
		if(fileData.isEmpty()) {
			System.out.println("Please check the input file it is either not in the same directory or it is empty");
		}
		else {
		enc.encoder(fileData,bitLength,inputFilename);	
		}
		}
	}

/* @Authors: Premkumar Sreedhar Vemula
 * 
 * @Parameters: 
 * fileName - name of the input file which is to be encoded
 * 
 * @Description:
 * This method takes the filename as the input and reads the file from the path 
 * */

  
	public static String readInputFiletoString(String fileName) {
	 
	 String data = null;
	 try {
		 data = new String(Files.readAllBytes(Paths.get(inputFilePath.concat(fileName))));	    
	} 
	 catch(Exception e) {
		 System.out.println("Error while reading file to string. Error is as below: " +e);
	 }
	return data;
 }
	
	
}
