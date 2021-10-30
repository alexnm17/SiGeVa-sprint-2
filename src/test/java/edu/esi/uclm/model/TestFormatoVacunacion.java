package edu.esi.uclm.model;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import edu.uclm.esi.exceptions.SiGeVaException;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
public class TestFormatoVacunacion extends TestCase {

	private FormatoVacunacion formato;

	@Test
	void FormatoVacunacionCorrectaTester() {

		formato = new FormatoVacunacion("12:30", "16:30", 30, 10);
		try {
			assertTrue(formato.horasCorrectas());
		} catch (SiGeVaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Ha detectado que la hora de inicio es posterior a la de fin");

	}

	@Test
	void FormatoVacunacionIncorrectaTester() {
		try {
			formato = new FormatoVacunacion("17:00", "16:30", 30, 10);

			assertFalse(formato.horasCorrectas());
			fail("Ha detectado que el formato de horas es correcto cuando la hora de inicio es posterior");
		} catch (Exception e) {

		}
	}

	@Test
	void FormatoVacunacionFormatoNoValidoTester() {
		formato = new FormatoVacunacion("12:30", "fallo", 30, 10);

		Exception exception = assertThrows(SiGeVaException.class, () -> formato.horasCorrectas());
		fail("Acepta el nuevo texto como un formato de horas");

		String expectedMessage = "El formato introducido no es válido ";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void FormatoVacunacionCondicionesValidasTester() {
		formato = new FormatoVacunacion("12:30", "16:30", 30, 3);

		assertTrue(formato.condicionesValidas());
		fail("A pesar de que las condiciones son validas, no las acepta");

	}

	@Test
	void FormatoVacunacionCondicionesNoValidasTester() {
		formato = new FormatoVacunacion("12:30", "16:30", 30, 30);

		assertFalse(formato.condicionesValidas());
		fail("Aunque las condiciones no son justas, las acepta");

	}

}
