package Twitter.Service;

import java.math.BigInteger;

public class Cipher {

	private static final BigInteger secretKey = new BigInteger("8271997208960872478735181815578166723519929177896558845922250595511921395049126920528021164569045773");

	private final BigInteger key;
	private final String message;

	public Cipher (String key, String message) {
		this.key = new BigInteger(key);
		this.message = message;
	}

	public char[] decrypt () {


		char[] intermediateMessage = undiagonalize();
		char[] decryptedMessage = unCaesarify(intermediateMessage);

		return decryptedMessage;
	}

	private char[] undiagonalize() {

		// The length is expected to be a perfect square
		int gridSize = (int) Math.sqrt(message.length());
		if (message.length() != (gridSize * gridSize)) {
			throw new RuntimeException("Message length not a perfect square: " +
					message.length());
		}
		//char[][] messageMatrix = new char [ gridSize ] [ gridSize ];
		char[] resultMessage = new char [message.length()];
		int index = 0;

		// r, c -> r*gridSize + c

		/*for (int k = 0; k < message.length(); k++) {
			char c = message.charAt(k);
			messageMatrix[ k / gridSize ][ k % gridSize ] = c;
		}*/

		// reading string along diagonals
	    /*for( int k = 0 ; k <= (gridSize -1) * 2 ; k++ ) {
	        for( int i = 0 ; i <= k ; i++ ) {
	            int j = k - i;
	            if( i <= (gridSize -1)  && j <= (gridSize -1) ) {
	            	resultMessage[index++] = messageMatrix[i][j];
	            }
	        }
	    }*/

	    // first column major walk - col=0 to gridSize-1
	    // then row major walk - row=1 to gridSize-1
	    for (int c = 0; c < gridSize; c++) {
	    	for (int r = 0; r <= c; r++) {
	    		//resultMessage[index++] = messageMatrix[r][c-r];
	    		resultMessage[index++] = message.charAt((r * gridSize) + c - r);
	    	}
	    }
	    for (int r = 1; r < gridSize; r++) {
	    	for (int c = gridSize-1; c >= r; c--) {
	    		//resultMessage[index++] = messageMatrix[r + gridSize - c - 1][c];
	    		resultMessage[index++] = message.charAt(((r + gridSize - c - 1) * gridSize) + c);
	    	}
	    }

	    return resultMessage;

	}

	private char[] unCaesarify(char[] message) {
		// Intermediate Key: Z = 1 + Y % 25
		BigInteger messageKey = key.divide(secretKey);
		BigInteger intermediateKey = messageKey.mod(new BigInteger("25"));
		int shift = intermediateKey.intValue() + 1;

		for (int k = 0; k < message.length; k++) {
			message[k] = (char)('A' + (message[k] - 'A' + 26 - shift) % 26);
		}
		return message;
	}

	public static void main(String[] args) {
		int count = 1;
		long startTime = System.nanoTime();


		for (int i = 0; i < count; i++) {

			Cipher test = new Cipher("306063896731552281713201727176392168770237379582172677299123272033941091616817696059536783089054693601",
				"URYYBBJEX");
			test.decrypt();
			System.out.println(test.decrypt());
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/count;


		System.out.println("Time: " + duration + "nanos");

	}


}
