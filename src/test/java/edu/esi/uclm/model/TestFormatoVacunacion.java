package edu.esi.uclm.model;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import edu.uclm.esi.exceptions.SiGeVaException;

@RunWith(SpringRunner.class)
class TestFormatoVacunacion {

	private FormatoVacunacion formato;

	void FormatoVacunacionTester() {
		try {
			formato = new FormatoVacunacion("12:30", "16:30", 30, 10);
			assertTrue(formato.horasCorrectas());
			fail("Ha detectado que la hora de inicio es posterior a la de fin");

			formato.setHoraInicioVacunacion("17:00");

			assertFalse(formato.horasCorrectas());
			fail("Ha detectado que el formato de horas es correcto cuando la hora de inicio es posterior");

			formato.setHoraInicioVacunacion("fallo");
			Exception exception = assertThrows(SiGeVaException.class, () -> formato.horasCorrectas());
			fail("Acepta el nuevo texto como un formato de horas");

			String expectedMessage = "El formato introducido no es válido ";
			String actualMessage = exception.getMessage();

			assertTrue(actualMessage.contains(expectedMessage));
		} catch (Exception e) {

		}
	}
}
