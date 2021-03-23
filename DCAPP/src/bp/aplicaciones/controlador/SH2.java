package bp.aplicaciones.controlador;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SH2 {

	//Metodo para crear clave de 256bits.
	public static String getSHA256(String input) {

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	public static String getSHA512(String input) {

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	
	  public static void main(String[] argv) {
	  
	  String inputValue = "123";
	  
	  // With the java libraries 
	  String sha256 = getSHA256(inputValue);
	  
	  System.out.println("The SHA-256 of \"" + inputValue + "\" is:");
	  System.out.println(sha256); 
	  System.out.println();
	  
	  // With Apache commons sha256 =
	  org.apache.commons.codec.digest.DigestUtils.sha256Hex(inputValue);
	  
	  System.out.println("The SHA-256 of \"" + inputValue + "\" is:");
	  //System.out.println(sha256); 
	  System.out.println();
	  
	  // With the java libraries String sha512 = getSHA512( inputValue );
	  
	  System.out.println("The SHA-512 of \"" + inputValue + "\" is:");
	  //System.out.println(sha512); 
	  System.out.println();
	  
	  // With Apache commons sha512 =
	  org.apache.commons.codec.digest.DigestUtils.sha512Hex(inputValue);
	  
	  System.out.println("The SHA-512 of \"" + inputValue + "\" is:");
	  //System.out.println(sha512);
	  
	  }
	 

}
