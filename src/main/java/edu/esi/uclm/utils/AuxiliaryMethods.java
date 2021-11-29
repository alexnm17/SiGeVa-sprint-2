package edu.esi.uclm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import edu.esi.uclm.exceptions.SigevaException;

public class AuxiliaryMethods {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	private static final String MSG_CONFLICT_DNI = "No cumple con el formato de un DNI";
	
	private AuxiliaryMethods() {
		throw new IllegalStateException("Utility class");
	}
	
	public static void comprobarCampoVacio(String campo) throws SigevaException {
		if(campo.equalsIgnoreCase("")) throw new SigevaException(HttpStatus.NOT_IMPLEMENTED,"El campo esta vacio.");
	}
	
	public static void comprobarDni(String dni) throws SigevaException {
		char[] cadenaDni = dni.toCharArray();
		if (cadenaDni.length != 9)
			throw new SigevaException(HttpStatus.CONFLICT, MSG_CONFLICT_DNI);
		for (int i = 0; i < 7; i++)
			if (!Character.isDigit(cadenaDni[i]))
				throw new SigevaException(HttpStatus.CONFLICT, MSG_CONFLICT_DNI);
		if (!Character.isLetter(cadenaDni[8]))
			throw new SigevaException(HttpStatus.CONFLICT, MSG_CONFLICT_DNI);
	}
	
	public static void comprobarEmail(String email) throws SigevaException {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		if (!matcher.find())
			throw new SigevaException(HttpStatus.CONFLICT, MSG_CONFLICT_DNI);
	}
	
	public static boolean controlarContrasena(String password) throws SigevaException {
		if (password.length() < 8)
			throw new SigevaException(HttpStatus.CONFLICT, "La contraseña no tiene la longitud adecuada");
		if (password.equals(password.toLowerCase()))
			throw new SigevaException(HttpStatus.CONFLICT, "La contraseña no contiene una letra mayuscula");
		if (password.equals(password.toUpperCase()))
			throw new SigevaException(HttpStatus.CONFLICT, "La contraseña no contiene una letra minuscula");
		return true;
	}
}
