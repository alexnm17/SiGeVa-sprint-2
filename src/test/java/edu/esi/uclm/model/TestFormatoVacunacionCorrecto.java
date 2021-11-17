package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.exceptions.SigevaException;
<<<<<<< HEAD
=======

>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa

class TestFormatoVacunacionCorrecto {

	@Test
	public void test() {
		FormatoVacunacion formato = new FormatoVacunacion("12:30", "16:30", 30, 10);
		try {
			boolean resultado = formato.horasCorrectas();
			assertTrue(resultado);
		} catch (SigevaException e) {
<<<<<<< HEAD

=======
			
			e.printStackTrace();
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa
		}
	}

}
