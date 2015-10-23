package Twitter;

import java.math.BigInteger;


public class Cipher {
	
	private static BigInteger secretKey = new BigInteger("8271997208960872478735181815578166723519929177896558845922250595511921395049126920528021164569045773");
	
	private BigInteger key;
	private String message;
	
	public Cipher (String key, String message) {
		this.key = new BigInteger(key);
		this.message = message;
	}
	
	public String decrypt () {
		
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
		BigInteger messageKey = key.divide(secretKey);
		BigInteger intermediateKey = messageKey.mod(new BigInteger("25"));
		int shift = Integer.parseInt(intermediateKey.toString()) + 1;

		for (int k = 0; k < message.length; k++) {
			message[k] = (char)(65 + (message[k] - 65 + shift) % 26);
		}
		return message;
		
	}
	
	public static void main(String[] args) {
		int count = 100000;
		long startTime = System.nanoTime();

		
		for (int i = 0; i < count; i++) {
		
			Cipher test = new Cipher("306063896731552281713201727176392168770237379582172677299123272033941091616817696059536783089054693601", 
				"URYYBBJEX");
			test.decrypt();
			//System.out.println(test.decrypt());
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/count;
		
		
		System.out.println("Time: " + duration + "nanos");

	}
	
	
}
