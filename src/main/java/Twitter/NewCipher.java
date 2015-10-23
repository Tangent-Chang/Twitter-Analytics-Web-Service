package Twitter;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.LinkedHashMap;


public class NewCipher {
	private String key;
	private String message;
	private LinkedHashMap<String, Integer> ketTable;
	
	public NewCipher () {
		loadkeyTable();
	}
	
	public void loadkeyTable() {
		
		ketTable = new LinkedHashMap<String, Integer>();
	    String line;
	    try{
			BufferedReader reader = new BufferedReader(new FileReader("keyTable.txt"));
	      line = reader.readLine();
	      Integer i = 1;
	      while (line != null && !line.isEmpty()) {
	    	  ketTable.put(line, i++);
	    	  line = reader.readLine();
	      }
	    } catch (Exception ex) {
	      System.out.println("Please put dictionary.txt under current folder");
	      System.out.println(ex);
	    }
	}
	
	public String decrypt (String key, String message) {
		this.key = key;
		this.message = message;
		
		char[] intermediateMessage = undiagonalize();
		char[] decryptedMessage = unCaesarify(intermediateMessage);
		
		return new String(decryptedMessage);
	}
	
	private char[] undiagonalize() {
		
		// The length is expected to be a perfect square
		int gridSize = (int) Math.sqrt(message.length());
		char[][] messageMatrix = new char [ gridSize ] [ gridSize ];
		char[] resultMessage = new char [message.length()];
		int index = 0;
		
		for (int k = 0; k < message.length(); k++) {
			char c = (char) message.charAt(k);
			messageMatrix[ k / gridSize ][ k % gridSize ] = c;
		}
		
		// reading string along diagonals
	    for( int k = 0 ; k <= (gridSize -1) * 2 ; k++ ) {
	        for( int i = 0 ; i <= k ; i++ ) {
	            int j = k - i;
	            if( i <= (gridSize -1)  && j <= (gridSize -1)  ) {
	            	resultMessage[index++] = (Character)messageMatrix[i][j];
	            }
	        }
	    }
	    
	    return resultMessage;
		
	}
	
	private char[] unCaesarify(char[] message) {
		// Intermediate Key: Z = 1 + Y % 25 

		int messageKey = ketTable.get(key);
		int shift = messageKey % 25 + 1;
		
		for (int k = 0; k < message.length; k++) {
			message[k] = (char)(65 + (message[k] - 65 + shift) % 26);
		}
		
		return message;
	}
	
	public static void main(String[] args) {

		NewCipher test = new NewCipher();
		
		int count = 100000;
		long startTime = System.nanoTime();
		for (int i = 0; i < count; i++) {
			test.decrypt("306063896731552281713201727176392168770237379582172677299123272033941091616817696059536783089054693601", 
					"URYYBBJEX");
			//System.out.println(test.decrypt());
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/count;
		
		System.out.println("Time: " + duration + "nanos");

	}
	
	public void generateTable() {

	    try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("keyTable.txt"));
			BigInteger secretKey = new BigInteger("8271997208960872478735181815578166723519929177896558845922250595511921395049126920528021164569045773");
			for (int i = 1; i < 10000; i++) {
				BigInteger messageKey = secretKey.multiply(new BigInteger(String.valueOf(i)));
				writer.write(messageKey.toString() + "\n");
			}
	    } catch (Exception ex) {
	      System.out.println("Please put dictionary.txt under current folder");
	      System.out.println(ex);
	    }
	}
}
