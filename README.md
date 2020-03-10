# LZW_algorithm

### Description
The Lempel–Ziv–Welch (LZW) algorithm is a lossless data compression algorithm. LZW is an adaptive compression algorithm that does not assume prior knowledge of the input data distribution. This algorithm works well when the input data is sufficiently large and there is redundancy in the data.

This algorithm is used to compress a text file which has ASCII characters as the data, the 2 steps involeved in the algorthm are,

Encoding/Compression - this compresses the large file into a comparatively smaller one with integer codes as for every character or group of charaters which is referenced from a predefined initial table which here is an ASCII table (http://www.asciitable.com/) with the ASCII character and an integer/decimal values assigned to it in a sequential manner, the method would have an integer code appended in the file which compared to the orginal file takes less place and hence compression is acheieved. The compressed file is a 16-bit file in .lzw format. 

Decoding/Decompression - here it reads the compressed .lzw file and rebuilds the original file with the help of the same initial predefined table used in the encoding process. It generates the .txt file same as the original file inputted to the Encoding Algorithm.

The Encoding and Decoding are independent to each other, only the predefined initial table used in both the methods should be having same data initially.

#### Logic for Encoding/Compression
```javascript
MAX_TABLE_SIZE= 2(bit_length) //bit_length is number of encoding bits
initialize TABLE[0 to 255] = code for individual characters
STRING = null
while there are still input symbols:
 SYMBOL = get input symbol
 if STRING + SYMBOL is in TABLE:
 STRING = STRING + SYMBOL
 else:
 output the code for STRING
 If TABLE.size < MAX_TABLE_SIZE: // if table is not full
 add STRING + SYMBOL to TABLE // STRING + SYMBOL now has a code
 STRING = SYMBOL
output the code for STRING
```

#### Logic for Decoding/Decompression
```javascript
MAX_TABLE_SIZE= 2(bit_length)
initialize TABLE[0 to 255] = code for individual characters
CODE = read next code from encoder
STRING = TABLE[CODE]
output STRING
while there are still codes to receive:
 CODE = read next code from encoder
 if TABLE[CODE] is not defined: // needed because sometimes the
 NEW_STRING = STRING + STRING[0] // decoder may not yet have code!
 else:
 NEW_STRING = TABLE[CODE]
 output NEW_STRING
 if TABLE.size < MAX_TABLE_SIZE:
 add STRING + NEW_STRING[0] to TABLE
 STRING = NEW_STRING
```
## Programming Language
 -Java
 
## Pre-Requisite
 -Java 1.8 or above
 
## How to Compile and Run Program
 -There are 2 seperate class files that are Encoder.java and Decoder.java, both of them should be compiled.
 
 -To execute the Encoder.class 2 arguments need to be passed <Input .txt file name> <bitLength>
 
    -The input file should be placed in the same directory as that of the .java file
 
    -Once the inputs are passed the compression algorithm would get executed and output the path at which the encoded file   gets     generated. 
  
 -To execute the Decoder.class 2 arguments need to be passed <Encoded .lzw file name> <bitLength>
     
    -The input file should be placed in the same directory as that of the .java file
  
    -Once the input arguments are passed the decompression algorithm would get executed and output the path at which the decoded    file gets generated.
  
## File Structure and Design

There are 2 java files Encoder.java and Decoder.java which can be executed independently followoing the above steps. The files are in a default source folder "src" ie. LZW Algorithm --> src --> <the .java files are present here>
  
  ##### Encoder.java
   
   -This file has the constructor that initiates the predefined ASCII table.
   -main()
     
     The main methods takes the input arguments
	   args[0] - name of the input file which is a text file that has to be encoded
	   args[1] - bitLength
	   Passes these arguments as input to the readInputFiletoString() then encoder() and lastly generateEncodedOutputFile()
     
     
   -readInputFiletoString(String fileName)
     
     This method takes the filename as the input and reads the file from the path. 
   
   -encoder(String inputString, double bitLength, String fileName)
     
     This method follows the logic of Lempel–Ziv–Welch (LZW) algorithm 
	   and encodes the input string into integer codes which is stored in an ArrayList
     
   -generateEncodedOutputFile(ArrayList encodedCode,String inFileName)
	   
     This method is used to generate a compressed output 16-bit file (.lzw) 
	   in the same path where the input file was with .lzw extension 
	   it reads the encoded codes in for each block and writes it to the file.
     Returns the path at which the file was created.
     
  ##### Decoder.java
  
  -This file has the constructor that initiates the predefined ASCII table.
   -main()
     
     The main methods takes the input arguments
	   args[0] - name of the input file which is the encoded file in .lzw format
	   args[1] - bitLength
	   Passes these arguments as input to the readInputFiletoString() then encoder() and lastly generateEncodedOutputFile()
     
     
   -readInputFiletoString(String filename)
     
     This method takes the filename as the input and reads the file from the path
	   since the file is encoded as a 16-bit compressed file, it reads in the same format
	   and puts the encoded integers into an ArrayList  
   
   -decoder(ArrayList<Integer> inputCodes, double bitLength , String fileName )
     
     This method follows the logic of Lempel–Ziv–Welch (LZW) algorithm for decompression
	   and decodes the encoded integer codes into a String which is stored in an Stringbuffer
     
   -generateDecodedFile(StringBuffer decodedString, String inFileName)
	   
     This method is used to generate a .txt file which has the decoded data for the encoded .lzw file
	   in the same path where the encoded file was present 
	   it writes the whole stringbuffer as a string into the text file using the BufferedWriter class

## Data Structure

Used HashMap to implement the predefined initial table in both Encoding and decoding as the data in the table was the ASCII character and its corresponding integer value. Throughout the execution of the code the search and addition operations happen a multiple times for which this data structure has a very good time complexity.

Encoder.java
  HashMap<ASCII Character, Integer assigned to character in table> 
  
Decoder.java
  HashMap<Integer assigned to character in table, ASCII Character> 

To store the Integer codes respective to the Strings I have used the ArrayList as it takes less memory and its time complexity is good for search operations.

## Summary

The programs executes the algorithm very well and returns handled exceptions in scenarios like if the input file to the method is not in format etc. The encoder program creates a 16bit format compressed file which again can be decoded by the decoder program to regenerate the original .txt file.  
  
     
     
     
